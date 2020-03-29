package col106.assignment3.Heap;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;

public class HeapDriverCode {
	static PrintStream out;
	public PrintStream fileout() {
		return out;
	}
	public static void main(String[] args) throws IOException {
        Heap<Integer, Integer> heap = new Heap();
        File file;
        out = new PrintStream(new FileOutputStream("OUT_HEAP", false), true);
        System.setOut(out);
        if (args.length == 0) {
            URL url = HeapDriverCode.class.getResource("INP_HEAP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String key_value;
                int key;
                int value;
                switch (st.strip()) {
                    case "INSERT":
                        key_value = br.readLine();
                        key = Integer.parseInt(key_value.split(",")[0].trim());
                        value = Integer.parseInt(key_value.split(",")[1].trim());
                        System.out.println("Inserting: " + Integer.toString(key) + ", " + Integer.toString(value));
                        heap.insert(key, value);
                        break;
                    case "EXTRACT_MAX":
                        System.out.println("Extracting Max: ");
                        System.out.println(heap.extractMax());
                        break;
                    case "UPDATE":
                        key_value = br.readLine();
                        key = Integer.parseInt(key_value.split(",")[0].trim());
                        value = Integer.parseInt(key_value.split(",")[1].trim());
                        System.out.println("Updating Key " + Integer.toString(key) + " to value " + Integer.toString(value) + ":");
                        heap.increaseKey(key, value); 
                        break;
                    case "DELETE":
                        key_value = br.readLine();
                        key = Integer.parseInt(key_value.trim());
                        System.out.println("Deleting element with key " + Integer.toString(key) + ":");
                        heap.delete(key); 
                        break;
                    case "PRINT_HEAP":
                        System.out.println("Printing heap in level order:");
                        heap.printHeap();
                        break;
                    default:
                        System.err.println("Unknown command: " + st);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. " + file.getAbsolutePath());
        } catch (NullPointerException ne) {
            ne.printStackTrace();

        }
    }
}
