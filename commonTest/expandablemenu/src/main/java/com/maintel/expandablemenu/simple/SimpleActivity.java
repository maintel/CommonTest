package com.maintel.expandablemenu.simple;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.maintel.expandablemenu.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jieyu.chen
 * @date 2019/7/12
 */
public class SimpleActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.expandable_main_activity);
        final ExpandableSelector sizesExpandableSelector = (ExpandableSelector) findViewById(R.id.expanded_menu);
        List<ExpandableItem> expandableItems = new ArrayList<ExpandableItem>();
        expandableItems.add(new ExpandableItem("XL"));
        expandableItems.add(new ExpandableItem("L"));
        expandableItems.add(new ExpandableItem("M"));
        expandableItems.add(new ExpandableItem("S"));
        sizesExpandableSelector.showExpandableItems(expandableItems);

        sizesExpandableSelector.setOnExpandableItemClickListener(new OnExpandableItemClickListener() {
            @Override public void onExpandableItemClickListener(int index, View view) {
                switch (index) {
                    case 1:
                        ExpandableItem firstItem = sizesExpandableSelector.getExpandableItem(1);
                        swipeFirstItem(1, firstItem);
                        break;
                    case 2:
                        ExpandableItem secondItem = sizesExpandableSelector.getExpandableItem(2);
                        swipeFirstItem(2, secondItem);
                        break;
                    case 3:
                        ExpandableItem fourthItem = sizesExpandableSelector.getExpandableItem(3);
                        swipeFirstItem(3, fourthItem);
                        break;
                    default:
                }
                sizesExpandableSelector.collapse();
            }

            private void swipeFirstItem(int position, ExpandableItem clickedItem) {
                ExpandableItem firstItem = sizesExpandableSelector.getExpandableItem(0);
                sizesExpandableSelector.updateExpandableItem(0, clickedItem);
                sizesExpandableSelector.updateExpandableItem(position, firstItem);
            }
        });

    }
}
