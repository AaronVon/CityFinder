package com.pioneer.aaron.cityfinder.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.pioneer.aaron.cityfinder.R;
import com.pioneer.aaron.cityfinder.utils.PinyinUtil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aaron on 12/24/15.
 */
public class PinyinSearch extends AppCompatActivity {

    private EditText mEditText;
    private ListView mListView;
    private ArrayAdapter<String> adapter;
    private List<String> mlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyinsearch);
        setData();
        initView();
    }

    private void initView() {
        mEditText = (EditText) findViewById(R.id.search_field);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                final String str = s.toString();
                new Thread() {
                    @Override
                    public void run() {
                        mlist = PinyinUtil.search(str);
                        Message message = new Message();
                        message.what = 0;
                        mHandler.sendMessage(message);
                    }
                }.start();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mlist);
        mListView = (ListView) findViewById(R.id.listView);
        mListView.setAdapter(adapter);

        try {
            PinyinUtil.setData(mlist);
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
//                Log.d("handleMessage", "notifyDataSetChanged");
                for (int i = 0; i < mlist.size(); ++i) {
                    Log.d("----------", mlist.get(i));
                }
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void setData() {
        mlist = new ArrayList<>();
        mlist.add("安徽");
        mlist.add("王小二");
        mlist.add("Android将军");
        mlist.add("天津大麻花");
        mlist.add("马场道");
        mlist.add("海河湾");
        mlist.add("五大道");
        mlist.add("苏州道");
        mlist.add("爱国道");
        mlist.add("天津图书大厦");
    }
}
