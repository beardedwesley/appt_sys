package apptSys.controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.ColumnConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class CalWeekViewController implements Initializable {

    @FXML
    private ColumnConstraints sunCol;

    @FXML
    private ColumnConstraints monCol;

    @FXML
    private ColumnConstraints tuesCol;

    @FXML
    private ColumnConstraints wedCol;

    @FXML
    private ColumnConstraints thursCol;

    @FXML
    private ColumnConstraints friCol;

    @FXML
    private ColumnConstraints satCol;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
