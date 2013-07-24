

/**
 * 
 * @author James Roberts jpr242
 *
 */
public class Node<T> {

	protected T data;
	protected Node<T> frontPointer;
	
	/**
	 * Creates an empty node
	 */
	public Node() {
		this.data = null;
		this.frontPointer = null;
	}
	
	/**
	 * Creates a new node with the given data
	 * @param data the data to be held
	 */
	public Node(T data) {
		this.data = data;
		this.frontPointer = null;
	}

	/**
	 * Gets the data held by this node
	 * @return the data held
	 */
	public T getData() {
		return data;
	}

	/**
	 * Sets the data held by this node
	 * @param data the data to be set
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * Returns the front node pointed to
	 * @return attached node
	 */
	public Node<T> getFrontPointer() {
		return frontPointer;
	}

	/**
	 * Sets the front node pointed to
	 * @param frontPointer the node to be pointed to
	 */
	public void setFrontPointer(Node<T> frontPointer) {
		this.frontPointer = frontPointer;
	}
	
}
