package com.google.sample.cloudvision;

//public class SQLiteOpenHelper {
//}

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class ProductDBHelper extends SQLiteOpenHelper {  //새로 생성한 adapter 속성은 SQLiteOpenHelper이다.
    public ProductDBHelper(Context context) {
        super(context, "sampleTBL.db", null, 1);    // db명과 버전만 정의 한다.
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onOpen(SQLiteDatabase db){
        //println("onOpen 호출됨");
    }

}