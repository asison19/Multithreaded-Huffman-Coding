/*
 * ValueComparator.java
 */
package huffmanEncoding;

import java.util.Comparator;
import java.util.Map;

class ValueComparator implements Comparator {
	Map<?, ?> original;
	public ValueComparator(Map<?, ?> unsorted) {
		this.original = unsorted;
	}
	public int compare(Object a, Object b) {
		// for lowest to highest switch 1 to -1 and vice versa
		if ((Integer) original.get(a) < (Integer) original.get(b)) {
			return -1;
		} else if ((Integer) original.get(a) > (Integer) original.get(b)) {
			return 1;
		} else {
			if((Character) a < (Character) b) {
				return -1;
			} else if((Character) a > (Character) b) {
				return 1;
			} else {
				return 0;
			}
		}
	}
}
