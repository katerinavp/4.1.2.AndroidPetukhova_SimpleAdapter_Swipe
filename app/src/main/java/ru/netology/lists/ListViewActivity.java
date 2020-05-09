package ru.netology.lists;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    private static final String KEY_TITLE = "key_title";
    private static final String KEY_COUNT = "key_count";
    private static ListView list;
    private static List<Map<String, String>> result = new ArrayList<>();
    private static SharedPreferences myListSharedPref;
    private static String LIST_TEXT = "list_text";
    private static BaseAdapter listContentAdapter;
    private static String listTxt;
    private static String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        list = findViewById(R.id.list);
        List<Map<String, String>> values = prepareContent();
        listContentAdapter = createAdapter(values);
        list.setAdapter(listContentAdapter);

        myListSharedPref = getSharedPreferences("MyList", MODE_PRIVATE);

       //getDateFromSharedPref();
        onClick();
    }




    @NonNull
    private BaseAdapter createAdapter(List<Map<String, String>> values) {
        return new SimpleAdapter(this, values, R.layout.item, new String[]{KEY_TITLE, KEY_COUNT},
                new int[]{R.id.txtTv, R.id.txtSymbol});
        //return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);

    }


    @NonNull
    private List<Map<String, String>> prepareContent() {
        SharedPreferences myListSharedPref = getSharedPreferences("MyNote", MODE_PRIVATE);

        titles = getString(R.string.large_text).split("\n\n");
        for (String title : titles) {
            Map<String, String> map = new HashMap<>();
            map.put(KEY_TITLE, title);
            map.put(KEY_COUNT, title.length() + "");
            result.add(map);
            SharedPreferences.Editor myEditor = myListSharedPref.edit();
           listTxt = title;

            myEditor.putString(LIST_TEXT, listTxt);
            myEditor.apply();
            //getDateFromSharedPref(title);
        }
        return result;
        //return getString(R.string.large_text).split("\n\n");
    }

    protected void onClick() {
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { // метод для реализации нажатия на список
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // View view - получаем вью на которую нажал пользователь, int position- позицию на которую была нажата 9 как правило ее используем из всех ),
                // получаем id - который не используется 90% случаев, иполучаем AdapterView<?> parent - который будет у нас ListView

                result.remove(position);
                listContentAdapter.notifyDataSetChanged();// обязательно вызывать когда делаем какие то изменения в списке
            }
        });
    }

//    private void getDateFromSharedPref(String title) {
//       listTxt = myListSharedPref.getString(LIST_TEXT, "");
//       // title.setText(listTxt);
//
//    }
}

