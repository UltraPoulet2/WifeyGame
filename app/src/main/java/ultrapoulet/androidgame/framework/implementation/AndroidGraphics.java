package ultrapoulet.androidgame.framework.implementation;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.IOException;
import java.io.InputStream;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 1/8/2016.
 */
public class AndroidGraphics implements Graphics {
    AssetManager assets;
    Bitmap frameBuffer;
    Canvas canvas;
    Paint paint;
    Rect srcRect = new Rect();
    Rect dstRect = new Rect();

    public AndroidGraphics(AssetManager assets, Bitmap frameBuffer) {
        this.assets = assets;
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
        this.paint = new Paint();
    }

    @Override
    public Image newImage(String fileName, ImageFormat format) {
        Config config = null;
        if (format == ImageFormat.RGB565)
            config = Config.RGB_565;
        else if (format == ImageFormat.ARGB4444)
            config = Config.ARGB_4444;
        else
            config = Config.ARGB_8888;

        Options options = new Options();
        options.inPreferredConfig = config;

        InputStream in = null;
        Bitmap bitmap = null;
        try {
            in = assets.open(fileName);
            bitmap = BitmapFactory.decodeStream(in, null, options);
            if (bitmap == null)
                throw new RuntimeException("Couldn't load bitmap from asset '"
                        + fileName + "'");
        }
        catch (IOException e) {
            throw new RuntimeException("Couldn't load bitmap from asset '"
                    + fileName + "'");
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                }
            }

        }

        if (bitmap.getConfig() == Config.RGB_565)
            format = ImageFormat.RGB565;
        else if (bitmap.getConfig() == Config.ARGB_4444)
            format = ImageFormat.ARGB4444;
        else
            format = ImageFormat.ARGB8888;

        return new AndroidImage(bitmap, format);
    }

    @Override
    public void clearScreen(int color) {
        canvas.drawRGB((color & 0xff0000) >> 16, (color & 0xff00) >> 8,
                (color & 0xff));
    }

    @Override
    public void drawLine(int x, int y, int x2, int y2, int color){
        paint.setColor(color);
        canvas.drawLine(x, y, x2, y2, paint);
    }

    @Override
    public void drawRect(int x, int y, int width, int height, int color) {
        paint.setColor(color);
        paint.setStyle(Style.FILL);
        canvas.drawRect(x, y, x + width - 1, y + height - 1, paint);
    }

    @Override
    public void drawARGB(int a, int r, int g, int b){
        paint.setStyle(Style.FILL);
        canvas.drawARGB(a, r, g, b);
    }

    @Override
    public void drawString(String text, int x, int y, Paint paint){
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void drawString(String text, int x, int y, Paint paint, int maxWidth, int maxFont){
        int fontSize = maxFont;
        paint.setTextSize(fontSize);
        while(paint.measureText(text) > maxWidth){
            fontSize--;
            paint.setTextSize(fontSize);
        }
        int newY = y - ((maxFont - fontSize) / 2);
        drawString(text, x, newY, paint);
    }


    @Override
    public boolean canDrawString(String text, Paint paint, int maxWidth, int fontSize){
        Paint copyPaint = new Paint(paint);
        copyPaint.setTextSize(fontSize);
        return copyPaint.measureText(text) <= maxWidth;
    }

    @Override
    public void drawMultiLineString(String text, int x, int y, int width, TextPaint paint){
        /* Do not question this. For some reason, the first time a Screen calls this function, the
         * text written out would be different than any other frame. This was causing a 'flicker'.
         * By drawing an empty string, the flicker is removed. Somehow.
         */
        StaticLayout blank = new StaticLayout("", paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(x,y);
        blank.draw(canvas);
        canvas.restore();

        StaticLayout textLayout = new StaticLayout(
                text, paint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        canvas.save();
        canvas.translate(x,y);
        textLayout.draw(canvas);
        canvas.restore();
    }

    @Override
    public void drawImage(Image image, int x, int y, int srcX, int srcY,
                          int srcWidth, int srcHeight){
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = x;
        dstRect.top = y;
        dstRect.right = x + srcWidth;
        dstRect.bottom = y + srcHeight;

        canvas.drawBitmap(((AndroidImage) image).bitmap, srcRect, dstRect,
                null);
    }

    @Override
    public void drawImage(Image image, int x, int y){
        canvas.drawBitmap(((AndroidImage) image).bitmap, x, y, null);
    }

    @Override
    public void drawImageAlpha(Image image, int x, int y, int alpha) {
        Paint paint = new Paint();
        paint.setAlpha(alpha);
        canvas.drawBitmap(((AndroidImage) image).bitmap, x, y, paint);
    }

    @Override
    public void drawScaledImage(Image image, int posX, int posY, int dispWidth, int dispHeight, int srcX,
                                int srcY, int srcWidth, int srcHeight){
        srcRect.left = srcX;
        srcRect.top = srcY;
        srcRect.right = srcX + srcWidth;
        srcRect.bottom = srcY + srcHeight;

        dstRect.left = posX;
        dstRect.top = posY;
        dstRect.right = posX + dispWidth;
        dstRect.bottom = posY + dispHeight;

        canvas.drawBitmap(((AndroidImage) image).bitmap, srcRect, dstRect, null);
    }

    @Override
    public void drawScaledImage(Image image, int posX, int posY, int dispWidth, int dispHeight){
        drawScaledImage(image, posX, posY, dispWidth, dispHeight, 0, 0, image.getWidth(), image.getHeight());
    }

    public void drawPercentageImage(Image image, int posX, int posY, int percX, int percY){
        int origWidth = image.getWidth();
        int origHeight = image.getHeight();
        int newWidth = image.getWidth()*percX/100;
        int newHeight = image.getHeight()*percY/100;

        drawScaledImage(image, posX, posY, newWidth, newHeight, 0, 0, origWidth, origHeight);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }

    @Override
    public int getHeight() {
        return frameBuffer.getHeight();
    }
}
