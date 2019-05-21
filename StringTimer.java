/**
 * @author Helena Gray
 * @version 9.16.2018
 * 
 * This class returns a string that is a second string repeated a specified number of times 
 * with no gaps in between.
 */
public class StringTimer implements Combiner<String, Integer, String> {

	/**
	 * @param operand1
	 *            is the string to be repeated
	 * @param operand2
	 *            is the number of times to repeat the string
	 *            DynamicArray
	 * @return returns the repeated string
	 */
	public String combine(String operand1, Integer operand2) {
		// O(NL) where N is the value of operand2 and L is the length of
		// operand1
		String str = "";
		// return a string as a repetition of the original string operand1
		// the number of repeats is specified by integer operand2
		for (int i = 0; i < operand2; i++) {
			str += operand1;
		}
		return str;
	}

	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------

	public static void main(String[] args) {
		StringTimer st = new StringTimer();
		if (st.combine("a", 1).equals("a") && st.combine("ab", 3).equals("ababab")
				&& st.combine("abc", -1).equals("")) {
			System.out.println("Yay 1");
		}

	}
}