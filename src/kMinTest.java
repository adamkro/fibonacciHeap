import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class kMinTest {
    public static void testKmin(){
        FibonacciHeap h = new FibonacciHeap();
        Random rd = new Random();
        int[] arr;
        int num, binomialTreeSize, j, i, k;
        Set<Integer> hs= new HashSet<Integer>();
        j = 0;
        while(j < 15){ // loop tree sizes
            binomialTreeSize = (int) Math.pow(2,j) + 1;
            arr = new int[binomialTreeSize];
            i = 0;
            while(i < arr.length) { // create tree with random unique keys
                num = rd.nextInt();
                if (hs.contains(num))
                    continue;
                hs.add(num);
                arr[i] = num;
                h.insert(num);
                i++;
            }
            k = Math.max(rd.nextInt(arr.length),1);
            Arrays.sort(arr);
            arr = Arrays.copyOfRange(arr, 1, k+1); // slice sorted array
            h.deleteMin();
            int[] kmin = FibonacciHeap.kMin(h, k);
            if (!Arrays.equals(kmin, arr)){
                System.out.println("---------------------------");
                System.out.println("Problem with:");
                //printHeap.printHeapFib(h);
                System.out.println("kmin: "+ Arrays.toString(kmin));
                System.out.println("arr: "+ Arrays.toString(arr));
            }
            j++;
            h = new FibonacciHeap();
        }
    }

    public static void main(String[] args){
        for (int i = 0; i < 100; i++) {
            testKmin();
        }
        System.out.println("done!");
    }

}
