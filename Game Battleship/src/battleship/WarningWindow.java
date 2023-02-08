package battleship;

import java.io.IOException;
import battleship.resources.BattleshipButton;
import battleship.resources.BattleshipResource;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.*;

public class WarningWindow {
	
	static boolean answer;
	
	public static boolean quitTask(String title, String message) throws IOException {
		Stage confirmWindow = new Stage();
		
		confirmWindow.initModality(Modality.APPLICATION_MODAL);
		confirmWindow.setTitle(title);
		// confirmWindow.setMinWidth(250);

		
		Label confirmExitMessage = BattleshipResource.CustomLabel(message, true, 350, 20);
		confirmExitMessage.setTextAlignment(TextAlignment.CENTER);
		
		Label emptySpace1 = BattleshipResource.EmptyLabel();
		Label emptySpace2 = BattleshipResource.EmptyLabel();
		
		BattleshipButton yesButton = new BattleshipButton("YES");
		yesButton.setOnAction(e -> {
			answer = true;
			confirmWindow.close();
		});
		
		BattleshipButton noButton = new BattleshipButton("NO");
		noButton.setOnAction(e -> {
			answer = false;
			confirmWindow.close();
		});
		
		// Background (BG) image
		Background background = BattleshipResource.ImageBackground2(960, 540);
		
		VBox quitLayoutTitle = new VBox(10);
		quitLayoutTitle.setAlignment(Pos.BOTTOM_CENTER);
		quitLayoutTitle.getChildren().addAll(emptySpace1, confirmExitMessage, emptySpace2);
		
		HBox quitLayoutButtons = new HBox(15);
		quitLayoutButtons.getChildren().addAll(yesButton, noButton);
		quitLayoutButtons.setAlignment(Pos.TOP_CENTER);
		
		BorderPane quitLayout = new BorderPane();
		quitLayout.setTop(quitLayoutTitle);
		quitLayout.setCenter(quitLayoutButtons);
		quitLayout.setBackground(background);
		
		Scene windowQuit = new Scene(quitLayout, 400, 200);
		
		confirmWindow.getIcons().add(BattleshipResource.ImageIcon());	
		confirmWindow.setScene(windowQuit);
		confirmWindow.setResizable(false);
		confirmWindow.showAndWait();
		
		return answer;
	}

}