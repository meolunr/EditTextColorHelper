package me.iacn.editcolorhelper;

/**
 * Created by iAcn on 2017/2/12
 * Emali iAcn0301@foxmail.com
 */

public class ColorUtils {

    // Copy from Android v4 support package
    public static int setAlphaComponent(int color, int alpha) {
        if (alpha < 0 || alpha > 255) {
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        }

        return (color & 0x00ffffff) | (alpha << 24);
    }
}