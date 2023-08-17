package com.example.duduhgee;
import com.example.duduhgee.BiometricActivity;
import android.content.Context;

import androidx.annotation.RawRes;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Base64;


public class base64urlEncode {

    //이 두 줄을 액티비티 파일에 넣으면 될듯.?
    byte[] serverCrtData = readRawResource(BiometricActivity.this, R.raw.bpmserver);
    String base64urlEncoded = base64UrlEncode(serverCrtData);

    public static byte[] readRawResource(Context context, @RawRes int resourceId) throws IOException {
        InputStream inputStream = context.getResources().openRawResource(resourceId);
        byte[] buffer = new byte[inputStream.available()];
        inputStream.read(buffer);
        inputStream.close();
        return buffer;
    }

    public static String base64UrlEncode(byte[] data) {
        Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
        return encoder.encodeToString(data);
    }

}


