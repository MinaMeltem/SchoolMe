package nyc.c4q.ashiquechowdhury.schoolme.swipe;

import android.animation.Animator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

public class SwipeHelper implements View.OnTouchListener {

    private final SwipeStack swipeStack;
    private View observedView;
    private boolean listenForTouchEvents;
    private float downX;
    private float downY;
    private float initialX;
    private float initialY;
    private int pointerId;
    private float opacityEnd = SwipeStack.DEFAULT_SWIPE_OPACITY;
    private int animationDuration = SwipeStack.DEFAULT_ANIMATION_DURATION;

    public SwipeHelper(SwipeStack swipeStack) {
        this.swipeStack = swipeStack;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                if(!listenForTouchEvents || !swipeStack.isEnabled()) {
                    return false;
                }

                v.getParent().requestDisallowInterceptTouchEvent(true);
                swipeStack.onSwipeStart();
                pointerId = event.getPointerId(0);
                downX = event.getX(pointerId);
                downY = event.getY(pointerId);
                return true;

            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(pointerId);
                if (pointerIndex < 0) return false;

                float dx = event.getX(pointerIndex) - downX;
                float dy = event.getY(pointerIndex) - downY;
                float newX = observedView.getX() + dx;
                float newY = observedView.getY() + dy;

                observedView.setX(newX);
                observedView.setY(newY);

                float dragDistanceX = newX - initialX;
                float swipeProgress = Math.min(Math.max(
                        dragDistanceX / swipeStack.getWidth(), -1), 1);

                swipeStack.onSwipeProgress(swipeProgress);

                if (opacityEnd < 1f) {
                    float alpha = 1 - Math.min(Math.abs(swipeProgress * 2), 1);
                    observedView.setAlpha(alpha);
                }
                return true;

            case MotionEvent.ACTION_UP:
                v.getParent().requestDisallowInterceptTouchEvent(false);
                swipeStack.onSwipeEnd();
                checkViewPosition();
                return true;
        }
        return false;
    }

    private void checkViewPosition() {
        if(!swipeStack.isEnabled()) {
            resetViewPosition();
            return;
        }

        float viewCenterHorizontal = observedView.getX() + (observedView.getWidth() / 2);
        float parentFirstThird = swipeStack.getWidth() / 3f;
        float parentLastThird = parentFirstThird * 2;

        if (viewCenterHorizontal < parentFirstThird &&
                swipeStack.getAllowedSwipeDirections() != SwipeStack.SWIPE_DIRECTION_ONLY_RIGHT) {
            swipeViewToLeft(animationDuration / 2);
        } else if (viewCenterHorizontal > parentLastThird &&
                swipeStack.getAllowedSwipeDirections() != SwipeStack.SWIPE_DIRECTION_ONLY_LEFT) {
            swipeViewToRight(animationDuration / 2);
        } else {
            resetViewPosition();
        }
    }

    private void resetViewPosition() {
        observedView.animate()
                .x(initialX)
                .y(initialY)
                .rotation(0)
                .alpha(1)
                .setDuration(animationDuration)
                .setInterpolator(new OvershootInterpolator(1.4f))
                .setListener(null);
    }

    private void swipeViewToLeft(int duration) {
        if (!listenForTouchEvents) return;
        listenForTouchEvents = false;
        observedView.animate().cancel();
        observedView.animate()
                .x(-swipeStack.getWidth() + observedView.getX())
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimationUtils.AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        swipeStack.onViewSwipedToLeft();
                    }
                });
    }

    private void swipeViewToRight(int duration) {
        if (!listenForTouchEvents) return;
        listenForTouchEvents = false;
        observedView.animate().cancel();
        observedView.animate()
                .x(swipeStack.getWidth() + observedView.getX())
                .alpha(0f)
                .setDuration(duration)
                .setListener(new AnimationUtils.AnimationEndListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        swipeStack.onViewSwipedToRight();
                    }
                });
    }

    public void registerObservedView(View view, float initialX, float initialY) {
        if (view == null) return;
        observedView = view;
        observedView.setOnTouchListener(this);
        this.initialX = initialX;
        this.initialY = initialY;
        listenForTouchEvents = true;
    }

    public void unregisterObservedView() {
        if (observedView != null) {
            observedView.setOnTouchListener(null);
        }
        observedView = null;
        listenForTouchEvents = false;
    }

    public void setAnimationDuration(int duration) {
        animationDuration = duration;
    }

    public void setOpacityEnd(float alpha) {
        opacityEnd = alpha;
    }

    public void swipeViewToLeft() {
        swipeViewToLeft(animationDuration);
    }

    public void swipeViewToRight() {
        swipeViewToRight(animationDuration);
    }
}
