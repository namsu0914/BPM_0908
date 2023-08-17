
package com.example.duduhgee;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

public class SignatureActivity extends AppCompatActivity {
    private static final String TAG = "SignatureActivity";
    //private static final String KEY_NAME = "my_key";

    public byte[] signChallenge(String challenge, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(challenge.getBytes());
            byte[] encodedSignature = signature.sign();
            String signedChallenge = Base64.encodeToString(encodedSignature, Base64.NO_WRAP);

            return encodedSignature;
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            Log.d(TAG, "Exception occurred: " + e.getMessage(), e); // Log the exception
            return null;
        }
    }

    // Other methods and code within the SignatureActivity class
}

