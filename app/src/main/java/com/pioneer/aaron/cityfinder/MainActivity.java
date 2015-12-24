package com.pioneer.aaron.cityfinder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pioneer.aaron.cityfinder.activities.PinyinSearch;
import com.pioneer.aaron.cityfinder.finder.Finder;

public class MainActivity extends AppCompatActivity {
    TextView location_text, pinyinSearch;
    ImageButton locationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        location_text = (TextView) findViewById(R.id.home_location_text);
        locationButton = (ImageButton) findViewById(R.id.btn_locate);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Finder.class);
                startActivityForResult(intent, 1);
            }
        });

        pinyinSearch = (TextView) findViewById(R.id.pinyinsearch);
        pinyinSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PinyinSearch.class);
                startActivityForResult(intent, 1);

            }
        });
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
