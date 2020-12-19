import com.sun.deploy.security.SelectableSecurityManager;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
    private static int linksCount = 0;
    private static int cutsCount = 0;

    private int size = 0;
    private int[] potential = {0,0};
    private HeapNode min;
    private HeapNode root;

   /**
    * public boolean isEmpty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean isEmpty()
    {
    	return size == 0;
    }

    public HeapNode getRoot()
    {
        return root;
    }
    public HeapNode getMin()
    {
        return min;
    }

    public int[] getPotential()
    {
        return potential;
    }


    /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * 
    * Returns the new node created. 
    */
    public HeapNode insert(int key){
        HeapNode node = new HeapNode(key);
        updateMin(node);
        setRoot(node);
        size++;
        potential[0]++;
        return new HeapNode(key); // should be replaced by student code
    }

    private void setRoot(HeapNode node){
        if (size != 0)
            node.insertBetween(root.left, root);
        root = node;
    }

    private void updateMin(HeapNode node){
        if (min == null || node.getKey() < min.getKey())
            min = node;
    }

   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	return; // should be replaced by student code
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	return new HeapNode(0);// should be replaced by student code
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
   public void meld (FibonacciHeap heap2) {
       if (heap2.isEmpty())
           return;
       if (this.isEmpty())
           root = heap2.getRoot();
       else
           heap2.getRoot().insertBetween(root.left, root);
       updateMin(heap2.getMin());
       potential[0] += heap2.getPotential()[0];
       potential[1] += heap2.getPotential()[1];
       size += heap2.size();
   }

   // 1 --> 2 --> 3 --> 4 -- > 5 --> heap2 --> heap2 --> heap2
   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size(){
    	return this.size;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep()
    {
	int[] arr = new int[42];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) 
    {    
    	return; // should be replaced by student code
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	x.decreaseBy(delta);
        if (x.getParent() == null) {
            updateMin(x);
            return;
        } else if (x.getKey() > x.getParent().getKey()) //############

    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return potential[0] + 2*potential[1]; // should be replaced by student code
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks()
    {    
    	return 0; // should be replaced by student code
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return 0; // should be replaced by student code
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k minimal elements in a binomial tree H.
    * The function should run in O(k*deg(H)). 
    * You are not allowed to change H.
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[42];
        return arr; // should be replaced by student code
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{

	public int key;
	private boolean mark;
	private HeapNode parent;
	private HeapNode child;
	private HeapNode left;
    private HeapNode right;
    private int degree;

  	public HeapNode(int key) {
  	    this.key = key;
  	    this.mark = false;
  	    this.parent = null;
        this.child = null;
        this.left = this;
        this.right = this;
        this.degree = 0;
      }

       public HeapNode getParent() {
           return parent;
       }

       public HeapNode getLeft() {
           return left;
       }

       public HeapNode getRight() {
           return right;
       }

       public HeapNode getChild() {
           return child;
       }

       public int getDegree() {
           return degree;
       }

       public boolean isMarked() {
           return mark;
       }

       private void decreaseBy(int delta) {
           key -= delta;
       }

       private void insertBetween(HeapNode start, HeapNode end ){
           this.left.right = end; // last --> end
           end.left = this.left; // last <-- end
           this.left = start; // start <-- first
           start.right = this; // start --> first
       }

       public int getKey() {
	    return this.key;
      }

    }
}
