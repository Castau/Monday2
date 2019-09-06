
document.getElementById("submit").addEventListener("click", function (event) {
    addName(event);
});
document.getElementById("first").addEventListener("click", function (event) {
    removefirst(event);
});
document.getElementById("last").addEventListener("click", function (event) {
    removelast(event);
});

    let listText = document.getElementById("listtext");
    let names = ["Lars", "Jan", "Peter", "Bo", "Frederik", "Eva"];


    function createList() {
        let namesList = names.map(name => "<li>" + name + "</li>");
        let listTag = "<ul>" + namesList.join('') + "</ul>";
        listText.innerHTML = listTag;
    }
    createList();

    function addName(event) {
        event.preventDefault();
        let addname = document.getElementById("inputtext").value;
        names.push(addname);
        createList();
    }

    function removefirst(event) {
        event.preventDefault();
        names.shift();
        createList();
    }

    function removelast(event) {
        event.preventDefault();
        names.pop();
        createList();
    }