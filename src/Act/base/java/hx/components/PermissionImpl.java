package hx.components;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import java.util.Arrays;

/**
 * Created by neu on 16/2/23.
 *
 * Android6.0 以后,权限检查与获取.
 */

public class PermissionImpl {

    public static final int PERMISSION_REQ_CODE = 0x1234;

    public static void require(Activity act, String ... permissions) {
        boolean[] granted = new boolean[permissions.length];
        Arrays.fill(granted, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(act, permissions, PERMISSION_REQ_CODE);
        }else {
            for(int i = 0; i < permissions.length; i++) {
                if (ActivityCompat.checkSelfPermission(act, permissions[i]) == PackageManager.PERMISSION_GRANTED) granted[i] = true;
            }
        }
    }

    public static boolean checkIfGranted(Activity act, String permission){
        return ActivityCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean checkIfGranted(Activity act, String ... permissions){
//        boolean[] granted = new boolean[permissions.length];
//        for(int i = 0; i < permissions.length; i++) granted[i] = ActivityCompat.checkSelfPermission(mAct, permissions[i]) == PackageManager.PERMISSION_GRANTED;
//        for(boolean g : granted) if(!g) return false;
//        return true;
        for(String permission : permissions){
            boolean granted = ActivityCompat.checkSelfPermission(act, permission) == PackageManager.PERMISSION_GRANTED;
            if(!granted) return false;
        }
        return true;
    }

    public void requirePermissions(Fragment fra, String ... permissions){
        fra.requestPermissions(permissions, PERMISSION_REQ_CODE);
    }
}
