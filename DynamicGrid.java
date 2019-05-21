/**
 * @author Helena Gray
 * @version 9.16.2018
 * 
 * This class creates a dynamic grid that will grow or shrink depending on grid contents, capacity, and input.
 */
public class DynamicGrid<T> {
	private DynamicArray<DynamicArray<T>> storage; // underlying storage
	// HINT: Read the big-O requirements of the methods below to determine
	// how the columns/rows should be stored in storage.

	public DynamicGrid() {
		// constructor
		// create an empty table of 0 rows and 0 cols
		this.storage = new DynamicArray<DynamicArray<T>>();
	}

	/**
	 * @return this.storage.size() which is the number of rows in the grid
	 */
	public int getNumRow() {
		// O(1)
		// return number of rows in the grid
		return storage.size();
	}

	/**
	 * @return this.storage.get(0).size() which is the number of columns in the
	 *         grid
	 */
	public int getNumCol() {
		// O(1)
		// return number of columns in the grid
		try {
			return storage.get(0).size();
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

	/**
	 * @param indexRow
	 *            is the row index of the DynamicArray with the value to be
	 *            returned
	 * @param indexCol
	 *            is the column index of the DynamicArray with the value to be
	 *            returned
	 * @return returns the value of the row and column indices of the
	 *         DynamicGrid
	 */
	public T get(int indexRow, int indexCol) {
		// O(1)
		// return the value at the specified row and column
		// get method throws IndexOutOfBoundsException for invalid index
		T returnValue = storage.get(indexRow).get(indexCol);
		return returnValue;
	}

	/**
	 * @param indexRow
	 *            is the row index of the DynamicArray with the value to be
	 *            returned
	 * @param indexCol
	 *            is the column index of the DynamicArray with the value to be
	 *            returned
	 * @param value
	 *            is the value to replace the value in the specified slot of the
	 *            DynamicGrid
	 * @throws IndexOutOfBoundsException
	 *             if row or column index is less than 0 or greater than number
	 *             of rows or columns
	 * @return returns the value of the row and column indices of the
	 *         DynamicGrid
	 */

	public T set(int indexRow, int indexCol, T value) {
		// O(1)
		// Note: this can not be used to add new items, only replace
		// existing items.
		T oldItem;
		if (indexRow < 0 || indexCol < 0 || indexRow >= this.getNumRow() || indexCol >= this.getNumCol()) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			oldItem = this.get(indexRow, indexCol);
			// change value at the specified row and column to be value
			storage.get(indexRow).set(indexCol, value);
			// return the old value
			return oldItem;
		}
	}

	/**
	 * @param index
	 *            is the row index of the Dynamic Grid to where the new row will
	 *            be added
	 * @param newRow
	 *            is the new row that will be added to the Dynamic Grid
	 * @return returns the old value that was replaced with value in the
	 *         DynamicArray
	 */
	public boolean addRow(int index, DynamicArray<T> newRow) {
		// O(C+R) where R is the number of rows and C is the number of columns
		// of the grid
		// Note: this can be used to append rows as well as insert rows
		// copy values from newRow to add a row at the row index specified

		// O(C)
		DynamicArray tempArr = new DynamicArray(newRow.capacity());
		for (int i = 0; i < newRow.size(); i++) {
			tempArr.add(newRow.get(i));
		}

		// O(R)
		boolean addRow;
		// return true if newRow can be added correctly
		// return false otherwise
		if (this.getNumCol() == 0 || tempArr.size() == this.getNumCol()) {
			try {
				storage.add(index, tempArr);
				addRow = true;
			} catch (IndexOutOfBoundsException e) {
				addRow = false;
			}
		} else {
			// cannot add if the length of newRow does not match existing rows
			addRow = false;
		}
		return addRow;
	}

	/**
	 * @param index
	 *            is the column index of the Dynamic Grid to where the new
	 *            column will be added
	 * @param newCol
	 *            is the new column that will be added to the Dynamic Grid
	 * @return returns the old value that was replaced with value in the
	 *         DynamicArray
	 */

	public boolean addCol(int index, DynamicArray<T> newCol) {
		// O(RC) where R is the number of rows and C is the number of columns of
		// the grid
		// Note: this can be used to append columns as well as insert columns
		boolean addCol;
		if (this.getNumCol() == 0 || newCol.size() == this.getNumRow()) {
			// copy values from newCol to add a column at the column index
			// specified
			try {
				for (int i = 0; i < newCol.size(); i++) {
					this.storage.get(i).add(index, newCol.get(i));
				}
				// return true if newCol can be added correctly
				addCol = true;
				// return false otherwise
			} catch (IndexOutOfBoundsException e) {
				addCol = false;
			}
			// cannot add if the length of newCol does not match existing
			// columns
		} else {
			addCol = false;
		}
		return addCol;
	}

	/**
	 * @param index
	 *            is the row index in the Dynamic Grid that indicates which row
	 *            to remove
	 * @throws IndexOutOfBoundsException
	 *             if index is less than 0 or greater than or equal to number of
	 *             rows
	 * @return returns the row that was removed from the Dynamic Grid
	 */
	public DynamicArray<T> removeRow(int index) {
		// O(R) where R is the number of rows of the grid
		// remove and return a row at index x
		if (index < 0 || index >= this.getNumRow()) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			DynamicArray OldRow = new DynamicArray(this.getNumCol());
			for (int i = 0; i < this.getNumCol(); i++) {
				OldRow.add(this.storage.get(index).get(i));
			}
			this.storage.get(index).set(0, null);
			DynamicGrid tempGrid = new DynamicGrid();
			tempGrid.storage = (DynamicArray<T>) new DynamicArray(this.getNumRow() - 1);
			// shift rows to remove the gap
			for (int k = 0; k < this.getNumRow(); k++) {
				if (this.storage.get(k).get(0) != null) {
					tempGrid.storage.add(this.storage.get(k));
				}
			}
			this.storage = tempGrid.storage;
			return OldRow;
		}

	}

