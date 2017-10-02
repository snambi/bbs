

// load the JSON
var fs = require("fs");
var jsonStr = fs.readFileSync("../resources/test.json");
//console.log("JSON : "+ jsonStr);

// load the function
var parseFn = require("../resources/ParseFnNode.js");
// parse and get the results
var out = parseFn.getHeader(jsonStr);

// print
console.log("Parsed header: "+ out);
