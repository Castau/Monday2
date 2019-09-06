document.getElementById("buttons").addEventListener("click", function (event) {
    if(event.target.innerText !== "="){
        event.preventDefault();
        display(event.target.innerText);
    }
});

document.getElementById("calculate").addEventListener("click", function () {
    calculate();
});

function display(input){
    document.getElementById("display").innerHTML += input;
}

function calculate(){
    let display = document.getElementById("display").innerText;
    document.getElementById("display").innerHTML = eval(display);
}
