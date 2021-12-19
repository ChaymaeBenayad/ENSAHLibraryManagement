package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

public class MainApp extends Application {
	@Override
	public void start(Stage Stage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/view/FirstPage.fxml"));
			Scene scene = new Scene(root);
		     Stage.initStyle(StageStyle.TRANSPARENT);
			Stage.setScene(scene);
			Stage.show();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
}
