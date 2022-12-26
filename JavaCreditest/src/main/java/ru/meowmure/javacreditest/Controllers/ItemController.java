package ru.meowmure.javacreditest.Controllers;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.meowmure.javacreditest.ClockShopApplication;
import ru.meowmure.javacreditest.Exceptions.IncorrectNumberException;
import ru.meowmure.javacreditest.Model.Clock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemController {

    @FXML
    private Button buttonCreate = new Button();
    @FXML
    private TextField name = new TextField();
    @FXML
    private ChoiceBox<String> mark = new ChoiceBox<>();
    @FXML
    private TextField price = new TextField();
    @FXML
    private CheckBox type = new CheckBox();
    private ClockShopApplication app;
    private ListView<Clock> listView;
    private Clock clock;

    List<Color> colors = new ArrayList<>(Arrays.asList(Color.GOLD, Color.GHOSTWHITE, Color.ROYALBLUE, Color.LIMEGREEN, Color.LIGHTSTEELBLUE));
    List<String> brands = new ArrayList<>(Arrays.asList("Rolex", "Patek Philippe", "TAG Heuer", "Longines", "Audemars Piguet"));


    public void setApp(ClockShopApplication app) {
        mark.setItems(FXCollections.observableArrayList("Rolex", "Patek Philippe", "TAG Heuer", "Longines", "Audemars Piguet"));
        this.app = app;
    }

    public void setListView(ListView<Clock> listView) {
        this.listView = listView;
    }

    public ListView<Clock> getListView() {
        return listView;
    }

    public void setObject(Clock clock) {
        this.clock = clock;
    }

    public void create(ActionEvent actionEvent) {
        if ((mark == null || mark.getItems().isEmpty()) || (name == null || name.getText().isEmpty()) || (price == null || price.getText().isEmpty()) || (type == null)) {
            new Alert(Alert.AlertType.ERROR, "The one of the fields is null or empty").showAndWait();
            return;
        }
        clock.setMark(mark.getValue());
        clock.setColor(colors.get(brands.indexOf(mark.getValue())));

        try {
            clock.setCost(Integer.parseInt(price.getText()));
        } catch ( IncorrectNumberException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            return;
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            return;
        }

        clock.setName(name.getText());
        clock.setTyped(type.isSelected());

        //checkMatches();

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
    //public void setObject(Clock clock) {
//        this.clock = clock;
//    }

    public void returnList() {
        app.GUIcontroller.setListView(listView);
    }
}
