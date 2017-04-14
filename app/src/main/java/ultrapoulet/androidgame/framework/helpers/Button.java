package ultrapoulet.androidgame.framework.helpers;

import android.graphics.Paint;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 11/7/2016.
 */

public class Button {

    private int leftX;
    private int rightX;
    private int topY;
    private int botY;

    private boolean active;
    private boolean hidden = false;
    private String name;

    private Image activeImage;
    private Image inactiveImage;

    public Button(int leftX, int rightX, int topY, int botY, boolean active, String name){
        this(leftX, rightX, topY, botY, active, name, null, null);
    }

    public Button(int leftX, int rightX, int topY, int botY, boolean active, String name, Image activeImage){
        this(leftX, rightX, topY, botY, active, name, activeImage, null);
    }

    public Button(int leftX, int rightX, int topY, int botY, boolean active, String name, Image activeImage, Image inactiveImage){
        this.leftX = leftX;
        this.rightX = rightX;
        this.topY = topY;
        this.botY = botY;
        this.active = active;
        this.name = name;
        this.activeImage = activeImage;
        this.inactiveImage = inactiveImage;
    }

    public boolean isActive(){
        return this.active;
    }

    public void setActive(boolean state){
        this.active = state;
    }

    public boolean isHidden(){
        return this.hidden;
    }

    public void setHidden(boolean state) {
        this.hidden = state;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String value){
        this.name = value;
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

    public void setActiveImage(Image in) {
        this.activeImage =  in;
    }

    public void drawImage(Graphics g){
        if(hidden){
            return;
        }
        //Draw scaled to ensure it fits in region
        if(active && activeImage != null) {
            g.drawScaledImage(activeImage, leftX, topY, rightX - leftX, botY - topY);
        }
        else if(!active && inactiveImage != null) {
            g.drawScaledImage(inactiveImage, leftX, topY, rightX - leftX, botY - topY);
        }
    }

    public void drawString(Graphics g, Paint p){
        if(!hidden && active) {
            int X = leftX + ((rightX - leftX) / 2);
            int Y = botY - ((botY - topY - (int) p.getTextSize()) / 2);
            g.drawString(this.name, X, Y, p);
        }
    }

    //Draw the string, ignoring the active boolean
    public void forceDrawString(Graphics g, Paint p){
        int X = leftX + ((rightX - leftX) / 2);
        int Y = botY - ((botY - topY - (int) p.getTextSize()) / 2);
        g.drawString(this.name, X, Y, p);
    }

    public void drawString(Graphics g, Paint p, int offsetX, int offsetY){
        if(!hidden && active) {
            int X = leftX + ((rightX - leftX) / 2) + offsetX;
            int Y = botY - ((botY - topY - (int) p.getTextSize()) / 2) + offsetY;
            g.drawString(this.name, X, Y, p);
        }
    }
}
