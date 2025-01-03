var selected;

var a = document.getElementsByClassName("card");
for (const i of a) {
	addCardListener(i);
}

function addCardListener(card) {
    card.addEventListener('click', () => {
        sel(card);
    });
}

function sel(executor) {
	if (executor.classList.contains("active")) {
		executor.classList.remove("active");
	}
	else {
	    if (executor.classList.contains("valueCard")) {
	        if (document.getElementsByClassName("valueCard active").length == 0) {
		        executor.classList.add("active");
		    }
	    } else {
		    if (calculateCurrentCost() + cardData[executor.id].cost <= gold) {
		        executor.classList.add("active");
		    }
	    }
	}
}

function calculateCurrentCost() {
    var list = document.querySelectorAll(".active:not(.valueCard)");
    var cost = 0;
    for (var i = 0; i < list.length; i++) {
        cost += cardData[list[i].id].cost;
    }
    return cost;
}

function play() {
    var list = document.getElementsByClassName("active");
    var value = null;
    for (var i = 0; i < list.length; i++) {
        var card = list[i];
        if (card.classList.contains("valueCard")) {
            value = card;
        } else {
            sendMessage("PLP " + card.id);
        }
    }
    sendMessage("PLV " + value.id)
}

function listEntry(text) {
    var list = document.getElementById("listbox");
    var temp = document.createElement("div");
    temp.classList.add("list-item");
    temp.innerHTML = text;
    list.insertBefore(temp, list.firstChild);
}

function listEntryAlert(text) {
    listEntry("<div style=\"color: pink\">" + text + "</div>");
}

function points() {
    if (playersData == "") {
        text = "No information to show."
    } else {
        var text = "Points: "
        for (var i = 0; i < playersData.length; i++) {
            text += "<b>" + playersData[i].name + "</b>: " + playersData[i].points + " ";
        }
    }
    document.getElementById("points").innerHTML = text;
}