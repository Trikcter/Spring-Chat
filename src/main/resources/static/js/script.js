var sock = new SockJS('/chat');

sock.onopen = function() {
    console.log('open');
};

sock.onmessage = function(e) {
    var words = e.data.split(":");
    var id = words[0];

    var messages = document.getElementById("chat");

    var messageElement = document.createElement('li');
    messageElement.className += " collection-item avatar";
    messageElement.id = id;

    var spanElement = document.createElement("span");
    spanElement.className += " title";
    spanElement.appendChild(document.createTextNode(words[1] + " отправил:"));

    var textnode = document.createElement("p");
    textnode.appendChild(document.createTextNode(words[2]));

    var img = document.createElement("img");
    img.className += "circle";
    img.src = "img/avatar_test.jpg";

    var a = document.createElement("a");
    a.className += "secondary-content";
    a.onclick = function(){
        var xmlHttp = new XMLHttpRequest();
        var url = "/panel/delmes/" + id;

        xmlHttp.open( "GET", url, true );
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
};

sock.onclose = function() {
    console.log('close');
};

function onClose(){
    sock.close();
    alert("Вы вышли из чата!");
}

function sendMessage(){
    var message = document.getElementById("message");
    var username = document.getElementById("username").innerText;

    if(message.value != "")
        sock.send(username + ":" + message.value);

    message.value = "";
}
