package com.mila.rationhelper.Helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class DownloadImage extends AsyncTask<String,Void, Bitmap>
{
    private WeakReference<ImageView> imageView; //weak reference to prevent memory leak

    public DownloadImage(ImageView imageView)
    {
        this.imageView = new WeakReference<>(imageView);
    }



    /*
        doInBackground(Params... params)
            Override this method to perform a computation on a background thread.
     */
    protected Bitmap doInBackground(String... urls)
    {
        String urlOfImage = urls[0];
        Log.d("DetailsAdapter", "Downloading image at address " + urlOfImage);
        Bitmap logo = null;
        try
        {
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        } catch (Exception e) { // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }



    /*
        onPostExecute(Result result)
            Runs on the UI thread after doInBackground(Params...).
     */
    protected void onPostExecute(Bitmap result)
    {
        if (imageView != null && result != null)
        {
            final ImageView image = imageView.get();
            if (image != null)
            {
                image.setImageBitmap(result);
            }
        }
    }
}