

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class LinkedQueue<T> extends LinkedList<T> implements QueueInterface<T> {

	/**
	 * Creates a new empty linked queue
	 */
	public LinkedQueue() {
		super();
	}
	
	/**
	 * Creates a new linked queue with data as the only entry
	 * @param data the first entry
	 */
	public LinkedQueue(T data) {
		super(data);
	}
	
	/**
	 * Adds given data to the end of this queue
	 * @param data the data to add
	 */
	@Override
	public void add(T data) {
		this.insertLast(data);
		
	}

	/**
	 * Removes and returns the first element of this queue
	 * @return The first element of this queue
	 * @throws EmptyListException
	 */
	@Override
	public T poll() throws EmptyListException {
		return this.removeFirst();
	}

	/**
	 * Returns the first element of this queue
	 * @return The first element of this queue
	 * @throws EmptyListException
	 */
	@Override
	public T front() throws EmptyListException {
		return this.peekFirst();
	}
	

}
