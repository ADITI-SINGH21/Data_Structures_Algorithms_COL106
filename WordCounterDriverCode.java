package col106.assignment4.HashMap;
import java.io.*;

public class WordCounterDriverCode{
	static PrintStream out;
	public PrintStream fileout() {
		return out;
	}
    public static void main(String[] args) throws IOException {

        File file;
        out = new PrintStream(new FileOutputStream(args[1], false), true);
        System.setOut(out);
		file = new File(args[0]);

		WordCounter wc = new WordCounter();

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st = br.readLine().trim();
			int testCases = Integer.parseInt(st);

			String str, word;
			for(int tc=0;tc<testCases;tc++){
				st = br.readLine();
				str = st.split(" ")[0].trim();
				word = st.split(" ")[1].trim();
				System.out.println(wc.count(str, word));
			}

        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + file.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();
		}
	}
}
