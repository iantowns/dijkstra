import java.util.*;
public class Heap {
    public Node[] a; //nodes
    private int count;
    public HashMap<Node, Integer> locs = new HashMap<Node, Integer>();
    
    public Heap(int n) { //int is length
        a = new Node[n]; //array of nodes
        count = 0;
    }
    public void enqueue(Node x, boolean two) { //nodes
        a[++count] = x;
        int location = siftUp(count, two);
        locs.put(x, location );
    }
    private int siftUp(int k, boolean two) { //takes location rather than node
        if(k <= 1) return k;
        if(a[k].getDist(two) < a[k/2].getDist(two)) {
            swap(k, k/2); 
            siftUp(k/2, two);
            return k;
        }
        return k;
    }
    public Node dequeue(boolean two) {
        if(count == 0) throw new NullPointerException("can't dequeue from empty Heap");
        Node result = a[1];
        a[1] = a[count--];
        siftDown(1, two);
        return result;
    }
    private int siftDown(int k, boolean two) { 
        if(2*k > count) return k;
        if(2*k == count) {
            if(a[2*k].getDist(two) < a[k].getDist(two)) swap(k, 2*k); //dist1 if heap is q1, dist2 if heap is q2
            return k;
        }
        int small;
        if(a[2*k].getDist(two) < a[2*k + 1].getDist(two)) small = 2*k;
        else small = (2*k + 1);
        if(a[small].getDist(two) < a[k].getDist(two)) {
        swap(small, k);
        siftDown(small, two);
        }
        
            
        //2 children case - determine if [2*k] or 
        //a[2*k + 1] is smaller, compare a[k] to smallest
        // if appropriate, swap and recur
        return k;
    }
    
    public Node peek() {
        return a[1];
    }
    
    public void swap(int p, int q) {
        Node n = a[p];
        a[p] = a[q];
        a[q] = n;
    }
    
    public void resift(Node n, boolean two) {
        int nodeLoc = locs.get(n);
        siftDown(nodeLoc, two);
    }
    
    public boolean isEmpty() {
        if(count == 0) return true;
        else return false;
    }
    
}