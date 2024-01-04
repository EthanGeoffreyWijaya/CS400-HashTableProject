
// --== CS400 Project One File Header ==--
// Name: Ethan Geoffrey Wijaya
// Email: egwijaya@wisc.edu
// Team: red
// Group: CI
// TA: Tingjia Cao
// Lecturer: Florian Heimerl
// Notes to Grader: 
import java.util.NoSuchElementException;
import java.util.LinkedList;

/**
 * This class creates a hashtable which holds key value pairs of KeyType and
 * ValueType respectively.
 * 
 * @author Ethan
 *
 * @param <KeyType>   Generic key of the hash items
 * @param <ValueType> Generic value of the hash items
 */
public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
	private LinkedList<HashItem<KeyType, ValueType>>[] hashTable;
	private static final double LF_THRESHOLD = 0.8;
	private int size = 0;

	/**
	 * Constructor for HashTableMap with default capacity
	 */
	@SuppressWarnings("unchecked")
	public HashtableMap() {
		hashTable = new LinkedList[20];
	}

	/**
	 * Constructor for HashtableMap with user defined capacity
	 * 
	 * @param capacity The length of the array serving as the hash table
	 */
	@SuppressWarnings("unchecked")
	public HashtableMap(int capacity) {
		hashTable = new LinkedList[capacity];
	}

	/**
	 * Gets a key and calculates the associated array index based on the hashCode
	 * function and the following formula: 
	 * |key.hashCode()| % length of hashtable array
	 * 
	 * @param key The key of the hash table entry
	 * @return The index associated with the selected key
	 */
	private int hashFunction(KeyType key) {
		return Math.abs(key.hashCode()) % hashTable.length;
	}

	/**
	 * Doubles the capacity of the hash table and rehashes it. Does this by creating
	 * a new array twice the size of the original and iterating through the original
	 * to find each key to rehash.
	 */
	@SuppressWarnings("unchecked")
	private void growTable() {
		LinkedList<HashItem<KeyType, ValueType>>[] tempTable = hashTable;
		hashTable = new LinkedList[tempTable.length * 2];
		size = 0; // Size is reset to 0 here since the put() method will be called repeatedly
					// below, incrementing size by the number of entries in the original array (The
					// same size as before).
		for (int i = 0; i < tempTable.length; i++) {
			if (tempTable[i] == null) {
				continue;
			}

			for (int j = 0; j < tempTable[i].size(); j++) {
				put(tempTable[i].get(j).getKey(), tempTable[i].get(j).getValue());
			}
		}

	}

	/**
	 * Places a new hash table entry into an index of the hash table based on the
	 * hash function of the key. When the loadfactor of the hash table reaches a
	 * certain threshold, will call growTable() to double and rehash.
	 * 
	 * @param key   The key of the key value pair
	 * @param value The value of the key value pair
	 * @return True if hash table entry successfully added
	 */
	public boolean put(KeyType key, ValueType value) {
		if (key == null || containsKey(key)) {
			return false;
		}

		int index = hashFunction(key);

		if (hashTable[index] == null) {
			hashTable[index] = new LinkedList<>(); // Creates a new LinkedList for each entry
		}

		hashTable[index].add(new HashItem<KeyType, ValueType>(key, value));

		if (Double.compare(((double) ++size / (double) hashTable.length), LF_THRESHOLD) >= 0) {
			// Checks if loadfactor reaches assigned threshold
			growTable();
		}

		return true;
	}

	/**
	 * Accepts the key of the hash table entry as parameter and returns the value
	 * associated with that key.
	 * 
	 * @param key The key of the hash table entry from which to get the value
	 * @return The value associated with the key
	 * @throws NoSuchElementException if hashtable does not contain the desired key
	 */
	public ValueType get(KeyType key) throws NoSuchElementException {
		if (!containsKey(key)) {
			throw new NoSuchElementException();
		}
		int index = hashFunction(key); 
		ValueType value = null;
		for (int i = 0; i < hashTable[index].size(); i++) {
			if (hashTable[index].get(i).getKey().equals(key)) {
				value = hashTable[index].get(i).getValue();
			}
		}
		return value;
	}

	/**
	 * Returns the number of key value pairs within the hash table array.
	 * 
	 * @return Size of the array(Number of key value pairs)
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Checks whether a specific key exists within the hash table. Uses a hash
	 * function to locate a LinkedList which is iterated through to find the
	 * specified key.
	 * 
	 * @param key The key to search for
	 * @return True if the hash table contains such a key
	 */
	public boolean containsKey(KeyType key) {
		int index = hashFunction(key);// To directly get index of hashTable
		if (hashTable[index] == null) {// Checks if a LinkedList exists at this index to avoid NullPointerException
			return false;
		}
		for (int i = 0; i < hashTable[index].size(); i++) {
			if (hashTable[index].get(i).getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes an entry with a specified key from the hash table. If the key doesn't
	 * exist in the hash table, returns null. Locates the entry by using the hash
	 * function to find the LinkedList to iterate through, which contains the key.
	 * 
	 * @param key The key of the entry to remove
	 * @return The value of the removed key or null if the key doesn't exist
	 */
	public ValueType remove(KeyType key) {
		if (!containsKey(key)) {
			return null;
		}
		ValueType value = null;
		int index = hashFunction(key); // To directly get hashTable index
		for (int i = 0; i < hashTable[index].size(); i++) {
			if (hashTable[index].get(i).getKey().equals(key)) {
				value = hashTable[index].remove(i).getValue();
			}
		}
		size--;
		return value;
	}

	/**
	 * Clears the hash table of all key value pairs.
	 */
	public void clear() {
		hashTable = new LinkedList[hashTable.length];
		size = 0;
	}

	/**
	 * Creates a String representation of the hash table in order to review its
	 * contents and the way in which entries are arranged. Mainly used for testing.
	 * 
	 * @return A String representing the hash table and its contents
	 */
	@Override
	public String toString() { // For testing
		String str = "[";
		for (int i = 0; i < hashTable.length; i++) {
			if (hashTable[i] == null) {
				str += "[null]";
				if (i != hashTable.length - 1) {
					str += ", ";
				}
				continue;
			}
			str += "[";
			for (int j = 0; j < hashTable[i].size(); j++) {
				str += hashTable[i].get(j).getValue();
				if (j != hashTable[i].size() - 1) {
					str += ", ";
				}
			}
			if (i == hashTable.length - 1) {
				str += "]";
			} else {
				str += "], ";
			}

		}
		return str + "]";
	}

}
