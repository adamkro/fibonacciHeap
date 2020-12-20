public class Testing {

    public static void manualTest(){
        FibonacciHeap h = new FibonacciHeap();
        for (int i = 0; i < 4; i++) {
            h.insert(i);
        }
        h.display();
        h.deleteMin();
        h.display();
    }

    public static void main(String[] args){
       manualTest();
    }
}
