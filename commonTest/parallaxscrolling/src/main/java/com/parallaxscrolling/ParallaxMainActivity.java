package com.parallaxscrolling;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import com.parallaxscrolling.fragmentBuilder.PageOptions;
import com.parallaxscrolling.fragmentBuilder.PageOptionsProvider;
import com.parallaxscrolling.fragmentBuilder.ParallaxConfiguration;
import com.parallaxscrolling.transfrom.FragmentTransformer;
import com.parallaxscrolling.transfrom.ITransformerCallBack;

/**
 * @author jieyu.chen
 * @date 2018/9/11
 */
public class ParallaxMainActivity extends FragmentActivity {

    private ViewPager viewPager;

    private ParallaxConfiguration configuration;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lib);
        configuration = new ParallaxConfiguration.Builder()
                .setPageCount(4)
                .setPageProvider(pageOptionsProvider)
                .build();

        viewPager = findViewById(R.id.vp);
        viewPager.setPageTransformer(true, new FragmentTransformer(transformerCallBack));
        viewPager.setAdapter(new MyFragmentAdapter(getSupportFragmentManager()));
    }


    private ITransformerCallBack transformerCallBack = new ITransformerCallBack() {
        @Override
        public void transformPage(View page, float position) {

        }
    };


    private final PageOptionsProvider pageOptionsProvider = new PageOptionsProvider() {

        @Override
        public PageOptions provider(int poi) {
            return null;
        }
    };


    class MyFragmentAdapter extends FragmentPagerAdapter {

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) configuration.getPagerProvider().providerPage(position);
        }

        @Override
        public int getCount() {
            return configuration.getCount();
        }
    }
}
