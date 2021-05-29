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

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.google.sample.cloudvision.Fragment2.checkall;


public class Fragment1 extends Fragment implements View.OnClickListener {
    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = Fragment1.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    public static ingredientDBcheck check1; //ingredeintDBcheck 클래스 생성.//비교해보는 클래스
    public static SkinTypeDBcheck check2;/////////////
    public static SkinTypeDBcheck check3;
    private static String[] ingScore = new String[100];
    public static int ingScorelen;

    //private static String[] ingScore;

    //public String[] ingScore = new String[100];

    private TextView mImageDetails;
    private ImageView mMainImage;
    private static TextView textDB;
    private static TextView textskintype;

    public static final String ROOT_DIR = "/data/data/com.google.sample.cloudvision/databases/";
    public static void setDB(Fragment1 ctx, String fileDB) {
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
    String DBname = "Harmful";
    String DBnameREC = "forthisType";
    String DBnameCARE = "Typecareful";

    String fileDBname = "harmful.db";
    String fileDBname_REC = "forthisType.db";
    String fileDBname_CARE= "TypeCareful.db";


    private void ShowDBInfo(String name, String fileDB){
        //Log.v("dbname: ",  name);
        setDB(this, fileDB);
        mHelper=new ProductDBHelper(getActivity(), fileDB);
        db =mHelper.getReadableDatabase();

        int len=0;//배열들 길이
        String namecol= null;
        String seffectcol= null;
        String rolecol = null;



        String sql = "Select * FROM " +name; // DBname;
        String resNamesql[] = new String[100];
        String reseffectsql[] = new String[200];
        String resrolesql[] = new String[100];

        cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()) {
            namecol= cursor.getString(0);
            seffectcol = cursor.getString(1);
            rolecol = cursor.getString(2);
            resNamesql[len] = namecol;
            reseffectsql[len] = seffectcol;
            resrolesql[len] = rolecol;
            len++;
            }
        //Log.v("first: " , namecol);
        check1.getDBIG(resNamesql, reseffectsql, resrolesql, len);
        //for(int i=0;i<len;i++) {
        //    Log.v("showdbinfo", resNamesql[i] + ", "+reseffectsql[i]+ ", "+ resrolesql[i]);
        //}
    }

    /////
    //
    private void ShowDBInfo_Type(String name, String fileDB){

        Log.v("dbname: ",  name);
        Log.v("디비파일 이름 확인", fileDB);
        setDB(this, fileDB);//setDB_Type(this);
        mHelper=new ProductDBHelper(getActivity(), fileDB);
        db =mHelper.getReadableDatabase();

        int len=0;//배열들 길이
        String namecol= null;
        String typecol = null;

        String sql = "Select * FROM " +name; // DBname;
        String resNamesql[] = new String[100];
        String resTypesql[] = new String[100];

        cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()) {

            typecol = cursor.getString(0);
            namecol= cursor.getString(1);

            resTypesql[len] = typecol;
            resNamesql[len] = namecol;

            len++;
        }

        check2.getTypeDBIG(resNamesql, resTypesql, len); //public static void getTypeDBIG(String[] dbIG, String[] dbType, int dblength){

