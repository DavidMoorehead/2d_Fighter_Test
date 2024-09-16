import javafx.scene.input.KeyCode;

import java.security.Key;

public class Player_Inputs { //this is the class that handles all player inputs
    New_Player player;
    private boolean a_key = false;
    private boolean d_key = false;
    private boolean w_key = false;

    private KeyCode dash_check;
    public Player_Inputs(New_Player player){
        this.player = player;
    }




    public void process_inputs(KeyCode key_input){ //the main function that handles the logic
        //essentially it follows the pattern of first checking what key was pressed
        //then it checks the current animation value to determine what should happen
        //whether second attack, first attack, jump attack, etc.

        if(player.get_dash_check() == false){
            update_dash_check(); //resets dash value to null if dash wasnt performed in time
        }

        if(player.get_canCancel() == true) { //some form of idle

            if (key_input.equals(KeyCode.U)) {
                if (player.get_counterAnim_value() > 13 & player.get_counterAnim_value() <= 17) { //light attack with 4frame cancel window (so it shows slap haha)
                    player.set_counterAnim_value(18);
                } else if (player.get_counterAnim_value() <= 11) { //idle
                    player.set_counterAnim_value(12);
                }
            } else if (key_input.equals(KeyCode.I)) {
                if (player.get_counterAnim_value() <= 11) { //idle
                    player.set_counterAnim_value(39);
                }
            } else if (key_input.equals(KeyCode.D)) {
                move_right(KeyCode.D);

            } else if (key_input.equals(KeyCode.A)) {
                move_left(KeyCode.A);
            }


            //W key
            else if(key_input.equals(KeyCode.W)){

                w_key = true;
                player.set_w_key(true);


                player.set_counterAnim_value(112);
            }
        }

        else{ //not idle anymore

            //jumping state allowed inputs
            if(player.get_isJumping() == true) {
                if (key_input.equals(KeyCode.A)) { //move left
                    player.set_leftJump(true);
                } else if (key_input.equals(KeyCode.D)) { //move right
                    player.set_rightJump(true);
                }

                //jump attacks
                if(player.get_isJumpAttacking() == false){
                    if (key_input.equals(KeyCode.U)){ //jump light attack
                        player.set_isJumpAttacking(true);
                        player.set_counterAnim_value(120);
                    }
                }
            }
        }



    }


    public void process_outputs(KeyCode key_input){ //this is for the afterwards effect.
        //Essentially after animations are done, and you're allowed to press the button again
        //This needs to happen to prevent just infinite spamming of attacks.
        if(key_input.equals(KeyCode.W)){
            player.set_w_key(false);
            w_key = false;
            if(player.get_isJumping() == false){
                player.set_counterAnim_value(-1);
            }
        }


        if(key_input.equals(KeyCode.A)){
            player.set_leftJump(false);
            if(player.get_isJumping() == false && player.get_canCancel() == true){
                player.set_a_key(false);
                a_key = false;
                player.set_counterAnim_value(-1); //reset to Idle
                player.set_dash_check(true);
            }
        }

        if(key_input.equals(KeyCode.D)){
            player.set_rightJump(false);
            if(player.get_isJumping() == false && player.get_canCancel() == true){
                player.set_d_key(false);
                d_key = false;
                player.set_counterAnim_value(-1); //reset to Idle
                player.set_dash_check(true);
            }
        }

        set_dash_check(key_input);
    }


    public boolean get_a_key(){
        return a_key;
    }

    public boolean get_d_key(){
        return d_key;
    }

    public void set_dash_check(KeyCode key_input)
    {
        dash_check = key_input;
    }

    public void update_dash_check(){
        dash_check = null;
    }

    public void move_left(KeyCode key_input){ //this movement function, and all the rest function the same.
        //they just activate booleans for other code to check for whenever they're using logic at different
        //animation values
        player.set_leftJump(true);
        if(player.get_isJumping() == false) {
            a_key = true;
            player.set_a_key(true);

            if(player.get_process_player() == true){
                forward_dash(key_input);
            } else{
                back_dash(key_input);
            }

        }
    }

    public void move_right(KeyCode key_input){
        player.set_rightJump(true);

        if(player.get_isJumping() == false) {
            d_key = true;
            player.set_d_key(true);
            if(player.get_process_player() == false){
                forward_dash(key_input);
            } else{
                back_dash(key_input);
            }


        }
    }

    public void forward_dash(KeyCode key_input){
        if (dash_check == key_input && player.get_dash_check() == true) { //3 frame dash window
            player.set_counterAnim_value(449);
        } else{
            forward_walk(key_input);
        }
    }
    public void forward_walk(KeyCode key_input){
        if(player.get_counterAnim_value() <= 11) {//idle
            player.set_counterAnim_value(425);
        }
    }

    public void back_dash(KeyCode key_input){
        if (dash_check == key_input && player.get_dash_check() == true) { //3 frame dash window
            player.set_counterAnim_value(458);
        } else{
            back_walk(key_input);
        }
    }

    public void back_walk(KeyCode key_input){
        if(player.get_counterAnim_value() <= 11) {//idle
            player.set_counterAnim_value(438);
        }
    }
}

