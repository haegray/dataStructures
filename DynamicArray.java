import com.sun.xml.internal.bind.v2.runtime.output.NamespaceContextImpl;
import sun.security.jca.GetInstance.Instance;

/**
 * @author Helena Gray
 * @version 9.16.2018
 * 
 * This class creates an array that will grow or shrink depending on array contents, capacity, and input.
 */
public class DynamicArray<T> {

	private static final int INITCAP = 2; // default initial capacity / minimum capacity
	private T[] storage; // underlying array
	// end is the cell after the last cell with a value in it
	private int end = 0;
	// size is how many elements not equal to null in array
	private int size = 0;

	@SuppressWarnings("unchecked")
	public DynamicArray() {
		// constructor
		// initial capacity of the array should be INITCAP
		this.storage = (T[]) new Object[this.INITCAP];
	}

	/**
	 * @param initCapacity
	 *            gives the initial and lowest capacity for a DynamicArray
	 *            instance
	 */
	@SuppressWarnings("unchecked")
	public DynamicArray(int initCapacity) {
		// constructor
		// throw IllegalArgumentException if initCapacity < 1
		if (initCapacity < 1) {
			throw new IllegalArgumentException("Array cannot be smaller than 1!");
		} else {
			// set the initial capacity of the array as initCapacity
			this.storage = (T[]) new Object[initCapacity];
		}
	}

	/**
	 * @return numElements which is the number of elements not equal to null in
	 *         the array
	 */
	public int size() {
		// O(1)
		// report the number of elements in the list
		return size;
	}

	/**
	 * @return this.storage.length which is how many elements the array can hold
	 */
	public int capacity() {
		// O(1)
		// report the max number of elements before the next expansion
		return storage.length;
	}

