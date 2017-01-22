package ultrapoulet.androidgame.framework.helpers;

import java.util.ArrayList;
import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;

/**
 * Created by John on 11/7/2016.
 */

public class ButtonList {

    private List<Button> buttons = new ArrayList<>();

    public void addButton(Button newButton){
        buttons.add(newButton);
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

    //For use for numbered buttons
    public void setIndexActive(int index, boolean value){
        if(index < buttons.size() && index >= 0){
            buttons.get(index).setActive(value);
        }
        else{
            System.out.println("ButtonList:setIndexActive(): Invalid index provided: " + index);
        }
    }

    //For use for numbered buttons
    public void setIndexCoord(int index, int leftX, int rightX, int topY, int botY){
        if(index < buttons.size() && index >= 0){
            buttons.get(index).setCoordinates(leftX, rightX, topY, botY);
        }
        else{
            System.out.println("ButtonList:setIndexCoord(): Invalid index provided: " + index);
        }
    }

    public void drawImage(Graphics g){
        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).drawImage(g);
        }
    }
}
