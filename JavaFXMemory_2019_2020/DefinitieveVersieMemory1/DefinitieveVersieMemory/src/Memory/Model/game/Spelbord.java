package Memory.Model.game;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

//Voorstellen grid kaarten

public class Spelbord {
    private Kaart[][] cards;
    private Difficulty difficulty;


    public Spelbord(Difficulty difficulty) {
        this.difficulty = difficulty;
        cards = new Kaart[difficulty.getRow()][difficulty.getColumn()];
        initialiseer();
    }

    public void initialiseer(){
        //Kaarten selecteren + schudden
        LinkedList<KaartImages> deck = new LinkedList<>(Arrays.asList(KaartImages.values()));
        deck.removeLast(); // Default kaart verwijderen uit de opties
        Collections.shuffle(deck);

        LinkedList<KaartImages> mogelijkheden = new LinkedList<>();
        for (int i = 0; i < (difficulty.getRow() * difficulty.getColumn()) / 2; i++) {
            KaartImages img = deck.removeFirst();
            mogelijkheden.add(img);
            mogelijkheden.add(img);
        }
        Collections.shuffle(mogelijkheden);

        //Opvullen grid
        for (int i = 0; i < difficulty.getRow(); i++) {
            for (int j = 0; j <difficulty.getColumn() ; j++) {
                Kaart kaart = new Kaart(mogelijkheden.removeFirst().getPath(), i, j);
                cards[i][j] = kaart;
            }
        }
    }

    public Kaart getHint(Kaart currentCard) {
        List<Kaart> kaarten = new LinkedList<>();
        for (Kaart[] k : cards) {
            kaarten.addAll(Arrays.asList(k));
        }

        //Huidige kaart kaart controleren met match adhv coordinaten en afbeelding
        for (Kaart kaart : kaarten) {
            if (kaart.getAfbeelding().equals(currentCard.getAfbeelding()) && !kaart.getCoord().equals(currentCard.getCoord())) {
                return kaart;
            }
        }

        return null;
    }

    public Kaart[][] getCards() {
        return cards;
    }
    public Difficulty getDifficulty() {
        return difficulty;
    }

}
