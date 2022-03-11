package com.medic.medicvrahiapp.f_profil;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.medic.medicvrahiapp.MainActivity;
import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.InitOsnovnActivityBottomButton;
import com.medic.medicvrahiapp.model.MetodiStatic;
import com.medic.medicvrahiapp.model.Utils;
import com.medic.medicvrahiapp.model.Vrahi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditUserInfoActivity extends InitOsnovnActivityBottomButton {
    int DIALOG_DATE = 1;
    TextView tvDateIzmenitRojdenie;
    EditText etImiaIzmenit, etOthestvoIzmenit, etFamiliaIzmenit, etObrazovanieIzmenit, etStajIzmenit;
    private static final int CAMERA_REQUEST = 0;
    private static final int FILE_SELECT_CODE = 1;
    ImageView ivVraha;
    static int spinerPositSpecialnost=0;
    static String spinerPositSpecialnostString="";

    Button btSaveIzmenitInfoUser;
    Bitmap thumbnailBitmap;
    AppCompatActivity activityThis;
    ConstraintLayout conEditPof;
    ProgressBar prEditProfil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profil_edit_info_user_activity);

        activityThis=this;

        conEditPof =  findViewById(R.id.conEditPof);
        prEditProfil =  findViewById(R.id.prEditProfil);
        ivVraha =  findViewById(R.id.ivVraha);
        etImiaIzmenit = (EditText) findViewById(R.id.etImiaIzmenit);
        etOthestvoIzmenit = (EditText) findViewById(R.id.etOthestvoIzmenit);
        etFamiliaIzmenit = (EditText) findViewById(R.id.etFamiliaIzmenit);
        etObrazovanieIzmenit = (EditText) findViewById(R.id.etObrazovanieIzmenit);
        etStajIzmenit = (EditText) findViewById(R.id.etStajIzmenit);
        btSaveIzmenitInfoUser=findViewById(R.id.btSaveIzmenitInfoUser);
        Picasso.get()
                    .load(Vrahi.vrah.getFotoVraha())
                    .into(ivVraha);

        etImiaIzmenit.setText(Vrahi.vrah.getImia());
        etOthestvoIzmenit.setText(Vrahi.vrah.getOthestvo());
        etFamiliaIzmenit.setText(Vrahi.vrah.getFamilia());
        etObrazovanieIzmenit.setText(Vrahi.vrah.getObrazovanie());
        etStajIzmenit.setText(Vrahi.vrah.getStaj());
        final Activity thisActiv=this;

loadSpecialnostVrahey(this);

    }


    public void ivFotoVrahaClick(View v){
createAlertEmailChange(this).show();
    }


    public static AlertDialog createAlertEmailChange(final Activity activity) {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        final View view = layoutInflater.inflate(R.layout.ask_camera_or_file, null);
        Button btSdelatFoto=view.findViewById(R.id.btSdelatFoto);
        Button btVibratFoto=view.findViewById(R.id.btVibratFoto);
        final AlertDialog alertF = new AlertDialog.Builder(activity).create();
        btSdelatFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Click");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                File file = new File(Environment.getExternalStorageDirectory(),
//                        "test.jpg");
//                Uri outputFileUri = Uri.fromFile(file);
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                alertF.dismiss();
            }
        });
        btVibratFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(activity);
                alertF.dismiss();
            }
        });

        alertF.setTitle(activity.getResources().getString(R.string.vibor_foto));
