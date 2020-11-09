package Memory.View.MainScreen;

import Memory.Model.Memory;
import Memory.Model.game.Kaart;
import Memory.Model.game.KaartImages;
import Memory.Model.game.Status;
import Memory.Model.player.Speler;
import Memory.Model.util.Coord;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class MainScreenPresenter {
    private Memory model;
    private MainScreenView view;
    private Timeline timerTimeLine;
    private Coord selectedCoord;

    public MainScreenPresenter(Memory model, MainScreenView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        initialiseTimer();
        selectedCoord = new Coord(0, 0);
        this.view.getGameView().highlightButton(0, 0);
    }

    private void addEventHandlers() {
        for (int i = 0; i < model.getSpelbord().getDifficulty().getRow(); i++) {
            for (int j = 0; j < model.getSpelbord().getDifficulty().getColumn(); j++) {
                final int row = i;
                final int column = j;

                view.getGameView().getCells()[i][j].setOnMousePressed(event -> {
                    // Left Click
                    if (event.getButton().equals(MouseButton.PRIMARY)) {
                        handleLeftClick(row, column);
                    }

                    // Right click
                    if (event.getButton().equals(MouseButton.SECONDARY)) {
                        handleRightClick(row, column);
                    }
                });
            }
        }
    }

    //Keyinputs controleren om cel te kiezen, pijltjes, tab, enter, CTRL-enter(tip)
    public void addWindowEventHandlers(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent keyEvent) -> {
            int x = selectedCoord.getX();
            int y = selectedCoord.getY();
            int lastRow = view.getGameView().getRowCount() - 1;
            int lastColumn = view.getGameView().getColumnCount() - 1;

            boolean eventHandled = false;

            KeyCodeCombination kb = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
            if (kb.match(keyEvent)) {
                handleRightClick(x, y);
                eventHandled = true;
            }

            if (!eventHandled) {
                switch (keyEvent.getCode()) {
                    case UP:
                        if (y == 0) {
                            y = lastRow;
                        } else {
                            y--;
                        }
                        break;
                    case RIGHT:
                        if (x == lastColumn) {
                            x = 0;
                        } else {
                            x++;
                        }
                        break;
                    case DOWN:
                        if (y == lastRow) {
                            y = 0;
                        } else {
                            y++;
                        }
                        break;
                    case LEFT:
                        if (x == 0) {
                            x = lastColumn;
                        } else {
                            x--;
                        }
                        break;
                    case TAB:
                        if (x == lastColumn && y == lastRow) {
                            y = 0;
                            x = 0;
                        } else if (x == lastColumn) {
                            y++;
                            x = 0;
                        } else {
                            x++;
                        }
                        break;
                    case ENTER:
                        handleLeftClick(x, y);
                }

                selectedCoord = new Coord(x, y);
                view.getGameView().highlightButton(selectedCoord.getX(), selectedCoord.getY());
            }

            keyEvent.consume();
        });
    }

    private void handleLeftClick(int row, int column) {

        ImageView currentButton = view.getGameView().getCells()[row][column];
        Kaart currentKaart = model.getSpelbord().getCards()[row][column];
        currentButton.setImage(new Image(currentKaart.getAfbeelding()));
        Status status = model.checkCard(currentKaart);

        if (status != Status.CHECKING) {
            view.getGameView().disableAllButtons();
        }

        model.getSpeler().getScore().verhogenKliks();
        view.getScoreView().getScore().setText(model.getSpeler().getScore().getKliks() + "");

        switch (status) {
            case MATCH: {
                // Timeline
                // https://tomasmikula.github.io/blog/2014/06/04/timers-in-javafx-and-reactfx.html
                Timeline timeline = new Timeline(
                        new KeyFrame(
                                Duration.millis(2500),
                                ae -> {
                                    model.getBuffer().clear();
                                    view.getGameView().enableAllButtonsExcept(model.getCoordsOfMatchedCards());
                                })
                );
                timeline.play();

                //Op het einde van het spel de score wegschrijven naar een texfile
                if (model.isFinished()) {
                    timerTimeLine.stop();
                    writeScoreToTextfile();
                    showGameOverPopup();
                }

                break;
            }
            case NOMATCH: {
                // https://tomasmikula.github.io/blog/2014/06/04/timers-in-javafx-and-reactfx.html
                Timeline timeline = new Timeline(
                        new KeyFrame(
                                Duration.millis(2500),
                                ae -> {
                                    currentButton.setImage(new Image(KaartImages.DEFAULT.getPath()));

                                    Kaart otherKaart = model.getBuffer().getFirst();
                                    ImageView otherButton = view.getGameView().getCells()[otherKaart.getX()][otherKaart.getY()];
                                    otherButton.setImage(new Image(KaartImages.DEFAULT.getPath()));
                                    model.getBuffer().clear();

                                    view.getGameView().enableAllButtonsExcept(model.getCoordsOfMatchedCards());
                                })
                );
                timeline.play();
                break;
            }
            case CHECKING:
                Kaart otherKaart = model.getBuffer().getFirst();
                ImageView otherButton = view.getGameView().getCells()[otherKaart.getX()][otherKaart.getY()];
                otherButton.setDisable(true);
                break;
        }
    }

    //RightClick om oplossing weer te geven
    private void handleRightClick(int row, int column) {
        Kaart currentKaart = model.getSpelbord().getCards()[row][column];
        Kaart hintKaart = model.getSpelbord().getHint(currentKaart);
        Coord coord = hintKaart.getCoord();
        view.getGameView().highlightButtonTemp(coord.getX(), coord.getY());
    }

    private void initialiseTimer() {
        timerTimeLine = new Timeline(
                new KeyFrame(
                        Duration.millis(1000),
                        ae -> {
                            // Updates timer
                            model.getSpeler().refreshTimer();

                            // Visualizes timer
                            String timerString = formatTimeString();
                            view.getScoreView().getTijd().setText(timerString);

                            // Audio timer
                            try {
                                File file = new File(
                                        getClass().getClassLoader().getResource("audio/Timer.mp3").getFile()
                                );
                                Media media = new Media(file.toURI().toString());
                                MediaPlayer mediaPlayer = new MediaPlayer(media);
                                mediaPlayer.setAutoPlay(true);
                            } catch (NullPointerException e) {
                                System.out.println("Could not find audio file.");
                            }

                        })
        );
        timerTimeLine.setCycleCount(Animation.INDEFINITE);
        timerTimeLine.play();
    }

    private void showGameOverPopup() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText("You have completed the game!");

        String winMessage = String.format("%s - %s - %d", model.getSpeler().getNaam(), formatTimeString(), model.getSpeler().getScore().getKliks());
        alert.setContentText(winMessage);

        ButtonType restart = new ButtonType("Restart");
        ButtonType quit = new ButtonType("Quit");
        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(restart, quit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == restart) {
            Speler speler = new Speler(model.getSpeler().getNaam());
            Memory memory = new Memory(speler, model.getSpelbord().getDifficulty());
            MainScreenView mainView = new MainScreenView(memory.getSpelbord(), speler);
            new MainScreenPresenter(memory, mainView);
            view.getScene().setRoot(mainView);
            mainView.getScene().getWindow().sizeToScene();
        } else if (result.get() == quit) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void writeScoreToTextfile() {
        String userHomeFolder = System.getProperty("user.home");
        File textFile = new File(userHomeFolder + "/Desktop", "memory-score.txt");
        try (BufferedWriter out = new BufferedWriter(new FileWriter(textFile, true))) {
            String score = String.format(
                    "%s - %s - %s - %d%n",
                    model.getSpeler().getScore().getDatum(),
                    model.getSpeler().getNaam(),
                    formatTimeString(),
                    model.getSpeler().getScore().getKliks()
            );

            out.write(score);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatTimeString() {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(model.getSpeler().getScore().getDuratie()),
                TimeUnit.MILLISECONDS.toSeconds(model.getSpeler().getScore().getDuratie()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(model.getSpeler().getScore().getDuratie()))
        );
    }
}
