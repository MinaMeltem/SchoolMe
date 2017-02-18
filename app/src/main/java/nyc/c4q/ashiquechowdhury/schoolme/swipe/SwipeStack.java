package nyc.c4q.ashiquechowdhury.schoolme.swipe;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;
import java.util.Random;

import nyc.c4q.ashiquechowdhury.schoolme.R;

public class SwipeStack extends ViewGroup {

    public static final int SWIPE_DIRECTION_BOTH = 0;
    public static final int SWIPE_DIRECTION_ONLY_LEFT = 1;
    public static final int SWIPE_DIRECTION_ONLY_RIGHT = 2;

    public static final int DEFAULT_ANIMATION_DURATION = 300;
    public static final int DEFAULT_STACK_SIZE = 3;
    public static final float DEFAULT_SWIPE_OPACITY = 1f;
    public static final float DEFAULT_SCALE_FACTOR = 1f;
    public static final boolean DEFAULT_DISABLE_HW_ACCELERATION = true;

    private static final String KEY_SUPER_STATE = "superState";
    private static final String KEY_CURRENT_INDEX = "currentIndex";

    private Adapter adapter;

    private int allowedSwipeDirections;
    private int animationDuration;
    private int currentViewIndex;
    private int numberOfStackedViews;
    private int viewSpacing;
    private float swipeOpacity;
    private float scaleFactor;
    private boolean disableHwAcceleration;
    private boolean isFirstLayout = true;

    private View topView;
    private SwipeHelper swipeHelper;
    private DataSetObserver dataSetObserver;
    private SwipeStackListener swipeStackListener;
    private SwipeProgressListener progressListener;

    public SwipeStack(Context context) {
        this(context, null);
    }

    public SwipeStack(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeStack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttributes(attrs);
        initialize();
    }

    private void readAttributes(AttributeSet attributeSet) {
        TypedArray attrs = getContext().obtainStyledAttributes(attributeSet, R.styleable.SwipeStack);

        try {
            allowedSwipeDirections =
                    attrs.getInt(R.styleable.SwipeStack_allowed_swipe_directions,
                            SWIPE_DIRECTION_BOTH);
            animationDuration =
                    attrs.getInt(R.styleable.SwipeStack_animation_duration,
                            DEFAULT_ANIMATION_DURATION);
            numberOfStackedViews =
                    attrs.getInt(R.styleable.SwipeStack_stack_size, DEFAULT_STACK_SIZE);
            viewSpacing =
                    attrs.getDimensionPixelSize(R.styleable.SwipeStack_stack_spacing,
                            getResources().getDimensionPixelSize(R.dimen.default_stack_spacing));
            swipeOpacity =
                    attrs.getFloat(R.styleable.SwipeStack_swipe_opacity, DEFAULT_SWIPE_OPACITY);
            scaleFactor =
                    attrs.getFloat(R.styleable.SwipeStack_scale_factor, DEFAULT_SCALE_FACTOR);
            disableHwAcceleration =
                    attrs.getBoolean(R.styleable.SwipeStack_disable_hw_acceleration,
                            DEFAULT_DISABLE_HW_ACCELERATION);
        } finally {
            attrs.recycle();
        }
    }

