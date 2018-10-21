/*
 * @author Andrew Sison
 */

/*
 * HuffmanEncoding.java
 */
package huffmanEncoding;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class HuffmanEncoding {
	private static Map<Character, Integer> frequency;
	private static String fileName = "const.txt";
	
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		File constitution = new File(fileName);//original size: 44.6 KB (45,704 bytes).
		try {
			BufferedReader br = new BufferedReader((new FileReader(constitution))); 
			BufferedReader brOutput = new BufferedReader((new FileReader(constitution))); 
			long createTreeStartTime = System.nanoTime();
			Map<Character, Integer> frequencyMap = findFrequency(br, constitution);
			HuffmanTree huffmanTree = new HuffmanTree(frequencyMap);
			long createTreeEndTime = System.nanoTime();
			double createTreeTotalTime = ( (double) createTreeEndTime - createTreeStartTime) / 1000000000.0;
			System.out.println("Time to create Huffman Tree: " + createTreeTotalTime + " seconds.");
			BinaryWriter bw = new BinaryWriter(huffmanTree);
			
			try {
				
				//TODO add huffman Tree to output file for decompression and add a way to decompress file
				//compress file using huffman tree
				int j;
				long encodeTreeStartTime = System.nanoTime();
				while( (j = brOutput.read()) != -1) {
					Character c = (char) j;
					//System.out.print(c); //prints out entire original file's contents
					for(int i = 0; i < huffmanTree.getBinaryValueOf(c).length(); i ++) {
						if (huffmanTree.getBinaryValueOf(c).charAt(i) == '0') {
							bw.writeBit(false);
						} else if (huffmanTree.getBinaryValueOf(c).charAt(i) == '1') {
							bw.writeBit(true);
						} else {

						}
					}
				}
				bw.writeRemainingBits();
				long encodeTreeEndTime = System.nanoTime();
				double encodeTreeTotalTime = ( (double) encodeTreeEndTime - encodeTreeStartTime) / 1000000000.0;
				System.out.println("Time to encode Huffman Tree: " + encodeTreeTotalTime + " seconds.");
				
				//compare size
				System.out.println("Size of Original: " + constitution.length() + " bytes.");
				System.out.println("Size of Compressed File: " + bw.getOutputFileSize() + " bytes.");
				float percentSaved = ((float) bw.getOutputFileSize() / constitution.length()) * 100;
				System.out.println("Compressed file % size of Original: " + percentSaved + "%");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		long endTime = System.nanoTime();
		double totalTime = ( (double) endTime - startTime) / 1000000000.0;
		System.out.println("Total time to complete: " + totalTime + " seconds.");
	}

	private static Map<Character, Integer> findFrequency(BufferedReader br, File constitution) {
		frequency = new HashMap<Character, Integer>();
		
		try {
			// find the frequency of each character
			Character c;
			int cCode;
			ArrayList<FrequencyThread> threads = new ArrayList<>();
			while((cCode = br.read()) != -1) {
				FrequencyThread temp = new FrequencyThread(frequency, cCode);
				threads.add(temp);
				temp.start();
			}
			for(FrequencyThread ft : threads) {
				try {
					ft.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//sort HashMap
			ValueComparator comp = new ValueComparator(frequency);
			Map<Character, Integer> sortedFreq = new TreeMap<Character, Integer>(comp);
			sortedFreq.putAll(frequency);
			
			return sortedFreq;
		} catch (IOException e) {	
			e.printStackTrace();
		}
		return frequency;
	}
	
	private static class FrequencyThread extends Thread{
		Character c;
		HashMap<Character, Integer> frequency;
		
		public FrequencyThread(Map<Character, Integer> frequency2, int cCode) {
				this.frequency = (HashMap<Character, Integer>) frequency2;
				c = (char) cCode;
		}
		
		public void run() {
			
			Integer currentFreq = frequency.get(c);
			
			if(currentFreq == null) {
				currentFreq = new Integer(1);
			} else {
				currentFreq = new Integer(currentFreq.intValue() + 1);
			}				
			putIn(currentFreq);
		
		}
		
		private synchronized void putIn(Integer currentFreq) {
			frequency.put(c, currentFreq);
		}
	}
}



