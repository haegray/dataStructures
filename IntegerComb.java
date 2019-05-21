/**
 * An abstract class that implements Combiner with Integer operands and Integer result. 
 * @author CS310 GMU
 */
 
abstract class IntegerComb implements Combiner<Integer,Integer,Integer> { }

/**
 * An integer combiner for addition operation. 
 * <p>
 * @author CS310 GMU
 */

class IntegerAdder extends IntegerComb{

	/**
	 *  Add two integers and return the total.
	 *  @param operand1 first integer to add
	 *  @param operand2 second integer to add
	 *  @return total of the two  operands
	 */

	@Override
	public Integer combine(Integer operand1, Integer operand2){
		return operand1 + operand2;
	}
	
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */

	public static void main(String[] args){
		IntegerAdder it = new IntegerAdder();
		System.out.println(it.combine(3,5));
	}
}

/**
 * An integer combiner for multiplication operation. 
 * <p>
 * @author CS310 GMU
 */
 
 class IntegerTimer extends IntegerComb{
	/**
	 *  Multiply two integers and return the product.
	 *  @param operand1 first integer as the multiplicand
	 *  @param operand2 second integer as the multiplier
	 *	@return product of the two operands
	 */
	@Override
	public Integer combine(Integer operand1, Integer operand2){
		return operand1 * operand2;
	}

	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */

	public static void main(String[] args){
		IntegerTimer it = new IntegerTimer();
		System.out.println(it.combine(3,5));
	}
	

}