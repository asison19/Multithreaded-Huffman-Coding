/*
 * Node.Java
 */
package huffmanEncoding;

public class Node implements Comparable{	
	Character c; //the character this node represents, only if it's a leaf
	int frequency; // the amount of times this character appears
	String binaryValue = ""; //binary representation of the character	
	Node left; //left child
	Node right; //right child
	
	public Node() {
		c = null;	
	}
	
	public Node(Character key, Integer value) {
		c = key;
		frequency = value;
	}

	@Override
	public int compareTo(Object arg0) {
		Node n = (Node) arg0;

		// lowest to highest
		if (n.frequency < this.frequency) {
			return 1;
		} else if (n.frequency > this.frequency) {
			return -1;
		} else {
			return 0;
		}
	}
	
	public String getBinaryValue() {
		return this.binaryValue;
	}
	
	public synchronized void setBinaryValue(String str) {
		binaryValue = str;
	}
	
}
