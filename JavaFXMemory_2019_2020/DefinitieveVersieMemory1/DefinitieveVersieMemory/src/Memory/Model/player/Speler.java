package Memory.Model.player;

import java.time.Duration;
import java.time.Instant;

//Eigenschappen van een speler (naam, score, starttijd (voor berekening speelduur))

public class Speler {
    private final String naam;
    private final Score score;
    private final Instant startTime;


    public Speler(String naam){
        this.naam=naam;
        this.score= new Score();
        this.startTime = Instant.now();
    }
    public String getNaam(){
        return naam;
    }
    public Score getScore() {
        return score;
    }
    public void refreshTimer(){
        this.getScore().setDuratie(Duration.between(startTime, Instant.now()).toMillis());
    }

}

