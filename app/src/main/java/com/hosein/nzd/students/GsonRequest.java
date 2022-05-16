package com.hosein.nzd.students;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

public class GsonRequest<T> extends Request<T> {

    Gson gson = new Gson();
    Response.Listener<T> listener;
    Type type;
    JSONObject jsonObject;

    public GsonRequest(int method, String url , Type type, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.listener = listener;
        this.type = type;
    }

    public GsonRequest(int method, String url , Type type,JSONObject jsonObject, Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener) {
        this(method , url , type , listener , errorListener);
        this.jsonObject = jsonObject;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse networkResponse) {

        try {
            String parseJson = new String(networkResponse.data);
            T response = gson.fromJson(parseJson , type);
            return Response.success(response , HttpHeaderParser.parseCacheHeaders(networkResponse));
        }catch (Exception e){
            return Response.error(new VolleyError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (jsonObject == null)
            return super.getBody();
        else
            return jsonObject.toString().getBytes();
    }

    @Override
    public String getBodyContentType() {
        return "application/json";
    }
}
