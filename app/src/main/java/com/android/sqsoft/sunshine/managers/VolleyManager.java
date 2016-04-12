package com.android.sqsoft.sunshine.managers;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by Pedro on 11/04/2016.
 */
public class VolleyManager {
    public static VolleyManager volleyManager;
    private final Context context;
    private final ImageLoader imageLoader;
    private RequestQueue requestQueue;

    private VolleyManager(Context context) {
        this.context = context;

        requestQueue = getRequestQueue();

        imageLoader = new ImageLoader(requestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String,Bitmap> cache = new LruCache<>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url,bitmap);
                    }
                });

    }

    public synchronized static VolleyManager getInstance(Context context){
        if(volleyManager == null){
            volleyManager = new VolleyManager(context);
        }

        return volleyManager;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue==null) {
            requestQueue = Volley.newRequestQueue(this.context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

}
