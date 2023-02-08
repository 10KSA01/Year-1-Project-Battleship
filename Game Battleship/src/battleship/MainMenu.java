package battleship;

import java.io.IOException;
import java.util.Random;

import battleship.MainGame.Square;
import battleship.resources.BattleshipButton;
import battleship.resources.BattleshipResource;
import javafx.application.Application;

import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenu extends Application{
	public static Stage windowMain;
	private static final int windowWidth = 960;
	private static final int windowHeight = 540;	
    public static MainGame enemyBoard, cEnemyBoard;
    private static Random random = new Random();
    public static int counterShips, counterMonsters;
    public static TextArea statusReport;
    public static Label currentScore, finalScore;
    
    public static Ship AircraftCarrier, Battleship, Submarine, Destroyer, PatrolBoat, Kraken, Cetus;
    
	public static BorderPane layoutMenu = new BorderPane(); // Main Menu
	public static BorderPane layoutLobby = new BorderPane(); // Lobby Menu
	public static BorderPane layoutOriginalMode = new BorderPane(); // Original Game Mode Menu
	public static BorderPane layoutOption = new BorderPane(); // Option Menu
	public static BorderPane layoutEnd = new BorderPane();

	public static Scene sceneMain, sceneLobby,sceneOriginalGame, sceneOption, sceneEnd;
	
	public static boolean enableCheats;
	
	@SuppressWarnings("static-access")
	public void start(Stage mainStage) throws Exception{
		windowMain = mainStage;
		windowMain.setTitle("Battleship");
		
		sceneMain = new Scene(layoutMenu, windowWidth, windowHeight); // Main Menu
		sceneLobby = new Scene(layoutLobby, windowWidth, windowHeight); // Lobby Menu
		sceneOriginalGame = new Scene(layoutOriginalMode, windowWidth, windowHeight); // Original Game Mode Menu
		sceneOption = new Scene(layoutOption, windowWidth, windowHeight);
		sceneEnd = new Scene(layoutEnd, windowWidth, windowHeight);
		
		Background backgroundMain = BattleshipResource.ImageBackground1(windowWidth, windowHeight); // Background image
		
		// Main Menu -----------------------------------------
		HBox topMenu = new HBox();
		topMenu.setAlignment(Pos.CENTER);
		
		// Title image
		ImageView titleMain  = BattleshipResource.ImageTitle1();
		
		topMenu.getChildren().addAll(titleMain);
		
		// Centre of the menu		
		VBox centreMenu = new VBox(10); // putting an integers between the parameters puts a space between each object.
		centreMenu.setAlignment(Pos.CENTER);
		
		// Play button
		BattleshipButton  btnPlay = new BattleshipButton("PLAY");
		btnPlay.setOnAction(e -> {
			windowMain.setScene(sceneLobby);
		});	
		
		// Option button
		BattleshipButton btnOptions = new BattleshipButton("HELP");
		btnOptions.setOnAction(e -> windowMain.setScene(sceneOption));
		
		// Quit Button
		BattleshipButton btnQuit = new BattleshipButton("QUIT");
		btnQuit.setOnAction(e -> {
			try {
				quitProgram();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		windowMain.setOnCloseRequest(e -> {
			e.consume(); // this will allow the program to close properly otherwise it will still close if user presses no
			try {
				quitProgram();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		centreMenu.getChildren().addAll(btnPlay, btnOptions, btnQuit);
		
		layoutMenu.setTop(topMenu);
		layoutMenu.setCenter(centreMenu);
		layoutMenu.setBackground(backgroundMain);
		
		// Lobby Menu ----------------------------------
		HBox centreLobby = new HBox(10);
		centreLobby.setAlignment(Pos.CENTER);		
		
		// Original button
		BattleshipButton btnOriginal = new BattleshipButton("ORIGINAL");
		btnOriginal.setOnAction(e -> {
		    AircraftCarrier = new Ship(Math.random() < 0.5, 5, "AircraftCarrier");
		    Battleship = new Ship(Math.random() < 0.5, 4, "Battleship");
		    Submarine = new Ship(Math.random() < 0.5, 3, "Submarine");
		    Destroyer = new Ship(Math.random() < 0.5, 3, "Destroyer");
		    PatrolBoat = new Ship(Math.random() < 0.5, 2, "PatrolBoat");
		    Kraken = new Ship(Math.random() < 0.5, 1, "Kraken");
		    Cetus = new Ship(Math.random() < 0.5, 1, "Cetus"); 
			counterShips = 5;
			counterMonsters = 2;
			MainGame.ships = 5;
			
			windowMain.setScene(sceneOriginalGame);
	        enemyBoard = new MainGame(event -> {
	        	Square cell = (Square) event.getSource();
	            if (cell.wasShot)
	                return;
	            cell.shoot(); // this line lets the user shoot.
	            if (enemyBoard.ships == 0) {
	                windowMain.setScene(sceneEnd);
	            }

	        });
			startGame();
			layoutOriginalMode.setCenter(enemyBoard);
			statusReport.appendText("Aircraft Carrier is un-sunk \n");
			statusReport.appendText("Battleship is un-sunk \n");
			statusReport.appendText("Submarine is un-sunk \n");
			statusReport.appendText("Destroyer is un-sunk \n");
			statusReport.appendText("Patrol Boat is un-sunk \n");
		});
		// CLASSIC button
		BattleshipButton btnClassic = new BattleshipButton("CLASSIC");
		
		// Back button (lobby to main)
		BattleshipButton btnBackLobbyToMain = new BattleshipButton("BACK");
		btnBackLobbyToMain.setOnAction(e -> windowMain.setScene(sceneMain));
		
		CheckBox cbCheats = BattleshipResource.CustomCheckBox("Enable cheats?", 20);
		cbCheats.setOnAction(e -> {
			if (cbCheats.isSelected()) {
				enableCheats = true;
			}
			else if (!cbCheats.isSelected()) {
				enableCheats = false;
			}
		});
		
		centreLobby.getChildren().addAll(btnOriginal, btnClassic);
		
		layoutLobby.setTop(cbCheats);
		layoutLobby.setCenter(centreLobby);
		layoutLobby.setBottom(btnBackLobbyToMain);
		layoutLobby.setBackground(backgroundMain);
		
		// Option Menu -------------------------		
		Label labelHelpMessage1 = BattleshipResource.CustomLabel("1) The black squares indicates the shots that you have missed", true, 800, 20);
		Label labelHelpMessage2 = BattleshipResource.CustomLabel("2) The red squares indicates that you missed hitting a ship", true, 800, 20);
		Label labelHelpMessage3 = BattleshipResource.CustomLabel("3) The green squares indicates that you hit a sea monster", true, 800, 20);
		Label labelHelpMessage4 = BattleshipResource.CustomLabel("4) There are 5 different ships: Aircraft Carrier (size 5), Battleship (size 4), Submarine (size 3), Destroyer (size 3) and Patrol Boat (size 2) ", true, 800, 20);
		Label labelHelpMessage5 = BattleshipResource.CustomLabel("5) There are 2 different monsters: Kraken who will return your score back to 0 and Cetus who will move all un-sunk ships", true, 800, 20);
		Label labelHelpMessage6 = BattleshipResource.CustomLabel("6) You will win when you manage to destroy all the ships", true, 800, 20);
		
		VBox boxHelpMessage = new VBox(10);
		boxHelpMessage.getChildren().addAll(labelHelpMessage1, labelHelpMessage2, labelHelpMessage3, labelHelpMessage4, labelHelpMessage5, labelHelpMessage6);
		boxHelpMessage.setAlignment(Pos.CENTER);
		
		// Back button (lobby to main)
		BattleshipButton btnBackOptionToMain = new BattleshipButton("BACK");
		btnBackOptionToMain.setOnAction(e -> windowMain.setScene(sceneMain));
		
		layoutOption.setCenter(boxHelpMessage);
		layoutOption.setBottom(btnBackOptionToMain);
		layoutOption.setBackground(backgroundMain);
		
		// Original Game Mode -------------------*------        
		
		// I have set the game board in the Original button in the lobby
		VBox boxStatus = new VBox(10);
		Label statusLabel = BattleshipResource.CustomLabel("STATUS REPORT: ", false, 200, 10);
		statusReport = new TextArea();
		statusReport.setMaxHeight(400);
		statusReport.setMaxWidth(200);
		statusReport.setEditable(false);

		
		boxStatus.getChildren().addAll(statusLabel, statusReport);
		boxStatus.setAlignment(Pos.CENTER);
		
		HBox boxScore = new HBox();
		Label labelScore = BattleshipResource.CustomLabel("Score: ", true, 300, 20);
		currentScore = BattleshipResource.CustomLabel("0", true, 300, 20);
		boxScore.getChildren().addAll(labelScore, currentScore);
		boxScore.setAlignment(Pos.CENTER);
		
		BattleshipButton btnBackOriginalToLobby = new BattleshipButton("QUIT");
		btnBackOriginalToLobby.setOnAction(e -> {	
			try {
				boolean answer2 = WarningWindow.quitTask("CONFIRM QUITING GAME", "ARE YOU SURE YOU WANT TO QUIT YOUR CURRENT GAME?");
				if (answer2) { // There is no need for answer1	 == true because if answer1 is false then it won't close the program and if answer1 is true then it will close the program.
					windowMain.setScene(sceneEnd);
					layoutOriginalMode.getChildren().remove(enemyBoard);
					statusReport.setText("");
					currentScore.setText("0");
					MainGame.score = 0;
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		layoutOriginalMode.setTop(boxScore);
		layoutOriginalMode.setBottom(btnBackOriginalToLobby);
		layoutOriginalMode.setRight(boxStatus);
		layoutOriginalMode.setCenter(enemyBoard);
		layoutOriginalMode.setBackground(backgroundMain);
		
		// End Menu ----------------------
		HBox boxFinalScore = new HBox();
		Label labelFinalScore = BattleshipResource.CustomLabel("Your final Score: ", true, 900, 45);
		
		finalScore = BattleshipResource.CustomLabel("0", true, 300, 45);
		boxFinalScore.getChildren().addAll(labelFinalScore, finalScore);
		boxFinalScore.setAlignment(Pos.CENTER);
		
		VBox boxEnd = new VBox(40);
		
		BattleshipButton btnPlayAgain = new BattleshipButton("PLAY AGAIN");
		btnPlayAgain.setOnAction(e -> {
			windowMain.setScene(sceneLobby);
			statusReport.setText("");
			currentScore.setText("0");
			MainGame.ships = 5;
			MainGame.score = 0;
		});
		
		BattleshipButton btnEndToMain = new BattleshipButton("MAIN MENU");
		btnEndToMain.setOnAction(e -> {
			windowMain.setScene(sceneMain);
			statusReport.setText("");
			currentScore.setText("0");
			MainGame.ships = 5;
			MainGame.score = 0;
		});
		
		BattleshipButton btnQuitEnd = new BattleshipButton("QUIT");
		btnQuitEnd.setOnAction(e -> {
			try {
				quitProgram();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		boxEnd.getChildren().addAll(boxFinalScore, btnPlayAgain, btnEndToMain, btnQuitEnd);
		boxEnd.setAlignment(Pos.CENTER);
		
		layoutEnd.setCenter(boxEnd);
		layoutEnd.setBackground(backgroundMain);
		
		windowMain.getIcons().add(BattleshipResource.ImageIcon());		
		windowMain.setResizable(false); // prevents screen from being full screen
		windowMain.setScene(sceneMain);
		windowMain.show();
	}
	
	private void quitProgram() throws IOException {
		boolean answer1 = WarningWindow.quitTask("CONFIRM CLOSING PROGRAM", "ARE YOU SURE YOU WANT TO CLOSE THE PROGRAM?");
		if (answer1) { // There is no need for answer1 == true because if answer1 is false then it won't close the program and if answer1 is true then it will close the program.
			windowMain.close();	
		}
	}
	

    public static void startGame() {
        while (counterShips > 0) {
            if (counterShips == 5) {
            	int xShip1 = random.nextInt(10);;
            	int yShip1 = random.nextInt(10);;
              	if (enemyBoard.placeShip(AircraftCarrier, xShip1, yShip1)) {
              		counterShips--;
                      }
            }
            else if (counterShips == 4) {
            	int xShip2 = random.nextInt(10);;
            	int yShip2 = random.nextInt(10);;
            	if (enemyBoard.placeShip(Battleship, xShip2, yShip2)) {
              		counterShips--;
                      }
            }
            else if (counterShips == 3) {
            	int xShip3 = random.nextInt(10);;
            	int yShip3 = random.nextInt(10);;
              	if (enemyBoard.placeShip(Submarine, xShip3, yShip3)) {
              		counterShips--;
                      }
            }
            else if (counterShips == 2) {
            	int xShip4 = random.nextInt(10);;
            	int yShip4 = random.nextInt(10);;
              	if (enemyBoard.placeShip(Destroyer, xShip4, yShip4)) {
              		counterShips--;
                      }
            }
            else if (counterShips == 1) {
            	int xShip5 = random.nextInt(10);;
            	int yShip5 = random.nextInt(10);;
              	if (enemyBoard.placeShip(PatrolBoat, xShip5, yShip5)) {
              		counterShips--;
                      }
            }
        }
        
    	while (counterMonsters > 0) {
            if (counterMonsters == 2) {
            	int xMonster1 = random.nextInt(10);
                int yMonster1 = random.nextInt(10);
            	if (enemyBoard.placeMonster(Kraken, xMonster1, yMonster1)) {
            		counterMonsters--;
                    }
            }
            else if (counterMonsters == 1) {
            	int xMonster2 = random.nextInt(10);
            	int yMonster2 = random.nextInt(10);
            	if (enemyBoard.placeMonster(Cetus, xMonster2, yMonster2)) {
            		counterMonsters--;
                    }
            }
    	}
    }
	
	public static void main(String[] args) {
		launch(args);
	}	
}

