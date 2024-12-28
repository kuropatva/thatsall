const socket = new WebSocket('/socket');
var hash = -1;

const goldCount = document.getElementById("goldCount");
const playfield = document.getElementById("playfield");

socket.onopen = function(event) {
  console.log("Websocket connected");
};

socket.onerror = function(event) {
    alert("Websocket/server error.");
};

socket.onmessage = function(event) {
  handleMessage(event.data);
};

socket.onclose = function(event) {
    alert("Server connection lost. Refresh page to attempt to reconnect.");
};

function sendMessage(message) {
  socket.send(message);
}

function upt() {
    socket.send("UPT");
}

function str() {
    socket.send("STR");
}

function handleMessage(message) {
    var split = message.substring(0, 3);
    switch (split) {
        case "REF":
            var space = message.indexOf(" ", 4);
            var messageHash = message.substring(4, space - 1);
            if (hash == messageHash) return;
            hash = messageHash;
            ref(JSON.parse(message.substring(space + 1)));
            break;
    }
}

function ref(json) {
    console.log(json);
    goldCount.innerHTML = json.gold;
    playfield.innerHTML = JSON.stringify(json.players);
    var tempCardList = document.createElement("div");
    for (var i in json.value) {
        var cardValue = json.value[i];
        tempCardList.appendChild(htmlValueCard(cardValue));
    }

    for (var i in json.power) {
        var cardID = json.power[i];
        var cardInfo = cardData[cardID];
        console.log(cardID);
        console.log(cardInfo);
        tempCardList.appendChild(htmlPowerCard(cardID, cardInfo));
    }
    tempCardList.id = "cardlist";
    document.getElementById("cardlist").replaceWith(tempCardlist);
}

function testUPT() {
    setInterval(() => {hash = -1; upt();},1000);
}

function htmlPowerCard(id, data) {
    var temp = document.createElement("div");
    temp.classList.add("card");
    temp.id = id;
    addCardListener(temp);

    appendDiv(temp, "cardCost", data.cost);
    appendDiv(temp, "cardTitle", " " + data.name);
    appendDiv(temp, "", data.description);

    return temp;
}

function htmlValueCard(cardValue) {
    var temp = document.createElement("div");
    temp.classList.add("card");
    temp.classList.add("valueCard")
    temp.id = cardValue;

    appendDiv(temp, "cardTitle", cardValue);

    addCardListener(temp);
    return temp;
}

function appendDiv(parent, class, text) {
    var tempName = document.createElement("div");
    tempName.classList.add(class);
    tempName.innerHTML = text;
    temp.appendChild(tempName);
}