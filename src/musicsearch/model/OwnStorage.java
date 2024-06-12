package musicsearch.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OwnStorage {
	private ArrayList<Music> ownList = new ArrayList<Music>();
	private String ownStorageFileName = "ownStorage.txt";
	
	public OwnStorage() throws IOException {
		loadMusicFromFile();
	}

	private void loadMusicFromFile() {
		FileReader fr;
		try {
			fr = new FileReader(ownStorageFileName);
			BufferedReader br = new BufferedReader(fr);
			String idStr;
			while ((idStr = br.readLine()) != null && !idStr.equals("")) {
				int musicID = Integer.parseInt(idStr);
				String title = br.readLine();
				String artist = br.readLine();
				String composer = br.readLine();
				String genre = br.readLine();
				ownList.add(new Music(musicID, title, artist, composer, genre));
			}
			fr.close();
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void saveMusicListToFile() {
		try {
			FileWriter fw = new FileWriter(ownStorageFileName);
			for (Music music : ownList) {
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

	public boolean addMusic(int musicID, String title, String artist, String composer, String genre) {
		if (!checkMusicExist(musicID)) {
			Music music = new Music(musicID, title, artist, composer, genre);
			ownList.add(music);
			return true;
		}
		return false;
	}

	private boolean checkMusicExist(int musicID) {
		for (Music music : ownList) {
			if (music.getMusicID() == musicID) {
				return true;
			}
		}
		return false;
	}
}
