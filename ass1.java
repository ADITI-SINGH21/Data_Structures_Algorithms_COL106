import java.util.Scanner;
import java.text.NumberFormat;
import java.io.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.*;
//import java.edu.iitd.col1062020.*;
class IncompatibleDimensionException extends Exception{
}
class SubBlockNotFoundException extends Exception{
}
class InverseDoesNotExistException extends Exception{
}
class TwoDBlockMatrix{
	public float[][] array;
	public static TwoDBlockMatrix buildTwoDBlockMatrix(java.io.InputStream in){
		    float[][] arr;
		    String token="";
			Scanner s = new Scanner(in);
			List<String> temps = new ArrayList<String>();
			while (s.hasNext()){
				String token1 = s.nextLine();
				temps.add(token1);
				//System.out.println(temps.get(xa++));
			}
			int c=0; 
			int a=0; 
			int b=0;
			int i, j;
			s.close();
			for (i=0; i<temps.size(); i++) {
				if ((temps.get(i)).charAt(temps.get(i).length()-1)=='#') c++;
				else if ((temps.get(i)).charAt(temps.get(i).length()-1)==';') a++;
			}
			b = 2*c+a;
			String a1= "";
			int[] rx = new int[c+1];
			rx[0] = 0;
			int ri=0, rxIndex=1, flag=0;
			i=0;
			while(i<b){
				while ((temps.get(i)).charAt(temps.get(i).length()-1)==';'){
					ri++;
					i++;
					flag=1;
				}
				if(flag==1)
					rx[rxIndex++]=ri;
				ri=0;
				i++;
				flag = 0;
			}//rx = count of rows in each block
			int[] r = new int[c];
			for (i=1;i<c+1 ;i++ ) {
				r[i-1]=rx[i]; 
			}
			//System.out.println(r[0]+" "+r[1]);
			Vector rc = new Vector();
			i = 0;
			while (i<b){
				if ((temps.get(i)).charAt(temps.get(i).length()-1)!='#' && (temps.get(i)).charAt(temps.get(i).length()-1)!=';'){
					rc.add(temps.get(i));
				}i++;
			}
			Vector rc1 = new Vector();
			Vector rc2 = new Vector();
			for (i=0;i<rc.size() ;i++ ) {
				rc1.add(Integer.parseInt((((String)rc.get(i))).split(" ")[0]));
				rc2.add(Integer.parseInt((((String)rc.get(i))).split(" ")[1]));
			}//rc1 vector of rows and columns
			int row=0,col=0;
			for (i=0;i<r.length ;i++) {
				if (row<r[i]+(int)rc1.get(i)-1){
					row=r[i]+(int)rc1.get(i)-1;
				}
			}
			int d=1, m=0;
			Vector c2 = new Vector();
			for (i=0;i<c ;i++ ) {
				m=(temps.get(d).split(" ")).length;
				d += r[i]+2;
				c2.add(m);
			}
			for (i=0;i<c ;i++ ) {
				if (col<(int)c2.get(i)+(int)rc2.get(i)-1){
					col=(int)c2.get(i)+(int)rc2.get(i)-1;
				}
			}
			s.close();
			arr = new float[row][col];
			for(i=0; i<row; ++i){
				for(m=0; m<col; ++m){
					arr[i][m] = 0;
				}
			}
			i = 0;
			int p=0, q=0;
			while(i<b){
				if((temps.get(i)).charAt(temps.get(i).length()-1)==';'){
					String[] star = temps.get(i).split(" ");
					for(j=0; j<star.length-1; ++j){
						arr[p][q+j] = Float.parseFloat(star[j]);
					}
					arr[p][q+j] = Float.parseFloat(star[j].substring(0, star[j].length()-1));
					//arr[p][q+j] = Float.parseFloat(((star[j].length()-1).split(";"))[0]);
					p ++;
				}
				else if((temps.get(i)).charAt(temps.get(i).length()-1)=='#'){
					++ i;
					continue;
				}
				else{
					String[] star = temps.get(i).split(" ");
					p = Integer.parseInt(star[0])-1;
					q = Integer.parseInt(star[1])-1;
				}
				++ i;
			}
			TwoDBlockMatrix ar = new TwoDBlockMatrix(arr);
			return ar;
    }   
    public float round(float num){
    	float x = num*100;
    	int i = (int)x;
    	int j = (int)(x*10);
    	if(j%10>5)
    		i ++;
    	else if(j%10==5){
    		if(((j%100)/10)%2!=0)
    			i ++;
    	}
    	return (float)(i)/(float)100.0;
    }

