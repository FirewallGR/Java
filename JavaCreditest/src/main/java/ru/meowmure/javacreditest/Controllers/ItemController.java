package ru.meowmure.javacreditest.Controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.meowmure.javacreditest.ClockShopApplication;
import ru.meowmure.javacreditest.Clockshop.Clock;
import ru.meowmure.javacreditest.Exceptions.IncorrectNumberException;

import java.net.URL;
import java.util.*;

public class ItemController implements Initializable {
    ObservableList list = FXCollections.observableArrayList();
    @FXML
    private Button buttonCreate = new Button();
    @FXML
    private TextField name   = new TextField();
    @FXML
    private ChoiceBox<String> brandsChoiceBox;
    @FXML
    private TextField price = new TextField();
    @FXML
    private CheckBox type = new CheckBox();
    private ClockShopApplication app;
    private ListView<Clock> listView;
    private Clock clock;
    private List<String> brands = Arrays.asList("Rolex", "Patek Philippe", "TAG Heuer", "Longines", "Audemars Piguet");
    private List<Color> brandColors = Arrays.asList(Color.GOLD, Color.GHOSTWHITE, Color.ROYALBLUE, Color.LIMEGREEN, Color.LIGHTSTEELBLUE);


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
        if ((brandsChoiceBox == null || brandsChoiceBox.getItems().isEmpty()) || (name == null || name.getText().isEmpty()) || (price == null || price.getText().isEmpty()) || (type == null)) {
            new Alert(Alert.AlertType.ERROR, "The one of the fields is null or empty").showAndWait();
            return;
        }
        clock.setMark(brandsChoiceBox.getValue());
        clock.setColor(brandColors.get(brands.indexOf(brandsChoiceBox.getValue())));
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

        listView.getItems().add(clock);

        returnList();

        ((Stage)(buttonCreate.getScene().getWindow())).close();
    }

    public void setObject(Clock clock) {
        this.clock = clock;
    }

    public void returnList() {
        app.GUIcontroller.setListView(listView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brandsChoiceBox.getItems().addAll(brands);
    }
}
