// --== CS400 Project One File Header ==--
// Name: Ethan Geoffrey Wijaya
// Email: egwijaya@wisc.edu
// Team: red
// Group: CI
// TA: Tingjia Cao
// Lecturer: Florian Heimerl
// Notes to Grader: 
/**
 * This class tests the methods of HashTableMap.java, including put(), get(),
 * containsKey(), remove(), size(), and clear()
 * 
 * @author Ethan
 *
 */
public class HashtableMapTests {

	/**
	 * Tests put() method. Checks for return value, size incrementation, and adding
	 * to table. Will also test if chaining is applied and if the table is doubled
	 * and rehashed properly. Also includes tests for improper keys that are either
	 * null or already existing in the table.
	 * 
	 * @return true if all tests pass
	 */
	public static boolean test1() {
		boolean pass = true;
		HashtableMap map = new HashtableMap(5);

		// 1) Basic put test
		if (!map.put(9, "A")) {
			System.out.println("test1 Error 1.1| put() returned false for proper value");
			pass = false;
		}
		if (map.size() != 1) {
			System.out.println("test1 Error 1.2| size not incremented properly after put()");
			pass = false;
		}
		if (!map.containsKey(9)) {
			System.out.println("test1 Error 1.3| put() did not add item properly");
			pass = false;
		}

		// 2) Chaining test
		map.put(4, "B");
		map.put(10, "C");
		if (!map.toString().equals("[[C], [null], [null], [null], [A, B]]")) {
			System.out.println("test1 Error 2| Chaining not implemented properly. "
					+ "\nExpected: [[C], [null], [null], [null], [A, B]]. \nActual: " + map);
			pass = false;
		}

		// 3) Grow table and rehash test
		try {
			map.put(7, "D");
			if (map.toString().equals("[[C], [null], [D], [null], [A, B]]")) {
				System.out.println("test1 Error 3.2| Table not doubled after reaching load factor");
				pass = false;
			}

			if (map.size() != 4) {
				System.out.println("test1 Error 3.3| size altered improperly. Expected: 4. Actual: " + map.size());
				pass = false;
			}

			if (!map.toString().equals("[[C], [null], [null], [null], [B], [null], [null], [D], [null], [A]]")) {
				System.out.println("test1 Error 3.4| Table not rehashed properly. "
						+ "\nExpected: [[C], [null], [null], [null], [B], [null], [null], [D], [null], [A]]"
						+ "\nActual: " + map);
				pass = false;
			}

		} catch (Exception e) {
			System.out.println("test1 Error 3.1| An error occured when growing the table");
			pass = false;
		}

		try {
			map.put(6, "E");
		} catch (Exception e) {
			System.out.println("test1 Error 3.5| An error occurred when adding an item to a rehashed table");
			pass = false;
		}

		// 4) put() null key and same key tests
		if (map.put(null, "F")) {
			System.out.println("test1 Error 4.1| put() should return false with null key");
			pass = false;
		}
		if (map.put(6, "F")) {
			System.out.println("test1 Error 4.2| put() should return false when trying to use same key");
			pass = false;
		}

		return pass;
	}

	/**
	 * Tests containsKey() method. Sees if it has a proper return value for the
	 * appropriate situations. Also checks if the method is functional with the
	 * HashTable's chaining structure.
	 * 
	 * @return true if all tests pass
	 */
	public static boolean test2() {
		boolean pass = true;
		HashtableMap map = new HashtableMap();

		// 1) basic containsKey() test
		map.put(3, "A");
		if (!map.containsKey(3)) {
			System.out.println("test2 Error 1.1| containsKey returned false for existing key");
			pass = false;
		}
		if (map.containsKey(2)) {
			System.out.println("test2 Error 1.2| containsKey returned true for nonexisting key");
			pass = false;
		}

		// 2) containsKey test with chaining
		map.put(23, "B");
		if (!map.containsKey(23)) {
			System.out.println("test2 Error 2.1| containsKey returned false for existing key in LinkedList");
			pass = false;
		}
		if (map.containsKey(33)) {
			System.out.println("test 2 Error 2.2| containsKey returned true for nonexisting key in LinkedList");
			pass = false;
		}

		return pass;
	}

