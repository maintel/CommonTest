package maintel.commontest.view;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Created by asus on 2015/11/24.
 */
public class ViewPagerAdapter extends PagerAdapter {
    List<View> mPages;

    public ViewPagerAdapter(List<View> pages) {
        mPages = pages;
    }

    public void refresh(List<View> pages) {
        this.mPages = pages;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        try {
            container.removeView(mPages.get(position));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = mPages.get(position);
        container.addView(v);
        return v;
    }

    public View getItemByPoint(int position) {
        try {
            return mPages.get(position);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
