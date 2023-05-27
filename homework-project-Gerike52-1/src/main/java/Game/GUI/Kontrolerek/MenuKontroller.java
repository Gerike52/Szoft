package Game.GUI.Kontrolerek;


import Game.Logika.Jatekosok;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class MenuKontroller {


    @FXML
    public javafx.scene.control.TextField jatekosEgy;

    @FXML
    public javafx.scene.control.TextField jatekosKetto;

    private HashMap<Jatekosok, String> jatekosNevek() {
        var jatekosok = new HashMap<Jatekosok, String>();
        jatekosok.put(Jatekosok.JATEKOS_EGY, jatekosEgy.getText());
        jatekosok.put(Jatekosok.JATEKOS_KETTO, jatekosKetto.getText());
        System.out.println(jatekosok);
        return jatekosok;
    }

    @FXML
    public void game(MouseEvent mouseEvent) throws IOException {
        Logger.info("Belépés!");
        String filePath = System.getProperty("user.home") + File.separator + ".results";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Jatek.fxml"));
        Parent root = fxmlLoader.load();
        var controller = (Game.GUI.Kontrolerek.JatekKontroller) fxmlLoader.getController();
        controller.nevek(jatekosNevek());
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos ki szeretnél lépni?");
        var var = alert.showAndWait();

        if (var.get() == ButtonType.OK) {
            Logger.info("Kilépés a menüből!");
            Platform.exit();
        }
    }
}
