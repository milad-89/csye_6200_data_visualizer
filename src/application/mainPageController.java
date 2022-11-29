package application;

import java.io.IOException;

import javafx.fxml.FXML;
import application.Main;

public class mainPageController {

	public Main main;
		
	@FXML
	private void goToMenu() throws IOException {
		main.showMainView();
	}
	
}