var xmlHttp = new XMLHttpRequest();

function banUser(id){
    var url = "/panel/block/" + id;

    xmlHttp.open( "GET", url, true );
    xmlHttp.send( null );

    var doc = document.getElementById(id);
    var li = doc.childNodes[1];
    var p = li.getElementsByClassName("active_color");
    var i = p[0];

    var button = li.getElementsByClassName("waves-effect waves-red btn-small red")[0];
    button.className = "waves-effect waves-red btn-small green";
    button.childNodes[1].data = "Разблокировать";
    button.childNodes[0].innerText = "sentiment_very_satisfied";

    button.onclick = function(){ unBanUser(id); };

    i.className = "material-icons icon-red active_color";
}

function unBanUser(id){
     var url = "/panel/unblock/" + id;

     xmlHttp.open( "GET", url, true );
     xmlHttp.send( null );

     var doc = document.getElementById(id);
     var li = doc.childNodes[1];
     var p = li.getElementsByClassName("active_color");
     var i = p[0];

     var button = li.getElementsByClassName("waves-effect waves-red btn-small green")[0];
     button.className = "waves-effect waves-red btn-small red";
     button.childNodes[1].data = "Заблокировать";
     button.childNodes[0].innerText = "sentiment_very_dissatisfied";

     button.onclick = function(){ banUser(id); };

     i.className = "material-icons icon-green active_color";
}

function deleteUser(id){
    var url = "/panel/" + id;

    xmlHttp.open( "DELETE", url, true );
    xmlHttp.send( null );

    document.getElementById(id).remove();
}

function makeSuperuser(id){
    var url = "/panel/roles/" + id;

    xmlHttp.open( "GET", url, true );
    xmlHttp.send( null );

    var doc = document.getElementById(id);
    var li = doc.childNodes[1];

    var button = li.getElementsByClassName("waves-effect waves-green btn-small green")[0];
    button.className = "waves-effect waves-green btn-small red";
    button.childNodes[1].data = "Убрать из модераторов";

    button.onclick = function(){ makeSimpleUser(id); };
}

function makeSimpleUser(id){
    var url = "/panel/roles/" + id;

    xmlHttp.open( "DELETE", url, true );
    xmlHttp.send( null );

    var doc = document.getElementById(id);
    var li = doc.childNodes[1];

    var button = li.getElementsByClassName("waves-effect waves-green btn-small red")[0];
    button.className = "waves-effect waves-green btn-small green";
    button.childNodes[1].data = "Сделать модератором";

    button.onclick = function(){ makeSuperuser(id); };
}