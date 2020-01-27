import java.util.Scanner;
import java.util.Arrays;
public class MyCalculator{
	MyStack<Integer> store = new MyStack<>();
	public MyCalculator(){
		MyStack<Integer> store = new MyStack<>();
	}
	public int calculate(String expression){
		int result=0;
		String[] x = infixtopostfix(expression);
		try{	
			for(int i=0;i<x.length;i++){
				if (isNumeric(x[i])==true) {
					int d = Integer.parseInt(x[i]);
					store.push(d);
				}
				else{
					int a = store.pop(); 
					int b = store.pop(); 
					char c = x[i].charAt(0);
					store.push(operate(b,c,a));
				}
			}
			result = store.pop();
		}
		catch(EmptyStackException e){
			System.out.println("Error");
		}
		return result;
	}
	public String[] infixtopostfix(String expression){
		int i=0;
		expression = expression.replaceAll("\\s", "");
		expression="("+expression+")"; 
		String[] rs = new String[expression.length()];
		MyStack<Character> inp = new MyStack<>();
		
		try{
			while(i<expression.length()){
				if (isNumeric(Character.toString(expression.charAt(i)))==true) {
					String num = "";
					while(Character.isDigit(expression.charAt(i))==true){
						num+=Character.toString(expression.charAt(i));
						i++;
					}
					rs[nul(rs)]=num;
				}
				else if (expression.charAt(i)=='(') {
					inp.push(expression.charAt(i));
					i++;
				}
				else if (expression.charAt(i)==')') {
					while(inp.isEmpty()!=true && inp.top()!='('){
						rs[nul(rs)]=Character.toString(inp.pop());
					}
					inp.pop();
					i++;
				}
				else{
					while(inp.isEmpty()!=true && precedence(expression.charAt(i))<=precedence(inp.top())){
						rs[nul(rs)]=Character.toString(inp.pop());
					}
					inp.push(expression.charAt(i));
					i++;
				}
			}
			while (inp.isEmpty()!=true){
				rs[nul(rs)]=Character.toString(inp.pop());
			}

		}
		catch(EmptyStackException e){
			System.out.println("Error");
		}
		int k = 0;
		while(rs[k]!=null){
			k++;
		}
		String[] fin = new String[k];
		for (int j =0;j<k ;j++ ) {
			fin[j]=rs[j];
		}
		return fin;
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
	public int operate(int a, char operand, int b){
		if (operand=='+'){
			return a + b;
		}
		else if (operand=='-'){
			return a - b;
		}
		else if(operand=='*'){
			return a * b;
		}
		else{
			return 0;
		}
	}
	public int precedence(char op){
		if (op=='+' || op=='-' ){
			return 1;
		}
		else if (op=='*'){
			return 2;
		}
		else{
			return 0;
		}
	}
	public static boolean isNumeric(String strNum){
    	if (strNum==null){
        	return false;
    	}
    	try {
        	int d = Integer.parseInt(strNum);
    	}
    	catch (NumberFormatException nfe){
        	return false;
    	}
    	//System.out.println("Hi");
    	return true;
	}
	public static void main(String[] args) {
		MyCalculator check = new MyCalculator();
		/*String[] m = check.infixtopostfix("(1+4+5+2-3+6+18)");
		for (int i = 0; i < m.length ;i++ ) {
			System.out.println(m[i]+" ");
		}*/
		System.out.println(check.calculate("1+4+5+2-3+6+18"));
	}
}


/*int i=0;
		char[] exp=expression.toCharArray();
		int n=exp.length;
		try{
			while(i<n){
				if (exp[i]==' ') {
					i++;
					continue;
				}
				else if (exp[i]>='0' && exp[i]<='9') {
					String num="";
					while(i<n && exp[i]>='0' && exp[i]<='9'){
						num+=Character.toString(exp[i]);
						i++;
					}
					store.push(num);
				}
				else if(exp[i]=='+'||exp[i]=='-'||exp[i]=='*'){
					store.push(Character.toString(exp[i]));
					i++;
				}
				else if (exp[i]==')'){
					String s ="";
					while(store.top()!="("){
						s+=store.pop();
					}
					store.pop();
					store.push(eval(s));
					i++;
				}
			}
			String fnl = "";
			while(store.isEmpty()!=true){
				fnl+=store.pop();
			}
			result = Integer.parseInt(eval(fnl));
			}
		catch(EmptyStackException e){
			System.out.println("Error");
		}
		return(result);
	public String eval(String s){
		int r = 0;
		MyStack<String> evaluate = new MyStack<String>();
		int i=0;
		while (i<s.length()){
				String nm = "";
				while(i<s.length() && s.charAt(i)>='0' && s.charAt(i)<='9'){
					nm+=Character.toString(s.charAt(i));
					i++;
				}
				if (s.charAt(i)=='+' || s.charAt(i)=='-') {
					evaluate.push(nm);
					evaluate.push(Character.toString(s.charAt(i)));
					i++;
				}
				else{
					int p = 1;
					int j = Integer.parseInt(nm);
					p*=j;
					while(s.charAt(i)!='+'&&s.charAt(i)!='-'){
						if (s.charAt(i)=='*') {
							i++;
						}
						else{
							String ns= "";
							while(i<s.length() && s.charAt(i)>='0' && s.charAt(i)<='9'){
								ns+=Character.toString(s.charAt(i));
								i++;
							}
							p*=Integer.parseInt(ns);
						}
					}evaluate.push(Integer.toString(p));
					evaluate.push(Character.toString(s.charAt(i)));
					i++;
				}

			}
		try{
			if (evaluate.size()==1) {
			r = Integer.parseInt(evaluate.top());
			}
			else{
				while(evaluate.isEmpty()!=true){
					r+=Integer.parseInt(evaluate.pop());
					if (evaluate.pop()=="+") {
						r+=Integer.parseInt(evaluate.pop());
					}
					else{
						evaluate.pop();
						r-=Integer.parseInt(evaluate.pop());
					}
				}
			}
		}
		catch(EmptyStackException e){
			System.out.println("Error");
		}
		return Integer.toString(r);
	}*/
