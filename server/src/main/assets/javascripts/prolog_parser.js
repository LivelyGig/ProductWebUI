// -*- mode: Javascript;-*-
// Filename:    candidate_prolog_parser_in_js.js
// Authors:     lgm
// Creation:    Thu Mar 13 11:41:47 2014
// Copyright:   Not supplied
// Description:
// ------------------------------------------------------------------------

var prologParser = (function() {
    // Object (of a style...) definitions:
    // Rule = (Head, Body)
    // Head = Term
    // Body = [Term]
    // Term = (id, Parameters)
    // Parameters {Partlist} = [Part]
    // Part = Variable | Atom | Term

    function Variable(head) {
        this.name = head;
        // I had a sneaking suspicion that the (rather nice, I reckon)
        // idiom below returned a closure-like reference rather than an
        // anonymous function reference. It does; but I'm not overly
        // concerned with efficiency here. This is is Prolog interpreter
        // written in JS, for goodness' sake!
        this.print = function() {
            print(this.name);
        };
        this.type = "Variable";
    }

    function Atom(head) {
        this.name = head;
        this.print = function() {
            print(this.name);
        };
        this.type = "Atom";
    }

    function Term(head, list) {
        this.name = head;
        this.partlist = new Partlist(list);
        this.print =
            function() {
                if (this.name == "cons") {
                    var x = this;
                    while (x.type == "Term" && x.name == "cons" && x.partlist.list.length == 2) {
                        x = x.partlist.list[1];
                    }
                    if ((x.type == "Atom" && x.name == "nil") || x.type == "Variable") {
                        x = this;
                        print("[");
                        var com = false;
                        while (x.type == "Term" && x.name == "cons" && x.partlist.list.length == 2) {
                            if (com) print(", ");
                            x.partlist.list[0].print();
                            com = true;
                            x = x.partlist.list[1];
                        }
                        if (x.type == "Variable") {
                            print(" | ");
                            x.print();
                        }
                        print("]");
                        return;
                    }
                }
                print("" + this.name + "(");
                this.partlist.print();
                print(")");
            };
        this.type = "Term";
    }

    function Partlist(list) {
        this.list = list;
        this.print =
            function() {
                for (var i = 0; i < this.list.length; i++) {
                    this.list[i].print();
                    if (i < this.list.length - 1)
                        print(", ");
                }
            };
    }

    function Body(list) {
        this.list = list;
        this.print =
            function() {
                for (var i = 0; i < this.list.length; i++) {
                    this.list[i].print();
                    if (i < this.list.length - 1)
                        print(", ");
                }
            };
    }

    function Rule(head) {
        return new Rule(head, null);
    }

    function Rule(head, bodylist) {
        this.head = head;
        if (bodylist != null)
            this.body = new Body(bodylist);
        else
            this.body = null;

        this.print =
            function() {
                if (this.body == null) {
                    this.head.print();
                    print(".\n");
                } else {
                    this.head.print();
                    print(" :- ");
                    this.body.print();
                    print(".\n");
                }
            };
    }

    // The Tiny-Prolog parser goes here.
    function Tokeniser(string) {
        this.remainder = string;
        this.current = null;
        this.type = null; // "eof", "id", "var", "punc" etc.
        this.consume =
            function() {
                if (this.type == "eof") return;
                // Eat any leading WS
                var r = this.remainder.match(/^\s*(.*)$/);
                if (r) {
                    this.remainder = r[1];
                }

                if (this.remainder == "") {
                    this.current = null;
                    this.type = "eof";
                    return;
                }

                r = this.remainder.match(/^([\(\)\.,\[\]\|]|\:\-)(.*)$/);
                if (r) {
                    this.remainder = r[2];
                    this.current = r[1];
                    this.type = "punc";
                    return;
                }

                r = this.remainder.match(/^([A-Z][a-zA-Z0-9]*)(.*)$/);
                if (r) {
                    this.remainder = r[2];
                    this.current = r[1];
                    this.type = "var";
                    return;
                }

                // URLs in angle-bracket pairs
                r = this.remainder.match(/^(\<[^\>]*\>)(.*)$/);
                if (r) {
                    this.remainder = r[2];
                    this.current = r[1];
                    this.type = "id";
                    return;
                }

                // Quoted strings
                r = this.remainder.match(/^("[^"]*")(.*)$/);
                if (r) {
                    this.remainder = r[2];
                    this.current = r[1];
                    this.type = "id";
                    return;
                }

                r = this.remainder.match(/^([a-zA-Z0-9]*)(.*)$/);
                if (r) {
                    this.remainder = r[2];
                    this.current = r[1];
                    this.type = "id";
                    return;
                }

                this.current = null;
                this.type = "eof";

            };
        this.consume(); // Load up the first token.
    }

    var tokenstring;
    var currenttoken;

    function ParseRule(tk) {
        // A rule is a Head followed by . or by :- Body

        var h = ParseHead(tk);
        if (!h) return null;

        if (tk.current == ".") {
            // A simple rule.
            return new Rule(h);
        }

        if (tk.current != ":-") return null;
        tk.consume();
        var b = ParseBody(tk);

        if (tk.current != ".") return null;

        return new Rule(h, b);
    }

    function ParseHead(tk) {
        // A head is simply a term. (errors cascade back up)
        return ParseTerm(tk);
    }

    function ParseTerm(tk) {
        // Term -> id ( optParamList )

        if (tk.type != "id") return null;
        var name = tk.current;
        tk.consume();

        if (tk.current != "(") return null;
        tk.consume();

        var p = [];
        var i = 0;
        while (tk.current != ")") {
            if (tk.type == "eof") return null;

            var part = ParsePart(tk);
            if (part == null) return null;

            if (tk.current == ",") tk.consume();
            else if (tk.current != ")") return null;

            // Add the current Part onto the list...
            p[i++] = part;
        }
        tk.consume();

        return new Term(name, p);
    }

    // This was a beautiful piece of code. It got kludged to add [a,b,c|Z] sugar.
    function ParsePart(tk) {
        // Part -> var | id | id(optParamList)
        // Part -> [ listBit ] ::-> cons(...)
        if (tk.type == "var") {
            var n = tk.current;
            tk.consume();
            return new Variable(n);
        }

        if (tk.type != "id") {
            if (tk.type != "punc" || tk.current != "[") return null;
            // Parse a list (syntactic sugar goes here)
            tk.consume();
            // Special case: [] = new atom(nil).
            if (tk.type == "punc" && tk.current == "]") {
                tk.consume();
                return new Atom("nil");
            }

            // Get a list of parts into l
            var l = [],
                i = 0;

            while (true) {
                var t = ParsePart(tk);
                if (t == null) return null;

                l[i++] = t;
                if (tk.current != ",") break;
                tk.consume();
            }

            // Find the end of the list ... "| Var ]" or "]".
            var append;
            if (tk.current == "|") {
                tk.consume();
                if (tk.type != "var") return null;
                append = new Variable(tk.current);
                tk.consume();
            } else {
                append = new Atom("nil");
            }
            if (tk.current != "]") return null;
            tk.consume();
            // Return the new cons.... of all this rubbish.
            for (i--; i >= 0; i--) append = new Term("cons", [l[i], append]);
            return append;
        }

        var name = tk.current;
        tk.consume();

        if (tk.current != "(") return new Atom(name);
        tk.consume();

        var p = [];
        var i = 0;
        while (tk.current != ")") {
            if (tk.type == "eof") return null;

            var part = ParsePart(tk);
            if (part == null) return null;

            if (tk.current == ",") tk.consume();
            else if (tk.current != ")") return null;

            // Add the current Part onto the list...
            p[i++] = part;
        }
        tk.consume();

        return new Term(name, p);
    }

    function ParseBody(tk) {
        // Body -> Term {, Term...}

        var p = [];
        var i = 0;

        var t;
        while ((t = ParseTerm(tk)) != null) {
            p[i++] = t;
            if (tk.current != ",") break;
            tk.consume();
        }

        if (i == 0) return null;
        return p;
    }

    function StringToTerm(s) {
        return ParseTerm(new Tokeniser(s));
    }
    /* Method to get labels */
    function StringToLabel(json) {
//            console.log(json)

            var result = []
            for (var j = 0; j < json.length; j ++){
                var term = StringToTerm(json[j])
                termToLabel(term,result);
            }
            return result

        }

    var termToLabel = function(term, result) {
        var l = new Label(null, null, null, term);
        l.parentUid = "self"
        if (term.name == "and" || term.name == "all") Lambda.iter(term.partlist.list, function(term1) {
            larray = larray.concat(ui.helper.PrologHelper.termToLabel(term1));
        });
        else {

            if (term.name == "node") {
                /*l.progeny = new Array();*/
                if (term.partlist) {
                    var termParts = term.partlist.list;
                    var progenyTerm = termParts[termParts.length - 1];
                    var progenyTermParts = progenyTerm.partlist.list;
                    for (var i = 0; i < progenyTermParts.length; i++) {
                        var progeny = termToLabel(progenyTermParts[i], result);
                        var child = progeny;
                        // l.progeny.push(child);
                        child.parentUid = l.uid;
                        // larray = larray.concat(progeny);
                    }
                }


            }
        }
        result.push(l)
        return l;
    }
    var m3 = {}
    m3.util = {}
    m3.util.UidGenerator = function() { }
    m3.util.UidGenerator.chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabsdefghijklmnopqrstuvwxyz0123456789";
    m3.util.UidGenerator.__name__ = ["m3","util","UidGenerator"];
    m3.util.UidGenerator.create = function(length) {
    	if(length == null) length = 20;
    	var str = new Array();
    	var charsLength = m3.util.UidGenerator.chars.length;
    	while(str.length == 0) {
    		var ch = m3.util.UidGenerator.randomChar();
    		if(m3.util.UidGenerator.isLetter(ch)) str.push(ch);
    	}
    	while(str.length < length) {
    		var ch = m3.util.UidGenerator.randomChar();
    		str.push(ch);
    	}
    	return str.join("");
    }
    m3.util.UidGenerator.isLetter = function($char) {
    	var _g1 = 0, _g = m3.util.UidGenerator.chars.length;
    	while(_g1 < _g) {
    		var i = _g1++;
    		if(m3.util.UidGenerator.chars.charAt(i) == $char) return true;
    	}
    	return false;
    }
    m3.util.UidGenerator.randomNum = function() {
    	var max = m3.util.UidGenerator.chars.length - 1;
    	var min = 0;
    	return min + Math.round(Math.random() * (max - min) + 1);
    }
    m3.util.UidGenerator.randomIndex = function(str) {
    	var max = str.length - 1;
    	var min = 0;
    	return min + Math.round(Math.random() * (max - min) + 1);
    }
    m3.util.UidGenerator.randomChar = function() {
    	var i = 0;
    	while((i = m3.util.UidGenerator.randomIndex(m3.util.UidGenerator.chars)) >= m3.util.UidGenerator.chars.length) continue;
    	return m3.util.UidGenerator.chars.charAt(i);
    }
    m3.util.UidGenerator.randomNumChar = function() {
    	var i = 0;
    	while((i = m3.util.UidGenerator.randomIndex(m3.util.UidGenerator.nums)) >= m3.util.UidGenerator.nums.length) continue;
    	return Std.parseInt(m3.util.UidGenerator.nums.charAt(i));
    }

    m3.util.ColorProvider = function() { }

    m3.util.ColorProvider.__name__ = ["m3","util","ColorProvider"];
    m3.util.ColorProvider.getNextColor = function() {
    	if(m3.util.ColorProvider._INDEX >= m3.util.ColorProvider._COLORS.length) m3.util.ColorProvider._INDEX = 0;
    	return m3.util.ColorProvider._COLORS[m3.util.ColorProvider._INDEX++];
    }
    m3.util.ColorProvider.getRandomColor = function() {
    	var index;
    	do index = Std.random(m3.util.ColorProvider._COLORS.length); while(m3.util.ColorProvider._LAST_COLORS_USED.contains(index));
    	m3.util.ColorProvider._LAST_COLORS_USED.push(index);
    	return m3.util.ColorProvider._COLORS[index];
    }

    var Label = function(text, color, imgSrc, term) {
        // ui.model.ModelObj.call(this);
        this.uid = m3.util.UidGenerator.create(32);
        if (term == null) {
            this.text = text;
            if (color == null) this.color = m3.util.ColorProvider.getNextColor();
            else this.color = color;
            if (imgSrc == null) this.imgSrc = "";
            else this.imgSrc = imgSrc;
        } else {
            if (term.partlist) {
                var termParts = term.partlist.list;
                var textTerm = termParts[0];
                var textTermParts = textTerm.partlist.list;
                var textTermAtom = textTermParts[0];
                var displayTerm = termParts[1];
                var displayTermParts = displayTerm.partlist.list;
                var colorTerm = displayTermParts[0];
                var colorTermParts = colorTerm.partlist.list;
                var colorTermAtom = colorTermParts[0];
                var imageTerm = displayTermParts[1];
                var imageTermParts = imageTerm.partlist.list;
                var imageTermAtom = imageTermParts[0];
                var textTermLiteral = textTermAtom.name;
                this.text = textTermLiteral.substring(1, textTermLiteral.length - 1);
                var colorTermLiteral = colorTermAtom.name;
                this.color = colorTermLiteral.substring(1, colorTermLiteral.length - 1);
                var imageTermLiteral = imageTermAtom.name;
                this.imgSrc = imageTermLiteral.substring(1, imageTermLiteral.length - 1);
            }

        }
    };

    return {
        StringToLabel: StringToLabel,
        Tokeniser: Tokeniser,
        ParseTerm: ParseTerm,
        Variable: Variable,
        Atom: Atom,
        Term: Term,
        Partlist: Partlist,
        Body: Body,
        Rule: Rule,
        //LabelData : LabelData,
        //StringToLabelList : StringToLabelList
    }
})();
