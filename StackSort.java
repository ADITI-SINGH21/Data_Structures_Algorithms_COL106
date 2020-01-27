import java.util.Scanner;
import java.util.Arrays;
public class StackSort{
	MyStack<Integer> stacksort = new MyStack<Integer>();
	MyStack<Integer> stacksortd = new MyStack<Integer>();
	MyStack<Integer> stacksortx = new MyStack<Integer>();
	String[] array;
	int[] check;
	String[][] output;
	public StackSort(){
		MyStack<Integer> stacksort = new MyStack<Integer>();
		MyStack<Integer> stacksortd = new MyStack<Integer>();
		MyStack<Integer> stacksortx = new MyStack<Integer>();
	}
	public String[] sort(int[] nums){
		array = new String[nums.length*2];
		check = new int[nums.length];
		int i=0;
		try{
			for(i=0;i<nums.length;i++){
				if(stacksort.isEmpty()==true){
					stacksort.push(nums[i]);
					array[nul(array)]="PUSH";
				}
				else if (stacksort.top()<nums[i]){
					while(stacksort.top()<nums[i]){
						check[inul(check)]=stacksort.pop();
						array[nul(array)]="POP";
						if(stacksort.isEmpty()==true){
							break;
						}
					}
					stacksort.push(nums[i]);
					array[nul(array)]="PUSH";
					}

				else if (stacksort.top()>nums[i]){
					stacksort.push(nums[i]);
					array[nul(array)]="PUSH";
				}
				else{
					continue;
				}
			}
			while(stacksort.isEmpty()==false){
				check[inul(check)]=stacksort.pop();
				array[nul(array)]="POP";
				}
			}
			catch(EmptyStackException e){
				System.out.println("Error");
			}
			
			if (match(nums,check)==true) {
				return array;
			}
			else{
				String[] array2;
				array2 = new String[]{"NOTPOSSIBLE"};
				return array2;
			}
		}
	public int[] matsort(int[] arr){
		int[] arr1 = new int[arr.length];
		for (int i=0;i<arr.length ;i++ ) {
			arr1[i]=arr[i];
		}
		int n = arr.length; 
        for (int i = 1; i < n; i++) { 
            int key = arr1[i]; 
            int j = i - 1; 
            while (j >= 0 && arr1[j] > key) { 
                arr1[j + 1] = arr1[j]; 
                j = j - 1; 
            } 
            arr1[j + 1] = key; 
        }
        return arr1; 
	}
	public int nul(String[] mat){
		int k=0;
		if (mat[0]==null){
			return 0;
		}
		else{
			while (k<mat.length ){
			if (mat[k]==null){
				break;
			}k++;
		}
		return k;
		}
		
	}
	public int inul(int[] mat){
		int k=0;
		if (mat[0]==0){
			return 0;
		}
		else{
			while (k<mat.length){
			if (mat[k]==0){
				break;
			}k++;
		}
		return k;
		}
		
	}
	public boolean match(int[] nums , int[] check){
		int l=0;
		int[] tm = new int[nums.length];
		tm=matsort(nums);
			for (l=0;l<nums.length ;l++ ) {
				if (check[l]!=tm[l]) {
					break;
				}
			}
		if (l==nums.length) {
			return true;
		}
		else {
			return false;
		}

	}
	public String[][] kSort(int[] nums){
		int k = key(nums);
		int[] matrix = new int[nums.length];
		matrix = nums;
		String[] sequence = new String[nums.length*2];
		output=new String[k][nums.length*2];
		for (int i=0;i<k ;i++) {
			sequence=sortx(matrix);
			for (int j=0;j<nums.length*2;j++) {
				output[i][j]=sequence[j];
			}
			matrix = sortnew(matrix);
		}
		return output;
	}
	public int key(int[] nums){
		int k = 0;
		int[] matr = new int[nums.length];
		String[] sequence = new String[nums.length*2];
		matr = nums;
		while(match(matsort(nums),matr)!=true){
			k++;
			matr = sortnew(matr);
		}
		return k;
	}
	public int[] sortnew(int[] nums){
		int[] check1 = new int[nums.length];
		try{
			for(int i=0;i<nums.length;i++){
				if(stacksortd.isEmpty()==true){
					stacksortd.push(nums[i]);
				}
				else if (stacksortd.top()<nums[i]){
					while(stacksortd.top()<nums[i]){
						check1[inul(check1)]=stacksortd.pop();
						if(stacksortd.isEmpty()==true){
							break;
						}
					}
					stacksortd.push(nums[i]);
					}

				else if (stacksortd.top()>nums[i]){
					stacksortd.push(nums[i]);
				}
				else{
					continue;
				}
			}
			while(stacksortd.isEmpty()==false){
				check1[inul(check1)]=stacksortd.pop();
				}
			}
			catch(EmptyStackException e){
				System.out.println("Error");
			}
			return check1;
		}
		public String[] sortx(int[] nums){
		String[] array1 = new String[nums.length*2];
		int i=0;
		try{
			for(i=0;i<nums.length;i++){
				if(stacksortx.isEmpty()==true){
					stacksortx.push(nums[i]);
					array1[nul(array1)]="PUSH";
				}
				else if (stacksortx.top()<nums[i]){
					while(stacksortx.top()<nums[i]){
						stacksortx.pop();
						array1[nul(array1)]="POP";
						if(stacksortx.isEmpty()==true){
							break;
						}
					}
					stacksortx.push(nums[i]);
					array1[nul(array1)]="PUSH";
					}

				else if (stacksortx.top()>nums[i]){
					stacksortx.push(nums[i]);
					array1[nul(array1)]="PUSH";
				}
				else{
					continue;
				}
			}
			while(stacksortx.isEmpty()==false){
				stacksortx.pop();
				array1[nul(array1)]="POP";
				}
			}
			catch(EmptyStackException e){
				System.out.println("Error");
			}
			return array1;
		}
	public static void main(String[] args) {
		int[] mat1;
		mat1 = new int[]{10,702,36,125,82};
		String[] mat2 = new String[2*mat1.length];
		StackSort stacks = new StackSort();
		mat2=stacks.sort(mat1);
		for (int i=0;i<(mat2).length;i++ ) {
			System.out.println(mat2[i]+" ");
		}System.out.println();
		int n = stacks.key(mat1);
		String[][] mat3 = new String[n][2*mat1.length];
		mat3=stacks.kSort(mat1);
		for (int i=0;i<mat3.length ;i++ ) {
			for (int j=0;j<mat3[0].length ;j++ ) {
				System.out.println(mat3[i][j]);
			}System.out.println();
		}
	}
}