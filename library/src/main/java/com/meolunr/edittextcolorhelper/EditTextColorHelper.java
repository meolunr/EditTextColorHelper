package com.meolunr.edittextcolorhelper;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by Meolunr on 2017/2/12
 * Email meolunr@gmail.com
 */
public class EditTextColorHelper {

    private static WeakReference<Field> mEditor;
    private static WeakReference<Field> mCursorDrawableRes;

    private static WeakReference<Field> mSelectHandleLeft;
    private static WeakReference<Field> mSelectHandleRight;
    private static WeakReference<Field> mSelectHandleCenter;

    public static void setColor(EditText editText, int color) {
        // Update underline color
        setUnderlineColor(editText, color);

        // Update highlight color
        setHighlightColor(editText, color);

        try {
            obtainEditorField();
            obtainCursorField();
            obtainSelectField();
            Object editor = mEditor.get().get(editText);

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
        obtainEditorField();

        Object editor = mEditor.get().get(editText);
        setCursorColor(editText, color, editor);
    }

    // Used for external calls
    public static void setSelectHandleColor(EditText editText, int color) throws Exception {
        obtainSelectField();

        Object editor = mEditor.get().get(editText);
        setSelectHandleColor(editText, color, editor);
    }

    private static void setSelectHandleColor(EditText editText, int color, Object editor) throws Exception {
        Drawable leftDrawable = (Drawable) mSelectHandleLeft.get().get(editor);
        Drawable rightDrawable = (Drawable) mSelectHandleRight.get().get(editor);
        Drawable centerDrawable = (Drawable) mSelectHandleCenter.get().get(editor);

        updateSelectHandleColor(leftDrawable, "mTextSelectHandleLeftRes", editText, color);
        updateSelectHandleColor(rightDrawable, "mTextSelectHandleRightRes", editText, color);
        updateSelectHandleColor(centerDrawable, "mTextSelectHandleRes", editText, color);
    }

    private static void setCursorColor(EditText editText, int color, Object editor) throws Exception {
        int cursorId = mCursorDrawableRes.get().getInt(editText);
        Drawable drawable = editText.getContext().getDrawable(cursorId);

        if (drawable != null) {
            drawable.setTint(color);
        }

        ReflectUtils.setObjectField(mEditor.get().getType(), "mCursorDrawable",
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

    private static void obtainEditorField() {
        if (mEditor == null || mEditor.get() == null) {
            mEditor = new WeakReference<>(ReflectUtils.getDeclaredField(TextView.class, "mEditor"));
        }
    }

    private static void obtainCursorField() {
        if (mCursorDrawableRes == null || mCursorDrawableRes.get() == null) {
            mCursorDrawableRes = new WeakReference<>(ReflectUtils.getDeclaredField(TextView.class, "mCursorDrawableRes"));
        }
    }

    private static void obtainSelectField() {
        if (mSelectHandleLeft == null || mSelectHandleLeft.get() == null ||
                mSelectHandleRight == null || mSelectHandleRight.get() == null ||
                mSelectHandleCenter == null || mSelectHandleCenter.get() == null) {

            Class<?> editorClass = mEditor.get().getType();

            mSelectHandleLeft = new WeakReference<>(ReflectUtils.getDeclaredField(editorClass, "mSelectHandleLeft"));
            mSelectHandleRight = new WeakReference<>(ReflectUtils.getDeclaredField(editorClass, "mSelectHandleRight"));
            mSelectHandleCenter = new WeakReference<>(ReflectUtils.getDeclaredField(editorClass, "mSelectHandleCenter"));
        }
    }
}