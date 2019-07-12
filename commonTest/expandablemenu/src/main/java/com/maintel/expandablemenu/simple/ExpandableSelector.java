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

package com.maintel.expandablemenu.simple;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


import com.maintel.expandablemenu.R;
import com.maintel.expandablemenu.simple.animation.ExpandableSelectorAnimator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * FrameLayout extension used to show a list of ExpandableItems instances represented with Button
 * or ImageButton widgets which can be collapsed and expanded using an animation. The configurable
 * elements of the class are:
 * <p>
 * - List of items to show represented with ExpandableItem instances.
 * - Time used to perform the collapse/expand animations. Expressed in milliseconds.
 * - Show or hide the view background when the List of ExpandaleItems are collapsed.
 * - Configure a ExpandableSelectorListeners to be notified when the view is going to be
 * collapsed/expanded or has
 * been collapsed/expanded.
 * - Configure a OnExpandableItemClickListener to be notified when an item is clicked.
 */
public class ExpandableSelector extends FrameLayout {

    private static final int DEFAULT_ANIMATION_DURATION = 300;

    private List<ExpandableItem> expandableItems = Collections.EMPTY_LIST;
    private List<View> buttons = new ArrayList<View>();
    private ExpandableSelectorAnimator expandableSelectorAnimator;
    private ExpandableSelectorListener listener;
    private OnExpandableItemClickListener clickListener;
    private ExpandableInitHelper expandableInitHelper;

    private boolean hideBackgroundIfCollapsed;
    private Drawable expandedBackground;

    public ExpandableSelector(Context context) {
        this(context, null);
    }

