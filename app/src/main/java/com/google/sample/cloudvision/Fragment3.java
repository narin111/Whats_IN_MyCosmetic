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

import android.app.Activity;
import android.content.ContentValues;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Fragment3 extends Fragment {

    Recommend fragment1;
    //public static final String keyIng= " ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInastanceState){
        super.onStart();

        View view3 = inflater.inflate(R.layout.fragment3, container, false);
        /*
        Thread thread = new Thread() {
            public void run() {
                ApiExamSearchShop api = new ApiExamSearchShop();
                api.main(keyIng);
            }
        };
        thread.start();
        */
        fragment1 = new Recommend();

        Button buttonHN = (Button) view3.findViewById(R.id.honey); //꿀 버튼
        buttonHN.setOnClickListener((View.OnClickListener) this);


        return view3;
    }
    /*
    public void crawlApi(String Ing){
        Thread thread = new Thread() {
            public void run() {
                ApiExamSearchShop api = new ApiExamSearchShop();
                api.main(keyIng);
            }
        };
        thread.start();
    }
*/
    public void onClick(View view3){
        switch(view3.getId()){
            case R.id.honey:
                Log.v("버튼 눌림","꿀");
                //crawlApi("꿀");
                //Recommend프래그먼트 불러오기
                //getSupportFragmentManager().beginTransaction().replace(R.layout.fragment3, fragment1).commit();
                break;
            default:
                Log.v("onclick 에러", "에러");
        }
    }

}
