package com.example.duduhgee;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;

public class MainActivity extends AppCompatActivity {

    private TextView tv_id;
    private Button btn_info;

    //private static final String KEY_NAME = userID;
    private KeyStore keyStore;
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private BiometricPrompt.AuthenticationCallback authenticationCallback;
    private CancellationSignal cancellationSignal = null;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_id = findViewById(R.id.tv_id);
        btn_info = findViewById(R.id.btn_info);

        ImageView imageViewClickable = findViewById(R.id.imageViewClickable);
        Intent intent = getIntent();
        String userID = intent.getStringExtra("userID");

        imageViewClickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the new activity when the ImageView is clicked
                Intent intent = new Intent(MainActivity.this, RP_BuyActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });

        // 회원정보 버튼
        btn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                String userID = intent.getStringExtra("userID");

                intent = new Intent(MainActivity.this, BiometricActivity.class);
                intent.putExtra("userID", userID);
                startActivity(intent);
            }
        });
        tv_id.setText(userID);
    }


    private void notifyUser(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    private CancellationSignal getCancellationSignal() {
        cancellationSignal = new CancellationSignal();
        cancellationSignal.setOnCancelListener(() -> notifyUser("Authentication was Cancelled by the user"));
        return cancellationSignal;
    }

    private boolean checkPrivateKeyAccess(String keyAlias) {
        try {
            KeyStore keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);

            return keyStore.entryInstanceOf(keyAlias, KeyStore.PrivateKeyEntry.class);
        } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException |
                 IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.P)
    public Boolean checkBiometricSupport() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        if (!keyguardManager.isDeviceSecure()) {
            notifyUser("Fingerprint authentication has not been enabled in settings");
            return false;
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            notifyUser("Fingerprint Authentication Permission is not enabled");
            return false;
        }
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            return true;
        } else {
            return false;
        }
    }

}