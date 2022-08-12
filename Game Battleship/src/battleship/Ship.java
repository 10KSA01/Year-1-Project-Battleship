package battleship;

public class Ship {
    public boolean vertical = true; // orientation of the ship
    public int health; // health of the ship
    public String name;
    
    public Ship(boolean shipOrientation, int shipHealth, String shipName) {
    	vertical = shipOrientation;
    	health = shipHealth;
    	name = shipName;
    }

    public void hit() {  // if the ship is hit then it will lose 1 health
        health--;
    }

    public boolean isAlive() { // this checks if the ship still has health or not. If the health is greater than or equal to 0 then the ship is alive
        return health > 0;
    }
}

