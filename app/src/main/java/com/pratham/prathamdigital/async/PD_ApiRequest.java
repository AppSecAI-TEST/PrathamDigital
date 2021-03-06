package com.pratham.prathamdigital.async;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.pratham.prathamdigital.interfaces.VolleyResult_JSON;
import com.pratham.prathamdigital.util.PD_Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by HP on 30-12-2016.
 */

public class PD_ApiRequest {
    Context mContext;
    VolleyResult_JSON volleyResult = null;
    //    VolleyResult_String volleyResult_string = null;

    public PD_ApiRequest(Context context, VolleyResult_JSON volleyResult) {
        this.mContext = context;
        this.volleyResult = volleyResult;
    }

//    public PD_ApiRequest(Context context, VolleyResult_String volleyResult_string) {
//        this.mContext = context;
//        this.volleyResult_string = volleyResult_string;
//        progressDialog = new SpotsDialog(context, R.style.Custom);
//    }

//    private HurlStack getstack() {
//        HurlStack hurlStack = new HurlStack() {
//            @Override
//            protected HttpURLConnection createConnection(URL url) throws IOException {
//                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
//                try {
//                    httpsURLConnection.setSSLSocketFactory(getSSLSocketFactory());
//                    httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                return httpsURLConnection;
//            }
//        };
//        return hurlStack;
//    }

    // Let's assume your server app is hosting inside a server machine
    // which has a server certificate in which "Issued to" is "localhost",for example.
    // Then, inside verify method you can verify "localhost".
    // If not, you can temporarily return true
//    private HostnameVerifier getHostnameVerifier() {
//        return new HostnameVerifier() {
//            @Override
//            public boolean verify(String hostname, SSLSession session) {
//                //return true; // verify always returns true, which could cause insecure network traffic due to trusting TLS/SSL server certificates for wrong hostnames
//                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
//                return hv.verify("localhost", session);
//            }
//        };
//    }
//
//    private TrustManager[] getWrappedTrustManagers(TrustManager[] trustManagers) {
//        final X509TrustManager originalTrustManager = (X509TrustManager) trustManagers[0];
//        return new TrustManager[]{
//                new X509TrustManager() {
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return originalTrustManager.getAcceptedIssuers();
//                    }
//
//                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
//                        try {
//                            if (certs != null && certs.length > 0) {
//                                certs[0].checkValidity();
//                            } else {
//                                originalTrustManager.checkClientTrusted(certs, authType);
//                            }
//                        } catch (CertificateException e) {
//                            Log.w("checkClientTrusted", e.toString());
//                        }
//                    }
//
//                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
//                        try {
//                            if (certs != null && certs.length > 0) {
//                                certs[0].checkValidity();
//                            } else {
//                                originalTrustManager.checkServerTrusted(certs, authType);
//                            }
//                        } catch (CertificateException e) {
//                            Log.w("checkServerTrusted", e.toString());
//                        }
//                    }
//                }
//        };
//    }
//
//    private SSLSocketFactory getSSLSocketFactory()
//            throws CertificateException, KeyStoreException, IOException, NoSuchAlgorithmException, KeyManagementException {
//        CertificateFactory cf = CertificateFactory.getInstance("X.509");
////        InputStream caInput = mContext.getResources().openRawResource(R.raw.my_cert); // this cert file stored in \app\src\main\res\raw folder path
////
////        Certificate ca = cf.generateCertificate(caInput);
////        caInput.close();
//
//        KeyStore keyStore = KeyStore.getInstance("BKS");
//        keyStore.load(null, null);
////        keyStore.setCertificateEntry("ca", ca);
//
//        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
//        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
//        tmf.init(keyStore);
//
//        TrustManager[] wrappedTrustManagers = getWrappedTrustManagers(tmf.getTrustManagers());
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//        sslContext.init(null, wrappedTrustManagers, null);
//
//        return sslContext.getSocketFactory();
//    }

