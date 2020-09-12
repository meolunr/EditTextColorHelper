package com.meolunr.edittextcolorhelper;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by Meolunr on 2017/2/12
 * Email meolunr@gmail.com
 */
public class EditTextColorHelper {

    private static Field mEditor;
    private static Field mCursorDrawableRes;

    private static Field mSelectHandleLeft;
    private static Field mSelectHandleRight;
    private static Field mSelectHandleCenter;

    public static void setColor(EditText editText, int color) {
        // Update underline color
        setUnderlineColor(editText, color);

        // Update highlight color
        setHighlightColor(editText, color);

        try {
            getEditorFieldFromReflect();
            getCursorFieldFromReflect();
            getSelectFieldFromReflect();
            Object editor = mEditor.get(editText);

            // Update cursor color
            setCursorColor(editText, color, editor);

            // Update select handle color
            setSelectHandleColor(editText, color, editor);

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

    // Used for external calls
    public static void setCursorColor(EditText editText, int color) throws Exception {
        getEditorFieldFromReflect();

        Object editor = mEditor.get(editText);
        setCursorColor(editText, color, editor);
    }

    // Used for external calls
    public static void setSelectHandleColor(EditText editText, int color) throws Exception {
        getSelectFieldFromReflect();

        Object editor = mEditor.get(editText);
        setSelectHandleColor(editText, color, editor);
    }

    private static void setSelectHandleColor(EditText editText, int color, Object editor) throws Exception {
        Drawable leftDrawable = (Drawable) mSelectHandleLeft.get(editor);
        Drawable rightDrawable = (Drawable) mSelectHandleRight.get(editor);
        Drawable centerDrawable = (Drawable) mSelectHandleCenter.get(editor);

        updateSelectHandleColor(leftDrawable, "mTextSelectHandleLeftRes", editText, color);
        updateSelectHandleColor(rightDrawable, "mTextSelectHandleRightRes", editText, color);
        updateSelectHandleColor(centerDrawable, "mTextSelectHandleRes", editText, color);
    }

    private static void setCursorColor(EditText editText, int color, Object editor) throws Exception {
        int cursorId = mCursorDrawableRes.getInt(editText);
        Drawable drawable = editText.getContext().getDrawable(cursorId);

        if (drawable != null) {
            drawable.setTint(color);
        }

        ReflectUtils.setObjectField(mEditor.getType(), "mCursorDrawable",
                editor, new Drawable[]{drawable, drawable});
    }

    private static void updateSelectHandleColor(Drawable drawable, String fields, EditText editText, int color) {
        if (drawable == null) {
            int resource = ReflectUtils.getIntField(TextView.class, fields, editText);
            drawable = editText.getContext().getDrawable(resource);
        }

        if (drawable != null) {
            drawable.setTint(color);
        }
    }

    private static void getEditorFieldFromReflect() {
        if (mEditor == null) {
            mEditor = ReflectUtils.getDeclaredField(TextView.class, "mEditor");
        }
    }

    private static void getCursorFieldFromReflect() {
        if (mCursorDrawableRes == null) {
            mCursorDrawableRes = ReflectUtils.getDeclaredField(TextView.class, "mCursorDrawableRes");
        }
    }

    private static void getSelectFieldFromReflect() {
        if (mSelectHandleLeft == null || mSelectHandleRight == null || mSelectHandleCenter == null) {

            Class<?> EditorClass = mEditor.getType();

            mSelectHandleLeft = ReflectUtils.getDeclaredField(EditorClass, "mSelectHandleLeft");
            mSelectHandleRight = ReflectUtils.getDeclaredField(EditorClass, "mSelectHandleRight");
            mSelectHandleCenter = ReflectUtils.getDeclaredField(EditorClass, "mSelectHandleCenter");
        }
    }
}