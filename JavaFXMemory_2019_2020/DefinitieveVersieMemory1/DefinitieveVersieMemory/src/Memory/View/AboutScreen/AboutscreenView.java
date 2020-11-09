package Memory.View.AboutScreen;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class AboutscreenView extends BorderPane {
    private Button btnOk;
    private Label lblAbout;

    public AboutscreenView(){
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        btnOk = new Button("Ok");
        btnOk.setPrefWidth(60);
        lblAbout = new Label("Deze applicatie is geschreven door Robin Benoot en Michiel De Ruyter. ~ Schooljaar 2019-2020");
    }

    private void layoutNodes() {
        lblAbout.getStyleClass().add("about-label");
        setCenter(lblAbout);
        setPadding(new Insets(10));
        BorderPane.setAlignment(btnOk, Pos.CENTER_RIGHT);
        BorderPane.setMargin(btnOk, new Insets(10, 0, 0, 0));
        setBottom(btnOk);
    }

    Button getBtnOk(){
        return btnOk;
    }
}
