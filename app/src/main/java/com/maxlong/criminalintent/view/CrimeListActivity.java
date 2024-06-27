package com.maxlong.criminalintent.view;

import androidx.fragment.app.Fragment;

public class CrimeListActivity extends SingleCrimeActivity {

    @Override
    protected Fragment ceateFragment() {
        return new CrimeListFragment();
    }
}