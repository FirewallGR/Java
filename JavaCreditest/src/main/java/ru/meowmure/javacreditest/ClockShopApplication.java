package ru.meowmure.javacreditest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ru.meowmure.javacreditest.Model.Clock;
import ru.meowmure.javacreditest.Controllers.GUIController;
import ru.meowmure.javacreditest.Controllers.ItemController;
import ru.meowmure.javacreditest.Controllers.TimeController;

import java.io.IOException;

public class ClockShopApplication extends Application {
    public GUIController GUIcontroller;
    public ItemController itemController;
    public TimeController timeController;
    private Group group;

    @Override
    public void start(Stage stage) throws IOException {
        showMainWindow();
    }

    public void showMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(ClockShopApplication.class.getResource("MainWindow.fxml"));
            Scene mainScene = new Scene(loader.load());
            Stage mainStage = new Stage();
            mainStage.setTitle("Shop");
            mainStage.setScene(mainScene);
            GUIcontroller = loader.getController();
            GUIcontroller.setApp(this);
            GUIcontroller.defaultClockBrands();
            mainStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void showAddWindow(Clock clock, ListView<Clock> listView) {
        FXMLLoader loader = new FXMLLoader(ClockShopApplication.class.getResource("NewItem.fxml"));
        try {
            Scene newScene = new Scene(loader.load(),240,400);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Add new");
            itemController = loader.getController();
            itemController.setApp(this);
            itemController.setListView(listView);
            itemController.setObject(clock);
            newStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showTimeWindow(ListView<Clock> listView) {
        FXMLLoader loader = new FXMLLoader(ClockShopApplication.class.getResource("SetTimeForm.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.setTitle("Set Time");
            timeController = loader.getController();
            timeController.setApp(this);
            timeController.setListView(listView);
            newStage.showAndWait();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}