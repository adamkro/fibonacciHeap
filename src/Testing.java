public class Testing {
    public static void main(String[] args){
        FibonacciHeap h = new FibonacciHeap();
        for (int i = 0; i < 10; i++) {
            h.insert(i);
        }
        h.deleteMin();
        h.display();

    }
}
