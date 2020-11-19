package challenge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
* This class StringManipulator reads data from Challenge_tests.CSV files      
*  and writes the manipulated strings (Sort, hasUniqueChars, Clean) to Challenge_sorted.CSV 
* 
*/
public class StringManipulator {

		public static  void main(String[] args) {

		StringManipulator stringManipulator = new StringManipulator();
      
		String csvFile = "src/atdd/challenge_tests.csv";
        BufferedReader reader = null;
        String line = "";
        String word = "";
        
        //list to store the words of file
        List<String> data = new ArrayList<String>();
        
        try {

            reader = new BufferedReader(new FileReader(csvFile));
            
            //this skips the first header line  
            reader.readLine();
            
            
            while ((line = reader.readLine()) != null) {
               if(!line.trim().isEmpty())
            	    word = line;
                    data.add(line);
                    System.out.println("Has Unique Character in the String:" + stringManipulator.hasUniqueChars(word));
            }
	
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Close buffer reader
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// Sort words if list is not empty
		if (!data.isEmpty()) {
			String[] arr = new String[data.size()];
			stringManipulator.sortStrings(data.toArray(arr));
		}
	}
	
   /**
	* Removes all non-alphabetic characters from a string and converts all
	* characters to upper case.
	* 
	* @param str
	*            - String to clean
	* @return Cleaned string in upper case.
	* 
	* */
	public String cleanString(String str) {
		
		if(str == null || str.isEmpty())
			return str;
			
			//This REGEX replaces all non alphabetic char to "" and converts string to UpperCase
			str = str.replaceAll("[^a-zA-Z]", "").toUpperCase();
		
		return str;
	}
	
	
	/**
	 * this functions checks the string has unique character or not.
	 * if not then return false else true.
	 * 
	 * @param word
	 *           
	 * @return Boolean value which indicates string has unique character or not 
	 */
	public boolean hasUniqueChars(String word) {
		
		if (null == word || word.trim().isEmpty())
			return false;
		
		word =  cleanString(word);
		
		if(word.length() == 1) {
			 return true;
		}
		
		//this for loop compares the values against each char in the string 
		//if there is a matching char then it returns false.
		for (int i = 1; i < word.length(); i++) {
			if (word.charAt(i) == word.charAt(i-1)) 
				return false; 
		}
              
        // If no duplicate characters encountered, 
        // return true 
        return true; 
	}
	
	/**
	 * this function Get the average weight of passed string. Weight is numeric value that is the
	 * average of each char ASCII value contained in the string.
	 * 
	 * @param word
	 *           
	 * @return Average weight of the string and the value is returns in Double DataType
	 */
	public double getWeight(String word) {
		
		if (null == word || word.trim().isEmpty())
			return 0;
		
		word = cleanString(word);
		
		double weight = 0;
		for (char c : word.toCharArray()) {
			weight += (int) c;
		}
		
		return weight / word.length();
		
	}
	
	
	/**
	 * This function Sort the Strings as per weight if array is not null and size of array is empty
	 * and it also writes the sorted and Cleaned strings to the file. 
	 * 
	 * @param words
	 *            
	 */
	public void sortStrings(String[ ] words){
		
		if (words == null || words.length == 0)
			return;

		// Cleans the string and assigns back to the Array
		for (int i = 0; i < words.length; i++) {
			if (words[i] != null) {
				words[i] = cleanString(words[i]);
			}
		}
		
		//Tree map sorts the keys in the ascending order 
		TreeMap<Double, List<String>> sorted = new TreeMap< Double, List<String>>();
		
		Double weight = null;

		for(int i = 0; i < words.length; i++) {			
			weight = getWeight(words[i]);
			if (sorted.get(weight) != null)
			 sorted.get(weight).add(words[i]);
			else {
				List<String> list = new ArrayList<String>();
				list.add(words[i]);
				sorted.put(weight, list);
			}
		}
		
		System.out.println(sorted.keySet());
		System.out.println(sorted.values());
			    
		String FILENAME = "src/atdd/challenge_sorted.csv";
		BufferedWriter bwriter = null;
		FileWriter fwriter = null;

		try {
			
			fwriter = new FileWriter(FILENAME);
			bwriter = new BufferedWriter(fwriter);
			bwriter.write("Words,Unique,Weight\n");
				
			//iterates the sorted tree maps for every entry,
			//it writes and print the data to the file
			for (Map.Entry<Double,List<String>> entry : sorted.entrySet()) {
				Double wordWeight = entry.getKey();
				List<String> sortedwords = entry.getValue();
				for (String str : sortedwords) {
					String outputString = str + "," + ((hasUniqueChars(str) ? "TRUE" : "FALSE")) + "," + wordWeight + "\n";
					System.out.print(outputString);
					bwriter.write(outputString);
				}
			}     
 
		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {

				if (bwriter != null)
					bwriter.close();

				if (fwriter != null)
					fwriter.close();

			} catch (IOException ex) {

				ex.printStackTrace();

			}

		}

	}
}

