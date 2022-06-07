package com.example.myapplication.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    private ImageView bmImage;
    public DownloadImage(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bm = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bm = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.d("Error", e.getStackTrace().toString());
        }
        return bm;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
