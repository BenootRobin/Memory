package Memory.Model;

import Memory.Model.game.Difficulty;
import Memory.Model.game.Kaart;
import Memory.Model.game.Spelbord;
import Memory.Model.game.Status;
import Memory.Model.player.Speler;
import Memory.Model.util.Coord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//Klasse waar alles bij elkaar komt

public class Memory {
    private Spelbord spelbord;
    private Speler speler;
    private LinkedList<Kaart> buffer;
    private ArrayList<Kaart> matchedCards;

    public Memory(Speler speler, Difficulty difficulty) {
        this.speler = speler;
        this.spelbord = new Spelbord(difficulty);
        this.buffer = new LinkedList<>();
        this.matchedCards = new ArrayList<>();

    }

    //Elke match in matchedCards wordt vergeleken met de gridsize
    public boolean isFinished(){
        int column = spelbord.getDifficulty().getColumn();
        int row = spelbord.getDifficulty().getRow();
        return matchedCards.size() == (row * column);
    }

    //Returns MATCH als er 2 kaarten in buffer zitten die overeenkomen en worden in matchedCards gestoken,
    // NOMATCH als er 2 kaarten in de buffer zitten die niet overeenkomen,
    // CHECKING als er maar 1 kaart in de buffer zit
    public Status checkCard(Kaart kaart){
        this.buffer.add(kaart);
        if (buffer.size() == 2){
           if (buffer.getFirst().getAfbeelding().equals(buffer.getLast().getAfbeelding())){
               matchedCards.addAll(buffer);
               return Status.MATCH;
           } else return Status.NOMATCH;
        }
        return Status.CHECKING;
    }

    //Geeft lijst van coordinaten van alle matched kaarten
    public List<Coord> getCoordsOfMatchedCards() {
        List<Coord> coords = new ArrayList<>();
        for (Kaart matchedCard : matchedCards) {
            coords.add(matchedCard.getCoord());
        }
        return coords;
    }

    public Spelbord getSpelbord() {
        return spelbord;
    }
    public Speler getSpeler() {
        return speler;
    }
    public LinkedList<Kaart> getBuffer() {
        return buffer;
    }

}

