package ru.meowmure.javacreditest.Clockshop;

import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.io.Serializable;

public class ClockPane {
    private Group group;
    private Clock clock;
    public Color color;
    private Line sline;
    private Line mline;
    private Line hline;
    private double width = 350, height = 350;


    public ClockPane(Group group, Clock clock, Color color){
        this.group = group;
        this.clock = clock;
        this.color = color;
    }

    public void PaintClock(int hours, int minutes, int seconds) {
        group.getChildren().clear();

        double clockRadius = Math.min(width, height) * 0.8 * 0.5;
        double centerX = group.getLayoutBounds().getCenterX();
        double centerY = group.getLayoutBounds().getCenterY();
        double slength = clockRadius * 0.8;
        double mlength = clockRadius * 0.65;
        double hlength = clockRadius * 0.5;

        double seedSecondDegrees  = seconds * (360 / 60);
        double seedMinuteDegrees  = (minutes + seedSecondDegrees / 360.0) * (360 / 60);
        double seedHourDegrees    = (hours + seedMinuteDegrees / 360.0) * (360 / 12);

        Circle mainCircle = new Circle(centerX,centerY, clockRadius, Color.WHITE);
        mainCircle.setStrokeWidth(2);

        Circle helpCircle = new Circle(centerX,centerY, clockRadius - 5, Color.WHITE);
        Circle centerDot = new Circle(centerX,centerY, clockRadius * 0.02,Color.BLACK);

        mainCircle.setStroke(color);
        group.getChildren().add(mainCircle);
        for (int i = 0; i < 60; i++){
            Line segment = new Line(centerX, centerY, centerX + clockRadius * Math.sin(i * (Math.PI / 30)), centerY - clockRadius * Math.cos(i * (Math.PI / 30)));
            if (i % 5 == 0) {
                segment.setStroke(Color.RED);
                segment.setStrokeWidth(3);
            }
            else {segment.setStroke(Color.BLACK);}
            group.getChildren().add(segment);
        }
        group.getChildren().add(helpCircle);
        group.getChildren().add(centerDot);

        if (clock.isTyped()) {
            sline = new Line(centerX, centerY, centerX, centerY - slength);
            sline.setStroke(Color.RED);
            sline.setTranslateX(0);
            sline.setTranslateY(0);

            group.getChildren().add(sline);

            Rotate srotate = new Rotate(seedSecondDegrees);
            sline.getTransforms().add(srotate);

            Timeline secondTime = new Timeline(
                    new KeyFrame(
                            Duration.seconds(60),
                            new KeyValue(
                                    srotate.angleProperty(),
                                    360 + seedSecondDegrees,
                                    Interpolator.LINEAR
                            )
                    )
            );
            secondTime.setCycleCount(Animation.INDEFINITE);
            secondTime.play();
        }


        mline = new Line(centerX, centerY, centerX, centerY - mlength);
        mline.setStroke(Color.BLACK);
        mline.setTranslateX(0);
        mline.setTranslateY(0);

        Rotate mrotate = new Rotate(seedMinuteDegrees);
        mline.getTransforms().add(mrotate);

        Timeline minuteTime = new Timeline(
                new KeyFrame(
                        Duration.minutes(60),
                        new KeyValue(
                                mrotate.angleProperty(),
                                360 + seedMinuteDegrees,
                                Interpolator.LINEAR
                        )
                )
        );
        minuteTime.setCycleCount(Animation.INDEFINITE);
        minuteTime.play();



        hline = new Line(centerX, centerY, centerX, centerY - hlength);
        hline.setStroke(Color.BLUE);
        hline.setTranslateX(0);
        hline.setTranslateY(0);

        Rotate hrotate = new Rotate(seedHourDegrees);
        hline.getTransforms().add(hrotate);

        Timeline hourTime = new Timeline(
                new KeyFrame(
                        Duration.hours(12),
                        new KeyValue(
                                hrotate.angleProperty(),
                                360 + seedHourDegrees,
                                Interpolator.LINEAR
                        )
                )
        );
        hourTime.setCycleCount(Animation.INDEFINITE);
        hourTime.play();


        Label label12 = new Label("12");
        label12.setFont(new Font(15));
        label12.setLayoutX(centerX - 8);
        label12.setLayoutY(centerY - clockRadius + 3);

        Label label3 = new Label("3");
        label3.setFont(new Font(15));
        label3.setLayoutX(centerX + clockRadius - 15);
        label3.setLayoutY(centerY - 11);

        Label label6 = new Label("6");
        label6.setFont(new Font(15));
        label6.setLayoutX(centerX - 5);
        label6.setLayoutY(centerY + clockRadius - 25);

        Label label9 = new Label("9");
        label9.setFont(new Font(15));
        label9.setLayoutX(centerX - clockRadius + 8);
        label9.setLayoutY(centerY - 11);

        group.getChildren().addAll(mline, hline, label3, label6, label9, label12);
    }
}
