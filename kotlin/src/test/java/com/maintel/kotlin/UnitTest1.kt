package com.maintel.kotlin

import org.junit.Test

/**
 *
 * @author jieyu.chen
 * @date 2018/12/3
 */
class UnitTest1 : ExampleUnitTest() {

    @Test
    fun test1() {
        val utils = Utils()
        utils.soutStr(1000)
    }

}