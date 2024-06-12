package musicsearch.controller;

import java.io.IOException;

import musicsearch.model.Admin;
import musicsearch.model.Customer;
import musicsearch.model.Music;
import musicsearch.model.MusicStorage;
import musicsearch.model.OwnStorage;
import musicsearch.model.PlayList;
import musicsearch.model.PlayListStorage;
import musicsearch.view.ConsoleView;

public class MusicSearchController {
	MusicStorage mMusicStorage;
	PlayListStorage mPlayListStorage;
	PlayList mPlayList;
	OwnStorage mOwnStorage;
	Customer mCustomer;
	Admin mAdmin;
	ConsoleView view;

	String[] menuList = { "0. Exit", "1. 음원 목록 보기", "2. 음원 검색", "3. 음원 구매", "4. 플레이리스트", "5. Admin Mode" };
	String[] playListStorageMenuList = { "0. Exit", "1. 플레이리스트 추가", "2. 플레이리스트 삭제", "3. 플레이리스트 접속" };
	String[] playListMenuList = { "0. Exit", "1. 음원 추가", "2. 음원 삭제", "3. 플레이리스트 구매" };
	String[] adminMenuList = { "0. Exit", "1. 음원 추가", "2. 음원 삭제", "3. 음원 수정" };

	public MusicSearchController(MusicStorage musicStorage, PlayListStorage playListStorage, OwnStorage ownStorage,
			ConsoleView view) {
		mMusicStorage = musicStorage;
		mPlayListStorage = playListStorage;
		mOwnStorage = ownStorage;
		mAdmin = new Admin();
		this.view = view;
	}

	public void start() {
		welcome();

		int menu;

		do {
			menu = view.selectMenu(menuList);

			switch (menu) {
			case 0 -> end();
			case 1 -> viewMusicStorage();
			case 2 -> searchMusic();
			case 3 -> puchaseMusic();
			case 4 -> playListStorageMode();
			case 5 -> adminMode();

			}

		} while (menu != 0);

	}

	private void playListStorageMode() {
		int menu;

		do {
			menu = view.selectMenu(playListStorageMenuList);

			switch (menu) {
			case 1 -> addPlayList();
			case 2 -> delPlayList();
			case 3 -> connectPlayList();
			}

		} while (menu != 0);
	}

	private void playListMode() {
		int menu;

		do {
			menu = view.selectMenu(playListMenuList);

			switch (menu) {
			case 1 -> addMusicToPlayList();
			case 2 -> delMusicInPlayList();
			case 3 -> purchasePlayList();
			}

		} while (menu != 0);
	}
	
	private void adminMode() {
		if(authenticateAdmin()) {
			int menu;
			
			do {
				menu = view.selectMenu(adminMenuList);
				
				switch (menu) {
				case 1 -> addMusicToStorage();
				case 2 -> delMusicInStorage();
				case 3 -> modifyMusicInStorage();
				}
				
			} while (menu != 0);
			
		}
	}

	private void modifyMusicInStorage() {
		if (!mMusicStorage.isEmpty()) {
			view.displayMusicStorageInfo(mMusicStorage);
			int index = view.readNumber("수정할 음원의 번호 입력 : ") - 1;
			int musicID = view.readNumber("MusicID : ");
			String title = view.readString("Title : ");
			String artist = view.readString("Artist : ");
			String composer = view.readString("Composer : ");
			String genre = view.readString("Genre : ");
			view.errorMessage(mMusicStorage.modifyMusicInfo(index, musicID, title, artist, composer, genre));
			mMusicStorage.saveMusicListToFile();
		} else {
			view.errorMessage("empty");
		}
	}

	private void delMusicInStorage() {
		if (!mMusicStorage.isEmpty()) {
			view.displayMusicStorageInfo(mMusicStorage);
			int index = view.readNumber("삭제할 음원의 번호 입력 : ") - 1;
			view.errorMessage(mMusicStorage.delMusic(index));
			mMusicStorage.saveMusicListToFile();
		} else {
			view.errorMessage("empty");
		}
	}

	private void addMusicToStorage() {
		int musicID = view.readNumber("MusicID : ");
		String title = view.readString("Title : ");
		String artist = view.readString("Artist : ");
		String composer = view.readString("Composer : ");
		String genre = view.readString("Genre : ");
		view.errorMessage(mMusicStorage.addMusic(musicID, title, artist, composer, genre));
		mMusicStorage.saveMusicListToFile();
	}

	private void puchaseMusic() {
		viewMusicStorageInfo();
		int musicID = view.readNumber("구매하려는 음원 MusicID 입력 : ");
		Music music = mMusicStorage.isValid(musicID);
		if (music != null) {
			if (view.askConfirm("진짜 구매하려면 yes를 입력하세요 : ", "yes")) {
				if (mOwnStorage.addMusic(music.getMusicID(), music.getTitle(), music.getArtist(), music.getComposer(),
						music.getGenre())) {
					mOwnStorage.saveMusicListToFile();
					view.showMessage("구매 완료.");
				} else {
					view.errorMessage("exists");
				}
			}
		} else {
			view.errorMessage("nothingness");
		}
	}

