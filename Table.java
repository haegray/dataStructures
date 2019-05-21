/**
 * @author Helena Gray
 * @version 9.16.2018
 * 
 * This class creates a dynamic grid that will grow or shrink depending on grid contents, capacity, and input.
 * This dynamic grid is special because it has a header row and column that are dynamic arrays and can change all 
 * of the contents of its cells based on what operation is being used.
 */
public class Table<RowType, ColType, CellType, OpType extends Combiner<RowType, ColType, CellType>> {

	private DynamicArray<RowType> rowHead; // rowHead as a list of RowType
											// values
	private DynamicArray<ColType> colHead; // colHead as a list of ColType
											// values
	private DynamicGrid<CellType> board; // a 2-D grid of CellType values
											// determined by rowHead, colHead,
											// and op
	private OpType op; // op that defines a function f: f(RowType,ColType)->
						// CellType

	/**
	 * @param op
	 *            op is the operator used to calculate the values for the cells
	 *            in the table
	 */
	public Table(OpType op) {
		// constructor
		// create an table of empty rowHead and colHead, board of 0 rows and 0
		// cols
		rowHead = new DynamicArray();
		colHead = new DynamicArray();
		board = new DynamicGrid();
		// set the operator
		this.op = op;

	}

	/**
	 * @return returns the the number of rows in the board
	 */

	public int getSizeRow() {
		// O(1)
		// report the number of rows in board
		return board.getNumRow();
	}

	/**
	 * @return returns the the number of columns in the board
	 */

	public int getSizeCol() {
		// O(1)
		// report the number of columns in board
		return board.getNumCol();

	}

	/**
	 * @param r
	 *            the index of the row
	 * @return returns the row label of row r
	 */

	public RowType getRowHead(int r) {
		// O(1)
		// return the item at index r from rowHead
		// get method throws IndexOutOfBoundsException for invalid index
		return rowHead.get(r);
	}

	/**
	 * @param c
	 *            the index of the column
	 * @return returns the column label of column r
	 */

	public ColType getColHead(int c) {
		// O(1)
		// return the item at index c from colHead
		// get method throws IndexOutOfBoundsException for invalid index
		return colHead.get(c);
	}

	/**
	 * @param r
	 *            the index of the row
	 * @param c
	 *            the index of the column
	 * @return returns the contents of the cell at row r and column c
	 */

	public CellType getCell(int r, int c) {
		// O(1)
		// return the cell at row r, column c from board
		// get method throws IndexOutOfBoundsException for invalid index
		return board.get(r, c);
	}

	/**
	 * @param op
	 *            op is the operator used to calculate the values for the cells
	 *            in the table
	 */

	public void setOp(OpType op) {
		// O(CR) where C is the number of columns and R is the number of rows of
		// the grid
		// change the operation
		this.op = op;
		// re-calculate and reset the cells of the board
		for (int i = 0; i < this.getSizeRow(); i++) {
			for (int k = 0; k < this.getSizeCol(); k++) {
				board.set(i, k, op.combine(this.getRowHead(i), this.getColHead(k)));
			}
		}
	}

	/**
	 * @param i
	 *            the index that indicates where to insert the row and row head
	 * @param v
	 *            the row label to insert in the row head
	 * @return returns true if row is able to be added
	 */
	public boolean addRow(int i, RowType v) {
		// O(C+R) where R is the number of rows of the grid and
		// C is the number of columns of the grid
		// i may be equal to the size (indicating that you are appending a row)
		// calculate the new row based on v, existing colHead and op
		// insert a new row to the grid at row index i
		if (colHead.size() != 0) {
			if (rowHead.size() == 0) {
				// O(C)
				for (int m = 0; m < colHead.size(); m++) {
					board.set(i, m, op.combine(v, this.getColHead(m)));
				}
			} else {
				DynamicArray tempArr = new DynamicArray(this.getSizeRow());
				// O(C)
				for (int m = 0; m < colHead.size(); m++) {
					tempArr.add(op.combine(v, this.getColHead(m)));
				}
				// O(C+R)
				board.addRow(i, tempArr);
			}
		} else {
			// O(C+R)
			DynamicArray tempArr = new DynamicArray();
			tempArr.add(v);
			board.addRow(i, tempArr);
		}
		// insert v to rowHead at index i
		rowHead.add(i, v);
		return true;
	}

