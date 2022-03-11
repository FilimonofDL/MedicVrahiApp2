package com.medic.medicvrahiapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.medic.medicvrahiapp.a_chat.SpisokKonsultaciyActivity;
import com.medic.medicvrahiapp.model.ComplexPreferences.ComplexPreferences;
import com.medic.medicvrahiapp.model.MetodiStatic;
import com.medic.medicvrahiapp.model.Utils;
import com.medic.medicvrahiapp.model.Vrahi;

public class MainActivity extends AppCompatActivity {
    public static AppCompatActivity activityThisMainActiv;

    public static RequestQueue requestQueue;
    EditText etMess;
    Button btSend;
    public  static TextView tvUid, tvToken;
    boolean firstLaunch= true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        activityThisMainActiv=this;
        etMess=findViewById(R.id.etMess);
        btSend=findViewById(R.id.btSend);
        tvUid=findViewById(R.id.tvUid);
        tvToken=findViewById(R.id.tvToken);
        final Intent intent=new Intent(this, SpisokKonsultaciyActivity.class);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseConfig();


    }

    private void firebaseConfig() {
        final FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        initConfigData(mFirebaseRemoteConfig);
        long cacheExpiration = 3600;
        mFirebaseRemoteConfig.fetch(cacheExpiration)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        System.out.println( "Fetch complete");
                        MetodiStatic.loadSpisokChatov();
                        zagruzitInfuOVrahe();
                        if (task.isSuccessful()) {
                            System.out.println( "Fetch Succeeded");
                            btSend.performClick();
                            mFirebaseRemoteConfig.activateFetched();
                            System.out.println("Config "+mFirebaseRemoteConfig.getString("ip_adres"));
                            initConfigData(mFirebaseRemoteConfig);
                        } else {
                            System.out.println("Config not sync "+task.getException().toString());
                        }
                    }
                });
    }

    private void initConfigData(FirebaseRemoteConfig mFirebaseRemoteConfig) {

        Utils.ipAdresConfig=(mFirebaseRemoteConfig.getString("ip_adres"));
        Utils.papkiAdres=(mFirebaseRemoteConfig.getString("adres_papki_php_files"));
    }

    private void zagruzitInfuOVrahe() {
        if (firstLaunch) {
            MetodiStatic.readeObjFromSD(MainActivity.this, Vrahi.VRAH_KEY);
            if(FirebaseAuth.getInstance().getCurrentUser()!=null&&
                    FirebaseAuth.getInstance().getCurrentUser().getUid()!=null)
                Vrahi.vrah.setGoogle_id(FirebaseAuth.getInstance().getCurrentUser().getUid());
            firstLaunch=false;
        }
    }


}
