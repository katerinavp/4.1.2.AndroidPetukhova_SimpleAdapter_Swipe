package ru.netology.lists;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ListViewActivity extends AppCompatActivity {
    private ListView list;
    private List<Map<String, String>> result = new ArrayList<>();
    private static final String LIST_TEXT = "list_text";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_COUNT = "key_count";
    private BaseAdapter listContentAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                result.clear();
                prepareContent();
                listContentAdapter.notifyDataSetChanged();
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list = findViewById(R.id.list);
        prepareContent();
        listContentAdapter = createAdapter(result);
        list.setAdapter(listContentAdapter);
        onClick();

    }

    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        return new SimpleAdapter(this, values, R.layout.item, new String[]{KEY_TITLE, KEY_COUNT},
                new int[]{R.id.txtTv, R.id.txtSymbol});
    }

    private List<Map<String, String>> prepareContent() {
        SharedPreferences myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);
        if (!myListSharedPref.contains(LIST_TEXT)) {
            myListSharedPref.edit()
                    .putString(LIST_TEXT, getString(R.string.large_text))
                    .apply();
        }
        String rawContent = Objects.requireNonNull(myListSharedPref.getString(LIST_TEXT, null));
        String[] titles = rawContent.split("\n\n");
        for (String title : titles) {
            Map<String, String> map = new HashMap<>();
            map.put(KEY_TITLE, title);
            map.put(KEY_COUNT, title.length() + "");
            result.add(map);
        }
        return result;

    }

    protected void onClick() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                result.remove(position);
                listContentAdapter.notifyDataSetChanged();

            }
        });

    }

}