var width = 800;
var height = 500;
var speed = 1;
var pos = 0;
var id;
var initialized = false;
var nodeDiameter = 15;

var backgroundColor = "black";
var nodeColor = "white";
var pathColor = "white";
var margin = 50;
var minx;
var maxx;
var miny; 
var maxy;

var background;
var files;

var numNodes;
var numRoads;
var numPlows;
var separator = ' ';
var offset;

var nodes = [];
var roads = [];

var img;

function setup(){

    //Reading in the files 
    document.getElementById('file').onchange = function(){

        var file = this.files[0];
      
        var reader = new FileReader();
        reader.onload = function(progressEvent){
      
         //Reads in the lines
          var lines = this.result.split('\n');
          for(var line = 0; line < lines.length; line++){
            lines[line] = lines[line].split(separator);
          }

          //The information about nodes roads etc.
          numPlows = parseInt(lines[0][0]);
          numNodes = parseInt(lines[0][1]);
          numRoads = parseInt(lines[0][2]);

          //Read in the nodes

          minx = parseInt(lines[1][1]);
          maxx = minx;
          miny = parseInt(lines[1][2]);
          maxy = miny;
          
          for(var node = 0; node < numNodes; node++){
            var x = parseInt(lines[node+1][1]);
            var y = parseInt(lines[node+1][2]);
            nodes.push(new Node(lines[node+1][0], x, y));
            if(x < minx) minx = x;
            if(x > maxx) maxx = x;
            if(y < miny) miny = y;
            if(y > maxy) maxy = y;
          }

          nodes.forEach(function(node){node.updateImg()});
          //This creates all of the roads

          var offset = numNodes+1;

          for(var road = 0; road < numRoads; road++){

            var source = parseInt(lines[road+offset][0]);
            var dest = parseInt(lines[road+offset][1]);
            var time = parseInt(lines[road+offset][2]);
            var priority = parseInt(lines[road+offset][3]);

            roads.push(new Road(nodes[source], nodes[dest], time, priority));
          }



          alert(nodes[0].x);

        };
        reader.readAsText(file);
      };

    //Create the background element

    background = document.createElement("div");
    background.style.width = width + 'px';
    background.style.height = height + 'px';
    background.style.background = backgroundColor;
    container.appendChild(background);

    
    /*var node = document.createElement("IMG");
    node.style.position = "relative";
    node.src = "node.png";
    node.style.top = '400px';
    node.style.left = '400px';
    node.style.width = 10*nodeDiameter + 'px';
    node.style.height = 10*nodeDiameter*width/length + 'px';
    background.appendChild(node);
    */

}

function loop(){
    
}

function play(){
    id = setInterval(loop, 5);
}

function pause(){
    clearInterval(id);
}

//Objects

function Node(id,x,y){
    this.id = id;
    this.x = x;
    this.y = y;
    this.img = document.createElement("IMG");
    this.img.style.position = "absolute";
    this.img.src = "node.png";

    this.updateImg = function(){
        var imgx =  (x-minx)/(maxx-minx)*(width-2*margin)+margin;
        var imgy = (y-miny)/(maxy-miny)*(height-2*margin)+margin;
        this.img.style.top = imgy + 'px';
        this.img.style.left = imgx + 'px';
        this.img.style.width = nodeDiameter + 'px';
        this.img.style.height = nodeDiameter*width/length + 'px';
        //alert(imgx + ' ' + imgy);
        background.appendChild(this.img);    
    };
}

function Road(source, dest, time, priority){
    this.source = source;
    this.dest = dest;
    this.time = time;
    this.priority = priority;
}

