function addItem(trainingHashCode) {
    var accordion = document.getElementById("accordion");
    var card = document.createElement("div");
    card.setAttribute('class', "card");

    var card_header = document.createElement("div");
    card_header.setAttribute('class', "card-header");

    var a = document.createElement("a");
    a.setAttribute('class', "collapserd card-link");
    a.setAttribute('data-toggle', "collapse");
    a.setAttribute('th:href', "'#collapse'+${training.hashCode()}");
    a.setAttribute('th:text', "${training.getTrainingName()}");

    var body = document.createElement("div");
    body.setAttribute('th:id', "'collapse'+${training.hashCode()}");
    body.setAttribute('class', "collapse");
    body.setAttribute('data-parent', "#accordion");

    var card_body = document.createElement("div");
    card_body.setAttribute('class', "card-body");
    card_body.setAttribute('th:id', "'child'+${training.hashCode()}");

    accordion.appendChild(card);
    card.appendChild(card_header);
    card_header.appendChild(a);
    card.appendChild(body);
    body.appendChild(card_body);

}
