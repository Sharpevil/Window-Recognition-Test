package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class CmdTest {

	public static void main(String[] args) {
		
	}
	
	public static void simpleTestAndPrint() {
		List<String> commands = new ArrayList<String>();
        commands.add("ping");
        System.out.println(commands);

        //Run macro on target
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(new File("/home/documents"));
        pb.redirectErrorStream(true);
        Process process;
		try {
			process = pb.start();
			
			//Read output
			StringBuilder out = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null, previous = null;
			while ((line = br.readLine()) != null)
				if (!line.equals(previous)) {
					previous = line;
					out.append(line).append('\n');
					System.out.println(line);
				}
			
			//Check result
			if (process.waitFor() == 0) {
				System.out.println("Success!");
				System.exit(0);
			}
			
			//Abnormal termination: Log command parameters and output and throw ExecutionException
			System.err.println(commands);
			System.err.println(out.toString());
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
