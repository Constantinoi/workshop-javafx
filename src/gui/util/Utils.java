package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	public static Stage currentsStage(ActionEvent event) {//acessar meu Stage onde o Controller(ActionEvente) está;ex. se eu clico no botao eu pego o Stage daquel botao
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
}
