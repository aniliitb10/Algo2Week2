# Algo2Week2

This is my work for exercise of week 2 (http://coursera.cs.princeton.edu/algs4/assignments/seam.html).

To actually treat the picture as a graph, I have manually constrcuted the neighbours of each point based on if it
is vertical seam or horizontal seam.

One tricky part is to make a first point and a last point (I actually never 'explicitly' used the latr point). And then,
let's say it was for vertical seam, the adjacents of first point will be all of top row points (and likewise, last point 
will in adjacents of all bottom row points. And to find the vertical seam, find the lowest energy path from first point
to the last point.
