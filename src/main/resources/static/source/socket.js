const socket = new WebSocket('/socket');
var hash = -1;
var gold = 0;
var playersData = "";
var test = "";

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
    console.log(message)
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
        case "ERR":
            listEntryAlert("Error:  " + message.substring(4));
            break;
        case "FNR":
            var winner = message.substring(4);
            if (winner == "") {
                listEntryAlert("Round finished with a tie");
            } else {
                listEntryAlert("Round won by player " + winner);
            }
            break;
        case "MSG":
            listEntry(message.substring(4));
            break;
        case "FNG":
            alert("Game won by " + message.substring(4));
            break;
    }
}

function ref(json) {
    console.log(json);
    goldCount.innerHTML = "Gold: " + json.gold;
    gold = json.gold;
    switchButtonOn();
    playersData = json.players;
    points();
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
    document.getElementById("cardlist").replaceWith(tempCardList);
}

function htmlPowerCard(id, data) {
    var temp = document.createElement("div");
    temp.classList.add("card");
    temp.id = id;
    addCardListener(temp);

    appendDiv(temp, "cardCost", data.cost);
    appendDiv(temp, "cardTitle", " " + data.name);
    appendDiv(temp, "text", data.description);

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

function appendDiv(parent, className, text) {
    var tempName = document.createElement("div");
    tempName.classList.add(className);
    tempName.innerHTML = text;
    parent.appendChild(tempName);
}

function isValueCardSelected() {
    return document.getElementsByClassName("valueCard active").length != 0;
}

function clickButton() {
    if (isValueCardSelected()) {
        play();
        switchButtonOff();
    } else if (hash == -1) {
        str();
    }
}

function switchButtonOn() {
    var button = document.getElementById("playButton")
    button.disabled = false;
    button.style.visibility = "visible"
}

function switchButtonOff() {
    var button = document.getElementById("playButton")
    button.disabled = true;
    button.style.visibility = "hidden"
}