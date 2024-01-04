// --== CS400 Project One File Header ==--
// Name: Ethan Geoffrey Wijaya
// Email: egwijaya@wisc.edu
// Team: red
// Group: CI
// TA: Tingjia Cao
// Lecturer: Florian Heimerl
// Notes to Grader: 
/**
 * This class holds the key value pair for the hash table
 * 
 * @author Ethan
 *
 * @param <KeyType> Generic key 
 * @param <ValueType> Generic value
 */
public class HashItem<KeyType, ValueType> {
	private KeyType key;
	private ValueType value;

	/**
	 * Constructor for inputting key and value.
	 * 
	 * @param k The key
	 * @param v The value
	 */
	public HashItem(KeyType k, ValueType v) {
		this.key = k;
		this.value = v;
	}

	/**
	 * Gets the key in the key value pair
	 * 
	 * @return The key
	 */
	public KeyType getKey() {
		return this.key;
	}

	/**
	 * Gets the value in the key value pair
	 * 
	 * @return The value
	 */
	public ValueType getValue() {
		return this.value;
	}
}
