package maintel.commontest.utils;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/7/24 10:28
 * 备注：
 */

public class LoadBitmap {

    private static LoadBitmap INSTANCE;

    public static LoadBitmap getINSTANCE() {

        if (INSTANCE == null) {
            INSTANCE = new LoadBitmap();
        }

        return INSTANCE;
    }

    int i;

    private LoadBitmap() {
    }


//    private Map<String, Bitmap> bitmapMap = new HashMap<>(); // 直接这样使用会引起OOM
    private Map<String,SoftReference<Bitmap>> bitmapMap = new HashMap<>();


    public void addBitmapCache(String path) {
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
        bitmapMap.put(path, softReference);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void addBitmapCache(Resources res, int id) {
        i++;
//        Bitmap bitmap = BitmapFactory.decodeResource(res, id);

        Bitmap bitmap = BitmapFactory.decodeResource(res, id);
        int sizeOf = bitmap.getRowBytes() * bitmap.getHeight();
        Log.d("LoadBitmap", "bitmap.getRowBytes()* bitmap.getHeight():" + (bitmap.getRowBytes() * bitmap.getHeight()));
        Log.d("LoadBitmap", "bitmap.getByteCount():" + bitmap.getByteCount());
        SoftReference<Bitmap> softReference = new SoftReference<Bitmap>(bitmap);
        bitmapMap.put(i + "", softReference);
    }
}
