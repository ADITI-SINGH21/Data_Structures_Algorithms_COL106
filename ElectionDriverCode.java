package col106.assignment3.Election;

import java.io.*;
import java.net.URL;

public class ElectionDriverCode {
	static PrintStream out;
	public PrintStream fileout() {
		return out;
	}
    public static void main(String[] args) throws IOException {
        Election election = new Election();
        File file;
        out = new PrintStream(new FileOutputStream("OUT", false), true);
        System.setOut(out);
        if (args.length == 0) {
            URL url = ElectionDriverCode.class.getResource("INP");
            file = new File(url.getPath());
        } else {
            file = new File(args[0]);
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            System.err.println("Input file Not found. "+file.getAbsolutePath());
        }

        String st;
        while (true) {
            try {
                if ((st = br.readLine()) == null) break;
                String[] cmd = st.split(" ");
                if (cmd.length == 0) {
                    System.err.println("Error parsing: " + st);
                    return;
                }
                String[] str;
                switch (st.trim()) {
                    case "INSERT":
                        String[] info = br.readLine().split(",");
                        System.out.println("Inserting: " + info[0].trim() + ", " + info[1].trim() + ", " + info[2].trim() + ", " + info[3].trim() + ", " + info[4].trim()  + ", " + info[5].trim()  + ", " + info[6].trim());
                        election.insert(info[0].trim(), info[1].trim(), info[2].trim(), info[3].trim(), info[4].trim(), info[5].trim(),info[6].trim()); //TO Do
                        break;

                    case "UPDATE":
		    			String[] update_term = br.readLine().split(",");
                        System.out.println("Updating "+ update_term[0].trim()+ ", " + update_term[1].trim()+ ", " + update_term[2].trim()+ ":"); //name, candID, votes
                        election.updateVote(update_term[0].trim(), update_term[1].trim(), update_term[2].trim());
                        break;

				    case "TOP k IN CONSTITUENCY":
						str = br.readLine().split(",");
						System.out.println("Reporting Top "+str[1].trim()+" in constituency " + str[0].trim()+ ":");
						election.topkInConstituency(str[0].trim(), str[1].trim());
						break;
				    case "LEADING PARTY IN STATE":
                        str = br.readLine().split(",");
                        System.out.println("Reporting the leading party in state " + str[0].trim()+ ":");
                        election.leadingPartyInState(str[0].trim());
                        break;
			    	case "DISCARD ALL VOTES IN CONSTITUENCY":
                        str = br.readLine().split(",");
                        System.out.println("Discarding the info of all candidates in constituency " + str[0].trim()+":");
                        election.cancelVoteConstituency(str[0].trim());
                        break;
			    	case "GLOBAL LEADING PARTY":
                        System.out.println("Reporting leading party across all constituencies:");
                        election.leadingPartyOverall();
                        break;
			    	case "VOTE SHARES":
                        str = br.readLine().split(",");
                        System.out.println("Reporting vote share of party " + str[0].trim() + " in state " + str[1].trim() + ":");
                        election.voteShareInState(str[0].trim(),str[1].trim());
                        break;
			    	case "PRINT":
			    		System.out.println("Printing Election data:");
			    		election.printElectionLevelOrder();
			    		break;
			    	default:
                        System.err.println("Unknown command: " + st);
                }
            } catch (IOException e) {
                System.err.println("File Not Found");
            }
        }
    }

}
