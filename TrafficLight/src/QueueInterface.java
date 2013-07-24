

/**
 * 
 * @author James Roberts jpr242
 *
 */
public interface QueueInterface<T> {
	
	/**
	 * Adds given data to the end of this queue
	 * @param data the data to add
	 */
	void add(T data);
	/**
	 * Removes and returns the first element of this queue
	 * @return The first element of this queue
	 * @throws EmptyListException
	 */
	T poll() throws EmptyListException;
	/**
	 * Returns the first element of this queue
	 * @return The first element of this queue
	 * @throws EmptyListException
	 */
	T front() throws EmptyListException;
	/**
	 * Returns the empty state of this list
	 * @return true if empty, false if not
	 */
	boolean isEmpty();
	
}
