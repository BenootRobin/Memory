package Memory.View.MainScreen;

import Memory.Model.game.KaartImages;
import Memory.Model.game.Spelbord;
import Memory.Model.util.Coord;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.List;

//Layout spelbord

public class GameView extends GridPane {
    private ImageView[][] cells;

    public GameView(Spelbord spelbord) {
        initialiseGrid(spelbord);
        layoutNodes();
    }

    private void initialiseGrid(Spelbord spelbord) {
        cells = new ImageView[spelbord.getDifficulty().getRow()][spelbord.getDifficulty().getColumn()];

        for (int i = 0; i <spelbord.getDifficulty().getRow(); i++) {
            for (int j = 0; j < spelbord.getDifficulty().getColumn(); j++) {
                cells[i][j] = new ImageView(KaartImages.DEFAULT.getPath());
            }
        }
    }


    private void layoutNodes() {
        this.setPadding(new Insets(5.0));
        for (int i = 0; i < this.cells.length; i++) {
            for (int j = 0; j < this.cells[i].length; j++) {
                this.cells[i][j].setFitHeight(100);
                this.cells[i][j].setFitWidth(100);
                this.add(this.cells[i][j], i, j);
                GridPane.setMargin(this.cells[i][j], new Insets(3.0));
            }
        }
    }

    public void enableAllButtonsExcept(List<Coord> excludedCoords) {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                Coord coord = new Coord(i, j);
                if (!excludedCoords.contains(coord)) {
                    this.cells[i][j].setDisable(false);
                }
            }
        }
    }

    //Disable zodat er geen 3 kaarten tegelijk/al gevonden matches meer aangeklikt kunnen worden
    public void disableAllButtons() {
        for (ImageView[] cell : cells) {
            for (ImageView imageView : cell) {
                imageView.setDisable(true);
            }
        }
    }


    //Highlighter om de huidige cel aan te duiden
    public void highlightButton(int x, int y) {
        for (ImageView[] cell : cells) {
            for (ImageView imageView : cell) {
                imageView.setEffect(null);
            }
        }

        ImageView targetButton = this.cells[x][y];
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(0, 1, 0));
        targetButton.setEffect(dropShadow);
    }

    //Highlighter om de oplossing weer te geven
    public void highlightButtonTemp(int x, int y) {
        ImageView targetButton = this.cells[x][y];

        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(5.0);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);
        dropShadow.setColor(Color.color(1, 0, 0));
        targetButton.setEffect(dropShadow);

        //Timer om oplossingseffect uit te zetten na 2.5sec
        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(2500),
                        ae -> {
                            targetButton.setEffect(null);
                        })
        );
        timeline.play();
    }

    ImageView[][] getCells() {
        return cells;
    }
}
