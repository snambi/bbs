function getHeader( jsonStr ){

    var json = JSON.parse(jsonStr);

    return json.menu.header;
}