    public void postDataVolley(Context context, final String requestType, String url, JSONObject object) {
        try {
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObj = new JsonObjectRequest(url, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (volleyResult != null)
                        volleyResult.notifySuccess(requestType, response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (volleyResult != null) {
                        volleyResult.notifyError(requestType, error);
                        NetworkResponse response = error.networkResponse;
                        if (error instanceof ServerError && response != null) {
                            try {
                                String res = new String(response.data,
                                        HttpHeaderParser.parseCharset(response.headers));
                                // Now you can use any deserializer to make sense of data
                                Log.d("resp_err:", res);
                                JSONObject obj = new JSONObject(res);
                                Log.d("resp_errr", obj.toString());
                            } catch (UnsupportedEncodingException e1) {
                                // Couldn't properly decode data to string
                                e1.printStackTrace();
                            } catch (JSONException e2) {
                                // returned data is not JSONObject?
                                e2.printStackTrace();
                            }
                        }
                    }
                }
            }) /*{
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            }*/;
            jsonObj.setShouldCache(false);
            jsonObj.setRetryPolicy(new DefaultRetryPolicy(
                    6000,  /*timeout*/
                    3,      /*MAX_RETRIES*/
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            queue.add(jsonObj);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public void putVolleyRequest(final Context context, final String requestType, String url, JSONObject object) {
//        try {
//            if (progressDialog != null)
//                progressDialog.show();
//            RequestQueue queue = Volley.newRequestQueue(context);
//            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.PUT, url, object, new Response.Listener<JSONObject>() {
//                @Override
//                public void onResponse(JSONObject response) {
//                    if (progressDialog != null)
//                        progressDialog.dismiss();
//                    if (volleyResult_string != null)
//                        volleyResult_string.responceSuccess(requestType, response.toString());
//                }
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    if (progressDialog != null)
//                        progressDialog.dismiss();
//                    if (volleyResult_string != null) {
//                        volleyResult_string.responceError(requestType, error);
//                        NetworkResponse response = error.networkResponse;
//                        if (error instanceof ServerError && response != null) {
//                            try {
//                                String res = new String(response.data,
//                                        HttpHeaderParser.parseCharset(response.headers));
//                                 Now you can use any deserializer to make sense of data
//                                Log.d("resp_err:", res);
//                                JSONObject obj = new JSONObject(res);
//                                Log.d("resp_errr", obj.toString());
//                            } catch (UnsupportedEncodingException e1) {
//                                 Couldn't properly decode data to string
//                                e1.printStackTrace();
//                            } catch (JSONException e2) {
//                                 returned data is not JSONObject?
//                                e2.printStackTrace();
//                            }
//                        }
//                    }
//                }
//            }) {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    HashMap<String, String> headers = new HashMap<String, String>();
//                    FE_SharedPreference preference = new FE_SharedPreference(context, null);
//                    FE_Utility.DEBUG_LOG(1, "token::", preference.getStringValue(FE_Constant.PREF_KEY_USER_TOKEN, "no_token"));
//                    headers.put("Authorization", "Token " + preference.getStringValue(FE_Constant.PREF_KEY_USER_TOKEN, "no_token"));
//                    headers.put("Content-Type", "application/x-www-form-urlencoded");
//                    return headers;
//                }
//            };
//            jsonObj.setShouldCache(false);
//            jsonObj.setRetryPolicy(new DefaultRetryPolicy(
//                    6000,  /*timeout*/
//                    3,      /*MAX_RETRIES*/
//                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
//            ));
//
//            queue.add(jsonObj);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void getDataVolley(final String requestType, String url) {
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            PD_Utility.DEBUG_LOG(1, "volley_url::", url);
            StringRequest jsonObj = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (volleyResult != null)
                        volleyResult.notifySuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (volleyResult != null)
                        volleyResult.notifyError(requestType, error);
                }
            });
            jsonObj.setRetryPolicy(new DefaultRetryPolicy(
                    6000,  /*timeout*/
                    3,      /*MAX_RETRIES*/
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));
            queue.add(jsonObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