	/**
	 * Test get() method. Tests functionality in returning correct value with
	 * chaining structure. Also tests if properly throws NoSuchElement exception
	 * when key doesn't exist.
	 * 
	 * @return true if all tests pass
	 */
	public static boolean test3() {
		boolean pass = true;
		HashtableMap map = new HashtableMap(10);

		// 1) basic get() test
		map.put(15, "A");
		try {
			if (!map.get(15).equals("A")) {
				System.out.println("test3 Error 1.1| get() failed to return proper value." + "Expected: A. Actual: "
						+ map.get(15));
				pass = false;
			}
		} catch (java.util.NoSuchElementException e) {
			System.out.println("test3 Error 1.2| failed to get key which exists");
			pass = false;
		} catch (Exception e) {

		}

		// 2) get() test with chaining
		map.put(5, "B");
		map.put(25, "C");
		try {
			if (!map.get(25).equals("C")) {
				System.out.println("test3 Error 2.1| get() failed to produce return value." + "Expected: C. Actual: "
						+ map.get(25));
				pass = false;
			}
		} catch (java.util.NoSuchElementException e) {
			System.out.println("test3 Error 2.2| failed to get key which exists");
			pass = false;
		} catch (Exception e) {

		}

		// 3) nonexistent key test
		try {
			map.get(2);
			System.out.println(
					"test3 Error 3.1| NoSuchElementException should be thrown when calling get() on nonexistent key");
			pass = false;
		} catch (java.util.NoSuchElementException e) {

		} catch (Exception e) {
			System.out
					.println("test3 Error 3.2| Wrong exception thrown for nonexistent key in get(). " + e.getMessage());
			pass = false;
		}

		return pass;
	}

	/**
	 * Tests remove() method. Tests if correct value is returned, entry is removed
	 * from table, and size is properly adjusted. Also tests if using nonexistent
	 * key as parameter.
	 * 
	 * @return true if all tests pass
	 */
	public static boolean test4() {
		boolean pass = true;
		HashtableMap map = new HashtableMap();

		// 1) remove() nonexistent key test
		if (map.remove(3) != null) {
			System.out.println("test4 Error 1| remove() should return null for nonexistent key");
			pass = false;
		}

		// 2) remove() correct value test
		map.put(12, "A");
		map.put(4, "B");
		map.put(9, "C");
		String str = (String) map.remove(12);
		if (!str.equals("A")) {
			System.out.println("test4 Error 2| remove() returned incorrect value." + "Expected: A. Actual: " + str);
			pass = false;
		}

		// 3) value removed from table test
		if (map.containsKey(12)) {
			System.out.println("test4 Error 3| table entry not properly removed");
			pass = false;
		}

		// 4) size test after remove
		if (map.size() != 2) {
			System.out.println("test4 Error 4| Size not altered properly.\nExpected: 2. Actual: " + map.size());
			pass = false;
		}

		return pass;
	}

	/**
	 * Tests size() and clear() method. Ensures size returns correct values for
	 * empty or partially filled tables. ALso tests the functionality of clear(), if
	 * it adjusts the size value accurately and it properly removes all entries from
	 * the table.
	 * 
	 * @return true if all tests pass
	 */
	public static boolean test5() {
		boolean pass = true;
		HashtableMap map = new HashtableMap();

		// 1) size test on empty table
		if (map.size() != 0) {
			System.out.println("test5 Error 1| Incorrect size returned. Expected: 0. Actual: " + map.size());
			pass = false;
		}

		map.put(14, "A");
		map.put(3, "B");
		// 2) basic size() test
		if (map.size() != 2) {
			System.out.println("test5 Error 2| Incorrect size returned. Expected: 2. Actual: " + map.size());
			pass = false;
		}

		// 3) clear() test
		map.clear();
		if (map.size() != 0) {
			System.out.println("test5 Error 3.1| Size should be 0 after clear. Size: " + map.size());
			pass = false;
		}
		if (map.containsKey(14) || map.containsKey(3)) {
			System.out.println("test5 Error 3.2| Some entries still exist after clear()");
			pass = false;
		}
		return pass;
	}

	public static void main(String[] args) {
		/*
		 * HashtableMap<Integer, String> map = new HashtableMap<Integer, String>(5);
		 * map.put(5, "B"); map.put(8, "C");
		 */
		// System.out.println(map.size());
		/*
		 * // Manual test Set 1 System.out.println(map.size());
		 * System.out.println(map.containsKey(8));
		 * System.out.println(map.containsKey(12)); System.out.println(map.get(5));
		 * System.out.println(map.remove(8)); try { System.out.println(map.get(8)); }
		 * catch (java.util.NoSuchElementException e) { System.out.println("YEET"); }
		 * map.clear(); System.out.println(map.size());
		 */

		// Manual Test Set 2 (Rehashing and shit)
		/*
		 * map.put(3, "E"); System.out.println(map); System.out.println(map.get(3));
		 * map.put(1, "P"); System.out.println(map);
		 */
		if (test1() && test2() && test3() && test4() & test5()) {
			System.out.println("All tests passed!");
		}

	}

}
