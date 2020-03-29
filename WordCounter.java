package col106.assignment4.HashMap;

public class WordCounter {
	public int c=0;
	public WordCounter(){
		// write your code here
		c=0;
	}

	public int count(String str, String word){
		// write your code here
		int size = str.length()-word.length()+1;
		HashMap<Integer> wordcount = new HashMap<>(1000000);
		String[] array = new String[size];
		//int b = 0;
		for (int i=0;i<size;i++) {
			array[i] = str.substring(i,i+word.length());
			//if (array[i].equals(word)) b++;
				
		}
		c=0;
		for (int i=0;i<size;i++ ) {
		 	if (wordcount.contains(array[i])){
		 		wordcount.put(array[i],wordcount.get(array[i])+1);
		 	}
		 	else
		 		wordcount.put(array[i],1);
		}
		if(wordcount.get(word)==null) return 0;
		c = wordcount.get(word);
		return c; 
	}
}
	
/*for (int i=0;i<size;i++ ) {
		 	if (wordcount.contains(array[i])){
		 		wordcount.put(array[i],wordcount.get(array[i]);+1);
		 	}
		 	else
		 		wordcount.put(array[i],1);
		}
		if(wordcount.get(word)==null) return 0;
		c = wordcount.get(word);*/