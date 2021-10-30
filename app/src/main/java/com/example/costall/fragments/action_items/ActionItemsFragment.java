package com.example.costall.fragments.action_items;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.costall.R;

public class ActionItemsFragment extends Fragment {

    private ActionItemsViewModel mActionItemsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mActionItemsViewModel =
                ViewModelProviders.of(this).get(ActionItemsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_action_items, container, false);
        final TextView textView = root.findViewById(R.id.text_action_items);
        mActionItemsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}