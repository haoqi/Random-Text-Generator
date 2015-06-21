// Name:		Zhiyang Lu
// USC loginid:	zhiyangl
// CS 455 PA4
// Spring 2015

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * RandomTextGenerator class
 * 
 * A class stores the information of prefix-successors from an article.
 * Contain function to generate random text.
 *
 */
public class RandomTextGenerator {

	private Map<Prefix, ArrayList<String>> prefix_map;
	private ArrayList<String> words;
	private int prefixLength;
	private Random rand;
	
	/**
	 * Read from the ArrayList which represents the article and initialize the 
	 * parameters in the class including the Map from Prefix to successors.
	 * 
	 * @param words : An ArrayList that contains all the words of an article
	 * @param prefixLength : the prefix length that will be use in an instance
	 */
	public RandomTextGenerator(ArrayList<String> words, int prefixLength){
		prefix_map = new HashMap<Prefix, ArrayList<String>>();
		this.words = new ArrayList<String>(words);
		this.prefixLength = prefixLength;
		rand = new Random();
		
		generatePrefix();
	}
	
	/**
	 * initiate the prefix map
	 */
	private void generatePrefix(){
		for(int i = 0; i  + prefixLength <= words.size(); i++){
			String pre = "";
			for(int p = 0; p < prefixLength - 1; p++){
				pre =  pre + words.get(i+p) + " ";
			}
			pre += words.get(i + prefixLength - 1);

			Prefix prefix = new Prefix(pre);
			if(prefix_map.containsKey(prefix) && i < words.size() - prefixLength){
				prefix_map.get(prefix).add(words.get(i + prefixLength));
			}else{
				if(!prefix_map.containsKey(prefix)){
					prefix_map.put(prefix, new ArrayList<String>());					
				}
				if(i < words.size() - prefixLength){
					prefix_map.get(prefix).add(words.get(i + prefixLength));					
				}
			}
		}	
	}
	
	
	/**
	 * 
	 * @param numWords: the number of words need to generate
	 * @param debug: true if is generated under debug mode
	 * @return return the randomly generated text based on this RandomTextGenerator
	 */
	public String generateRandomText(int numWords, boolean debug){
		String text = "";
		Prefix current_prefix = getInitialPrefix();
		if(debug){
			System.out.println("DEBUG: chose a new initial prefix: " + current_prefix.toString());
		}
		String nextword = "";
		for(int i = 0; i < numWords; i++){
			ArrayList<String> successors = prefix_map.get(current_prefix);
			if(successors.size() <= 0){						//The prefix has no successor
				if(debug){
					System.out.println("DEBUG: prefix: " + current_prefix.toString());
					System.out.println("DEBUG: successors: <END OF FILE>");
				}
				current_prefix = getInitialPrefix();
				if(debug){
					System.out.println("DEBUG: chose a new initial prefix: " + current_prefix.toString());
				}
				i--;
				continue;
			}else{											
				int random_index = rand.nextInt(successors.size());
				nextword = successors.get(random_index);
				if(debug){
					System.out.println("DEBUG: prefix: " + current_prefix.toString());
					System.out.println("DEBUG: successors: " + SuccessorToString(successors));
					System.out.println("DEBUG: word generated: " + nextword);
				}
				current_prefix = getNextPrefix(current_prefix, nextword);
				text = text + nextword + " ";
			}
		}
		return text;
	}
	
	
	/**
	 * return the next prefix with previous prefix and its successor
	 * @word
	 * @return
	 */
	private Prefix getNextPrefix(Prefix prefix, String word){
		String nextPrefix = prefix.getSubPrefix() + word;
		return new Prefix(nextPrefix);
	}
	
	/**
	 * find and return a random prefix in the generator
	 * @return
	 */
	private Prefix getInitialPrefix(){
		int random = rand.nextInt(prefix_map.keySet().size());
		for(Prefix init : prefix_map.keySet()){
			if(random == 0) return init;
			else random--;
		}		
		return null;
	}
	
	/**
	 * used for debug mode, return the successor string of a given Prefix
	 * @param prefix
	 * @return
	 */
	private String SuccessorToString(ArrayList<String> list){
		String successors = "";
		for(int i = 0; i < list.size() - 1; i++){
			successors += list.get(i) + " ";
		}
		successors += list.get(list.size() - 1);
		return successors;
	}

}
