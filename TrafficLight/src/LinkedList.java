

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class LinkedList<T> implements LinkedListInterface<T> {

	protected Node<T> head, tail, current;
	private int size;
	
	/**
	 * Creates a new, empty LinkedList
	 */
	public LinkedList() {
		this.head = null;
		this.tail = null;
		this.current = null;
		this.size = 0;
	}
	
	/**
	 * Creates a new LinkedList containing the data input
	 * @param data the data added to the new LinkedList
	 */
	public LinkedList(T data) {
		this.head = new Node<T>(data);
		this.tail = this.head;
		this.current = null;
		this.size = 1;
	}
	
	//adds to front
	/**
	 * Inserts the given data into the front of this LinkedList
	 * @param data The information to insert
	 */
	public void insert(T data) {
		Node<T> newNode = new Node<T>(data);
		this.size++;
		if(this.isEmpty()) {
			this.head = newNode;
			this.tail = this.head;
			return;
		}
		newNode.setFrontPointer(this.head);
//		if(this.head == this.tail) {
//			this.tail = this.head;
//		}
		this.head = newNode;
	}
	
	//adds to front
	/**
	 * Inserts the given data into the front of this LinkedList
	 * @param data The information to insert
	 */
	public void insertFirst(T data) {
		this.insert(data);
	}
	
	//adds to back
	/**
	 * Inserts the given data into the end of this LinkedList
	 * @param data The information to insert
	 */
	public void insertLast(T data) {
		if(this.isEmpty()) {
			this.insert(data);
			return;
		}
		this.size++;
		Node<T> newNode = new Node<T>(data);
		this.tail.setFrontPointer(newNode);
		this.tail = newNode;
	}
	
	//adds to location
	/**
	 * Inserts the given data into the given location of this LinkedList
	 * @param data The information to insert
	 * @param location The location to insert this data
	 */
	public void insert(T data, int location) {
		if(location > this.size || location < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(location == 0) {
			this.insert(data);
			return;
		}
		if(location == this.size) {
			this.insertLast(data);
			return;
		}
		Node<T> newNode = new Node<T>(data);
		this.moveCurrentForwards(location - 1);
		newNode.setFrontPointer(this.current.getFrontPointer());
		this.current.setFrontPointer(newNode);
		this.size++;
	}
	
	//removes from front
	/**
	 * Returns the first element of this list and removes it
	 * @return The first element
	 * @throws EmptyListException
	 */
	public T remove() throws EmptyListException{
		if(this.isEmpty()) {
			throw new EmptyListException();
		}
		this.size--;
		if(this.size == 0) {
			this.tail = null;
		}
		T toReturn = this.head.getData();
		this.head = this.head.getFrontPointer();
		return toReturn;
	}
	
	//removes from front
	/**
	 * Returns the first element of this list and removes it
	 * @return The first element
	 * @throws EmptyListException
	 */
	public T removeFirst() throws EmptyListException {
		return this.remove();
	}
	
	//removes from end
	/**
	 * Returns the last element of this list and removes it
	 * @return The last element
	 * @throws EmptyListException
	 */
	public T removeLast() throws EmptyListException {
		if(this.isEmpty()) {
			throw new EmptyListException();
		}
		if(this.size == 1) {
			return this.remove();
		}
		this.size--;
		T toReturn = this.tail.getData();
		this.moveCurrentForwards(this.size - 1);
		this.current.setFrontPointer(null);
		this.tail = this.current;
		return toReturn;
	}
	
	//removes from location
	/**
	 * Returns the element at location of this list and removes it
	 * @param location The location to remove from
	 * @return The location element
	 * @throws EmptyListException
	 */
	public T remove(int location) throws EmptyListException {
		if(this.isEmpty()) {
			throw new EmptyListException();
		}
		if(location >= this.size || location < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(location == 0) {
			return this.remove();
		}
		if(location == this.size - 1) {
			return this.removeLast();
		}
		this.size--;
		this.moveCurrentForwards(location - 1);
		T toReturn = this.current.getFrontPointer().getData();
		this.current.setFrontPointer(this.current.getFrontPointer().getFrontPointer());
		return toReturn;
	}
	
	//looks at front
	/**
	 * Returns the first element of this list without removing it
	 * @return The first element
	 * @throws EmptyListException
	 */
	public T peek() throws EmptyListException {
		if(this.isEmpty()) {
			throw new EmptyListException();
		}
		return this.head.getData();
	}

	//looks at front
	/**
	 * Returns the first element of this list without removing it
	 * @return The first element
	 * @throws EmptyListException
	 */
	public T peekFirst() throws EmptyListException {
		return this.peek();
	}
	
	//looks at end
	/**
	 * Returns the last element of this list without removing it
	 * @return The first element
	 * @throws EmptyListException
	 */
	public T peekLast() throws EmptyListException {
		if(this.isEmpty()) {
			throw new EmptyListException();
		}
		return this.tail.getData();
	}
	
	//looks at location
	/**
	 * Returns the element at location from this list without removing it
	 * @param location The element to peek
	 * @return The location element
	 * @throws EmptyListException
	 */
	public T peek(int location) throws EmptyListException {
		if(location >= this.size || location < 0) {
			throw new IndexOutOfBoundsException();
		}
		if(this.isEmpty()) {
			throw new EmptyListException();
		}
		if(location == 0) {
			return this.peek();
		}
		if(location == this.size - 1) {
			return this.peekLast();
		}
		this.moveCurrentForwards(location);
		return this.current.getData();
	}
	
	/**
	 * Empties this list
	 */
	public void clear() {
		this.head = null;
		this.tail = null;
		this.current = null;
	}
	
	/**
	 * Returns the size of this list
	 * @return the size of this list
	 */
	public int size() {
		return this.size;
	}
	
	/**
	 * Returns the empty state of this list
	 * @return true if empty, false if not
	 */
	public boolean isEmpty() {
		if(this.head == null) {
			return true;
		}
		return false;
	}
	
	/**
	 * Moves the "current" node forwards to location
	 * @param location the place to move current
	 */
	protected void moveCurrentForwards(int location) {
	//	System.out.println("Forwards");
		this.current = this.head;
		for(int j = 0; j < location && current.getFrontPointer() != null; j++, this.current = this.current.getFrontPointer());
	}
	
}
