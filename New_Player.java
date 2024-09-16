import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;


public class New_Player{ //This class is the base class for either player
    //presets for boolean locks for inputs to prevent gameplay issues,
    //as well as other presets like file pathways and what not for convenience
    private AtomicInteger counterAnim = new AtomicInteger(-1 ); //for starting at increment and get
    private Image player_spritesheet;
    public Timeline anim;
    public ImageView imageView;
    private boolean can_cancel = true;
    private String base_address = "assets/hisui/hisui/";
    private String cur_base = "";

    private boolean a_key = false;
    private boolean d_key = false;
    private boolean w_key = false;
    private boolean dash_check = false;
    private Player_Movement player_movement;

    private boolean is_jumping = false;
    private boolean left_jump = false;

    private boolean right_jump = false;
    private boolean jump_attacking = false;

    private boolean player_type = false;
    private boolean facing;
    private double frameRate;
    private String name;
    private int health = 100;
    private String last_attack;


    //Player Constructor -> Naming for reference, attaches player_movement(not abstract class), player1/player2 assignment
    //and then the rate at which everything is processed
    public New_Player(String character, Player_Movement pm, boolean player_type, double frameRate){

        this.name = character;

        //This is just the first idle animation frame
        player_spritesheet = new Image(getClass().getResource(base_address + "000_000.bmp_ID_0.png").toString());
        ImageView temp = animation_iv(player_spritesheet);
        this.imageView = temp;
        this.player_movement = pm;

        this.player_type = player_type;
        this.frameRate = frameRate;



        process_player_view();


        anim = animation_generator(imageView);
        anim.setCycleCount(Animation.INDEFINITE);

        anim.play();
    }

    //This function handles pixel adjustments for frames during gameplay.
    //It removes the green from the background and if necessary inverts the colours (for player 2)
    public ImageView animation_iv(Image image) {



        int start_width = 0;
        int start_height = 0;

        int areaWidth = (int) image.getWidth();  // Get image width
        int areaHeight = (int) image.getHeight();  // Get image height


        javafx.scene.image.WritableImage modifiedImage = new javafx.scene.image.WritableImage(areaWidth, areaHeight);
        PixelReader pixelReader = image.getPixelReader();
        PixelWriter pixelWriter = modifiedImage.getPixelWriter();

        if(name == "player_1") { //player 1 checks if it's green and if so makes all those pixels transparent
            for (int x = 0; x < areaWidth; x++) {
                for (int y = 0; y < areaHeight; y++) {
                    if (pixelReader.getColor(start_width + x, start_height + y).equals(Color.rgb(0, 140, 74))) {
                        pixelWriter.setColor(x, y, Color.TRANSPARENT);
                    } else {
                        pixelWriter.setColor(x, y, pixelReader.getColor(start_width + x, start_height + y));
                    }
                }
            }
        } else{ //player 2 checks if green and makes everything transparent, then inverts the colours
            for (int x = 0; x < areaWidth; x++) {
                for (int y = 0; y < areaHeight; y++) {
                    Color originalColor = pixelReader.getColor(start_width + x, start_height + y);
                    if (originalColor.equals(Color.rgb(0, 140, 74))) {
                        pixelWriter.setColor(x, y, Color.TRANSPARENT);
                    } else {
                        // Inverts the colours here
                        Color invertedColor = originalColor.invert();
                        pixelWriter.setColor(x, y, invertedColor);
                    }
                }
            }

        }

        //this is done in two steps to prevent potential errors
        ImageView temp = new ImageView(modifiedImage);
        return temp;
    }


    public Timeline animation_generator(ImageView imageView){ //this is what actually processe the animations
        //it sends info to other functions as needed to start animations and adjust allowable game inputs

        Timeline temp =  new Timeline(new KeyFrame(Duration.seconds(frameRate), e -> {

            int currentValue = counterAnim.incrementAndGet();
            Image temp_img;

            cur_base = get_address_value(currentValue);

            //sending info to player movement to process applicable movements if needed
            player_movement.process_player_movement(currentValue, imageView, left_jump, right_jump, get_process_player());


            //grabs and sets sprite based on animation location
            temp_img = new Image(getClass().getResource(base_address + cur_base).toString());
            this.imageView.setImage(animation_iv(temp_img).getImage()); // Update the ImageView


            //Flips the sprites horizontal to make sure they're facing
            if(facing == false){
                imageView.setScaleX(1);
            } else{
                imageView.setScaleX(-1);
            }

            process_anim_counter();


            //this is to allow 3 frame window for dash inputs
            if(currentValue>3){
                set_dash_check(false);
            }


        }));

        return temp;
    }

