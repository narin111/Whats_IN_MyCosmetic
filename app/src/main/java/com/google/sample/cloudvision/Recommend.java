package com.google.sample.cloudvision;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.List;

import static com.google.sample.cloudvision.ApiExamSearchShop.CosmeticList;



public class Recommend extends Fragment implements View.OnClickListener {
    public static final String KeyW=" ";
    private static TextView textCos;
    public static String[] CosList =new String[100];//화장품 목록, ApiExamSearchShop.java의 title


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInastanceState){
        super.onStart();

        View viewR = inflater.inflate(R.layout.recommend, container, false);
        crawlApiF("꿀");
        String textid=" ";
        String textlist=" ";

        textCos= viewR.findViewById(R.id.textCosmetic1); //알러지 조회내용 출력

        //꿀 정보 얻어오는거 에러남.
//        for(int i=0;i<CosmeticList.costitlelength;i++){
//            textCos.append(CosList[i]);
//            Log.v("꿀 제발22",CosList[i]);
//        }

        //textlist = CosList[0];
        //textCos.append(textlist);

        for(int i=0;i<CosmeticList.costitlelength;i++){
            //Log.v("꿀 제발11",CosmeticList.costitle[i]);
            CosList[i] = CosmeticList.costitle[i];
            Log.v("꿀 제발33",Integer.toString(i)+" " +CosList[i]);
        }


        return viewR;
    }

    public void crawlApiF(String keyword){
        Thread thread = new Thread() {
            public void run() {
                ApiExamSearchShop api = new ApiExamSearchShop();
                api.main(keyword); //keyword에 버튼 내용, 검색하고자 하는 성분을 입력하면 됨. ex)꿀
                for(int i=0;i<CosmeticList.costitlelength;i++){
                    //Log.v("꿀 제발11",CosmeticList.costitle[i]);
                    CosList[i] = CosmeticList.costitle[i];
                    Log.v("꿀 제발22",Integer.toString(i)+" " +CosList[i]);
                }
            }
        };
        thread.start();
    }

    public void onClick(View view3){
        switch(view3.getId()){
            case R.id.honey:
                Log.v("버튼 눌림","꿀");
                //웹뷰띄우기
                break;
            default:
                Log.v("onclick 에러", "에러");
        }
    }



}