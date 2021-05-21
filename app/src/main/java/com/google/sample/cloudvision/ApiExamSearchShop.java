package com.google.sample.cloudvision;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiExamSearchShop extends Thread{


    public static CosmeticList tlist; //리스트 목록을 가져올 수 있는 클래스
    public static String[] title=new String[100];

    public static void main(String query) { //void main(String query) {

        String clientId = "OSj0lA9NwIVPu55cGHBj"; //애플리케이션 클라이언트 아이디값"
        String clientSecret = "tZQxrVxI8k"; //애플리케이션 클라이언트 시크릿값"

        String text = null;
        try {
            text = URLEncoder.encode(query+" 화장품", "UTF-8"); //("알로에 화장품", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        //100개 크롤링
        String apiURL = "https://openapi.naver.com/v1/search/shop?query=" + text +"&display=30&start=1";    // json 결과
        //String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; // xml 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        for(int i=0;i<title.length;i++){
                Log.v("출력 data -for문: ", title[i]);
          }

        //System.out.println(responseBody);
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                Log.v("정상호출", "야호");
                return readBody(con.getInputStream());
            } else { // 에러 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;

            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line).append("\n");
            }

            //Log.v("출력: ", responseBody.toString());

            String data = responseBody.toString(); // 전체 출력내용을 단순히 배열에 저장함. <b>같은 문자들도 함께 됨.
            Log.v("출력 data: ", data);

            String[] array;
            array = data.split("\"");
            //String[] title=new String[100];
            int k=0;
            for(int i=0;i<array.length;i++){
                if(array[i].equals("title")){
                    title[k] = array[i+2];
                    title[k] = title[k].replace("<b>", "").replace("</b>",""); // <b>, </b> 없애기
                    Log.v("출력 data -for문: ", String.valueOf(k)+": "+title[k]);
                    k++;
                }
            }
            tlist.getcosTitle(title,k);
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }

    public static class CosmeticList{ //화장품 목록을 가져오도록 구성한 클래스. readBody에서 호출함.
        public static String[] costitle=new String[100];
        public static int costitlelength;
        public static void getcosTitle(String[] ctitle, int ctlength){
            costitlelength = ctlength;
            for(int i=0;i<costitlelength;i++){
                costitle[i] = ctitle[i];
            }
            for(int i=0;i<costitlelength;i++) {
                Log.v("ApiExamSearchShop 클래스 Class", costitle[i]);
            }
        }
    }

}