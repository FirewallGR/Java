package ru.meowmure.javacreditest.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.meowmure.javacreditest.ClockShopApplication;
import ru.meowmure.javacreditest.Model.Clock;

public class TimeController {
    @FXML
    private TextField textFieldAllHours;
    @FXML
    private TextField textFieldAllMins;
    @FXML
    private TextField textFieldAllSecs;
    @FXML
    private Button buttonConfirm;

    private ClockShopApplication app;

    private ListView<Clock> listView;


    public void setApp(ClockShopApplication app) {
        this.app = app;
    }

    public ListView<Clock> getListView() {
        return listView;
    }

    public void setListView(ListView<Clock> listView) {
        this.listView = listView;
    }

    public void returnList() {
        app.GUIcontroller.setListView(listView);
    }

    public void confirm(ActionEvent actionEvent) {
        for (Clock clock : listView.getItems()) {
            clock.setTime(Integer.parseInt(textFieldAllHours.getText()),Integer.parseInt(textFieldAllMins.getText()), Integer.parseInt(textFieldAllSecs.getText()));
        }
        listView.getSelectionModel().getSelectedItem().draw();
        returnList();
        ((Stage)(buttonConfirm.getScene().getWindow())).close();
    }
}
