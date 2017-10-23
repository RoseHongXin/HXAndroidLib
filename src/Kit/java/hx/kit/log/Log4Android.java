package hx.kit.log;import android.util.Log;public class Log4Android {	private static int LOG_LEVEL = 7;	private final static int ERROR  = 1;		//错误		红色	private final static int WARN   = 2;		//警告		橙色	private final static int INFO   = 3;		//提示性消息  绿色	private final static int DEBUG  = 4;		//调试 		蓝色	private final static int VERBOS = 5;		//啰嗦 		黑色	private final static int SYSOUT = 6;		//调试		绿色	private static boolean m2File = false;	public static void debugSwitch(boolean on){		LOG_LEVEL = on ? 7 : 2;	}	public static void toFileSwitch(boolean on){		m2File = on;	}	public static void e(Object obj, String msg) {		String tag = obj instanceof String ? (String) obj : obj.getClass().getName();		if (m2File) Log2File.e(tag, msg);		if (LOG_LEVEL > ERROR) Log.e(tag, msg);	}	public static void w(Object obj, String msg) {		String tag = obj instanceof String ? (String) obj : obj.getClass().getName();		if (m2File) Log2File.w(tag, msg);		if (LOG_LEVEL > WARN) Log.w(tag, msg);	}	public static void i(Object obj, String msg){		String tag = obj instanceof String ? (String) obj : obj.getClass().getName();		if (m2File) Log2File.i(tag, msg);		if (LOG_LEVEL > INFO) Log.i(tag, msg);	}	public static void d(Object obj, String msg){		String tag = obj instanceof String ? (String) obj : obj.getClass().getName();		if (m2File) Log2File.d(tag, msg);		if (LOG_LEVEL > DEBUG) Log.d(tag, msg);	}	public static void v(Object obj, String msg){		String tag = obj instanceof String ? (String) obj : obj.getClass().getName();		if (m2File) Log2File.v(tag, msg);		if (LOG_LEVEL > VERBOS) Log.v(tag, msg);	}	public static void sysout(Object obj, String msg){		String tag = obj instanceof String ? (String) obj : obj.getClass().getName();		if (m2File) Log2File.sysout(tag, msg);		if (LOG_LEVEL > SYSOUT) System.out.println(tag + "->: " + msg);	}}