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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class Fragment3 extends Fragment implements View.OnClickListener{

    Recommend fragment1;
    public static String keyIng= " ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInastanceState){
        super.onStart();

        View view3 = inflater.inflate(R.layout.fragment3, container, false);
        fragment1 = new Recommend();
        Button buttonHN = (Button) view3.findViewById(R.id.honey); //꿀 버튼
        buttonHN.setOnClickListener(this);
        Button buttonhong = (Button) view3.findViewById(R.id.hong);
        buttonhong.setOnClickListener(this);
        Button buttonhoho = (Button) view3.findViewById(R.id.hoho);
        buttonhoho.setOnClickListener(this);
        Button buttonheal = (Button) view3.findViewById(R.id.heal);
        buttonheal.setOnClickListener(this);
        Button buttonbutter = (Button) view3.findViewById(R.id.butter);
        buttonbutter.setOnClickListener(this);
        Button buttonoe = (Button) view3.findViewById(R.id.oe);
        buttonoe.setOnClickListener(this);
        Button buttongreen = (Button) view3.findViewById(R.id.green);
        buttongreen.setOnClickListener(this);
        Button buttonaloe = (Button) view3.findViewById(R.id.aloe);
        buttonaloe.setOnClickListener(this);
        Button buttonttree = (Button) view3.findViewById(R.id.ttree);
        buttonttree.setOnClickListener(this);
        Button buttonbp = (Button) view3.findViewById(R.id.bp);
        buttonbp.setOnClickListener(this);
        Button buttonclg = (Button) view3.findViewById(R.id.clg);
        buttonclg.setOnClickListener(this);
        Button buttonsnail = (Button) view3.findViewById(R.id.snail);
        buttonsnail.setOnClickListener(this);
        Button buttonlemon = (Button) view3.findViewById(R.id.lemon);
        buttonlemon.setOnClickListener(this);
        Button buttonvi = (Button) view3.findViewById(R.id.vi);
        buttonvi.setOnClickListener(this);

        return view3;
    }

    public void onClick(View view3){
        switch(view3.getId()){
            case R.id.honey:
                Log.v("버튼 눌림","꿀");
                keyIng="꿀";

                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction.addToBackStack(null);
                transaction.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;

            case R.id.hong:
                Log.v("버튼 눌림","홍삼");
                keyIng="홍삼";
                FragmentTransaction transaction2 = getFragmentManager().beginTransaction();
                transaction2.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction2.addToBackStack(null);
                transaction2.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.heal:
                Log.v("버튼 눌림","히알루론산");
                keyIng="히알루론산";

                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                transaction3.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction3.addToBackStack(null);
                transaction3.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.hoho:
                Log.v("버튼 눌림","호호바오일");
                keyIng="호호바오일";

                FragmentTransaction transaction5 = getFragmentManager().beginTransaction();
                transaction5.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction5.addToBackStack(null);
                transaction5.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.butter:
                Log.v("버튼 눌림","시어버터");
                keyIng="시어버터";

                FragmentTransaction transaction6 = getFragmentManager().beginTransaction();
                transaction6.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction6.addToBackStack(null);
                transaction6.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.oe:
                Log.v("버튼 눌림","오이");
                keyIng="오이";

                FragmentTransaction transaction7 = getFragmentManager().beginTransaction();
                transaction7.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction7.addToBackStack(null);
                transaction7.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.green:
                Log.v("버튼 눌림","녹차");
                keyIng="녹차";

                FragmentTransaction transaction8 = getFragmentManager().beginTransaction();
                transaction8.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction8.addToBackStack(null);
                transaction8.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.ttree:
                Log.v("버튼 눌림","티트리");
                keyIng="티트리";

                FragmentTransaction transaction9 = getFragmentManager().beginTransaction();
                transaction9.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction9.addToBackStack(null);
                transaction9.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.aloe:
                Log.v("버튼 눌림","알로에");
                keyIng="알로에";

                FragmentTransaction transaction10 = getFragmentManager().beginTransaction();
                transaction10.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction10.addToBackStack(null);
                transaction10.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.bp:
                Log.v("버튼 눌림","병풀");
                keyIng="병풀";

                FragmentTransaction transaction11 = getFragmentManager().beginTransaction();
                transaction11.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction11.addToBackStack(null);
                transaction11.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.clg:
                Log.v("버튼 눌림","콜라겐");
                keyIng="콜라겐";

                FragmentTransaction transaction12 = getFragmentManager().beginTransaction();
                transaction12.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction12.addToBackStack(null);
                transaction12.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.snail:
                Log.v("버튼 눌림","달팽이");
                keyIng="달팽이";

                FragmentTransaction transaction13 = getFragmentManager().beginTransaction();
                transaction13.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction13.addToBackStack(null);
                transaction13.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.lemon:
                Log.v("버튼 눌림","레몬");
                keyIng="레몬";

                FragmentTransaction transaction14 = getFragmentManager().beginTransaction();
                transaction14.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction14.addToBackStack(null);
                transaction14.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            case R.id.vi:
                Log.v("버튼 눌림","비타민");
                keyIng="비타민";

                FragmentTransaction transaction15 = getFragmentManager().beginTransaction();
                transaction15.add(R.id.id_container_main3,fragment1);//attach(fragment1);//replace(R.layout.content_main3, fragment1).commit();
                transaction15.addToBackStack(null);
                transaction15.commit();
                fragment1.crawlApiF(keyIng);
                //Fragment3.java에서 버튼을 누를때 버튼의 글씨 내용을 Recommend.java에 보내줘야함.
                break;
            default:
                Log.v("onclick 에러", "에러");
        }
    }

}
