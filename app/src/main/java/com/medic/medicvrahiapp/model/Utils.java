package com.medic.medicvrahiapp.model;

public  class Utils {
    public static String ipAdresConfig ="";
    public static String papkiAdres ="";
    public static final String BASE_IP = "http://35.198.188.230/android/medic/";
    public static final String SAVE_VRAH_TOKEN_TO_MySQL =  "sendVrahToken.php";
    public static final String UPDATE_SAVE_FOTO_TO_MySQL =  "UpdateSaveFotoVraha.php";
    public static final String UPDATE_FIO_VRAHA_SQL =  "UpdateFioVraha.php";
    public static final String INSERT_TALONHIKI_MySQL =  "insertTalonhiki.php";
    public static final String SHOW_USERS =  "showUsers.php";
    public static final String LOAD_INFO_VRAHA = "loadInfoVraha.php";
    public static final String SELECT_SPECIALNOST_VRAHEY = "SelectSpecialnostVrahei.php";
    public static final String SAVE_UID_AND_TOKEN =  "saveUidAndToken.php";

    public static final String SHOW_TALONHIK =  "showTalonhik.php";
    public static final String SHOW_TALONHIK_DOSTUPNIE_DATI =  "showTalonhikDostupnieDati.php";
    public static String getAdres(String file){
        System.out.println("Adres faila PHP "+"http://"+ipAdresConfig+papkiAdres+file);
        return "http://"+ipAdresConfig+papkiAdres+file;
    }
}