        for(int i=0;i<len;i++) {
            Log.v("showdbinfo_type", resTypesql[i] + ", "+resNamesql[i]);
        }

    }

    RadioGroup radiog; //라디오그룹

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onStart();

        View view1 = inflater.inflate(R.layout.fragment1, container, false);
        Button btndb = (Button) view1.findViewById(R.id.buttonDB);
        Button btnoil = (Button) view1.findViewById(R.id.buttonoil);
        Button btndry = (Button) view1.findViewById(R.id.buttondry);
        Button btnsens = (Button) view1.findViewById(R.id.buttonsens);

        btndb.setOnClickListener(this);
        btndry.setOnClickListener(this);
        btnoil.setOnClickListener(this);
        btnsens.setOnClickListener(this);

        mImageDetails = view1.findViewById(R.id.image_details);
        mMainImage = view1.findViewById(R.id.main_image);
        textDB = view1.findViewById(R.id.textViewDB);
        textskintype = view1.findViewById(R.id.textskintype);

        FloatingActionButton fab = view1.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            //AlertDialog.Builder builder = new AlertDialog.Builder(Fragment1.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder
                    .setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery, (dialog, which) -> startGalleryChooser())
                    .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());
            builder.create().show();
        });

        radiog = (RadioGroup) view1.findViewById(R.id.radioGroup);
        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioButton:
                        Log.v("라디오 그룹", "매우만족");
                        inputScore(100);
                        //Toast.makeText(getActivity(),"매우만족 선택",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton2:
                        Log.v("라디오 그룹", "만족");
                        inputScore(75);
                        //Toast.makeText(getActivity(),"만족 선택",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton3:
                        Log.v("라디오 그룹", "ㅂㅌ");
                        inputScore(50);
                        //Toast.makeText(getActivity(),"보통 선택",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton4:
                        Log.v("라디오 그룹", "ㅂ만족");
                        inputScore(25);
                        //Toast.makeText(getActivity(),"불만족 선택",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radioButton5:
                        Log.v("라디오 그룹", "매우 ㅂ만족");
                        inputScore(1);
                        //Toast.makeText(getActivity(),"매우 불만족 선택",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });

        return view1;
    }

    public void onClick(View view){ ///추천도 성분조회 버튼 클릭 시 알림창 띄움
        switch(view.getId()){
            case R.id.buttonDB: ////앱종료 방지하기
                Log.v("디비버튼", "눌림");
                ShowDBInfo(DBname, fileDBname);
                check1.IGcheck();
                ShowDBscore("scoreinput");////
                break;
            case R.id.buttonoil:
                Log.v("지성버튼", "눌림");
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("지성(여드름) 피부", "추천");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("지성(여드름) 피부", "주의");
                break;
            case R.id.buttondry:
                Log.v("건성버튼", "눌림");
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("건성(노화) 피부", "추천");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("건성(노화) 피부", "주의");
                break;
            case R.id.buttonsens:
                Log.v("민감성버튼", "눌림");
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("민감성 피부", "추천");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("민감성 피부", "주의");
                break;
            default:
                Log.v("onclick 에러", "에러");

        }
    }


    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(getActivity(), GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                getActivity(),
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(getActivity(),  getActivity().getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri),
                                MAX_DIMENSION);

                callCloudVision(bitmap);
                mMainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(getActivity(), R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(getActivity(), R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    private Vision.Images.Annotate prepareAnnotationRequest(Bitmap bitmap) throws IOException {
        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        VisionRequestInitializer requestInitializer =
                new VisionRequestInitializer(CLOUD_VISION_API_KEY) {
                    /**
                     * We override this so we can inject important identifying fields into the HTTP
                     * headers. This enables use of a restricted cloud platform API key.
                     */
                    @Override
                    protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                            throws IOException {
                        super.initializeVisionRequest(visionRequest);

                        String packageName = getActivity().getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getActivity().getPackageManager(), packageName);

                        visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                    }
                };

        Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
        builder.setVisionRequestInitializer(requestInitializer);

        Vision vision = builder.build();

        BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                new BatchAnnotateImagesRequest();
        batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
            AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

            // Add the image
            Image base64EncodedImage = new Image();
            // Convert the bitmap to a JPEG
            // Just in case it's a format that Android understands but Cloud Vision
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();

            // Base64 encode the JPEG
            base64EncodedImage.encodeContent(imageBytes);
            annotateImageRequest.setImage(base64EncodedImage);

            // add the features we want
            annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                Feature labelDetection = new Feature();
                //labelDetection.setType("LABEL_DETECTION");
                labelDetection.setType("TEXT_DETECTION");
                labelDetection.setMaxResults(MAX_LABEL_RESULTS);
                add(labelDetection);
            }});

            // Add the list of one thing to the request
            add(annotateImageRequest);
        }});

        Vision.Images.Annotate annotateRequest =
                vision.images().annotate(batchAnnotateImagesRequest);
        // Due to a bug: requests to Vision API containing large images fail when GZipped.
        annotateRequest.setDisableGZipContent(true);
        Log.d(TAG, "created Cloud Vision request object, sending request");

        return annotateRequest;
    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<Fragment1> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LableDetectionTask(Fragment1 activity, Vision.Images.Annotate annotate) {
            mActivityWeakReference = new WeakReference<>(activity);
            mRequest = annotate;
        }

        @Override
        protected String doInBackground(Object... params) {
            try {
                Log.d(TAG, "created Cloud Vision request object, sending request");
                BatchAnnotateImagesResponse response = mRequest.execute();
                return convertResponseToString(response);

            } catch (GoogleJsonResponseException e) {
                Log.d(TAG, "failed to make API request because " + e.getContent());
            } catch (IOException e) {
                Log.d(TAG, "failed to make API request because of other IOException " +
                        e.getMessage());
            }
            return "Cloud Vision API request failed. Check logs for details.";
        }

        protected void onPostExecute(String result) {
            Fragment1 activity = mActivityWeakReference.get();
            if (activity != null && !activity.getActivity().isFinishing()) {
                TextView imageDetail = activity.getActivity().findViewById(R.id.image_details);
                imageDetail.setText(result);
            }
        }
    }

    private void callCloudVision(final Bitmap bitmap) {
        // Switch text to loading
        mImageDetails.setText(R.string.loading_message);

        // Do the real work in an async task, because we need to use the network anyway
        try {
            AsyncTask<Object, Void, String> labelDetectionTask = new LableDetectionTask(this, prepareAnnotationRequest(bitmap));
            labelDetectionTask.execute();
        } catch (IOException e) {
            Log.d(TAG, "failed to make API request because of other IOException " +
                    e.getMessage());
        }
    }

    private Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }


    public static String convertResponseToString(BatchAnnotateImagesResponse response) {
        String message = "I found these things:\n\n";
        List<EntityAnnotation> labels = response.getResponses().get(0).getTextAnnotations();
        if (labels != null) {
            message  = labels.get(0).getDescription();
        } else {
            message  = "nothing";
        }
        ///// ingredient 배열에 단어자른거 저장
        Log.v("출력 ", message);
        message = message.replace(", ", ","); // , 뒤 공백 없애기
        message = message.replace("\n", ""); // 개행문자 없애기
        String ingredient[] = message.split(","); // , 기준으로 단어 자르기 // ,으로 표기 안했다면...?

        for(int i=0;i<ingredient.length; i++){
            Log.v("단어자르기", ingredient[i]); // 로그로 확인
            ingScore[i]=ingredient[i];

        }
        /////
        //////ingredeint 배열에 성분단어 자른거 ingredientDBcheck 클래스에 보내기.
        check1.getImageIG(ingredient, ingredient.length);
        check2.getImageIG(ingredient, ingredient.length);
        checkall.getImageIGall(ingredient, ingredient.length);
        ingScorelen = ingredient.length;

        /////통계결과showdbscore부르기///
        //ShowDBscore("scoreinput");
        return message;
    }
    /////////////////////////////////
    //효민
    public static class ingredientDBcheck{ //이미지 성분과 데이터베이스 성분들 비교하기. //샘플코드.public?private?
        private static String imageIngredient[] = new String[100]; //유저의 사진에서 가져온 성분배열.[성분명]

        private static String DBIngredeint[] = new String[100]; //미리 생성해둔 데이터베이스에서 가져온 유해성분이나 알러지 배열.[성분명]
        private static String DBSEffect[] = new String[200]; //미리 생성해둔 데이터베이스에서 가져온 유해성분이나 알러지 배열.[유해성분]
        private static String DBRole[] = new String[100]; //미리 생성해둔 데이터베이스에서 가져온 성분의 역할[유해성분]

        private static String checkIng[] = new String[100]; //비교결과[성분명]
        private static String checkEff[] = new String[200]; //비교결과[유해성분]
        private static String checkRol[] = new String[100]; //비교결과[역할]

        public static int imageArrLength; //이미지 성분배열 길이 저장하는 변수.
        private static int dbArrLength; //데이터베이스 성분배열 길이 저장하는 변수.
        private static int checkCount = 0; //비교결과 갯수(유해성분 개수)
        //1. 사진에서 성분가져온걸 저장하는 함수.
        public static void getImageIG(String[] imageIG, int IGlength){
            imageArrLength = IGlength;
            for(int i=0;i<imageArrLength;i++){
                imageIngredient[i] = imageIG[i];
            }
            for(int i=0;i<imageArrLength;i++) {
                Log.v("이미지성분", imageIngredient[i]);
            }
        }
        //2. 데이터베이스에서 성분가져온걸 저장하는 함수.
        //resultNamesql, resultseffectsql, resultrolesql, len
        public static void getDBIG(String[] dbIG, String[] dbSE, String[] dbRO, int dblength){

            dbArrLength = dblength;
            for(int i=0;i<dbArrLength;i++){
                DBIngredeint[i] = dbIG[i];
                DBSEffect[i] = dbSE[i];
                DBRole[i] = dbRO[i];
            }
            for(int i=0;i<dbArrLength;i++) {
                Log.v("데이터베이스 출력", DBIngredeint[i] + ", "+DBSEffect[i]+ ", "+DBRole[i]);
            }
        }
        //3. 이미지 성분명과 데이터베이스 성분명 비교하고 결과 출력하는 함수.
        public static void IGcheck(){
            textDB.setText("");
            for(int i=0;i<imageArrLength;i++) {
                for (int j = 0; j < dbArrLength; j++) {
                    if (imageIngredient[i].equals(DBIngredeint[j])) { //이미지 성분명과 DB성분명 비교해서 같을때의 성분명과 유해효과 문자열 배열에 저장.
                        checkIng[checkCount] = DBIngredeint[j];
                        checkEff[checkCount] = DBSEffect[j];
                        checkRol[checkCount] = DBRole[j];
                        checkCount++; //유해성분 개수
                    }
                }
            }
            if(checkIng[0]==null) textDB.append("의심되는 유해성분이 없습니다.\n");
            for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                Log.v("비교결과", "결과"+i+": "+checkIng[i]+"-유해한 이유: "+checkEff[i]+", 역할: "+checkRol[i]);
                textDB.append("결과"+(i+1)+": "+checkIng[i]+"-유해한 이유: "+checkEff[i]+", 역할: "+checkRol[i]+" \n");
            }
        }
    }

    /////////////////////////////////
    //나린 //안드로이드 오류해결 확인
    //나린

    public static class SkinTypeDBcheck{ //이미지 성분과 데이터베이스 성분들 비교하기.

        private static String imageIngredient[] = new String[100]; //유저의 사진에서 가져온 성분배열.[성분명]

        private static String DBIngredient[] = new String[100];
        private static String DBType[] = new String[100]; //미리 생성해둔 데이터베이스에서 가져온 추천/주의DB의 피부타입종류

        private static String checkIng[] = new String[100]; //비교결과[성분명]
        //private static String checkEff[] = new String[200]; //비교결과[유해성분]

        public static int imageArrLength; //이미지 성분배열 길이 저장하는 변수.
        private static int dbArrLength; //데이터베이스 성분배열 길이 저장하는 변수.
        private static int checkCount = 0; //비교결과 개수
        
        //1. 사진에서 성분가져온걸 저장하는 함수.
        public static void getImageIG(String[] imageIG, int IGlength){
            imageArrLength = IGlength;
            for(int i=0;i<imageArrLength;i++){
                imageIngredient[i] = imageIG[i];
            }
            for(int i=0;i<imageArrLength;i++) {
                Log.v("이미지성분 피부타입클래스", imageIngredient[i]);
            }
        }
        //2. 데이터베이스에서 성분가져온걸 저장하는 함수.
        //resultNamesql, resultseffectsql, resultrolesql, len
        public static void getTypeDBIG(String[] dbIG, String[] dbType, int dblength){
            //public static void getTypeDBIG(String[] dbIG, String[] dbType, int dblength, String RECorCARE){
            dbArrLength = dblength;
            for(int i=0;i<dbArrLength;i++){
                DBIngredient[i] = dbIG[i];
                DBType[i] = dbType[i];
                //DBSEffect[i] = dbSE[i];
                //DBRole[i] = dbRO[i];
            }


        }
        //3. 이미지 성분명과 데이터베이스 성분명 비교하고 결과 출력하는 함수.
        //public static void IGcheck_Type(String skintype){
        public static void IGcheck_Type(String skintype, String RECorCARE){

            Log.v("타입파라미터 확인", skintype);
            for(int i=0;i<imageArrLength;i++) {
                for (int j = 0; j < dbArrLength; j++) {
                    //Log.v("디비전체출력: ", DBType[i]);
                    if(skintype.equals(DBType[i])) Log.v("타입일치 확인 if: ", DBType[j] + skintype);
                    //if (imageIngredient[i].equals(DBIngredient[j]) && skintype.equals(DBType[i])) { //이미지, DB성분명 비교해서 문자열 배열에 저장
                    //if (imageIngredient[i].equals(DBIngredient[j])) {
                    if(skintype.equals(DBType[j])){
                        Log.v("출력출력111111: ", imageIngredient[i]+DBType[j]);
                        //if(skintype.equals(DBType[j])){
                        if (imageIngredient[i].equals(DBIngredient[j])) {
                            Log.v("출력출력출력: ", imageIngredient[i]);
                            checkIng[checkCount] = DBIngredient[j];
                            checkCount++; //유해성분 개수
                        }

                    }
                }
            }
           
            if(RECorCARE=="추천"){
                textskintype.setText("");
                textskintype.append("결과추천\n");
                for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                    Log.v("비교결과", "결과"+i+": "+checkIng[i]);
                    //textskintype.append("결과 추천:"+(i+1)+": "+checkIng[i]);
                    textskintype.append(checkIng[i]);
                }
                textskintype.append("\n");
            }
            if(RECorCARE=="주의"){
                textskintype.append("\n결과주의\n");
                for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                    Log.v("비교결과", "결과"+i+": "+checkIng[i]);
                    textskintype.append(checkIng[i]);
                }
            }
            for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                Log.v("checkIng 초기화", "초기화"+i+": "+checkIng[i]);
                checkIng[i]="";
            }
            ////
        }
    }


    public void inputScore(int inscore){
        //Log.v("점수:", String.valueOf(inscore));
        setDB(this, "scoreinput.db");
        int len = ingScorelen;
        Log.v("성분잘받아짐?", ingScore[0]);
        mHelper=new ProductDBHelper(getActivity(), "scoreinput.db");
        db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0; i<len; i++){
            Log.v("포문 확인","확인확인");///
            //Log.v("출력", ingScore[i]);
            values.put("ingredient", ingScore[i]);
            values.put("score", inscore);
            db.insert("scoreinput", null, values);
        }
        Log.v("포문끝","확인");
        //ShowDBscore("scoreinput");
    }

    public void CntScore(String[] nameing, int[] scoreing, int dblen){
        int len = ingScorelen; //이미지성분길이
        String[] resultname = new String[100];
        int[] resultscore = new int[100];
        int cnt=0;
        int ScoreSum=0;
        for(int i=0; i<len; i++){
            for(int j=0;j<dblen;j++){ //성분이미지랑 비교해서 사용자만족도DB와 같은 성분만 모두 합
                if(ingScore[i].equals(nameing[j])){
                    Log.v("ingScore검사",ingScore[i]);
                    cnt++;
                    ScoreSum+=scoreing[j];
                    Log.v("scoreing 더해짐", String.valueOf(scoreing[j]));
                    Log.v("더해짐", String.valueOf(ScoreSum));
                }
                Log.v("총점", String.valueOf(ScoreSum));
                resultname[i]=ingScore[i];
                if(cnt!=0)resultscore[i]=ScoreSum/cnt;
                else if(cnt==0) resultscore[i]=ScoreSum/1;
            }
            cnt=0;
            ScoreSum=0;
        }

        for(int j=0; j<len; j++){
            Log.v("&&&&점수통계결과&&&&", resultname[j]+resultscore[j]);
        }

        int samenum = resultname.length; //사용자만족도DB랑 사진성분이랑 같은 개수
        int satisavg=0;
        for(int k=0; k<samenum; k++){
            if(resultscore[k]>=50){
                satisavg++;
                Log.v("얼마나 포함?", String.valueOf(satisavg));
            }
        }

        int resultnum = (satisavg*100/len); //만족도높은 성분/전체 비율
        //String result = String.format("%.2f", resultnum);

        ////성분이미지와 얼마나 일치하는지 비교
        //showRec("만족도가 높았던 화장품의 성분들이"+samenum+"일치해요!");
        showRec("만족도가 높았던 화장품의 성분들이 \n"+resultnum+" % 만큼 포함되어 있어요!");

        for(int j=0; j<len; j++){
            resultname[j]="";
            resultscore[j]=0;
        }



    }


    private void ShowDBscore(String name){

        Log.v("dbname: ",  "score");

        setDB(this, "scoreinput.db");//setDB_Type(this);
        mHelper=new ProductDBHelper(getActivity(), "scoreinput.db");
        db =mHelper.getReadableDatabase();

        int len=0;//배열들 길이
        String namecol= null;
        int scorecol = 0;

        String sql = "Select * FROM " + name; // DBname;
        String[] Nameing = new String[100];
        int[] Scoreing = new int[100];


        cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()) {
            Log.v("여기까지", len + "가나요");
            namecol = cursor.getString(0);
            scorecol= cursor.getInt(1);

            Log.v("타입:",namecol);
            Log.v("점수", String.valueOf(scorecol));

            Nameing[len] = namecol; //사용자가 사용했던 화장품 성분배열
            Scoreing[len] = scorecol; //사용자가 입력한 만족도 점수


            len++;
        }
        CntScore(Nameing, Scoreing,len);
        for(int i=0;i<len;i++) {
            Log.v("score 성분 db", Nameing[i]+String.valueOf(Scoreing[i]));
        }
    }
    private void showRec(String RecSentence){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTh);
        builder.setTitle("사용자 맞춤추천");
        builder.setMessage(RecSentence);

        //builder.setIcon(R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인했어요!", new DialogInterface.OnClickListener(){ //실행은 안됨(textview지저분)
            public void onClick(DialogInterface dialog, int which){
                String message = "추천도를 확인했습니다";

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    ////////////////////////////////
}
