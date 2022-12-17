package ru.meowmure.javacreditest.Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import ru.meowmure.javacreditest.ClockShopApplication;
import ru.meowmure.javacreditest.Clockshop.Clock;
import ru.meowmure.javacreditest.Exceptions.IncorrectNumberException;

public class ItemController {

    @FXML
    private Button buttonCreate = new Button();
    @FXML
    private TextField name   = new TextField();
    @FXML
    private TextField mark = new TextField();
    @FXML
    private TextField price = new TextField();
    @FXML
    private CheckBox type = new CheckBox();
    private ClockShopApplication app;
    private ListView<Clock> listView;
    private Clock clock;

    public void setApp(ClockShopApplication app) {
        this.app = app;
    }

    public void setListView(ListView<Clock> listView) {
        this.listView = listView;
    }

    public ListView<Clock> getListView() {
        return listView;
    }

    public void create(ActionEvent actionEvent) {
        if ((mark == null || mark.getText().isEmpty()) || (name == null || name.getText().isEmpty()) || (price == null || price.getText().isEmpty()) || (type == null)) {
            new Alert(Alert.AlertType.ERROR, "The one of the fields is null or empty").showAndWait();
            return;
        }
        clock.setMark(mark.getText());

        try {
            clock.setCost(Integer.parseInt(price.getText()));
        } catch ( IncorrectNumberException e) {
            new Alert(Alert.AlertType.ERROR, "Price is below zero").showAndWait();
            return;
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Price is not a number").showAndWait();
            return;
        }

        clock.setName(name.getText());
        clock.setTyped(type.isSelected());

        checkMatches();

        listView.getItems().add(clock);

        returnList();

        ((Stage)(buttonCreate.getScene().getWindow())).close();
    }

    public void checkMatches() {
        for(Clock temp : listView.getItems()) {
            if (temp.getMark().compareTo(clock.getMark()) == 0) {
                clock.setClockPane(temp.getClockPane());
            }
        }
    }
    public void setObject(Clock clock) {
        this.clock = clock;
    }

    public void returnList() {
        app.GUIcontroller.setListView(listView);
    }
}
