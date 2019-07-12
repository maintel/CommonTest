/*
 * Copyright (C) 2015 Karumi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.maintel.expandablemenu.simple.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Performs all the animations and size or position changes related to the
 * ExpandableSelectorComponent and controls the view state in terms of collapsed/expanded
 * animation.
 */
public class ExpandableSelectorAnimator {

    public static final int EXPANDABLE_DIR_LEFT = 0;  //向左展开
    public static final int EXPANDABLE_DIR_TOP = 1; //向上展开
    public static final int EXPANDABLE_DIR_RIGHT = 2; //向右展开
    public static final int EXPANDABLE_DIR_BOTTOM = 3;//向下展开


    private static final String Y_ANIMATION = "translationY";
    private static final String X_ANIMATION = "translationX";
    private static final float CONTAINER_ANIMATION_OFFSET = 1.16f;

    private final View container;
    private final int animationDuration;
    private final int expandInterpolatorId;
    private final int collapseInterpolatorId;
    private final int containerInterpolatorId;

    private List<View> buttons;
    private boolean isCollapsed = true;
    private boolean hideFirstItemOnCollapse;
    private int animationDirection = EXPANDABLE_DIR_LEFT; // 展开方向  默认向上

    public ExpandableSelectorAnimator(View container, int animationDuration, int expandInterpolatorId, int
            collapseInterpolatorId, int containerInterpolatorId) {
        this.container = container;
        this.animationDuration = animationDuration;
        this.expandInterpolatorId = expandInterpolatorId;
        this.collapseInterpolatorId = collapseInterpolatorId;
        this.containerInterpolatorId = containerInterpolatorId;
        this.animationDirection = EXPANDABLE_DIR_LEFT;
    }

    public ExpandableSelectorAnimator(View container, int animationDuration, int expandInterpolatorId, int
            collapseInterpolatorId, int containerInterpolatorId, int animationDirection) {
        this.container = container;
        this.animationDuration = animationDuration;
        this.expandInterpolatorId = expandInterpolatorId;
        this.collapseInterpolatorId = collapseInterpolatorId;
        this.containerInterpolatorId = containerInterpolatorId;
        this.animationDirection = animationDirection;
    }

    /**
     * Returns true if the ExpandableSelector widget is collapsed or false if is expanded.
     */
    public boolean isCollapsed() {
        return isCollapsed;
    }

    /**
     * Returns true if the ExpandableSelector widget is expanded or false if is collapsed.
     */
    public boolean isExpanded() {
        return !isCollapsed();
    }

    /**
     * Configures the List of buttons used to calculate the animation parameters.
     */
    public void setButtons(List<View> buttons) {
        this.buttons = buttons;
    }

    /**
     * Expands the ExpandableSelector performing a resize animation and at the same time moves the
     * buttons configures as childrens to the associated position given the order in the List<View>
     * used to keep the reference to the buttons. The visibility of the buttons inside the
     * ExpandableSelector changes to View.VISIBLE before to perform the animation.
     */
    public void expand(Listener listener) {
        setCollapsed(false);  // 设置状态
        changeButtonsVisibility(View.VISIBLE); // 设置 按钮的可见状态
        expandButtons(); // 展开按钮
        expandContainer(listener); //展开view
    }

    /**
     * Collapses the ExpandableSelector performing a resize animation and at the same time moves the
     * buttons configures as childrens to the associated position given the order in the List<View>
     * used to keep the reference to the buttons. The visibility of the buttons inside the
     * ExpandableSelector changes to View.INVISIBLE after the resize animation.
     */
    public void collapse(Listener listener) {
        setCollapsed(true);
        collapseButtons();
        collapseContainer(listener);
    }

    /**
     * Configures the Button/ImageButton added to the ExpandableSelector to match with the initial
     * configuration needed by the component.
     */
    public void initializeButton(View button) {
        changeGravityToBottomCenterHorizontal(button);
    }

    /**
     * Configures the ExpandableSelectorAnimator to change the first item visibility to View.VISIBLE
     * /
     * View.INVISIBLE once the collapse/expand animation has been performed.
     */
    public void setHideFirstItemOnCollapse(boolean hideFirstItemOnCollapsed) {
        this.hideFirstItemOnCollapse = hideFirstItemOnCollapsed;
    }

    /**
     * Returns the component to the initial state without remove configuration related to animation
     * durations of if the first item visibility has to be changed.
     */
    public void reset() {
        this.buttons = new ArrayList<View>();
        this.isCollapsed = true;
    }

    private void setCollapsed(boolean isCollapsed) {
        this.isCollapsed = isCollapsed;
    }

