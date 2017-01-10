package maintel.commontest.ZipExtractor;

import maintel.commontest.base.BasePresenter;
import maintel.commontest.base.BaseView;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/1/9 13:46
 * 备注：
 */

public interface ZipExtractorContract {
    interface Presenter extends BasePresenter {

        void zipExtractor();

        void downLoad();
    }

    interface View extends BaseView<Presenter> {
        void setMessage(String msg);
    }
}
