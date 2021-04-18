package com.example.jorunal_bishe.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jorunal_bishe.R;
import com.example.jorunal_bishe.util.JLogUtils;

public class ShareFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_share, container, false);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        JLogUtils.getInstance().d("OnResume");
    }
}