//        alertF.setCancelable(false);
//        alertF.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                System.out.println("Dialog");
////                if (emailToChange.getText().toString() != null && !emailToChange.getText().toString().equals("")) {
////                    FirebaseHelper.changeEmailGoogleID(activityThisMainActiv, emailToChange.getText().toString());
////                }
//            }
//        });
//
//
//        alertF.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                alertDialog.dismiss();
//            }
//        });


        alertF.setView(view);
        return alertF;
    }
    // отображаем диалоговое окно для выбора даты
    public void setDate() {
//        dateAndTime.set(1980, 01,01);
//        DatePickerDialog dtP=
//                new DatePickerDialog(this, d,
//                        dateAndTime.get(Calendar.YEAR),
//                        dateAndTime.get(Calendar.MONTH),
//                        dateAndTime.get(Calendar.DAY_OF_MONTH))
//                ;
//        dtP.show();



    }

    // отображаем диалоговое окно для выбора времени
    public void setTime() {

//        new TimePickerDialog(this, t,
//                dateAndTime.get(Calendar.HOUR_OF_DAY),
//                dateAndTime.get(Calendar.MINUTE), true)
//                .show();

    }
    // установка начальных даты и времени


    private static void showFileChooser(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            activity.startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(activity, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Act RESULT "+data.toString()+requestCode+" "+resultCode);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем картинку
            thumbnailBitmap = (Bitmap) data.getExtras().get("data");
            ivVraha.setImageBitmap(thumbnailBitmap);
        }
        if(requestCode == FILE_SELECT_CODE && resultCode == RESULT_OK){
                // Get the Uri of the selected file
                Uri uri = data.getData();
            try {
                thumbnailBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ivVraha.setImageBitmap(thumbnailBitmap);
                MetodiStatic.saveFotoIn_File_SQL_STORAGE_From_Uri(uri);
                System.out.println("Uri"+uri.toString());

                // Get the path
                String path = null;
                try {
                    path = MetodiStatic.getPath(this, uri);
                    System.out.println( "File Uri: " + uri.toString());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                System.out.println( "File Path: " + path);
                // Get the file instance
                // File file = new File(path);
                // Initiate the upload

        }
    }

    public void profilEditSaveClick(View v){
        System.out.println("btSaveIzmenitInfoUser Click");
        conEditPof.setVisibility(View.GONE);
        prEditProfil.setVisibility(View.VISIBLE);

        if(etImiaIzmenit.getText().toString().length()>0) {
            Vrahi.vrah.setImia(etImiaIzmenit.getText().toString());
        }
        if(etOthestvoIzmenit.getText().length()>0) {
            Vrahi.vrah.setOthestvo(etOthestvoIzmenit.getText().toString());
        }
        if(etFamiliaIzmenit.getText().length()>0) {
            Vrahi.vrah.setFamilia(etFamiliaIzmenit.getText().toString());
        }
        if(etObrazovanieIzmenit.getText().length()>0) {
            Vrahi.vrah.setObrazovanie(etObrazovanieIzmenit.getText().toString());
        }
        if(etStajIzmenit.getText().length()>0) {
            Vrahi.vrah.setStaj(etStajIzmenit.getText().toString());
        }
        Vrahi.vrah.setSpecialnost(spinerPositSpecialnostString);

        MetodiStatic.saveDataInSQL5param(
                Vrahi.vrah.getImia(), "imia",
                Vrahi.vrah.getOthestvo(), "othestvo",
                Vrahi.vrah.getFamilia(), "familia",
                Vrahi.vrah.getSpecialnost(), "specialnost",
                Vrahi.vrah.getObrazovanie(), "obrazovanie",
                (Vrahi.vrah.getStaj()), "staj",
                FirebaseAuth.getInstance().getCurrentUser().getUid(), "uid",
                Utils.UPDATE_FIO_VRAHA_SQL
        );
        MetodiStatic.saveObjToSD(Vrahi.vrah, this, Vrahi.VRAH_KEY );

        if(thumbnailBitmap!=null) {
            MetodiStatic.saveFotoIn_File_SQL_STORAGE(thumbnailBitmap);
            Picasso.get()
                    .load(Vrahi.vrah.getFotoVraha())
                    .into(ProfilInfoActivity.ivProfilInfo);
        }

        finish();
    }
    public static void loadSpecialnostVrahey(final Activity activity) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.SELECT_SPECIALNOST_VRAHEY),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println("RESP loadSpecialnostVrahey " + response);
                            JSONObject jsonObject = new JSONObject(response);

                            List <String> specList = new ArrayList();
                            JSONArray jsArrSpecialnosti=jsonObject.getJSONArray("specname");
                            for (int x = 0; x<jsArrSpecialnosti.length(); x++) {
                                specList.add((String) jsArrSpecialnosti.get(x));
    if(Vrahi.vrah.getSpecialnost().equals((String) jsArrSpecialnosti.get(x))){
        spinerPositSpecialnost=x;
        spinerPositSpecialnostString=(String) jsArrSpecialnosti.get(x);
    }

                            }
                            EditUserInfoActivity.spinner(activity, specList);

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

                System.out.println("PARAM Load info spec " + parameters.toString());
                return parameters;
            }
        };

        MainActivity.requestQueue.add(stringRequest);
    }
    public static void spinner(final Activity activity, final List<String> list){

        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (activity, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = activity.findViewById(R.id.spSpecialnost);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Title");
        // выделяем элемент
        spinner.setSelection(spinerPositSpecialnost);
        // устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // показываем позиция нажатого элемента
                spinerPositSpecialnostString=list.get(position);
                spinerPositSpecialnost=position;
                Toast.makeText(activity, "Position = " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

}