import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
//this code constructs a magic square of order n;
public class MagicSquare{
	public int[][] constructMagicSquare(int n){
		if(n==2){
			System.out.println("MagicSquare is not possible for 2x2 matrix");
			return null;
		}
		int sum = n*(n*n+1)/2;
		System.out.println("All the rows, columns and diagnols sum upto "+sum);
		if (n%4==0) {//doubly even
			int[][] arr = new int[n][n]; 
        	int i, j; 
	        for ( i = 0; i < n; i++) 
	            for ( j = 0; j < n; j++) 
	                arr[i][j] = (n*i) + j + 1; 
	        for ( i = 0; i < n/4; i++) 
	            for ( j = 0; j < n/4; j++) 
	                arr[i][j] = (n*n + 1) - arr[i][j]; 
	        for ( i = 3 * n/4; i < n; i++) 
	            for ( j = 3 * n/4; j < n; j++) 
	                arr[i][j] = (n*n + 1) - arr[i][j]; 
	        for ( i = 0; i < n/4; i++) 
	            for ( j = 3 * (n/4); j < n; j++) 
	                arr[i][j] = (n*n + 1) - arr[i][j]; 
	        for ( i = 3 * n/4; i < n; i++) 
	            for ( j = 0; j < n/4; j++) 
	                arr[i][j] = (n*n+1) - arr[i][j]; 
	        for ( i = n/4; i < 3 * n/4; i++) 
	            for ( j = n/4; j < 3 * n/4; j++) 
	                arr[i][j] = (n*n + 1) - arr[i][j];
				
			return arr;
		}
		else if(n%4==2){// Singly Even
			return null;
		}
		//Odd Numbered
		int[][] array = new int[n][n];
		int i=n/2;
		int j=n-1;
		int num = 1;
		array[i][j]=num;
		int count=1;
		while(num<n*n){
			num++;
			i=i-1;
			j=j+1;
			if(i==-1&&j!=n){
				i=n-1;
			}
			else if(j==n&&i!=-1){
				j=0;
			}
			else if (i==-1&&j==n) {
				i=0;
				j=n-2;
			}
			if(array[i][j]!=0){
				i=i+1;
				j=j-2;
			}
			array[i][j]=num;
		}
		return array;
	}
	public static void main(String[] args) {
		MagicSquare s = new MagicSquare();
		int n = Integer.parseInt(args[0]);
		int[][] matrix = s.constructMagicSquare(n);
		if (n%2!=0||n%4==0) {
			for (int i=0;i<n;i++) {
				for (int j=0;j<n;j++) {
					System.out.print(matrix[i][j]+" ");
				}
				System.out.println();
			}
		}
	}
}