package threadtest;

import org.junit.Test;

/**
 * @author jieyu.chen
 * @date 2018/9/4
 */
public class EnumTest {

    public static enum RECPMMEND_TIME {
        MORNING(1, "早晨", true),
        NOON(2, "下午", true),
        NIGHT(3, "晚上", true),
        NO_INNER(0, "不在推荐时间段内", false);

        int key;
        boolean isShow;
        String msg;

        RECPMMEND_TIME(int i, String msg, boolean isShow) {
            key = i;
            this.isShow = isShow;
            this.msg = msg;
        }

        public int getKey() {
            return key;
        }

        public boolean isShow() {
            return isShow;
        }

        public String getMsg() {
            return msg;
        }


        public static RECPMMEND_TIME getByKey(int i) {
            for (RECPMMEND_TIME item: RECPMMEND_TIME.values()) {
                if (item.getKey() == i) {
                    return item;
                }
            }
            return null;
        }

        public static boolean isShow(int i) {
            for (RECPMMEND_TIME item: RECPMMEND_TIME.values()) {
                if (item.getKey() == i) {
                    return item.isShow();
                }
            }
            return false;
        }

    }


    @Test
    public void test() {
        System.out.println(RECPMMEND_TIME.isShow(1));
        System.out.println(RECPMMEND_TIME.getByKey(1).msg);
    }

}
