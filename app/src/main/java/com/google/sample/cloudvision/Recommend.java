package com.google.sample.cloudvision;

import android.content.Intent;
import android.net.Uri;
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
    public static String[] CosLinkList =new String[100];//화장품 각 링크 목록, ApiExamSearchShop.java의 link


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInastanceState){
        super.onStart();

        View viewR = inflater.inflate(R.layout.recommend, container, false);

        textCos1= viewR.findViewById(R.id.textCosmetic1);
        textCos1.append(CosList[0]+"\n");
        textCos2= viewR.findViewById(R.id.textCosmetic2);
        textCos2.append(CosList[1]+"\n");
        textCos3= viewR.findViewById(R.id.textCosmetic3);
        textCos3.append(CosList[2]+"\n");
        textCos4= viewR.findViewById(R.id.textCosmetic4);
        textCos4.append(CosList[3]+"\n");
        textCos5= viewR.findViewById(R.id.textCosmetic5);
        textCos5.append(CosList[4]+"\n");
        textCos6= viewR.findViewById(R.id.textCosmetic6);
        textCos6.append(CosList[5]+"\n");
        textCos7= viewR.findViewById(R.id.textCosmetic7);
        textCos7.append(CosList[6]+"\n");
        textCos8= viewR.findViewById(R.id.textCosmetic8);
        textCos8.append(CosList[7]+"\n");
        textCos9= viewR.findViewById(R.id.textCosmetic9);
        textCos9.append(CosList[8]+"\n");
        textCos10= viewR.findViewById(R.id.textCosmetic10);
        textCos10.append(CosList[9]+"\n");
        textCos11= viewR.findViewById(R.id.textCosmetic11);
        textCos11.append(CosList[10]+"\n");
        textCos12= viewR.findViewById(R.id.textCosmetic12);
        textCos12.append(CosList[11]+"\n");
        textCos13= viewR.findViewById(R.id.textCosmetic13);
        textCos13.append(CosList[12]+"\n");
        textCos14= viewR.findViewById(R.id.textCosmetic14);
        textCos14.append(CosList[13]+"\n");
        textCos15= viewR.findViewById(R.id.textCosmetic15);
        textCos15.append(CosList[14]+"\n");
        textCos16= viewR.findViewById(R.id.textCosmetic16);
        textCos16.append(CosList[15]+"\n");
        textCos17= viewR.findViewById(R.id.textCosmetic17);
        textCos17.append(CosList[16]+"\n");
        textCos18= viewR.findViewById(R.id.textCosmetic18);
        textCos18.append(CosList[17]+"\n");
        textCos19= viewR.findViewById(R.id.textCosmetic19);
        textCos19.append(CosList[18]+"\n");
        textCos20= viewR.findViewById(R.id.textCosmetic20);
        textCos20.append(CosList[19]+"\n");
        textCos21= viewR.findViewById(R.id.textCosmetic21);
        textCos21.append(CosList[20]+"\n");
        textCos22= viewR.findViewById(R.id.textCosmetic22);
        textCos22.append(CosList[21]+"\n");
        textCos23= viewR.findViewById(R.id.textCosmetic23);
        textCos23.append(CosList[22]+"\n");
        textCos24= viewR.findViewById(R.id.textCosmetic24);
        textCos24.append(CosList[23]+"\n");
        textCos25= viewR.findViewById(R.id.textCosmetic25);
        textCos25.append(CosList[24]+"\n");
        textCos26= viewR.findViewById(R.id.textCosmetic26);
        textCos26.append(CosList[25]+"\n");
        textCos27= viewR.findViewById(R.id.textCosmetic27);
        textCos27.append(CosList[26]+"\n");
        textCos28= viewR.findViewById(R.id.textCosmetic28);
        textCos28.append(CosList[27]+"\n");
        textCos29= viewR.findViewById(R.id.textCosmetic29);
        textCos29.append(CosList[28]+"\n");
        textCos30= viewR.findViewById(R.id.textCosmetic30);
        textCos30.append(CosList[29]+"\n");

        Button button1 = (Button) viewR.findViewById(R.id.btnCS1);
        button1.setOnClickListener(this);
        Button button2 = (Button) viewR.findViewById(R.id.btnCS2);
        button2.setOnClickListener(this);
        Button button3 = (Button) viewR.findViewById(R.id.btnCS3);
        button3.setOnClickListener(this);
        Button button4 = (Button) viewR.findViewById(R.id.btnCS4);
        button4.setOnClickListener(this);
        Button button5 = (Button) viewR.findViewById(R.id.btnCS5);
        button5.setOnClickListener(this);
        Button button6 = (Button) viewR.findViewById(R.id.btnCS6);
        button6.setOnClickListener(this);
        Button button7 = (Button) viewR.findViewById(R.id.btnCS7);
        button7.setOnClickListener(this);
        Button button8 = (Button) viewR.findViewById(R.id.btnCS8);
        button8.setOnClickListener(this);
        Button button9 = (Button) viewR.findViewById(R.id.btnCS9);
        button9.setOnClickListener(this);
        Button button10 = (Button) viewR.findViewById(R.id.btnCS10);
        button10.setOnClickListener(this);
        Button button11 = (Button) viewR.findViewById(R.id.btnCS11);
        button11.setOnClickListener(this);
        Button button12 = (Button) viewR.findViewById(R.id.btnCS12);
        button12.setOnClickListener(this);
        Button button13 = (Button) viewR.findViewById(R.id.btnCS13);
        button13.setOnClickListener(this);
        Button button14 = (Button) viewR.findViewById(R.id.btnCS14);
        button14.setOnClickListener(this);
        Button button15 = (Button) viewR.findViewById(R.id.btnCS15);
        button15.setOnClickListener(this);
        Button button16 = (Button) viewR.findViewById(R.id.btnCS16);
        button16.setOnClickListener(this);
        Button button17 = (Button) viewR.findViewById(R.id.btnCS17);
        button17.setOnClickListener(this);
        Button button18 = (Button) viewR.findViewById(R.id.btnCS18);
        button18.setOnClickListener(this);
        Button button19 = (Button) viewR.findViewById(R.id.btnCS19);
        button19.setOnClickListener(this);
        Button button20 = (Button) viewR.findViewById(R.id.btnCS20);
        button20.setOnClickListener(this);
        Button button21 = (Button) viewR.findViewById(R.id.btnCS21);
        button21.setOnClickListener(this);
        Button button22 = (Button) viewR.findViewById(R.id.btnCS22);
        button22.setOnClickListener(this);
        Button button23 = (Button) viewR.findViewById(R.id.btnCS23);
        button23.setOnClickListener(this);
        Button button24 = (Button) viewR.findViewById(R.id.btnCS24);
        button24.setOnClickListener(this);
        Button button25 = (Button) viewR.findViewById(R.id.btnCS25);
        button25.setOnClickListener(this);
        Button button26 = (Button) viewR.findViewById(R.id.btnCS26);
        button26.setOnClickListener(this);
        Button button27 = (Button) viewR.findViewById(R.id.btnCS27);
        button27.setOnClickListener(this);
        Button button28 = (Button) viewR.findViewById(R.id.btnCS28);
        button28.setOnClickListener(this);
        Button button29 = (Button) viewR.findViewById(R.id.btnCS29);
        button29.setOnClickListener(this);
        Button button30 = (Button) viewR.findViewById(R.id.btnCS30);
        button30.setOnClickListener(this);

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
                for(int i=0;i<CosmeticList.coslinklength;i++){
                    //Log.v("꿀 제발11",CosmeticList.costitle[i]);
                    CosLinkList[i] = CosmeticList.coslink[i];
                    Log.v("꿀 제발22",Integer.toString(i)+" " + CosLinkList[i]);
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
            case R.id.btnCS1:
                Log.v("버튼 눌림","첫번째 웹뷰");
                //웹뷰띄우기
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[0]));
                startActivity(intent);
                break;
            case R.id.btnCS2:
                Log.v("버튼 눌림","두번째 웹뷰");
                //웹뷰띄우기
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[1]));
                startActivity(intent2);
                break;
            case R.id.btnCS3:
                Log.v("버튼 눌림","세번째 웹뷰");
                //웹뷰띄우기
                Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[2]));
                startActivity(intent3);
                break;
            case R.id.btnCS4:
                //웹뷰띄우기
                Intent intent4 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[3]));
                startActivity(intent4);
                break;
            case R.id.btnCS5:
                //웹뷰띄우기
                Intent intent5 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[4]));
                startActivity(intent5);
                break;
            case R.id.btnCS6:
                //웹뷰띄우기
                Intent intent6 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[5]));
                startActivity(intent6);
                break;
            case R.id.btnCS7:
                //웹뷰띄우기
                Intent intent7 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[6]));
                startActivity(intent7);
                break;
            case R.id.btnCS8:
                //웹뷰띄우기
                Intent intent8 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[7]));
                startActivity(intent8);
                break;
            case R.id.btnCS9:
                //웹뷰띄우기
                Intent intent9 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[8]));
                startActivity(intent9);
                break;
            case R.id.btnCS10:
                //웹뷰띄우기
                Intent intent10 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[9]));
                startActivity(intent10);
                break;
            case R.id.btnCS11:
                //웹뷰띄우기
                Intent intent11 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[10]));
                startActivity(intent11);
                break;
            case R.id.btnCS12:
                //웹뷰띄우기
                Intent intent12 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[11]));
                startActivity(intent12);
                break;
            case R.id.btnCS13:
                //웹뷰띄우기
                Intent intent13 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[12]));
                startActivity(intent13);
                break;
            case R.id.btnCS14:
                //웹뷰띄우기
                Intent intent14 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[13]));
                startActivity(intent14);
                break;
            case R.id.btnCS15:
                //웹뷰띄우기
                Intent intent15 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[14]));
                startActivity(intent15);
                break;
            case R.id.btnCS16:
                //웹뷰띄우기
                Intent intent16 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[15]));
                startActivity(intent16);
                break;
            case R.id.btnCS17:
                //웹뷰띄우기
                Intent intent17 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[16]));
                startActivity(intent17);
                break;
            case R.id.btnCS18:
                //웹뷰띄우기
                Intent intent18 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[17]));
                startActivity(intent18);
                break;
            case R.id.btnCS19:
                //웹뷰띄우기
                Intent intent19 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[18]));
                startActivity(intent19);
                break;
            case R.id.btnCS20:
                //웹뷰띄우기
                Intent intent20 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[19]));
                startActivity(intent20);
                break;
            case R.id.btnCS21:
                //웹뷰띄우기
                Intent intent21 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[20]));
                startActivity(intent21);
                break;
            case R.id.btnCS22:
                //웹뷰띄우기
                Intent intent22 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[21]));
                startActivity(intent22);
                break;
            case R.id.btnCS23:
                //웹뷰띄우기
                Intent intent23 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[22]));
                startActivity(intent23);
                break;
            case R.id.btnCS24:
                //웹뷰띄우기
                Intent intent24 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[23]));
                startActivity(intent24);
                break;
            case R.id.btnCS25:
                //웹뷰띄우기
                Intent intent25 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[24]));
                startActivity(intent25);
                break;
            case R.id.btnCS26:
                //웹뷰띄우기
                Intent intent26 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[25]));
                startActivity(intent26);
                break;
            case R.id.btnCS27:
                //웹뷰띄우기
                Intent intent27 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[26]));
                startActivity(intent27);
                break;
            case R.id.btnCS28:
                //웹뷰띄우기
                Intent intent28 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[27]));
                startActivity(intent28);
                break;
            case R.id.btnCS29:
                //웹뷰띄우기
                Intent intent29 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[28]));
                startActivity(intent29);
                break;
            case R.id.btnCS30:
                //웹뷰띄우기
                Intent intent30 = new Intent(Intent.ACTION_VIEW, Uri.parse(CosLinkList[29]));
                startActivity(intent30);
                break;
            default:
                Log.v("onclick 에러", "에러");
        }
    }



}