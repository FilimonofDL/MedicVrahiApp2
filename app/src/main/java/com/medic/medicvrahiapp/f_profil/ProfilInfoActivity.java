package com.medic.medicvrahiapp.f_profil;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.InitOsnovnActivityBottomButton;
import com.medic.medicvrahiapp.model.Vrahi;
import com.squareup.picasso.Picasso;

public class ProfilInfoActivity extends InitOsnovnActivityBottomButton {
    static TextView tvFIOuser;
    static TextView tvTelUser;
    static TextView tvObrazovanie;
    static TextView tvStaj;
    public static ImageView ivProfilInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_page);
        tvFIOuser = findViewById(R.id.tvFIOuser);
        tvTelUser = findViewById(R.id.tvTelUser);
        tvObrazovanie = findViewById(R.id.tvObrazovanie);
        tvStaj = findViewById(R.id.tvStaj);
        ivProfilInfo = findViewById(R.id.ivProfilInfo);

        updateInfoProfilPage();
    }

    public static void updateInfoProfilPage() {
        tvFIOuser.setText(Vrahi.vrah.getFamilia() + " \n" + Vrahi.vrah.getImia() + " " +
                Vrahi.vrah.getOthestvo());
        tvTelUser.setText(Vrahi.vrah.getSpecialnost());
        tvObrazovanie.setText(Vrahi.vrah.getObrazovanie());
        tvStaj.setText((Vrahi.vrah.getStaj())+" г.");
        if(!Vrahi.vrah.getFotoVraha().equals(""))
        Picasso.get()
                .load(Vrahi.vrah.getFotoVraha())
                .into(ivProfilInfo);
    }
    public void editProfilClick(View v ){
        Intent intent = new Intent(this, EditUserInfoActivity.class);
        startActivity(intent);
    }

    public void viytiIzAccount(View v ){
        FirebaseAuth.getInstance().signOut();
        Vrahi.vrah.setImia("");
        Vrahi.vrah.setOthestvo("");
        Vrahi.vrah.setFamilia("");
        Vrahi.vrah.setObrazovanie("");
        Vrahi.vrah.setStaj("");
        Vrahi.vrah.setFotoVraha("");
        Vrahi.vrah.setSpecialnost("");
        Vrahi.vrah.setStepen("");
        Intent intent2 = new Intent(this, RegistrationTelefonActivity.class);
        startActivity(intent2);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateInfoProfilPage();
    }
}

