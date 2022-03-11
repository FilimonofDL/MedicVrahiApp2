package com.medic.medicvrahiapp.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medic.medicvrahiapp.MyApplication;
import com.medic.medicvrahiapp.f_profil.ProfilInfoActivity;
import com.medic.medicvrahiapp.model.ComplexPreferences.ComplexPreferences;
import com.medic.medicvrahiapp.MainActivity;
import com.medic.medicvrahiapp.a_chat.MessageChatObj;
import com.medic.medicvrahiapp.b_taloni.SozdatTalonhikiInfoObject;
import com.medic.medicvrahiapp.a_chat.SpisokKonsultaciyActivity;
import com.medic.medicvrahiapp.a_chat.ChatActivity;
import com.medic.medicvrahiapp.a_chat.ChatAdapter;
import com.medic.medicvrahiapp.b_taloni.TalonhikiSozdat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class MetodiStatic {
    private static final String TALON_INFO = "talon_info";
    public static String messageAdresInFirebase = "mess";
    static ArrayList<MessageChatObj> messObjList = new ArrayList<>();
    private static String INFO_SHARE_TO_SD = "Save my info";

    //READ
    public static ArrayList<MessageChatObj> readFairBase(final ChatAdapter adapter) {

        DatabaseReference refMessIndatabase = FirebaseDatabase.getInstance().getReference(messageAdresInFirebase);
        DatabaseReference myVrahRef = refMessIndatabase;

        DatabaseReference user = refMessIndatabase.child(User.vibraniUser.getGoogleUID()).
                child(Vrahi.vrah.getGoogle_id());
        Boolean saved = false;


        myVrahRef.child(Vrahi.vrah.getGoogle_id()).
                child(User.vibraniUser.getGoogleUID())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        MessageChatObj messageChatToSend = dataSnapshot.getValue(MessageChatObj.class);

                        messObjList.add(messageChatToSend);
                        adapter.notifyDataSetChanged();
                        adapter.setLoaded();
                        ChatActivity.recSpisokChatov.smoothScrollToPosition(
                                ChatActivity.recSpisokChatov
                                        .getAdapter().getItemCount());
//                System.out.println("read FairBase");

                        System.out.println(dataSnapshot + " data");
                        System.out.println(messageChatToSend.getTextMessage() + " mess");


                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        //fetchData(dataSnapshot);
//                MessageChatObj comment = dataSnapshot.getValue(MessageChatObj.class);
//
//                messObjList.add(comment);
////                System.out.println(messObjList.size());
//                adapter.notifyDataSetChanged();
//                adapter.setLoaded();
//                System.out.println(comment.getTextMessage()+" mess");
//                System.out.println(adapter.getItemCount()+" items");

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }

                });
        return messObjList;

    }

    public static void loadUsersChata(final ProgressBar progresZapis,
                                      final List<User> userList,
                                      final UserAdapter userAdapter,
                                      final Class vizivaemiyClass) {

        final ProgressBar finalProgresZapis = progresZapis;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.SHOW_USERS), new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    System.out.println("Load Useri response " + response);
                    finalProgresZapis.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonObjectVrah = jsonObject.getJSONArray("users");

                    for (int y = 0; y < jsonObjectVrah.length(); y++) {
                        JSONObject jsonObjectVrahFor = (JSONObject) jsonObjectVrah.get(y);
                        JSONObject jsonVrahInfo = jsonObjectVrahFor.getJSONObject("user_info");
                        String id = jsonVrahInfo.getString("id");
                        String imia = jsonVrahInfo.getString("imia");
                        String othestvo = jsonVrahInfo.getString("othestvo");
                        String familia = jsonVrahInfo.getString("familia");
                        String telefon = jsonVrahInfo.getString("telefon");
                        String rojdenie = jsonVrahInfo.getString("rojdenie");
                        String google_id = jsonVrahInfo.getString("google_id");
                        JSONArray jsonArrayTokens = jsonObjectVrahFor.getJSONArray("tokens");

                        List<String> tokens = new ArrayList<>();
                        if (jsonArrayTokens.length() > 0) {
                            System.out.println("if tokens " + Integer.toString(jsonArrayTokens.length()));
//if(false) {
                            for (int d = 0; d < jsonArrayTokens.length(); d++) {

                                JSONObject jsObj = jsonArrayTokens.getJSONObject(d);
                                String data = jsObj.getString("token");
                                tokens.add(data);
                                System.out.println("tokens " + data);
                            }
                        } else {
                            System.out.println("not tokens " + Integer.toString(y + 1));
                        }

                        User user = new User(imia, othestvo, familia, "343", google_id, tokens);
                        userList.add(user);
//                       O.o(Integer.toString(zakazObjsList.size())+" size zakaz List");
                        userAdapter.setItems();
                    }
                    userAdapter.notifyDataSetChanged();
                    userAdapter.setLoaded();
                } catch (JSONException e) {
                    System.out.println("\n ERR" + response.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();//

                return spisokUserovVMoixChatax();


            }
        };
        finalProgresZapis.setVisibility(View.VISIBLE);
        RequestQueue requestQueue = Volley.newRequestQueue(progresZapis.getContext());
        requestQueue.add(stringRequest);
    }

    public static float pxToDp(final float dp) {

        return (int) (dp / Resources.getSystem().getDisplayMetrics().density);
    }

    //SAVE
    public static Boolean save(MessageChatObj mess) {
        DatabaseReference refMessIndatabase = FirebaseDatabase.getInstance().getReference(messageAdresInFirebase);
        DatabaseReference myVrahRef = refMessIndatabase;

        DatabaseReference user = refMessIndatabase.child(User.vibraniUser.getGoogleUID()).
                child(Vrahi.vrah.getGoogle_id());
        if (mess.getTextMessage().equals("")) {
//            saved=false;
        } else {

            try {
                myVrahRef.child(Vrahi.vrah.getGoogle_id()).child(User.vibraniUser.getGoogleUID()).push().setValue(mess);
                user.push().setValue(mess);

//                for(int z=0; z<MetodiStatic.vibranniyVrah.getTokens().size();z++){
//                    NotifTemp notifTemp=new NotifTemp();
//                    notifTemp.sendNotif(mess.getTextMessage(),
//                            MetodiStatic.vibranniyVrah.getTokens().get(z));
//                    System.out.println("Notif "+MetodiStatic.vibranniyVrah.getTokens().get(z));
//                }


//                saved=true;

            } catch (DatabaseException e) {
                e.printStackTrace();
//                saved=false;
            }

        }

        return true;
//        return saved;
    }

    public static void saveTalonInfoToSD(SozdatTalonhikiInfoObject infoTalona, Context context) {
        System.out.println("saveTalonInfoToSD");
//        MainActivity.tvSys.append("saveUserToSD"+"\n"+"\n");
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                context, INFO_SHARE_TO_SD, MODE_PRIVATE);
        complexPreferences.putObject(TALON_INFO, infoTalona);
        complexPreferences.commit();
    }
    public static void saveObjToSD(Object objToSave, Context context, String key) {
        System.out.println("saveObjToSD "+ Vrahi.vrah.getTelefon());

        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                context, INFO_SHARE_TO_SD, MODE_PRIVATE);
        complexPreferences.putObject(key, objToSave);
        complexPreferences.commit();
    }

    public static  void readeObjFromSD(Activity context, String key) {
        System.out.println("Reade obj "+key);
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                context, INFO_SHARE_TO_SD, MODE_PRIVATE);
        if(key.equals(Vrahi.VRAH_KEY))
            if(complexPreferences.
                getObject(key, Vrahi.class)!=null)
        Vrahi.vrah = complexPreferences.
                getObject(key, Vrahi.class);


    }

    public static SozdatTalonhikiInfoObject readTalonInfoFromSD(Activity context) {
        ComplexPreferences complexPreferences = ComplexPreferences.getComplexPreferences(
                context, INFO_SHARE_TO_SD, MODE_PRIVATE);
        SozdatTalonhikiInfoObject infoTalona = complexPreferences.getObject(TALON_INFO, SozdatTalonhikiInfoObject.class);
        System.out.println("Reade info talona " + infoTalona.getHour() + " " +
                infoTalona.getMinutes() + " " +
                infoTalona.getIntervalMin() + " " +
                infoTalona.getKolihestvo());
        return infoTalona;
    }

    public static void loadSpisokChatov() {
        DatabaseReference myVrahRef = FirebaseDatabase.getInstance().getReference(messageAdresInFirebase)
                .child(Vrahi.vrah.getGoogle_id());

        System.out.println("loadSpisokChatov " + myVrahRef.toString());
        myVrahRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SpisokKonsultaciyActivity.usersChatList.add(dataSnapshot.getKey());
                System.out.println(dataSnapshot.getKey() + " datasnapshotEventAddede ");
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                System.out.println(dataSnapshot.getKey() + " datasnapshotEventChanged ");
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private static Map<String, String> spisokUserovVMoixChatax() {
        Map<String, String> parameters = new HashMap<String, String>();
        String listToSQL = " ";
        for (int i = 0; i < SpisokKonsultaciyActivity.usersChatList.size(); i++) {
            if (i < SpisokKonsultaciyActivity.usersChatList.size() - 1) {
                listToSQL = listToSQL + "'" + SpisokKonsultaciyActivity.usersChatList.get(i) + "', ";
            } else {
                listToSQL = listToSQL + "'" + SpisokKonsultaciyActivity.usersChatList.get(i) + "' ";
            }
        }
        System.out.println("usersChat " + listToSQL);
        parameters.put("usersChat", listToSQL);
        System.out.println("Load Useri response spisokUserovVMoixChatax " + parameters.toString());
        return parameters;
    }

    public static void sendVrahTokenToSQL(final String uid, final String token) {


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.SAVE_VRAH_TOKEN_TO_MySQL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("RESP " + response);
                            JSONObject jsonObject = new JSONObject(response);
//                            boolean jsonErr=jsonObject.getBoolean("error");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERR " + error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("google_id", uid);
                parameters.put("token", token);
                System.out.println("PARAM " + parameters.toString());
                return parameters;
            }
        };

        MainActivity.requestQueue.add(stringRequest);
