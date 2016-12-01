package ultrapoulet.androidgame.framework.helpers;

/**
 * Created by John on 11/7/2016.
 */

public class Button {

    private int leftX;
    private int rightX;
    private int topY;
    private int botY;

    private boolean active;
    private String name;

    public Button(int leftX, int rightX, int topY, int botY, boolean active, String name){
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.botY = botY;
        this.active = active;
        this.name = name;
    }

    public boolean isActive(){
        return this.active;
    }

    public void setActive(boolean state){
        this.active = state;
    }

    public String getName(){
        return this.name;
    }

    public boolean isPressed(int x, int y){
        if(!this.active){
            return false;
        }
        return (x >= leftX && x <= rightX && y >= topY && y <= botY);
    }

    public void setCoordinates(int leftX, int rightX, int topY, int botY){
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.botY = botY;
    }
}
