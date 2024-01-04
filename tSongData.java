// interface (implemented with proposal)

interface SongDataInterface {
    public String getTitle();
    public String getArtist();
    public int getYearPublished();
}

// public class (implemented primarilly in final app week)

public class tSongData implements SongDataInterface {

	private String title;
	private String artist;
	private int year;
	
	public tSongData(String title, String artist, int year) {
		this.title = title;
		this.artist = artist;
		this.year = year;
	}
    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getArtist() {
        return this.artist;
    }

    @Override
    public int getYearPublished() {
        return this.year;
    }

}