package maintel.commontest.net;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/1/9 14:41
 * 备注：
 */

public interface ProgressResponseListener {
    void onResponseProgress(long bytesRead, long contentLength, boolean done);
}
