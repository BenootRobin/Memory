package Memory.View.MainScreen;

import Memory.Model.player.Speler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

//Layout van de score tijdens het spel
public class ScoreView extends VBox {
    private Label naam;
    private Label score;
    private Label tijd;

    public ScoreView(Speler speler) {
        initialiseNodes(speler);
        layoutNodes();
    }
    private void initialiseNodes(Speler speler) {
        this.naam = new Label(speler.getNaam());
        this.score = new Label(speler.getScore().getKliks() + "");
        this.tijd = new Label("00:00");
    }

    private void layoutNodes() {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10, 50, 0, 50));
        this.getChildren().addAll(naam,score,tijd);
    }

    public Label getScore() {
        return score;
    }

    public Label getTijd() {
        return tijd;
    }
}
