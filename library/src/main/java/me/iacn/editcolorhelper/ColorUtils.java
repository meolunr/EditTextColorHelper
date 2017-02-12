package me.iacn.editcolorhelper;

import android.content.res.ColorStateList;

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

    public static ColorStateList getPureColorList(int color) {
        int[][] states = new int[1][];
        states[0] = new int[]{};

        int[] colors = new int[1];
        colors[0] = color;

        return new ColorStateList(states, colors);
    }
}