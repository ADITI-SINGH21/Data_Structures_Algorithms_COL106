package col106.assignment4.HashMap;
import java.util.Vector;
public class HashMap<V> implements HashMapInterface<V> {
	public int count;
	public Entry[] hash;
	public HashMap(int size) {
		// write your code here
		count = size;
		hash = new Entry[count];
	}
	public V put(String key, V value){
		// write your code here
		if(!contains(key)){
			int n = hashcode(key);
			while(true){
				if (hash[n]==null) break;
				n=(n+1)%count;			
			}
			hash[n] = new Entry();
			hash[n].key = key;
			hash[n].value = value;
			return null;
		}
		V value1 = get(key);
		int k = search(key);
		hash[k].value = value;
		return value1;
	}

	public int hashcode(String key){
		int fx = 0;
		int n = key.length(); 
		fx = ((int)key.charAt(n-1))%count;
		for (int i=0; i<n-1 ;i++ ) {
			fx = (((int)key.charAt(n-2-i))+((41*fx)%count))%count;
		}
		return fx;
	}

	public int search(String key){
		int i = hashcode(key);
		while(true){
			if(hash[i]==null) break;
			if(hash[i].key.equals(key)) break;
			i=(i+1)%count;
		}
		return i;
	}

	public V get(String key){
		// write your code here
		int k = hashcode(key);
		int i = k;
		while(true){
			if(hash[i]==null)break;
			if (hash[i].key.equals(key)) return (V)hash[i].value;
			i=(i+1)%count;
		}
		return null;
	}

	public int length(){
		return count;
	}

	public boolean remove(String key){
		// write your code here
		if (!contains(key)) {
			return false;
		}
		int k = search(key);
		hash[k] = null;
		int i = (k+1)%count;
		while(true) {
			if(hash[i]==null) break;
			String k1 = hash[i].key;
			V v = (V)hash[i].value;
			hash[i] = null;
			put(k1,v);
			i=(i+1)%count;
		}
		
		return true;	
	}

	public boolean contains(String key){
		// write your code here
		int k = hashcode(key);
		int i = k;
		while(true) {
			if(hash[i]==null) break;
			if (hash[i].key.equals(key)) return true;
			i=(i+1)%count;
		}
		return false;
	}

	public Vector<String> getKeysInOrder(){
		// write your code here
		Vector<String> v1 = new Vector<>();
		for (int i=0 ;i<count ;i++ ) {
			if(hash[i]==null) continue;
			v1.add(hash[i].key);
		}
		return v1;
	}
}

class Entry<V>{
	String key;
	V value;
}