//        System.out.println("send to SQL "+stringRequest.toString());
    }

    public static void createTalonhikiInMySQL(Date nahaloPriema, int intervalPriema,
                                              int skolkoTalonovDobavit) {

        final JSONArray vremiaJsArr = new JSONArray();
        final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
        for (int i = 0; i < skolkoTalonovDobavit; i++) {
            String vremiaStr = Integer.toString(nahaloPriema.getYear()) + "-" +
                    Integer.toString(nahaloPriema.getMonth() + 1) + "-" +
                    Integer.toString(nahaloPriema.getDate()) + " " +
                    Integer.toString(nahaloPriema.getHours()) + ":" +
                    Integer.toString(nahaloPriema.getMinutes());
            System.out.println("Vremia str " + vremiaStr);
            vremiaJsArr.put(vremiaStr);
            long curTimeInMs = nahaloPriema.getTime();
            Date sleduheeVremia = new Date(curTimeInMs + (intervalPriema * ONE_MINUTE_IN_MILLIS));
            nahaloPriema = sleduheeVremia;

        }
        final Date finalNahaloPriema = nahaloPriema;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.INSERT_TALONHIKI_MySQL),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("RESP " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            TalonhikiSozdat.loadDatiTalonhikov(
                                    TalonhikiSozdat.calZapis);
                            TalonhikiSozdat.loadTalonhiki(finalNahaloPriema);
//                            boolean jsonErr=jsonObject.getBoolean("error");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERR " + error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("vrah_uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                parameters.put("vremiaarr", vremiaJsArr.toString());
                SimpleDateFormat dateFormatGmt = new SimpleDateFormat("ZZZZ");
//                dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
                System.out.println("GMT " + dateFormatGmt.format(new Date()) + "");


                parameters.put("gmt", dateFormatGmt.format(new Date()));
                System.out.println("PARAM Insert talon " + parameters.toString());
                return parameters;
            }
        };

        MainActivity.requestQueue.add(stringRequest);

    }

    public static void loadInfoVraha() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.LOAD_INFO_VRAHA),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("RESP loadInfoVraha " + response);
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectVrah=jsonObject.getJSONObject("vrah");
                            String familia = jsonObjectVrah.getString("familia");
                            String name = jsonObjectVrah.getString("imia");
                            String othestvo = jsonObjectVrah.getString("othestvo");
                            String google_id = jsonObjectVrah.getString("google_id");
                            String foto = jsonObjectVrah.getString("foto");
                            String specialnost = jsonObjectVrah.getString("specialnost");
                            String obrazovanie = jsonObjectVrah.getString("obrazovanie");
                            String staj = jsonObjectVrah.getString("staj");
                            Vrahi.vrah.setFamilia(familia);
                            Vrahi.vrah.setImia(name);
                            Vrahi.vrah.setOthestvo(othestvo);
                            Vrahi.vrah.setGoogle_id(google_id);
                            Vrahi.vrah.setFotoVraha(foto);
                            Vrahi.vrah.setSpecialnost(specialnost);
                            Vrahi.vrah.setObrazovanie(obrazovanie);
                            Vrahi.vrah.setStaj((staj));
                            System.out.println("Imia "+name);
