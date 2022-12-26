package ru.meowmure.javacreditest.Controllers;

import javafx.scene.Group;
import ru.meowmure.javacreditest.Model.Clock;
import ru.meowmure.javacreditest.Exceptions.IncorrectNumberException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBController {
    private Group group;

    public DBController(Group group) {
        this.group = group;
    }
    public List<Clock> findAll() {
        List<Clock> list = new ArrayList<>();
//        String sqlRequest = "SELECT * from clocks where name = ?";
        String sqlRequest = "SELECT * from clocks";
        try(PreparedStatement preparedStatement = SessionController.getConnection().prepareStatement(sqlRequest)) {
            //preparedStatement.setString(1, name);
            preparedStatement.execute();
            ResultSet rs = preparedStatement.getResultSet();
            while (rs.next()) {
                Clock clock = new Clock();
                clock.setName(rs.getString("name"));
                clock.setId(rs.getInt("id"));
                clock.setMark(rs.getString("mark"));
                clock.setCost(rs.getInt("cost"));
                clock.setTyped(rs.getBoolean("isTyped"));
                clock.setHours(rs.getInt("hours"));
                clock.setMinutes(rs.getInt("minutes"));
                clock.setSeconds(rs.getInt("seconds"));
                clock.setFinalHours(rs.getInt("finalHours"));
                clock.setFinalMinutes(rs.getInt("finalMinutes"));
                clock.setFinalSeconds(rs.getInt("finalSeconds"));
                clock.setRed(rs.getDouble("red"));
                clock.setBlue(rs.getDouble("blue"));
                clock.setGreen(rs.getDouble("green"));
                clock.setLongTimeStart(rs.getLong("timeStart"));
                clock.clockRestored(group);
                list.add(clock);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IncorrectNumberException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void Save(Clock clock) {
        String sqlRequest = "INSERT INTO clocks VALUES (? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try(PreparedStatement preparedStatement = SessionController.getConnection().prepareStatement(sqlRequest)) {
            preparedStatement.setInt(1, clock.getId());
            preparedStatement.setString(2, clock.getName());
            preparedStatement.setString(3, clock.getMark());
            preparedStatement.setInt(4, clock.getCost());
            preparedStatement.setBoolean(5, clock.isTyped());
            preparedStatement.setDouble(6, clock.getRed());
            preparedStatement.setDouble(7, clock.getGreen());
            preparedStatement.setDouble(8, clock.getBlue());
            preparedStatement.setInt(9, clock.getHours());
            preparedStatement.setInt(10, clock.getMinutes());
            preparedStatement.setInt(11, clock.getSeconds());
            preparedStatement.setInt(12, clock.getFinalHours());
            preparedStatement.setInt(13, clock.getFinalMinutes());
            preparedStatement.setInt(14, clock.getFinalSeconds());
            preparedStatement.setLong(15, clock.getLongTimeStart());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() {
        String sqlRequest = "DELETE FROM clocks";
        try(PreparedStatement preparedStatement = SessionController.getConnection().prepareStatement(sqlRequest)) {
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
