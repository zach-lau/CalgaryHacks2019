# CalgaryHacks2019
This is a project to optimize road clearing in the case of extreme weather events using a genetic algorithm.

The project consists of three parts.

The input GUI allows the user to create a city from nodes and edges. Each edge is directed and has a time taken to shovel and priority. The input GUI will produce a txt that describes the city in the form of shown below.

Plows Nodes Edges

NodeID NodeX NodeY

...

EdgeSrc	Dest Time Priority

...

The genetic algorithm (src/root) takes this in and outputs another txt file with directions for plows in the forom shown below.

Node1 Node2 Node3 ...

Node1 Node2 Node3 ...

The output (/output) GUI will ask the user to input the city map txt and neural net txt and animate this input.
