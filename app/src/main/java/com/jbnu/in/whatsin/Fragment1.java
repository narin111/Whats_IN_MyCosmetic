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

package com.jbnu.in.whatsin;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
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
import com.jbnu.in.whatsin.R;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


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
    public static ingredientDBcheck check1; //ingredeintDBcheck ????????? ??????.//??????????????? ?????????
    public static SkinTypeDBcheck check2;
    public static SkinTypeDBcheck check3;
    private static String[] ingScore = new String[100];
    public static int ingScorelen;
    public static int ingCheckTextCount=0;

    public static int Flag1 = 0;
    private Context context;

    private TextView mImageDetails;
    private ImageView mMainImage;
    private static TextView textDB;
    private static TextView textskintype;

    public static final String ROOT_DIR = "/data/data/com.jbnu.in.whatsin/databases/";
    public static void setDB(Fragment1 ctx, String fileDB) {
        File folder = new File(ROOT_DIR);
        if(folder.exists()) {
        } else {
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();
        // db?????? ?????? ????????????
        File outfile = new File(ROOT_DIR+fileDB);
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

        int len=0;//????????? ??????
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

    private void ShowDBInfo_Type(String name, String fileDB){

        Log.v("dbname: ",  name);
        Log.v("???????????? ?????? ??????", fileDB);
        setDB(this, fileDB);//setDB_Type(this);
        mHelper=new ProductDBHelper(getActivity(), fileDB);
        db =mHelper.getReadableDatabase();

        int len=0;//????????? ??????
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

        check2.getTypeDBIG(resNamesql, resTypesql, len);

        for(int i=0;i<len;i++) {
            //Log.v("showdbinfo_type", resTypesql[i] + ", "+resNamesql[i]);
        }

    }

    RadioGroup radiog; //???????????????

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

        context = container.getContext();

        mImageDetails = view1.findViewById(R.id.image_details);
        mMainImage = view1.findViewById(R.id.main_image);
        textDB = view1.findViewById(R.id.textViewDB);
        textskintype = view1.findViewById(R.id.textskintype);

        FloatingActionButton fab = view1.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            ingCheckTextCount=1; //0->1
            textDB.setText("");
            CropImage.activity().start(getContext(), this);
        });

        radiog = (RadioGroup) view1.findViewById(R.id.radioGroup);
        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Flag1 = 1;
                switch (checkedId){
                    case R.id.radioButton:
                        Log.v("????????? ??????", "????????????");
                        if(Flag1==0){
                            Toast.makeText(context, "????????? ????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else{
                            inputScore(100);
                            Flag1 = 0;
                            break;
                        }
                    case R.id.radioButton2:
                        Log.v("????????? ??????", "??????");
                        if(Flag1==0){
                            Toast.makeText(context, "????????? ????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else {
                            inputScore(75);
                            Flag1 = 0;
                            break;
                        }
                    case R.id.radioButton3:
                        Log.v("????????? ??????", "??????");
                        if(Flag1==0){
                            Toast.makeText(context, "????????? ????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else {
                            inputScore(50);
                            Flag1 = 0;
                            break;
                        }
                    case R.id.radioButton4:
                        Log.v("????????? ??????", "?????????");
                        if(Flag1==0){
                            Toast.makeText(context, "????????? ????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else {
                            inputScore(25);
                            Flag1 = 0;
                            break;
                        }
                    case R.id.radioButton5:
                        Log.v("????????? ??????", "?????? ?????????");
                        if(Flag1==0){
                            Toast.makeText(context, "????????? ????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        else {
                            inputScore(0);
                            Flag1 = 0;
                            break;
                        }
                }

            }
        });

        return view1;
    }

    public void onClick(View view){ ///????????? ???????????? ?????? ?????? ??? ????????? ??????
        switch(view.getId()){
            case R.id.buttonDB: ////????????? ????????????
                Log.v("????????????", "??????");
                if(Flag1==0&&ingCheckTextCount==1){
                    Log.v("???????????? 1111", "??????");
                    Toast.makeText(context, "????????? ????????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    ingCheckTextCount=1;
                }
                else if(Flag1==1&&ingCheckTextCount==1) {
                    Log.v("???????????? 222", "??????");
                    ShowDBInfo(DBname, fileDBname);
                    check1.IGcheck();//1->0
                    ShowDBscore("scoreinput");
                    ingCheckTextCount = 0; //0?????? ?????????
                }
                else if(ingCheckTextCount==0) {//(Flag1==1&&ingCheckTextCount==0) {
                    Log.v("???????????? 333", "??????");
                    Toast.makeText(getActivity(),"????????? ????????? ??????????????????!",Toast.LENGTH_SHORT).show();
                    ingCheckTextCount=0;
                }
                Log.v("???????????? ee", "??????");
                break;
            case R.id.buttonoil:
                Log.v("????????????", "??????");
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("??????(?????????) ??????", "??????");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("??????(?????????) ??????", "??????");
                break;
            case R.id.buttondry:
                Log.v("????????????", "??????");
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("??????(??????) ??????", "??????");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("??????(??????) ??????", "??????");
                break;
            case R.id.buttonsens:
                Log.v("???????????????", "??????");
                ShowDBInfo_Type(DBnameREC, fileDBname_REC);
                check2.IGcheck_Type("????????? ??????", "??????");
                ShowDBInfo_Type(DBnameCARE, fileDBname_CARE);
                check3.IGcheck_Type("????????? ??????", "??????");
                break;
            default:
                Log.v("onclick ??????", "??????");

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
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                uploadImage(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
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
        ///// ingredient ????????? ??????????????? ??????
        Log.v("?????? ", message);
        message = message.replace(", ", ","); // , ??? ?????? ?????????
        message = message.replace("\n", ""); // ???????????? ?????????
        String ingredient[] = message.split(","); // , ???????????? ?????? ?????????

        for(int i=0;i<ingredient.length; i++){
            Log.v("???????????????", ingredient[i]); // ????????? ??????
            ingScore[i]=ingredient[i];

        }

        //////ingredeint ????????? ???????????? ????????? ingredientDBcheck ???????????? ?????????.
        check1.getImageIG(ingredient, ingredient.length);
        check2.getImageIG(ingredient, ingredient.length);
        Fragment2.checkall.getImageIGall(ingredient, ingredient.length);
        ingScorelen = ingredient.length;

        return message;
    }

    public static class ingredientDBcheck{ //????????? ????????? ?????????????????? ????????? ????????????.
        private static String imageIngredient[] = new String[100]; //????????? ???????????? ????????? ????????????.[?????????]

        private static String DBIngredeint[] = new String[100]; //?????? ???????????? ???????????????????????? ????????? ?????????????????? ????????? ??????.[?????????]
        private static String DBSEffect[] = new String[200]; //?????? ???????????? ???????????????????????? ????????? ?????????????????? ????????? ??????.[????????????]
        private static String DBRole[] = new String[100]; //?????? ???????????? ???????????????????????? ????????? ????????? ??????[????????????]

        private static String checkIng[] = new String[100]; //????????????[?????????]
        private static String checkEff[] = new String[200]; //????????????[????????????]
        private static String checkRol[] = new String[100]; //????????????[??????]

        public static int imageArrLength; //????????? ???????????? ?????? ???????????? ??????.
        private static int dbArrLength; //?????????????????? ???????????? ?????? ???????????? ??????.
        private static int checkCount = 0; //???????????? ??????(???????????? ??????)
        //1. ???????????? ?????????????????? ???????????? ??????.
        public static void getImageIG(String[] imageIG, int IGlength){
            imageArrLength = IGlength;
            for(int i=0;i<imageArrLength;i++){
                imageIngredient[i] = imageIG[i];
            }
            for(int i=0;i<imageArrLength;i++) {
                Log.v("???????????????", imageIngredient[i]);
            }

            Flag1 = 1;
        }
        //2. ???????????????????????? ?????????????????? ???????????? ??????.
        //resultNamesql, resultseffectsql, resultrolesql, len
        public static void getDBIG(String[] dbIG, String[] dbSE, String[] dbRO, int dblength){

            dbArrLength = dblength;
            for(int i=0;i<dbArrLength;i++){
                DBIngredeint[i] = dbIG[i];
                DBSEffect[i] = dbSE[i];
                DBRole[i] = dbRO[i];
            }
            for(int i=0;i<dbArrLength;i++) {
                //Log.v("?????????????????? ??????", DBIngredeint[i] + ", "+DBSEffect[i]+ ", "+DBRole[i]);
            }
        }
        //3. ????????? ???????????? ?????????????????? ????????? ???????????? ?????? ???????????? ??????.
        public static void IGcheck(){
            textDB.setText("");

            for(int i=0;i<imageArrLength;i++) {
                for (int j = 0; j < dbArrLength; j++) {
                    if (imageIngredient[i].equals(DBIngredeint[j])) { //????????? ???????????? DB????????? ???????????? ???????????? ???????????? ???????????? ????????? ????????? ??????.
                        checkIng[checkCount] = DBIngredeint[j];
                        Log.v("??????", checkIng[i]+" "+DBIngredeint[j]);
                        checkEff[checkCount] = DBSEffect[j];
                        checkRol[checkCount] = DBRole[j];
                        checkCount++; //???????????? ??????
                        Log.v("????????????", checkCount+"??????");

                    }
                }
            }
            //if(checkIng.equals(null)) textDB.append("???????????? ??????????????? ????????????.\n");
            if(checkCount==0){
                textDB.append("???????????? ??????????????? ????????????.\n");
            }
            else{
                Log.v("????????????", "??????");
                for(int i=0;i<checkCount;i++) { //???????????? ????????????.
                    Log.v("????????????", "??????"+i+": "+checkIng[i]+"-????????? ??????: "+checkEff[i]+", ??????: "+checkRol[i]);
                    textDB.append("??????"+(i+1)+": "+checkIng[i]+"\n????????? ??????: "+checkEff[i]+"\n??????: "+checkRol[i]+" \n");
                }
            }

            for(int i=0;i<checkCount;i++) { //???????????? ????????????.
                //Log.v("?????? ??? ??????", "??????"+i+": "+checkIng[i]+"-????????? ??????: "+checkEff[i]+", ??????: "+checkRol[i]);
                checkIng[i]="";
                checkEff[i]="";
                checkRol[i]="";
            }

            checkCount = 0;
            Flag1 = 0;
        }
    }


    public static class SkinTypeDBcheck{ //????????? ????????? ?????????????????? ????????? ????????????.

        private static String imageIngredient[] = new String[100]; //????????? ???????????? ????????? ????????????.[?????????]

        private static String DBIngredient[] = new String[100];
        private static String DBType[] = new String[100]; //?????? ???????????? ???????????????????????? ????????? ??????/??????DB??? ??????????????????

        private static String checkIng[] = new String[100]; //????????????[?????????]

        public static int imageArrLength; //????????? ???????????? ?????? ???????????? ??????.
        private static int dbArrLength; //?????????????????? ???????????? ?????? ???????????? ??????.
        private static int checkCount = 0; //???????????? ??????
        
        //1. ???????????? ?????????????????? ???????????? ??????.
        public static void getImageIG(String[] imageIG, int IGlength){
            imageArrLength = IGlength;
            for(int i=0;i<imageArrLength;i++){
                imageIngredient[i] = imageIG[i];
            }
            for(int i=0;i<imageArrLength;i++) {
                Log.v("??????????????? ?????????????????????", imageIngredient[i]);
            }
        }
        //2. ???????????????????????? ?????????????????? ???????????? ??????.
        //resultNamesql, resultseffectsql, resultrolesql, len
        public static void getTypeDBIG(String[] dbIG, String[] dbType, int dblength){
            dbArrLength = dblength;
            for(int i=0;i<dbArrLength;i++){
                DBIngredient[i] = dbIG[i];
                DBType[i] = dbType[i];
            }


        }
        //3. ????????? ???????????? ?????????????????? ????????? ???????????? ?????? ???????????? ??????.
        //public static void IGcheck_Type(String skintype){
        public static void IGcheck_Type(String skintype, String RECorCARE){

            Log.v("?????????????????? ??????", skintype);
            for(int i=0;i<imageArrLength;i++) {
                for (int j = 0; j < dbArrLength; j++) {
                    //Log.v("??????????????????: ", DBType[i]);
                    if(skintype.equals(DBType[i])) Log.v("???????????? ?????? if: ", DBType[j] + skintype);
                    if(skintype.equals(DBType[j])){
                        //Log.v("????????????111111: ", imageIngredient[i]+DBType[j]);
                        if (imageIngredient[i].equals(DBIngredient[j])) {
                            //Log.v("??????????????????: ", imageIngredient[i]);
                            checkIng[checkCount] = DBIngredient[j];
                            checkCount++; //???????????? ??????
                        }

                    }
                }
            }
           
            if(RECorCARE=="??????"){
                textskintype.setText("");
                textskintype.append("????????????\n");
                for(int i=0;i<checkCount;i++) { //???????????? ????????????.
                    Log.v("????????????", "??????"+i+": "+checkIng[i]);
                    //textskintype.append("?????? ??????:"+(i+1)+": "+checkIng[i]);
                    if(checkIng[i]!=null){
                        textskintype.append(checkIng[i]+" ");
                    }
                }
                textskintype.append("\n");
            }
            if(RECorCARE=="??????"){
                textskintype.append("\n????????????\n");
                for(int i=0;i<checkCount;i++) { //???????????? ????????????.
                    Log.v("????????????", "??????"+i+": "+checkIng[i]);
                    if(checkIng[i]!=null){
                        textskintype.append(checkIng[i]+" ");
                    }
                }
            }
            for(int i=0;i<checkCount;i++) { //???????????? ????????????.
                Log.v("checkIng ?????????", "?????????"+i+": "+checkIng[i]);
                checkIng[i]=null;
            }

        }
    }


    public void inputScore(int inscore){
        //Log.v("??????:", String.valueOf(inscore));
        setDB(this, "scoreinput.db");
        int len = ingScorelen;
        //Log.v("???????????????????", ingScore[0]);
        mHelper=new ProductDBHelper(getActivity(), "scoreinput.db");
        db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        for(int i=0; i<len; i++){
            //Log.v("?????? ??????","????????????");///
            //Log.v("??????", ingScore[i]);
            values.put("ingredient", ingScore[i]);
            values.put("score", inscore);
            db.insert("scoreinput", null, values);
        }
        Log.v("?????????","??????");
    }

    public void CntScore(String[] nameing, int[] scoreing, int dblen){
        int len = ingScorelen; //?????????????????????
        String[] resultname = new String[100];
        int[] resultscore = new int[100];
        int cnt=0;
        int ScoreSum=0;
        for(int i=0; i<len; i++){
            for(int j=0;j<dblen;j++){ //?????????????????? ???????????? ??????????????????DB??? ?????? ????????? ?????? ???
                if(ingScore[i].equals(nameing[j])){
                    Log.v("ingScore??????",ingScore[i]);
                    cnt++;
                    ScoreSum+=scoreing[j];
                    Log.v("scoreing ?????????", String.valueOf(scoreing[j]));
                    Log.v("?????????", String.valueOf(ScoreSum));
                }
                Log.v("??????", String.valueOf(ScoreSum));
                resultname[i]=ingScore[i];
                if(cnt!=0)resultscore[i]=ScoreSum/cnt;
                else if(cnt==0) resultscore[i]=ScoreSum/1;
            }
            cnt=0;
            ScoreSum=0;
        }

        for(int j=0; j<len; j++){
            //Log.v("&&&&??????????????????&&&&", resultname[j]+resultscore[j]);
        }

        int samenum = resultname.length; //??????????????????DB??? ?????????????????? ?????? ??????
        int satisavg=0;
        for(int k=0; k<samenum; k++){
            if(resultscore[k]>=50){
                satisavg++;
                Log.v("????????? ???????", String.valueOf(satisavg));
            }
        }

        int resultnum = (satisavg*100/len); //??????????????? ??????/?????? ??????

        ////?????????????????? ????????? ??????????????? ??????
        //showRec("???????????? ????????? ???????????? ????????????"+samenum+"????????????!");
        showRec("???????????? ????????? ???????????? ???????????? \n"+resultnum+" % ?????? ???????????? ?????????!");

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

        int len=0;//????????? ??????
        String namecol= null;
        int scorecol = 0;

        String sql = "Select * FROM " + name; // DBname;
        String[] Nameing = new String[100];
        int[] Scoreing = new int[100];


        cursor = db.rawQuery(sql , null);
        while (cursor.moveToNext()) {
            Log.v("????????????", len + "?????????");
            namecol = cursor.getString(0);
            scorecol= cursor.getInt(1);

            Log.v("??????:",namecol);
            Log.v("??????", String.valueOf(scorecol));

            Nameing[len] = namecol; //???????????? ???????????? ????????? ????????????
            Scoreing[len] = scorecol; //???????????? ????????? ????????? ??????


            len++;
        }
        CntScore(Nameing, Scoreing,len);
        for(int i=0;i<len;i++) {
            Log.v("score ?????? db", Nameing[i]+String.valueOf(Scoreing[i]));
        }
    }
    private void showRec(String RecSentence){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.DialogTh);
        builder.setTitle("????????? ????????????");
        builder.setMessage(RecSentence);

        builder.setPositiveButton("???????????????!", new DialogInterface.OnClickListener(){ //????????? ??????(textview?????????)
            public void onClick(DialogInterface dialog, int which){
                String message = "???????????? ??????????????????";

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
}
