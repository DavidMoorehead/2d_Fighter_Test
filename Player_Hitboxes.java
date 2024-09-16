import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;


public class Player_Hitboxes { //this is the class for handling hit detection for the game
    private New_Player player_1;
    private New_Player player_2;
    private Scene scene;
    private ImageView player_1_hp;
    private ImageView player_2_hp;

    public Player_Hitboxes(New_Player player_1, New_Player player_2, Scene scene, ImageView player_1_hp, ImageView player_2_hp){
        this.player_1 = player_1;
        this.player_2 = player_2;

        this.scene = scene;

        this.player_1_hp = player_1_hp;
        this.player_2_hp = player_2_hp;
    }

    public void process_hitboxes(){
        //Player 1 attacks. This could've been more modular but I had 2 hours left in challenge
        //essentially it just checks what attack is being processed, and if it can be processed again
        //if processed, it then checks if the opponent is close enough, and if so assigns damage
        if(player_1.get_counterAnim_value()>=12 && player_1.get_counterAnim_value()<=17 && player_1.get_last_attack() != "u1"){ //light attack hitbox
            player_1.set_last_attack("u1");

            if(player_1.get_process_player() == false){ //facing right
                if(player_2.imageView.getTranslateX() > player_1.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_2.imageView.getTranslateY()==player_1.imageView.getTranslateY()){
                            process_damage(25, player_2);
                            update_health_bars(true);

                        }

                    }
                }
            } else{ //facing left
                if(player_2.imageView.getTranslateX() < player_1.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_2.imageView.getTranslateY()==player_1.imageView.getTranslateY()) {
                            process_damage(25, player_2);
                            update_health_bars(true);
                        }
                    }
                }
            }
        }

        //Light attack 2 on ground
        else if(player_1.get_counterAnim_value()>17 && player_1.get_counterAnim_value()<=25 && player_1.get_last_attack()!= "u2"){ //light attack hitbox
            player_1.set_last_attack("u2");

            if(player_1.get_process_player() == false){ //facing right
                if(player_2.imageView.getTranslateX() > player_1.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_2.imageView.getTranslateY()==player_1.imageView.getTranslateY()){
                            process_damage(50, player_2);
                            update_health_bars(true);
                        }

                    }
                }
            } else{ //facing left
                if(player_2.imageView.getTranslateX() < player_1.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_2.imageView.getTranslateY()==player_1.imageView.getTranslateY()) {
                            process_damage(50, player_2);
                            update_health_bars(true);
                        }
                    }
                }
            }
        }

        //I attack, basically your only anti air. Can't hit grounded opponents
        else if(player_1.get_counterAnim_value()>=39 && player_1.get_counterAnim_value()<=48 && player_1.get_last_attack()!= "I"){
            player_1.set_last_attack("I");

            if(player_1.get_process_player() == false){ //facing right
                if(player_2.imageView.getTranslateX() > player_1.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.15){
                        if(player_2.imageView.getTranslateY()<player_1.imageView.getTranslateY()){
                            process_damage(100, player_2);
                            update_health_bars(true);
                        }
                    }
                }
            } else { //facing right
                if(player_2.imageView.getTranslateX() < player_1.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.15){
                        if(player_2.imageView.getTranslateY()<player_1.imageView.getTranslateY()){
                            process_damage(100, player_2);
                            update_health_bars(true);
                        }
                    }
                }
            }
        }


        //your only jump attack that you can do twice
        else if (player_1.get_counterAnim_value()>=120 && player_1.get_counterAnim_value()<=125 && player_1.get_last_attack()!= "j"){
            player_1.set_last_attack("j");

            if(player_1.get_process_player() == false){ //facing right
                if(player_2.imageView.getTranslateX() > player_1.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.2){
                        if(player_2.imageView.getTranslateY()>player_1.imageView.getTranslateY()){
                            process_damage(40, player_2);
                            update_health_bars(true);
                        }
                    }
                }
            } else { //facing left
                if(player_2.imageView.getTranslateX() < player_1.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.2){
                        if(player_2.imageView.getTranslateY()>player_1.imageView.getTranslateY()){
                            process_damage(40, player_2);
                            update_health_bars(true);
                        }
                    }
                }
            }
        }


        //Player 2
        else if(player_2.get_counterAnim_value()>=12 && player_2.get_counterAnim_value()<=17 && player_2.get_last_attack() != "u1"){
            player_2.set_last_attack("u1");

            if(player_2.get_process_player() == false){ //facing right
                if(player_1.imageView.getTranslateX() > player_2.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_1.imageView.getTranslateY()==player_2.imageView.getTranslateY()) {
                            process_damage(25, player_1);
                            update_health_bars(false);
                        }
                    }
                }
            } else{ //facing left
                if(player_1.imageView.getTranslateX() < player_2.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_2.imageView.getTranslateY()==player_1.imageView.getTranslateY()) {
                            process_damage(25, player_1);
                            update_health_bars(false);
                        }
                    }
                }
            }
        }


        else if(player_2.get_counterAnim_value()>17 && player_2.get_counterAnim_value()<=25 && player_2.get_last_attack()!= "u2"){ //light attack hitbox
            player_2.set_last_attack("u2");

            if(player_2.get_process_player() == false){ //facing right
                if(player_1.imageView.getTranslateX() > player_2.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_1.imageView.getTranslateY()==player_2.imageView.getTranslateY()){
                            process_damage(50, player_1);
                            update_health_bars(false);
                        }

                    }
                }
            } else{ //facing left
                if(player_1.imageView.getTranslateX() < player_2.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.17){
                        if(player_1.imageView.getTranslateY()==player_2.imageView.getTranslateY()) {
                            process_damage(50, player_1);
                            update_health_bars(false);
                        }
                    }
                }
            }
        }

        else if(player_2.get_counterAnim_value()>=39 && player_2.get_counterAnim_value()<=48 && player_2.get_last_attack()!= "I"){
            player_2.set_last_attack("I");

            if(player_2.get_process_player() == false){ //facing right
                if(player_1.imageView.getTranslateX() > player_2.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.15){
                        if(player_1.imageView.getTranslateY()<player_2.imageView.getTranslateY()){
                            process_damage(100, player_1);
                            update_health_bars(false);
                        }
                    }
                }
            } else { //facing right
                if(player_1.imageView.getTranslateX() < player_2.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.15){
                        if(player_1.imageView.getTranslateY()<player_2.imageView.getTranslateY()){
                            process_damage(100, player_1);
                            update_health_bars(false);
                        }
                    }
                }
            }
        }

        else if (player_2.get_counterAnim_value()>=120 && player_2.get_counterAnim_value()<=125 && player_2.get_last_attack()!= "j"){

            player_2.set_last_attack("j");

            if(player_2.get_process_player() == false){ //facing right
                if(player_1.imageView.getTranslateX() > player_2.imageView.getTranslateX()){
                    if(player_1.imageView.getTranslateX()-player_2.imageView.getTranslateX()<scene.getWidth()*0.2){
                        if(player_1.imageView.getTranslateY()>player_2.imageView.getTranslateY()){
                            process_damage(40, player_1);
                            update_health_bars(false);
                        }
                    }
                }
            } else { //facing left
                if(player_1.imageView.getTranslateX() < player_2.imageView.getTranslateX()){
                    if(player_2.imageView.getTranslateX()-player_1.imageView.getTranslateX()<scene.getWidth()*0.2){
                        if(player_1.imageView.getTranslateY()>player_2.imageView.getTranslateY()){
                            process_damage(40, player_1);
                            update_health_bars(false);
                        }
                    }
                }
            }
        }
    }

    public void process_damage(int damage, New_Player player_hit){
        player_hit.set_health(damage);

    }

    public void update_health_bars(boolean health_bar) { //This just puts a basic pixel texture overlay
        //to the original imageview of the healthbar to make it look like it's being depleted as
        //the hp values go down

        int start_width = 0;
        int start_height = 0;



        if (health_bar == false) {//player 1


            int areaWidth = (int) player_1_hp.getImage().getWidth();  // Get image width
            int areaHeight = (int) player_1_hp.getImage().getHeight();  // Get image height



            double temp = player_1.get_health();
            double x_value = areaWidth - (temp/100 * areaWidth);

            javafx.scene.image.WritableImage modifiedImage = new javafx.scene.image.WritableImage(areaWidth, areaHeight);
            PixelReader pixelReader = player_2_hp.getImage().getPixelReader();
            PixelWriter pixelWriter = modifiedImage.getPixelWriter();

            for (int x = 0; x < areaWidth; x++) {
                for (int y = 0; y < areaHeight; y++) {
                    Color originalColor = pixelReader.getColor(start_width + x, start_height + y);

                    if (x <= x_value && x> areaWidth*0.04 && y <= areaHeight*0.675 && y >= areaHeight*0.2) {


                        pixelWriter.setColor(x, y, Color.rgb(200, 0, 0));

                    }else {
                        pixelWriter.setColor(x, y, pixelReader.getColor(start_width + x, start_height + y));
                    }
                }
            }

            player_1_hp.setImage(modifiedImage);



        } else { //player 2


            int areaWidth = (int) player_2_hp.getImage().getWidth();  // Get image width
            int areaHeight = (int) player_2_hp.getImage().getHeight();  // Get image height



            double temp = player_2.get_health();
            double x_value = areaWidth - (temp/100 * areaWidth);

            javafx.scene.image.WritableImage modifiedImage = new javafx.scene.image.WritableImage(areaWidth, areaHeight);
            PixelReader pixelReader = player_2_hp.getImage().getPixelReader();
            PixelWriter pixelWriter = modifiedImage.getPixelWriter();


            for (int x = 0; x < areaWidth; x++) {
                for (int y = 0; y < areaHeight; y++) {
                    Color originalColor = pixelReader.getColor(start_width + x, start_height + y);

                    if (x <= x_value && x> areaWidth*0.04 && y <= areaHeight*0.675 && y >= areaHeight*0.2) {


                        pixelWriter.setColor(x, y, Color.rgb(200, 0, 0));

                    }else {
                        pixelWriter.setColor(x, y, pixelReader.getColor(start_width + x, start_height + y));
                    }
                }
            }

            player_2_hp.setImage(modifiedImage);
        }
    }
}
