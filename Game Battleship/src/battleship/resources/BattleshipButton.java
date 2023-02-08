package battleship.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

public class BattleshipButton extends Button{
	private final String fontLocation = "src/battleship/resources/KongtextFont.ttf";
	private final String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #ffffff;";
	private final String buttonStyleHover = "-fx-background-color: transparent; -fx-text-fill: #00ff00;";
	
	public BattleshipButton(String buttonLabel) {
		setText(buttonLabel);
		setButtonFont();
		setButtonStyle();
		setButtonStyleOnHover();
		setButtonStyleLeaveHover();
		
	}
	private void setButtonFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(fontLocation), 40));
		} catch (FileNotFoundException e) {
			setFont(Font.font("Verdana", 23));
		}
	}
	
	private void setButtonStyle() {
		setStyle(buttonStyle);
	}	
	private void setButtonStyleOnHover() {
		setOnMouseEntered(e -> {
			setStyle(buttonStyleHover);
		});
	}
	
	private void setButtonStyleLeaveHover() {
		setOnMouseExited(e -> {
			setStyle(buttonStyle);
		});
	}
}
