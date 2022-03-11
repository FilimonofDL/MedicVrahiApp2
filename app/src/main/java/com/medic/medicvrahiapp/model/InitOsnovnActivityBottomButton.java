package com.medic.medicvrahiapp.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.a_chat.SpisokKonsultaciyActivity;
import com.medic.medicvrahiapp.c_vopros.VoprosPageActivity;
import com.medic.medicvrahiapp.b_taloni.TalonhikiSozdat;
import com.medic.medicvrahiapp.f_profil.ProfilInfoActivity;
import com.medic.medicvrahiapp.f_profil.RegistrationTelefonActivity;

public class InitOsnovnActivityBottomButton extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_bar);


    }

    public void chatButtonClick(View v) {
        System.out.println("chatButtonClick");
        final Intent intent = new Intent(this, SpisokKonsultaciyActivity.class);
        startActivity(intent);
        finish();
    }

    public void voprosButton(View v) {
        final Intent intent = new Intent(this, VoprosPageActivity.class);
        startActivity(intent);
        finish();
    }

    public void talonhikiSozdatClikck(View v) {
        System.out.println("talonhikiSozdatClikck");
        Intent intent = new Intent(this, TalonhikiSozdat.class);
        startActivity(intent);
        finish();
    }

    public void profilPage(View v) {
        Intent intent = new Intent(this, RegistrationTelefonActivity.class);
        Intent intent2 = new Intent(this, ProfilInfoActivity.class);
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            startActivity(intent);
            System.out.println("registr");
        } else {
            startActivity(intent2);
            System.out.println("profil info");
        }
        finish();

    }


}
