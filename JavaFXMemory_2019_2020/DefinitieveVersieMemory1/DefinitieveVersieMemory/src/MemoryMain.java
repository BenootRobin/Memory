import Memory.View.StartScreen.StartScreenPresenter;
import Memory.View.StartScreen.StartScreenView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MemoryMain extends Application {
    @Override
    public void start(Stage stage){
        final StartScreenView view = new StartScreenView();
        new StartScreenPresenter(view);
        final Scene scene = new Scene(view);
        scene.getStylesheets().addAll("stylesheets/styles.css");
        stage.setTitle("Memory");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
