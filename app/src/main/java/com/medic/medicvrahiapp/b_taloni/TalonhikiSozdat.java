package com.medic.medicvrahiapp.b_taloni;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.medic.medicvrahiapp.model.InitOsnovnActivityBottomButton;
import com.medic.medicvrahiapp.MainActivity;
import com.medic.medicvrahiapp.model.MetodiStatic;
import com.medic.medicvrahiapp.R;
import com.medic.medicvrahiapp.model.Utils;
import com.medic.medicvrahiapp.model.Vrahi;
import com.medic.medicvrahiapp.mcalendarview.DateData;
import com.medic.medicvrahiapp.mcalendarview.ExpCalendarView;
import com.medic.medicvrahiapp.mcalendarview.OnExpDateClickListener;
import com.medic.medicvrahiapp.mcalendarview.OnMonthScrollListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TalonhikiSozdat extends InitOsnovnActivityBottomButton {
    static AppCompatActivity activityThis;

    private static final int DIALOG_TIME = 101;
    List<TalonhikObj> talonhikObjList = new ArrayList<>();
    static List<List<TalonhikObj>> talonhikListList = new ArrayList<>();
    LinearLayout linDliaTalonhikov;
    static ConstraintLayout conLayWidthInPx;
    ConstraintLayout conLayZapisMain;
    ConstraintLayout conVseTalonhiki;
    static LayoutInflater ltInflater2;
    static LinearLayout linLayout;
    static ProgressBar progresZagruzkaTalonhikov;
    public static ExpCalendarView calZapis;
    SozdatTalonhikiInfoObject talonInfoObj = new SozdatTalonhikiInfoObject(10, 1, 20, 1);

    EditText
            etIntervalMinutZapisi, etKolihTalonovDobavit;
    TextView tvNahaloPriema;
    Button btDobavit;
    Date clickDateTemp = new Date();


    private TextView YearMonthTv;
    private DateData selectedDate;

    TalonLinInflateAdapter talonLinInflateAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tolonhiki_calendar);
        activityThis = this;
        conVseTalonhiki = findViewById(R.id.conVseTalonhiki);
        tvNahaloPriema = findViewById(R.id.tvNahaloPriema);
        etIntervalMinutZapisi = findViewById(R.id.etIntervalMinutZapisi);
        etKolihTalonovDobavit = findViewById(R.id.etKolihTalonovDobavit);
        btDobavit = findViewById(R.id.btDobavit);
        linDliaTalonhikov = findViewById(R.id.linDliaTalonhikov);
        calZapis = findViewById(R.id.calendar_exp);
        conLayWidthInPx = findViewById(R.id.conVseTalonhiki);
        progresZagruzkaTalonhikov = findViewById(R.id.progresZagruzkaTalonhikov);
        YearMonthTv = (TextView) findViewById(R.id.main_YYMM_Tv);

        try {
            if (MetodiStatic.readTalonInfoFromSD((this)) != null)
                talonInfoObj = MetodiStatic.readTalonInfoFromSD(this);
        } catch (Exception exReadeTalonInfo) {
            System.out.println("Reade info talona " + exReadeTalonInfo.toString());
        }
        if (talonInfoObj.getMinutes() < 10) {
            tvNahaloPriema.setText(Integer.toString(talonInfoObj.getHour()) + " : 0" +
                    Integer.toString(talonInfoObj.getMinutes()));
        } else {
            tvNahaloPriema.setText(Integer.toString(talonInfoObj.getHour()) + " : " +
                    Integer.toString(talonInfoObj.getMinutes()));
        }
        etIntervalMinutZapisi.setText(Integer.toString(talonInfoObj.getIntervalMin()));
        etKolihTalonovDobavit.setText(Integer.toString(talonInfoObj.getKolihestvo()));
        DateData dateDataNow = new DateData(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        calZapis.travelTo(dateDataNow);
        DateData dateSelectCurent = new DateData(dateDataNow.getYear(),
                dateDataNow.getMonth() + 1,
                dateDataNow.getDay());
        YearMonthTv.setText(dateSelectCurent.getMonthString() + dateDataNow.getYear());

        calZapis.setOnDateClickListener(new OnExpDateClickListener()).setOnMonthScrollListener(new OnMonthScrollListener() {
            @Override
            public void onMonthChange(int year, int month) {
            }

            @Override
            public void onMonthChangeString(int year, String monthStr) {
                YearMonthTv.setText(monthStr + " " + Integer.toString(year));
            }

            @Override
            public void onMonthScroll(float positionOffset) {
            }
        });
        calZapis.setOnDateClickListener(new OnExpDateClickListener()).setOnDateClickListener(new OnExpDateClickListener() {

            @Override
            public void onDateClick(View view, DateData date) {
                super.onDateClick(view, date);
                conVseTalonhiki.setVisibility(View.VISIBLE);
                loadTalonhiki(date.getDate());
                clickDateTemp = date.getDate();
                System.out.println("date click " + clickDateTemp.getDate() + " " + clickDateTemp.getYear());
                btDobavit.setVisibility(View.VISIBLE);
            }
        });
        loadDatiTalonhikov(calZapis);
        btDobavit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int intervalPriema = Integer.parseInt(etIntervalMinutZapisi.getText().toString());
                int skolkoTalonovDobavit = Integer.parseInt(etKolihTalonovDobavit.getText().toString());
                clickDateTemp.setHours(talonInfoObj.getHour());
                clickDateTemp.setMinutes(talonInfoObj.getMinutes());
                MetodiStatic.createTalonhikiInMySQL(clickDateTemp, intervalPriema, skolkoTalonovDobavit);
                talonInfoObj.setIntervalMin(Integer.parseInt(etIntervalMinutZapisi.getText().toString()));
                talonInfoObj.setKolihestvo(Integer.parseInt(etKolihTalonovDobavit.getText().toString()));
                MetodiStatic.saveTalonInfoToSD(talonInfoObj, TalonhikiSozdat.this);

            }
        });
        tvNahaloPriema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_TIME);
            }
        });
    }

    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_TIME) {
            TimePickerDialog tpd = new TimePickerDialog(this, myCallBack, talonInfoObj.getHour(),
                    talonInfoObj.getMinutes(), true);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    TimePickerDialog.OnTimeSetListener myCallBack = new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            talonInfoObj.setHour(hourOfDay);
            talonInfoObj.setMinutes(minute);
            if (minute < 10) {
                tvNahaloPriema.setText(hourOfDay + " : 0" + minute);
            } else {
                tvNahaloPriema.setText(hourOfDay + " : " + minute);
            }

        }
    };

    private static int skolkoTalonhikovVlezetGET(AppCompatActivity activity) {
        float conLayWidthInDp = MetodiStatic.pxToDp(conLayWidthInPx.getWidth());
        float marginDp = MetodiStatic.pxToDp(activity.getResources().getDimension(R.dimen.talonhika_margin));
        float razmerTalonaDp = MetodiStatic.pxToDp(activity.getResources().getDimension(R.dimen.talohhika_shirina));
        int otvet = Math.round((conLayWidthInDp - razmerTalonaDp) / (marginDp + razmerTalonaDp));
//        System.out.println(otvet+" skolko vlezet "+conLayWidthInDp+" "+marginDp+" "+razmerTalonaDp);
        return otvet;
    }

    public static void loadTalonhiki(final Date dateClicked) {


        Calendar calendar = Calendar.getInstance();
        calendar.set(dateClicked.getYear(), dateClicked.getMonth(), dateClicked.getDate());
//        System.out.println("load talonhiki " + calendar.get(Calendar.DATE));
//        calendar.setTime(new Date(2018,7,10) );
        final String dateStr = (Integer.toString(calendar.get(Calendar.YEAR)) + "-" +
                Integer.toString(calendar.get(Calendar.MONTH) + 1) + "-" +
                Integer.toString(calendar.get(Calendar.DATE)));
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        final String dateStr2 = (Integer.toString(calendar.get(Calendar.YEAR)) + "-" +
                Integer.toString(calendar.get(Calendar.MONTH) + 1) + "-" +
                Integer.toString(calendar.get(Calendar.DATE)));

        //progresZapis=findViewById(R.id.progresZapis);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.SHOW_TALONHIK), new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    if (linLayout != null) {
                        linLayout.removeAllViews();
                        talonhikListList.clear();
                    }
                    ltInflater2 = activityThis.getLayoutInflater();
                    linLayout = (LinearLayout) activityThis.findViewById(R.id.linDliaRiadaTalonhikov);
                    progresZagruzkaTalonhikov.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonObjectVrah = jsonObject.getJSONArray("talonhiki");

                    List<TalonhikObj> talonhikList;
                    talonhikList = new ArrayList<>();
                    int skolkoTalonhikovVlezet = skolkoTalonhikovVlezetGET(activityThis);
                    int z = 0;
                    for (int y = 0; y < jsonObjectVrah.length(); y++) {

                        JSONObject jsonObjectVrahFor = (JSONObject) jsonObjectVrah.get(y);

                        String id = jsonObjectVrahFor.getString("id");
                        String talonhik = jsonObjectVrahFor.getString("talonhik");
                        String gmt = jsonObjectVrahFor.getString("gmt");
                        TalonhikObj talonhikJSON = new TalonhikObj(id, talonhik, gmt);
                        //    System.out.println(z + " Z");
                        if (z < skolkoTalonhikovVlezet - 1) {

                            talonhikList.add(talonhikJSON);
                            z++;
                        } else {
                            talonhikList.add(talonhikJSON);
                            z = 0;
                            talonhikListList.add(talonhikList);
                            talonhikList = new ArrayList<>();
                        }
                        if (y == jsonObjectVrah.length() - 1) {
//                            System.out.println("ELSE IF");
                            talonhikListList.add(talonhikList);
                            addViewTalonhikiVremia(talonhikListList);
//                            talonhikListList.add(talonhikList);
//                            talonLinInflateAdapter.notifyDataSetChanged();
//                            talonLinInflateAdapter.setLoaded();

                        }
                    }


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
                Map<String, String> parameters = new HashMap<String, String>();

                parameters.put("vrah", FirebaseAuth.getInstance().getCurrentUser().getUid());
                parameters.put("date", dateStr);
                parameters.put("date2", dateStr2);
