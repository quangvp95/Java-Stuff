package demo.example;

import java.util.Arrays;

public class BuyHonkaiStragedy {

	private static final int[][] ARR = { { 65310, 999 }, { 78423, 233 }, { 3111, 520 }/* , { 2568, 0 } */, { 29209, 100 },
			/* { 2018, 0 }, */ { 17250, 999 }, { 1476, 666 }/* , { 1967, 0 } */};

	private final int mBalance;
	private int[] result = new int[ARR.length];
	private int min = Integer.MAX_VALUE;

	public BuyHonkaiStragedy(int balance) {
		mBalance = balance;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new BuyHonkaiStragedy(1000000).process();
	}

	private void process() {
		int[] temp = new int[ARR.length];
		tryNext(temp, 0, mBalance);
		System.out.println("Min :" + min);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < result.length; i++) {
			builder.append(ARR[i][0]).append(" : ").append(result[i]).append(" | ");
		}
		System.out.println("List :" + builder);
	}

	private void tryNext(int[] currentArr, int nextIndex, int balance) {
		if (nextIndex == ARR.length - 1) {
			currentArr[nextIndex] = balance / ARR[nextIndex][0];
			int c = caculate(currentArr);
			StringBuilder builder = new StringBuilder("c :" + c).append(" - ");
			for (int i : currentArr) {
				builder.append(i).append(" | ");
			}
			System.out.println(builder);
			
			if (0 <= c && c < min) {
				result = Arrays.copyOf(currentArr, ARR.length);
				min = c;
			}
			currentArr[nextIndex] = 0;
			return;
		}
		
		int maxTry = Math.min(balance / ARR[nextIndex][0], ARR[nextIndex][1]);
		for (int i = 0; i <= maxTry; i++) {
			if (nextIndex == 6 && i > 100)
				System.out.print("");
	
			currentArr[nextIndex] = i;
			int c = caculate(currentArr);
			
			StringBuilder builder = new StringBuilder("c :" + c).append(" - ");
			for (int j : currentArr) {
				builder.append(j).append(" | ");
			}
			System.out.println(builder);
			
			if (0 <= c && c < min) {
				result = Arrays.copyOf(currentArr, ARR.length);
				min = c;
			}
			tryNext(currentArr, nextIndex + 1, balance - i * ARR[nextIndex][0]);
		}
		currentArr[nextIndex] = 0;
	}

	private int caculate(int[] arr) {
		int balance = mBalance;
		for (int i = 0; i < arr.length; i++) {
			int j = arr[i];
			balance -= j * ARR[i][0];
		}
		return balance;
	}
}
