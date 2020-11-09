package Memory.View.StartScreen;


import Memory.Model.game.Difficulty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import java.util.List;

//Layout van de startscreen

public class StartScreenView extends BorderPane {
    private Button btnStart;
    private Button btnAbout;
    private TextField txtNaam;
    private ComboBox<Difficulty> cbDifficulty;
    private VBox box;

    public StartScreenView(){
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes(){
        this.btnStart= new Button("Start");
        this.btnAbout= new Button("About");
        this.txtNaam = new TextField();
        this.box = new VBox();
        this.cbDifficulty = new ComboBox<>();
        cbDifficulty.getItems().addAll(List.of(Difficulty.values()));
    }

    private void layoutNodes(){
        setPrefSize(300,300);
        box.getChildren().addAll(txtNaam, cbDifficulty, btnStart);
        box.setAlignment(Pos.CENTER);
        box.setSpacing(25);
        cbDifficulty.getSelectionModel().select(0);

        btnStart.setPrefWidth(120);
        btnStart.setId("start");
        btnStart.setPrefHeight(50);
        btnStart.setDisable(true);
        txtNaam.setFocusTraversable(false);
        btnAbout.setPrefWidth(60);
        txtNaam.setMaxWidth(200);
        txtNaam.setPrefWidth(200);
        txtNaam.setAlignment(Pos.CENTER);
        txtNaam.setPromptText("Naam");

        this.setCenter(box);
        this.setTop(btnAbout);
    }

    Button getBtnStart() {return btnStart;}
    Button getBtnAbout() {return btnAbout;}
    TextField getTxtNaam() {
        return txtNaam;
    }
    ComboBox<Difficulty> getCbDifficulty() {
        return cbDifficulty;
    }
}
