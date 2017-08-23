package dpyinjie.statebutton;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;


/**
 * @author YinJie 2017/8/23 0023 下午 4:37.
 */
public class StateButton extends AppCompatButton {

    //text color
    private int mTextColorNormal = 0;
    private int mTextColorPressed = 0;
    private int mTextColorUnable = 0;
    private int mTextColorSelected = 0;
    private ColorStateList mTextColorStateList;

    //animation duration
    private int mAnimDuration = 0;

    //radius
    private float mCornersRadius = 0;
    private boolean mRound;

    //stroke
    private float mStrokeDashWidth = 0;
    private float mStrokeDashGap = 0;

    private int mStrokeWidthNormal = 0;
    private int mStrokeWidthPressed = 0;
    private int mStrokeWidthUnable = 0;
    private int mStrokeWidthSelected = 0;

    private int mStrokeColorNormal = 0;
    private int mStrokeColorPressed = 0;
    private int mStrokeColorUnable = 0;
    private int mStrokeColorSelected = 0;

    //background color
    private int mBackgroundColorNormal = 0;
    private int mBackgroundColorPressed = 0;
    private int mBackgroundColorUnable = 0;
    private int mBackgroundColorSelected = 0;

    private GradientDrawable mBackgroundDrawableNormal;
    private GradientDrawable mBackgroundDrawablePressed;
    private GradientDrawable mBackgroundDrawableUnable;
    private GradientDrawable mBackgroundDrawableSelected;

    private int[][] mStates;

    private StateListDrawable mStateBackgroundDrawable;

    public StateButton(Context context) {
        this(context, null);
    }

