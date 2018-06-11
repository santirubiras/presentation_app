package com.example.santirubiras.catalog_slider_app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private String[] filesNames;
    private String[] filesPaths;
    private File[] files;
    private String NameOfFolder = "/Presentacion";
    private File[] rutas;

    public ImageAdapter(Context c) {
        mContext = c;

        String file_path = Environment.getExternalStorageDirectory().getPath() + NameOfFolder + "/";
        File dir = new File(file_path);

        //This checks if there are photos in specific storage.

        if (!dir.exists()) {
            //Toast.makeText(this, "No hay ningúna diapositiva en la presentación", Toast.LENGTH_LONG);
        } else {

            if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                filesPaths = new String[0];
                filesNames = new String[0];
                //Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
            } else {
                File dirDownload = Environment.getExternalStoragePublicDirectory( NameOfFolder + "/");
                if (dirDownload.isDirectory()) {
                    files = dirDownload.listFiles();
                    rutas = files;
                    filesPaths = new String[files.length];
                    filesNames = new String[files.length];

                    for (int i = 0; i < files.length; i++) {
                        filesPaths[i] = files[i].getAbsolutePath();
                        filesNames[i] = files[i].getName();
                    }
                }
            }
        }
    }

    public File[] rutas() {
        return rutas;
    }

    public int getCount() {
        return filesPaths.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(300, 300));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(2, 2, 2, 2);
        } else {
            imageView = (ImageView) convertView;
        }

        Bitmap bmp = BitmapFactory.decodeFile(filesPaths [position]);
        imageView.setImageBitmap(bmp);
        return imageView;
    }

}