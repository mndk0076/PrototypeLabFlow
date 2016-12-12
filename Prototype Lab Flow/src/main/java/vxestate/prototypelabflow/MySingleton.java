package vxestate.prototypelabflow;
//Kenneth Mendoza(N00598007)
//Sukhdeep Sehra (N01046228)
//Matheus Almeida
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {
    private static MySingleton myInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private MySingleton(Context context){
        mCtx = context;
        requestQueue = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }

    public static synchronized MySingleton getMyInstance(Context context){
        if(myInstance == null){
            myInstance = new MySingleton(context);
        }
        return myInstance;
    }

    public <T> void addToRequestque(Request<T> request){
        requestQueue.add(request);
    }

}
