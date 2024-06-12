package musicsearch.model;

public class Music {
	private int musicID;
	private String title;
	private String artist;
	private	String composer;
	private String genre;
	
	public Music(int musicID, String title, String artist, String composer, String genre) {
		super();
		this.musicID = musicID;
		this.title = title;
		this.artist = artist;
		this.composer = composer;
		this.genre = genre;
	}

	@Override
	public String toString() {
		return "Title : " + title + " / Artist : " + artist + " / Composer : " + composer
				+ " / Genre : " + genre;
	}
	
	
	public String musicInfo() {
		return "MusicID : " + musicID + " / Title : " + title + " / Artist : " + artist + " / Composer : " + composer
				+ " / Genre : " + genre;
	}

	public void setMusicID(int musicID) {
		this.musicID = musicID;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public void setComposer(String composer) {
		this.composer = composer;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getMusicID() {
		return musicID;
	}
	public String getTitle() {
		return title;
	}
	public String getArtist() {
		return artist;
	}
	public String getComposer() {
		return composer;
	}
	public String getGenre() {
		return genre;
	}

}
