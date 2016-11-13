package br.com.thiengo.webviewusersignup.domain;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import br.com.thiengo.webviewusersignup.extras.Util;

/**
 * Created by viniciusthiengo on 12/11/16.
 */

public class ImageJS extends Observable {
    private Uri uri;
    private String base64;


    ImageJS( Observer observer ){
        addObserver( observer );
    }

    public void setUri( String path ){
        uri = Uri.parse(path);
    }

    /*public Uri getUri(){
        return uri;
    }*/

    public File getAsFile(){
        return new File( uri.toString() );
    }

    public String getBase64(){
        return base64;
    }

    public void generateBase64(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                if( !Util.isPreLollipop() ){
                    Bitmap bitmap = generateBitmap();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] byteArray = byteArrayOutputStream.toByteArray();
                    String imgageBase64 = Base64.encodeToString(byteArray, Base64.DEFAULT);
                    base64 = "data:image/png;base64," + imgageBase64;
                }

                setChanged();
                notifyObservers();
            }
        }).start();
    }

    private Bitmap generateBitmap(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inJustDecodeBounds = false;
        //options.inSampleSize = 10;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile( uri.toString(), options );
        //bitmap.recycle();
        return bitmap;

        /*try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(uri.toString()),null,o);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale++;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(uri.toString()), null, o2);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;*/
    }
}
