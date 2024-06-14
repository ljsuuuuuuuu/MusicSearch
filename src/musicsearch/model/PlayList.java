package musicsearch.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayList {
	private ArrayList<Music> playList = new ArrayList<>();
	private String playListFileName;
	
	public PlayList(String playListFileName) throws IOException {
		this.playListFileName = playListFileName;
		loadMusicFromFile();
	}
	
	public String getMusicInfo(int index) {
		return playList.get(index).musicInfo();
	}
	
	public Music getMusic(int index) {
		return playList.get(index);
	}
	
	private void loadMusicFromFile() {
		FileReader fr;
		try {
			fr = new FileReader(playListFileName+".txt");
			BufferedReader br = new BufferedReader(fr);
			String idStr;
			while ((idStr = br.readLine()) != null && !idStr.equals("")) {
				int musicID = Integer.parseInt(idStr);
				String title = br.readLine();
				String artist = br.readLine();
				String composer = br.readLine();
				String genre = br.readLine();
				playList.add(new Music(musicID, title, artist, composer, genre));
			}
			fr.close();
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayListSize() {
		return playList.size();
	}
	
	public void saveMusicListToFile() {
		try {
			FileWriter fw = new FileWriter(playListFileName+".txt");
			for (Music music : playList) {
				fw.write("");
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
	
	private boolean checkMusicExist(int musicID) {
		for (Music music : playList) {
			if (music.getMusicID() == musicID) {
				return true;
			}
		}
		return false;
	}
	
	public String addMusic(int musicID, String title, String artist, String composer, String genre) {
		if (!checkMusicExist(musicID)) {
			Music music = new Music(musicID, title, artist, composer, genre);
			playList.add(music);
			return "success";
		}
		return "exists";
	}
	
	public String delMusic(int index) {
		if(playList.isEmpty()) {
			return "empty";
		}
		
		if(index >= playList.size() || index < 0) {
			return "indexOutOfBound";
		}
		
		playList.remove(index);
		return "success";
	}

	public boolean isEmpty() {
		if(playList.isEmpty()) {
			return true;
		}
		return false;
	}
	
}
