package com.android.sqsoft.sunshine.logic;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Logic {
    protected final Context context;
    public RequestQueue requestQueue;

    public Logic(Context context) {
        this.context = context;
    }

    protected RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public interface Listener<T> {
        void onResult(T object);
        void onError(String errorMessage);
    }
}
