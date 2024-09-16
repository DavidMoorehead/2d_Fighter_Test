import javafx.scene.input.KeyCode;

public class Player2_Inputs { //Player_inputs cloned for Player2 as they need to be different.
    //I could've made a more modular code that handled any inputs for players
    //but time was of the essence, and I had only 2 players so it was the most efficient decision
    New_Player player;
    private boolean a_key = false;
    private boolean d_key = false;
    private boolean w_key = false;

    private KeyCode dash_check;
    public Player2_Inputs(New_Player player){
        this.player = player;
    }




    public void process_inputs(KeyCode key_input){ //Identical to Player_Inputs, just the
        //Input values it's checking are swapped (like NUMPAD4 instead of U key)

        if(player.get_dash_check() == false){
            update_dash_check(); //resets dash value to null
        }

        if(player.get_canCancel() == true) { //some form of idle

            if (key_input.equals(KeyCode.NUMPAD4)) {
                if (player.get_counterAnim_value() > 13 & player.get_counterAnim_value() <= 17) { //light attack with 4frame cancel window (so it shows slap haha)
                    player.set_counterAnim_value(18);
                } else if (player.get_counterAnim_value() <= 11) { //idle
                    player.set_counterAnim_value(12);
                }
            } else if (key_input.equals(KeyCode.NUMPAD5)) {
                if (player.get_counterAnim_value() <= 11) { //idle
                    player.set_counterAnim_value(39);
                }
            } else if (key_input.equals(KeyCode.LEFT)) {
                move_left(KeyCode.LEFT);
            }else if (key_input.equals(KeyCode.RIGHT)) {
                move_right(KeyCode.RIGHT);
            }


            //W key
            else if(key_input.equals(KeyCode.UP)){

                w_key = true;
                player.set_w_key(true);


                player.set_counterAnim_value(112);
            }
        }

        else{ //not idle anymore

            //jumping state allowed inputs
            if(player.get_isJumping() == true) {
                if (key_input.equals(KeyCode.LEFT)) { //move left
                    player.set_leftJump(true);
                } else if (key_input.equals(KeyCode.RIGHT)) { //move right
                    player.set_rightJump(true);
                }

                //jump attacks
                if(player.get_isJumpAttacking() == false){
                    if (key_input.equals(KeyCode.NUMPAD4)){ //jump light attack
                        player.set_isJumpAttacking(true);
                        player.set_counterAnim_value(120);
                    }
                }
            }
        }



    }


    public void process_outputs(KeyCode key_input){
        if(key_input.equals(KeyCode.UP)){
            player.set_w_key(false);
            w_key = false;
            if(player.get_isJumping() == false){
                player.set_counterAnim_value(-1);
            }
        }


        if(key_input.equals(KeyCode.LEFT)){
            player.set_leftJump(false);
            if(player.get_isJumping() == false && player.get_canCancel() == true){
                player.set_a_key(false);
                a_key = false;
                player.set_counterAnim_value(-1); //reset to Idle
                player.set_dash_check(true);
            }
        }

        if(key_input.equals(KeyCode.RIGHT)){
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

    public void move_left(KeyCode key_input){
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

