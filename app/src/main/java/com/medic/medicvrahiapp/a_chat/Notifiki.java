package com.medic.medicvrahiapp.a_chat;


import android.util.Log;

import com.medic.medicvrahiapp.model.Vrahi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Notifiki {


    MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .addHeader("Content-Type","application/json")
                .addHeader("Authorization","key=AIzaSyC7HZmp7bJ093TSXYAiEIzNsBuLpZNoPEM")
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
    public  void sendNotif(String text, String token){
        try {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("to", token);


            jsonObject.put("collapse_key", "type_a");

            JSONObject paramNotif = new JSONObject();
            paramNotif.put("body", text);
            paramNotif.put("title", Vrahi.vrah.getImia()+" "+
            Vrahi.vrah.getOthestvo());

            paramNotif.put("click_action", "a2");
            jsonObject.put("notification", paramNotif);

            JSONObject paramData = new JSONObject();
            paramData.put("body", text);
            paramData.put("title", Vrahi.vrah.getImia()+" "+
                    Vrahi.vrah.getOthestvo());
            paramData.put("uid", Vrahi.vrah.getGoogle_id());
            paramData.put("otkogo", Vrahi.vrah.getGoogle_id());
            jsonObject.put("data", paramData);

            System.out.println("JSON Notif send to Notif "+jsonObject.toString());
            post("https://fcm.googleapis.com/fcm/send", jsonObject.toString(), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            //Something went wrong
                            System.out.println("Notif ERROR "+e.toString());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            if (response.isSuccessful()) {
                                String responseStr = response.body().string();
                                System.out.println("Response "+ responseStr);
                                // Do what you want to do with the response.
                            } else {
                                // Request not successful
                            }
                        }
                    }
            );
        } catch (JSONException ex) {
            Log.d("Exception", "JSON exception", ex);
        }
    }

}
