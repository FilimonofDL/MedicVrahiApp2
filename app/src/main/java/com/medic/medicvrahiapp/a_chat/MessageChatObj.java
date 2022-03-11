package com.medic.medicvrahiapp.a_chat;



import com.medic.medicvrahiapp.model.Vrahi;

import java.util.Date;

public class MessageChatObj {

    private String textMessage;
    private String autor;
    private Date timeMessage;



    public MessageChatObj(String textMessage) {
        this.textMessage = textMessage;
        this.autor = Vrahi.vrah.getGoogle_id();

        timeMessage = new Date();
    }

    public MessageChatObj() {//НЕ ЗА ЧТО НЕ УДАЛЯТЬ!!!
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getTimeMessage() {
        return timeMessage;
    }

    public void setTimeMessage(Date timeMessage) {
        this.timeMessage = timeMessage;
    }
}