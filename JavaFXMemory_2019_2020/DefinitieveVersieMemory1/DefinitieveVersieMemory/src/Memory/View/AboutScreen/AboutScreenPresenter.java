package Memory.View.AboutScreen;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AboutScreenPresenter {
    private AboutscreenView view;

    public AboutScreenPresenter(AboutscreenView view){
        this.view=view;
        addEventHandlers();
    }

    private void addEventHandlers(){
        view.getBtnOk().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getScene().getWindow().hide();
            }
        });
    }
}
