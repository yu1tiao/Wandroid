package com.pretty.core.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.pretty.core.R;

/**
 * 带有显示密码按钮的输入框
 */
public class PasswordEditText extends TextInputEditText {

    /**
     * This area is added as padding to increase the clickable area of the icon
     */
    private final static int EXTRA_TAPPABLE_AREA = 50;

    @DrawableRes
    private int showPwIcon = R.mipmap.ic_show_pwd;

    @DrawableRes
    private int hidePwIcon = R.mipmap.ic_hide_pwd;

    //Visibility enabled: Icon opacity at 54%, with the password visible
    private final static int ALPHA_ICON_ENABLED = (int) (255 * 0.54f);

    //Visibility disabled: Icon opacity at 38%, with the password represented by midline ellipses
    private final static int ALPHA_ICON_DISABLED = (int) (255 * 0.38f);

    private Drawable showPwDrawable;

    private Drawable hidePwDrawable;

    private boolean passwordVisible;

    private boolean showingIcon;

    private boolean setErrorCalled;

    private boolean hoverShowsPw;

    private boolean useNonMonospaceFont;

    private boolean disableIconAlpha;

    private boolean handlingHoverEvent;

    public PasswordEditText(Context context) {
        this(context, null);
    }

    public PasswordEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFields(attrs, 0);
    }

    public PasswordEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFields(attrs, defStyleAttr);
    }

    public void initFields(AttributeSet attrs, int defStyleAttr) {

        if (attrs != null) {
            TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.PasswordEditText, defStyleAttr, 0);
            try {
                showPwIcon = styledAttributes.getResourceId(R.styleable.PasswordEditText_pet_iconShow, showPwIcon);
                hidePwIcon = styledAttributes.getResourceId(R.styleable.PasswordEditText_pet_iconHide, hidePwIcon);
                hoverShowsPw = styledAttributes.getBoolean(R.styleable.PasswordEditText_pet_hoverShowsPw, false);
                useNonMonospaceFont = styledAttributes.getBoolean(R.styleable.PasswordEditText_pet_nonMonospaceFont, false);
                disableIconAlpha = styledAttributes.getBoolean(R.styleable.PasswordEditText_pet_disableIconAlpha, false);
            } finally {
                styledAttributes.recycle();
            }
        }

        // As the state (like alpha) should not be shared, mutate to make sure it is not reused
        hidePwDrawable = ContextCompat.getDrawable(getContext(), hidePwIcon).mutate();
        showPwDrawable = ContextCompat.getDrawable(getContext(), showPwIcon).mutate();

        if (!disableIconAlpha) {
            hidePwDrawable.setAlpha(ALPHA_ICON_ENABLED);
            showPwDrawable.setAlpha(ALPHA_ICON_DISABLED);
        }

        if (useNonMonospaceFont) {
            setTypeface(Typeface.DEFAULT);
        }

        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //NOOP
            }

            @Override
            public void onTextChanged(CharSequence seq, int start, int before, int count) {
                //NOOP
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    if (setErrorCalled) {
                        // resets drawables after setError was called as this leads to icons
                        // not changing anymore afterwards
                        setCompoundDrawables(null, null, null, null);
                        setErrorCalled = false;
                        showPasswordVisibilityIndicator(true);
                    }
                    if (!showingIcon) {
                        showPasswordVisibilityIndicator(true);
                    }
                } else {
                    // hides the indicator if no text inside text field
                    passwordVisible = false;
                    handlePasswordInputVisibility();
                    showPasswordVisibilityIndicator(false);
                }

            }
        });
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, showingIcon, passwordVisible);
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        showingIcon = savedState.isShowingIcon();
        passwordVisible = savedState.isPasswordVisible();
        handlePasswordInputVisibility();
        showPasswordVisibilityIndicator(showingIcon);
    }

    @Override
    public void setError(CharSequence error) {
        super.setError(error);
        setErrorCalled = true;

    }

    @Override
    public void setError(CharSequence error, Drawable icon) {
        super.setError(error, icon);
        setErrorCalled = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!showingIcon) {
            return super.onTouchEvent(event);
        } else {
            Drawable rightDrawable = getCompoundDrawables()[2];
            if (rightDrawable != null) {
                final Rect bounds = rightDrawable.getBounds();
                final int x = (int) event.getRawX();
                int iconXRect = getRight() - bounds.width() - EXTRA_TAPPABLE_AREA;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (hoverShowsPw) {
                            if (x >= iconXRect) {
                                togglePasswordIconVisibility();
                                // prevent keyboard from coming up
                                event.setAction(MotionEvent.ACTION_CANCEL);
                                handlingHoverEvent = true;
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        if (handlingHoverEvent || (x >= iconXRect)) {
                            togglePasswordIconVisibility();
                            // prevent keyboard from coming up
                            event.setAction(MotionEvent.ACTION_CANCEL);
                            handlingHoverEvent = false;
                        }
                        break;
                }
            }
            return super.onTouchEvent(event);
        }
    }


    private void showPasswordVisibilityIndicator(boolean shouldShowIcon) {
        if (shouldShowIcon) {
            Drawable drawable = passwordVisible ? hidePwDrawable : showPwDrawable;
            showingIcon = true;
            setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        } else {
            // reset drawable
            setCompoundDrawables(null, null, null, null);
            showingIcon = false;
        }
    }

    /**
     * This method toggles the visibility of the icon and takes care of switching the input type
     * of the view to be able to see the password afterwards.
     * <p>
     * This method may only be called if there is an icon visible
     */
    private void togglePasswordIconVisibility() {
        passwordVisible = !passwordVisible;
        handlePasswordInputVisibility();
        showPasswordVisibilityIndicator(true);
    }

    /**
     * This method is called when restoring the state (e.g. on orientation change)
     */
    private void handlePasswordInputVisibility() {
        int selectionStart = getSelectionStart();
        int selectionEnd = getSelectionEnd();
        if (passwordVisible) {
            setTransformationMethod(null);
        } else {
            setTransformationMethod(PasswordTransformationMethod.getInstance());

        }
        setSelection(selectionStart, selectionEnd);

    }

    /**
     * Convenience class to save / restore the state of icon.
     */
    protected static class SavedState extends View.BaseSavedState {

        private final boolean mShowingIcon;
        private final boolean mPasswordVisible;

        private SavedState(Parcelable superState, boolean sI, boolean pV) {
            super(superState);
            mShowingIcon = sI;
            mPasswordVisible = pV;
        }

        private SavedState(Parcel in) {
            super(in);
            mShowingIcon = in.readByte() != 0;
            mPasswordVisible = in.readByte() != 0;
        }

        public boolean isShowingIcon() {
            return mShowingIcon;
        }

        public boolean isPasswordVisible() {
            return mPasswordVisible;
        }

        @Override
        public void writeToParcel(Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeByte((byte) (mShowingIcon ? 1 : 0));
            destination.writeByte((byte) (mPasswordVisible ? 1 : 0));
        }

        public static final Parcelable.Creator<SavedState> CREATOR = new Creator<SavedState>() {

            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }

        };
    }
}
