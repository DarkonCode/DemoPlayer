package com.demo.demoplayer.functionOne;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.demoplayer.R;
import com.demo.demoplayer.adapters.FunctionOneTrackListAdapter;
import com.demo.demoplayer.interfaces.OnImageClickListener;

/**
 * Created by Brandon Lee Roets  on 2016/06/21.
 */
public class FunctionOneMainActivity extends AppCompatActivity implements OnImageClickListener {

    private RecyclerView trackList;
    private FunctionOneTrackListAdapter trackAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.function_one);
        setContentView(R.layout.activity_function_one_main_activity);
        trackList = (RecyclerView) findViewById(R.id.activity_functionOne_mainActivity_trackList);
    }

    public void refresh() {

        trackAdapter = new FunctionOneTrackListAdapter(this, this);
        trackList.setAdapter(trackAdapter);
        trackList.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();

        refresh();
    }

    @Override
    public void imageClick(int position) {

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment previousFrag = getFragmentManager().findFragmentByTag("dialog");
        if (previousFrag != null) {
            fragmentTransaction.remove(previousFrag);
        }
        fragmentTransaction.addToBackStack(null);

        DialogFragment frag = FunctionOneTrackFragment.newInstance(trackAdapter.getItemAtPosition(position));
        frag.show(fragmentTransaction, "dialog");
    }
}
