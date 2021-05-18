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

package com.google.sample.cloudvision;

import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Fragment2 extends Fragment {



    public static final String ROOT_DIR = "/data/data/com.google.sample.cloudvision/databases/";
    public static void setDB(Fragment2 ctx, String fileDB) {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        // db파일 이름 적어주기
        File outfile = new File(ROOT_DIR+fileDB); //// 이부분 모르겠다. helper에도 이름 추가하기?
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
    /*String DBname = "Harmful";
    String DBnameREC = "forthisType";
    String DBnameCARE = "Typecareful";
    String DBnameAll = "Allergy";

    String fileDBname = "harmful.db";
    String fileDBname_REC = "forthisType.db";
    String fileDBname_CARE= "TypeCareful.db";*/
    String fileDBname_All = "allergy.db";




    private void InsertDBAllergy(String name, String fileDB,String userAll){
        setDB(this, fileDB);
        mHelper=new ProductDBHelper(getActivity(), fileDB);
        db =mHelper.getWritableDatabase();
        Log.v("알러지 Helper ",setAllergy);
        ContentValues values = new ContentValues();
        values.put("allergyName", setAllergy);
        db.insert(name, null, values);
        Log.v("알러지 db에 넣음: ",userAll); //로그는 출력되지만 insert가 제대로 되는지 모르겠다.
    }

    EditText edittextA; //알러지 받아오는 edittext
    String setAllergy; //알러지 담아오는 변수

    public static class allergyDBcheck{ //이미지 성분과 알러지 데이터베이스 성분들 비교하기.
        private static String imageIng[] = new String[100]; //유저의 사진에서 가져온 성분배열.[성분명]

        private static String DBAllergy[] = new String[100]; //알러지 배열.[성분명]
        private static String checkAll[] = new String[100]; //비교결과[알러지명]

        public static int imageArrLength; //이미지 성분배열 길이 저장하는 변수.
        private static int dbArrLength; //데이터베이스 성분배열 길이 저장하는 변수.
        private static int checkCount = 0; //비교결과 갯수(유해성분 개수)
        //1. 사진에서 성분가져온걸 저장하는 함수.
        public static void getImageIGall(String[] imageIG, int IGlength){
            imageArrLength = IGlength;
            for(int i=0;i<imageArrLength;i++){
                imageIng[i] = imageIG[i];
            }
            for(int i=0;i<imageArrLength;i++) {
                Log.v("알러지-이미지성분Class", imageIng[i]);
            }
        }
        //2. 데이터베이스에서 성분가져온걸 저장하는 함수.
        /*public static void getDBIGall(String[] dbIG, int dblength){
            dbArrLength = dblength;
            for(int i=0;i<dbArrLength;i++){
                DBAllergy[i] = dbIG[i];
            }
            for(int i=0;i<dbArrLength;i++) {
                Log.v("알러지-데이터베이스 출력Class", DBAllergy[i]);
            }
        }
        //3. 이미지 성분명과 데이터베이스 성분명 비교하고 결과 출력하는 함수.
        public static void IGcheckall(){
            for(int i=0;i<imageArrLength;i++) {
                for (int j = 0; j < dbArrLength; j++) {
                    if (imageIng[i].equals(DBAllergy[j])) { //이미지 성분명과 DB성분명 비교해서 같을때의 성분명과 유해효과 문자열 배열에 저장.
                        checkAll[checkCount] = DBAllergy[j];
                        checkCount++; //유해성분 개수
                    }
                }
            }
            for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                Log.v("알러지 비교결과Class", "결과"+i+": "+checkAll[i]);
                textviewA.append("결과"+(i+1)+": "+checkAll[i]+" \n");
            }
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onStart(savedInstanceState);
        super.onStart();
        //setContentView(R.layout.fragment1);
        //Toolbar toolbar = getView().findViewById(R.id.toolbar2);
        //setSupportActionBar(toolbar);
        /////
        /*Thread thread = new Thread() { //API 주석
            public void run() {
                ApiExamSearchShop api = new ApiExamSearchShop();
                api.main();
            }
        };
        thread.start();*/
        /////
        return inflater.inflate(R.layout.fragment2, container, false);
    }
    ////////////////////////////////
}
