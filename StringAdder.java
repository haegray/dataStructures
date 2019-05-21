/**
 * A String combiner for concatenation. 
 * <p>
 * @author CS310 GMU
 */

public class StringAdder implements Combiner<String, String, String>{
	/**
	 *  Concatenate two strings and return the combined string.
	 *  @param operand1 first string
	 *  @param operand2 second string
	 *  @return string concatenation with one space padded in between
	 */

	public String combine(String operand1, String operand2){
		return operand1 + " " + operand2;
	}
	
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */
	 public static void main(String[] args){
		StringAdder adder = new StringAdder();
		System.out.println(adder.combine("george", "mason"));
	}

	
}