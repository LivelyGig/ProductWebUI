describe("SRPClient", function() {

  // Test data

  var email = 'testonly@test.com', password = 'qwerty12345';

  var salt = "00baff7984a42298dd4cce4822406d4eba09595938e769075d5641cb7600eee51e32fbd3bbc69924996cc395770db27019529ae1f478517a4125309ae8313d4f33336d47f709845fc398fc73b52f3d7f79cae444b3a262038823d245411c05a3d1fe199c9ae4ff634103eeb15c4525cdcf1bd7d8ea874cdc7a9d6132b818c9530a";
  var kHex = "2d08116ead5b3f57f48fa51d4d39fa68fdef1700c791e4ea18a037f4a3389747444239d648dca2bfceb0d4f2c836e571765391a22347426556761b9a6e33f047";

  var srpc = new SRPClient(email, password);
  var srpc2 = new SRPClient(email, password);

  var group = sjcl.keyexchange.srp.knownGroup('1024');
  var N = group.N;
  var g = group.g

  var k = sjcl.bn.fromBits(sjcl.codec.hex.toBits(kHex));

  var x = srpc.calculateX(salt);
  var v = srpc.calculateV(salt);

  var verifier = srpc.getVerifierHex(salt);

  var B = calculateServerB(k, v);
  var u = srpc.calculateU(srpc.A, B);

  var serverS = calculateServerS(v, sjcl.bn.fromBits(u), srpc.A);
  var serverM = calculateServerM(srpc.A, B, serverS);
  var serverM2 = calculateServerM(srpc.A, sjcl.bn.fromBits(sjcl.codec.hex.toBits(serverM)), serverS);

  var clientS = srpc.calculateS(B, sjcl.bn.fromBits(x), sjcl.bn.fromBits(u));
  var clientM = srpc.getMHex(sjcl.codec.hex.fromBits(B.toBits()), salt);

  // Server-side stuff

   var b = sjcl.bn.fromBits(sjcl.random.randomWords(8,0)); //bitArray

  function calculateServerB(k, v) {
    return k.mul(v).mod(N).add(g.powermod(b, N)).mod(N);
  }

  function calculateServerS(v, u, A) {
    return v.powermod(u, N).mul(A).mod(N).powermod(b, N);
  }

  function calculateServerM(A, B, S) {
    var toHash = sjcl.bitArray.concat(sjcl.bitArray.concat(srpc._getPadded(A), srpc._getPadded(B)), srpc._getPadded(S));
    return sjcl.codec.hex.fromBits(srpc.calculateH(toHash));
  }

  // Specs

  it("should calculate padLength", function() {
    expect(srpc.padLength).toEqual(256);
  });

  it("should generate `a` value on creation", function() {
    expect(srpc.a).toBeDefined();
  });

  it("should generate `A` value on creation", function() {
      expect(srpc.A).toBeDefined();
  });

  it("should load `N` of 1024-bit group on creation", function() {
    expect(srpc.N).toEqual(N);
  });

  it("should load `g` of 1024-bit group on creation", function() {
    expect(srpc.g).toEqual(g);
  });

  it("should calculate `k` value on creation", function() {
    expect(srpc.k).toEqual(k);
  });

  it("should generate `a` value randomly", function() {
    expect(srpc.a).not.toEqual(srpc2.a);
  });

  it("should calculate `x` value", function() {
    expect(x).toBeDefined();
  });

  it("should calculate `v` value", function() {
    expect(v).toBeDefined();
  });

  it("should generate verifier hex string", function() {
    expect(sjcl.codec.hex.fromBits(v.toBits())).toEqual(verifier);
  });

  it("should calculate `u` value", function() {
    expect(u).toBeDefined();
  });

  it("should calculate `S` value", function() {
    expect(clientS).toEqual(serverS);
  });

  it("should calculate `M` hex string", function() {
    expect(clientM).toEqual(serverM);
  });

  it("should calculate and store `M2` value", function() {
    expect(srpc.M2).toBeDefined();
  });

  it("should match the server-side `M2` value", function() {
    expect(srpc.matches(serverM2)).toEqual(true);
  });

});