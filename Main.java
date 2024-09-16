import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.Dimension;
import java.awt.Toolkit;

//Main line function

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    } //launches java fx

    @Override
    public void start(Stage primaryStage) { // main line for game to run

        //This block is for some base imports needed to run javafx application
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        StackPane root = new StackPane();
        Scene scene = new Scene(root,screenSize.width, screenSize.height);

        //create's player movement object, and two player objects. Assigns what control type for each,
        //and for the frameRate (how fast the animationd are processed)
        Player_Movement player_movement = new Player_Movement(scene);
        New_Player player_1 = new New_Player("player_1", player_movement,false, 0.10); //change frameRate to change game speed. Optimal is 0.08
        New_Player player_2 = new New_Player("player_2", player_movement, true, 0.10);

        //creates background, sets it to 65% of game screen
        Image image = new Image(getClass().getResource("assets/background.png").toString());
        ImageView background_iv = new ImageView(image);
        background_iv.setScaleY(0.65);
        background_iv.setTranslateY(scene.getHeight()*0.1);
        background_iv.setFitHeight(scene.getHeight());
        background_iv.setFitWidth(scene.getWidth());

        //Adds layers for upper game ui
        image = new Image(getClass().getResource("assets/base_layer.png").toString());
        ImageView base_layer = new ImageView(image);
        image = new Image(getClass().getResource("assets/health_bar.png").toString());
        ImageView player_1_health_bar = new ImageView(image);
        ImageView player_2_health_bar = new ImageView(image);

        //Player portraits are set up for each player
        image = new Image(getClass().getResource("assets/player_1_portrait.png").toString());
        ImageView player_1_portrait = new ImageView(image);
        image = new Image(getClass().getResource("assets/player_2_portrait.png").toString());
        ImageView player_2_portrait = new ImageView(image);


        //Adds images and imageviews to the stackpane
        root.getChildren().addAll(base_layer, player_1_portrait, player_2_portrait, background_iv, player_1.imageView, player_2.imageView, player_1_health_bar, player_2_health_bar);
        root.setAlignment(player_1.imageView, Pos.BOTTOM_LEFT);
        root.setAlignment(player_2.imageView, Pos.BOTTOM_LEFT);


        //The following just sets the base positioning for each player in the game screen.
        base_layer.setFitHeight(scene.getHeight()+ scene.getHeight()*0.2);
        base_layer.setFitWidth(scene.getWidth()+ scene.getWidth()*0.1);

        base_layer.setTranslateY(scene.getHeight()*-0.1);
        player_1.imageView.setTranslateX(scene.getWidth()*-0.20);
        player_2.imageView.setTranslateX(scene.getWidth()*0.5);

        player_1_portrait.setScaleX(0.275);
        player_1_portrait.setScaleY(0.275);

        player_2_portrait.setScaleX(0.275);
        player_2_portrait.setScaleY(0.275);

        player_1_portrait.setTranslateY(scene.getHeight()*-0.35);
        player_1_portrait.setTranslateX(scene.getWidth()*-0.4);

        player_1_health_bar.setTranslateY(scene.getHeight()*-0.45);
        player_1_health_bar.setTranslateX(scene.getWidth()*-0.1);

        player_2_portrait.setTranslateY(scene.getHeight()*-0.35);
        player_2_portrait.setTranslateX(scene.getWidth()*0.4);

        player_2_health_bar.setTranslateY(scene.getHeight()*-0.275);
        player_2_health_bar.setTranslateX(scene.getWidth()*0.1);

        player_1_health_bar.setFitHeight(scene.getHeight()*0.1);
        player_1_health_bar.setFitWidth(scene.getWidth()*0.4);

        player_2_health_bar.setFitHeight(scene.getHeight()*0.1);
        player_2_health_bar.setFitWidth(scene.getWidth()*0.4);


        //assigns controls to player 1 and player 2
        Player_Inputs player_inputs = new Player_Inputs(player_1);
        Player2_Inputs player2_inputs = new Player2_Inputs(player_2);

        //adds Hitboxes to the players
        Player_Hitboxes hitboxes = new Player_Hitboxes(player_1, player_2, scene, player_1_health_bar, player_2_health_bar);

        scene.setOnKeyPressed(e->{ //scene function for whenever an inputs is pressed by a player

            //this block grabs what the current active input is for each player if any
            player_inputs.process_inputs(e.getCode());
            player2_inputs.process_inputs(e.getCode());

            //This is a feature for facing each other in the game
            if(player_1.imageView.getTranslateX() < player_2.imageView.getTranslateX()){
                player_1.set_process_player(false);
                player_2.set_process_player(true);
            }else{
                player_1.set_process_player(true);
                player_2.set_process_player(false);
            }

            //processes any colission or hits if necessary
            hitboxes.process_hitboxes();
        });

        scene.setOnKeyReleased(e ->{

            //checks for when buttons released (dash check if movement pressed twice)
            player_inputs.process_outputs(e.getCode());
            player_inputs.set_dash_check(e.getCode());

            player2_inputs.process_outputs(e.getCode());
            player2_inputs.set_dash_check(e.getCode());
        });


        //final setups for javafx to launch application
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        primaryStage.show();
    }





}
