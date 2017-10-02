// returns the parsed value
var getHeader = function( jsonStr ){
    //console.log("received")
    var json = JSON.parse(jsonStr);
    //console.log("JSON: "+JSON.stringify(json) );
    var result = json.menu.header;
    return result;
}

// export the function
exports.getHeader = getHeader;