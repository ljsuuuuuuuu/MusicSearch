package musicsearch.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlayListStorage {
	private ArrayList<String> playListStorage = new ArrayList<>();
	private String playListStorageFileName = "playListStorage.txt";

	public PlayListStorage() throws IOException {
		loadMusicFromFile();
	}

	public String getPlayLists() {
		String playList = "";
		for(int i = 0; i < playListStorage.size(); i += 1) {
			playList += (i+1) + ". " + playListStorage.get(i) + "\n";
		}
		return playList;
	}
	
	private void loadMusicFromFile() {
		FileReader fr;
		try {
			fr = new FileReader(playListStorageFileName);
			BufferedReader br = new BufferedReader(fr);
			String playListName;
			while ((playListName = br.readLine()) != null && !playListName.equals("")) {
				playListStorage.add(playListName);
			}
			fr.close();
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMusicListToFile() {
		try {
			FileWriter fw = new FileWriter(playListStorageFileName);
			for (String playListName : playListStorage) {
				fw.write(playListName + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String addPlayList(String playListName) {
		if (checkPlayListExist(playListName)) {
			return "exists";
		}

		playListStorage.add(playListName);
		File f;
		try {
			f = new File(playListName + ".txt");
			f.createNewFile();
			saveMusicListToFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	private boolean checkPlayListExist(String inputName) {
		for (String playListName : playListStorage) {
			if (playListName.equals(inputName)) {
				return true;
			}
		}
		return false;
	}

	public String delPlayList(int index) {
		if (playListStorage.isEmpty()) {
			return "empty";
		}

		if (index >= playListStorage.size() || index < 0) {
			return "indexOutOfBound";
		}
		String playListName = playListStorage.get(index);
		playListStorage.remove(index);
		File f;
		try {
			f = new File(playListName + ".txt");
			f.delete();
			saveMusicListToFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	public boolean isValidPlayList(int index) {
		if (index >= playListStorage.size() || index < 0) {
			return false;
		}
		return true;
	}
	
	public String getPlayListName(int index) {
		return playListStorage.get(index);
	}

	public boolean isEmpty() {
		if (playListStorage.isEmpty()) {
			return true;
		}
		return false;
	}
	
}
