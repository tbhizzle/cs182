public class MySorts {
    // the number of items in a sub array that when bellow an algo will use insert instead of itself
    private final static int MERGE_THRESHOLD = 5;
    //threshold for the minimum distance between elements in the array
    private final static int SHELL_THRESHOLD = 3;

    public static void main(String[] args) {
        Integer[] integers = new Integer[args.length];
        int[] ints = new int[args.length];
        String[] args2 = new String[args.length];
        for(int i = 0; i < args.length; i++)
            args2[i] = args[i];

        for (int i = 0; i < args.length; i++) {
            if(args[i].equals("null")){
                integers[i] = null;
                continue;
            }
            integers[i] = new Integer(args[i]);
            //Integers[i] = new Integer(Integer.toString((int)(Math.random() * 10000.0)));
        }

        for(int i = 0; i < integers.length; i++){
            ints[i] = (integers[i] == null)? 0: integers[i].intValue();
        }
        insert(null, 10);
        merge(null, 10);
        shell(null, -99);
        insert(args, args2.length);
        merge(args2, args.length);
        shell(ints, ints.length);
        System.out.println("Insert");
        for(String s: args)
            System.out.println(s);
        System.out.println("merge");
        for (String s: args2)
            System.out.println(s);
        System.out.println("shell");
        for(int i: ints)
            System.out.println(i);


    }
    //The method users should call
    public static void insert(String[] arr, int n) {
        Comparable[] arr2 = arr;
        insert(arr2, n);
    }
    //comparable version of the insert method
    private static void insert(Comparable[] arr, int n) {
        //error checking
        if (arr != null && n > 1) {
            if (n > arr.length)
                n = arr.length;
            insert(arr, n, 0, 1);
        }
    }
    //comparable version of insert sort, swaps small values to the front of the array
    private static void insert(Comparable[] arr, int n, int start, int incr){
        int i, j;
        for(i = start + incr; i < n; i+= incr){
            for(j = i; j > start && nullSafeCompareTo(arr[j], arr[j-incr]) <= 0; j-=incr){
                //System.out.println("swap");
                swap(arr, j, j - incr);
            }
        }
    }

    //inner insert sort that uses primitive type int instead of comparable
    private static void insert(int[] arr, int n, int start, int incr){
        int i, j;
        for(i = start + incr; i < n; i+= incr){
            for(j = i; j > start && (arr[j] < arr[j-incr]); j-=incr){
                //System.out.println("swap");
                swap(arr, j, j - incr);
            }
        }
    }

    //The method users should call
    public static void merge(String[] arr, int n){
        Comparable[] arr2 = arr;
        merge(arr2, n);//comparable version of method
    }
    //private method that can take any comparable array as an argument
    private static void merge(Comparable[] arr, int n){
        if (arr != null && n > 1) {
            if (n > arr.length)
                n = arr.length;
            if(n < 10)//if the array is too small just do an insert sort
                insert(arr, n);
            Comparable[] temp = new Comparable[n];
            merge(arr, 0, n - 1, temp);
        }
    }

    //private method that recursively calls itself
    private static void merge(Comparable[] arr, int l, int r, Comparable[] temp){
        int middle = l + (r-l)/2;

        if(r > l){
            //to break the case where the method recursively keeps sorting a array of length 1 on the right side
            if(middle == l)
                middle++;

            if(r-l < MERGE_THRESHOLD)//do a insert sort instead of a recursvie merge sort on small sections
                insert(arr, r-l, l, 1);
            //sort the two halves
            merge(arr, l, middle-1, temp);
            merge(arr, middle, r, temp);

            //merge the two sorted halves together

            //copy from the arr into the temp
            for(int i = 0; i < middle; i++)
                temp[i] = arr[i];

            //copy second half of arr into temp, but backwards
            for(int i = middle, j = r; i <= r; i++, j--)
                temp[i] = arr[j];

            //comparison loop
            for(int k = l, i = l, j = r; k <= r; k++){
                if(nullSafeCompareTo(temp[i], temp[j]) <= 0 )
                    arr[k] = temp[i++];
                else
                    arr[k] = temp[j--];
            }
        }

    }
    //sorts primitive ints by first by attempting to put large items towards the back and small towards the front
    public static void shell(int[] arr, int n) {
        if (arr != null && n > 1) {
            if (n > arr.length)
                n = arr.length;
            int incr, s;
            //sort non-adjecent indexes, makes generally more sorted, larger items tend to the back and small to front
            for (incr = n / 2; incr > SHELL_THRESHOLD; incr = (incr / 2) + 1) {
                for (s = 0; s < incr; s++)
                    insert(arr, n, s, incr);
            }
            insert(arr, n, 0, 1);
        }
    }
    //swaps objects using a temp var. This includes swapping strings
    private static void swap(Object[] arr, int i, int j) {
        Object temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    //swaps ints without the use of a temp var
    private static void swap(int[] arr, int i, int j){
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
    }

    //compares comparables in a null safe fashion, including strings
    private static <T extends Comparable> int nullSafeCompareTo(final T one, final T two){
        if(one == null ^ two == null){//if only one is null
            return (one == null)? -1 : 1;//null is always less than anything else
        }
        if(one == null && two == null){//both are null
            return 0;
        }
        return one.compareTo(two);
    }
}
