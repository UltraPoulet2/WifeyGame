package ultrapoulet.androidgame.framework.helpers;

import java.util.List;

import ultrapoulet.androidgame.framework.Graphics;
import ultrapoulet.androidgame.framework.Image;

/**
 * Created by John on 11/25/2016.
 */

public class NumberPrinter {

    //For convenience, it is assumed that numbers can NOT be negative
    public enum Align{
        LEFT{ //Left means x will be the LEFT X position of the left most digit
            @Override
            protected void align(Graphics g, int num, int minSize, int x, int y, int width, int height, int spacing, List<Image> numbers){
                int numDigits = Integer.toString(num).length();
                int numZeroes = minSize - numDigits;
                int size = numZeroes <= 0 ? numDigits : minSize;
                int offset = width + spacing;
                for(int i = 0; i < size; i++){
                    int leftX = x + (offset * i);
                    if(i < numZeroes){
                        g.drawScaledImage(numbers.get(0), leftX, y, width, height);
                    }
                    else {
                        int divisor = (int) Math.pow(10, size - i - 1);
                        g.drawScaledImage(numbers.get((num / divisor) % 10), leftX, y, width, height);
                    }
                }
            }
        },
        CENTER{ //Center means x will be the X position of the center of the number
            @Override
            protected void align(Graphics g, int num, int minSize, int x, int y, int width, int height, int spacing, List<Image> numbers) {
                int numDigits = Integer.toString(num).length();
                int numZeroes = minSize - numDigits;
                int size = numZeroes <= 0 ? numDigits : minSize;
                int offset = width + spacing;
                int totalWidth = (width * numDigits) + (spacing * (numDigits - 1));
                int midpoint = totalWidth / 2;
                int startX = x - midpoint;
                for(int i = 0; i < size; i++){
                    int leftX = startX + (offset * i);
                    if(i < numZeroes){
                        g.drawScaledImage(numbers.get(0), leftX, y, width, height);
                    }
                    else {
                        int divisor = (int) Math.pow(10, size - i - 1);
                        g.drawScaledImage(numbers.get((num / divisor) % 10), leftX, y, width, height);
                    }
                }
            }
        },
        RIGHT{ //Right means x will be the RIGHT X position of the right most digit
            @Override
            protected void align(Graphics g, int num, int minSize, int x, int y, int width, int height, int spacing, List<Image> numbers) {
                int numDigits = Integer.toString(num).length();
                int numZeroes = minSize - numDigits;
                int size = numZeroes <= 0 ? numDigits : minSize;
                int offset = width + spacing;
                for(int i = 0; i < size; i++){
                    int leftX = x - width - (offset * i);
                    if(i >= numDigits){
                        g.drawScaledImage(numbers.get(0), leftX, y, width, height);
                    }
                    else {
                        int divisor = (int) Math.pow(10, i);
                        g.drawScaledImage(numbers.get((num / divisor) % 10), leftX, y, width, height);
                    }
                }
            }
        };
        protected abstract void align(Graphics g, int num, int minSize, int x, int y, int width, int height, int spacing, List<Image> numbers);
    }

    public static void drawNumber(Graphics g, int num, int x, int y, int width, int height, int spacing, List<Image> numbers, Align alignment){
        alignment.align(g, num, 0, x, y, width, height, spacing, numbers);
    }

    //Draws a number left padded with zeroes to at least minSize
    public static void drawNumberPadded(Graphics g, int num, int minSize, int x, int y, int width, int height, int spacing, List<Image> numbers, Align alignment){
        alignment.align(g, num, minSize, x, y, width, height, spacing, numbers);
    }
}
