package com.example.natan.splitconta;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class CameraActivity extends Activity implements SurfaceHolder.Callback{

    protected static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 0;
    private SurfaceView SurView;
    private SurfaceHolder camHolder;
    private boolean previewRunning;
    private Button button1;
    final Context context = this;
    public static Camera camera = null;
    private ImageView camera_image;
    Bitmap bmp,bmp1;
    private ByteArrayOutputStream bos;
    private BitmapFactory.Options options,o,o2;
    private FileInputStream fis;
    ByteArrayInputStream fis2;
    private FileOutputStream fos;
    private File dir_image2,dir_image;
    private RelativeLayout CamView;
    private TessOCR tessOCR;
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        AssetManager assetManager = getAssets();
        tessOCR = new TessOCR(assetManager);

        CamView = (RelativeLayout) findViewById(R.id.camview);

        SurView = (SurfaceView)findViewById(R.id.sview);
        if ( SurView == null)
            Log.d("SURVIEW", "EH NULL");
        camHolder = SurView.getHolder();
        camHolder.addCallback(this);
        camHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        button1 = (Button)findViewById(R.id.button_1);


        camera_image = (ImageView) findViewById(R.id.camera_image);
        //tessOCR = new TessOCR(assetManager);
        if ( bmp != null) {
            String result = tessOCR.getOCRResult(bmp);
            Toast.makeText(this, result,
                    Toast.LENGTH_SHORT).show();
            Log.d("RESULTADO", result);
        }

        button1.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v)
            {

                button1.setClickable(false);
                button1.setVisibility(View.INVISIBLE);  //<-----HIDE HERE
                camera.takePicture(null, null, mPicture);

                //EditText editText = (EditText) findViewById(R.id.edit_message);
               // String message = editText.getText().toString();
                //Handler h = new Handler();
/*                try {
                    this.wait(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds

                        Intent intent = new Intent(CameraActivity.this,BillActivity.class);
                        intent.putExtra("texto","E aí, maxo!");
                        startActivity(intent);
                    }
                }, 2000);


            }

        });


    }




    @TargetApi(Build.VERSION_CODES.ECLAIR)
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        if(previewRunning){
            camera.stopPreview();
        }
        //camera = Camera.open(0);
        if ( camera == null) Log.d("CAMERA","EH NULL");
        Camera.Parameters camParams = camera.getParameters();
        Camera.Size size = camParams.getSupportedPreviewSizes().get(0);
        camParams.setPreviewSize(size.width, size.height);
        camera.setParameters(camParams);
        try{
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            previewRunning=true;
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try{
            camera=Camera.open(0);
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();
        camera.release();
        camera=null;
    }



    public void TakeScreenshot(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int nu = preferences.getInt("image_num",0);
        nu++;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("image_num",nu);
        editor.commit();
        CamView.setDrawingCacheEnabled(true);
        CamView.buildDrawingCache(true);
        bmp = Bitmap.createBitmap(CamView.getDrawingCache());
        CamView.setDrawingCacheEnabled(false);
        bos = new ByteArrayOutputStream();
        //bmp.compress(CompressFormat.JPEG, 100, bos);
        byte[] bitmapdata = bos.toByteArray();
        fis2 = new ByteArrayInputStream(bitmapdata);

        String picId=String.valueOf(nu);
        String myfile="MyImage"+picId+".bmp";

        dir_image = new  File(Environment.getExternalStorageDirectory()+
                File.separator + "My Custom Folder");
        dir_image.mkdirs();

        try {
            File tmpFile = new File(dir_image,myfile);
            fos = new FileOutputStream(tmpFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = fis2.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
            fis2.close();
            fos.close();

            /*Toast.makeText(getApplicationContext(),
                    "The file is saved at :/My Custom Folder/"+"MyImage"+picId+".jpeg",Toast.LENGTH_LONG).show();*/
            String result = tessOCR.getOCRResult(bmp);
           Toast.makeText(this, result,
                    Toast.LENGTH_SHORT).show();
            Log.d("RESULTADO" , result);
            bmp1 = null;
            camera_image.setImageBitmap(bmp1);
            camera.startPreview();
            button1.setClickable(true);
            button1.setVisibility(View.VISIBLE);//<----UNHIDE HER
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            dir_image2 = new  File(Environment.getExternalStorageDirectory()+
                    File.separator+"My Custom Folder");
            dir_image2.mkdirs();


            File tmpFile = new File(dir_image2,"TempImage.bmp");
            try {
                fos = new FileOutputStream(tmpFile);
                fos.write(data);
                fos.close();
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
            }
            options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            bmp1 = decodeFile(tmpFile);
            bmp=Bitmap.createScaledBitmap(bmp1,CamView.getWidth(), CamView.getHeight(),true);
            camera_image.setImageBitmap(bmp);
            tmpFile.delete();
            TakeScreenshot();

        }
    };


    public Bitmap decodeFile(File f) {
        Bitmap b = null;
        try {
            // Decode image size
            o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;

            fis = new FileInputStream(f);
            BitmapFactory.decodeStream(fis, null, o);
            fis.close();
            int IMAGE_MAX_SIZE = 1000;
            int scale = 1;
            if (o.outHeight > IMAGE_MAX_SIZE || o.outWidth > IMAGE_MAX_SIZE) {
                scale = (int) Math.pow(
                        2,
                        (int) Math.round(Math.log(IMAGE_MAX_SIZE
                                / (double) Math.max(o.outHeight, o.outWidth))
                                / Math.log(0.5)));
            }

            // Decode with inSampleSize
            o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, o2);
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }


}
