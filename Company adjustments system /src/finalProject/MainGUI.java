package finalProject;

import javafx.application.Application;
import javafx.stage.Stage;
import mvc.Controller;
import mvc.Model;
import mvc.View;

public class MainGUI extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage newStage) throws Exception {
		Model model = new Model();
		View view = new View(newStage);
		Controller controller = new Controller(model, view);
	}

	
}
