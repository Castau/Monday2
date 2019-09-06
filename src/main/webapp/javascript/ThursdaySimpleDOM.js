document.addEventListener('DOMContentLoaded', function() {
    divColor();
    }, false);

document.getElementById("changecolor").addEventListener('click', function() {
    changeDivColor();
}, false);


let divColor = function () {
    let divElements = document.getElementsByTagName("div");
    Array.from(divElements).forEach(function(divEle){
        divEle.style.backgroundColor="green";
    });
}

let changeDivColor = function() {
    document.getElementById("first").style.backgroundColor="purple";
    document.getElementById("second").style.backgroundColor="brown";
    document.getElementById("third").style.backgroundColor="yellow";
}


