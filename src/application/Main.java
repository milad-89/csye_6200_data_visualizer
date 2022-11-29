package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Main extends Application {
	private static Stage primaryStage;
	private static AnchorPane mainLayout1;
	private static BorderPane mainLayout2;
	
	
	@Override
	public void start(Stage primaryStage) throws IOException {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Data Visualizer App");
		showStartPage();
	}
	
	private void showStartPage() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("startPage.fxml"));
		mainLayout1 = loader.load();
		Scene scene = new Scene(mainLayout1);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void showMainView() throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("Main.fxml"));
		mainLayout2 = loader.load();
		Scene scene = new Scene(mainLayout2);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
