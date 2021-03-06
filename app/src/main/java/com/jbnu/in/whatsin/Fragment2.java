/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jbnu.in.whatsin;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.jbnu.in.whatsin.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Fragment2 extends Fragment implements View.OnClickListener {

    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = Fragment2.class.getSimpleName();
    public static allergyDBcheck checkall;

    private static TextView textskintype;///////
    private static TextView textviewA;
    private static TextView textViewDB;

    //View v = inflater.inflate(R.layout.fragment1, container, false);
    public static final String ROOT_DIR = "/data/data/com.jbnu.in.whatsin/databases/";

    public static void setDB(Fragment2 ctx, String fileDB) {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        // db?????? ?????? ????????????
        File outfile = new File(ROOT_DIR+fileDB);
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open(fileDB, AssetManager.ACCESS_BUFFER);
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
            } else {}
        } catch (IOException e) {

        }
    }

    public SQLiteDatabase db;
    public Cursor cursor;
    ProductDBHelper mHelper;
    String DBnameAll = "Allergy";
    String fileDBname_All = "allergy.db";

    private void ShowDB_Allergy(String name, String fileDB){

        Log.v("dbname: ",  name);
        Log.v("???????????? ?????? ??????", fileDB);
        setDB(this,fileDB);//setDB_All(this);
        mHelper=new ProductDBHelper(getActivity(), fileDB);
        db =mHelper.getReadableDatabase();

        int len=0;//????????? ??????
        String nameAcol= null;

        String sql = "Select * FROM " +name; // DBname;
        String nameAsql[] = new String[100];

        cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()) {
            nameAcol= cursor.getString(0);
            nameAsql[len] = nameAcol;
            len++;
        }

        checkall.getDBIGall(nameAsql, len);
        for(int i=0;i<len;i++) {
            Log.v("????????? showdb", nameAsql[i]);
        }
    }

    private void InsertDBAllergy(String name, String fileDB,String userAll){
        setDB(this, fileDB);
        mHelper=new ProductDBHelper(getActivity(), fileDB);
        db =mHelper.getWritableDatabase();
        Log.v("????????? Helper ",setAllergy);
        ContentValues values = new ContentValues();
        values.put("allergyName", setAllergy);
        db.insert(name, null, values);
        Log.v("????????? db??? ??????: ",userAll);
    }

    EditText edittextA; //????????? ???????????? edittext
    String setAllergy; //????????? ???????????? ??????


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onStart();

        View view2 = inflater.inflate(R.layout.fragment2, container, false);

        textViewDB= view2.findViewById(R.id.textViewDBal);
        //checkall.getDBIGall(nameAsql, len);
        ShowDB_Allergy(DBnameAll, fileDBname_All);

        edittextA = view2.findViewById(R.id.editTextAllergy); //????????? ???????????? edittext

        Button buttonAcre = (Button) view2.findViewById(R.id.buttonAC); //????????? ????????????
        buttonAcre.setOnClickListener(this);


        textviewA= view2.findViewById(R.id.textViewAl); //????????? ???????????? ??????
        Button buttonAout = (Button) view2.findViewById(R.id.buttonAget); //????????? ????????????
        buttonAout.setOnClickListener(this);

        return view2;
    }

    public void onClick(View view2){
        switch(view2.getId()){
            case R.id.buttonAC:
                setAllergy = edittextA.getText().toString(); //????????? ?????? ?????????
                edittextA.setText(null);
                Log.v("????????? ?????????",setAllergy);
                //???????????? db??? ?????????.
                InsertDBAllergy(DBnameAll, fileDBname_All,setAllergy);
                ShowDB_Allergy(DBnameAll, fileDBname_All);
                break;
            case R.id.buttonAget: //????????? ?????? ?????????!20??? ????????? ??????.
                //?????????db?????? ??????
                Log.v("????????? shodb????????????:","??????");
                ShowDB_Allergy(DBnameAll, fileDBname_All);
                Log.v("????????? shodb????????????:","??????");
                //????????? ????????? ??????. allergycheck??????????????????
                checkall.IGcheckall();
                break;
            default:
                Log.v("onclick ??????", "??????");
        }
    }

    public static class allergyDBcheck{ //????????? ????????? ????????? ?????????????????? ????????? ????????????.
        private static String imageIng[] = new String[100]; //????????? ???????????? ????????? ????????????.[?????????]

        private static String DBAllergy[] = new String[100]; //????????? ??????.[?????????]
        private static String checkAll[] = new String[100]; //????????????[????????????]

        public static int imageArrLength; //????????? ???????????? ?????? ???????????? ??????.
        private static int dbArrLength; //?????????????????? ???????????? ?????? ???????????? ??????.
        private static int checkCount = 0; //???????????? ??????(???????????? ??????)
        //1. ???????????? ?????????????????? ???????????? ??????.
        public static void getImageIGall(String[] imageIG, int IGlength){
            imageArrLength = IGlength;
            for(int i=0;i<imageArrLength;i++){
                imageIng[i] = imageIG[i];
            }
            for(int i=0;i<imageArrLength;i++) {
                Log.v("?????????-???????????????Class", imageIng[i]);
            }
        }
        //2. ???????????????????????? ?????????????????? ???????????? ??????.
        public static void getDBIGall(String[] dbIG, int dblength){
            textViewDB.setText("");
            dbArrLength = dblength;
            for(int i=0;i<dbArrLength;i++){
                DBAllergy[i] = dbIG[i];
            }
            for(int i=0;i<dbArrLength;i++) {
                Log.v("?????????-?????????????????? ??????Class", DBAllergy[i]);
            }
            for(int i=0;i<dbArrLength;i++) {
                textViewDB.append(DBAllergy[i]+" ");
            }
        }
        //3. ????????? ???????????? ?????????????????? ????????? ???????????? ?????? ???????????? ??????.
        public static void IGcheckall(){
            textviewA.setText("");
            for(int i=0;i<imageArrLength;i++) {
                for (int j = 0; j < dbArrLength; j++) {
                    if (imageIng[i].equals(DBAllergy[j])) { //????????? ???????????? DB????????? ???????????? ???????????? ???????????? ???????????? ????????? ????????? ??????.
                        checkAll[checkCount] = DBAllergy[j];
                        checkCount++; //???????????? ??????
                    }
                }
            }
            for(int i=0;i<checkCount;i++) { //???????????? ????????????.
                Log.v("????????? ????????????Class", "??????"+i+": "+checkAll[i]);
                textviewA.append("??????"+(i+1)+": "+checkAll[i]+" \n");
            }

            for(int i=0;i<checkCount;i++) { //???????????? ????????????.
                checkAll[i]="";
            }
            checkCount = 0;
        }
    }

}
