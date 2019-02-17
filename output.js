var length = 500;
var speed = 1;
var pos = 0;
var id;
var initialized = false;

function setup(){

}

function loop(){
    
}

function play(){
    id = setinterval(loop, 5);
}

function pause(){
    clearInterval(id);
}