package musicsearch.view;

import java.util.Scanner;

import musicsearch.model.Customer;
import musicsearch.model.MusicStorage;
import musicsearch.model.PlayList;
import musicsearch.model.PlayListStorage;

public class ConsoleView {

	public void displayWelcome() {
		String welcome = "*****************************************\n" + "*     Welcome to ljsu Music Storage     *\n"
				+ "*****************************************";
		System.out.println(welcome);
	}

	public void inputCustomerInfo(Customer customer) {
		Scanner input = new Scanner(System.in);
		System.out.println("음원 검색사이트를 이용하시려면 이름과 전화번호를 입력하세요.");
		System.out.print(">> 이름 : ");
		customer.setName(input.nextLine());
		System.out.print(">> 전화번호 : ");
		customer.setPhone(input.nextLine());
	}

	public int selectMenu(String[] menuList) {

		displayMenu(menuList);

		int menu;
		do {
			menu = readNumber(">> 메뉴 선택 : ");
			if (menu < 0 || menu >= menuList.length)
				System.out.println("0부터 " + (menuList.length - 1) + "까지의 숫자를 입력하세요.");
		} while (menu < 0 || menu >= menuList.length);
		return menu;
	}

	private void displayMenu(String[] menuList) {
		System.out.println("=========================================");
		for (int i = 1; i < menuList.length; i++) {
			System.out.println(menuList[i]);
		}
		System.out.println(menuList[0]);
		System.out.println("=========================================");
	}

	public int readNumber(String message) {
		if (message != null) {
			System.out.print(message);
		}
		Scanner input = new Scanner(System.in);
		try {
			int number = input.nextInt();
			return number;
		} catch (Exception e) {
			System.out.print("숫자를 입력하세요 :");
			return readNumber(message);
		}
	}

	public String readString(String message) {
		if (message != null) {
			System.out.print(message);
		}
		Scanner input = new Scanner(System.in);
		try {
			String str = input.nextLine();
			return str;
		} catch (Exception e) {
			System.out.print("문자를 입력하세요 :");
			return readString(message);
		}
	}

	public void showMessage(String message) {
		System.out.println(message);
	}

	public void displayMusicStorage(MusicStorage musicStorage) {
		for (int i = 0; i < musicStorage.getMusicListSize(); i++) {
			String musicInfo = (i + 1) + ". " + musicStorage.getMusicInfo(i);
			showMessage(musicInfo);
		}
	}
	
	public void displayMusicStorageInfo(MusicStorage musicStorage) {
		for (int i = 0; i < musicStorage.getMusicListSize(); i++) {
			String musicInfo = (i + 1) + ". " + musicStorage.getMusicIDInfo(i);
			showMessage(musicInfo);
		}
	}

	private void displayPlayLists(PlayListStorage playListStorage) {
		showMessage(playListStorage.getPlayLists());
	}

	public boolean askConfirm(String message, String yes) {
		System.out.print(message);
		Scanner input = new Scanner(System.in);
		if (input.nextLine().equals(yes))
			return true;
		return false;

	}

	public void errorMessage(String str) {
		switch (str) {
		case "empty" -> showMessage("목록이 비어있습니다.");
		case "nothingness" -> showMessage("존재하지 않습니다.");
		case "success" -> showMessage("작업을 성공적으로 완수 했습니다.");
		case "exists" -> showMessage("이미 존재합니다.");
		case "indexOutOfBound" -> showMessage("인덱스 값이 잘못되었습니다");
		default -> showMessage(str);
		}
	}

	public void displayPlaylistStorage(PlayListStorage mPlayListStorage) {
		showMessage(mPlayListStorage.getPlayLists());
	}

	public void displayPlayList(PlayList playList) {
		for(int i = 0; i < playList.getPlayListSize(); i += 1) {
			showMessage(playList.getMusicInfo(i));
		}
	}

}
