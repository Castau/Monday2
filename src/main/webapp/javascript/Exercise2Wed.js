
// JavaScript functions and Callbacks
// 1
function add(n1, n2) {
    return n1 + n2;
}

var sub = function (n1, n2) {
    return n1 - n2
}

var cb = function (n1, n2, callback) {
    return "Result from the two numbers: " + n1 + "+" + n2 + "=" + callback(n1, n2);
};

// 2
console.log(add(1, 2));
// 3 (1+2)

console.log(add);
// referencen til add metoden

console.log(add(1, 2, 3));
// 3 (1+2)

console.log(add(1));
// NaN (1+undefined)

console.log(cb(3, 3, add));
//"Result from the two numbers: 3+3=6

console.log(cb(4, 3, sub));
//"Result from the two numbers: 4+3=1

//console.log(cb(3, 3, add()));
// cb forventer en reference til en function, men får resultatet af add()-function

console.log(cb(3, "hh", add));
//"Result from the two numbers: 3+hh=3hh

// 3
var cbNew = function (n1, n2, callback) {
    try {
        if (typeof n1 !== "number") {
            throw new Error('n1 is NaN');
        }
        if (typeof n2 !== "number") {
            throw new Error('n2 is NaN');
        }
        if (typeof callback !== "function") {
            throw new Error('callback is not a function');
        }

    } catch (error) {
        return error.message;
    }

    return "Result from the two numbers: " + n1 + "+" + n2 + "=" + callback(n1, n2);
};

console.log(cbNew(3, "hh", add));

// 4
var mul = function (n1, n2) {
    return n1 * n2
};

// 5 
console.log(cbNew(9, 3, function () { return 9 / 3 }));

// Callbacks (with map, filter and forEach)
// 1 
var names = ["Lars", "Jan", "Peter", "Bo", "Frederik", "Eva"];
const longNames = names.filter(name => name.length <= 3);

names.forEach(n => console.log(n));
longNames.forEach(n => console.log(n));

// 2
const longNamesCASE = longNames.map(name => name.toUpperCase());
console.log(longNamesCASE);

// 3
var namesList = names.map(name => "<li>" + name + "</li>");
var listTag = "<ul>" + namesList.join('') + "</ul>";

// 4
var cars = [
    { id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000 },
    { id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900 },
    { id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000 },
    { id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799 },
    { id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799 }
];

const carsNew = cars.filter(car => car.year > 1999);
const carsVolvo = cars.filter(car => car.make === "Volvo");
const carsCheap = cars.filter(car => car.price < 5000);

console.log("new");
carsNew.forEach(n => console.log(n));
console.log("volvo");
carsVolvo.forEach(n => console.log(n));
console.log("cheap");
carsCheap.forEach(n => console.log(n));

// 4a
var sqlCarsArray = cars.map(car => "INSERT INTO cars (id,year,make,model,price) VALUES ( " + car.id + ", " + car.year + " '" + car.make + "','" + car.model + "', " + car.price + " );");
var sqlCarsString = sqlCarsArray.join('');
console.log(sqlCarsString);

// Asynchronous Callbacks
// 1
// exp a,d,f,e,b

// 2
var msgPrinter = function (msg, delay) {
    setTimeout(function () {
        console.log(msg);
    }, delay);
};
console.log("aaaaaaaaaa");
msgPrinter("bbbbbbbbbb", 2000);
console.log("dddddddddd");
msgPrinter("eeeeeeeeee", 1000);
console.log("ffffffffff");


// this and constructor functions 
// 1
function Person(name) { // Konstruktør
    this.name = name;
    console.log("Name: " + this.name);
    setTimeout(function () {
        console.log("Hi  " + this.name);  //er undefined da den er asynkron og ikke har et tilhørsforhold til objektet
    }, 2000);
}

Person("Kurt Wonnegut"); // instans af Person
console.log("I'm global: " + name); //Navnet der blier givet med ind, tilhører objektet

// Person("Kurt Wonnegut") <= det er en konstruktør

// 2
var p = new Person("Kurt Wonnegut");
console.log("I'm global: "+ name);

// 3
function Person(name){
    this.name = name;
    var self = this;
    console.log("Name: "+ this.name);
    setTimeout(function(){
      console.log("Hi  "+self.name);  
    },2000);
  }
  
  function Person(name){
    this.name = name;  
    console.log("Name: "+ this.name);
    setTimeout(function(){
      console.log("Hi  "+this.name);  
    }.bind(this),2000);
  }
  var p = new Person("Kurt Wonnegut");
  console.log("I'm global: "+ name);

  // 4

  var greeter = function(){
    console.log(this.message);
  };
  var comp1 = { message: "Hello World" };
  var comp2 = { message: "Hi" };
  
  var g1 = greeter.bind(comp1 );//We can store a reference, with a specific “this” to use
  var g2 = greeter.bind(comp2 );//And here another “this”
  setTimeout(g1,500);
  setTimeout(g2,1000);

  function stupidObject(name, year, color, hat) { 
    this.name = name;
    this.year = year;
    this.color = color;
    this.hat = hat;
  }
var sobj = new stupidObject("idiot",2019,"green","noHat");

  for(prop in sobj){
      console.log(prop, sobj[prop]);
  };

  delete sobj.year;
  for(prop in sobj){
    console.log(prop, sobj[prop]);
};

sobj.contry = "Denmark";
for(prop in sobj){
    console.log(prop, sobj[prop]);
};

// 2
function Person2(firstName, lastName , age) { 
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
  }

var details = function(person2obj){
    return person2obj.firstName + " " + person2obj.lastName + " " + person2obj.age;
}

var person2 = new Person2("idiot","hat",3);
console.log(details(person2));
