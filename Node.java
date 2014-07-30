import java.util.*;
public class Node {
    boolean enqueued1, enqueued2;
    Node via1, via2;
    int dist1, dist2;
    boolean done1, done2;
    LinkedList<Neighbor> neighbors;
    int time1, time2;
    
    
    Node() {
        via1 = null;
        via2 = null;
        dist1 = Integer.MAX_VALUE; // set max
        dist2 = Integer.MAX_VALUE;// set max
        done1 = false;
        done2 = false;
        time1 = 0;
        time2 = 0;
        enqueued1 = false;
        enqueued2 = false;
        neighbors = new LinkedList<Neighbor>();
    }
    //takes neighbor node, returns distance to neighbor
    public int dist(Node neighbor) {
        for(Neighbor n : neighbors) {
            if(n.nNode == neighbor) return n.dist;
        }
        throw new NullPointerException("these nodes are not neighbors");
    }
    
	//typical getters and setters
	
    public void setVia(Node n, boolean second) {
        if(second) via2 = n;
        else via1 = n;
    }
    
    public Node getVia(boolean second) {
        if(second) return via2;
        else return via1;
    }
    
    public void setDist(int dist, boolean second) {
        if(second) dist2 = dist;
        else dist1 = dist;
    }
    
    public int getDist(boolean second) {
        if(second) return dist2;
        else return dist1;
    }
    
    public void setDone(boolean done, boolean second) {
        if(second) done2 = done;
        else done1 = done;
    }
    
    public boolean getDone(boolean second) {
        if(second) return done2;
        else return done1;
    }
    
    public void setTime(int time, boolean second) {
        if(second) time2 = time;
        else time1 = time;
    }
    
    public int getTime(boolean second) {
        if(second) return time2;
        else return time1;
    }
    
    public void setEnqueued(boolean enqueued, boolean second) {
        if(second) enqueued2 = enqueued;
        else enqueued1 = enqueued;
    }
    
    public boolean getEnqueued(boolean second) {
        if(second) return enqueued2;
        else return enqueued1;
    }
    
	//checks that node is a neighbor of this one
	//as dist will give exception if not neighbor
    public boolean inNeighbors(Node v) {
        for (Neighbor n : neighbors) {
            if(n.nNode == v) return true;
        }
        return false;
    }
    
        
            
     
}