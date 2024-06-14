package musicsearch.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MusicStorage {
	private ArrayList<Music> musicList = new ArrayList<Music>();
	private String musicStorageFileName = "musicStorage.txt";

	public MusicStorage() throws IOException {
		loadMusicFromFile();
	}

	private void loadMusicFromFile() {
		FileReader fr;
		try {
			fr = new FileReader(musicStorageFileName);
			BufferedReader br = new BufferedReader(fr);
			String idStr;
			while ((idStr = br.readLine()) != null && !idStr.equals("")) {
				int musicID = Integer.parseInt(idStr);
				String title = br.readLine();
				String artist = br.readLine();
				String composer = br.readLine();
				String genre = br.readLine();
				musicList.add(new Music(musicID, title, artist, composer, genre));
			}
			fr.close();
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMusicListToFile() {
		try {
			FileWriter fw = new FileWriter(musicStorageFileName);
			for (Music music : musicList) {
				fw.write(music.getMusicID() + "\n");
				fw.write(music.getTitle() + "\n");
				fw.write(music.getArtist() + "\n");
				fw.write(music.getComposer() + "\n");
				fw.write(music.getGenre() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String addMusic(int musicID, String title, String artist, String composer, String genre) {
		if (!checkMusicExist(musicID)) {
			Music music = new Music(musicID, title, artist, composer, genre);
			musicList.add(music);
			return "success";
		}
		return "exists";
	}

	private boolean checkMusicExist(int musicID) {
		for (Music music : musicList) {
			if (music.getMusicID() == musicID) {
				return true;
			}
		}
		return false;
	}

	public String delMusic(int index) {
		if (musicList.isEmpty()) {
			return "empty";
		}

		if (index >= musicList.size() || index < 0) {
			return "indexOutOfBound";
		}

		musicList.remove(index);
		return "success";
	}

	public String modifyMusicInfo(int index, int musicID, String title, String artist, String composer, String genre) {
		if (index < 0 || index >= musicList.size()) {
			return "indexOutOfBound";
		}
		if (checkMusicExist(musicID)) {
			return "exists";
		}
		Music music = musicList.get(index);
		music.setMusicID(musicID);
		music.setTitle(title);
		music.setArtist(artist);
		music.setComposer(composer);
		music.setGenre(genre);
		return "success";
	}
	
	public boolean checkIndex(int index) {
		if (index < 0 || index >= musicList.size()) {
			return false;
		}
		return true;
	}

	public int getMusicListSize() {
		return musicList.size();
	}

	public Music getMusic(int index) {
		return musicList.get(index);
	}

	public Music isValid(int musicID) {
		for (Music music : musicList) {
			if (music.getMusicID() == musicID) {
				return music;
			}
		}
		return null;
	}

	public boolean isEmpty() {
		if (musicList.isEmpty()) {
			return true;
		}

		return false;
	}

	public String getMusicInfo(int index) {
		if (index >= musicList.size() || index < 0) {
			return "indexOutOfBound";
		}
		return musicList.get(index).toString();
	}
	
	public String getMusicIDInfo(int index) {
		if (index >= musicList.size() || index < 0) {
			return "indexOutOfBound";
		}
		return musicList.get(index).musicInfo();
	}

	public String searchMusic(String str) {
		String musicInfo = "";
		for (Music music : musicList) {
			if (str.equals(music.getTitle()) || str.equals(music.getArtist()) || str.equals(music.getGenre())) {
				musicInfo += music.toString() + "\n";
			}
		}

		if (musicInfo.equals("")) {
			return "nothingness";
		}

		return musicInfo;
	}

	public static void main(String[] args) throws IOException {
		MusicStorage ms = new MusicStorage();
		System.out.println(ms.searchMusic("NWA"));
	}

}
