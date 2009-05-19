//
//  ---------
// |.##> <##.|  CardContact Software & System Consulting
// |#       #|  32429 Minden, Germany (www.cardcontact.de)
// |#       #|  Copyright (c) 1999-2005. All rights reserved
// |'##> <##'|  See file COPYING for details on licensing
//  --------- 
//
// Setup runtime environment
// 

// The following definitions must match constants defined in GPByteBuffer and GPByteString

HEX = 16;                       // to match Number.toString(16) method
UTF8 = 2;
ASCII = 3;
BASE64 = 4;
CN = 5;
OID = 6;


//
// Shortcut to reset card and display atr
//
function reset() {
        card = new Card(_scsh3.reader);
            
        var atr = card.reset(Card.RESET_COLD);
        return atr;
}


//
// Shortcut to exchange APDU with card
//
function apdu(data) {
        if (typeof(card) == "undefined")
                card = new Card(_scsh3.reader);
                      
        var res = card.plainApdu(new ByteString(data, HEX));
        if (card.SW != 0x9000) {
                print("Card error " + card.SW.toString(16));
        }
        return res;
}

//
// Minimal assert() function
// 
function assert() {
	for (var i = 0; i < arguments.length; i++) {
		if (!arguments[i]) {
			throw new GPError("shell", GPError.USER_DEFINED, 0,"Assertion failed for argument " + (i + 1));
		}
	}
}


//
// Even shorter shortcuts
// 
function r()     { return reset(); };
function a(data) { return apdu(data); };
function q()     { quit(); };


//
// Display help to user
//
function help() {
        print("q | quit                 Quit shell");
        print("r | reset                Reset card in reader");
        print("a | apdu(string)         Send APDU to card");
        print("print(string, ..)        Print string(s)");
        print("load(file)               Load and execute file");
        print("assert(expression, ..)   Assert that expressions are all true");
        print("defineClass(file)        Load Java class defining native objects");
        print("or any other valid ECMAScript expression.");
        print("See doc/index.html for the complete documentation.");
}


// All GP classes report errors through GPError
defineClass("de.cardcontact.scdp.gp.GPError");
defineClass("de.cardcontact.scdp.gp.GPSystem");
defineClass("de.cardcontact.scdp.gp.ByteString");
defineClass("de.cardcontact.scdp.gp.GPByteBuffer");
defineClass("de.cardcontact.scdp.gp.GPAtr");
defineClass("de.cardcontact.scdp.gp.Card");
defineClass("de.cardcontact.scdp.gp.GPKey");
defineClass("de.cardcontact.scdp.gp.GPCrypto");
defineClass("de.cardcontact.scdp.gp.GPXML");
defineClass("de.cardcontact.scdp.gp.GPTLV");
defineClass("de.cardcontact.scdp.gp.GPTLVList");
defineClass("de.cardcontact.scdp.gp.Application");
defineClass("de.cardcontact.scdp.gp.GPApplication");
defineClass("de.cardcontact.scdp.gp.GPSecurityDomain");
defineClass("de.cardcontact.scdp.gp.ApplicationFactory");
defineClass("de.cardcontact.scdp.js.JsX509");
defineClass("de.cardcontact.scdp.js.JsCRL");
defineClass("de.cardcontact.scdp.js.JsASN1");
defineClass("de.cardcontact.scdp.js.JsKeyStore");
defineClass("de.cardcontact.scdp.js.JsCardFile");
defineClass("de.cardcontact.scdp.js.JsIsoSecureChannel");
defineClass("de.cardcontact.scdp.js.JsOCSPQuery");
defineClass("de.cardcontact.scdp.js.JsLDAP");
defineClass("de.cardcontact.scdp.js.JsCMSSignedData");
defineClass("de.cardcontact.scdp.pkcs11.JsPKCS11Provider");
defineClass("de.cardcontact.scdp.pkcs11.JsPKCS11Session");
defineClass("de.cardcontact.scdp.pkcs11.JsPKCS11Object");

defineClass("org.globaltester.testmanager.gp.AssertionError");


// Load persistent settings which defines the _scsh3 object

var filename = GPSystem.mapFilename(".settings.js", GPSystem.AUTO);

if (filename) {
        if (filename.equals(GPSystem.mapFilename(".settings.js", GPSystem.SYS))) {
                print(GPSystem.mapFilename(".settings.js", GPSystem.SYS));
                print("Warning: File .settings.js found in installation directory !");
                print("This happens, when you selected the installation directory as");
                print("user directory and saved settings to the .settings.js file.");
                print("In this case, the .settings.js file in the installation");
                print("directory takes precendence over any other .settings.js.");
                print("You should remove the .settings.js file and create/use your own");
                print("user directory rather than the installation directory.");
        }
        load(filename);
} else {
        _scsh3 = new Object();
}

_scsh3.setProperty = function(property, value) {
        this[property] = value;
        var filename = GPSystem.mapFilename(".settings.js", GPSystem.AUTO);
        if (!filename) {
                filename = GPSystem.mapFilename(".settings.js", GPSystem.USR);
        }
        var cf = new java.io.FileWriter(filename);
        cf.write("//\n");
        cf.write("// Automatically generated file - Do not change\n");
        cf.write("//\n");
        cf.write("_scsh3 = new Object();\n");
        for (i in this) {
                if (!(this[i] instanceof Function)) {
                        cf.write("_scsh3[\"" + i + "\"] = \"" + this[i] + "\";\n");
                }
        }
        cf.close();
}

