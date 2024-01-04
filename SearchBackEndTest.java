import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;

public class SearchBackEndTest {
	public static SearchBackEnd backend() {
		SearchBackEnd backend = new SearchBackEnd();
		backend.addSong(new tSongData("Bohemian Rhapsody", "Queen", 2000));
		backend.addSong(new tSongData("In my life", "The Beatles", 1966));
		backend.addSong(new tSongData("Sympathy for the devil", "The Rolling Stones", 1990));
		backend.addSong(new tSongData("It's my life", "Bon Jovi", 1997));
		backend.addSong(new tSongData("Bad guy", "Billie Eilish", 2017));
		backend.addSong(new tSongData("A ball", "B", 2001));
		backend.addSong(new tSongData("That a ball", "C", 2001));
		backend.addSong(new tSongData("Offer", "D", 1990));
		backend.addSong(new tSongData("Offer", "E", 1990));
		backend.addSong(new tSongData("Offer", "F", 1990));
		backend.addSong(new tSongData("Offer", "E", 1990));
		backend.addSong(new tSongData("Offer", "G", 1999));
		return backend;
	}

	public static boolean testFindTitles() {
		SearchBackEnd backend = backend();
		boolean pass = true;
		LinkedList<String> expected = new LinkedList<>();

		// Test 1| normal case
		expected.add("In my life");
		expected.add("It's my life");
		if (!backend.findTitles("my").equals(expected)) {
			System.out.println("testFindTitles error1| Did not return correct list of titles"
					+ "\nExpected: [In my life, It's my life]. Actual: " + backend.findTitles("my"));
			pass = false;
		}
		// Test 2| proper search for words
		expected = new LinkedList<>();
		expected.add("A ball");
		expected.add("That a ball");
		if (!backend.findTitles("a").equals(expected)) {
			System.out.println("testFindTitles error2| Returned list with some titles without specified word"
					+ "\nExpected: [A ball, That a ball]. Actual: " + backend.findTitles("a"));
			pass = false;
		}
		// Test 3| Duplicate titles
		expected = new LinkedList<>();
		expected.add("Offer");
		expected.add("Offer");
		expected.add("Offer");
		expected.add("Offer");
		if (!backend.findTitles("Offer").equals(expected)) {
			System.out.println("testFindTitles error3| returned incorrect list when looking for same song titles"
					+ "\nExpected: [Offer, Offer, Offer, Offer]. Actual: " + backend.findTitles("Offer"));
			pass = false;
		}
		// Test 4| Case sensitivity
		expected = new LinkedList<>();
		expected.add("Bohemian Rhapsody");
		if (!backend.findTitles("BoHeMiAn").equals(expected)) {
			System.out.println("testFindTitles error4| findtitles not case insensitive"
					+ "\nExpected: [Bohemian Rhapsody]. Actual: " + backend.findTitles("BoHeMiAn"));
			pass = false;
		}
		return pass;
	}

	public static boolean testFindArtists() {
		SearchBackEnd backend = backend();
		boolean pass = true;
		LinkedList<String> expected = new LinkedList<>();

		// Test 1| Normal case
		expected.add("The Beatles");
		expected.add("Bon Jovi");
		if (!backend.findArtists("life").equals(expected)) {
			System.out.println("testFindArtists() error1| Did not return proper list of artists"
					+ "\nExpected: [The Beatles, Bon Jovi]. Actual: " + backend.findArtists("life"));
			pass = false;
		}
		// Test 2| Duplicate titles with different artists
		expected = new LinkedList<>();
		expected.add("D");
		expected.add("E");
		expected.add("F");
		expected.add("G");
		if (!backend.findArtists("Offer").equals(expected)) {
			System.out.println("testFindArtists() error2| Did not return proper list for duplicate keys"
					+ "\nExpected: [D, E, F, G]. Actual: " + backend.findArtists("Offer"));
			pass = false;
		}
		return pass;
	}

	public static boolean testFindNumberOfSongsInYear() {
		SearchBackEnd backend = backend();

		if (backend.findNumberOfSongsInYear("Offer", 1990) != 3) {
			System.out.println("testFindNumberOfSongsInYear failed| Expected: 3. Actual: "
					+ backend.findNumberOfSongsInYear("Offer", 1990));
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		
		testFindTitles();
		testFindArtists();
		testFindNumberOfSongsInYear();
		
		

	}

}