    public StateButton(Context context, AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.buttonStyle);
    }

    public StateButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWidget(context, attrs);
    }

    private void initWidget(Context context, AttributeSet attrs) {

        Drawable drawable = getBackground();
        if (drawable != null && drawable instanceof StateListDrawable) {
            mStateBackgroundDrawable = (StateListDrawable) drawable;
        } else {
            mStateBackgroundDrawable = new StateListDrawable();
        }

        mBackgroundDrawableNormal = new GradientDrawable();
        mBackgroundDrawablePressed = new GradientDrawable();
        mBackgroundDrawableUnable = new GradientDrawable();
        mBackgroundDrawableSelected = new GradientDrawable();

        mStates = new int[5][];
        mStates[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};//pressed
        mStates[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};//focused
        mStates[2] = new int[]{android.R.attr.state_enabled, android.R.attr.state_selected};//selected
        mStates[3] = new int[]{-android.R.attr.state_enabled};//unable
        mStates[4] = new int[]{android.R.attr.state_enabled};//enable/normal

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StateButton);

        //set text color
        mTextColorStateList = getTextColors();
        int defPressedTextColor = mTextColorStateList.getColorForState(mStates[0], getCurrentTextColor());
        int defNormalTextColor = mTextColorStateList.getColorForState(mStates[4], getCurrentTextColor());
        int defUnableTextColor = mTextColorStateList.getColorForState(mStates[3], getCurrentTextColor());
        int defSelectedTextColor = mTextColorStateList.getColorForState(mStates[2], getCurrentTextColor());
        mTextColorNormal = a.getColor(R.styleable.StateButton_textColorNormal, defNormalTextColor);
        mTextColorPressed = a.getColor(R.styleable.StateButton_textColorPressed, defPressedTextColor);
        mTextColorUnable = a.getColor(R.styleable.StateButton_textColorUnable, defUnableTextColor);
        mTextColorSelected = a.getColor(R.styleable.StateButton_textColorSelected, defSelectedTextColor);
        setTextColor();

        //set animation duration
        mAnimDuration = a.getInteger(R.styleable.StateButton_animationDuration, mAnimDuration);
        mStateBackgroundDrawable.setEnterFadeDuration(mAnimDuration);
        mStateBackgroundDrawable.setExitFadeDuration(mAnimDuration);

        //set background color
        mBackgroundColorNormal = a.getColor(R.styleable.StateButton_backgroundColorNormal, 0);
        mBackgroundColorPressed = a.getColor(R.styleable.StateButton_backgroundColorPressed, 0);
        mBackgroundColorUnable = a.getColor(R.styleable.StateButton_backgroundColorUnable, 0);
        mBackgroundColorSelected = a.getColor(R.styleable.StateButton_backgroundColorSelected, 0);
        mBackgroundDrawableNormal.setColor(mBackgroundColorNormal);
        mBackgroundDrawablePressed.setColor(mBackgroundColorPressed);
        mBackgroundDrawableUnable.setColor(mBackgroundColorUnable);
        mBackgroundDrawableSelected.setColor(mBackgroundColorSelected);

        //圆角
        mRound = a.getBoolean(R.styleable.StateButton_round, false);
        if (!mRound) {
            mCornersRadius = a.getDimensionPixelSize(R.styleable.StateButton_cornersRadius, 0);
            if (mCornersRadius > 0) {
                mBackgroundDrawableNormal.setCornerRadius(mCornersRadius);
                mBackgroundDrawablePressed.setCornerRadius(mCornersRadius);
                mBackgroundDrawableUnable.setCornerRadius(mCornersRadius);
                mBackgroundDrawableSelected.setCornerRadius(mCornersRadius);
            } else {
                float cornersRadiusTopLeft = a.getDimension(R.styleable.StateButton_cornersRadiusTopLeft, 0);
                float cornersRadiusTopRight = a.getDimension(R.styleable.StateButton_cornersRadiusTopRight, 0);
                float cornersRadiusBottomLeft = a.getDimension(R.styleable.StateButton_cornersRadiusBottomLeft, 0);
                float cornersRadiusBottomRight = a.getDimension(R.styleable.StateButton_cornersRadiusBottomRight, 0);
                mBackgroundDrawableNormal.setCornerRadii(new float[]{
                        cornersRadiusTopLeft, cornersRadiusTopLeft,
                        cornersRadiusTopRight, cornersRadiusTopRight,
                        cornersRadiusBottomLeft, cornersRadiusBottomLeft,
                        cornersRadiusBottomRight, cornersRadiusBottomRight});
                mBackgroundDrawablePressed.setCornerRadii(new float[]{
                        cornersRadiusTopLeft, cornersRadiusTopLeft,
                        cornersRadiusTopRight, cornersRadiusTopRight,
                        cornersRadiusBottomLeft, cornersRadiusBottomLeft,
                        cornersRadiusBottomRight, cornersRadiusBottomRight});
                mBackgroundDrawableUnable.setCornerRadii(new float[]{
                        cornersRadiusTopLeft, cornersRadiusTopLeft,
                        cornersRadiusTopRight, cornersRadiusTopRight,
                        cornersRadiusBottomLeft, cornersRadiusBottomLeft,
                        cornersRadiusBottomRight, cornersRadiusBottomRight});
                mBackgroundDrawableSelected.setCornerRadii(new float[]{
                        cornersRadiusTopLeft, cornersRadiusTopLeft,
                        cornersRadiusTopRight, cornersRadiusTopRight,
                        cornersRadiusBottomLeft, cornersRadiusBottomLeft,
                        cornersRadiusBottomRight, cornersRadiusBottomRight});
            }
        }

        //set stroke
        mStrokeDashWidth = a.getDimensionPixelSize(R.styleable.StateButton_strokeDashWidth, 0);
        mStrokeDashGap = a.getDimensionPixelSize(R.styleable.StateButton_strokeDashWidth, 0);
        mStrokeWidthNormal = a.getDimensionPixelSize(R.styleable.StateButton_strokeWidthNormal, 0);
        mStrokeWidthPressed = a.getDimensionPixelSize(R.styleable.StateButton_strokeWidthPressed, 0);
        mStrokeWidthUnable = a.getDimensionPixelSize(R.styleable.StateButton_strokeWidthUnable, 0);
        mStrokeWidthSelected = a.getDimensionPixelSize(R.styleable.StateButton_strokeWidthSelected, 0);
        mStrokeColorNormal = a.getColor(R.styleable.StateButton_strokeColorNormal, 0);
        mStrokeColorPressed = a.getColor(R.styleable.StateButton_strokeColorPressed, 0);
        mStrokeColorUnable = a.getColor(R.styleable.StateButton_strokeColorUnable, 0);
        mStrokeColorSelected = a.getColor(R.styleable.StateButton_strokeColorSelected, 0);
        setStroke();

        //set background
        mStateBackgroundDrawable.addState(mStates[0], mBackgroundDrawablePressed);
        mStateBackgroundDrawable.addState(mStates[1], mBackgroundDrawablePressed);
        mStateBackgroundDrawable.addState(mStates[2], mBackgroundDrawableSelected);
        mStateBackgroundDrawable.addState(mStates[3], mBackgroundDrawableUnable);
        mStateBackgroundDrawable.addState(mStates[4], mBackgroundDrawableNormal);
        setBackgroundDrawable(mStateBackgroundDrawable);
        a.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setRound(mRound);
    }

    public void setStrokeColorNormal(@ColorInt int strokeColorNormal) {
        this.mStrokeColorNormal = strokeColorNormal;
        setStroke(mBackgroundDrawableNormal, mStrokeColorNormal, mStrokeWidthNormal);
    }

    public void setStrokeColorPressed(@ColorInt int strokeColorPressed) {
        this.mStrokeColorPressed = strokeColorPressed;
        setStroke(mBackgroundDrawablePressed, mStrokeColorPressed, mStrokeWidthPressed);
    }

    public void setStrokeColorUnable(@ColorInt int strokeColorUnable) {
        this.mStrokeColorUnable = strokeColorUnable;
        setStroke(mBackgroundDrawableUnable, mStrokeColorUnable, mStrokeWidthUnable);
    }

    public void setStateStrokeColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable, @ColorInt int selected) {
        mStrokeColorNormal = normal;
        mStrokeColorPressed = pressed;
        mStrokeColorUnable = unable;
        mStrokeColorSelected = selected;
        setStroke();
    }

    public void setStrokeWidthNormal(int strokeWidthNormal) {
        this.mStrokeWidthNormal = strokeWidthNormal;
        setStroke(mBackgroundDrawableNormal, mStrokeColorNormal, mStrokeWidthNormal);
    }

    public void setStrokeWidthPressed(int strokeWidthPressed) {
        this.mStrokeWidthPressed = strokeWidthPressed;
        setStroke(mBackgroundDrawablePressed, mStrokeColorPressed, mStrokeWidthPressed);
    }

    public void setStrokeWidthUnable(int strokeWidthUnable) {
        this.mStrokeWidthUnable = strokeWidthUnable;
        setStroke(mBackgroundDrawableUnable, mStrokeColorUnable, mStrokeWidthUnable);
    }

    public void setStateStrokeWidth(int normal, int pressed, int unable) {
        mStrokeWidthNormal = normal;
        mStrokeWidthPressed = pressed;
        mStrokeWidthUnable = unable;
        setStroke();
    }

    public void setStrokeDash(float strokeDashWidth, float strokeDashGap) {
        this.mStrokeDashWidth = strokeDashWidth;
        this.mStrokeDashGap = strokeDashWidth;
        setStroke();
    }

    private void setStroke() {
        setStroke(mBackgroundDrawableNormal, mStrokeColorNormal, mStrokeWidthNormal);
        setStroke(mBackgroundDrawablePressed, mStrokeColorPressed, mStrokeWidthPressed);
        setStroke(mBackgroundDrawableUnable, mStrokeColorUnable, mStrokeWidthUnable);
        setStroke(mBackgroundDrawableSelected, mStrokeColorSelected, mStrokeWidthSelected);
    }

    private void setStroke(GradientDrawable backgroundDrawable, int strokeColor, int strokeWidth) {
        backgroundDrawable.setStroke(strokeWidth, strokeColor, mStrokeDashWidth, mStrokeDashGap);
    }

    public void setCornersRadius(@FloatRange(from = 0) float cornersRadius) {
        this.mCornersRadius = cornersRadius;
        mBackgroundDrawableNormal.setCornerRadius(mCornersRadius);
        mBackgroundDrawablePressed.setCornerRadius(mCornersRadius);
        mBackgroundDrawableUnable.setCornerRadius(mCornersRadius);
    }

    public void setRound(boolean round) {
        this.mRound = round;
        int height = getMeasuredHeight();
        if (mRound) {
            setCornersRadius(height / 2f);
        }
    }

    public void setRadius(float[] radii) {
        mBackgroundDrawableNormal.setCornerRadii(radii);
        mBackgroundDrawablePressed.setCornerRadii(radii);
        mBackgroundDrawableUnable.setCornerRadii(radii);
        mBackgroundDrawableSelected.setCornerRadii(radii);
    }

    public void setStateBackgroundColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable, @ColorInt int selected) {
        mBackgroundColorNormal = normal;
        mBackgroundColorPressed = pressed;
        mBackgroundColorUnable = unable;
        mBackgroundColorSelected = selected;
        mBackgroundDrawableNormal.setColor(mBackgroundColorNormal);
        mBackgroundDrawablePressed.setColor(mBackgroundColorPressed);
        mBackgroundDrawableUnable.setColor(mBackgroundColorUnable);
        mBackgroundDrawableSelected.setColor(mBackgroundColorSelected);
    }

    public void setBackgroundColorNormal(@ColorInt int backgroundColorNormal) {
        this.mBackgroundColorNormal = backgroundColorNormal;
        mBackgroundDrawableNormal.setColor(mBackgroundColorNormal);
    }

    public void setBackgroundColorPressed(@ColorInt int backgroundColorPressed) {
        this.mBackgroundColorPressed = backgroundColorPressed;
        mBackgroundDrawablePressed.setColor(mBackgroundColorPressed);
    }

    public void setBackgroundColorUnable(@ColorInt int backgroundColorUnable) {
        this.mBackgroundColorUnable = backgroundColorUnable;
        mBackgroundDrawableUnable.setColor(mBackgroundColorUnable);
    }

    public void setAnimationDuration(@IntRange(from = 0) int duration) {
        this.mAnimDuration = duration;
        mStateBackgroundDrawable.setEnterFadeDuration(mAnimDuration);
    }

    private void setTextColor() {
        int[] colors = new int[]{mTextColorPressed, mTextColorPressed, mTextColorSelected, mTextColorUnable, mTextColorNormal};
        mTextColorStateList = new ColorStateList(mStates, colors);
        setTextColor(mTextColorStateList);
    }

    public void setStateTextColor(@ColorInt int normal, @ColorInt int pressed, @ColorInt int unable, @ColorInt int selected) {
        this.mTextColorNormal = normal;
        this.mTextColorPressed = pressed;
        this.mTextColorUnable = unable;
        this.mTextColorSelected = selected;
        setTextColor();
    }

    public void setTextColorNormal(@ColorInt int textColorNormal) {
        this.mTextColorNormal = textColorNormal;
        setTextColor();
    }

    public void setTextColorPressed(@ColorInt int textColorPressed) {
        this.mTextColorPressed = textColorPressed;
        setTextColor();
    }

    public void setTextColorUnable(@ColorInt int textColorUnable) {
        this.mTextColorUnable = textColorUnable;
        setTextColor();
    }
}