	private void delMusicInPlayList() {
		if (!mPlayList.isEmpty()) {
			view.displayPlayList(mPlayList);
			int index = view.readNumber("삭제할 음원의 번호 입력 : ") - 1;
			view.errorMessage(mPlayList.delMusic(index));
			mPlayList.saveMusicListToFile();
		} else {
			view.errorMessage("empty");
		}
	}

	private void purchasePlayList() {
		if (!mPlayList.isEmpty()) {
			view.displayPlayList(mPlayList);
			try {
				mOwnStorage = new OwnStorage();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (view.askConfirm("진짜 구매하려면 yes를 입력하세요 : ", "yes")) {
				int cnt = 0;
				for (int i = 0; i < mPlayList.getPlayListSize(); i += 1) {
					Music music = mPlayList.getMusic(i);
					if (!mOwnStorage.addMusic(music.getMusicID(), music.getTitle(), music.getArtist(),
							music.getComposer(), music.getGenre())) {
						cnt ++;
					}
				}
				mOwnStorage.saveMusicListToFile();
				if(cnt == mPlayList.getPlayListSize()) {
					view.showMessage("전부 보유한 음원입니다.");
				} else if(cnt != 0) {
					view.showMessage("보유 음원 "+cnt+"개를 제외하고 구매완료.");					
				} else {
					view.showMessage("전체 구매 완료.");
				}
			}
		} else {
			view.errorMessage("empty");
		}
	}

	private void addMusicToPlayList() {
		view.displayMusicStorage(mMusicStorage);
		int index = view.readNumber("추가할 음원의 번호 입력 : ") - 1;
		Music music = mMusicStorage.getMusic(index);
		view.errorMessage(mPlayList.addMusic(music.getMusicID(), music.getTitle(), music.getArtist(),
				music.getComposer(), music.getGenre()));
		mPlayList.saveMusicListToFile();
	}

	private void connectPlayList() {
		if (!mPlayListStorage.isEmpty()) {
			view.displayPlaylistStorage(mPlayListStorage);
			int index = view.readNumber("접속할 플레이리스트 번호 입력 : ") - 1;
			if (mPlayListStorage.isValidPlayList(index)) {
				String playListName = mPlayListStorage.getPlayListName(index);
				try {
					mPlayList = new PlayList(playListName);
					playListMode();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				view.errorMessage("indexOutOfBound");
			}
		} else {
			view.errorMessage("empty");
		}
	}

	private void delPlayList() {
		if (!mPlayListStorage.isEmpty()) {
			view.displayPlaylistStorage(mPlayListStorage);
			int index = view.readNumber("삭제할 플레이리스트 번호 입력 : ") - 1;
			view.errorMessage(mPlayListStorage.delPlayList(index));
		} else {
			view.errorMessage("empty");
		}
	}

	private void addPlayList() {
		String playListName = view.readString("추가할 플레이리스트 이름 : ");
		view.errorMessage(mPlayListStorage.addPlayList(playListName));
	}

	private boolean authenticateAdmin() {
		view.showMessage("관리자 모드 진입을 위한 관리자 id와 password 확인");
		String id = view.readString("관리자 ID : ");
		String password = view.readString("관리자 Password : ");
		if (!mAdmin.login(id, password)) {
			view.showMessage("관리자 ID 또는 Password가 잘못 입력되었습니다.");
			return false;
		}
		return true;
	}

	private void endAdminMode() {
		view.showMessage(">> 관리자 모드를 종료합니다.\n");
	}

	private void searchMusic() {
		if (!mMusicStorage.isEmpty()) {
			String musicInfo = mMusicStorage.searchMusic(view.readString("검색 키워드 입력 : "));
			view.errorMessage(musicInfo);
		} else {
			view.errorMessage("empty");
		}
	}

	private void welcome() {
		view.displayWelcome();
		registerCustomerInfo();
	}

	private void registerCustomerInfo() {
		mCustomer = new Customer();
		view.inputCustomerInfo(mCustomer);
	}

	private void end() {
		view.showMessage(">> ljsu Music Storage를 종료합니다.");
	}

	private void viewMusicStorage() {
		if (!mMusicStorage.isEmpty()) {
			view.displayMusicStorage(mMusicStorage);
		} else {
			view.errorMessage("empty");
		}
	}
	
	private void viewMusicStorageInfo() {
		if (!mMusicStorage.isEmpty()) {
			view.displayMusicStorageInfo(mMusicStorage);
		} else {
			view.errorMessage("empty");
		}
	}

}
