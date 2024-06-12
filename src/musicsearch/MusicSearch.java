package musicsearch;

import java.io.IOException;

import musicsearch.controller.MusicSearchController;
import musicsearch.model.MusicStorage;
import musicsearch.model.OwnStorage;
import musicsearch.model.PlayListStorage;
import musicsearch.view.ConsoleView;

public class MusicSearch {
	public static void main(String[] args) throws IOException {
		MusicStorage ms = new MusicStorage();
		PlayListStorage ps = new PlayListStorage();
		OwnStorage os = new OwnStorage();
		ConsoleView view = new ConsoleView();
		
		MusicSearchController mc = new MusicSearchController(ms, ps, os, view);
		mc.start();
	}	
}
