import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
public class Player_Movement { //this class is for all the player movements
//it also defines the constraints in terms of the areas of the screen the players can move


    ImageView iv;
    private boolean a_key = false;
    private boolean d_key = false;

    private KeyCode dash_check;
    private Scene scene;

    private double end_right_pos;
    private double end_left_pos;
    private double right_value;
    private double left_value;



    public Player_Movement(Scene scene){
        this.scene = scene;
        this.end_right_pos = scene.getWidth()*0.725;
        this.end_left_pos = scene.getWidth()*-0.20;


    }

    public void process_player_movement(int value, ImageView iv, boolean left_jump, boolean right_jump, boolean facing_value){
        //this function just does the following:
        //First, it figures out if you're on the left side of the opponent or right side
        //Second, it it figures out what animation you're currently in (value) and then assigns
        //a position due to it or if too far off screen prevents a position



        this.right_value = iv.getTranslateX()+ scene.getWidth()*0.05;
        this.left_value = iv.getTranslateX()- scene.getWidth() * 0.035;


        if(facing_value == false) {
            //running/dashes
            if (value >= 459 && value <= 462) {
                if (left_value > end_left_pos) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }
            } else if (value >= 450 && value <= 458) {


                if (right_value < end_right_pos) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.05);
                }
            }


            //walking
            else if (value >= 427 && value <= 437) {
                if (right_value < end_right_pos) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.02);
                }
            } else if (value >= 438 && value <= 449) {
                if (left_value > end_left_pos) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.015);
                }
            }

            //jumping

            else if (value >= 113 && value <= 116) { //jump up


                iv.setTranslateY(iv.getTranslateY() - scene.getHeight() * 0.075);
                if (left_jump == true && left_value > end_left_pos && right_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }
                if (right_jump == true && right_value < end_right_pos && left_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.035);
                }

            } else if (value >= 117 && value <= 120) { //jump down
                iv.setTranslateY(iv.getTranslateY() + scene.getHeight() * 0.075);
                if (left_jump == true && left_value > end_left_pos && right_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }
                if (right_jump == true && right_value < end_right_pos && left_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.035);
                }
            } else if (value >= 121 && value <= 126) {
                if (iv.getTranslateY() + scene.getHeight() * 0.025 < 0) {

                    iv.setTranslateY(iv.getTranslateY() + scene.getHeight() * 0.025);
                } else {
                    iv.setTranslateY(0);

                }
                if (left_jump == true && left_value > end_left_pos && right_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }
                if (right_jump == true && right_value < end_right_pos && left_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.035);
                }

                if (iv.getTranslateY() != 0 && value == 126) {
                    iv.setTranslateY(0);
                }
            }
        } else{
            //running/dashes
            if (value >= 459 && value <= 462) {

                if (right_value < end_right_pos) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.05);
                }

            } else if (value >= 450 && value <= 458) {
                if (left_value > end_left_pos) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }

            }


            //walking
            else if (value >= 427 && value <= 437) {
                if (left_value > end_left_pos) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.015);
                }
            } else if (value >= 438 && value <= 449) {


                if (right_value < end_right_pos) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.02);
                }
            }

            //jumping

            else if (value >= 113 && value <= 116) { //jump up


                iv.setTranslateY(iv.getTranslateY() - scene.getHeight() * 0.075);
                if (left_jump == true && left_value > end_left_pos && right_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }
                if (right_jump == true && right_value < end_right_pos && left_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.035);
                }

            } else if (value >= 117 && value <= 120) { //jump down
                iv.setTranslateY(iv.getTranslateY() + scene.getHeight() * 0.075);
                if (left_jump == true && left_value > end_left_pos && right_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }
                if (right_jump == true && right_value < end_right_pos && left_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.035);
                }
            } else if (value >= 121 && value <= 126) {
                if (iv.getTranslateY() + scene.getHeight() * 0.025 < 0) {

                    iv.setTranslateY(iv.getTranslateY() + scene.getHeight() * 0.025);
                } else {
                    iv.setTranslateY(0);

                }
                if (left_jump == true && left_value > end_left_pos && right_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() - scene.getWidth() * 0.035);
                }
                if (right_jump == true && right_value < end_right_pos && left_jump == false) {
                    iv.setTranslateX(iv.getTranslateX() + scene.getWidth() * 0.035);
                }

                if (iv.getTranslateY() != 0 && value == 126) {
                    iv.setTranslateY(0);
                }
            }
        }
    }
}
