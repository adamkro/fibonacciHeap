public class Medidot {

    public static void seq1 (int m){
        long startTime = System.nanoTime();
        FibonacciHeap.HeapNode[] arr = new  FibonacciHeap.HeapNode[m+1];
        FibonacciHeap heap = new FibonacciHeap();
        for (int i = m ; i >= 0 ; i--){
            arr[i] = heap.insert(i);
        }
        heap.deleteMin();
        for (int i = 0 ; i < (int)Math.log(m)/Math.log(2); i++){
            double sigma = 0;
            for (int k = 1; k <= i; k++)
                sigma += Math.pow(0.5,k);
            sigma = m*sigma +2;
            heap.decreaseKey(arr[(int)sigma],m+1);
        }
        //heap.decreaseKey(arr[m-1],m+1);
//        System.out.println(heap.getPotentialMarks());//
//        System.out.println(heap.getPotentialTrees());//
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("the m is "+m);
        System.out.println("running time "+ totalTime);
        System.out.println("totalLinks "+FibonacciHeap.totalLinks());
        System.out.println("totalCut "+ FibonacciHeap.totalCuts());
        System.out.println("potentail "+heap.potential());
        System.out.println("***********");
        FibonacciHeap.cutsCount = 0;
        FibonacciHeap.linksCount = 0;
    }

    public static void seq2 (int m){
        long startTime = System.nanoTime();
        FibonacciHeap heap = new FibonacciHeap();
        for (int i = m ; i >= 1 ; i--)
            heap.insert(i);
        for (int i = 1 ; i <= m/2; i++)
            heap.deleteMin();


        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("the m is "+m);
        System.out.println("running time "+ totalTime);
        System.out.println("totalLinks "+FibonacciHeap.totalLinks());
        System.out.println("totalCut "+ FibonacciHeap.totalCuts());
        System.out.println("potentail "+heap.potential());
        System.out.println("***********");
        FibonacciHeap.cutsCount = 0;
        FibonacciHeap.linksCount = 0;
    }



    public static void main (String[] args){
        seq1((int)Math.pow(2,10));
        seq1((int)Math.pow(2,11));
        seq1((int)Math.pow(2,12));

        seq2(1000);
        seq2(2000);
        seq2(3000);

    }
}
