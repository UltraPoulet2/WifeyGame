package ultrapoulet.androidgame.framework.helpers;

import android.graphics.Paint;

import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 2/6/2017.
 */

public class DropdownMenu extends ButtonList {

    private Button menuButton;
    private Paint textPaint;

    public DropdownMenu(int leftX, int rightX, int topY, int botY, Image menuBg, Image selectBg, Paint textPaint, List<String> options){
        this.textPaint = textPaint;
        int height = botY - topY;
        menuButton = new Button(leftX, rightX, topY, botY, true, options.get(0), menuBg, menuBg);
        for(int i = 0; i < options.size(); i++) {
            buttons.add(new Button(leftX, rightX, topY + (height * (i + 1)), botY + (height * (i + 1)), true, options.get(i), selectBg, null));
        }
    }

    public boolean isMenuPressed(int x, int y){
        return menuButton.isPressed(x, y);
    }

    public void menuActivated(){
        menuButton.setActive(false);
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).setActive(true);
        }
    }

    public void menuDeactivated(){
        menuButton.setActive(true);
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).setActive(false);
        }
    }

    //Sets the title to the value of the button at the selected index
    public void setTitle(int index){
        menuButton.setName(buttons.get(index).getName());
    }

    @Override
    public void drawImage(Graphics g) {
        menuButton.drawImage(g);
        //This will draw the dropdown list of buttons.
        super.drawImage(g);
        if(menuButton.isActive()){
            menuButton.drawString(g, textPaint);
        }
        else{
            for(int i = 0; i < buttons.size(); i++){
                buttons.get(i).drawString(g, textPaint);
            }
        }
    }
}
