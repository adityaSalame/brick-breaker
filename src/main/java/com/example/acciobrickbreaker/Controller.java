package com.example.acciobrickbreaker;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.robot.Robot;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



        public class Controller implements Initializable {
            @FXML
            private Circle circle;

            @FXML
            private AnchorPane scene;

            @FXML
            private Text result;

            @FXML
            private AnchorPane Boundarywall;

            Robot robot = new Robot();

            double deltax = 0.5;
            double deltay = 0.7;

            ArrayList<Rectangle> all_bricks = new ArrayList<>();

            private Rectangle slider;

            private Button left,right,start,restart,pause;

            public int score=0;





            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    // we need to move the ball.
                    circle.setLayoutY(circle.getLayoutY() + deltay);
                    circle.setLayoutX(circle.getLayoutX() + deltax);

                    // we need to check for collisions every time here.

                    // collision with walls, which is collision with scene.
                    check_collision_with_wall();

                    // collision with bricks
                    check_collision_with_bricks();

                    // collision with slider.
                    check_collision_with_slider();

                    // see if all the bricks are empty, then exit the game.
                    if(all_bricks.isEmpty()){
                        //System.exit(10);
                        timeline.stop();
                        result.setText("YOU WIN YOUR SCORE "+score+" !");
                        left.setVisible(false);
                        right.setVisible(false);
                        start.setVisible(false);
                        pause.setVisible(false);
                        restart.setVisible(true);


                    }
                }
            }));

            public void check_collision_with_bricks(){
                all_bricks.removeIf(current_brick -> check_collision_with_single_brick(current_brick));
            }

            public boolean check_collision_with_single_brick(Rectangle brick){
                if(circle.getBoundsInParent().intersects(brick.getBoundsInParent())){
                    Bounds bounds = brick.getBoundsInLocal();
                    boolean bottomside = circle.getLayoutY() - circle.getRadius() <= bounds.getMaxY();
                    boolean topside = circle.getLayoutY() - circle.getRadius() >= bounds.getMinY();
                    boolean rightside = circle.getLayoutX() - circle.getRadius() <= bounds.getMaxX();
                    boolean leftside = circle.getLayoutX() + circle.getRadius() >= bounds.getMinX();

                    if(rightside || leftside){
                        deltax *= -1;

                    }

                    if(topside || bottomside){
                        deltay *= -1;

                    }

                    Boundarywall.getChildren().remove(brick);
                    score+=10;
                    result.setText("SCORE : "+score );
                    return true;
                }
                return false;
            }

            public void check_collision_with_slider(){
                if(circle.getBoundsInParent().intersects(slider.getBoundsInParent())){
                     deltay *= -1;
                     //deltax*= -1;
                }
            }

            public void check_collision_with_wall(){
                Bounds bounds = Boundarywall.getBoundsInLocal();
                boolean rightside = circle.getLayoutX() + circle.getRadius() >= bounds.getMaxX();
                boolean leftside = circle.getLayoutX() - circle.getRadius() <= bounds.getMinX();
                boolean topside = circle.getLayoutY() - circle.getRadius() <= bounds.getMinY();
                boolean bottomside = circle.getLayoutY() + circle.getRadius() >= bounds.getMaxY();

                if(rightside || leftside){
                    deltax *= -1;
                }

                if(topside){
                    deltay *= -1;
                }

                // uncomment this code, this is just for testing purpose.
                if(bottomside){
                   deltay *= -1;
                }

               if(bottomside){
                    //System.exit(99);
                   timeline.stop();
                  result.setText("Game Over Your Score is "+score+" !!!");
                  left.setVisible(false);
                   right.setVisible(false);
                   start.setVisible(false);
                   restart.setVisible(true);
                   pause.setVisible(false);

                  //WinText.setText("GAME OVER Your Score is"!!") ;
               }
            }


            @Override
            public void initialize(URL url, ResourceBundle resourceBundle) {
                timeline.setCycleCount(Animation.INDEFINITE);

                adding_slider();
                create_bricks();
                adding_buttons();


                //timeline.play();
            }


            public void adding_buttons(){
                left = new Button("left");
                right = new Button("right");
                start = new Button("start");
                restart= new Button("restart");
                pause= new Button("pause");

                left.setLayoutY(410);
                right.setLayoutY(410);
                start.setLayoutY(410);
                pause.setLayoutY(410);
                restart.setLayoutY(410);

                left.setLayoutX(20);
                right.setLayoutX(540);
                start.setLayoutX(270);
                pause.setLayoutX(270);
                restart.setLayoutX(270);



                left.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(slider.getLayoutX() > -200)
                            slider.setLayoutX(slider.getLayoutX() - 20);
                    }
                });


                right.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if(slider.getLayoutX() < 300)
                            slider.setLayoutX(slider.getLayoutX() + 20);
                    }
                });

                start.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        timeline.play();
                        pause.setVisible(true);
                        start.setVisible(false);
                    }
                });

                pause.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        timeline.pause();
                        pause.setVisible(false);
                        start.setVisible(true);
                    }
                });


                restart.setOnAction(new EventHandler<ActionEvent>() {
                  @Override
                  public void handle(ActionEvent event){
//                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("startpage.fxml"));
//                       Scene sce
                        timeline.stop();
                        score=0;
                        result.setText("SCORE : "+score );
                        all_bricks.forEach(rectangle -> Boundarywall.getChildren().remove(rectangle));
                        all_bricks.clear();
                        Boundarywall.setStyle("-fx-background-color: pink;");

                        create_bricks();
                        deltax = 0.5;
                        deltay = 0.7;
                       // circle = new Circle(0.7,0.5,10);
                        //circle.setFill(Color.BLUE);
                        circle.setLayoutY(274 + deltay);
                        circle.setLayoutX(253 + deltax);
                        slider.setLayoutX(0);

                        left.setVisible(true);
                        right.setVisible(true);
                        start.setVisible(true);
                        pause.setVisible(false);
                        restart.setVisible(false);
//
//
//
                    }
           });

                scene.getChildren().add(left);
                scene.getChildren().add(right);
                scene.getChildren().add(start);
                scene.getChildren().add(restart);
                scene.getChildren().add(pause);
                pause.setVisible(false);
                restart.setVisible(false);
            }


            public void adding_slider(){
                slider = new Rectangle(200, 280, 100, 10);
                slider.setFill(Color.BLACK);
                Boundarywall.getChildren().add(slider);
            }

            public void create_bricks(){
                int counter = 1;
                for(int i = 170; i > 0; i -= 35){
                    for(int j = 508; j >= 0; j -= 40){
                        if(counter % 2 == 1){
                            Rectangle rect = new Rectangle(j, i, 75, 30);
                            if(counter % 3 == 0){
                                rect.setFill(Color.YELLOW);
                            }
                            else if(counter % 3 == 1){
                                rect.setFill(Color.RED);
                            }
                            else{
                                rect.setFill(Color.CYAN);
                            }
                            Boundarywall.getChildren().add(rect);
                            all_bricks.add(rect);
                        }
                        counter++;
                    }
                }
            }
        }