//                            boolean jsonErr=jsonObject.getBoolean("error");
                            MetodiStatic.saveToMySQLUidAndToken();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERR " + error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("tel", Vrahi.vrah.getTelefon());

                System.out.println("PARAM Load info vraha " + parameters.toString());
                return parameters;
            }
        };

        MainActivity.requestQueue.add(stringRequest);
    }


    private static void saveToMySQLUidAndToken() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.SAVE_UID_AND_TOKEN),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("RESP saveToMySQLUidAndToken " + response);
                            JSONObject jsonObject = new JSONObject(response);
                            ProfilInfoActivity.updateInfoProfilPage();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERR " + error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
                parameters.put("tel", Vrahi.vrah.getTelefon());
                parameters.put("uid", firebaseUser.getUid());
                String FCMToken = null;
                try {
                    FCMToken = FirebaseInstanceId.getInstance().getToken();
                    parameters.put("token", FCMToken);


                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("PARAM Load info vraha " + parameters.toString());
                return parameters;
            }
        };

        MainActivity.requestQueue.add(stringRequest);
    }


    public static void saveFotoIn_File_SQL_STORAGE(Bitmap bitmap) {
        String name = FirebaseAuth.getInstance().getCurrentUser().getUid();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);

        File filesDir = MyApplication.getAppContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
            System.out.println("Saved foto");
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        StorageReference fotoRef = storageRef.child("images/"+
                name
                +".jpg");

        fotoRef.putFile(Uri.fromFile(imageFile))
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        System.out.println("Saved in Starage "+downloadUrl.toString());
                        Vrahi.vrah.setFotoVraha(downloadUrl.toString());
                        saveDataInSQL5param(
                                downloadUrl.toString(), "foto",
                                FirebaseAuth.getInstance().getCurrentUser().getUid(), "uid",
                                "null", "null",
                                "null", "null",
                                "null", "null",
                                "null", "null",
                                "null", "null",
                                (Utils.UPDATE_SAVE_FOTO_TO_MySQL));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
        } catch (Exception e) {

        }
    }

    public static void saveFotoIn_File_SQL_STORAGE_From_Uri(Uri uri) {
        String name = FirebaseAuth.getInstance().getCurrentUser().getUid();

        try {
           StorageReference storageRef = FirebaseStorage.getInstance().getReference();

        StorageReference fotoRef = storageRef.child("images/"+
                name
                +".jpg");

        fotoRef.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        System.out.println("Saved in Starage "+downloadUrl.toString());
                        saveDataInSQL5param(
                                downloadUrl.toString(), "foto",
                                FirebaseAuth.getInstance().getCurrentUser().getUid(), "uid",
                                "null", "null",
                                "null", "null",
                                "null", "null",
                                "null", "null",
                                "null", "null",
                                (Utils.UPDATE_SAVE_FOTO_TO_MySQL));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
        } catch (Exception e) {

        }
    }

    public static void saveDataInSQL5param(
            final String data1, final String putName1,
            final String data2, final String putName2,
            final String data3, final String putName3,
            final String data4, final String putName4,
            final String data5, final String putName5,
            final String data6, final String putName6,
            final String data7, final String putName7,
            String adres) {
System.out.println("Adres PHP "+Utils.getAdres(adres));
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
    Utils.getAdres(adres),
    new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            try {
                System.out.println("RESP save data to SQL " + response);
                JSONObject jsonObject = new JSONObject(response);
                int upd_succ_row=jsonObject.getInt("upd_succ_row");
                System.out.println("Row updatet "+Integer.toString(upd_succ_row));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("ERR " + error.toString());

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put(putName1, data1);
                parameters.put(putName2, data2);
                parameters.put(putName3, data3);
                parameters.put(putName4, data4);
                parameters.put(putName5, data5);
                parameters.put(putName6, data6);
                parameters.put(putName7, data7);
                System.out.println("PARAM " + parameters.toString());
                return parameters;
            }
        };

        MainActivity.requestQueue.add(stringRequest);
    }

    public static  String BitMapToString(Bitmap bitmap){

        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);

        return temp;
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
