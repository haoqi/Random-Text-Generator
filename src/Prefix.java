
public class Prefix {

	private String prefix;
	
	public Prefix(String prefix){
		this.prefix = prefix;
	}
	
	public String toString(){
		return prefix;
	}
	
	/**
	 * return the SubPrefix that exclude the first word in this prefix
	 * @return
	 */
	public String getSubPrefix(){
		String SubPrefix = "";
		String[] words = prefix.split("\\s+");
		for(int i = 1; i < words.length; i++){
			SubPrefix = SubPrefix + words[i] + " ";
		}
		return SubPrefix;
	}
		
	/**
	 * Override equals to determine duplicate Prefix Class
	 */
	public boolean equals(Object obj) {

		Prefix other_prefix = (Prefix)obj;
		return this.prefix.equals(other_prefix.prefix);
	}
	
	/**
	 * Override hashCode()
	 */
	public int hashCode(){
		return prefix.hashCode();
	}

}
