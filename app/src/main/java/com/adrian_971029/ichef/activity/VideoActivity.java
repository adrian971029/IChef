package com.adrian_971029.ichef.activity;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;

import com.adrian_971029.ichef.R;
import com.adrian_971029.ichef.fragment.VideoFragment;
import com.adrian_971029.ichef.model.Step;

import butterknife.ButterKnife;

public class VideoActivity extends BaseActivity {

    private static final String STEPS = "steps";

    private Step steps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        ButterKnife.bind(this);

        steps = new Step();
        steps = getIntent().getExtras().getParcelable(STEPS);

        criarFragments();

    }

    private void criarFragments() {
        VideoFragment videoFragment = new VideoFragment();
        videoFragment.setSteps(steps);

        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .add(R.id.video_container, videoFragment)
                .commit();

    }


}
