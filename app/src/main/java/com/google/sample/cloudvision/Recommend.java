package com.google.sample.cloudvision;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Recommend extends Fragment {
    public static final String KeyIng=" ";
    private static TextView textCos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInastanceState){
        super.onStart();

        View viewR = inflater.inflate(R.layout.recommend, container, false);

        /*
        Thread thread = new Thread() {
            public void run() {
                ApiExamSearchShop api = new ApiExamSearchShop();
                api.main(keyIng);
            }
        };
        thread.start();
        */

        //Button buttonHN = (Button) viewR.findViewById(R.id.honey); //꿀 버튼
        //buttonHN.setOnClickListener((View.OnClickListener) this);
        String textid=" ";

        //for(int i=1;i<=30;i++){
        //textid = "R.id.textCosmetic" +Integer.toString(i);
        textCos= viewR.findViewById(R.id.textCosmetic1); //알러지 조회내용 출력
        //}
        crawlApi("꿀");
        //textCos.append();
        return viewR;
    }

    public void crawlApi(String keyIng){
        Thread thread = new Thread() {
            public void run() {
                ApiExamSearchShop api = new ApiExamSearchShop();
                api.main(KeyIng);
            }
        };
        thread.start();
    }

    public void onClick(View view3){
        switch(view3.getId()){
            case R.id.honey:
                Log.v("버튼 눌림","꿀");
                //crawlApi("꿀");
                break;
            default:
                Log.v("onclick 에러", "에러");
        }
    }



}