	/**
	 * @param i
	 *            the index that indicates where to insert the column and the
	 *            column head
	 * @param v
	 *            the column label to insert in the column head
	 * @return returns true if column is able to be added
	 */
	public boolean addCol(int i, ColType v) {
		// O(CR) where R is the number of rows of the grid and
		// C is the number of columns of the grid
		// i may be equal to the size (indicating that you are appending a
		// column)
		// calculate the new column based on v, existing rowHead and op
		// insert a new column to the grid at column index i
		if (rowHead.size() != 0) {
			if (colHead.size() == 0) {
				for (int m = 0; m < rowHead.size(); m++) {
					// O(C)
					board.set(m, i, op.combine(this.getRowHead(m), v));
				}
			} else {
				DynamicArray tempArr = new DynamicArray();
				// O(C)
				for (int m = 0; m < rowHead.size(); m++) {
					tempArr.add(op.combine(this.getRowHead(m), v));
				}
				// O(RC)
				board.addCol(i, tempArr);
			}
		} else {
			// O(RC)
			DynamicArray tempArr = new DynamicArray();
			tempArr.add(v);
			board.addCol(i, tempArr);
		}
		// insert v to colHead at index i
		colHead.add(i, v);
		return true;
	}

	/**
	 * @param i
	 *            the index that indicates which row to remove
	 * @throws IndexOutOfBoundsException
	 *             if index is less than 0 or greater than number of rows
	 * @return returns old value of row head of removed row
	 */
	public RowType removeRow(int i) {
		// O(R) where R is the number of rows of the grid
		if (i < 0 || i >= this.getSizeRow()) {
			// throws IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			// remove and return value from rowHead at index i
			RowType oldValue = rowHead.remove(i);
			// also remove row i from grid
			board.removeRow(i);
			return oldValue;
		}
	}

