import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
public class SpecialProblem{
    public static int workbook(int n, int k, int[] arr) {
        int count = 0;
        HashMap<Integer,List<Integer>> map = new HashMap<>();
        int counter = 1;
        for(int i=0; i<arr.length;i++){
            int num = arr[i];
            int m =1;
            for(int j=1;j<=(num/k);j++){
                List<Integer> list = new ArrayList<>();
                for(m=j;m<=j*k;m++){
                    list.add(m);
                }
                map.put(counter,list);
                counter++;
            }
            if(num%k!=0){
                List<Integer> list = new ArrayList<>();
                m = (num/k)*k;
                for(int j=m+1;j<=m+(num%k);j++){
                    list.add(j);
                }
                map.put(counter,list);
                counter++;
            }
        }
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            int p = entry.getKey();
            List<Integer> list = entry.getValue();
            System.out.println(p);
            for(int i=0;i<list.size();i++){
                System.out.print(list.get(i)+" ");
            }
            System.out.println();
            for(int i=0;i<list.size();i++){
                if(list.get(i)==p){
                    count++;
                }
            }
        }
        System.out.println();
        return count;
    }
    public static void main(String[] args){
        int[] arr = {4,2,6,1,10};
        int n = 5;
        int k = 3;
        SpecialProblem object = new SpecialProblem();
        System.out.println(object.workbook(n,k,arr));
    }
}
