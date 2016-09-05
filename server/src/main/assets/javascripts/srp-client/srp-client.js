/*
 * Construct an SRP object by a username and a password.
 *
 * Uses 1024-bit group numbers and SHA-512 hashing algorithm
 *
 The following is a description of SRP-6 and 6a, the latest versions of SRP:

 N    A large safe prime (N = 2q+1, where q is prime)
      All arithmetic is done modulo N.
 g    A generator modulo N
 k    Multiplier parameter (k = H(N, g) in SRP-6a, k = 3 for legacy SRP-6)
 s    User's salt
 I    Username
 p    Cleartext Password
 H()  One-way hash function
 ^    (Modular) Exponentiation
 u    Random scrambling parameter
 a,b  Secret ephemeral values
 A,B  Public ephemeral values
 x    Private key (derived from p and s)
 v    Password verifier

 The host stores passwords using the following formula:

 x = H(s, p)               (s is chosen randomly)
 v = g^x                   (computes password verifier)

 The host then keeps {I, s, v} in its password database.
 The authentication protocol itself goes as follows:

 User -> Host:  I, A = g^a                  (identifies self, a = random number)
 Host -> User:  s, B = kv + g^b             (sends salt, b = random number)

 Both:  u = H(A, B)

 User:  x = H(s, p)                 (user enters password)
 User:  S = (B - kg^x) ^ (a + ux)   (computes session key)
 User:  K = H(S)

 Host:  S = (Av^u) ^ b              (computes session key)
 Host:  K = H(S)

 Now the two parties have a shared, strong session key K. To complete authentication,
 they need to prove to each other that their keys match. One possible way:

 User -> Host:  M = H(H(N) xor H(g), H(I), s, A, B, K)
 Host -> User:  H(A, M, K)

 The two parties also employ the following safeguards:

 The user will abort if he receives B == 0 (mod N) or u == 0.
 The host will abort if it detects that A == 0 (mod N).
 The user must show his proof of K first.
 If the server detects that the user's proof is incorrect,
 it must abort without showing its own proof of K.
 */

SRPClient = function (username, password) {

    // Verify presence of username.
    if (!username || !password)
        throw 'Username and password cannot be empty.';

    // Store username and password.
    this.username = username;
    this.password = password;

    var group = sjcl.keyexchange.srp.knownGroup('1024');

    // Set N and g from initialization values.
    this.N = group.N;
    this.g = group.g;

    this.padLength = 2 * ((sjcl.codec.hex.fromBits(this.N.toBits()).length * 4 + 7) >> 3);

    // Pre-compute k from N and g.
    this.k = this.k();

    // Pre-compute a and A.
    this.a = this._srpRandom();
    this.A = this.calculateA();

};

/*
 * Implementation of an SRP client conforming
 * to the SRP protocol 6A (see RFC5054).
 */
