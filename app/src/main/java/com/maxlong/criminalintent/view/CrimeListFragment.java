package com.maxlong.criminalintent.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstduy.R;
import com.maxlong.criminalintent.Crime;
import com.maxlong.criminalintent.CrimeLab;
import com.maxlong.criminalintent.utils.DateFormat;
import com.maxlong.criminalintent.utils.DateUtil;

import java.util.List;

public class CrimeListFragment extends Fragment {

    private RecyclerView mCrimeRecycleView;

    private CrimeAdapter mAdapter;

    private static final int REQUEST_CRIME = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crime_list, container, false);
        mCrimeRecycleView = view.findViewById(R.id.crime_recycler_view);
        mCrimeRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.getInstance(getActivity());

        if (mAdapter == null) {
            mAdapter = new CrimeAdapter(crimeLab.getCrimes());
            mCrimeRecycleView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class CrimeHolder extends RecyclerView.ViewHolder {

        public Crime mCrime;

        public CheckBox mSolvedCheckBox;

        public TextView mTitleTextView;

        public TextView mDateTextView;

        public void bindCrime(Crime crime) {
            this.mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            mDateTextView.setText(DateUtil.dateToStr(crime.getDate(), DateFormat.STYLE15));
            mSolvedCheckBox.setChecked(crime.isSolved());
        }

        public CrimeHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(view -> {
//                Intent intent = CrimeActivity.newIntent(getActivity(), mCrime.getId());
                Intent intent = CrimePagerActivity.newIntent(getActivity(), mCrime.getId());
                startActivity(intent);
            });
            mSolvedCheckBox = itemView.findViewById(R.id.list_item_crime_solved_check_box);
            mTitleTextView = itemView.findViewById(R.id.list_item_crime_title_text_view);
            mDateTextView = itemView.findViewById(R.id.list_item_crime_date_text_view);
            mSolvedCheckBox.setOnClickListener(v -> {
                mCrime.setSolved(mSolvedCheckBox.isChecked());
            });
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder> {

        private List<Crime> mCrimes;

        public CrimeAdapter(List<Crime> crimes) {
            mCrimes = crimes;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item_crime, parent, false);
            return new CrimeHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder holder, int position) {
            holder.bindCrime(mCrimes.get(position));
        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }
    }
}