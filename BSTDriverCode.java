package col106.assignment3.BST;

import java.io.*;
import java.net.URL;

public class BSTDriverCode {
	static PrintStream out;
	public PrintStream fileout() {
		return out;
	}
    public static void main(String[] args) throws IOException {
        BST<Integer, Integer> bst = new BST();
        File file;
        out = new PrintStream(new FileOutputStream("OUT_BST", false), true);
        System.setOut(out);
        if (args.length == 0) {
            URL url = BSTDriverCode.class.getResource("INP_BST");
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
                        bst.insert(key, value);
                        break;
                    case "UPDATE":
                        key_value = br.readLine();
                        key = Integer.parseInt(key_value.split(",")[0].trim());
                        value = Integer.parseInt(key_value.split(",")[1].trim());
                        System.out.println("Updating key " + Integer.toString(key) + " to value " + Integer.toString(value) + ":");
                        bst.update(key, value); 
                        break;
                    case "DELETE":
                        key_value = br.readLine();
                        key = Integer.parseInt(key_value.trim());
                        System.out.println("Deleting element with key " + Integer.toString(key) + ":");
                        bst.delete(key); 
                        break;
                    case "PRINT_BST":
                        System.out.println("Printing BST in level order:");
                        bst.printBST();
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
