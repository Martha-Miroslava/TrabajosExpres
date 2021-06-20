package com.example.trabajosexpres.HTTPRequest;
import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HTTPRequest extends JsonObjectRequest {
    private static String token = "";
    private static String cookies = "";

    public HTTPRequest(int method, String url, @Nullable JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                       @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.setRetryPolicy(new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String json = new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers, PROTOCOL_CHARSET));
            JSONObject jsonObject = new JSONObject();
            if (json != null && json.length() > 0) {
                jsonObject = new JSONObject(json);
            }
            jsonObject.put("status", networkResponse.statusCode);
            jsonObject.put("headers", new JSONObject(networkResponse.headers));
            JSONObject headers = (JSONObject) jsonObject.get("headers");
            if (headers.has("Set-Cookies")) {
                HTTPRequest.cookies = String.valueOf(headers.get("Set-Cookie"));
            }
            return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() {
        Map<String, String> map = new HashMap<>();
        if (!HTTPRequest.cookies.equals("")) {
            map.put("Cookie", HTTPRequest.cookies);
        }
        if (!HTTPRequest.token.equals("")) {
            map.put("Token", HTTPRequest.token);
        }
        return map;
    }
}