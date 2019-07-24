var sock = new SockJS('/chat');
var currentRoom;

sock.onopen = function() {
    var room = document.getElementById("activeRoom")
    currentRoom = room.value;

    console.log('open');
};

sock.onmessage = function(e) {
    var answer = e.data;

    var words = JSON.parse(answer);

    var type = words.typeOfMessage;

    switch(type){
        case "text":
            drawMessage(words.id,words.from,words.message);
            break;
        case "Error":
            alert("Ошибка");
            break;
        case "Rename":
            rename(words.message);
            alert("Имя было изменено!");
            break;
        case "Ok":
            alert("Все окей");
            break;
        case "Connect":
            alert("Вы подключились к комнате");
            currentRoom = words.to;

            var room = document.getElementById("activeRoom")
            room.value = currentRoom;

            for(var i = 0;i<words.historyOfMessage.length;i++){
                drawMessage(words.historyOfMessage[i].id,words.historyOfMessage[i].author.username,words.historyOfMessage[i].message);
            }

            break;
    }
};

sock.onclose = function() {
    console.log('close');
};

function onClose(){
    sock.close();
    alert("Вы вышли из чата!");
}

function sendMessage(){
    var element = document.getElementById("message");
    var username = document.getElementById("username").innerText;

    var payload = element.value;

    var msg = {
        "message":payload,
        "to": currentRoom
    }

    var message = JSON.stringify(msg);

    if(element.value != "")
        sock.send(message);

    element.value = "";
}

function drawMessage(id,username,payload){
    var messages = document.getElementById("chat");

    var messageElement = document.createElement('li');
    messageElement.className += " collection-item avatar";
    messageElement.id = id;

    var spanElement = document.createElement("span");
    spanElement.className += " title";
    spanElement.appendChild(document.createTextNode(username + " отправил:"));

    var textnode = document.createElement("p");
    textnode.appendChild(document.createTextNode(payload));

    var img = document.createElement("img");
    img.className += "circle";
    img.src = "img/avatar_test.jpg";

    var a = document.createElement("a");
    a.className += "secondary-content";
    a.onclick = function(){
        var xmlHttp = new XMLHttpRequest();
        var url = "/panel/messages/" + id;

        xmlHttp.open( "DELETE", url, true );
        xmlHttp.send( null );

        document.getElementById(id).remove();
    }

    var i = document.createElement("i");
    i.className = "small material-icons icon-red";
    i.innerText = "delete";

    a.appendChild(i);

    messageElement.appendChild(img);
    messageElement.appendChild(spanElement);
    messageElement.appendChild(textnode);
    messageElement.appendChild(a);

    messages.appendChild(messageElement);
}

