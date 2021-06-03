package com.jbnu.in.whatsin;

//public class SQLiteOpenHelper {
//}

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.fragment.app.FragmentActivity;

class ProductDBHelper extends SQLiteOpenHelper {  //새로 생성한 adapter 속성은 SQLiteOpenHelper이다.
    public ProductDBHelper(FragmentActivity context, String DBname) {
        super(context, DBname, null, 1);    // db명과 버전만 정의 한다.
        //super(context, "forthisType.db", null, 1);
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
