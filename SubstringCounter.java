/**
 * @author Helena Gray
 * @version 9.16.2018
 * 
 * This class counts and returns the number of occurences of a substring within another string.
 */
public class SubstringCounter implements Combiner<String, String, Integer>{
	
		/**
		 * @param operand1
		 *            is the string that will be checked for the presence of operand2
		 * @param operand2
		 *            is the substring whose presence will be checked for in the string operand1
		 *            DynamicArray
		 * @return returns the number of times operand2 is present in operand1
		 */
	public Integer combine(String operand1, String operand2){
		// O(NM) where N is the length of operand1 and M is the length of operand2
		  
		Integer count = 0;
		int check = operand1.length() - operand2.length();
		// count how many times operand2 occurs in operand1 as a substring
		for(int k=0; k<= check; k++){
			if(operand1.regionMatches(k,operand2,0,(operand2.length()-1))){
				count++;
			}
		}
		// return the count
		return count;
	}
	
	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------

	public static void main(String[] args){
		SubstringCounter sc = new SubstringCounter();
		if (sc.combine("aaaaaaaaa","aaa") == 7 && sc.combine("abab","ab") == 2 && sc.combine("aa","aab") == 0
			&& sc.combine("23232","232") == 2 
			&& sc.combine("helloabchelloddefzdfjhello","hello")==3) {
			System.out.println("Yay 1");
		}

	}
}