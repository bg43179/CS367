import java.io.*;

public class PrintFrequencies {
    public static void main(String[] args) {
	if(args.length != 1) {
	    System.out.println("Usage: java PrintFrequencies path_to_input_file");
	    System.exit(-1);
	}

        int[] frequencies = null;
        try {
            frequencies = 
                FrequencyParser.getFrequencies(args[0]);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

	for(int i = 0; i < 128; i++) {
	    if(i < 33) {
		System.out.format("%s: %d\n", Config.specialChars[i], frequencies[i]);
	    } else {
		System.out.format("%c: %d\n", (char) i, frequencies[i]);
	    }
	}
    }
}
