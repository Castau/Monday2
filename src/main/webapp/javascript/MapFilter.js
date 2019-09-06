document.getElementById("submit").addEventListener("click", function (event) {
    filterPrice(event);
});

let cars = [
    { id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000 },
    { id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900 },
    { id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000 },
    { id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799 },
    { id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799 }
];


function filterPrice(event){
    event.preventDefault();
    let filterprice = document.getElementById("inputtext").value;
    let carsCheap = cars.filter(car => car.price < filterprice);
    table.innerHTML = "";
    tableData(table, carsCheap);
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

let table = document.querySelector("table");
let data = Object.keys(cars[0]);
tableHead(table, data);
tableData(table, cars);
