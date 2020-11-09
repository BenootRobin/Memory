package Memory.View.StartScreen;

import Memory.Model.Memory;
import Memory.Model.player.Speler;
import Memory.View.AboutScreen.AboutScreenPresenter;
import Memory.View.AboutScreen.AboutscreenView;
import Memory.View.MainScreen.MainScreenPresenter;
import Memory.View.MainScreen.MainScreenView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartScreenPresenter {
    private StartScreenView view;
    private boolean nameFilledIn;

    public StartScreenPresenter(StartScreenView view){
        this.view=view;
        addEventHandlers();
    }

    //Spel kan niet gestart worden zolang er geen naam ingegeven is
    private void addEventHandlers(){
        view.getTxtNaam().setOnKeyPressed(event -> {
            nameFilledIn = view.getTxtNaam().getText().trim().length() > 0;
            view.getBtnStart().setDisable(!nameFilledIn);
        });

        view.getBtnStart().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Speler speler= new Speler(view.getTxtNaam().getText());
                Memory model = new Memory(speler, view.getCbDifficulty().getValue());
                MainScreenView mainView= new MainScreenView(model.getSpelbord(), speler);
                MainScreenPresenter presenter = new MainScreenPresenter(model, mainView);
                presenter.addWindowEventHandlers(view.getScene());
                view.getScene().setRoot(mainView);
                mainView.getScene().getWindow().sizeToScene();
        }

        });

        view.getBtnAbout().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                AboutscreenView aboutView= new AboutscreenView();
                new AboutScreenPresenter(aboutView);
                Scene scene = new Scene(aboutView);
                scene.getStylesheets().addAll("stylesheets/styles.css");
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            }
        });
    }
}
