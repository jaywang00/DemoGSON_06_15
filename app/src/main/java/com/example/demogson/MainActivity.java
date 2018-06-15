package com.example.demogson;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DemoGSON";
//    private static final String HTTP_URL = "http://asphoster.FreeASPHost.net/";
    private static final String HTTP_URL = "http://192.168.2.78/JSON/Default.aspx";
    EditText et_name, et_age, et_address, et_phone, et_favor1, et_favor2, et_favor3, et_favor4;
    RadioButton radio_male;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
    }

    void findViews() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_age = (EditText) findViewById(R.id.et_age);
        et_address = (EditText) findViewById(R.id.et_address);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_favor1 = (EditText) findViewById(R.id.et_favor1);
        et_favor2 = (EditText) findViewById(R.id.et_favor2);
        et_favor3 = (EditText) findViewById(R.id.et_favor3);
        et_favor4 = (EditText) findViewById(R.id.et_favor4);
        radio_male = (RadioButton) findViewById(R.id.radio_male);
    }

    public void submit(View v) {
        MyAsncTask task = new MyAsncTask();
        task.execute(createJsonString());
    }

    private String createJsonString() {
        String name = et_name.getText().toString();
        Person p;
        Data data;
        if (!name.equals("")) {
            String address = et_address.getText().toString();
            String phoneNumber = et_phone.getText().toString();
            data = new Data(address,phoneNumber);
            int age = 0;
            boolean male = false;
            try {
                age = Integer.parseInt(et_age.getText().toString());
                male = radio_male.isChecked();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            ArrayList<String> list = new ArrayList<>();
            list.add(et_favor1.getText().toString());
            list.add(et_favor2.getText().toString());
            list.add(et_favor3.getText().toString());
            list.add(et_favor4.getText().toString());
            p = new Person(name, age, male, data, list);
        } else
            p = null;

        return new Gson().toJson(p);
    }

    class MyAsncTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            System.out.println("JSON = " + params[0]);
            return uploadData(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            showPersonalData(result);
        }
    }

    String uploadData(String jsonString) {
        HttpURLConnection conn = null;
        OutputStream out = null;
        InputStream in = null;
        try {
            URL url = new URL(HTTP_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("Submit", "Submit")
                    .appendQueryParameter("JSON", jsonString);
            String query = builder.build().getEncodedQuery();
            out = new BufferedOutputStream(conn.getOutputStream());
            out.write(query.getBytes());
            out.flush();

            in = new BufferedInputStream(conn.getInputStream());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            while (true) {
                int n = in.read(buf);
                if (n < 0) break;
                baos.write(buf, 0, n);
            }
            byte[] data = baos.toByteArray();

            String result = new String(data);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return "Send Failed";
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (conn != null)
                conn.disconnect();
        }
    }

    void showPersonalData(String result) {
        if (!result.equals("Send Failed")) {
            Intent intent = new Intent(MainActivity.this, PersonalDataActivity.class);
            intent.putExtra("jsonStr", result);
            startActivity(intent);
        } else
            Toast.makeText(this, "傳送失敗", Toast.LENGTH_SHORT).show();
    }
}
