package com.google.sample.cloudvision;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {
    MainActivity activity;
    //화면이 붙을때 작동하는 메서드
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //현재 소속된 액티비티를 메인 액티비티로 한다.
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment1, container, false); //container가 뭐지?
    }

    /*
    //교재309p, 299p 참조 mainactivity
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);
        image
     */
}
