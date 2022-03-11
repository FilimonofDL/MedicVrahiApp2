package com.medic.medicvrahiapp.model;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.medic.medicvrahiapp.MainActivity;

public class Vrahi {
    public static final String VRAH_KEY = "vrah";
    public static Vrahi vrah=new Vrahi();
    private String id="1";
    private String familia ="1";
    private String imia="1";
    private String othestvo="1";
    private String specialnost="1";
    private String staj="1";

    private int zapisonline=100;
    private String stepen="1";
    private String obrazovanie="1";
    private String telefon="+1";
    private String google_id = "1";
    private String fotoVraha="1";


    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        System.out.println("Set telefon "+telefon);
        this.telefon = telefon;
    }



    public static void loginAnonimuthGetUid(final Activity context){
        Vrahi.vrah=new Vrahi();
        final FirebaseUser[] firebaseUser = new FirebaseUser[1];
        FirebaseAuth.getInstance().signInAnonymously()
                .addOnCompleteListener(context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            User.user.setGoogleUID(FirebaseHelper.userFb.getUid());
                            firebaseUser[0] =FirebaseAuth.getInstance().getCurrentUser();
                            MainActivity.tvUid.setText(firebaseUser[0].getUid().toString());
                            vrah.setGoogle_id(firebaseUser[0].getUid().toString());
                            System.out.println("UID "+firebaseUser[0].getUid().toString());
//                            saveUserToSD(user, MainActivity.activityThisMainActiv);
//                            System.out.println(User.user.getUid()+" UID");
//                            MainActivity.ibProfil.performClick();
                            String FCMToken= null;
                            try {
                                FCMToken = FirebaseInstanceId.getInstance().getToken();
                                /** Store this token to firebase database along with user id **/
                              MainActivity.tvToken.setText(FCMToken.toString());
//                                System.out.println("token "+FCMToken.toString());
                                MetodiStatic.sendVrahTokenToSQL(firebaseUser[0].getUid().toString(), FCMToken);
                            } catch (Exception e) {
                                e.printStackTrace();
                                MainActivity.tvToken.setText(e.toString());
                            }
                        } else {
                            MainActivity.tvUid.setText("NU LL");
                        }
                        MetodiStatic.loadSpisokChatov();
                    }
                });
    }

    public String getGoogle_token() {
        return google_token;
    }

    public void setGoogle_token(String google_token) {
        this.google_token = google_token;
    }

    String google_token="1";

    public String getGoogle_id() {
        return google_id;
    }

    public void setGoogle_id(String google_id) {
        this.google_id = google_id;
    }

    public Vrahi() {
    }

    public int getZapisonline() {
        return zapisonline;
    }

    public void setZapisonline(int zapisonline) {
        this.zapisonline = zapisonline;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStaj() {
        return staj;
    }

    public void setStaj(String staj) {
        this.staj = staj;
    }

    public String getStepen() {
        return stepen;
    }

    public void setStepen(String stepen) {
        this.stepen = stepen;
    }

    public String getObrazovanie() {
        return obrazovanie;
    }

    public void setObrazovanie(String obrazovanie) {
        this.obrazovanie = obrazovanie;
    }




    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familiya) {
        this.familia = familiya;
    }

    public String getImia() {
        return imia;
    }

    public void setImia(String imia) {
        this.imia = imia;
    }

    public String getOthestvo() {
        return othestvo;
    }

    public void setOthestvo(String othestvo) {
        this.othestvo = othestvo;
    }

    public String getSpecialnost() {
        return specialnost;
    }

    public void setSpecialnost(String specialnost) {
        this.specialnost = specialnost;
    }

    public String getFotoVraha() {
        return fotoVraha;
    }

    public void setFotoVraha(String fotoVraha) {
        this.fotoVraha = fotoVraha;
    }
}
