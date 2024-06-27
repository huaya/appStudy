package com.maxlong.criminalintent.view;


import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appstduy.R;
import com.maxlong.criminalintent.Crime;
import com.maxlong.criminalintent.CrimeLab;
import com.maxlong.criminalintent.utils.DateFormat;
import com.maxlong.criminalintent.utils.DateUtil;

import java.util.UUID;

public class CrimeFragment extends Fragment {

    public static final String ARGS_CRIME_ID = "crime_id";

    private Crime mCrime;

    private EditText mTitleField;

    private Button mCrimeDate;

    private CheckBox mCrimeSolved;

    public static Fragment newIntance(UUID uuid) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ARGS_CRIME_ID, uuid);

        CrimeFragment crimeFragment = new CrimeFragment();
        crimeFragment.setArguments(bundle);
        return crimeFragment;
    }

    public void returnResult() {
        requireActivity().setResult(Activity.RESULT_OK, null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            UUID uuid = (UUID) getArguments().get(ARGS_CRIME_ID);
            mCrime = CrimeLab.getInstance(getActivity()).getCrime(uuid);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime, container, false);
        mTitleField = v.findViewById(R.id.crime_title);
        mCrimeDate = v.findViewById(R.id.crime_date);
        mCrimeSolved = v.findViewById(R.id.crime_solved);

        if (mCrime == null) {
            mCrime = new Crime();
        }

        mCrimeDate.setText(DateUtil.dateToStr(mCrime.getDate(), DateFormat.STYLE15));
        mCrimeDate.setEnabled(false);

        mCrimeSolved.setChecked(mCrime.isSolved());
        mCrimeSolved.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mCrime.setSolved(isChecked);
        });

        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return v;
    }
}
