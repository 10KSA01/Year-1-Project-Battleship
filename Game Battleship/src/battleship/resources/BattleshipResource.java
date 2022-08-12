package battleship.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Font;

public class BattleshipResource {
	private static String fontLocation = "src/battleship/resources/KongtextFont.ttf";
	
	public static Background ImageBackground1(int imageWidth, int imageHeight) throws IOException {
		// Background (BG) image
		InputStream streamBackGround = new FileInputStream("src/battleship/resources/images/background.png");
		Image imgBackGround = new Image(streamBackGround);
		streamBackGround.close();
		// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
		BackgroundSize imgBackGroundSize = new BackgroundSize(imageWidth, imageHeight, true, true, true, false);
		// new BackgroundImage(image, repeatX, repeatY, position, size)
		BackgroundImage ImageBackground = new BackgroundImage(imgBackGround, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, imgBackGroundSize);
		Background background1 = new Background(ImageBackground);
		return background1;
	}
	
	public static Background ImageBackground2(int imageWidth, int imageHeight) throws IOException {
		// Background (BG) image
		InputStream streamBackGround = new FileInputStream("src/battleship/resources/images/background.png");
		Image imgBackGround = new Image(streamBackGround);
		streamBackGround.close();
		// new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
		BackgroundSize imgBackGroundSize = new BackgroundSize(960, 540, false, false, false, false);
		// new BackgroundImage(image, repeatX, repeatY, position, size)
		BackgroundImage backgroundImage = new BackgroundImage(imgBackGround, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, imgBackGroundSize);
		Background background2 = new Background(backgroundImage);
		return background2;
	}
	
	public static ImageView ImageTitle1() throws IOException {

		// Title image
		InputStream streamTitle = new FileInputStream("src/battleship/resources/images/Title.png");
		Image imgTitle = new Image(streamTitle);
		streamTitle.close();
		ImageView imgTitleView = new ImageView(imgTitle); 
		imgTitleView.setFitWidth(900);
		imgTitleView.setFitHeight(150);
		return imgTitleView;
	}
	public static Image ImageIcon() throws IOException {

		InputStream streamIcon = new FileInputStream("src/battleship/resources/images/iconGlow.png");
		Image imgIcon = new Image(streamIcon);
		streamIcon.close();
		return imgIcon;
	}
	
	public static Font FontGame(int textSize) throws IOException{
		Font fontGame = Font.loadFont (new FileInputStream(fontLocation), textSize);
		return fontGame;
	}
	
	public static Label CustomLabel(String text, boolean wrappingText, int textMaxWidth, int textSize) throws FileNotFoundException {
		Label labelGame = new Label();
		Font fontGame = Font.loadFont (new FileInputStream(fontLocation), textSize);
		labelGame.setText(text);
		labelGame.setWrapText(wrappingText);
		labelGame.setMaxWidth(textMaxWidth);
		labelGame.setFont(fontGame);
		labelGame.setStyle("-fx-text-fill: #ffffff;");
		return labelGame;
	}
	
	
	public static Label EmptyLabel() {
		Label emptyLabel = new Label(" ");
		return emptyLabel;
	}
	
	public static CheckBox CustomCheckBox(String text, int textSize) throws FileNotFoundException{
		CheckBox cbGame = new CheckBox();
		Font fontGame = Font.loadFont (new FileInputStream(fontLocation), textSize);
		cbGame.setText(text);
		cbGame.setFont(fontGame);
		cbGame.setStyle("-fx-text-fill: #ffffff;");
		return cbGame;
	}
	
	public static TextArea CustomTextArea(String text, boolean wrappingText, int textMaxWidth, int textSize) throws FileNotFoundException {
		TextArea textArea = new TextArea();
		Font fontGame = Font.loadFont (new FileInputStream(fontLocation), textSize);
		textArea.setText(text);
		textArea.setWrapText(wrappingText);
		textArea.setMaxWidth(textMaxWidth);
		textArea.setFont(fontGame);
		textArea.setStyle("-fx-text-fill: #ffffff;");
		return textArea;
	}

}
