package me.iacn.editcolorhelper;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Created by iAcn on 2017/2/12
 * Emali iAcn0301@foxmail.com
 */

public class EditColorHelper {

    private static Field mEditor;
    private static Field mCursorDrawableRes;

    private static Field mSelectHandleLeft;
    private static Field mSelectHandleRight;
    private static Field mSelectHandleCenter;

    public static void setColor(EditText editText, int color) {
        // Update underline color
        setUnderlineColor(editText, color);

        // Update Highlight Color
        setHighlightColor(editText, color);

        try {
            getEditorFieldFromReflect();
            getSelectFieldFromReflect();
            Object editor = mEditor.get(editText);

            // Update Cursor Color
            setCursorColor(editText, color, editor);

            // Update Select Handle Color
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

        Drawable[] drawables = new Drawable[]{leftDrawable, rightDrawable, centerDrawable};
        String[] fields = new String[]{"mTextSelectHandleLeftRes", "mTextSelectHandleRightRes", "mTextSelectHandleRes"};

        updateSelectHandleColor(drawables, fields, editText, color);
    }

    private static void setCursorColor(EditText editText, int color, Object editor) throws Exception {
        getCursorFieldFromReflect();

        int cursorId = mCursorDrawableRes.getInt(editText);
        Drawable drawable = editText.getContext().getDrawable(cursorId);

        if (drawable != null) {
            drawable.setTint(color);
        }

        ReflectUtils.setObjectField(mEditor.getType(), "mCursorDrawable",
                editor, new Drawable[]{drawable, drawable});
    }

    private static void updateSelectHandleColor(Drawable[] drawables, String[] fields,
                                                EditText editText, int color) throws Exception {

        for (int i = 0; i < fields.length; i++) {
            Drawable drawable = drawables[i];

            if (drawable == null) {
                Field field = TextView.class.getDeclaredField(fields[i]);
                field.setAccessible(true);
                int res = (int) field.get(editText);

                drawable = editText.getContext().getDrawable(res);
            }

            if (drawable != null) {
                drawable.setTint(color);
            }
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