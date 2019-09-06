document.getElementById("submit").addEventListener("click", function (event) {
    if (event.target.innerText !== "=") {
        event.preventDefault();
        getUserId();
    }
});
document.getElementById("submit2").addEventListener("click", function (event) {
    if (event.target.innerText !== "=") {
        event.preventDefault();
        users();
    }
});
//let url = "https://jsonplaceholder.typicode.com/users/"

function getUserId() {
    let url = "https://jsonplaceholder.typicode.com/users/";
    let userId = document.getElementById("inputtext").value;
    let userurl = url + userId;

    fetch(userurl)
        .then(res => res.json())
        .then(data => {
            document.getElementById("name").innerHTML += data["name"];
            document.getElementById("phone").innerHTML += data["phone"];
            document.getElementById("street").innerHTML += data["address"]["street"];
            document.getElementById("city").innerHTML += data["address"]["city"];
            document.getElementById("zip").innerHTML += data["address"]["zipcode"];
            document.getElementById("geo").innerHTML += data["address"]["geo"]["lat"] + data["address"]["geo"]["lng"];
            console.log("data", data);
        });

}

function users() {
    let url = "https://jsonplaceholder.typicode.com/users";

    fetch(url)
        .then(res => res.json())
        .then(jsondata => {
            let table = document.querySelector("table");
            let data = Object.keys(jsondata[0]);
            tableHead(table, data);
            tableData(table, jsondata);
        });
}

function tableHead(table, data) {
    let head = table.createTHead();
    let row = head.insertRow();
    for (let key of data) {
        let th = document.createElement("th");
        let text = document.createTextNode(key);
        th.appendChild(text);
        row.appendChild(th);
    }

}
function tableData(table, data) {
    for (let element of data) {
        let row = table.insertRow();
        for (key in element) {
            let cell = row.insertCell();
            let text = document.createTextNode(element[key]);
            cell.appendChild(text);
        }
    }
}

