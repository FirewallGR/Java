package ru.meowmure.javacreditest.Controllers;

import javafx.scene.paint.Color;
import ru.meowmure.javacreditest.Exceptions.IncorrectNumberException;
import ru.meowmure.javacreditest.Model.Clock;

public class DefaultList {
    public static void createDefaultList(GUIController controller) {
        try {
            Color colorRolex = Color.GOLD;
            Clock clockRolex = new Clock(controller.getGroup(), colorRolex);
            clockRolex.setName("Day Date 40");
            clockRolex.setMark("Rolex");
            clockRolex.setCost(500000);
            clockRolex.setTyped(true);

            Color colorPatekPhilippe = Color.GHOSTWHITE;
            Clock clockPatekPhilippe = new Clock(controller.getGroup(), colorPatekPhilippe);
            clockPatekPhilippe.setName("Nautilus");
            clockPatekPhilippe.setMark("Patek Philippe");
            clockPatekPhilippe.setCost(990000);
            clockPatekPhilippe.setTyped(true);

            Color colorTAGHeuer = Color.ROYALBLUE;
            Clock clockTAGheuer = new Clock(controller.getGroup(), colorTAGHeuer);
            clockTAGheuer.setName("Monaco");
            clockTAGheuer.setMark("TAG Heuer");
            clockTAGheuer.setCost(478000);
            clockTAGheuer.setTyped(false);

            Color colorLongines = Color.LIMEGREEN;
            Clock clockLongines = new Clock(controller.getGroup(), colorLongines);
            clockLongines.setName("Spirit");
            clockLongines.setMark("Longines");
            clockLongines.setCost(333600);
            clockLongines.setTyped(false);

            Color colorAudemarsPiguet = Color.LIGHTSTEELBLUE;
            Clock clockAudemarsPiguet = new Clock(controller.getGroup(), colorAudemarsPiguet);
            clockAudemarsPiguet.setName("Royal Oak");
            clockAudemarsPiguet.setMark("Audemars Piguet");
            clockAudemarsPiguet.setCost(777000);
            clockAudemarsPiguet.setTyped(true);

            controller.getListView().getItems().add(clockRolex);
            controller.getListView().getItems().add(clockPatekPhilippe);
            controller.getListView().getItems().add(clockTAGheuer);
            controller.getListView().getItems().add(clockLongines);
            controller.getListView().getItems().add(clockAudemarsPiguet);
        } catch (IncorrectNumberException e) {
            throw new RuntimeException(e);
        }
    }
}
