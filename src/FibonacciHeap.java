
import java.util.Arrays;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
    public static int linksCount = 0;
    public static int cutsCount = 0;

    private int size = 0;
    private int potentialTrees = 0;
    private int potentialMarks = 0;
    private HeapNode min;
    private HeapNode root;

   /**
    * public boolean isEmpty()
    *
    * precondition: none
    *
    * The method returns true if and only if the heap
    * is empty.
    * Complexity - O(1)
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

    public int getPotentialTrees()
    {
        return potentialTrees;
    }

    public int getPotentialMarks()
    {
        return potentialMarks;
    }


    /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    *
    * Returns the new node created.
     * Complexity - O(1)
    */
    public HeapNode insert(int key){
        HeapNode node = new HeapNode(key);
        insertNode(node);
        size++;
        return node;
    }

    /**
     * public void insertNode(HeapNode x)
     * insert a node as head of roots.
     * updates min & number of trees
     * Complexity - O(1)
     */
    public void insertNode(HeapNode x){
        updateMin(x);
        setRoot(x);
        potentialTrees++;
    }

    /**
     * private void setRoot(HeapNode node)
     * insert a node as head of roots.
     * Complexity - O(1)
     */
    private void setRoot(HeapNode node){
        if (size != 0)
            node.insertBetween(root.left, root);
        root = node;
    }

    /**
     * private void updateMin(HeapNode node)
     * if node's key is smaller than current min, update min
     * Complexity - O(1)
     */
    private void updateMin(HeapNode node){
        if (min == null || node.getKey() < min.getKey())
            min = node;
    }

   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    * Complexity - O(n)
    */
    public void deleteMin()
    {
     	if (isEmpty())
     	    return;
     	size--;
        if (size == 0){
            min = null;
            root = null;
            potentialMarks = 0;
            potentialTrees = 0;
            return;
        }
     	HeapNode minNode = min;
     	if (minNode.getChild() == null){
            minNode.getLeft().setRight(minNode.getRight());
            minNode.getRight().setLeft(minNode.getLeft());
            if (size() != 0)
                successiveLinking(minNode.getRight());
        }
     	else{
            HeapNode minchild = minNode.getChild();
            minNode.setChild(null);
            if (minNode.getRight()!= minNode)
                 insertChain(minchild,minchild.getLeft(),minNode.getLeft(),minNode.getRight());
            while (minchild.getParent() != null) {
                HeapNode x = minchild.getRight();
                minchild.setParent(null);
                minchild = x;
            }
        successiveLinking(minchild);
        }
    }

    /**
     * private void insertChain(HeapNode startChain, HeapNode endCHain, HeapNode left, HeapNode right)
     * insert a chain from startChain to endChain between left and right.
     * Complexity - O(1)
     */
    private void insertChain(HeapNode startChain, HeapNode endCHain, HeapNode left, HeapNode right) {
        startChain.setLeft(left);
        left.setRight(startChain);
        endCHain.setRight(right);
        right.setLeft(endCHain);
    }

    /**
     * public void successiveLinking (HeapNode x)
     * successive link method as taught in class
     * Complexity - O(n)
     */
    public void successiveLinking (HeapNode x){
        HeapNode[] Buckets = new HeapNode[(int)Math.ceil(Math.log(size)/Math.log(2))+1];
        nodesToBuckets(x,Buckets);
        makeHeapFromBuckets(Buckets);
    }

    /**
     * public void nodesToBuckets (HeapNode x, HeapNode[] Buckets)
     * updates Buckets array with binomial trees by degree
     * Complexity - O(n)
     */

    public void nodesToBuckets (HeapNode x, HeapNode[] Buckets){
        x.getLeft().setRight(null);
        while (x != null){
            HeapNode node = x;
            x = x.getRight();
            node.setLeft(node);
            node.setRight(node);
            while (Buckets[node.getDegree()] != null) {
                node = link(node,Buckets[node.getDegree()]);
                Buckets[node.getDegree()-1] = null;
            }
            Buckets[node.getDegree()] = node;
        }
    }

    /**
     * public void makeHeapFromBuckets (HeapNode[] Buckets)
     * updates current heap based on Buckets array binomial trees
     * Complexity - O(log n)
     */
    public void makeHeapFromBuckets (HeapNode[] Buckets){
        potentialTrees = 0;
        HeapNode rootNode = null;
        HeapNode newMin = null;
        for (HeapNode node: Buckets){
            if (node != null){
                potentialTrees++;
                if (node.isMarked()){
                    node.setMark(false);
                    potentialMarks--;
                }
                if (rootNode == null){
                    rootNode = node;
                    newMin = node;
                    node.setRight(node);
                    node.setLeft(node);
                }
                else{
                    node.insertBetween(rootNode.getLeft(),rootNode);
                    if (node.getKey() < newMin.getKey())
                        newMin = node;
                }
            }
        }
       root = rootNode;
       min =  newMin;
    }

    /**
     * public HeapNode link (HeapNode x,HeapNode y)
     * links x and y as taught in class
     * return new root of tree
     * Complexity - O(1)
     */
    public HeapNode link (HeapNode x,HeapNode y){
        linksCount++;
        HeapNode tmp;
        if (x.getKey() > y.getKey()){
            tmp = y;
            y = x;
            x = tmp;
        }
        if (x.getDegree() > 0)
            y.insertBetween(x.getChild().getLeft(), x.getChild());
        x.setChild(y);
        y.setParent(x);
        x.changeDegreeBy(1);
        return x;
    }





    /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal.
    * Complexity - O(1)
    */
    public HeapNode findMin(){
    	return min;
    }

   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    * Complexity - O(1)
    */
   public void meld (FibonacciHeap heap2) {
       if (heap2.isEmpty())
           return;
       if (this.isEmpty())
           root = heap2.getRoot();
       else
           heap2.getRoot().insertBetween(root.left, root);
       updateMin(heap2.getMin());
       potentialTrees += heap2.getPotentialTrees();
       potentialMarks += heap2.getPotentialMarks();
       size += heap2.size();
   }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    * Complexity - O(1)
    */
    public int size(){
    	return this.size;
    }

    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap.
    * Complexity - O(n)
    */
    public int[] countersRep()
    {
    if (isEmpty())
        return new int[0];
    int maxDegree = 0;
	int[] arr = new int[(int)Math.ceil(Math.log(size)/Math.log(2))+1];
    HeapNode node = root;
    do {
        int degree = node.getDegree();
        arr[degree]++;
        if (degree > maxDegree)
            maxDegree = degree;
        node = node.getRight();
    } while (node != root);
	if (maxDegree + 1 < arr.length){
        int[] correctArr;
        correctArr= Arrays.copyOf(arr,maxDegree + 1 );
        return correctArr;
    }
    return arr;
    }

   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap.
    * Complexity - O(n)
    */
    public void delete(HeapNode x)
    {
        if (this.isEmpty())
            return;
        HeapNode minHeap = findMin();
    	if (x != minHeap) {
            int delta = (x.getKey() - minHeap.getKey());
    	    decreaseKey(x, delta+1);
        }
        deleteMin();
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    * Complexity - O(n)
    */
    public void decreaseKey(HeapNode x, int delta)
    {
    	x.decreaseBy(delta);
        if (x.getParent() == null) {
            updateMin(x);
            return;
        } else if (x.getKey() > x.getParent().getKey())
            return;
        cascadingCuts(x,x.getParent());
    }

    /**
     * public void cascadingCuts(HeapNode x, HeapNode parent)
     * cascading-cuts taught in class
     * Complexity - O(1)
     */
    public void cascadingCuts(HeapNode x, HeapNode parent)
    {
        cut(x,parent);
        if (parent.getParent() != null){
            if (!parent.isMarked()){
                parent.setMark(true);
                potentialMarks++;
            }
            else
                cascadingCuts (parent,parent.getParent());
        }
    }

    /**
     * public void cut(HeapNode x, HeapNode parent)
     * cut node x from it's parent, and insert to heap
     * Complexity - O(1)
     */
    public void cut(HeapNode x, HeapNode parent)
    {
        cutsCount++;
        x.setParent(null);
        if (x.isMarked())
            potentialMarks--;
        x.setMark(false);
        parent.changeDegreeBy(-1);
        if (x.getRight() == x)
            parent.setChild(null);
        else{
            if (parent.getChild() == x)
                parent.setChild(x.getRight());
                x.getLeft().setRight(x.getRight());
                x.getRight().setLeft(x.getLeft());
        }
        x.setRight(x);
        x.setLeft(x);
        insertNode(x);
    }





   /**
    * public int potential()
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap.
    * Complexity - O(1)
    */
    public int potential()
    {
    	return potentialTrees + 2*potentialMarks; // should be replaced by student code
    }

   /**
    * public static int totalLinks()
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value
    * in its root.
    * Complexity - O(1)
    */
    public static int totalLinks()
    {
    	return linksCount;
    }

   /**
    * public static int totalCuts()
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods).
    * Complexity - O(1)
    */
    public static int totalCuts()
    {
    	return cutsCount;
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k)
    *
    * This static function returns the k minimal elements in a binomial tree H.
    * The function should run in O(k*deg(H)).
    * You are not allowed to change H.
      * Complexity - O(k*deg(H))
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {
        int[] arr = new int[k];
        if(k == 0)
            return arr;
        FibonacciHeap h = new FibonacciHeap();
        HeapNode node = H.getMin();
        h.insert(node.getKey()).setTwin(node);
        HeapNode headOfList;
        for (int i = 0; i < k; i++) {
            arr[i] = h.getMin().getKey();
            node = h.getMin().getTwin().getChild();
            h.deleteMin();
            headOfList = node;
            if (node != null){
                do{
                    h.insert(node.getKey()).setTwin(node);
                    node = node.getRight();
                }
                while(node != null && node != headOfList);
            }
        }
        return arr;
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
    private HeapNode twin;
    private int degree;


  	public HeapNode(int key) {
  	    this.key = key;
  	    this.mark = false;
  	    this.parent = null;
        this.child = null;
        this.twin = null;
        this.left = this;
        this.right = this;
        this.degree = 0;
      }
       public HeapNode getTwin() {
           return twin;
       }

       public void setTwin(HeapNode x) {
           twin = x;
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
       /**
        * private void decreaseBy(int delta)
        * decrease this node's key by delta
        * Complexity - O(1)
        */
       private void decreaseBy(int delta) {
           key -= delta;
       }

       /**
        * private void insertBetween(HeapNode start, HeapNode end )
        * insert this node between start and end (linked list)
        * Complexity - O(1)
        */
       private void insertBetween(HeapNode start, HeapNode end ){
           this.left.right = end; // last --> end
           end.left = this.left; // last <-- end
           this.left = start; // start <-- first
           start.right = this; // start --> first
       }

       public int getKey() {
	    return this.key;
      }

       private void setParent(HeapNode parent) {
           this.parent = parent;
       }

       public void setRight(HeapNode right) {
           this.right = right;
       }

       private void changeDegreeBy(int delta) {
           this.degree += delta;
       }

       public void setLeft(HeapNode left) {
           this.left = left;
       }

       private void setChild(HeapNode child) {
           this.child = child;
       }

       private void setMark(boolean mark) {
           this.mark = mark;
       }

       @Override
       public String toString() {
        String l = left != null ? ""+left.getKey(): "";
        String r = right != null ? ""+right.getKey(): "";
           return
                   l + "<-- " + key + " ("+degree+ ") " + " --->" + r +
                   '}';
       }
   }
}
