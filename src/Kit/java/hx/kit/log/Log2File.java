package hx.kit.log;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by RoseHongXin on 2017/9/5 0005.
 */

public class Log2File {

    private static final String FILE_NAME_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
    private static final String LOG_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
//    private static final int CACHE_QUEUE_SIZE = Integer.MAX_VALUE / 0xffff;
    private static final int CACHE_QUEUE_SIZE = 2048;
    private static final String DEFAULT_DIR = "Logs";
    private static final SimpleDateFormat mFileNameDateFormat = new SimpleDateFormat(FILE_NAME_DATE_FORMAT, Locale.CHINA);
    private static final SimpleDateFormat mLogDateFormat = new SimpleDateFormat(LOG_DATE_FORMAT, Locale.CHINA);

    private static File mFile;
    private static ExecutorService sLogExecutor = Executors.newSingleThreadExecutor();
    private static Queue<String> sMsgQueue = new ArrayBlockingQueue<>(CACHE_QUEUE_SIZE);
    private static String mDir;
    private static boolean mImmediately = false;

    public static String getPath(String dir){
        File f = new File(Environment.getExternalStorageDirectory().toString() + File.separator + dir + File.separator);
        if (!f.exists()) {
            if(f.mkdirs()) return f.getAbsolutePath();
            f.mkdirs();
        }
        return f.getAbsolutePath();
    }

    public static void init(){
        init(DEFAULT_DIR, false);
    }
    public static void init(String dir, boolean immediately){
        Log4Android.toFileSwitch(true);
        mDir = dir;
        mImmediately = immediately;
        String path = getPath(TextUtils.isEmpty(mDir) ? DEFAULT_DIR : DEFAULT_DIR + File.separator + mDir);
        mFile = new File(path, mFileNameDateFormat.format(new Date()) + ".log");
    }

    public static void e(String tag, String msg){ write2File("--e--" + tag, msg);}
    public static void w(String tag, String msg){ write2File("--w--" + tag, msg);}
    public static void i(String tag, String msg){ write2File("--i--" + tag, msg);}
    public static void d(String tag, String msg){ write2File("--d--" + tag, msg);}
    public static void v(String tag, String msg){ write2File("--v--" + tag, msg);}
    public static void sysout(String tag, String msg){ write2File("--s--" + tag, msg);}


    private static void write2File(final String tag, final String msg) {
        sLogExecutor.execute(() -> {
//            String logMsg = String.format(Locale.CHINA, "%s pid=%d %s: %s\n", mLogDateFormat.format(new Date()), android.os.Process.myPid(), tag, msg);
            String logMsg = String.format(Locale.CHINA, "%s|%s: %s\n", mLogDateFormat.format(new Date()), tag, msg);
            if(mImmediately) flush2File(logMsg);
            else if(sMsgQueue.size() >= CACHE_QUEUE_SIZE) {
                flush2File();
            }else{
                sMsgQueue.add(logMsg);
            }
        });
    }
    private static void flush2File(String logs) {
        if(mFile == null) init(mDir, mImmediately);
        if(mFile == null) {
            Log.e("Log2File", "error, no specific file created.");
            return;
        }
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(mFile, true);
            fileWriter.write(logs);
            fileWriter.flush();
            Log.i("Log2File", "to file:" + mFile.getAbsolutePath());
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {fileWriter.close();} catch (Exception e) {e.printStackTrace();}
            }
        }
    }
    public static void flush2File(){
        StringBuilder stringBuilder = new StringBuilder();
        for (String message : sMsgQueue) {
            stringBuilder.append(message);
        }
        flush2File(stringBuilder.toString());
        sMsgQueue.clear();
        init(mDir, mImmediately);
    }

}
