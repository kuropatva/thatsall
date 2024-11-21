const socket = new WebSocket('/socket');

socket.onopen = function(event) {
  alert("Connected");
};

socket.onerror = function(event) {
    alert("Error");
};

socket.onmessage = function(event) {
  alert(event.data);
};

socket.onclose = function(event) {
    alert("Server connection lost. Refresh page to attempt to reconnect.");
};

function sendMessage(message) {
  socket.send(message);
}