    public String get_address_value(int currentValue){ //this processes the animations, and when they can be cancelled
        //redefine base string value for looping correct sprites
        //Essentially this just tells where to look for sprites, and what rules are allowed in that state
        setCan_cancel(false);

        if(currentValue<=9){
            setCan_cancel(true);
            cur_base = "000_00" + currentValue + ".bmp_ID_"+ currentValue + ".png";
        } else if(currentValue <=11 & currentValue>9){
            setCan_cancel(true);
            cur_base = "000_0" + currentValue + ".bmp_ID_"+ currentValue + ".png";
        }

        //light attack
        else if(currentValue>11 & currentValue <=17){
            setCan_cancel(true);
            cur_base = "001_00" + (currentValue-12) + ".bmp_ID_" + currentValue + ".png";
        } else if (currentValue>17 && currentValue <=24){ //light attack 2

            cur_base = "002_00" + (currentValue-18) + ".bmp_ID_" + currentValue + ".png";
        } else if (currentValue>=40 && currentValue <49){ //AA attack (the soup ladel)

            cur_base = "010_00" + (currentValue-40) + ".bmp_ID_" + currentValue + ".png";




            //WALKING BELOW

        } else if (currentValue==426){ //walk startup frame
            setCan_cancel(true);
            cur_base = "400_00" + (currentValue-426) + ".bmp_ID_" + currentValue + ".png";
        }else if(currentValue>=427 && currentValue<=437){ //walk frame loop
            setCan_cancel(true);

            if(currentValue-426<=9){

                cur_base = "400_00" + (currentValue-426) + ".bmp_ID_" + currentValue + ".png";
            }else{
                cur_base = "400_0" + (currentValue-426) + ".bmp_ID_" + currentValue + ".png";
            }
        } else if (currentValue==426001){ //just does the inverse of startup walk frame
            setCan_cancel(true);
            cur_base = "400_000.bmp_ID_426.png";
        }

        else if(currentValue == 438){
            setCan_cancel(true);
            cur_base = "401_000.bmp_ID_438.png";
        } else if(currentValue>438 && currentValue<=449){
            setCan_cancel(true);

            if(currentValue-438<=9){
                cur_base = "401_00" + (currentValue-438) + ".bmp_ID_" + currentValue + ".png";
            } else{
                cur_base = "401_0" + (currentValue-438) + ".bmp_ID_" + currentValue + ".png";
            }
        }

        else if(currentValue==449001){
            setCan_cancel(true);
            cur_base = "401_000.bmp_ID_438.png";
        }




        //RUNNING/DASH ANIMS BELOW


        else if(currentValue == 450){
            setCan_cancel(true);
            cur_base = "402_000.bmp_ID_450.png";
        }else if(currentValue>450 && currentValue<=458){
            setCan_cancel(true);

            if(currentValue-450<=9){
                cur_base = "402_00" + (currentValue-450) + ".bmp_ID_" + currentValue + ".png";
            } else{
                cur_base = "402_0" + (currentValue-450) + ".bmp_ID_" + currentValue + ".png";
            }
        } else if(currentValue == 458001){
            setCan_cancel(true);
            cur_base = "402_000.bmp_ID_450.png";
        }

        //backdash
        else if(currentValue>=459 && currentValue <=462){
            setCan_cancel(false);

            if((9+((currentValue-462)*-1))==9){
                cur_base = "402_00" + (9+((currentValue-462)*-1)) + ".bmp_ID_" + (((currentValue-462)*-1)+459) + ".png";
            } else{
                cur_base = "402_0" + (9+((currentValue-462)*-1)) + ".bmp_ID_" + (((currentValue-462)*-1)+459) + ".png";
            }
        }



        //jump
        else if(currentValue>=113 && currentValue <=120){
            setCan_cancel(false);
            is_jumping = true;
            cur_base = "200_00" + (currentValue-113) + ".bmp_ID_" + currentValue + ".png";


        }

        else if(currentValue>= 121 && currentValue <=126){
            setCan_cancel(false);
            is_jumping = true;
            cur_base = "201_00" + (currentValue-121) + ".bmp_ID_" + currentValue + ".png";
        }

        return cur_base;
    }