	/**
	 * @param i
	 *            the index that indicates which column to remove
	 * @throws IndexOutOfBoundsException
	 *             if index is less than 0 or greater than number of columns
	 * @return returns old value of column head of removed column
	 */
	public ColType removeCol(int i) {
		// O(CR) where R is the number of rows and
		// C is the number of columns of the grid
		if (i < 0 || i >= this.getSizeCol()) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			// remove and return value from colHead at index i
			ColType oldValue = this.colHead.remove(i);
			// also remove column i from grid
			board.removeCol(i);
			return oldValue;
		}
	}

	/**
	 * @param i
	 *            the index that indicates which row and row head to replace
	 * @param v
	 *            the row label to insert in the row head
	 * @throws IndexOutOfBoundsException
	 *             if index is less than 0 or greater than number of rows
	 * @return returns old value of row head of replaced row
	 */
	public RowType setRow(int i, RowType v) {
		// O(C) where C is the number of columns of the grid
		if (i < 0 || i >= this.getSizeRow()) {
			// throws IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			RowType oldValue = rowHead.get(i);
			// change value of rowHead at index i to be v
			rowHead.set(i, v);
			// also change the ith row of grid using v, the ColTypes, and op
			for (int m = 0; m < this.getSizeCol(); m++) {
				board.set(i, m, op.combine(v, this.getColHead(m)));
			}
			// return old value of rowHead from index i
			return oldValue;
		}
	}

	/**
	 * @param i
	 *            the index that indicates which column and column head to
	 *            replace
	 * @param v
	 *            the column label to insert in the column head
	 * @throws IndexOutOfBoundsException
	 *             if index is less than 0 or greater than number of columns
	 * @return returns old value of column head of column row
	 */
	public ColType setCol(int i, ColType v) {
		// O(R) where R is the number of rows of the grid
		if (i < 0 || i >= this.getSizeCol()) {
			// throws IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			ColType oldValue = colHead.get(i);
			// change value of colHead at index i to be v
			colHead.set(i, v);
			// also change the ith column of grid using v, the RowTypes, and op
			for (int m = 0; m < this.getSizeRow(); m++) {
				board.set(m, i, op.combine(this.getRowHead(m), v));
			}
			// return old value of colHead from index i
			return oldValue;
		}
	}

	// --------------------------------------------------------
	// PROVIDED for you to help with testing
	// DO NOT CHANGE CODE!
	// More testing code you can change further down...
	// --------------------------------------------------------

	/**
	 * Find the width we should use to print the specified column
	 * 
	 * @param colIndex
	 *            column index to specify which column of the grid to check
	 *            width
	 * @return an integer to be used to for formatted printing of the column
	 */

	private int getColMaxWidth(int colIndex) {
		int ans = -1;
		for (int i = 0; i < this.getSizeRow(); i++) {
			int width = (this.getCell(i, colIndex)).toString().length();
			if (ans < width)
				ans = width;
		}
		return ans + 1;
	}

	/**
	 * Find the width we should use to print the rowHead
	 * 
	 * @return an integer to be used to for formatted printing of the rowHead
	 */

	private int getRowHeadMaxWidth() {
		int ans = -1;
		for (int i = 0; i < this.getSizeRow(); i++) {
			int width = (rowHead.get(i)).toString().length();
			if (ans < width)
				ans = width;
		}
		return ans + 1;
	}

	/**
	 * Construct a string representation of the table
	 * 
	 * @return a string representation of the table
	 */

	@Override
	public String toString() {

		if (this.getSizeRow() == 0 && this.getSizeCol() == 0) {
			return "Empty Table";
		}

		// basic info of op and size
		StringBuilder sb = new StringBuilder("============================\nTable\n");
		sb.append("Operation: " + op.getClass() + "\n");
		sb.append("Size: " + this.getSizeRow() + " rows, " + this.getSizeCol() + " cols\n");

		// decide how many chars to use for rowHead
		int rowHeadWidth = getRowHeadMaxWidth();
		int totalWidth = rowHeadWidth;
		DynamicArray<Integer> colWidths = new DynamicArray<>();
		sb.append(String.format(String.format("%%%ds", rowHeadWidth), " "));

		// colHead
		for (int i = 0; i < this.getSizeCol(); i++) {
			int colWidth = getColMaxWidth(i);
			colWidths.add(colWidth);
			totalWidth += colWidth + 1;
			sb.append(String.format(String.format("|%%%ds", colWidth), colHead.get(i)));
		}

		sb.append("\n" + String.format(String.format("%%%ds", totalWidth), " ").replace(" ", "-") + "\n");

		// row by row
		for (int i = 0; i < this.getSizeRow(); i++) {
			sb.append(String.format(String.format("%%%ds", rowHeadWidth), rowHead.get(i)));
			for (int j = 0; j < this.getSizeCol(); j++) {
				int colWidth = colWidths.get(j);
				sb.append(String.format(String.format("|%%%ds", colWidth), board.get(i, j)));
			}
			sb.append("\n");
		}
		sb.append("============================\n");
		return sb.toString();

	}

	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------

	public static void main(String[] args) {
		StringAdder sa = new StringAdder();
		Table<String, String, String, StringAdder> stable = new Table<>(sa);
		stable.addRow(0, "red");
		stable.addRow(1, "yellow");
		stable.addCol(0, "apple");

		if (stable.getSizeRow() == 2 && stable.getSizeCol() == 1 && stable.getCell(0, 0).equals("red apple")
				&& stable.getCell(1, 0).equals("yellow apple")) {
			System.out.println("Yay 1");
		}
		// System.out.println(stable.toString());

		stable.addCol(1, "banana");
		stable.addCol(1, "kiwi");
		stable.addRow(2, "green");

		if (stable.getSizeRow() == 3 && stable.getSizeCol() == 3 && stable.getRowHead(2).equals("green")
				&& stable.getColHead(2).equals("banana") && stable.getCell(2, 1).equals("green kiwi")) {
			System.out.println("Yay 2");
		}

		// System.out.println(stable.toString());
		stable.removeCol(0);
		stable.setCol(1, "pear");

		if (stable.getSizeRow() == 3 && stable.getSizeCol() == 2 && stable.getColHead(0).equals("kiwi")
				&& stable.getRowHead(2).equals("green") && stable.getCell(2, 1).equals("green pear")) {
			System.out.println("Yay 3");
		}
		// System.out.println(stable.toString());

		Table<Integer, Integer, Integer, IntegerComb> itable = new Table<>(new IntegerAdder());
		for (int i = 0; i < 5; i++) {
			itable.addRow(itable.getSizeRow(), i + 1);
			itable.addCol(0, (i + 1) * 10);
		}
		if (itable.getSizeRow() == 5 && itable.getSizeCol() == 5 && itable.getCell(0, 0) == 51
				&& itable.getCell(4, 0) == 55 && itable.getCell(3, 4) == 14) {
			System.out.println("Yay 4");
		}
		// System.out.println(itable.toString());

		itable.setOp(new IntegerTimer());
		if (itable.getSizeRow() == 5 && itable.getSizeCol() == 5 && itable.getCell(0, 0) == 50
				&& itable.getCell(4, 0) == 250 && itable.getCell(3, 4) == 40) {
			System.out.println("Yay 5");
		}
		// System.out.println(itable.toString());

	}

}
