package com.parallaxscrolling.fragmentBuilder;


/**
 * 建造者模式 构建类
 *
 * @author jieyu.chen
 * @date 2018/9/11
 */
public class ParallaxConfiguration {

    static ParallaxConfiguration create(Builder builder) {
        ParallaxConfiguration configuration = new ParallaxConfiguration(builder);
        return configuration;
    }

    private PageProvider mProvider;
    private int mCount;

    private ParallaxConfiguration(Builder builder) {
        mProvider = builder.mPageProvider;
        mCount = builder.mCount;
    }


    public int getCount() {
        return mCount;
    }

    public PageProvider getPagerProvider() {
        return mProvider;
    }


    public final static class Builder<TFragment> {
        private PageProvider<TFragment> mPageProvider;

        private int mCount;

        public ParallaxConfiguration build() {
            return ParallaxConfiguration.create(this);
        }

        public Builder setPageCount(int i) {
            mCount = i;
            return this;
        }

        public Builder setPageProvider(final PageOptionsProvider pageOptionsProvider) {
            mPageProvider = new PageProvider<TFragment>() {

                @SuppressWarnings("unchecked")
                @Override
                public TFragment providerPage(int poi) {
                    return (TFragment) PageFragment.newInstance(pageOptionsProvider.provider(poi));
                }
            };
            return this;
        }
    }

}
