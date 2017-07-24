package maintel.commontest.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/1/9 15:52
 * 备注：
 */

public class FileUtils {
    public static File saveFile(InputStream is, String filePath, String name) {
        try {
//            File dir = new File(filePath);
//            if (!dir.exists()) {// 判断文件目录是否存在
//                //如果文件存在则删除已存在的文件夹。
//                dir.mkdirs();
//            }
            File file = new File(filePath, name);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
//            if(file.exists()){
//                file.delete();
//            }
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                fos.flush();
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static byte[] readFile(String url) {
        return readFile(readFileRetFile(url));
    }

    public static byte[] readFile(File file) {
        if (file == null) {
            return null;
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] b = new byte[fileInputStream.available()];
            fileInputStream.read(b);
            return b;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static File readFileRetFile(String url) {
        try {
            File file = new File(url);
            if (file.exists()) {
                return file;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static InputStream readFileAssets(Context context, String name) {
        try {
//Return an AssetManager instance for your application's package
            InputStream is = context.getResources().getAssets().open(name);
//            int size = is.available();
//
//            // Read the entire asset into a local byte buffer.
//            byte[] buffer = new byte[size];
//            is.read(buffer);
            return is;
            // Convert the buffer into a string.
            // Finally stick the string into the text view.
        } catch (IOException e) {
            // Should never happen!
            return null;
        }
    }

    public static String getFileMD5Assets(Context context, String name) {
        return getFileMD5(readFileAssets(context, name));
//        return MD5Util.getFileMD5(saveFile(readFileAssets(context, name), Environment.getExternalStorageDirectory().getPath() + "/", name));
    }


    public static String getFileMD5(InputStream is) {
        try {
            MessageDigest digest;
            FileInputStream in;
            byte buffer[] = new byte[1024];
            int len;
            digest = MessageDigest.getInstance("MD5");
            BufferedInputStream bis = new BufferedInputStream(is);
            while ((len = bis.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            is.close();
            BigInteger bigInt = new BigInteger(1, digest.digest());
            return bigInt.toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }
}
