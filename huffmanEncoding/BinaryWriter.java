/*
 * BinaryWriter.java
 * Use to write binary into a .bin file
 */

package huffmanEncoding;

import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class BinaryWriter {
	HuffmanTree huffmanTree;
	String fileName = "output.bin";
	DataOutputStream os;

	public BinaryWriter(HuffmanTree hTree) {
		huffmanTree = hTree;
		try {
			os = new DataOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	int b = 0;
	int bitIndex = 1;
	public void writeBit(boolean bit) {
		if(bitIndex > 8) {
			bitIndex = 1;
			try {
				os.writeByte(b);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if(bit) {
			switch(bitIndex) {
				case 1:
					b += 128;
					break;
				case 2:
					b += 64;
					break;
				case 3:
					b += 32;
					break;
				case 4:
					b += 16;
					break;
				case 5:
					b += 8;
					break;
				case 6:
					b += 4;
					break;
				case 7:
					b += 2;
					break;
				case 8:
					b += 1;
					break;
			}
			
			bitIndex++;
		} else {
			bitIndex++;
		}
	}
	
	public void writeRemainingBits() {
		try {
			os.writeByte(b);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getOutputFileSize() {
		return os.size();
	}

}