    private void initialize() {
        setClipToPadding(false);
        setClipChildren(false);

        swipeHelper = new SwipeHelper(this);
        swipeHelper.setAnimationDuration(animationDuration);
        swipeHelper.setOpacityEnd(swipeOpacity);

        dataSetObserver = new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                invalidate();
                requestLayout();
            }
        };
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState());
        bundle.putInt(KEY_CURRENT_INDEX, currentViewIndex - getChildCount());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            currentViewIndex = bundle.getInt(KEY_CURRENT_INDEX);
            state = bundle.getParcelable(KEY_SUPER_STATE);
        }

        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        if (adapter == null || adapter.isEmpty()) {
            currentViewIndex = 0;
            removeAllViewsInLayout();
            return;
        }

        for (int x = getChildCount();
             x < numberOfStackedViews && currentViewIndex < adapter.getCount();
             x++) {
            addNextView();
        }

        reorderItems();

        isFirstLayout = false;
    }

    private void addNextView() {
        if (currentViewIndex < adapter.getCount()) {
            View bottomView = adapter.getView(currentViewIndex, null, this);
            bottomView.setTag(R.id.new_view, true);

            if (!disableHwAcceleration) {
                bottomView.setLayerType(LAYER_TYPE_HARDWARE, null);
            }

            int width = getWidth() - (getPaddingLeft() + getPaddingRight());
            int height = getHeight() - (getPaddingTop() + getPaddingBottom());

            LayoutParams params = bottomView.getLayoutParams();
            if (params == null) {
                params = new LayoutParams(
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT);
            }

            int measureSpecWidth = MeasureSpec.AT_MOST;
            int measureSpecHeight = MeasureSpec.AT_MOST;

            if (params.width == LayoutParams.MATCH_PARENT) {
                measureSpecWidth = MeasureSpec.EXACTLY;
            }

            if (params.height == LayoutParams.MATCH_PARENT) {
                measureSpecHeight = MeasureSpec.EXACTLY;
            }

            bottomView.measure(measureSpecWidth | width, measureSpecHeight | height);
            addViewInLayout(bottomView, 0, params, true);
            currentViewIndex++;
        }
    }

    private void reorderItems() {
        for (int x = 0; x < getChildCount(); x++) {
            View childView = getChildAt(x);
            int topViewIndex = getChildCount() - 1;

            int distanceToViewAbove = (topViewIndex * viewSpacing) - (x * viewSpacing);
            int newPositionX = (getWidth() - childView.getMeasuredWidth()) / 2;
            int newPositionY = distanceToViewAbove + getPaddingTop();

            childView.layout(
                    newPositionX,
                    getPaddingTop(),
                    newPositionX + childView.getMeasuredWidth(),
                    getPaddingTop() + childView.getMeasuredHeight());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                childView.setTranslationZ(x);
            }

            boolean isNewView = (boolean) childView.getTag(R.id.new_view);
            float scaleFactor = (float) Math.pow(this.scaleFactor, getChildCount() - x);

            if (x == topViewIndex) {
                swipeHelper.unregisterObservedView();
                topView = childView;
                swipeHelper.registerObservedView(topView, newPositionX, newPositionY);
            }

            if (!isFirstLayout) {

                if (isNewView) {
                    childView.setTag(R.id.new_view, false);
                    childView.setAlpha(0);
                    childView.setY(newPositionY);
                    childView.setScaleY(scaleFactor);
                    childView.setScaleX(scaleFactor);
                }

                childView.animate()
                        .y(newPositionY)
                        .scaleX(scaleFactor)
                        .scaleY(scaleFactor)
                        .alpha(1)
                        .setDuration(animationDuration);

            } else {
                childView.setTag(R.id.new_view, false);
                childView.setY(newPositionY);
                childView.setScaleY(scaleFactor);
                childView.setScaleX(scaleFactor);
            }
        }
    }

    private void removeTopView() {
        if (topView != null) {
            removeView(topView);
            topView = null;
        }

        if (getChildCount() == 0) {
            if (swipeStackListener != null) swipeStackListener.onStackEmpty();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    public void onSwipeStart() {
        if (progressListener != null) progressListener.onSwipeStart(getCurrentPosition());
    }

    public void onSwipeProgress(float progress) {
        if (progressListener != null)
            progressListener.onSwipeProgress(getCurrentPosition(), progress);
    }

    public void onSwipeEnd() {
        if (progressListener != null) progressListener.onSwipeEnd(getCurrentPosition());
    }

    public void onViewSwipedToLeft() {
        if (swipeStackListener != null) swipeStackListener.onViewSwipedToLeft(getCurrentPosition());
        removeTopView();
    }

    public void onViewSwipedToRight() {
        if (swipeStackListener != null) swipeStackListener.onViewSwipedToRight(getCurrentPosition());
        removeTopView();
    }

    public int getCurrentPosition() {
        return currentViewIndex - getChildCount();
    }

    public Adapter getAdapter() {
        return adapter;
    }

    public void setAdapter(Adapter adapter) {
        if (this.adapter != null) this.adapter.unregisterDataSetObserver(dataSetObserver);
        this.adapter = adapter;
        this.adapter.registerDataSetObserver(dataSetObserver);
    }

    public int getAllowedSwipeDirections() {
        return allowedSwipeDirections;
    }

    public void setAllowedSwipeDirections(int directions) {
        allowedSwipeDirections = directions;
    }

    public void setListener(@Nullable SwipeStackListener listener) {
        swipeStackListener = listener;
    }

    public void setSwipeProgressListener(@Nullable SwipeProgressListener listener) {
        progressListener = listener;
    }

    public View getTopView() {
        return topView;
    }

    public void swipeTopViewToRight() {
        if (getChildCount() == 0) return;
        swipeHelper.swipeViewToRight();
    }

    public void swipeTopViewToLeft() {
        if (getChildCount() == 0) return;
        swipeHelper.swipeViewToLeft();
    }

    public interface SwipeStackListener {

        void onViewSwipedToLeft(int position);

        void onViewSwipedToRight(int position);

        void onStackEmpty();
    }

    public interface SwipeProgressListener {

        void onSwipeStart(int position);

        void onSwipeProgress(int position, float progress);

        void onSwipeEnd(int position);
    }
}
