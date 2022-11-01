package demo.example;

public class Fibonacci {
    public static void main(String[] args) {
        long[] arr =  new long[1000000];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i < arr.length; i++) {
            System.out.println(i + " -> " +getFibo(arr, i));
        }
    }

    private static long getFibo(long[] arr, int i) {
        if (i == 0) return 0;
        if (i == 1) return 1;
        if (i > arr.length) return -1;
        if (arr[i] > 0) return arr[i];
        arr[i] = (getFibo(arr, i -1) + getFibo(arr, i -2))%1000000;
        return arr[i];
    }
}
