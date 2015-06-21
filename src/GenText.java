// Name:		Zhiyang Lu
// USC loginid:	zhiyangl
// CS 455 PA4
// Spring 2015

/**
 * GenText class
 * 
 * main class to read command line and create relevant class to generate random text
 * 
 * Run: java GenText [-d] prefixLength numWords sourceFile outFile
 * 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class GenText {
	
	public static final int WORDS_EACH_LINE = 80;

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		boolean debug = false;
		int prefixLength;
		int numWords;
		String sourceFile;
		String outFile;
		
		if(args.length == 4){
			debug = false;
		}else if(args.length == 5 && args[0].equals("-d")){
			debug = true;
		}else{
			System.out.println("Error: missing or too many command-line arguments");
			usage();
			System.exit(1);
		}

		checkcommandline(debug, args);

		int index = debug ? 1 : 0;
		prefixLength = Integer.parseInt(args[0 + index]);
		numWords = Integer.parseInt(args[1 + index]);
		sourceFile = args[2 + index];
		outFile = args[3 + index];
		
		String context = generate(prefixLength, numWords, sourceFile, debug);
		
		//for test
		//System.out.println(context);
		
		outputContext(context, outFile);
		
	}
	
	
	/**
	 * a function shows how to run the program with correct command line
	 */
	private static void usage(){
		System.out.println("Run: java GenText [-d] prefixLength numWords sourceFile outFile");		
	}
	
	/**
	 * check the command line validation
	 * @param debug: if is in debug mode
	 * @param args: command line arguments
	 */
	private static void checkcommandline(boolean debug, String[] args){
		int index = debug ? 1 : 0;
		try{
			int prefixLength = Integer.parseInt(args[0 + index]);
			int numWords = Integer.parseInt(args[1 + index]);
			if(numWords < 0){
				System.out.println("Error: numWords is less than 0");
				usage();
				System.exit(1);
			}
			if(prefixLength < 1){
				System.out.println("Error: prefixLength is less than 1");
				usage();
				System.exit(1);
			}
		}catch(NumberFormatException e){
			System.out.println("Error: prefixLength or numWords arguments are not integers");
			usage();
			System.exit(1);
		}
	}
	
	/**
	 * Read data from input file and create the RandomTextGenerator instance and generate the text.
	 * @param prefixLength
	 * @param numWords
	 * @param sourceFile
	 * @param debug
	 * @return 
	 */
	private static String generate(int prefixLength, int numWords, String sourceFile, boolean debug) {
		File inputfile;
		RandomTextGenerator generator;
		String context = "";
		try{
			inputfile = new File(sourceFile);
			Scanner in = new Scanner(inputfile);
			ArrayList<String> words = new ArrayList<String>();
			while(in.hasNext()){
				words.add(in.next());
			}
			in.close();
			
			if(prefixLength >= words.size()){
				System.out.println("Error: prefixLength >= number of words in sourceFile");
				System.exit(1);
			}
			
			generator = new RandomTextGenerator(words, prefixLength);
			context = generator.generateRandomText(numWords, debug);
			//System.out.println(context);
			
		}catch(FileNotFoundException e){
			System.out.println("Error: input file does not exist");
			System.exit(1);
		}
		return context;
	}
	
	/**
	 * write context string into outputFile with required format.
	 * @param context: String of the whole context
	 * @param outFile: File to write into
	 */
	public static void outputContext(String context, String outFile){
		String[] words = context.split("\\s+");
		try{
			PrintWriter out = new PrintWriter(outFile);
			
			for(int i = 0; i < 8; i++){
				out.print("1234567890");
			}
			out.println();
			out.println();
			
			int lineCounter = 0;
			for(int i = 0; i < words.length; i++){
				if(lineCounter == 0){
					lineCounter += words[i].length();
					out.print(words[i]);
				}else if(lineCounter + 1 + words[i].length() <= WORDS_EACH_LINE){
					lineCounter += words[i].length() + 1;
					out.print(" " + words[i]);
				}else{
					out.println();
					lineCounter = 0;
					i--;
				}
			}
			
			out.close();
		}catch(FileNotFoundException e){
			System.out.println("Error: can't write to output file");
			System.exit(1);
		}
	}
	
	
}