    public ExpandableSelector(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableSelector(Context context, AttributeSet attrs, int defStyleAttr,
                              int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initializeView(attrs);
    }

    public void setExpandableInitHelper(ExpandableInitHelper expandableInitHelper) {
        this.expandableInitHelper = expandableInitHelper;
    }

    /**
     * 设置菜单按钮
     */
    public void showExpandableItems(List<ExpandableItem> expandableItems) {
        validateExpandableItems(expandableItems); // 验证item 如果为空则抛出异常

        reset();  // 重置状态
        setExpandableItems(expandableItems);
        renderExpandableItems();
        hookListeners();
        bringChildsToFront(expandableItems);
    }

    /**
     * 展开
     */
    public void expand() {
        expandableSelectorAnimator.expand(new ExpandableSelectorAnimator.Listener() {
            @Override
            public void onAnimationFinished() {
                notifyExpanded();
            }
        });
        notifyExpand();
        updateBackground();
    }

    /**
     * 折叠
     */
    public void collapse() {
        expandableSelectorAnimator.collapse(new ExpandableSelectorAnimator.Listener() {
            @Override
            public void onAnimationFinished() {
                updateBackground();
                notifyCollapsed();
            }
        });
        notifyCollapse();
    }

    /**
     * Returns true if the view is collapsed and false if the view is expanded.
     */
    public boolean isCollapsed() {
        return expandableSelectorAnimator.isCollapsed();
    }

    /**
     * Returns true if the view is expanded and false if the view is collapsed.
     */
    public boolean isExpanded() {
        return expandableSelectorAnimator.isExpanded();
    }

    /**
     * Configures a ExpandableSelectorListener instance to be notified when different collapse/expand
     * animations be performed.
     */
    public void setExpandableSelectorListener(ExpandableSelectorListener listener) {
        this.listener = listener;
    }

    /**
     * Configures a OnExpandableItemClickListener instance to be notified when a Button/ImageButton
     * inside ExpandableSelector be clicked. If the component is collapsed an the first button is
     * clicked the listener will not be notified. This listener will be notified about button clicks
     * just when ExpandableSelector be collapsed.
     */
    public void setOnExpandableItemClickListener(OnExpandableItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * Given a position passed as parameter returns the ExpandableItem associated.
     */
    public ExpandableItem getExpandableItem(int expandableItemPosition) {
        return expandableItems.get(expandableItemPosition);
    }

    /**
     * Changes the ExpandableItem associated to a given position and updates the Button widget to
     * show
     * the new ExpandableItem information.
     */
    public void updateExpandableItem(int expandableItemPosition, ExpandableItem expandableItem) {
        validateExpandableItem(expandableItem);
        expandableItems.remove(expandableItemPosition);
        expandableItems.add(expandableItemPosition, expandableItem);
        int buttonPosition = buttons.size() - 1 - expandableItemPosition;
        configureButtonContent(buttons.get(buttonPosition), expandableItem, buttonPosition);
    }

    /**
     * 初始化view
     *
     * @param attrs
     */
    private void initializeView(AttributeSet attrs) {
        TypedArray attributes =
                getContext().obtainStyledAttributes(attrs, R.styleable.expandable_selector);
        // 设置动画
        initializeAnimationDuration(attributes);
        //设置关闭背景
        initializeHideBackgroundIfCollapsed(attributes);
        // 设置是否隐藏第一个条目，用来确定隐藏起来的条目个数
        initializeHideFirstItemOnCollapse(attributes);
        attributes.recycle();
    }

    private void initializeHideBackgroundIfCollapsed(TypedArray attributes) {
        hideBackgroundIfCollapsed =
                attributes.getBoolean(R.styleable.expandable_selector_hide_background_if_collapsed, false);
        expandedBackground = getBackground();
        updateBackground();
    }

    private void initializeAnimationDuration(TypedArray attributes) {
        // 动画持续时间
        int animationDuration =
                attributes.getInteger(R.styleable.expandable_selector_animation_duration,
                        DEFAULT_ANIMATION_DURATION);
        // 展开动画差值器
        int expandInterpolatorId =
                attributes.getResourceId(R.styleable.expandable_selector_expand_interpolator,
                        android.R.anim.linear_interpolator);
        // 折叠动画差值器
        int collapseInterpolatorId =
                attributes.getResourceId(R.styleable.expandable_selector_collapse_interpolator,
                        android.R.anim.linear_interpolator);
        // 当前view自身的动画差值器
        int containerInterpolatorId =
                attributes.getResourceId(R.styleable.expandable_selector_container_interpolator,
                        android.R.anim.linear_interpolator);
        int expandDirection = attributes.getInteger(R.styleable.expandable_selector_expand_direction, 1);
        expandableSelectorAnimator = new ExpandableSelectorAnimator(this, animationDuration, expandInterpolatorId,
                collapseInterpolatorId, containerInterpolatorId, expandDirection);
    }

    private void initializeHideFirstItemOnCollapse(TypedArray attributes) {
        boolean hideFirstItemOnCollapsed =
                attributes.getBoolean(R.styleable.expandable_selector_hide_first_item_on_collapse, false);
        expandableSelectorAnimator.setHideFirstItemOnCollapse(hideFirstItemOnCollapsed);
    }

    private void updateBackground() {
        if (!hideBackgroundIfCollapsed) {
            return;
        }
        if (isExpanded()) {
            setBackgroundDrawable(expandedBackground);
        } else {
            setBackgroundResource(android.R.color.transparent);
        }
    }

    /**
     * 重置状态
     */
    private void reset() {
        this.expandableItems = Collections.EMPTY_LIST;
        for (View button : buttons) {
            removeView(button);
        }
        this.buttons = new ArrayList<View>();
        expandableSelectorAnimator.reset();
    }

    /**
     * 添加 view
     */
    private void renderExpandableItems() {
        int numberOfItems = expandableItems.size();
        for (int i = numberOfItems - 1; i >= 0; i--) {
            View button = initializeButton(i);
            addView(button);
            buttons.add(button);
            expandableSelectorAnimator.initializeButton(button);
            configureButtonContent(button, expandableItems.get((i)), i);
        }
        expandableSelectorAnimator.setButtons(buttons);
    }

    private void hookListeners() {
        final int numberOfButtons = buttons.size();
        boolean thereIsMoreThanOneButton = numberOfButtons > 1;
        // 如果 item 个数大于一个，则第一个在关闭状态下时被点击就展开菜单
        if (thereIsMoreThanOneButton) {
            buttons.get(numberOfButtons - 1).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isCollapsed()) {
                        expand();
                    } else {
                        notifyButtonClicked(0, v);
                    }
                }
            });
        }
        for (int i = 0; i < numberOfButtons - 1; i++) {
            final int buttonPosition = i;
            buttons.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    int buttonIndex = numberOfButtons - 1 - buttonPosition;
                    notifyButtonClicked(buttonIndex, v);
                }
            });
        }
    }

    private void notifyButtonClicked(int itemPosition, View button) {
        if (clickListener != null) {
            clickListener.onExpandableItemClickListener(itemPosition, button);
        }
    }

    private View initializeButton(int expandableItemPosition) {
        Context context = getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View button = expandableInitHelper.initView(layoutInflater, this);
        int visibility = expandableItemPosition == 0 ? View.VISIBLE : View.INVISIBLE;
        button.setVisibility(visibility);
        return button;
    }

    private void configureButtonContent(View button, ExpandableItem expandableItem, int index) {
        expandableInitHelper.configureViewContent(button, expandableItem, index);
    }

    private void notifyExpand() {
        if (hasListenerConfigured()) {
            listener.onExpand();
        }
    }

    private void notifyCollapse() {
        if (hasListenerConfigured()) {
            listener.onCollapse();
        }
    }

    private void notifyExpanded() {
        if (hasListenerConfigured()) {
            listener.onExpanded();
        }
    }

    private void notifyCollapsed() {
        if (hasListenerConfigured()) {
            listener.onCollapsed();
        }
    }

    private boolean hasListenerConfigured() {
        return listener != null;
    }

    private void validateExpandableItem(ExpandableItem expandableItem) {
        if (expandableItem == null) {
            throw new IllegalArgumentException(
                    "You can't use a null instance of ExpandableItem as parameter.");
        }
    }

    /**
     * 验证item 如果为空则抛出异常
     *
     * @param expandableItems
     */
    private void validateExpandableItems(List<ExpandableItem> expandableItems) {
        if (expandableItems == null) {
            throw new IllegalArgumentException(
                    "The List<ExpandableItem> passed as argument can't be null");
        }
    }

    private void setExpandableItems(List<ExpandableItem> expandableItems) {
        this.expandableItems = new ArrayList<ExpandableItem>(expandableItems);
    }

    private void bringChildsToFront(List<ExpandableItem> expandableItems) {
        int childCount = getChildCount();
        int numberOfExpandableItems = expandableItems.size();
        if (childCount > numberOfExpandableItems) {
            for (int i = 0; i < childCount - numberOfExpandableItems; i++) {
                getChildAt(i).bringToFront();
            }
        }
    }
}