    public TwoDBlockMatrix(float[][] a) {
    	//array=a;
    	int m = a.length;
    	int n = a[0].length;
    	array = new float[m][n];
    	for (int i=0;i<m ;i++ ) {
    		for (int j=0;j<n ;j++ ) {
    			array[i][j]=a[i][j];
    		}
    	}
    }
    public float[][] helpMat(int r,int c, float[][] matrix){
    	float[][] mat1 = new float[matrix.length-1][matrix[0].length-1];
    	for (int i=0;i<matrix.length;i++ ) {
       		for (int j=0; j<matrix[0].length;j++){
    			if (i==r||j==c) 
    				continue;
    			else if(j<c&&i<r)
    				mat1[i][j] = matrix[i][j];
    			else if(j<c&&i>r)
    				mat1[i-1][j] = matrix[i][j];
    			else if(j>c&&i<r)
    				mat1[i][j-1] = matrix[i][j];
    			else 
    				mat1[i-1][j-1] = matrix[i][j];
    		}
    	}  	
    	return mat1;
    }
    public float determinant(float[][] arr){
    	if (arr.length==1 && arr[0].length==1){
    		return arr[0][0];
    	}
    	float sum = 0, sign = -1;
    	for (int k=0; k<arr[0].length ;k++ ){
    		if(arr[0][k]==0){
    			continue;
    		}
    		float[][] newmat = helpMat(0, k, arr);
    		sign *= -1;
    		sum+=sign*arr[0][k]*determinant(newmat);
		}
		return sum;
	}
	public float cofactor(int r ,int c, float[][] matrix1){
		if ((r+c)%2==0) return determinant(helpMat(r,c,matrix1));	
		else return -1*determinant(helpMat(r,c,matrix1));
	}
	@Override
    public String toString(){
    	float[][] a = new float[array.length][array[0].length];
    	for(int i=0; i<a.length; ++i){
    		for(int j=0; j<a[0].length; ++j){
    			a[i][j]=array[i][j];
       		}
    	}
    	int i=0; int j=0;
    	String str = "";
    		while (i<a.length && j<a[0].length) {
    			if(a[i][j]!=0){
    				int c=j;
    				int r=i;
    				while(c<a[0].length){
    					if(a[i][c]!=0)
    						c ++;
    					else break;
    				}
    				while(r<a.length){
    					int ci = 0;
    					for(ci=j; ci<c; ++ci){
    						if(a[r][ci]==0)
    							break;
    					}
    					if(ci<c)
    						break;
    					r++;
    				}
    				str = str + String.valueOf(i+1) + " " + String.valueOf(j+1) + "\n"; 
    				for(int ir=i; ir<r; ++ir){
    					for(int ic=j; ic<c; ++ic){
    						str = str + String.valueOf(a[ir][ic]);
    						a[ir][ic] = 0;
    						if(ic<c-1){
    							str = str + " ";
    						}
    						else{
    							str = str + ";";
    						}
    					}
    					str = str + "\n";
    				}
    				str = str + "#\n";
    				if(c==a[0].length){
    					i ++;
    					j = 0;
    				}
    				else{
    					j ++;
    				}
    			}
    			else{
    				if(j==a[0].length-1){
    					j = 0;
    					i ++;
    				}
    				else{
    					j ++;
    				}
    			}
    	}
		return str; 
    }
	public TwoDBlockMatrix transpose(){
		float[][] t = new float[array[0].length][array.length];
		for (int i=0;i<array.length ;i++ ) {
			for (int j=0;j<array[0].length ;j++ ) {
				t[j][i]=round(array[i][j]);
			}			
		}
		TwoDBlockMatrix t1 = new TwoDBlockMatrix(t);
		return t1;
	}
	public TwoDBlockMatrix multiply(TwoDBlockMatrix other) throws IncompatibleDimensionException{
		if(array[0].length != other.array.length){
			throw new IncompatibleDimensionException();
		}
			float[][] m = new float[array.length][other.array[0].length];
			for (int i=0;i<array.length ;i++ ) {
				for (int j=0;j<other.array[0].length ;j++ ) {
					m[i][j]=0;
					for (int k=0;k<array[0].length;k++ ) {
						m[i][j]+=array[i][k]*other.array[k][j];
					}
					m[i][j] = round(m[i][j]);
				}
			}
			TwoDBlockMatrix m1 = new TwoDBlockMatrix(m);
			return m1;
    }
	public TwoDBlockMatrix getSubBlock (int row_start, int col_start, int row_end, int col_end) throws SubBlockNotFoundException{
		if (row_start>array.length || row_end>array.length || col_start>array[0].length || col_end>array[0].length || row_start-row_end>0 || col_start-col_end>0){
			throw new SubBlockNotFoundException();
		}
			float[][] sub = new float[row_end - row_start + 1][col_end - col_start + 1];
			for (int i=row_start-1;i<row_end-1 ;i++ ) {
				for (int j=col_start-1;j<col_end-1 ;j++ ) {
					sub[i-row_start+1][j-col_start+1]=round(array[i][j]);
				}
			}
			TwoDBlockMatrix sub1 = new TwoDBlockMatrix(sub);
			return sub1;
	}
	public TwoDBlockMatrix inverse() throws InverseDoesNotExistException{
		if (array.length!=array[0].length || determinant(array)==0){
			throw new InverseDoesNotExistException();
		}
		float[][] inv = new float[array.length][array[0].length];
		for (int i=0; i<array.length ; i++ ) {
			for (int j=0; j< array.length;j++ ) {
				inv[i][j]=cofactor(i,j,array)/determinant(array);
			}
		}
		TwoDBlockMatrix inv1 = new TwoDBlockMatrix(inv);
		return inv1.transpose();

	}
		
}
class SparseTwoDBlockMatrix extends TwoDBlockMatrix{
	
