package Game.GUI.Kontrolerek;

import java.util.List;

import Game.AdatB.Jdbi;
import Game.AdatB.Result;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.tinylog.Logger;

public class PontokKontroller {
    @FXML
    private TableView tableView;

    @FXML
    private TableColumn<Result, String> jatekosEgy;

    @FXML
    private TableColumn<Result, String> jatekosKetto;

    @FXML
    private TableColumn<Result, String> nyertes;


    @FXML
    private void initialize() {
        Logger.info("Az eddigi játékosok betöltése!");
        jatekosEgy.setCellValueFactory(new PropertyValueFactory<>("jatekosEgy"));
        jatekosKetto.setCellValueFactory(new PropertyValueFactory<>("jatekosKetto"));
        nyertes.setCellValueFactory(new PropertyValueFactory<>("nyertes"));
        List<Result> results = Jdbi.lister();
        ObservableList<Result> observableList = FXCollections.observableArrayList();
        observableList.addAll(results);
        tableView.setItems(observableList);

    }

    @FXML
    void exit() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Biztos elszeretnéd hagyni a játékot?");
        var result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Platform.exit();
            Logger.info("Kilépés a játékból!");
        }
    }


}
