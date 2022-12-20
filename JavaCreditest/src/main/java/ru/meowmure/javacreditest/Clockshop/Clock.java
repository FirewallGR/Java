package ru.meowmure.javacreditest.Clockshop;


import com.google.gson.annotations.Expose;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import ru.meowmure.javacreditest.Exceptions.IncorrectNumberException;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Entity
@Table(name = "clocks")
public class Clock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private transient int id;
    @Expose
    private String name;
    @Expose
    private String mark;
    @Expose
    private int cost;
    @Expose
    private boolean isTyped;
    @Expose
    private double red, green, blue;
    @Expose
    private int hours = 0, minutes = 0, seconds = 0;
    @Expose
    private int finalHours = 0, finalMinutes = 0, finalSeconds = 0;
    @Expose
    private GregorianCalendar timeStart;
    @Expose
    private GregorianCalendar timeEnd;
    private transient ClockPane clockPane;

    public Clock() {

    }
    public Clock(Group group) {
        clockPane = new ClockPane(group, this);
        timeStart = new GregorianCalendar();
    }

    public Clock(Group group, Color color) {
        clockPane = new ClockPane(group, this, color);
        timeStart = new GregorianCalendar();
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() { return name; }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() { return mark; }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getCost() { return cost; }

    public void setCost(int cost) throws IncorrectNumberException {
        if (cost < 0) throw new IncorrectNumberException("Price cannot be below zero");
        this.cost = cost;
    }

    public boolean isTyped() { return isTyped; }

    public void setTyped(boolean typed) { isTyped = typed; }

    public double getRed() { return red; }

    public void setRed(double red) { this.red = red; }

    public double getGreen() { return green; }

    public void setGreen(double green) { this.green = green; }

    public double getBlue() { return blue; }

    public void setBlue(double blue) { this.blue = blue; }

    public int getHours() { return hours; }

    public void setHours(int hours) { this.hours = hours; }

    public int getMinutes() { return minutes; }

    public void setMinutes(int minutes) { this.minutes = minutes; }

    public int getSeconds() { return seconds; }

    public void setSeconds(int seconds) { this.seconds = seconds; }

    @SuppressWarnings("JpaAttributeMemberSignatureInspection")
    public long getLongTimeStart() {
        return timeStart.getTime().getTime();
    }

    public void setLongTimeStart(long millis) {
        timeStart = new GregorianCalendar();
        timeStart.getTime().setTime(millis);
    }

    public int getFinalHours() {
        return finalHours;
    }

    public void setFinalHours(int finalHours) {
        this.finalHours = finalHours;
    }

    public int getFinalMinutes() {
        return finalMinutes;
    }

    public void setFinalMinutes(int finalMinutes) {
        this.finalMinutes = finalMinutes;
    }

    public int getFinalSeconds() {
        return finalSeconds;
    }

    public void setFinalSeconds(int finalSeconds) {
        this.finalSeconds = finalSeconds;
    }

    public void setTime(int hours, int minutes, int seconds) {
        finalSeconds = seconds;
        finalMinutes = minutes;
        finalHours = hours;
        timeStart = new GregorianCalendar();
    }

    public void setColor(Color color) {
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        clockPane.setColor(color);
    }

    public void clockRestored(Group group) {
        clockPane = new ClockPane(group, this, Color.color(red, green, blue));
    }

    @SuppressWarnings("JpaAttributeTypeInspection")
    public ClockPane getClockPane() {
        return clockPane;
    }

    public void setClockPane(ClockPane clockPane) {
        this.clockPane = clockPane;
    }

    public void draw() {
        timeEnd = new GregorianCalendar();
        seconds = timeEnd.get(Calendar.SECOND) - timeStart.get(Calendar.SECOND) + finalSeconds;
        minutes = timeEnd.get(Calendar.MINUTE) - timeStart.get(Calendar.MINUTE) + finalMinutes;
        hours = timeEnd.get(Calendar.HOUR) - timeStart.get(Calendar.HOUR) + finalHours;
        clockPane.PaintClock(hours, minutes, seconds);
    }


    @Override
    public String toString() {
        return name;
    }
}