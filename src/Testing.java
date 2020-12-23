public class Testing {

    public static void manualTest(){
        FibonacciHeap h = new FibonacciHeap();
        h.insert(5);
        h.insert(3);
        h.insert(4);
        h.insert(2);
        h.insert(0);
        h.insert(1);
//        for (int i = 0; i < 5; i++) {
//            h.insert(i);
//        }
//        h.displayRoots();
//        printHeap.printHeapFib(h);
//        System.out.println(h.getRoot().getKey());
//        h.deleteMin();
//        printHeap.printHeapFib(h);
//        h.displayRoots();
        System.out.println(h.findMin().getKey());
        h.displayRoots();
        h.deleteMin();
        System.out.println(h.findMin().getKey());
        h.displayRoots();
        h.deleteMin();
        System.out.println(h.findMin().getKey());
        h.deleteMin();
        System.out.println(h.findMin().getKey());
        h.deleteMin();
        System.out.println(h.findMin().getKey());
        h.deleteMin();
        System.out.println(h.findMin().getKey());
        h.deleteMin();
        System.out.println(h.findMin());
        h.deleteMin();
        System.out.println(h.findMin());
    }

    public static void main(String[] args){
       manualTest();
    }
}
