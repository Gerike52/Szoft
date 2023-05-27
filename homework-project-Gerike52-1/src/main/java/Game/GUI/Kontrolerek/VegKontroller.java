package Game.GUI.Kontrolerek;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class VegKontroller {

    @FXML
    public TextField onNyert;

    public void gyoztesNeve(String gyoztes) {
        onNyert.setText(gyoztes);
        Logger.info("A nyertes nevének lekérése!");
    }

    @FXML
    private void pontok(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Pontok.fxml"));
        Parent root = fxmlLoader.load();
        var kontroller = (PontokKontroller) fxmlLoader.getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void exitVegen() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos elszeretnéd hagyni a játékot?");
        var result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Platform.exit();
            Logger.info("Kilépés a játékból!");
        }
    }
}
