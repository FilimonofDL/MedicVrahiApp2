package com.medic.medicvrahiapp.a_chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;


import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.InitOsnovnActivityBottomButton;
import com.medic.medicvrahiapp.model.MetodiStatic;
import com.medic.medicvrahiapp.model.User;
import com.medic.medicvrahiapp.model.UserAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpisokKonsultaciyActivity extends InitOsnovnActivityBottomButton {
    public static List<String> usersChatList =new ArrayList<>();
     RecyclerView recSpisokKonsultaciy;
    UserAdapter adapterSpisokKonsultaciy;
    List<User> konsultVrahiList;
    ProgressBar progrsVseKonsultacii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spisok_konsultaciy);

        recSpisokKonsultaciy=findViewById(R.id.recSpisokKonsultaciy);
        progrsVseKonsultacii =findViewById(R.id.progrsVseKonsultacii);

        konsultVrahiList=new ArrayList<>();
        recSpisokKonsultaciy.setLayoutManager(new LinearLayoutManager(this));
        adapterSpisokKonsultaciy =new UserAdapter(recSpisokKonsultaciy, this,
                konsultVrahiList);
        recSpisokKonsultaciy.setAdapter(adapterSpisokKonsultaciy);

        MetodiStatic.loadUsersChata(progrsVseKonsultacii, konsultVrahiList,
                adapterSpisokKonsultaciy, this.getClass());



    }
}
