package battleship;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class MainGame extends Parent {
    private VBox rows = new VBox();
    public static int ships, numShipAlive, numMonsterAlive; // this shows the number ships in the game
    public int monsters = 2; // The sea monsters

    public static int score;
    public MainGame board;
    private static Random random = new Random();
    
    public MainGame(EventHandler<? super MouseEvent> handler) { // this creates the game board, boolean enemy indicates whether it is the enemy or the player board
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Square c = new Square(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }

            rows.getChildren().add(row);
        }

        getChildren().add(rows);
    }
    
// ORIGINAL
    public boolean placeShip(Ship ship, int x, int y) { // placing ships for classic mode
        if (canPlaceShip(ship, x, y)) {
            int length = ship.health;

            if (ship.vertical) { // vertical placing ships
                for (int i = y; i < y + length; i++) {
                    Square cell = getCell(x, i);
                    cell.ship = ship;
                    if (MainMenu.enableCheats == true) {
                    	cell.setFill(Color.YELLOW); // Cheat - shows the ships comment out to disable it 	
                    }
                    else if (MainMenu.enableCheats == false){
                    	cell.setFill(Color.WHITE);
                    }


                }
            }
            else { // horizontal placing ships
                for (int i = x; i < x + length; i++) {
                    Square cell = getCell(i, y);
                    cell.ship = ship;
                    if (MainMenu.enableCheats == true) {
                    	cell.setFill(Color.YELLOW); // Cheat - shows the ships comment out to disable it 	
                    }
                    else if (MainMenu.enableCheats == false){
                    	cell.setFill(Color.WHITE);
                    }
                }
            }

            return true;
        }

        return false;
    }

    public boolean placeMonster(Ship monster, int x, int y) { // placing ships for classic mode
        if (monster.health > 0) {
        	if (canPlaceShip(monster, x, y)) {
                Square cell = getCell(x, y);
                cell.monster = monster;
                if (MainMenu.enableCheats == true) {
                	cell.setFill(Color.BLUE); // Cheat - shows the ships comment out to disable it 	
                }
                else if (MainMenu.enableCheats == false){
                	cell.setFill(Color.WHITE);
                }

                return true;
            }	
        }
        return false;
    }

    public Square getCell(int x, int y) {

        return (Square)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    private Square[] getNeighbors(int x, int y) { // to get neighbouring coordinates
        Point2D[] points = new Point2D[] {
                new Point2D(x - 1, y),
                new Point2D(x + 1, y),
                new Point2D(x, y - 1),
                new Point2D(x, y + 1)
        };

        List<Square> neighbors = new ArrayList<Square>();

        for (Point2D p : points) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }

        return neighbors.toArray(new Square[0]);
    }

    private boolean canPlaceShip(Ship ship, int x, int y) {
        int length = ship.health;
        if (ship.vertical) {
            for (int i = y; i < y + length; i++) {
                if (!isValidPoint(x, i))
                    return false;

                Square cell = getCell(x, i);
                if (cell.ship != null)
                    return false;

                for (Square neighbor : getNeighbors(x, i)) { // this will prevent ships from being near each other
                    if (!isValidPoint(x, i))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }
        else {
            for (int i = x; i < x + length; i++) {
                if (!isValidPoint(i, y))
                    return false;

                Square cell = getCell(i, y);
                if (cell.ship != null)
                    return false;

                for (Square neighbor : getNeighbors(i, y)) { // this will prevent ships from being near each other
                    if (!isValidPoint(i, y))
                        return false;

                    if (neighbor.ship != null)
                        return false;
                }
            }
        }

        return true;
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10; // the grid is from 0 to 9 horizontally and vertically
    }

    public class Square extends Rectangle {
        public int x, y; // position of the cell in the game board
        public Ship ship = null; // if a cell does not have any part of the ship then this will be null 
        public Ship monster = null;
        public boolean wasShot = false; // this checks if the sell was shot or not
 
        private MainGame board;

        public Square(int x, int y, MainGame board) {
            super(40, 40);  // the width and height for each cell
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.WHITE); // cell fill colour
            setStroke(Color.BLACK);  // outline colour
        }

        public boolean shoot() {
            wasShot = true; // if the shell is shot then wasShot equals to true

            if (ship != null) { // if the ship was hit then the cell fill colour will change to red
                ship.hit();
                setFill(Color.RED);
                MainMenu.statusReport.appendText("My ship was hit! \n");
                
                if (ship.name == "AircraftCarrier") {
                	if (!ship.isAlive()) {
                		ships--;
                        MainMenu.statusReport.appendText("You sank my Aircraft Carrier! \n");
                		score = score + (5*2);
                	}
                }
                else if (ship.name == "Battleship") {
                	if (!ship.isAlive()) {
                		ships--;
                        MainMenu.statusReport.appendText("You sank my Battleship! \n");
                		score = score + (4*2);
                	}
                }
                else if (ship.name == "Submarine") {
                	if (!ship.isAlive()) {
                		ships--;
                        MainMenu.statusReport.appendText("You sank my Submarine! \n");
                		score = score + (3*2);
                	}
                }
                else if (ship.name == "Destroyer") {
                	if (!ship.isAlive()) {
                		ships--;
                        MainMenu.statusReport.appendText("You sank my Destroyer! \n");
                		score = score + (3*2);
                	}
                }
                else if (ship.name == "PatrolBoat") {
                	if (!ship.isAlive()) {
                		ships--;
                        MainMenu.statusReport.appendText("You sank my Patrol Boat! \n");
                		score = score + (2*2);

                	}
                }
        		MainMenu.currentScore.setText(Integer.toString(score));
        		MainMenu.finalScore.setText(Integer.toString(score));
                return true;  // if the ship is hit then it will return true and if the ship is missed then it will return false
            }
            else if (monster != null) { // if the sea monster was hit then the cell fill colour will change to red
            	monster.hit();
                setFill(Color.GREEN); 
                
                if (monster.name == "Kraken") {// if the sea monster is not alive then it will reduce the number of ships
                	if (!monster.isAlive()) {
                		board.monsters--;
                        MainMenu.statusReport.appendText("You hit the Kraken! \n");
                		score = 0;
                		MainMenu.currentScore.setText(Integer.toString(score));
                		MainMenu.finalScore.setText(Integer.toString(score));
                	}
                }
                else if (monster.name == "Cetus") {
                	if (!monster.isAlive()) {
                		board.monsters--;
                		numShipAlive = 0;
                		numMonsterAlive = 0;
                        MainMenu.statusReport.appendText("You hit the Cetus! \n");
                        Ship AircraftCarrier = new Ship(Math.random() < 0.5, 5, "AircraftCarrier");
                        Ship Battleship = new Ship(Math.random() < 0.5, 4, "Battleship");
                        Ship Submarine = new Ship(Math.random() < 0.5, 3, "Submarine");
                        Ship Destroyer = new Ship(Math.random() < 0.5, 3, "Destroyer");
                        Ship PatrolBoat = new Ship(Math.random() < 0.5, 2, "PatrolBoat");
                        Ship Kraken = new Ship(Math.random() < 0.5, 1, "Kraken"); 
                        
                        MainMenu.layoutOriginalMode.getChildren().remove(MainMenu.enemyBoard);
            	        
                        MainMenu.cEnemyBoard = new MainGame(event -> {
            	        	Square cell = (Square) event.getSource();
            	            if (cell.wasShot)
            	                return;
            	            cell.shoot(); // this line lets the user shoot.
            	            if (ships == 0) {
            	                MainMenu.windowMain.setScene(MainMenu.sceneEnd);
            	            }
            	        });
                        
                        
                        MainMenu.layoutOriginalMode.setCenter(MainMenu.cEnemyBoard);
                    	if (board.monsters == 1) {
                        	int xKraken = random.nextInt(10);
                        	int yKraken = random.nextInt(10);
                            if (MainMenu.cEnemyBoard.placeMonster(Kraken, xKraken, yKraken)) {
                            }
                    	}
                        
                        while (numShipAlive < 5) {

                            if (numShipAlive == 0) {
                                if (MainMenu.AircraftCarrier.isAlive() == true) {
                                	int xCetus1 = random.nextInt(10);
                                	int yCetus1 = random.nextInt(10);
                                    if (MainMenu.cEnemyBoard.placeShip(AircraftCarrier, xCetus1, yCetus1)) {
                                    	MainMenu.statusReport.appendText("Aircraft Carrier got moved! \n");
                                    	numShipAlive++;
                                    }      
                                }
                                else if (MainMenu.AircraftCarrier.isAlive() == false) {
                                	numShipAlive++;
                                }
                               
                            }
                            else if (numShipAlive == 1) {
                                if (MainMenu.Battleship.isAlive() == true) {
                                	int xCetus2 = random.nextInt(10);
                                	int yCetus2 = random.nextInt(10);
                                    if (MainMenu.cEnemyBoard.placeShip(Battleship, xCetus2, yCetus2)) {
                                    	MainMenu.statusReport.appendText("Battleship got moved! \n");
                                    	numShipAlive++;
                                    }
                                }
                                else if (MainMenu.Battleship.isAlive() == false) {
                                	numShipAlive++;
                                }
                            }
                            else if (numShipAlive == 2) {
                                if (MainMenu.Submarine.isAlive() == true) {
                                	int xCetus3 = random.nextInt(10);
                                	int yCetus3 = random.nextInt(10);
                                    if (MainMenu.cEnemyBoard.placeShip(Submarine, xCetus3, yCetus3)) {
                                    	MainMenu.statusReport.appendText("Submarine got moved! \n");
                                    	numShipAlive++;
                                    }
                                }
                                else if (MainMenu.Submarine.isAlive() == false) {
                                	numShipAlive++;
                                }
                            }
                            else if (numShipAlive == 3) {
                                if (MainMenu.Destroyer.isAlive() == true) {
                                	int xCetus4 = random.nextInt(10);
                                	int yCetus4 = random.nextInt(10);
                                    if (MainMenu.cEnemyBoard.placeShip(Destroyer, xCetus4, yCetus4)) {
                                    	MainMenu.statusReport.appendText("Destroyer got moved! \n");       
                                    	numShipAlive++;
                                    }
                                }
                                else if (MainMenu.Destroyer.isAlive() == false) {
                                	numShipAlive++;
                                }
                            }
                            else if (numShipAlive == 4) {
                                if (MainMenu.PatrolBoat.isAlive() == true) {
                                	int xCetus5 = random.nextInt(10);
                                	int yCetus5 = random.nextInt(10);
                                    if(MainMenu.cEnemyBoard.placeShip(PatrolBoat, xCetus5, yCetus5)) {
                                    	MainMenu.statusReport.appendText("Patrol Boat got moved! \n");
                                    	numShipAlive++;
                                    }
                                }
                                else if (MainMenu.Destroyer.isAlive() == false) {
                                	numShipAlive++;
                                }
                            }
                        }

                	}
                }
                MainMenu.currentScore.setText(Integer.toString(score));
                MainMenu.finalScore.setText(Integer.toString(score));
                return true;  // if the sea monster is hit then it will return true and if the sea monster is missed then it will return false
            }
            else if (ship == null) {
                setFill(Color.BLACK); // and will change the cell fill colour to black
                MainMenu.statusReport.appendText("You missed! \n");
                score--;
                MainMenu.currentScore.setText(Integer.toString(score));
                MainMenu.finalScore.setText(Integer.toString(score));
            }
          
            return false;
            
        }
    }

}