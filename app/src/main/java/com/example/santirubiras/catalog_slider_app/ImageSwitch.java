package com.example.santirubiras.catalog_slider_app;

import android.net.Uri;
import android.os.Environment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import android.view.MotionEvent;
import static  android.view.GestureDetector.*;


// With "implements OnGestureListener" you can do gestures with fingers like swipe up or something like that.
public class ImageSwitch extends AppCompatActivity implements OnGestureListener, OnDoubleTapListener {

    private ImageSwitcher imageSwitcher;
    private int posicion;
    private static final int DURACION = 5000;
    private Timer timer = null;
    private File file[];
    private GestureDetectorCompat GestureDetect;
    boolean run = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_switch);
        GestureDetect = new GestureDetectorCompat(this, this);
        GestureDetect.setOnDoubleTapListener(this);

        imageSwitcher();
    }


    public void imageSwitcher() {
        Bundle extras = getIntent().getExtras();

        // This create the path where images are -- String.
        String NameOfFolder = "/Presentacion";
        String path = Environment.getExternalStorageDirectory().getPath() + NameOfFolder;

        // This lists the file paths.
        File f = new File(path);
        file = f.listFiles();

        imageSwitcher = (ImageSwitcher) findViewById(R.id.imageSwitcher);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory()
        {
            public View makeView()
            {
                ImageView imageView = new ImageView(ImageSwitch.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams( ImageSwitcher.LayoutParams.FILL_PARENT, ImageSwitcher.LayoutParams.FILL_PARENT));

                return imageView;
            }
        });


        // For animations you will need to create an animation resource file.
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);
        start();
    }

    // This method change the photo every 5 seconds (you can change it).
    private void start() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    public void run()
                    {
//                        imageSwitcher.setImageResource(galeria[posicion]);
                        // Caragamos la imagen buscando el URI de la imagen en la posicion "posicion"
                        imageSwitcher.setImageURI(Uri.fromFile(file[posicion]));

                        posicion++;
                        if (posicion == file.length)
                            posicion = 0;
                    }
                });
            }
        }, 0, DURACION);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GestureDetect.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        if (run) {
            timer.cancel();
            run = false;
        } else if (!run) {
            run=true;
            start();
        }
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        this.finish();
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    //When you scroll up or down the imageSwitch closes.
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        this.finish();
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }
}