    // 展开按钮
    private void expandButtons() {
        // 根据展开方向不同，做不同的计算
        if (animationDirection == EXPANDABLE_DIR_LEFT) {
            expandLeftButtons();
        } else if (animationDirection == EXPANDABLE_DIR_TOP) {
            expandTopButtons();
        } else if (animationDirection == EXPANDABLE_DIR_RIGHT) {
            expandRightButtons();
        } else {
            expandBottomButtons();
        }
    }

    private void expandTopButtons() {
        int numberOfButtons = buttons.size();
        Animator[] animations = new Animator[buttons.size()];
        for (int i = 0; i < numberOfButtons; i++) {
            View button = buttons.get(i);
            TimeInterpolator interpolator = getExpandAnimatorInterpolation();
            float translation = 0;
            for (int j = numberOfButtons - 1; j > i; j--) {
                View buttonj = buttons.get(i);
                translation = translation - buttonj.getHeight() - getMarginTop(buttonj) - getMarginBottom(buttonj);
            }
            animations[i] = createAnimatorForButton(interpolator, button, translation);
        }
        playAnimatorsTogether(animations);
    }

    private void expandLeftButtons() {
        int numberOfButtons = buttons.size();
        Animator[] animations = new Animator[buttons.size()];
        for (int i = 0; i < numberOfButtons; i++) {
            View button = buttons.get(i);
            TimeInterpolator interpolator = getExpandAnimatorInterpolation();
            float translation = 0;
            for (int j = numberOfButtons - 1; j > i; j--) {
                View buttonj = buttons.get(i);
                translation = translation - buttonj.getWidth() - getMarginRight(buttonj) - getMarginLeft(buttonj);
            }
            animations[i] = createAnimatorForButton(interpolator, button, translation);
        }
        playAnimatorsTogether(animations);
    }

    private void expandRightButtons() {
        int numberOfButtons = buttons.size();
        Animator[] animations = new Animator[buttons.size()];
        for (int i = 0; i < numberOfButtons; i++) {
            View button = buttons.get(i);
            TimeInterpolator interpolator = getExpandAnimatorInterpolation();
            float translation = 0;
            for (int j = numberOfButtons - 1; j > i; j--) {
                View buttonj = buttons.get(i);
                // 向右应该加
                translation = translation + buttonj.getWidth() + getMarginRight(buttonj) + getMarginLeft(buttonj);
            }
            animations[i] = createAnimatorForButton(interpolator, button, translation);
        }
        playAnimatorsTogether(animations);
    }

    private void expandBottomButtons() {
        int numberOfButtons = buttons.size();
        Animator[] animations = new Animator[buttons.size()];
        for (int i = 0; i < numberOfButtons; i++) {
            View button = buttons.get(i);
            TimeInterpolator interpolator = getExpandAnimatorInterpolation();
            float translation = 0;
            for (int j = numberOfButtons - 1; j > i; j--) {
                View buttonj = buttons.get(i);
                translation = translation + buttonj.getHeight() + getMarginTop(buttonj) + getMarginBottom(buttonj);
            }
            animations[i] = createAnimatorForButton(interpolator, button, translation);
        }
        playAnimatorsTogether(animations);
    }

    /**
     * 折叠按钮
     */
    private void collapseButtons() {
        int numberOfButtons = buttons.size();
        TimeInterpolator interpolator = getCollapseAnimatorInterpolation();
        Animator[] animations = new Animator[numberOfButtons];
        for (int i = 0; i < numberOfButtons; i++) {
            View button = buttons.get(i);
            // 不管哪种动画，折叠都是归零的
            animations[i] = createAnimatorForButton(interpolator, button, 0);
        }
        playAnimatorsTogether(animations);
    }

    /**
     * 展开背景
     *
     * @param listener
     */
    private void expandContainer(final Listener listener) {
        float toWidth;
        float toHeight;
        if (animationDirection == EXPANDABLE_DIR_LEFT || animationDirection == EXPANDABLE_DIR_RIGHT) {
            toWidth = getSumWidth();
            toHeight = container.getHeight();
        } else {
            toWidth = container.getWidth();
            toHeight = getSumHeight();
        }
        Interpolator interpolator = getContainerAnimationInterpolator();
        ResizeAnimation resizeAnimation =
                createResizeAnimation(toWidth, interpolator, toHeight, listener);
        container.startAnimation(resizeAnimation);
    }

