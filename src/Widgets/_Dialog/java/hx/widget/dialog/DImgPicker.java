package hx.widget.dialog;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.io.File;
import java.util.List;

import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import hx.lib.R;

/**
 * Created by rose on 16-8-1.
 *
 * Based on GalleryFinal.
 *
 */

public class DImgPicker {

    public static void show(Activity act, Callback cb){
        show(act, null, cb);
    }
    public static void show(Activity act, ImageView iv, Callback cb){
        GalleryFinal.OnHanlderResultCallback callback = new GalleryFinal.OnHanlderResultCallback() {
            @Override public void onHanlderSuccess(int requestCode, List<PhotoInfo> resultList) {
                String path = resultList.get(0).getPhotoPath();
                if(iv != null) iv.setImageURI(Uri.fromFile(new File(path)));
                if(cb != null) cb.onPicture(path);
            }
            @Override public void onHanlderFailure(int requestCode, String errorMsg) {}
        };
        DialogPlus dialog = DialogPlus.newDialog(act)
                .setContentHolder(new ViewHolder(R.layout.d_img_picker))
                .setOnClickListener((dialogPlus, view) -> {
                    int id = view.getId();
                    if(id == R.id._v_camera) {
                        GalleryFinal.openCamera(0, callback);
                        dialogPlus.dismiss();
                    } else if(id == R.id._v_gallery) {
                        GalleryFinal.openGallerySingle(1, callback);
                        dialogPlus.dismiss();
                    } else if(id == R.id._v_cancel) dialogPlus.dismiss();
                })
                .setContentBackgroundResource(android.R.color.transparent)
                .setMargin(24, 0, 24, 24)
                .create();
        dialog.show();
    }

    public interface Callback{
        void onPicture(String path);
    }
}