	/**
	 * @param index
	 *            is the slot in the DynamicArray where the value will go
	 * @param value
	 *            is the value to replace value in the specified slot of the
	 *            DynamicArray
	 * @throws IndexOutOfBoundsException
	 *             if index is smaller than 0 or greater than or equal to the
	 *             end of the array which is the cell after the last element
	 *             that is not null
	 * @return returns the old value that was replaced with value in the
	 *         DynamicArray
	 */
	public T set(int index, T value) {
		// O(1)
		// Note: you cannot add new items with this method
		T oldItem;
		if (index < 0 || index >= this.end) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			oldItem = this.get(index);
			// change item x at index i to be value
			this.storage[index] = value;
			// return old item at index
			return oldItem;
		}
	}

	/**
	 * @param index
	 *            is the slot of the DynamicArray with the value to be returned
	 * @throws IndexOutOfBoundsException
	 *             if index is smaller than 0 or greater than or equal to the
	 *             end of the array which is the cell after the last element
	 *             that is not null
	 * @return returns the value of the index of the DynamicArray
	 */
	public T get(int index) {
		// O(1)
		if (index < 0 || index >= end) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			// return the item at index
			return storage[index];
		}
	}

	/**
	 * @param value
	 *            is the value that will go in the slot at the end of the
	 *            DynamicArray
	 * @throws IndexOutOfBoundsException
	 *             if index is smaller than 0 or greater than or equal to the
	 *             end of the array which is the cell after the last element
	 *             that is not null
	 * @return returns the old value that was replaced with value in the
	 *         DynamicArray
	 */
	@SuppressWarnings("unchecked")
	public boolean add(T value) {
		// amortized O(1)
		end++;
		size++;
		// double the capacity if no space is available
		if (end - 1 >= capacity()) {
			DynamicArray tempArr = new DynamicArray(this.capacity() * 2);
			System.arraycopy(storage, 0, tempArr.storage, 0, size - 1);
			storage = (T[]) tempArr.storage;
		}
		try {
			// add value to the end of the list (append)
			this.set(end - 1, value);
		} catch (IndexOutOfBoundsException e) {
			end--;
			size--;
		}
		return true;
	}

	/**
	 * @param index
	 *            is the slot in the DynamicArray where the value will go
	 * @param value
	 *            is the value to replace value in the specified slot of the
	 *            DynamicArray
	 * @throws IndexOutOfBoundsException
	 *             if index is smaller than 0 or greater than or equal to less
	 *             than one of the end of the array which is the slot after the
	 *             last element that is not null
	 */
	@SuppressWarnings("unchecked")
	public void add(int index, T value) {
		// O(N) where N is the number of elements in the list
		// Note: this method may be used to append items as
		// well as insert items
		if (index < 0 || index > end) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			// insert value at index, shift elements if needed
			if (index == end) {
				this.add(value);
			} else {
				// double the capacity if no space is available
				if (this.size() == this.capacity()) {
					DynamicArray tempArr = new DynamicArray(this.capacity() * 2);
					System.arraycopy(storage, 0, tempArr.storage, 0, this.size());
					storage = (T[]) tempArr.storage;
				}
				end++;
				for (int k = end - 2; k >= index; k--) {
					try {
						this.set(k + 1, this.get(k));
					} catch (IndexOutOfBoundsException e) {
						end--;
						break;
					}
				}
				try {
					this.set(index, value);
					size++;
				} catch (IndexOutOfBoundsException e) {
					size--;
				}
			}
		}
	}

	/**
	 * @param index
	 *            is the slot in the DynamicArray where the value will be
	 *            removed from
	 * @throws IndexOutOfBoundsException
	 *             if index is smaller than 0 or greater than or equal to the
	 *             end of the array which is the cell after the last element
	 *             that is not null
	 * @return returns the value that was removed from the DynamicArray
	 */
	@SuppressWarnings("unchecked")
	public T remove(int index) {
		// O(N) where N is the number of elements in the list
		T oldItem;
		if (index < 0 || index >= end) {
			// throw IndexOutOfBoundsException for invalid index
			throw new IndexOutOfBoundsException("Index out of bounds!");
		} else {
			// remove and return element at position index
			// shift elements to remove any gap in the list
			oldItem = this.get(index);
			this.set(index, null);
			DynamicArray tempArr = new DynamicArray(this.capacity());
			for (T t : storage) {
				if (t != null) {
					tempArr.add(t);
				}
			}
			storage = (T[]) tempArr.storage;
			size--;
			end--;
			if (this.size() < (this.capacity() / 3.0)) {
				// halve capacity if the number of elements falls below 1/3 of
				// the capacity
				if ((this.capacity() / 2.0) < INITCAP) {
					// capacity should NOT go below INITCAP
					System.out.println("Capacity cannot be less than " + INITCAP);
				} else {
					tempArr = new DynamicArray(this.capacity() / 2);
					System.arraycopy(storage, 0, tempArr.storage, 0, this.size());
					storage = (T[]) tempArr.storage;
				}
			}
		}
		return oldItem;
	}

	// --------------------------------------------------------
	// example testing code... edit this as much as you want!
	// --------------------------------------------------------

	// Not required, update for your own testing
	@Override
	public String toString() {
		// return string representation of DynamicArray
		// update if you want to include more information
		String str = "";
		for (int i = 0; i < this.size(); i++) {
			str = str + this.get(i) + " ";
		}
		return (str);

	}

	public static void main(String args[]) {
		// new list?
		DynamicArray<Integer> ida = new DynamicArray<>();
		if ((ida.size() == 0) && (ida.capacity() == 2)) {
			System.out.println("Yay 1");
		}

		// adding to the list?
		boolean ok = true;
		for (int i = 0; i < 3; i++) {
			// System.out.println("Size is: " + ida.size() + " Capacity is: " +
			// ida.capacity() + " End is: " + ida.end);
			ok = ok && ida.add(i * 5);
		}
		// System.out.println(ida.get(2));
		// System.out.println("Size is: " + ida.size() + " Capacity is: " +
		// ida.capacity() + " Ida at 2 is: " + ida.get(2));
		if (ok && ida.size() == 3 && ida.get(2) == 10 && ida.capacity() == 4) {
			System.out.println("Yay 2");
		}

		ida.add(1, -10);
		ida.add(4, 100);
		if (ida.set(1, -20) == -10 && ida.get(2) == 5 && ida.size() == 5 && ida.capacity() == 8) {
			System.out.println("Yay 3");
		}

		// removing from the list?
		if (ida.remove(0) == 0 && ida.remove(0) == -20 && ida.remove(2) == 100 && ida.size() == 2
				&& ida.capacity() == 4) {
			System.out.println("Yay 4");
		}

		// remember to tests more things...
	}

}