    /**
     * 折叠背景
     *
     * @param listener
     */
    private void collapseContainer(final Listener listener) {
        float toWidth;
        float toHeight;
        if (animationDirection == EXPANDABLE_DIR_LEFT || animationDirection == EXPANDABLE_DIR_RIGHT) {
            toWidth = getFirstItemWidth();
            toHeight = container.getHeight();
        } else {
            toWidth = container.getWidth();
            toHeight = getFirstItemHeight();
        }

        Interpolator interpolator = getContainerAnimationInterpolator();
        ResizeAnimation resizeAnimation =
                createResizeAnimation(toWidth, interpolator, toHeight, new Listener() {
                    @Override
                    public void onAnimationFinished() {
                        changeButtonsVisibility(View.INVISIBLE);
                        listener.onAnimationFinished();
                    }
                });
        container.startAnimation(resizeAnimation);
    }

    private ObjectAnimator createAnimatorForButton(TimeInterpolator interpolator, final View button,
                                                   float to) {
        String translation = Y_ANIMATION;
        if (animationDirection == EXPANDABLE_DIR_LEFT || animationDirection == EXPANDABLE_DIR_RIGHT) {
            translation = X_ANIMATION;
        }
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(button, translation, to);
        objectAnimator.setInterpolator(interpolator);
        objectAnimator.setDuration(animationDuration);
        return objectAnimator;
    }

    private ResizeAnimation createResizeAnimation(float toWidth, Interpolator interpolator,
                                                  float toHeight, final Listener listener) {
        ResizeAnimation resizeAnimation = new ResizeAnimation(container, toWidth, toHeight);
        resizeAnimation.setInterpolator(interpolator);
        resizeAnimation.setDuration((long) (animationDuration));
        resizeAnimation.setAnimationListener(new AbstractAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                listener.onAnimationFinished();
            }
        });
        return resizeAnimation;
    }

    private void playAnimatorsTogether(Animator[] animations) {
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animations);
        animatorSet.start();
    }

    private void changeButtonsVisibility(int visibility) {
        int lastItem = hideFirstItemOnCollapse ? buttons.size() : buttons.size() - 1;
        for (int i = 0; i < lastItem; i++) {
            View button = buttons.get(i);
            button.setVisibility(visibility);
        }
    }

    private TimeInterpolator getExpandAnimatorInterpolation() {
        return AnimationUtils.loadInterpolator(container.getContext(), expandInterpolatorId);
    }

    private TimeInterpolator getCollapseAnimatorInterpolation() {
        return AnimationUtils.loadInterpolator(container.getContext(), collapseInterpolatorId);
    }

    private Interpolator getContainerAnimationInterpolator() {
        return AnimationUtils.loadInterpolator(container.getContext(), containerInterpolatorId);
    }

    private int getSumHeight() {
        int sumHeight = 0;
        for (View button : buttons) {
            sumHeight += button.getHeight() + getMarginTop(button) + getMarginBottom(button);
        }
        return sumHeight;
    }

    private int getSumWidth() {
        int sumHeight = 0;
        for (View button : buttons) {
            sumHeight += button.getWidth() + getMarginRight(button) + getMarginLeft(button);
        }
        return sumHeight;
    }

    private int getMarginTop(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return layoutParams.topMargin;
    }

    private int getMarginRight(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return layoutParams.rightMargin;
    }

    private int getMarginLeft(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return layoutParams.leftMargin;
    }

    private int getMarginBottom(View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        return layoutParams.bottomMargin;
    }

    private float getFirstItemHeight() {
        View firstButton = buttons.get(0);
        int height = firstButton.getHeight();
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) firstButton.getLayoutParams();
        int topMargin = layoutParams.topMargin;
        int bottomMargin = layoutParams.bottomMargin;
        return height + topMargin + bottomMargin;
    }

    private float getFirstItemWidth() {
        View firstButton = buttons.get(0);
        int width = firstButton.getWidth();
        FrameLayout.LayoutParams layoutParams =
                (FrameLayout.LayoutParams) firstButton.getLayoutParams();
        int rightMargin = layoutParams.rightMargin;
        int leftMargin = layoutParams.leftMargin;
        return width + rightMargin + leftMargin;
    }

    /**
     * 设置按钮的对齐方式
     *
     * @param view
     */
    private void changeGravityToBottomCenterHorizontal(View view) {

        int gravity;

        if (animationDirection == EXPANDABLE_DIR_LEFT) {
            gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        } else if (animationDirection == EXPANDABLE_DIR_TOP) {
            gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        } else if (animationDirection == EXPANDABLE_DIR_RIGHT) {
            gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
        } else {
            gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        }

        ((FrameLayout.LayoutParams) view.getLayoutParams()).gravity = gravity;
    }

    public interface Listener {
        void onAnimationFinished();
    }
}