SRPClient.prototype = {

    getAHex: function() {
        return sjcl.codec.hex.fromBits(this.A.toBits());
    },

    getMHex: function(BHex, saltHex) {
        if(!BHex || !saltHex) {
            throw 'Missing parameter(-s).';
        }
        var A = this.A;
        var B = sjcl.bn.fromBits(sjcl.codec.hex.toBits(BHex));
        var u = sjcl.bn.fromBits(this.calculateU(A, B));
        var x = sjcl.bn.fromBits(this.calculateX(saltHex));
        var S = this.calculateS(B, x, u);
        var M1 = this.calculateM(A, B, S)
        // Calculate and store M2 for the further client verification
        var M2 = this.calculateM(A, sjcl.bn.fromBits(M1), S);
        this.M2 = M2;
        return sjcl.codec.hex.fromBits(M1);
    },

    getVerifierHex: function(saltHex) {
        if(!saltHex) {
            throw 'Missing parameter';
        }
        return sjcl.codec.hex.fromBits(this.calculateV(saltHex).toBits());
    },

    matches: function(M2Hex) {
        if(!M2Hex) {
            throw 'Missing parameter.';
        }

        var M2 = sjcl.bn.fromBits(sjcl.codec.hex.toBits(M2Hex));

        if (M2.mod(this.N).toString() == '0') {
            throw 'Illegal parameter.';
        }
        return sjcl.bitArray.equal(M2.toBits(), this.M2);
    },

    /**
     * @return {bn} k = H(N || g).
     */
    k: function() {
        var toHash = sjcl.bitArray.concat(this._getPadded(this.N),
                                          this._getPadded(this.g));
        return sjcl.bn.fromBits(this.calculateH(toHash));
    },

    /**
     * @param {bitArray|String} data a value to hash.
     * @return {bitArray} the hash value.
     */
    calculateH: function(data) {
        return sjcl.hash.sha512.hash(data);
    },

    /**
     *  @param {String} salt a random salt.
     *  @return {bitArray}
     */
    calculateX: function (salt) {
        if (!salt) {
            throw 'Missing parameter.';
        }
        var inner = sjcl.codec.hex.fromBits(this.calculateH(sjcl.codec.utf8String.toBits(this.username + ":" + this.password)));
        var bnSalt = sjcl.bn.fromBits(sjcl.codec.hex.toBits(salt));
        var bnInner = sjcl.bn.fromBits(sjcl.codec.hex.toBits(inner));
        var toHash = sjcl.bitArray.concat(this._getPadded(bnSalt), this._getPadded(bnInner));
        return this.calculateH(toHash);
    },

    /**
     * Calculates the verifier v = g^x % N.
     * @param {String} saltHex the server salt.
     * @return {bn}
     */
    calculateV: function(saltHex) {
        if (!saltHex) {
            throw 'Missing parameter.';
        }
        var x = this.calculateX(saltHex);
        return this.g.powermod(sjcl.bn.fromBits(x), this.N);
    },

    /**
     * Calculate u = H(A || B), which serves
     * to prevent an attacker who learns a user's verifier
     * from being able to authenticate as that user.
     * @param {bn} A an A value
     * @param {bn} B a B value
     * @return {bitArray}
     */
    calculateU: function(A, B) {
        if (!A || !B) {
            throw 'Missing parameter(-s).';
        }
        if (A.mod(this.N).toString() == '0' ||
            B.mod(this.N).toString() == '0') {
            throw 'Illegal parameter';
        }

        var toHash = sjcl.bitArray.concat(this._getPadded(A), this._getPadded(B));

        var u = this.calculateH(toHash);

        if(sjcl.bn.fromBits(u).mod(this.N).toString == '0')
            throw 'Illegal parameter';

        return u;
    },

    /**
     * Calculate the client's public value A = g^a % N.
     * @return {bn}
     */
    calculateA: function() {
        var A = this.g.powermod(sjcl.bn.fromBits(this.a), this.N);

        if (A.mod(this.N).toString() == '0')
            throw 'Illegal parameter';

        return A;
    },

    /**
     * Calculate the client's premaster secret
     * S = (B - (k * g^x)) ^ (a + (u * x)) % N.
     * @param {bn} B
     * @param {bn} x
     * @param {bn} u
     * @return {bn}
     *
     */
    calculateS: function(B, x, u) {
        if (!B || !x || !u)
            throw 'Missing parameters(-s).';

        if (B.mod(this.N).toString() == '0')
            throw 'Illegal parameter.';

        var exp = (u.mul(x)).add(sjcl.bn.fromBits(this.a));
        var tmp = ((this.g.powermod(x, this.N)).mul(this.k)).mod(this.N);
        var result = ((B.sub(tmp)).mod(this.N)).powermod(exp, this.N);

        return result;

    },

    /**
     * Calculate K = H(S)
     * @param {bn} S session key
     * @return {bn} secret key
     */
    calculateK: function (S) {
        return sjcl.bn.fromBits(this.calculateH(this._getPadded(S)));
    },

    /**
     * Calculate match M = H(A, B, K) or M = H(A, M, K)
     * @param {bn} A
     * @param {bn} B_or_M
     * @param {bn} K
     * @return {bitArray}
     */
    calculateM: function (A, B_or_M, S) {
        if (!A || !B_or_M || !S) {
            throw 'Missing parameter(-s).';
        }

        if (A.mod(this.N).toString() == '0' ||
            B_or_M.mod(this.N).toString() == '0') {
            throw 'Illegal parameter';
        }

        var toHash = sjcl.bitArray.concat(sjcl.bitArray.concat(this._getPadded(A), this._getPadded(B_or_M)), this._getPadded(S));

        return this.calculateH(toHash);

    },

    /**
    * @return (BitArray) random 64-bit value
    * */
    _srpRandom: function() {
        return sjcl.random.randomWords(8,0);
    },

    /**
     * Transform input to be prefixed with zeros to meet N hex width.
     * @param {bn} bnv
     * @return {bitArray}
     */
    _getPadded: function(bnv) {
        var bl = 2 * ((sjcl.codec.hex.fromBits(bnv.toBits()).length * 4 + 7) >> 3);
        if(bl < this.padLength) {
            return sjcl.codec.hex.toBits(this._nZeros(this.padLength - bl) + sjcl.codec.hex.fromBits(bnv.toBits()));
        } else {
            return bnv.toBits();
        }
    },

    _nZeros: function(n) {
        if(n < 1) return '';
        var t = this._nZeros(n >> 1);

        return ((n & 1) == 0) ?  t + t : t + t + '0';
    },

    _print: function(name, barr) {
        console.debug(name + " = " + sjcl.codec.hex.fromBits(barr));
    }
};
