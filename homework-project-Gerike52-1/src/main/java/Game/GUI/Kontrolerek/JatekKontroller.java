package Game.GUI.Kontrolerek;

import Game.AdatB.Jdbi;
import Game.AdatB.Result;
import Game.Logika.Helyzet;
import Game.Logika.Jatekosok;
import Game.Logika.Tabla;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.tinylog.Logger;


import java.io.IOException;
import java.util.HashMap;


import static javafx.scene.paint.Color.*;

public class JatekKontroller {

    private Tabla tabla;

    @FXML
    private GridPane grid;

    @FXML
    public TextField jnev;

    HashMap<Jatekosok, String> jnevek;

    @FXML
    private void initialize() {
        Logger.info("A játék betöltése");
        for (int oszlop = 0; oszlop < grid.getColumnCount(); oszlop++) {
            for (int sor = 0; sor < grid.getColumnCount(); sor++) {
                var kavics = kavics();
                grid.add(kavics, oszlop, sor);
            }
        }
        tabla = new Tabla();
    }

    public void nevek(HashMap<Jatekosok, String> jnevek) {
        this.jnevek = jnevek;
        jnev.setText(jnevek.get(tabla.getKezdo()));
        Logger.info("Játékos nevek beállítása");
    }

    private StackPane kavics() {
        Logger.info("kavicsok felhelyezése a táblára!");
        var kavics = new StackPane();
        kavics.getStyleClass().add("Kavics");
        var piece = new Circle(40);
        piece.setFill(DARKGRAY);
        kavics.getChildren().add(piece);
        kavics.setOnMouseClicked(this::handleMouseClick);
        return kavics;
    }


    @FXML
    private void handleMouseClick(MouseEvent event) {
        var kavicsok = (StackPane) event.getSource();
        var kor = (Circle) kavicsok.getChildren().get(0);
        var oszlop = GridPane.getColumnIndex(kavicsok);
        var sor = GridPane.getRowIndex(kavicsok);
        Helyzet pos = new Helyzet(sor, oszlop);

        if (kor.getFill().equals(GREEN)) {
            tabla.torles(pos);
            kor.setFill(DARKGRAY);
        } else if (kor.getFill().equals(DARKGRAY)) {
            tabla.kivalasztottPont(pos);
            kor.setFill(GREEN);
        } else if (kor.getFill().equals(TRANSPARENT)) {
            kor.setFill(TRANSPARENT);
        }
        Logger.info("Kiválasztottál egy kavicsot " + tabla.getKivalasztott());
    }

    private Node lapBeallitas(int sor, int oszlop) {
        Logger.info("A lapok sor és oszlop korddinátái: " + sor + " " + oszlop);
        for (Node lap : grid.getChildren()) {
            if (lap instanceof StackPane) {
                if (GridPane.getColumnIndex(lap) == oszlop && GridPane.getRowIndex(lap) == sor) {
                    return lap;
                }
            }
        }
        throw new IllegalArgumentException();
    }

    private boolean legalisValasztas() {
        Logger.info("Megnézzük hogy a vászatott mozdulat érvényes!");
        if (tabla.getKivalasztott().size() == 1) {
            return true;
        }
        if (!tabla.sorOszlop()) {
            return false;
        }
        if (tabla.azonosSor()) {
            int sorert = tabla.getKivalasztott().get(0).sor();

            Integer max = tabla.getKivalasztott().stream().mapToInt(Helyzet::oszlop).max().getAsInt();
            Integer min = tabla.getKivalasztott().stream().mapToInt(Helyzet::oszlop).min().getAsInt();
            for (int val = min; val < max; val++) {
                var kavics = (StackPane) lapBeallitas(sorert, val);
                var kor = (Circle) kavics.getChildren().get(0);

                if (kor.getFill().equals(TRANSPARENT)) {
                    return false;
                }
            }

        } else if (tabla.azonosOszlop()) {
            int oszlopert = tabla.getKivalasztott().get(0).oszlop();
            Integer max = tabla.getKivalasztott().stream().mapToInt(Helyzet::sor).max().getAsInt();
            Integer min = tabla.getKivalasztott().stream().mapToInt(Helyzet::sor).min().getAsInt();
            for (int val = min; val < max; val++) {
                var kavics = (StackPane) lapBeallitas(val, oszlopert);
                var kor = (Circle) kavics.getChildren().get(0);

                if (kor.getFill().equals(TRANSPARENT)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void kovetkezo(ActionEvent actionEvent) {
        if (!legalisValasztas()) {
            Logger.warn("Ez nem egy szabály szerű lépés!");
            return;
        }
        Logger.info("Lépés megtörtént.");
        tabla.kovetkezoj();

        for (var pozicio : tabla.getKivalasztott()) {
            var kavisc = (StackPane) lapBeallitas(pozicio.sor(), pozicio.oszlop());
            var kor = (Circle) kavisc.getChildren().get(0);
            kor.setFill(TRANSPARENT);
        }

        tabla.tisztit();
        jnev.setText(jnevek.get(tabla.getKezdo()));

        if (nyer()) {
            vege(actionEvent);
            Logger.info("A játék végetért!");
        }
    }

    public boolean nyer() {
        for (int sor = 0; sor < 4; ++sor) {
            for (int oszlop = 0; oszlop < 4; ++oszlop) {
                var kavics = (StackPane) lapBeallitas(sor, oszlop);
                var kor = (Circle) kavics.getChildren().get(0);
                if (!kor.getFill().equals(TRANSPARENT)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void vege(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/Veg.fxml"));
        Parent root = null;

        try {
            root = fxmlLoader.load();
            var kontroller = (VegKontroller) fxmlLoader.getController();
            kontroller.gyoztesNeve(jnevek.get(tabla.getKezdo()));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            Jdbi.insert(new Result(jnevek.get(Jatekosok.JATEKOS_EGY), jnevek.get(Jatekosok.JATEKOS_KETTO), jnevek.get(tabla.getKezdo())));
        } catch (IOException e) {
            e.printStackTrace();
        }
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




