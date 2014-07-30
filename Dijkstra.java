import java.io.*;
import java.lang.management.*;
public class Dijkstra {
    static Node[] adjList; //read off size from file, line p, after "sp ", until " "
    static int count = 0;
    static int lowestDist = Integer.MAX_VALUE; 
    static int enqueued;
    public static void main(String[] args){
    
    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
    
    try {
            /*  Sets up a file reader to read the file passed on the command
                line one character at a time */
            FileReader input = new FileReader("NY.gr.txt"); //probably will add in string directly
            /* Filter FileReader through a Buffered read to read a line at a
               time */
            BufferedReader bufRead = new BufferedReader(input);
            String line;    // String that holds current file line
            // Read first line
            line = bufRead.readLine();
            // Read through file one line at time. Print line # and line
            int linecount = 1;
            while (line != null){
            //System.out.println("newline");
            //do something
                String[] parts = line.split(" ");
                //System.out.println(parts[0].equals("a"));
                if(parts[0].equals("p")) {
                    adjList = new Node[ Integer.parseInt(parts[2]) + 1];
                    for(int i = 1; i < adjList.length; i++) {
                        adjList[i] = new Node();
                    }

                }
                else if(parts[0].equals("a")) {
                    Neighbor temp = new Neighbor(adjList[ Integer.parseInt(parts[2]) ], Integer.parseInt(parts[3]) );

                    adjList[ Integer.parseInt( parts[1] ) ].neighbors.add( temp ); 
                }
                line = bufRead.readLine();

            }
            bufRead.close(); 
            for(int i = 1; i < adjList.length; i++) { 

            }
            
        
        }
        catch (ArrayIndexOutOfBoundsException e){
            /* If no file was passed on the command line, this expception is
            generated. A message indicating how to the class should be
            called is displayed */
            System.out.println("Usage: java ReadFile filename\n");          
        }
        catch (IOException e){
        System.out.print("exception2");
            // If another exception is generated, print a stack trace
            e.printStackTrace();
        }
     
        for(int i = 0; i < 200; i++) { 
            int int1 = ((int) Math.ceil(Math.random()*264346));
            int int2 = ((int) Math.ceil(Math.random()*264346));
            System.out.print(int1 + "\t");
            System.out.print(int2 + "\t"); 
            //int check = check(int1, int2);
            long t = bean.getCurrentThreadUserTime();
            int d1 = dijkstra(adjList[int1], adjList[int2]);
            double time = (bean.getCurrentThreadUserTime()-t) / 1e9;
            //System.out.printf ("Dijkstra took %f seconds.\n", time);
            //System.out.println("dijkstra is correct: " + (check == d1));
            int e1 = enqueued;
            enqueued = 0;
            count++;
            lowestDist = Integer.MAX_VALUE;
            t = bean.getCurrentThreadUserTime();
            int d2 = singleD(adjList[int1], adjList[int2]);
            double time2 = (bean.getCurrentThreadUserTime()-t) / 1e9;
            //System.out.printf ("Single dijkstra took %f seconds.\n", time2);
            //System.out.println("single dijkstra is correct: " + (check == d2));
            System.out.print(e1 + "\t");
            System.out.print(enqueued + "\t");
            System.out.print(d1 + "\t");
            System.out.print(d2 + "\t");
            System.out.print((e1 - enqueued) + "\t");
            System.out.print(time + "\t");
            System.out.print(time2 + "\t");
            System.out.println((time - time2) + "\t");
            enqueued = 0;
            count++;
       }
    }
    public static int check(int x, int y) { 
        try {
            /*  Sets up a file reader to read the file passed on the command
                line one character at a time */
            FileReader input = new FileReader("us.out.txt"); //probably will add in string directly
            /* Filter FileReader through a Buffered read to read a line at a
               time */
            BufferedReader bufRead = new BufferedReader(input);
            String line = null;    // String that holds current file line
            // Read first line
            for(int i = 1; i < x; ++i) {
                bufRead.readLine();
            }
            line = bufRead.readLine();
            // Read through file one line at time. Print line # and line
            String[] parts = line.split(" ");
            return Integer.parseInt(parts[y - 1]);      
        }
        catch (IOException e){
        System.out.print("exception2");
            // If another exception is generated, print a stack trace
            e.printStackTrace();
        }
        return -1;
    }
    
    
   //unidirectional dijkstra 
   //takes start and target node, returns smallest distance
    public static int singleD(Node v, Node w) {
    Heap Q = new Heap(adjList.length + 1);
        v.setVia(null, false); 
        Q.enqueue(v, false);
        v.setEnqueued(true, false);    
        v.setTime(count, false);
        v.setDist(0, false);
        boolean end = false;
        Node q1;
        for(Neighbor n : v.neighbors) n.nNode.setDone(false, false);
        while(!Q.isEmpty()) { 
            v = Q.dequeue(false);
            if(v == w) {
                return v.dist1; 
            }
            v.setDone(true, false);
            dijkstraHelp(v, Q, false, false);
        }
        return lowestDist;
    }
	