//                O.o(parameters.toString()+" send paraam");
                return parameters;
            }
        };
        progresZagruzkaTalonhikov.setVisibility(View.VISIBLE);
        MainActivity.requestQueue.add(stringRequest);
    }

    private static void addViewTalonhikiVremia(final List<List<TalonhikObj>> talonhikList) {
        for (int e = 0; e < talonhikList.size(); e++) {
            View item = ltInflater2.inflate(R.layout.row_lin_lay_dlia_talonhikov, linLayout, false);
            LinearLayout linLayout3 = (LinearLayout) item.findViewById(R.id.linDliaTalonhikov);
            LayoutInflater ltInflater3 = activityThis.getLayoutInflater();
            for (int r = 0; r < talonhikList.get(e).size(); r++) {
                View item3 = ltInflater3.inflate(R.layout.row_talonhik, linLayout3, false);
                TextView tvVremiaTalonhika = item3.findViewById(R.id.tvVremiaTalonhika);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                tvVremiaTalonhika.setText(simpleDateFormat.format(talonhikList.get(e).get(r).getTalonTime()));

                final int finalR = r;
                final int finalE = e;
                tvVremiaTalonhika.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        MetodiStatic.vibranniyTalonhik = talonhikList.get(finalE).get(finalR);
//                        createIntentPodtverjdenieZapisa();
                        System.out.println("Zagrujen talon ");
                    }
                });
                linLayout3.addView(item3);
            }
            linLayout.addView(item);
