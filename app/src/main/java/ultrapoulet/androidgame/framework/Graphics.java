package ultrapoulet.androidgame.framework;

/**
 * Created by John on 1/8/2016.
 */

import android.graphics.Paint;
import android.text.TextPaint;

public interface Graphics {
    enum ImageFormat {
        ARGB8888, ARGB4444, RGB565
    }

    Image newImage(String fileName, ImageFormat format);

    void clearScreen(int color);

    void drawLine(int x, int y, int x2, int y2, int color);

    void drawRect(int x, int y, int width, int height, int color);

    void drawImage(Image image, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight);

    void drawImage(Image image, int x, int y);

    void drawImageAlpha(Image image, int x, int y, int alpha);

    void drawScaledImage(Image image, int posX, int posY, int dispWidth, int dispHeight, int srcX,
                         int srcY, int srcWidth, int srcHeight);

    void drawScaledImage(Image image, int posX, int posY, int dispWidth, int dispHeight);

    void drawPercentageImage(Image image, int posX, int posY, int percX, int percY);

    void drawString(String text, int x, int y, Paint paint);

    //Draw a string, modifying the font size of the Paint to make the text fit within the width
    void drawString(String text, int x, int y, Paint paint, int maxWidth, int maxFont);

    //Return true if the string can fit within the width at the specified font
    boolean canDrawString(String text, Paint paint, int maxWidth, int fontSize);

    void drawMultiLineString(String text, int x, int y, int width, TextPaint paint);

    int getWidth();

    int getHeight();

    void drawARGB(int i, int j, int k, int l);

}
