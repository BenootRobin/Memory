package Memory.Model.game;

import Memory.Model.util.Coord;

//Eigenschappen van kaart

public class Kaart {
    private String afbeelding;
    private Coord coord;

    public Kaart(String afbeelding, int x, int y){
        this.afbeelding= afbeelding;
        this.coord = new Coord(x, y);
    }

    public String getAfbeelding(){
        return afbeelding;
    }

    public int getX() {
        return coord.getX();
    }

    public int getY() {
        return coord.getY();
    }

    public Coord getCoord() {
        return coord;
    }
}
