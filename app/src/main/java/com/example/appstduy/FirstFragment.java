package com.example.appstduy;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.navigation.fragment.NavHostFragment;

import com.example.appstduy.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(view1 -> NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment));

        binding.toQuiz.setOnClickListener(v -> NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_To_Quiz));

        binding.toHilink.setOnClickListener(v -> NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_To_hilink));

        binding.toDashboard.setOnClickListener(v -> NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_To_Dashboard));
        binding.toCrimeList.setOnClickListener(v -> NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_CrimeListActivity));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}