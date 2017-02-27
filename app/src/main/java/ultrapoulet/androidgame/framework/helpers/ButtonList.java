package ultrapoulet.androidgame.framework.helpers;

import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;

/**
 * Created by John on 11/7/2016.
 */

public class ButtonList {

    protected List<Button> buttons = new ArrayList<>();

    public void addButton(Button newButton){
        buttons.add(newButton);
    }

    public Button get(int index) {
        return buttons.get(index);
    }

    public Button getButtonPressed(int x, int y){
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).isPressed(x,y)){
                return buttons.get(i);
            }
        }
        return null;
    }

    //For use for numbered buttons
    public int getIndexPressed(int x, int y){
        for(int i = 0; i < buttons.size(); i++){
            if(buttons.get(i).isPressed(x,y)){
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return buttons.size();
    }

    public void drawImage(Graphics g){
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).drawImage(g);
        }
    }

    public void drawString(Graphics g, Paint p){
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).drawString(g, p);
        }
    }
}
