import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;
//this code finds the highest palindrome value which is possible from a given number and the number of changes allowed
public class Solution {
    static String highestValuePalindrome(String s, int n, int k) {
        if(k>n){
            String a = new String();
            for (int i=0; i<n; i++ ) {
                a = a+"9";
            }
            return a;
        }
        int num = s.length();
        int c = num/2;
        int count = 0;
        int diff = 0;
        for(int i=0;i<c;i++){
            if(Character.toString(s.charAt(i)).equals(Character.toString(s.charAt(num-i-1)))){
                continue;
            }
            else{
                count++;
            }
        }
         if(count>k){
            return "-1";//if highest value palindrome is not possible then return -1
        }
        if(count==0){
            diff = k
            for(int i=0;i<c;i++){
                if(diff==0||diff==1){
                    break;
                }
                if(Character.getNumericValue(s.charAt(i))!=9){
                    s = s.substring(0,i)+"9"+s.substring(i+1,num-i-1)+"9"+s.substring(num-i,num);
                    diff=diff-2; 
                }
            }
            if(diff==1&&n%2==1){
                    s = s.substring(0,n/2)+"9"+s.substring(n/2+1,n);
            }
            return s;
        } 
        if(count==k){
            for(int i=0;i<c;i++){
                if(Character.toString(s.charAt(i)).equals(Character.toString(s.charAt(num-i-1)))){          
                    continue;
                }
                else{
                    if(Character.getNumericValue(s.charAt(i))>Character.getNumericValue(s.charAt(num-1-i))){
                        s = s.substring(0,num-i-1)+Character.toString(s.charAt(i))+s.substring(num-i,num);
                    }
                    else
                        s = s.substring(0,i)+ Character.toString(s.charAt(num-i-1))+s.substring(i+1,num);   
                }
            }
            return s; 
        }
        else {
            diff = k - count;
            for(int i=0;i<c;i++){
                if(Character.toString(s.charAt(i)).equals(Character.toString(s.charAt(num-i-1)))){          
                    continue;
                }
                else{
                    if(diff!=0&&Character.getNumericValue(s.charAt(i))!=9&&Character.getNumericValue(s.charAt(num-1-i))!=9){
                        s = s.substring(0,i)+"9"+s.substring(i+1,num-i-1)+"9"+s.substring(num-i,num);
                        diff--;
                    }
                    else{
                        if(Character.getNumericValue(s.charAt(i))>Character.getNumericValue(s.charAt(num-1-i))){
                        s = s.substring(0,num-i-1)+Character.toString(s.charAt(i))+s.substring(num-i,num);
                        }
                        else
                            s = s.substring(0,i)+ Character.toString(s.charAt(num-i-1))+s.substring(i+1,num);
                        }                    
                }
            }
            if(diff==0){
                return s;
            }
            else{
                for(int i=0;i<c;i++){
                    if(diff==0||diff==1){
                        break;
                    }
                    if(Character.getNumericValue(s.charAt(i))!=9){
                        s = s.substring(0,i)+"9"+s.substring(i+1,num-i-1)+"9"+s.substring(num-i,num);
                        diff=diff-2; 
                    }
                }
                if(diff==1&&n%2==1){
                    s = s.substring(0,n/2)+"9"+s.substring(n/2+1,n);
                }
                return s;
            }
        }

        


    }
    public static void main(String[] args) {
        Solution check = new Solution();
        String a = new String();
        a = check.highestValuePalindrome("11119111",8,4);
        System.out.println(a);

    }
}