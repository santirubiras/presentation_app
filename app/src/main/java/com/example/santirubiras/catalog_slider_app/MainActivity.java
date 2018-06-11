package com.example.santirubiras.catalog_slider_app;

import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1; // Request for camera use
    private static final int SELECTED_PIC = 1; //Request for gallery
    Button delete;
    boolean deleteMode = false;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        delete = findViewById(R.id.trash);
        Button buttonLoadImage = findViewById(R.id.loadimage);

        //When app is launched, the gridView shows itself.
        showGallery();

        //Here is the button to access to gallery. You can select one photo.
        buttonLoadImage.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECTED_PIC);
                // After startActivityForResult, onActivityResult is executed.
            }

        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);


        // When image is selected this is executed.
        if (requestCode == SELECTED_PIC && resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(targetUri, projection, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(projection[0]);
            String filepath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap bitmap = BitmapFactory.decodeFile(filepath);

            //Here is where image is saved. SameImage method is called.
            Save savefile = new Save();
            savefile.SaveImage(MainActivity.this, bitmap);

            // We need to refresh the gallery, so we call showGallery method.
            showGallery();

        } else

            //When an image is captured, then we save it and refresh the gridView.
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");

                Save savefile = new Save();
                savefile.SaveImage(MainActivity.this, bitmap);

                showGallery();
            }

    }

    // This is the method to take a photo with the phone camera.
    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }


    // This is the method to show the gridView.
    public void showGallery(){

        GridView gridview = findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(MainActivity.this));

        //Trash icon turns red when it is clicked. deleteMode is a Boolean.
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(deleteMode){
                    deletePhoto(position);
                } else {
                    viewPhoto(position);

                }
            }

        });

    }

    private void deletePhoto(int position) {
        ImageAdapter ia = new ImageAdapter(MainActivity.this);
        File[] rutasGV = ia.rutas();
        rutasGV[position].delete();
        showGallery();
    }

    private void viewPhoto(int position) {
        ImageAdapter ia = new ImageAdapter(MainActivity.this);
        File[] rutasGV = ia.rutas();
        String seleccionada = rutasGV[position].getAbsolutePath();
        Intent i = new Intent(MainActivity.this, FullScreenImage.class);
        i.putExtra("seleccionada", seleccionada);
        startActivity(i);
    }

    // This is the method to show a photo slider.
    public void imageSwitcher(View v) {
        Intent i = new Intent(this, ImageSwitch.class);
        startActivity(i);

    }

    public void delete(View v){
        if (!deleteMode) {
            v.setBackgroundTintList(getResources().getColorStateList(R.color.colorRed, null));
            Toast.makeText(MainActivity.this, "Haga click en la fotografía para eliminarla.",
                    Toast.LENGTH_LONG).show();
            deleteMode =true;
        } else if (deleteMode){
            v.setBackgroundTintList(getResources().getColorStateList(R.color.original, null));
            deleteMode = false;
        }
    }


}
