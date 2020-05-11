//package ru.netology.lists;
//
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.BaseAdapter;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//
//public class ListViewActivity extends AppCompatActivity {
//
//    private static final String KEY_TITLE = "key_title";
//    private static final String KEY_COUNT = "key_count";
//    private static ListView list;
//    private static List<Map<String, String>> result = new ArrayList<>();
//    private static String LIST_TEXT = "list_text";
//    private static BaseAdapter listContentAdapter;
//    private static String listTxt;
//    private static ArrayList<SharedPreferences.Editor> resultSharedPrefs = new ArrayList<SharedPreferences.Editor>();
//    private static SwipeRefreshLayout swipeLayout;
////    private static String rawContent;
//    private static SharedPreferences myListSharedPref;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_view);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        list = findViewById(R.id.list);
//
//        List<Map<String, String>> values = prepareContent();
//
//        listContentAdapter = createAdapter(values);
//        list.setAdapter(listContentAdapter);
////        myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);
//        onClick();
//        //swipe();
//        //swipe(rawContent);
//    }
//
//
//    @NonNull
//    private BaseAdapter createAdapter(List<Map<String, String>> values) {
//        return new SimpleAdapter(this, values, R.layout.item, new String[]{KEY_TITLE, KEY_COUNT},
//                new int[]{R.id.txtTv, R.id.txtSymbol});
//    }
//
//
//    @NonNull
//    private List<Map<String, String>> prepareContent() {
//
//        myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);
////        SharedPreferences myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);
//        if (!myListSharedPref.contains(LIST_TEXT)) {
//            myListSharedPref.edit()
//                    .putString(LIST_TEXT, getString(R.string.large_text))
//                    .apply();
//        }
//        String rawContent = Objects.requireNonNull(myListSharedPref.getString(LIST_TEXT, null));
////        swipe(rawContent);
//
//        String[] titles = rawContent.split("\n\n");
//        for (String title : titles) {
//            Map<String, String> map = new HashMap<>();
//            map.put(KEY_TITLE, title);
//            map.put(KEY_COUNT, title.length() + "");
//            result.add(map);
//        }
//        return result;
//        //swipe(rawContent);
//    }
//
////        SharedPreferences myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);
////
////        String[] titles = getString(R.string.large_text).split("\n\n");
////        for (String title : titles) {
////            Map<String, String> map = new HashMap<>();
////            map.put(KEY_TITLE, title);
////            map.put(KEY_COUNT, title.length() + "");
////            result.add(map);
////
////            SharedPreferences.Editor myEditor = myListSharedPref.edit();
////            listTxt = title;
////
////            myEditor.putString(LIST_TEXT, listTxt);
////            //myEditor.putString(LIST_COUNT, listTxt.length() + "");
////            myEditor.commit();
////            listTxt = myListSharedPref.getString(LIST_TEXT, "");//Чтение сохраненных данных разных типов из SharedPreferences
////            resultSharedPrefs.add(myEditor);
////            if (myEditor.commit()) {
////                Toast.makeText(ListViewActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
////            } else {
////                Toast.makeText(ListViewActivity.this, "Данные НЕ сохранены", Toast.LENGTH_SHORT).show();
////            }
////
////        }
////        return result;
////
////    }
//
//    protected void onClick() {
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                result.remove(position);
//                listContentAdapter.notifyDataSetChanged();// обязательно вызывать когда делаем какие то изменения в списке
//            }
//        });
//    }
//
////    private void swipe() {
////        swipeLayout = findViewById(R.id.swiperefresh);
////        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
////            @Override
////            public void onRefresh() {
////                result.clear();
//////                for (SharedPreferences.Editor resultSharedPref : rawContent) {
////                    Map<String, String> mapSharedPref = new HashMap<>();
////                    mapSharedPref.put(KEY_TITLE, String.valueOf(myListSharedPref));;
////                    mapSharedPref.put(KEY_COUNT, String.valueOf(myListSharedPref).length() + "");
////                    result.add(mapSharedPref);
////                    listContentAdapter.notifyDataSetChanged();
////                    swipeLayout.setRefreshing(false);
////
////            }
////        });
////    }
//}


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

//    private void swipe() {
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                result.clear();
//                prepareContent();
//                listContentAdapter.notifyDataSetChanged();
//            }
//        });
//    }

}