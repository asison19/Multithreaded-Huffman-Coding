/*
 * HuffmanTree.java
 */

package huffmanEncoding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class HuffmanTree {
	Map<Character, Integer> frequencyMap;
	private ArrayList<Character> keys = new ArrayList<Character>();
	Node root;
	public HuffmanTree(Map<Character, Integer> map) {
		frequencyMap = map;
		
		Object[] keyArray = frequencyMap.keySet().toArray();
		for (Object o : keyArray) {
			keys.add((Character) o);
		}
		createHuffmanTree();
		convertHuffmanTree();
	}

	private Node searchNode; //used to return binary value of a node that is looked for
	//returns the binary representation of the Character ch
	public String getBinaryValueOf(Character ch) {
		getBinaryValueOf( ch, root);
		return searchNode.binaryValue;
		
	}
	public void getBinaryValueOf(Character ch, Node node) {
		if(node == null)
			return;
		if(node.c == ch)
			searchNode = node;
		else {
			getBinaryValueOf(ch, node.left);
			getBinaryValueOf(ch, node.right);
		}
	}

	//gives the leaves of the huffman tree a binary representation of it's character
	//0 for left child, 1 for right child
	private void convertHuffmanTree() {
		convertHuffmanTree(root);
	}
	private void convertHuffmanTree(Node node) {
		if(node == null)
			return;
		if(node.left != null)
			node.left.setBinaryValue(node.binaryValue + "0");
		if(node.right !=null)
			node.right.setBinaryValue(node.binaryValue + "1");
		
		convertHuffmanTree(node.left);
		convertHuffmanTree(node.right);
		
	}

	//creates Huffman tree from the leaves that contain a character and it's frequency
	private void createHuffmanTree() {
		ArrayList<Node> nodes = new ArrayList<>();//list with nodes that contain a character and frequency of said character
		for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			Node temp = new Node(entry.getKey(), entry.getValue());
			nodes.add(temp);
		    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		}
		createHuffmanTree(nodes);
	}
	@SuppressWarnings("unchecked")
	private void createHuffmanTree(ArrayList<Node> nodes) {
		//ArrayList<TreeThread> threads = new ArrayList<>();
		if(keys.size() == 1) {
			int sum = nodes.get(0).frequency;
			root = new Node(null, sum);
			root.left = new Node(nodes.get(0).c, nodes.get(0).frequency);
			root.right = new Node();
		}
		if(nodes.size() > 1) {
			Collections.sort(nodes);
			int sum = nodes.get(0).frequency + nodes.get(1).frequency;
			root = new Node(null, sum);
			TreeThread temp = new TreeThread(root, nodes);
			temp.start();
			//threads.add(temp);
			try {
				temp.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			createHuffmanTree(nodes);
		}
	}
	
	//multi-threading createHuffmanTree shows no difference in time
	private static class TreeThread extends Thread{
		Node root;
		ArrayList<Node> nodes;
		public TreeThread(Node root, ArrayList<Node> nodes) {
			this.root = root;
			this.nodes = nodes;
		}
		
		public void run() {
			addNodes();
		}
		
		private synchronized void addNodes() {
			//critical section
			root.left = nodes.remove(0);
			root.right = nodes.remove(0);
			nodes.add(root);
			//critical section done
		}
	}
	
	//display binary values of characters in huffman tree
	public void displayHuffmanTree() {
		displayHuffmanTree(root);	
	}
	private void displayHuffmanTree(Node node) {
		if(node == null) 
			return;
		if(node.c != null)
			System.out.println(node.c + " has a binary value of: " + node.binaryValue);
		displayHuffmanTree(node.left);	
		displayHuffmanTree(node.right);
		
	}
	

}
