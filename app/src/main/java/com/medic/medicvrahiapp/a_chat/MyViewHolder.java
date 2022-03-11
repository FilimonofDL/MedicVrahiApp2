package com.medic.medicvrahiapp.a_chat;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.Vrahi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class MyViewHolder extends RecyclerView.ViewHolder  {

   TextView tvTextChatUser, tvDateChatUser, tvTextChatVrah, tvDateChatVrah;
   ConstraintLayout conMesUser, conMesVrah;

   public MyViewHolder(View itemView) {
       super(itemView);

       tvTextChatUser =(TextView) itemView.findViewById(R.id.tvTextChatUser);
       tvDateChatUser=(TextView) itemView.findViewById(R.id.tvDateChatUser);
       tvTextChatVrah =(TextView) itemView.findViewById(R.id.tvTextChatVrah);
       tvDateChatVrah=(TextView) itemView.findViewById(R.id.tvDateChatVrah);
       conMesUser= itemView.findViewById(R.id.conMesUser);
       conMesVrah= itemView.findViewById(R.id.conMesVrah);
   }

    public void bind(final MessageChatObj messageChat) {

        SimpleDateFormat simpleDateFormatAll=new SimpleDateFormat("d MMMM yy  H:mm");
        SimpleDateFormat simpleDateFormatDay=new SimpleDateFormat("d MMMM  H:mm");
        SimpleDateFormat simpleDateFormatHour=new SimpleDateFormat("H:mm");
        Date dateNow=new Date();
        SimpleDateFormat farmatToChange;
        if(messageChat.getTimeMessage().getDay()==dateNow.getDay()){
               farmatToChange=simpleDateFormatHour;
        }else if (messageChat.getTimeMessage().getYear()==dateNow.getYear()){
            farmatToChange=simpleDateFormatDay;
        }else {
            farmatToChange=simpleDateFormatAll;
        }

//        if(true){
        if(messageChat.getAutor().equals(Vrahi.vrah.getGoogle_id())){
            System.out.println("IF BIND "+messageChat.getAutor().toString());
           conMesUser.setVisibility( View.VISIBLE);
           conMesVrah.setVisibility( View.GONE);
//            System.out.println("Visib user");
            tvTextChatUser.setText(messageChat.getTextMessage());
            Calendar cal= Calendar.getInstance();
            cal.setTimeInMillis(messageChat.getTimeMessage().getTime());
            tvDateChatUser.setText(farmatToChange.format(messageChat.getTimeMessage()));
        } else {
            conMesVrah.setVisibility( View.VISIBLE);
            conMesUser.setVisibility( View.GONE);
//             System.out.println("Visib vrah");
            tvTextChatVrah.setText(messageChat.getTextMessage());
            Calendar cal= Calendar.getInstance();
            cal.setTimeInMillis(messageChat.getTimeMessage().getTime());
            tvDateChatVrah.setText(farmatToChange.format(messageChat.getTimeMessage()));
        }

    }
}