//            conLayZapisMain.updateViewLayout(this, );
        }
    }

    public static void loadDatiTalonhikov(final ExpCalendarView calZapis) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Utils.getAdres(Utils.SHOW_TALONHIK_DOSTUPNIE_DATI), new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    TalonhikiSozdat.progresZagruzkaTalonhikov.setVisibility(View.GONE);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonObjectVrah = jsonObject.getJSONArray("talonhiki");

                    Calendar calendar = Calendar.getInstance();
                    for (int y = 0; y < jsonObjectVrah.length(); y++) {
                        JSONObject jsonObject1 = (JSONObject) jsonObjectVrah.get(y);

                        String s = jsonObject1.getString("date");
                        String[] dateStrArr = s.split("-");

                        String year = dateStrArr[0];
                        String month = dateStrArr[1];
                        String day = dateStrArr[2];
                        int yearI = Integer.parseInt(year);
                        int monthI = Integer.parseInt(month) - 1;
                        int dayI = Integer.parseInt(day);

                        DateData kogdaEctTalonData = new DateData(yearI, monthI, dayI);
//                        calZapis.setDateCell(dayI);
                        calZapis.markDate(kogdaEctTalonData);
//
                        System.out.println("Mark date ");
//                        calendar.set(yearI,monthI,dayI);
//                        Event ev2 = new Event(Color.BLACK, calendar.getTimeInMillis());
//                        calZapis.markDate(ev2);
                    }


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
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("vrah", FirebaseAuth.getInstance().getCurrentUser().getUid());
                return parameters;
            }
        };
        progresZagruzkaTalonhikov.setVisibility(View.VISIBLE);
        MainActivity.requestQueue.add(stringRequest);
    }
}
