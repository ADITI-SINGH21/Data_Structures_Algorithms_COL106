import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
//Programming question which gives the array count of sticks
public class Sticks {
    static int[] cutTheSticks(int[] arr) {
        Arrays.sort(arr);
        int stick = arr.length;
        int length = arr[0];
        int m = 0;
        int l = 0;
        int dummy = length;
        Vector<Integer> v = new Vector<>();
        while(true){
            int n = m;
            for(int i=m;i<arr.length;i++){
                arr[i] = arr[i]-length;
                if(arr[i]==0){
                    m++;
                }
            }
            v.add((arr.length-n));
            if(m>=arr.length){
                break;
            }
            length = arr[m];
        }
        int[] result = new int[v.size()];
        for(int i=0;i<v.size();i++){
            result[i] = v.get(i);
        }
        return result;
    }
    public static void main(String[] args) {
        Sticks object = new Sticks();
        int[] test = {5,4,4,2,8,2};
        int[] res = object.cutTheSticks(test);

    }

} 