package com.medic.medicvrahiapp.a_chat;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.medic.medicvrahiapp.model.InitOsnovnActivityBottomButton;
import com.medic.medicvrahiapp.model.MetodiStatic;
import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.User;

import java.util.ArrayList;
import java.util.List;



public class ChatActivity extends InitOsnovnActivityBottomButton {
    EditText etMess;

    public static List<MessageChatObj> mesList;
    public  static RecyclerView recSpisokChatov;
    ChatAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        recSpisokChatov=findViewById(R.id.recSpisokChatov);
        etMess=findViewById(R.id.etMess);
        recSpisokChatov.setLayoutManager(new LinearLayoutManager(this));
        mesList= new ArrayList<>();
        adapter=new ChatAdapter(recSpisokChatov);
        recSpisokChatov.setAdapter(adapter);
        mesList= MetodiStatic.readFairBase(adapter);
    }
    public void chatButtonClic(View v) {
        if(!etMess.getText().toString().equals("")) {
            MessageChatObj messageChatObj = new MessageChatObj(etMess.getText().toString());
            MetodiStatic.save(messageChatObj);
            System.out.println("User tokens size "+Integer.toString(User.vibraniUser.getTokens().size()));
            for(int z=0; z<User.vibraniUser.getTokens().size();z++){
                Notifiki notifiki =new Notifiki();
                notifiki.sendNotif(etMess.getText().toString(),
                        User.vibraniUser.getTokens().get(z));
                System.out.println("Notif "+User.vibraniUser.getTokens().get(z));
            }

            etMess.setText("");
        }

    }
}
