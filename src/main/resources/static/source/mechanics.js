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
		executor.classList.add("active");
	}
}

function addCard() {

}