	public SparseTwoDBlockMatrix(float[][] a){
		super(a);
		int m = a.length;
    	int n = a[0].length;
    	array = new float[m][n];
    	for (int i=0;i<m ;i++ ) {
    		for (int j=0;j<n ;j++ ) {
    			array[i][j]=a[i][j];
    		}
    	}

	}

	public Vector nonzero(float[][] mat){
		Vector v1 = new Vector();
		for (int i=0;i<mat.length ;i++ ) {
			for (int j=0;j<mat[0].length ;j++ ) {
				if (mat[i][j]!=0){
					float[] v2 = {i,j,mat[i][j]};
					v1.add(v2);
				}
			}
		}
		return v1;
	}
	public SparseTwoDBlockMatrix multiply(SparseTwoDBlockMatrix other) throws IncompatibleDimensionException{
		if(array[0].length != other.array.length){
			throw new IncompatibleDimensionException();
		}
		Vector a1 = new Vector();
		Vector a2 = new Vector();
		a1 = nonzero(array);
		a2 = nonzero(other.array);
		float[][] a3 = new float[array.length][other.array[0].length];
		for (int i=0;i<array.length ;i++ ) {
			for (int j=0;j<other.array[0].length ;j++ ) {
				a3[i][j]=0;
			}
		}
		int k=0;
		for (int i=0;i<a1.size() ;i++ ) {
			k = 0;
			while (k<a2.size()){
				if (((float[])a1.get(i))[1]==((float[])a2.get(k))[0]) {
					a3[(int)(((float[])a1.get(i))[0])][(int)(((float[])a2.get(k))[1])]+=((float[])a1.get(i))[2]*((float[])a2.get(k))[2];
					k++;
				}
				else{
					k++;
				}
			}			
		}
		return new SparseTwoDBlockMatrix(a3);
	}
	/*public SparseTwoDBlockMatrix inverse() throws InverseDoesNotExistException{
		if (array.length!=array[0].length || det(array)==0){
			throw new InverseDoesNotExistException();
		}

	}*/
}
