package com.pioneer.aaron.cityfinder;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pioneer.aaron.cityfinder.activities.PinyinSearch;
import com.pioneer.aaron.cityfinder.finder.Finder;
import com.pioneer.aaron.cityfinder.finder.Setting;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.home_location_text)
    TextView location_text;

    @OnClick({R.id.btn_locate, R.id.home_location_text})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.btn_locate:
                Intent intent = new Intent(this, Finder.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.home_location_text:
                Snackbar.make(location_text, "You're at " + location_text.getText(), Snackbar.LENGTH_SHORT)
                        .setAction("OK", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .show();

                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        ButterKnife.bind(this);
        location_text.setText(Setting.LoadFromSharedPreferences(this, "city"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
            switch (resultCode) {
                case 2:
                    location_text.setText(data.getStringExtra("city"));
                    break;
                default:
                    break;
            }
        }
    }
}
