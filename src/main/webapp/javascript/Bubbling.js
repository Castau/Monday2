
/*document.getElementById("fourth").addEventListener('click', function(element) {
    hiAlert(this.id);
}, false);
document.getElementById("fifth").addEventListener('click', function(element) {
    hiAlert(this.id);
}, false);*/

Array.from(document.getElementsByClassName("mydiv"))
    .forEach(function (element) {
        element.addEventListener("click", function () {
            hiAlert(this.id);
        });
    });


let hiAlert = function (id) {
    //alert("hi from " + id);
    document.getElementById("ptext").innerHTML += "Hi from: " + id + "<br>";
}