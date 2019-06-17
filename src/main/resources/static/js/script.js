var sock = new SockJS('/chat');

sock.onopen = function() {
    console.log('open');
};

sock.onmessage = function(e) {
    var words = e.data.split(":");

    var messages = document.getElementById("chat");

    var messageElement = document.createElement('li');
    messageElement.className += " collection-item avatar";

    var spanElement = document.createElement("span");
    spanElement.className += " title";
    spanElement.appendChild(document.createTextNode(words[0] + " отправил:"));

    var textnode = document.createElement("p");
    textnode.appendChild(document.createTextNode(words[1]));

    var img = document.createElement("img");
    img.className += "circle";
    img.src = "img/avatar_test.jpg";

    messageElement.appendChild(img);
    messageElement.appendChild(spanElement);
    messageElement.appendChild(textnode);

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
