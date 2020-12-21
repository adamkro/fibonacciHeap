public class Testing {

    public static void manualTest(){
        FibonacciHeap h = new FibonacciHeap();
        for (int i = 0; i < 5; i++) {
            h.insert(i);
        }
        h.displayRoots();
        printHeap.printHeapFib(h);
        System.out.println(h.getRoot().getKey());
        h.deleteMin();
        printHeap.printHeapFib(h);
        h.displayRoots();
    }

    public static void main(String[] args){
       manualTest();
    }
}