	//bidirectional implementation of dijkstra
	//takes start and target node, returns smallest distance
    public static int dijkstra(Node v, Node w) {
        if(v == w) return 0;
        Heap Q1 = new Heap(adjList.length + 1);
        Heap Q2 = new Heap(adjList.length + 1);
        for(Neighbor n : v.neighbors) {
            Node y = n.nNode;
            y.setDone(false, false);
            y.setEnqueued(false, false);
        }
        for(Neighbor n : w.neighbors) {
            Node y = n.nNode;
            y.setDone(false, true);
            y.setEnqueued(false, true);
        }
		//clunky but works
        v.setVia(null, false); 
        v.setVia(null, true);
        w.setVia(null, true);
        w.setVia(null, false);
        v.setEnqueued(true, false);
        v.setEnqueued(false, true);
        w.setEnqueued(true, true);
        w.setEnqueued(false, false);
        v.setDist(0, false);
        v.setDist(Integer.MAX_VALUE, true);
        w.setDist(0, true);
        w.setDist(Integer.MAX_VALUE, false);
        v.setTime(count, false);
        w.setTime(count, true);
        Q1.enqueue(v, false);
        Q2.enqueue(w, true);
        v.setDone(false, true);
        w.setDone(false, false);
        boolean end = false;
        Node q1;
        Node q2;
        while(!Q1.isEmpty() || !Q2.isEmpty()) { 
            if(!Q1.isEmpty()) q1 = Q1.peek();
            else { 
                q1 = new Node();
                q1.setDist(Integer.MAX_VALUE, false);
            }
            if(!Q2.isEmpty()) q2 = Q2.peek();
            else { 
                q2 = new Node();
                q2.setDist(Integer.MAX_VALUE, true);
            }
            if(q1.getDist(false) < q2.getDist(true)) { 
                v = Q1.dequeue(false);
                v.setDone(true, false);
                if(v.getDone(true)) {
                    return lowestDist;
                }
                dijkstraHelp(v, Q1, false, true);
            }
            else {
                v = Q2.dequeue(true);
                v.setDone(true, true);
                if(v.getDone(false)){
                    return lowestDist;
                }
                dijkstraHelp(v, Q2, true, true);
            }
        }
        return lowestDist;
    }
	
	//takes current node, a heap, bool to identify which heap (Q1 or Q2), and bool for bidir vs single dir
	//updates, adds neighbors of current node to relevant heap or readjusts values if neighbors found through new path
    public static void dijkstraHelp(Node v, Heap pq, boolean t, boolean bidir) { 
        boolean two = t; 
        for(Neighbor nbr : v.neighbors) {
        //for all of the list of neighbors for v
                Node w = nbr.nNode; //get the node that's represented by the neighbor                
                //reset this node if it's using old values
                if(w.getTime(two) != count) { 
                    w.setVia(null, two);
                    w.setDone(false, two);
                    w.setEnqueued(false, two);
                    w.setDist(Integer.MAX_VALUE, two);
                    w.setTime(count, two);
                    if(bidir && w.getTime(!two) != count) {
                        
                        w.setVia(null, !two);
                        w.setDone(false, !two);
                        w.setEnqueued(false, !two);
                        w.setDist(Integer.MAX_VALUE, !two);
                        w.setTime(count, !two);
                    }
                        
                }
                
                //if it's already done, ignore it
                if(w.getDone(two)) continue;
                
                //if it isn't enqueued yet
                if(!w.getEnqueued(two)) {
                    w.setDist( (v.getDist(two) + nbr.dist), two); //set distance of neighbor to distance of first node + distance between them
                    w.setVia(v, two); //note that it's via this first node
                    
                    //if the v node has distances recorded to both start nodes, check if this is lowest possible result distance
                    if(bidir) {
                        if(w.getDist(!two) < Integer.MAX_VALUE) {
                            lowestDist = Math.min( (w.getDist(two) + w.getDist(!two)), lowestDist); //bidir
                            }
                    }
                    //lowest distance recorded is minimum of current distance and lowest recorded distance?
                    pq.enqueue(w, two); //enqueue
                    enqueued++;
                    w.setEnqueued(true, two);
                }
                else { 
                    //w is already enqueued
                    // if the distance from start to current selected node + distance between current & this neighbor is less than distance
                    // straight to this neighbor, adjust distance of neighbor
                    if( (v.getDist(two) + nbr.dist) < w.getDist(two) ) {
                        w.setDist( (v.getDist(two) + nbr.dist), two); //set distance to that shorter distance
                        w.setVia(v, two); //set via to match
                            pq.resift(w, two);
                        }
                        if(bidir) {
                            //check if current node has distances from both sides, if so update lowest possible accordingly
                            if(w.getDist(!two) < Integer.MAX_VALUE) lowestDist = Math.min( (w.getDist(two) + w.getDist(!two)), lowestDist); //bidir
                        }
                    }
                }
        }
           
        

}
            