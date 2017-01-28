package com.example.anggaprabawa.speaksscheduler;

/**
 * Created by Angga Prabawa on 1/12/2017.
 */

public class Config {

    //Address of our scripts of the CRUD
    public static final String URL_LOGIN ="http://192.168.135.1/jadwalperkuliahan/login.php";
    public static final String URL_SPEECHINPUT = "http://192.168.135.1/jadwalperkuliahan/speechinput.php";
    public static final String URL_SPEECHOUTPUT = "http://192.168.135.1/jadwalperkuliahan/speechoutput.php?=tanggapan";

    //Keys that will be used to send the request to php scripts
    public static final String ID_LOGIN = "id_login";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String ID_SPEECHINPUT = "id_pertanyaan";
    public static final String SPEECHINPUT = "pertanyaan";
    public static final String ID_SPEECHOUTPUT = "id_tanggapan";
    public static final String SPEECHOUTPUT = "tanggapan";

    //JSON Tags
    public static final String TAG_JSON_ARRAY ="result";
    public static final String TAG_ID_SPEECHINPUT = "id_pertanyaan";
    public static final String TAG_SPEECHINPUT = "pertanyaan";
    public static final String TAG_ID_SPEECHOUTPUT = "id_tanggapan";
    public static final String TAG_SPEECHOUTPUT = "tanggapan";
}
