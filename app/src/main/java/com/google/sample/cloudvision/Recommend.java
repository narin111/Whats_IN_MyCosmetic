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
    private static TextView textCos1;
    private static TextView textCos2;
    private static TextView textCos3;
    private static TextView textCos4;
    private static TextView textCos5;
    private static TextView textCos6;
    private static TextView textCos7;
    private static TextView textCos8;
    private static TextView textCos9;
    private static TextView textCos10;
    private static TextView textCos11;
    private static TextView textCos12;
    private static TextView textCos13;
    private static TextView textCos14;
    private static TextView textCos15;
    private static TextView textCos16;
    private static TextView textCos17;
    private static TextView textCos18;
    private static TextView textCos19;
    private static TextView textCos20;
    private static TextView textCos21;
    private static TextView textCos22;
    private static TextView textCos23;
    private static TextView textCos24;
    private static TextView textCos25;
    private static TextView textCos26;
    private static TextView textCos27;
    private static TextView textCos28;
    private static TextView textCos29;
    private static TextView textCos30;
    public static String[] CosList =new String[100];//화장품 목록, ApiExamSearchShop.java의 title


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInastanceState){
        super.onStart();

        View viewR = inflater.inflate(R.layout.recommend, container, false);
        crawlApiF("꿀");

        //이 아래부분 효율적으로 할수 있다면 바꿔보기ㅠㅠㅠㅠㅠㅠ
        textCos1= viewR.findViewById(R.id.textCosmetic1);
        textCos1.append(CosList[0]);
        textCos2= viewR.findViewById(R.id.textCosmetic2);
        textCos2.append(CosList[1]);
        textCos3= viewR.findViewById(R.id.textCosmetic3);
        textCos3.append(CosList[2]);
        textCos4= viewR.findViewById(R.id.textCosmetic4);
        textCos4.append(CosList[3]);//////////////////
        textCos5= viewR.findViewById(R.id.textCosmetic5);
        textCos5.append(CosList[4]);
        textCos6= viewR.findViewById(R.id.textCosmetic6);
        textCos6.append(CosList[5]);
        textCos7= viewR.findViewById(R.id.textCosmetic7);
        textCos7.append(CosList[6]);
        textCos8= viewR.findViewById(R.id.textCosmetic8);
        textCos8.append(CosList[7]);
        textCos9= viewR.findViewById(R.id.textCosmetic9);
        textCos9.append(CosList[8]);
        textCos10= viewR.findViewById(R.id.textCosmetic10);
        textCos10.append(CosList[9]);
        textCos11= viewR.findViewById(R.id.textCosmetic11);
        textCos11.append(CosList[10]);
        textCos12= viewR.findViewById(R.id.textCosmetic12);
        textCos12.append(CosList[11]);
        textCos13= viewR.findViewById(R.id.textCosmetic13);
        textCos13.append(CosList[12]);
        textCos14= viewR.findViewById(R.id.textCosmetic14);
        textCos14.append(CosList[13]);
        textCos15= viewR.findViewById(R.id.textCosmetic15);
        textCos15.append(CosList[14]);
        textCos16= viewR.findViewById(R.id.textCosmetic16);
        textCos16.append(CosList[15]);
        textCos17= viewR.findViewById(R.id.textCosmetic17);
        textCos17.append(CosList[16]);
        textCos18= viewR.findViewById(R.id.textCosmetic18);
        textCos18.append(CosList[17]);
        textCos19= viewR.findViewById(R.id.textCosmetic19);
        textCos19.append(CosList[18]);
        textCos20= viewR.findViewById(R.id.textCosmetic20);
        textCos20.append(CosList[19]);
        textCos21= viewR.findViewById(R.id.textCosmetic21);
        textCos21.append(CosList[20]);
        textCos22= viewR.findViewById(R.id.textCosmetic22);
        textCos22.append(CosList[21]);
        textCos23= viewR.findViewById(R.id.textCosmetic23);
        textCos23.append(CosList[22]);
        textCos24= viewR.findViewById(R.id.textCosmetic24);
        textCos24.append(CosList[23]);
        textCos25= viewR.findViewById(R.id.textCosmetic25);
        textCos25.append(CosList[24]);
        textCos26= viewR.findViewById(R.id.textCosmetic26);
        textCos26.append(CosList[25]);
        textCos27= viewR.findViewById(R.id.textCosmetic27);
        textCos27.append(CosList[26]);
        textCos28= viewR.findViewById(R.id.textCosmetic28);
        textCos28.append(CosList[27]);
        textCos29= viewR.findViewById(R.id.textCosmetic29);
        textCos29.append(CosList[28]);
        textCos30= viewR.findViewById(R.id.textCosmetic30);
        textCos30.append(CosList[29]);

//        textlist = CosList[0]; //성공
//        textCos.append(textlist);

//        for(int i=0;i<30;i++){
//            Log.v("꿀 제발33",i+" " +CosList[i]);
//        }




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
                    //Log.v("꿀 제발22",Integer.toString(i)+" " +CosList[i]);
                }
            }
        };
        thread.start();
        try{
            thread.join(); //메인 스레드에서 작업 스레드의 종료를 대기함.
            Log.v("꿀 스레드 try","메인 스레드에서 작업 스레드의 종료를 대기함");
        }
        catch (InterruptedException e){
            e.printStackTrace(); //예의
            Log.v("꿀 스레드 catch","예외 ; 메인 스레드에서 작업 스레드의 종료를 대기함");
        }
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