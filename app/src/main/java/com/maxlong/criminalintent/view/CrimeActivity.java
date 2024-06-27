package com.maxlong.criminalintent.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import java.util.UUID;

public class CrimeActivity extends SingleCrimeActivity {

    public static final String EXTRA_CRIME_ID = "extra_crime_id";

    public static Intent newIntent(Context context, UUID id) {
        Intent intent = new Intent(context, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, id);
        return intent;
    }

    @Override
    protected Fragment ceateFragment() {
        UUID uuid = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        Intent data = new Intent();
        data.putExtra(EXTRA_CRIME_ID, uuid);
        setResult(Activity.RESULT_OK, data);
        return CrimeFragment.newIntance(uuid);
    }
}