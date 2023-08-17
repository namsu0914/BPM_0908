package com.example.duduhgee.rp;

import static com.example.duduhgee.rp.RP_RegisterRequest.getPinnedCertSslSocketFactory;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.duduhgee.R;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class RP_BuyRequest extends StringRequest {
    final static private String URL = "https://192.168.0.2:443/SendChallenge.php";

    public RP_BuyRequest(Response.Listener<String> listener, Context context) throws CertificateException, IOException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        super(Method.GET, URL, listener, null);

        SSLSocketFactory sslSocketFactory = getPinnedCertSslSocketFactory(context, R.raw.bpmserver);
        HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);

        HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }
        });
    }

}
