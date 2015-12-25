package com.pioneer.aaron.cityfinder.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import com.pioneer.aaron.cityfinder.finder.DBManager;
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
    private List<String> temp;
    private AsyncTask asyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyinsearch);
        setData();
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                try {
                    asyncTask = new AsyncTask() {
                        @Override
                        protected Object doInBackground(Object[] params) {
                            mlist = PinyinUtil.search(str);
                            /*List<String> temp = PinyinUtil.search(str);
                            mlist.clear();
                            mlist = new ArrayList<String>();
                            for (int i = 0; i < temp.size(); ++i) {
                                mlist.add(temp.get(i));
                            }*/
                            /*mlist.clear();
                            mlist.addAll(PinyinUtil.search(str));*/
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Object o) {
                            adapter = new ArrayAdapter<String>(PinyinSearch.this, android.R.layout.simple_list_item_1, mlist);
                            ((ListView) findViewById(R.id.listView)).setAdapter(adapter);
                            for (int i = 0; i < mlist.size(); ++i) {
                                System.out.println("----" + mlist.get(i));
                            }
//                            adapter.notifyDataSetChanged();
                            super.onPostExecute(o);
                        }
                    }.execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    private void setData() {
        mlist = new ArrayList<>();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);

        Cursor cursor = database.rawQuery("select * from T_City order by NameSort", null);
        for (int i = 0; i < cursor.getCount(); ++i) {
            cursor.moveToPosition(i);
            mlist.add(cursor.getString(cursor.getColumnIndex("CityName")));
        }
        cursor.close();
        database.close();
    }
}
