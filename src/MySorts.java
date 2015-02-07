import java.util.Comparator;

/**
 * Created by O120 on 2/5/2015.
 */
public class MySorts {
    // the number of items in a sub array that when bellow an algro will use insert instead of itself
    private final static int INSERT_THRESHOLD = 5;

    public static void main(String[] args) {
        Integer[] integers = new Integer[args.length];
        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("null")){
                integers[i] = null;
                continue;
            }
            integers[i] = new Integer(args[i]);
            //integers[i] = new Integer(Integer.toString((int)(Math.random() * 10000.0)));
        }
        merge(integers, integers.length);
        for (Integer i : integers) {
            System.out.println(i);
        }

    }

    public static void insert(Comparable[] arr, int n) {

        if (arr != null && n > 1) {
            if (n > arr.length)
                n = arr.length;
            insert(arr, n, 0, 1);
        }
    }
    private static void insert(Comparable[] arr, int n, int start, int incr){
        int i, j;
        for(i = start + incr; i < n; i+= incr){
            for(j = i; j > start && nullSafeCompareTo(arr[j], arr[j-incr]) == -1; j-=incr){
                //System.out.println("swap");
                swap(arr, j, j - incr);
            }
        }
    }

    public static void merge(Comparable[] arr, int n){
        if (arr != null && n > 1) {
            if (n > arr.length)
                n = arr.length;
            if(n < 10)insert(arr, n);
            Comparable[] temp = new Comparable[n];
            merge(arr, 0, n - 1, temp);
        }
    }

    private static void merge(Comparable[] arr, int l, int r, Comparable[] temp){
        int middle = l + (r-l)/2;

        if(r > l){
            if(middle == l)middle++;

            if(r-l < INSERT_THRESHOLD)//do a insert sort instead of a recursvie merge sort on small sections
                insert(arr, r-l, l, 1);
            merge(arr, l, middle-1, temp);
            merge(arr, middle, r, temp);

            for(int i = 0; i < middle; i++){
                temp[i] = arr[i];

            }

            for(int i = middle, j = r; i <= r; i++, j--){
                temp[i] = arr[j];
            }
            for(int k = l, i = l, j = r; k <= r; k++){

                if(nullSafeCompareTo(temp[i], temp[j]) <= 0 )
                    arr[k] = temp[i++];
                else
                    arr[k] = temp[j--];
            }
        }

    }

    public static void shell(int[] arr, int n){

    }
    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static <T extends Comparable> int nullSafeCompareTo(final T one, final T two){
        if(one == null ^ two == null){
            return (one == null)? -1 : 1;
        }
        if(one == null && two == null){
            return 0;
        }
        return one.compareTo(two);
    }
}
