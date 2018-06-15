package com.example.demogson;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class PersonalDataActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_data);
        String jsonStr = getIntent().getStringExtra("jsonStr");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(PersonalDataActivity.this,
            android.R.layout.simple_list_item_1, parseJSON(jsonStr));
        setListAdapter(adapter);
    }

    List<String> parseJSON(String jsonStr) {
        Gson gson = new Gson();
        System.out.println("jsonStr = " + jsonStr);
        Person p = gson.fromJson(jsonStr, Person.class);
        ArrayList<String> list = new ArrayList<>();
        list.add("姓名：" + p.getName());
        list.add("地址：" + p.getData().getAddress());
        list.add("電話：" + p.getData().getPhoneNumber());
        list.add("性別：" + (p.isMale() ? "男" : "女"));
        list.add("年齡：" + p.getAge());
        list.add("興趣：" + p.getFavorites());
        return list;
    }
}
