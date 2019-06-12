package hw3;

public class ArrayUtils {
	
	
	public static int[] shiftArrayToTheRight(int[] array, int move) {
		// input: array and integer
		// output: array moved to the right as specified in the question
		int len = array.length;
		int[] newArray = new int[len];
		if (move >= 0) {
			for (int i = 0; i < len; i++) {
				newArray[(len - i + move - 1) % len] = array[len - i - 1];
			}
		} else {
			newArray = array;
		}
		return newArray;

	}

	public static int matrixTrace(int[][] m) {
		// input: matrix n X n
		// output: sum of numbers on the main diagonal
		int sum = 0;
		for (int i = 0; i < m.length; i++) {
			sum = sum + m[i][i];
		}
		return sum;

	}

	public static int[][] matrixSwitchRows(int[][] m, int I, int j) {
		// input: matrix and two row index
		// output: switch the two rows in the matrix
		int[] a = m[I];
		int[] b = m[j];
		m[I] = b;
		m[j] = a;
		return m;
	}

	public static int[][] matrixScalarRow(int[][] m, int s, int j) {
		// input: matrix, scalar and a row index
		// output: matrix after multiplying row j in the scalar s
		int[] a = m[j];
		for (int i = 0; i < a.length; i++) {
			a[i] = a[i] * s;
		}
		m[j] = a;

		return m;
	}

	public static int[][] matrixMultiplication(int[][] m, int[][] n) {
		// input: two matrix
		// output: their multiplication
		int[][] a = new int[m.length][n[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < n[0].length; j++) {
				int sum = 0;
				for(int k = 0;k<n.length;k++){
					sum = sum + m[i][k]*n[k][j];
				}
				a[i][j] = sum;
			}
		}
		return a;

	}

}
