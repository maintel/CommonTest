package com.parallaxscrolling.fragmentBuilder;

/**
 * @author jieyu.chen
 * @date 2018/9/11
 */
public interface PageProvider<TFragment> {

    TFragment providerPage(int poi);

}