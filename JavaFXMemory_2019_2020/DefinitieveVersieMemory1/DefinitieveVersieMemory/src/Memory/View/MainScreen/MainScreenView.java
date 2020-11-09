package Memory.View.MainScreen;

import Memory.Model.game.Spelbord;
import Memory.Model.player.Speler;
import javafx.scene.layout.BorderPane;

public class MainScreenView extends BorderPane {
    private GameView gameView;
    private ScoreView scoreView;

    public MainScreenView(Spelbord spelbord, Speler speler) {
        initialiseNodes(spelbord, speler);
        layoutNodes();
    }

    private void initialiseNodes(Spelbord spelbord, Speler speler) {
        this.gameView = new GameView(spelbord);
        this.scoreView = new ScoreView(speler);
    }

    private void layoutNodes() {
        this.setLeft(gameView);
        this.setRight(scoreView);
    }

    GameView getGameView() {
        return gameView;
    }

    ScoreView getScoreView() {
        return scoreView;
    }
}
