var sock;
var login;

function connection(){
     sock = new SockJS('/chat');

     sock.onopen = function() {
         login = document.getElementById('name');
         console.log('open');
         sock.send('connection is open');
     };

     sock.onmessage = function(e) {
         console.log('message', e.data);
         sock.close();
     };

     sock.onclose = function() {
         console.log('close');
     };
}

function sendMessage(){
}