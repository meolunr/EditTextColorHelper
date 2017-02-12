package me.iacn.editcolorhelper;

import android.content.res.ColorStateList;
import android.widget.EditText;

import java.lang.reflect.Field;

/**
 * Created by iAcn on 2017/2/12
 * Emali iAcn0301@foxmail.com
 */

public class EditColorHelper {

    private static Field mEditor;
    private static Field mCursorDrawableRes;

    public static void setColor(EditText editText, int color) {
        // Update underline color
        setUnderlineColor(editText, color);

        // Update Highlight Color
        setHighlightColor(editText, color);

        try {
            // Update Cursor Color
            setCursorColor();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setUnderlineColor(EditText editText, int color) {
        ColorStateList colorStateList = ColorUtils.getPureColorList(color);
        editText.setBackgroundTintList(colorStateList);
    }

    public static void setHighlightColor(EditText editText, int color) {
        // The value 102 is Android original EditText highlight transparency
        int translateColor = ColorUtils.setAlphaComponent(color, 102);
        editText.setHighlightColor(translateColor);
    }

    public static void setCursorColor(EditText editText, Object editor, int color) throws Exception {

    }

    private static void getEditorFieldFromReflect() {

    }
}