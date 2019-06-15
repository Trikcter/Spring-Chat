function connection(){
     var sock = new SockJS('/chat');
     sock.onopen = function() {
         console.log('open');
         sock.send('test');
     };

     sock.onmessage = function(e) {
         console.log('message', e.data);
         sock.close();
     };

     sock.onclose = function() {
         console.log('close');
     };
}