import java.util.List;
import java.util.LinkedList;

// interface (implemented with proposal)

interface SearchBackEndInterface {
	public void addSong(SongDataInterface song);

	public boolean containsSong(SongDataInterface song);

	// returns list of the titles of all songs that contain the word titleWord in
	// their song title
	public List<String> findTitles(String titleWord);

	// returns list of the artists of all songs that contain the word titleWord in
	// their song title
	public List<String> findArtists(String titleWord);

	// returns the number of songs that contain the word titleWord in their song
	// title, and were published in year
	public int findNumberOfSongsInYear(String titleWord, int year);
}

public class SearchBackEnd implements SearchBackEndInterface {

	private HashtableMap<String, List<SongDataInterface>> hashtable = new HashtableMap<String, List<SongDataInterface>>();

	private String[] createKeys(SongDataInterface song) {
		String key = song.getTitle().trim().toLowerCase();

		for (int i = 0; i < key.length(); i++) {
			if (!Character.isLetter(key.charAt(i)) && !Character.isWhitespace(key.charAt(i)) && key.charAt(i) != '\''
					&& key.charAt(i) != '-') {
				key = key.substring(0, i) + " " + key.substring(i + 1);
			}
		}
		String[] keyList = key.split(" ");
		return keyList;
	}

	@Override
	public void addSong(SongDataInterface song) {
		if (song == null || containsSong(song)) {
			return;
		}
		String[] keyList = createKeys(song);
		List<SongDataInterface> values;

		for (String key : keyList) {
			if (!hashtable.containsKey(key)) {
				values = new LinkedList<>();
				values.add(song);
				hashtable.put(key, values);
			} else {
				values = hashtable.remove(key);
				values.add(song);
				hashtable.put(key, values);
			}
		}
	}

	@Override
	public boolean containsSong(SongDataInterface song) {
		String[] keyList = createKeys(song);
		List<SongDataInterface> value;
		for (int i = 0; i < keyList.length; i++) {
			if (!hashtable.containsKey(keyList[i])) {
				return false;
			}
			value = hashtable.get(keyList[i]);
			boolean found = false;
			for (int j = 0; j < value.size(); j++) {
				found = value.get(j).getTitle().equals(song.getTitle())
						&& value.get(j).getArtist().equals(song.getArtist())
						&& value.get(j).getYearPublished() == song.getYearPublished();
				if (found) {
					break;
				}
			}
			if (!found) {
				return false;
			}
		}

		return true;
	}

	@Override
	public List<String> findTitles(String titleWord) {
		try {
			List<SongDataInterface> songs = hashtable.get(titleWord.toLowerCase());
			List<String> titles = new LinkedList<>();
			for (SongDataInterface song : songs) {
				titles.add(song.getTitle());
			}
			return titles;
		} catch (java.util.NoSuchElementException e) {
			return new LinkedList<String>();
		}
	}

	@Override
	public List<String> findArtists(String titleWord) {
		try {
			List<SongDataInterface> songs = hashtable.get(titleWord.toLowerCase());
			List<String> artists = new LinkedList<>();
			for (SongDataInterface song : songs) {
				artists.add(song.getArtist());
			}
			return artists;
		} catch (java.util.NoSuchElementException e) {
			return new LinkedList<>();
		}
	}

	@Override
	public int findNumberOfSongsInYear(String titleWord, int year) {
		try {
			List<SongDataInterface> songs = hashtable.get(titleWord.toLowerCase());
			int numSongs = 0;
			for (SongDataInterface song : songs) {
				if (song.getYearPublished() == year) {
					numSongs++;
				}
			}
			return numSongs;
		} catch (java.util.NoSuchElementException e) {
			return 0;
		}
	}

}
