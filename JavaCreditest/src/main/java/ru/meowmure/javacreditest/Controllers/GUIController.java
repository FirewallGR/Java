package ru.meowmure.javacreditest.Controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import ru.meowmure.javacreditest.ClockShopApplication;
import ru.meowmure.javacreditest.Clockshop.Clock;
import ru.meowmure.javacreditest.Exceptions.IncorrectNumberException;

import java.io.*;
import java.util.*;


public class GUIController {

    @FXML
    private ListView<Clock> listView = new ListView<>();
    @FXML
    private Button add;
    @FXML
    private Button setTime;
    @FXML
    private Button buttonSet;
    @FXML
    private Label labelName;
    @FXML
    private Label labelBrand;
    @FXML
    private Label labelType;
    @FXML
    private Label labelPrice;
    @FXML
    private Label labelHour;
    @FXML
    private Label labelMin;
    @FXML
    private Label labelSec;
    @FXML
    private TextField textFieldHour;
    @FXML
    private TextField textFieldMin;
    @FXML
    private TextField textFieldSec;
    @FXML
    private MenuItem menuItemAction1;
    @FXML
    private MenuItem menuItemAction2;
    @FXML
    private MenuItem menuItemAction3;
    @FXML
    private MenuItem menuItemOpen;
    @FXML
    private MenuItem menuItemSave;
    @FXML
    private MenuItem menuItemClose;
    @FXML
    private Group group;
    private HashMap<String, Integer> map = new HashMap<>();

    private ClockShopApplication app;

    public void setApp(ClockShopApplication app) { this.app = app; }

    public void setListView(ListView<Clock> listView) {
        this.listView = listView;
    }

    public void add(ActionEvent actionEvent) {
        Clock clock = new Clock(group);
        app.showAddWindow(clock, listView);
    }

    public void onItemSelected(MouseEvent mouseEvent) {
        if (listView.getItems().isEmpty()) return;

        labelName.setText(listView.getSelectionModel().getSelectedItem().getName());
        labelBrand.setText(listView.getSelectionModel().getSelectedItem().getMark());
        labelType.setText(listView.getSelectionModel().getSelectedItem().isTyped() ? "With seconds" : "Without seconds");
        labelPrice.setText(String.valueOf((listView.getSelectionModel().getSelectedItem().getCost())));

        listView.getSelectionModel().getSelectedItem().draw();

        labelHour.setVisible(true);
        labelMin.setVisible(true);
        labelSec.setVisible(true);
        textFieldHour.setVisible(true);
        textFieldMin.setVisible(true);
        textFieldSec.setVisible(true);
        buttonSet.setVisible(true);
    }

    public void getExpensive(ActionEvent actionEvent) {
        int max = 0;
        Iterator<Clock> iterator = listView.getItems().iterator();
        while (iterator.hasNext()) {
            Clock temp = iterator.next();
            if (temp.getCost() > max) {
                max = temp.getCost();
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "The most expensive clock price is: " + max).showAndWait();
    }

    //Button Set on main window
    public void setChosenTime(ActionEvent actionEvent) {
        listView.getSelectionModel().getSelectedItem().setTime(
                textFieldHour.getText().isEmpty() ? 0 : Integer.parseInt(textFieldHour.getText()),
                textFieldMin.getText().isEmpty() ? 0 : Integer.parseInt(textFieldMin.getText()),
                textFieldSec.getText().isEmpty() ? 0 : Integer.parseInt(textFieldSec.getText())
        );
        listView.getSelectionModel().getSelectedItem().draw();
    }

    //Action 2 in actions menu
    public void setUpAllClocks(ActionEvent actionEvent) {
        app.showTimeWindow(listView);
    }

    public void action3(ActionEvent actionEvent) {
        for (Clock clock : listView.getItems()) {
            if (!map.containsKey(clock.getMark())) {
                map.put(clock.getMark(), 1);
            } else {
                int count = map.get(clock.getMark());
                count++;
                map.put(clock.getMark(), count);
            }
        }
        String brand = null;
        int max = 0;
        for (Map.Entry<String, Integer> pair : map.entrySet()) {
            if (pair.getValue() > max) {
                max = pair.getValue();
                brand = pair.getKey();
            }
        }
        new Alert(Alert.AlertType.INFORMATION, "The most popular brand is: " + brand).showAndWait();
        map.clear();

    }

    public void serializationSave(ActionEvent actionEvent) {
        try {
            FileOutputStream fout = new FileOutputStream("ClockShopApplication.txt");
            ObjectOutputStream objOut = new ObjectOutputStream(fout);
            for (Clock temp : listView.getItems()) {
                objOut.writeObject(temp);
                objOut.flush();
            }
            objOut.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void serializationOpen(ActionEvent actionEvent) {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open...");
            File file = fileChooser.showOpenDialog(buttonSet.getScene().getWindow());
            if (file != null) {
                FileInputStream fileInputStream = new FileInputStream(file.getName());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                Clock temp = (Clock) objectInputStream.readObject();
                listView.getItems().clear();
                while (temp != null) {
                    temp.clockRestored(group);
                    listView.getItems().add(temp);
                    temp = (Clock) objectInputStream.readObject();
                }
                objectInputStream.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (EOFException e) {
            return;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void JSONOpen(ActionEvent actionEvent) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("Clock.json"));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();
            String jsonString;
            jsonString = bufferedReader.readLine();
            Clock[] arrayClocks = gson.fromJson(jsonString, Clock[].class);
            listView.getItems().clear();
            for (Clock temp:arrayClocks) {
                temp.clockRestored(group);
                listView.getItems().add(temp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void JSONSave(ActionEvent actionEvent) {
        try (PrintWriter out = new PrintWriter(new FileWriter("Clock.json"))) {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String jsonString = gson.toJson(listView.getItems());
            out.write(jsonString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dataBaseOpen(ActionEvent actionEvent) {
        DBController dbController = new DBController(group);
        listView.getItems().clear();
        listView.getItems().setAll(dbController.findAll());
    }

    public void dataBaseSave(ActionEvent actionEvent) {
        DBController dbController = new DBController(group);
        dbController.clear();
        for (Clock temp:listView.getItems()) {
            dbController.Save(temp);
        }
    }

    public void defaultClocks (){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("DefaultClocks.json"));
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.excludeFieldsWithoutExposeAnnotation().create();
            String jsonString;
            jsonString = bufferedReader.readLine();
            Clock[] arrayClocks = gson.fromJson(jsonString, Clock[].class);
            for (Clock temp:arrayClocks) {
                temp.clockRestored(group);
                listView.getItems().add(temp);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