    public void process_anim_counter(){ //this is what happens at the end of each animation
//anim counter is the current frame you're in for the frame logic out of all the possible animations
        //Idle animation loop
        if (counterAnim.get() == 11) {
            set_last_attack("none"); //reset quick fix for hitboxes and combo damage so you cant spam. Its a quick bugfix
            counterAnim.set(-1);

        } else  if(counterAnim.get() == 17){//idle anim loop
            set_last_attack("none"); //reset quick fix for hitboxes and combo damage so you cant spam. Its a quick bugfix
            setCan_cancel(true);
            set_counterAnim_value(-1); //return to idle
        } else if(counterAnim.get() == 25){
            set_last_attack("none"); //reset quick fix for hitboxes and combo damage so you cant spam. Its a quick bugfix
            setCan_cancel(true);
            set_counterAnim_value(-1);
        } else if(counterAnim.get() == 49){
            set_last_attack("none"); //reset quick fix for hitboxes and combo damage so you cant spam. Its a quick bugfix
            setCan_cancel(true);
            set_counterAnim_value(-1);

            //WALKING STUFF BELOW

        } else if(counterAnim.get() == 437){ //forward walk
            if(d_key == true){
                setCan_cancel(true);
                set_counterAnim_value(427);
            }else{
                set_counterAnim_value(426000);
            }
        }else if(counterAnim.get() == 426001){ //reset to idle
            setCan_cancel(true);
            set_counterAnim_value(-1);
        }

        else if(counterAnim.get() == 449){
            if(a_key == true){
                setCan_cancel(true);
                set_counterAnim_value(439);
            } else{
                set_counterAnim_value(438000);
            }
        } else if(counterAnim.get() == 438001){
            setCan_cancel(true);
            set_counterAnim_value(-1);
        }



        //DASH ANIMS BELOW
        else if(counterAnim.get() == 458){ //forward dash
            if(d_key == true){
                setCan_cancel(true);
                set_counterAnim_value(450);
            } else{
                set_counterAnim_value(458000);
            }
        } else if(counterAnim.get() == 458001){
            setCan_cancel(true);
            set_counterAnim_value(-1);
        }else if(counterAnim.get() == 462){ //resets backdash
            setCan_cancel(true);
            set_counterAnim_value(-1); //idle
        }


        //Jump

        else if(counterAnim.get() == 120){
            setCan_cancel(true);
            set_counterAnim_value(-1);


            set_leftJump(false);
            set_rightJump(false);
            is_jumping = false;

        }

        else if(counterAnim.get() >= 124 && counterAnim.get()<=125){
            set_last_attack("none"); //reset quick fix for hitboxes and combo damage so you cant spam. Its a quick bugfix
            if(imageView.getTranslateY()<0){
                set_isJumpAttacking(false);
            } else{
                set_isJumpAttacking(true);
            }
        }

        else if (counterAnim.get() == 126){
            setCan_cancel(true);
            set_counterAnim_value(-1);

            set_leftJump(false);
            set_rightJump(false);
            is_jumping = false;
            set_isJumpAttacking(false);
        }



    }


    public void set_counterAnim_value(int value){
        counterAnim.set(value);
    }

    public void setCan_cancel(boolean new_value){
        can_cancel = new_value;
    }

    public int get_counterAnim_value(){
        return counterAnim.get();
    }

    public void set_a_key(boolean new_value){
        a_key = new_value;
    }

    public void set_d_key(boolean new_value){
        d_key = new_value;
    }

    public void set_w_key(boolean new_value){
        w_key = new_value;
    }

    public void set_dash_check(boolean new_value){
        dash_check = new_value;
    }

    public boolean get_dash_check(){
        return dash_check;
    }

    public boolean get_canCancel(){
        return can_cancel;
    }

    public boolean get_isJumping(){
        return is_jumping;
    }

    public void set_leftJump(boolean new_value){
        left_jump = new_value;
    }

    public void set_rightJump(boolean new_value){
        right_jump = new_value;
    }

    public boolean get_isJumpAttacking(){
        return jump_attacking;
    }

    public void set_isJumpAttacking(boolean new_value){
        jump_attacking = new_value;
    }

    public void process_player_view(){ //this just makes the players face each other
        //even if sides swap
        if(player_type == false){
            this.facing = false;
        } else{
            this.facing = true;
        }
    }

    public boolean get_process_player(){
        return facing;
    }

    public void set_process_player(boolean new_value){
        facing = new_value;
    }

    public void set_health(int value){ //this is a quick way to say the game is over

        health-= value;
        health-= value;
        if(health <= 0){
            System.out.println("Fight Over!");
            System.out.println(name + " loses!");
        }
    }

    public int get_health(){
        return health;
    }

    public String get_last_attack(){
        return last_attack;
    }

    public void set_last_attack(String new_value){
        last_attack = new_value;
    }
}