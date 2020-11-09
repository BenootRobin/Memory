package Memory.Model.player;

import java.time.LocalDate;

//Eigenschappen van de score (datum,kliks en tijd)

public class Score {
    private long duratie;
    private LocalDate datum;
    private int kliks;


    public Score (){
        this.datum=LocalDate.now();
        this.kliks=0;
    }

    public void verhogenKliks() {
        this.kliks++;
    }
    public int getKliks() {
        return kliks;
    }
    public long getDuratie() {
        return duratie;
    }
    public void setDuratie(long duratie) {
        this.duratie = duratie;
    }
    public LocalDate getDatum() {
        return datum;
    }





}
