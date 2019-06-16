var sock = new SockJS('/chat');

sock.onopen = function() {
    console.log('open');
};

sock.onmessage = function(e) {
    console.log('message', e.data);
    var messages = document.getElementById("chat");
    messages.innerHTML = e.data;
};

sock.onclose = function() {
    console.log('close');
};

function onClose(){
    sock.close();
}

function sendMessage(){
    var message = document.getElementById("message").value;
    var username = document.getElementById("username").innerText;
    sock.send(username + ":" + message);
}
