var xmlHttp = new XMLHttpRequest();

function banUser(id){
    var url = "/panel/block/" + id;

    xmlHttp.open( "GET", url, true );
    xmlHttp.send( null );
}