

/**
 * 
 * @author James Roberts jpr242
 *
 */
public interface LinkedListInterface<T> {
	
	/**
	 * Inserts the given data into the front of this LinkedList
	 * @param data The information to insert
	 */
	void insert(T data);
	/**
	 * Inserts the given data into the front of this LinkedList
	 * @param data The information to insert
	 */
	void insertFirst(T data);
	/**
	 * Inserts the given data into the end of this LinkedList
	 * @param data The information to insert
	 */
	void insertLast(T data);
	/**
	 * Inserts the given data into the given location of this LinkedList
	 * @param data The information to insert
	 * @param location The location to insert this data
	 */
	void insert(T data, int location);
	/**
	 * Returns the first element of this list and removes it
	 * @return The first element
	 * @throws EmptyListException
	 */
	T remove() throws EmptyListException;
	/**
	 * Returns the first element of this list and removes it
	 * @return The first element
	 * @throws EmptyListException
	 */
	T removeFirst() throws EmptyListException;
	/**
	 * Returns the last element of this list and removes it
	 * @return The last element
	 * @throws EmptyListException
	 */
	T removeLast() throws EmptyListException;
	/**
	 * Returns the element at location of this list and removes it
	 * @param location The location to remove from
	 * @return The location element
	 * @throws EmptyListException
	 */
	T remove(int location) throws EmptyListException;
	/**
	 * Returns the first element of this list without removing it
	 * @return The first element
	 * @throws EmptyListException
	 */
	T peek() throws EmptyListException;
	/**
	 * Returns the first element of this list without removing it
	 * @return The first element
	 * @throws EmptyListException
	 */
	T peekFirst() throws EmptyListException;
	/**
	 * Returns the last element of this list without removing it
	 * @return The first element
	 * @throws EmptyListException
	 */
	T peekLast() throws EmptyListException;
	/**
	 * Returns the element at location from this list without removing it
	 * @param location The element to peek
	 * @return The location element
	 * @throws EmptyListException
	 */
	T peek(int location) throws EmptyListException;
	/**
	 * Empties this list
	 */
	void clear();
	/**
	 * Returns the size of this list
	 * @return the size of this list
	 */
	int size();
	/**
	 * Returns the empty state of this list
	 * @return true if empty, false if not
	 */
	boolean isEmpty();
	
}
