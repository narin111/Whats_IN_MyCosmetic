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
import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
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


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, FragmentCallback {
    private static final String CLOUD_VISION_API_KEY = BuildConfig.API_KEY;
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";
    private static final int MAX_LABEL_RESULTS = 10;
    private static final int MAX_DIMENSION = 1200;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    public static ingredientDBcheck check1; //ingredeintDBcheck 클래스 생성.//비교해보는 클래스
    public static SkinTypeDBcheck check2;/////////////
    public static SkinTypeDBcheck check3;
    public static allergyDBcheck checkall;

    private TextView mImageDetails;
    private ImageView mMainImage;
    private static TextView textDB;
    private static TextView textskintype;///////
    private static TextView textviewA;

    public static final String ROOT_DIR = "/data/data/com.google.sample.cloudvision/databases/";
    public static void setDB(Context ctx, String fileDB) {
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

    /////
 /*   public static void setDB_Type(Context ctx) {
        File folder2 = new File(ROOT_DIR);
        if(folder2.exists()) {
        } else {
            folder2.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();

        // db파일 이름 적어주기
        File outfile1 = new File(ROOT_DIR+"forthisType.db");
        InputStream is1 = null;
        FileOutputStream fo1 = null;
        long filesize1 = 0;
        try {
            is1 = assetManager.open("forthisType.db", AssetManager.ACCESS_BUFFER);
            filesize1 = is1.available();
            if (outfile1.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize1];
                is1.read(tempdata);
                is1.close();
                outfile1.createNewFile();
                fo1 = new FileOutputStream(outfile1);
                fo1.write(tempdata);
                fo1.close();
            } else {}
        } catch (IOException e) {

        }

        File outfile2 = new File(ROOT_DIR+"TypeCareful.db");
        InputStream is2 = null;
        FileOutputStream fo2 = null;
        long filesize2 = 0;
        try {
            is2 = assetManager.open("TypeCareful.db", AssetManager.ACCESS_BUFFER);
            filesize2 = is2.available();
            if (outfile2.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize2];
                is2.read(tempdata);
                is2.close();
                outfile2.createNewFile();
                fo2 = new FileOutputStream(outfile2);
                fo2.write(tempdata);
                fo2.close();
            } else {}
        } catch (IOException e) {

        }

    }*/
    ////
/*
    public static void setDB_All(Context ctx) {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        // db파일 이름 적어주기
        File outfileA = new File(ROOT_DIR+"allergy.db"); //// 이부분 모르겠다. helper에도 이름 추가하기?
        InputStream isA = null;
        FileOutputStream foA = null;
        long filesizeA = 0;
        try {
            isA = assetManager.open("allergy.db", AssetManager.ACCESS_BUFFER);
            filesizeA = isA.available();
            if (outfileA.length() <= 0) {
                byte[] tempdataA = new byte[(int) filesizeA];
                isA.read(tempdataA);
                isA.close();
                outfileA.createNewFile();
                foA = new FileOutputStream(outfileA);
                foA.write(tempdataA);
                foA.close();
            } else {}
        } catch (IOException e) {
            Log.v("알러지 setdb 오류: ","오류");
        }
        Log.v("알러지 setdb 함수 실행끝: ","성공");
    }
*/


    public SQLiteDatabase db;
    public Cursor cursor;
    ProductDBHelper mHelper;
    String DBname = "Harmful";
    String DBnameREC = "forthisType";
    String DBnameCARE = "Typecareful";
    String DBnameAll = "Allergy";

    String fileDBname = "harmful.db";
    String fileDBname_REC = "forthisType.db";
    String fileDBname_CARE= "TypeCareful.db";
    String fileDBname_All = "allergy.db";


    private void ShowDBInfo(String name, String fileDB){
        //Log.v("dbname: ",  name);
        setDB(this, fileDB);
        mHelper=new ProductDBHelper(this, fileDB);
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

    private void ShowDBInfo_Type(String name, String fileDB){

        Log.v("dbname: ",  name);
        Log.v("디비파일 이름 확인", fileDB);
        setDB(this, fileDB);//setDB_Type(this);
        mHelper=new ProductDBHelper(this, fileDB);
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
    //////////////////
    ////

    private void ShowDB_Allergy(String name, String fileDB){

        Log.v("dbname: ",  name);
        Log.v("디비파일 이름 확인", fileDB);
        setDB(this,fileDB);//setDB_All(this);
        mHelper=new ProductDBHelper(this, fileDB);
        db =mHelper.getReadableDatabase();

        int len=0;//배열들 길이
        String nameAcol= null;

        String sql = "Select * FROM " +name; // DBname;
        String nameAsql[] = new String[100];

        cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()) {
            nameAcol= cursor.getString(0);
            nameAsql[len] = nameAcol;
            len++;
        }

        checkall.getDBIGall(nameAsql, len);
        for(int i=0;i<len;i++) {
            Log.v("알러지 showdb", nameAsql[i]);
        }
    }

    private void InsertDBAllergy(String name, String fileDB,String userAll){
        setDB(this, fileDB);
        mHelper=new ProductDBHelper(this, fileDB);
        db =mHelper.getWritableDatabase();
        Log.v("알러지 Helper ",setAllergy);
        ContentValues values = new ContentValues();
        values.put("allergyName", setAllergy);
        db.insert(name, null, values);
        Log.v("알러지 db에 넣음: ",userAll); //로그는 출력되지만 insert가 제대로 되는지 모르겠다.
    }

    EditText edittextA; //알러지 받아오는 edittext
    String setAllergy; //알러지 담아오는 변수

    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    DrawerLayout drawer;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar); //2021.05.18 오전 12:41수정부분. 일단 run
        setSupportActionBar(toolbar);
        /////
        Thread thread = new Thread() {
            public void run() {
                ApiExamSearchShop api = new ApiExamSearchShop();
                api.main();
            }
        };
        thread.start();
        /////
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder
                    .setMessage(R.string.dialog_select_prompt)
                    .setPositiveButton(R.string.dialog_select_gallery, (dialog, which) -> startGalleryChooser())
                    .setNegativeButton(R.string.dialog_select_camera, (dialog, which) -> startCamera());
            builder.create().show();
        });

        mImageDetails = findViewById(R.id.image_details);
        mMainImage = findViewById(R.id.main_image);
        textDB = findViewById(R.id.textViewDB);
        Button buttondb = findViewById(R.id.buttonDB);
        buttondb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDBInfo(DBname, fileDBname);
                check1.IGcheck();
            }
        });
        /////
        textskintype = findViewById(R.id.textskintype);
        Button buttontypeoil = findViewById(R.id.buttonoil);
        buttontypeoil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ShowDBInfo_Type(디비이름, 추천또는주의);
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("지성(여드름) 피부", "추천");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("지성(여드름) 피부", "주의");

            }
        });
        Button buttontypedry = findViewById(R.id.buttondry);
        buttontypedry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("건성(노화) 피부", "추천");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("건성(노화) 피부", "주의");
            }
        });
        Button buttontypesens = findViewById(R.id.buttonsens);
        buttontypesens.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("민감성 피부", "추천");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("민감성 피부", "주의");
            }
        });

        //check2.IGCheck_Type("지성", "추천또는주의"); // 호출형식
        //button은 세개 지성 건성 민감성

        /////
        edittextA = findViewById(R.id.editTextAllergy); //알러지 받아오는 edittext
        Button buttonAcre = findViewById(R.id.buttonAC); //알러지 추가하는
        buttonAcre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAllergy = edittextA.getText().toString(); //알러지 이름 가져옴
                Log.v("알러지 가져옴",setAllergy);
                //알러지를 db에 추가함.
                InsertDBAllergy(DBnameAll, fileDBname_All,setAllergy);
            }
        });
        textviewA= findViewById(R.id.textViewAl); //알러지 조회내용 출력
        Button buttonAout = findViewById(R.id.buttonAget); //알러지 추가하는
        buttonAout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //알러지db내용 출력
                Log.v("알러지 shodb시작버튼:","성공");
                ShowDB_Allergy(DBnameAll, fileDBname_All);
                Log.v("알러지 shodb끝남버튼:","성공");
                //이미지 성분과 비교. allergycheck클래스만들기
                checkall.IGcheckall();
            }
        });
    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
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
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                MAX_DIMENSION);

                callCloudVision(bitmap);
                mMainImage.setImageBitmap(bitmap);

            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
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

                        String packageName = getPackageName();
                        visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                        String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

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
    @Override
    public void onBackPressed(){ //네비게이션drawer 관련 함수
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuitem) { //네비게이션drawer관련 함수
        int id= menuitem.getItemId();
        if(id==R.id.menu1){
            Toast.makeText(this,"첫번째 선택됨",Toast.LENGTH_LONG).show();
            onFragmentSelected(0,null);
        }else if(id==R.id.menu2){
            Toast.makeText(this,"두번째 선택됨",Toast.LENGTH_LONG).show();
            onFragmentSelected(1,null);
        }
        else if(id==R.id.menu3){
            Toast.makeText(this,"세번째 선택됨",Toast.LENGTH_LONG).show();
            onFragmentSelected(2,null);
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    //@Override
    public void onFragmentSelected(int position, Bundle bundle){ //네비게이션drawer관련 함수
        Fragment curFragment=null;

        if(position==0){
            curFragment=fragment1;
            toolbar.setTitle("첫번째 화면");
        }else if(position==1){
            curFragment=fragment2;
            toolbar.setTitle("두번째 화면");
        }else if(position==2){
            curFragment=fragment3;
            toolbar.setTitle("세번째 화면");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container,curFragment).commit(); //container는 어디지?

    }

    private static class LableDetectionTask extends AsyncTask<Object, Void, String> {
        private final WeakReference<MainActivity> mActivityWeakReference;
        private Vision.Images.Annotate mRequest;

        LableDetectionTask(MainActivity activity, Vision.Images.Annotate annotate) {
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
            MainActivity activity = mActivityWeakReference.get();
            if (activity != null && !activity.isFinishing()) {
                TextView imageDetail = activity.findViewById(R.id.image_details);
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

//    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
//        StringBuilder message = new StringBuilder("I found these things:\n\n");
//
//        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
//        if (labels != null) {
//            for (EntityAnnotation label : labels) {
//                message.append(String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription()));
//                message.append("\n");
//            }
//        } else {
//            message.append("nothing");
//        }
//
//        return message.toString();
//    }
    private static String convertResponseToString(BatchAnnotateImagesResponse response) {
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
        }
        /////
        //////ingredeint 배열에 성분단어 자른거 ingredientDBcheck 클래스에 보내기.
        check1.getImageIG(ingredient, ingredient.length);
        check2.getImageIG(ingredient, ingredient.length);
        checkall.getImageIGall(ingredient, ingredient.length);
        /////
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
            for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                Log.v("비교결과", "결과"+i+": "+checkIng[i]+"-유해한 이유: "+checkEff[i]+", 역할: "+checkRol[i]);
                textDB.append("결과"+(i+1)+": "+checkIng[i]+"-유해한 이유: "+checkEff[i]+", 역할: "+checkRol[i]+" \n");
            }
        }
    }
    /////////////////////////////////
    public static class allergyDBcheck{ //이미지 성분과 알러지 데이터베이스 성분들 비교하기.
        private static String imageIng[] = new String[100]; //유저의 사진에서 가져온 성분배열.[성분명]

        private static String DBAllergy[] = new String[100]; //알러지 배열.[성분명]
        private static String checkAll[] = new String[100]; //비교결과[알러지명]

        public static int imageArrLength; //이미지 성분배열 길이 저장하는 변수.
        private static int dbArrLength; //데이터베이스 성분배열 길이 저장하는 변수.
        private static int checkCount = 0; //비교결과 갯수(유해성분 개수)
        //1. 사진에서 성분가져온걸 저장하는 함수.
        public static void getImageIGall(String[] imageIG, int IGlength){
            imageArrLength = IGlength;
            for(int i=0;i<imageArrLength;i++){
                imageIng[i] = imageIG[i];
            }
            for(int i=0;i<imageArrLength;i++) {
                Log.v("알러지-이미지성분Class", imageIng[i]);
            }
        }
        //2. 데이터베이스에서 성분가져온걸 저장하는 함수.
        public static void getDBIGall(String[] dbIG, int dblength){
            dbArrLength = dblength;
            for(int i=0;i<dbArrLength;i++){
                DBAllergy[i] = dbIG[i];
            }
            for(int i=0;i<dbArrLength;i++) {
                Log.v("알러지-데이터베이스 출력Class", DBAllergy[i]);
            }
        }
        //3. 이미지 성분명과 데이터베이스 성분명 비교하고 결과 출력하는 함수.
        public static void IGcheckall(){
            for(int i=0;i<imageArrLength;i++) {
                for (int j = 0; j < dbArrLength; j++) {
                    if (imageIng[i].equals(DBAllergy[j])) { //이미지 성분명과 DB성분명 비교해서 같을때의 성분명과 유해효과 문자열 배열에 저장.
                        checkAll[checkCount] = DBAllergy[j];
                        checkCount++; //유해성분 개수
                    }
                }
            }
            for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                Log.v("알러지 비교결과Class", "결과"+i+": "+checkAll[i]);
                textviewA.append("결과"+(i+1)+": "+checkAll[i]+" \n");
            }
        }
    }
    //////

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
                textskintype.append("결과추천\n");
                for(int i=0;i<checkCount;i++) { //비교결과 로그출력.
                    Log.v("비교결과", "결과"+i+": "+checkIng[i]);
                    //textskintype.append("결과 추천:"+(i+1)+": "+checkIng[i]);
                    textskintype.append(checkIng[i]);
                }
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
    ////////////////////////////////
}