	/**
	 * @param index
	 *            is the column index in the Dynamic Grid that indicates which
	 *            column to remove
	 * @throws IndexOutOfBoundsException
	 *             if index is less than 0 or greater than or equal to number of
	 *             columns
	 * @return returns the column that was removed from the Dynamic Grid
	 */
	public DynamicArray<T> removeCol(int index) {
		// O(RC) where R is the number of rows and C is the number of columns
		// remove and return a column at index x
		if (index < 0 || index >= this.getNumCol()) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			DynamicArray OldCol = new DynamicArray(this.getNumRow());
			for (int i = 0; i < this.getNumRow(); i++) {
				// shift columns to remove the gap
				OldCol.add(this.storage.get(i).remove(index));
			}
			return OldCol;
		}
	}

	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------

	// Not required, update for your own testing
	@Override
	public String toString() {
		String grid = "";
		for (int i = 0; i < this.getNumRow(); i++) {
			grid = grid + this.storage.get(i).toString() + "\n";
		}
		System.out.println(grid);
		return grid;
	}

	public static void main(String[] args) {
		// make some simple grids
		DynamicGrid<String> sgrid = new DynamicGrid<>();
		DynamicArray<String> srow = new DynamicArray<>();
		srow.add("English");
		srow.add("Spanish");
		srow.add("German");
		if (sgrid.getNumRow() == 0 && sgrid.getNumCol() == 0 && sgrid.addRow(0, srow) && sgrid.getNumRow() == 1
				&& sgrid.getNumCol() == 3) {
			System.out.println("Yay 1");
		}
		// System.out.println("Number of sgrid rows is: " + sgrid.getNumRow() +
		// " Number of sgrid cols is: " + sgrid.getNumCol());
		// sgrid.toString();

		if (sgrid.get(0, 0).equals("English") && sgrid.set(0, 1, "Espano").equals("Spanish")
				&& sgrid.get(0, 1).equals("Espano")) {
			System.out.println("Yay 2");
		}
		// sgrid.toString();

		// more complicated grids
		DynamicGrid<Integer> igrid = new DynamicGrid<Integer>();
		boolean ok = true;
		// System.out.println("Number of igrid rows is: " + igrid.getNumRow() +
		// " Number of sgrid cols is: " + igrid.getNumCol());

		for (int i = 0; i < 3; i++) {
			DynamicArray<Integer> irow = new DynamicArray<>();
			irow.add((i + 1) * 10);
			ok = ok && igrid.addRow(igrid.getNumRow(), irow);
			// System.out.println("Addrow entry is: " + ok + " New row capacity
			// is: " + irow.capacity() +" Number of Rows is: " +
			// igrid.getNumRow() + " Number of Cols is: " + igrid.getNumCol());
		}
		// System.out.println("Number of igrid rows is: " + igrid.getNumRow() +
		// " Number of sgrid cols is: " + igrid.getNumCol() + " Igrid at 2, 0
		// is: " + igrid.get(2, 0));

		if (ok && igrid.getNumRow() == 3 && igrid.getNumCol() == 1 && igrid.get(2, 0) == 30) {
			System.out.println("Yay 3");
		}
		// igrid.toString();

		DynamicArray<Integer> icol = new DynamicArray<>();
		icol.add(-10);
		icol.add(-20);
		ok = igrid.addCol(1, icol);
		icol.add(-30);
		// System.out.println("Addcol entry is: " + ok + " Num of rows is: " +
		// igrid.getNumRow() + " Number of Cols is: " + igrid.getNumCol());
		if (!ok && igrid.addCol(1, icol) && igrid.getNumRow() == 3 && igrid.getNumCol() == 2) {
			System.out.println("Yay 4");
		}
		// igrid.toString();

		DynamicArray<Integer> irow = new DynamicArray<>();
		irow.add(5);
		irow.add(10);

		if (!igrid.addRow(5, irow) && igrid.addRow(0, irow) && igrid.getNumRow() == 4 && igrid.get(0, 0) == 5
				&& igrid.get(3, 1) == -30) {
			System.out.println("Yay 5");
		}
		// System.out.println("Igrid at 0, 0 is " + igrid.get(0,0) + " Number of
		// Rows in igrid is: " + igrid.getNumRow());
		// igrid.toString();

		// System.out.println(igrid.toString());
		// System.out.println(" Number of Cols in igrid is: " +
		// igrid.getNumCol() + ", " + igrid.get(2,1));

		irow = igrid.removeCol(1);
		// System.out.println(" Number of Rows in igrid is: " +
		// igrid.getNumRow() + " Number of Cols in igrid is: " +
		// igrid.getNumCol());

		if (igrid.getNumRow() == 4 && igrid.getNumCol() == 1 && irow.get(0) == 10 && igrid.get(0, 0) == 5
				&& igrid.get(2, 0) == 20) {
			System.out.println("Yay 6");
		}
		// igrid.toString();

	}

}