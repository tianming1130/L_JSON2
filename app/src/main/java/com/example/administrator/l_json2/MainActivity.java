package com.example.administrator.l_json2;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.l_json2.adapter.MenuAdapter;
import com.example.administrator.l_json2.bean.HttpResult;
import com.example.administrator.l_json2.bean.HttpResult1;
import com.example.administrator.l_json2.bean.Menu;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnGetData;
    private TextView tvShowMsg;
    private RecyclerView recyclerView;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGetData=(Button)findViewById(R.id.btn_get_data);
//        tvShowMsg=(TextView)findViewById(R.id.tv_show_msg);
        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);

        btnGetData.setOnClickListener(this);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String strData=msg.getData().getString("data");
//                tvShowMsg.setText(strData);
//                try {
//                    JSONObject object=new JSONObject(strData);
//                    int code=object.getInt("code");
//                    String message=object.getString("message");
//                    String data=object.getString("data");
////                    tvShowMsg.setText("code:"+code+" messge:"+message+" data:"+data);
//                    HttpResult httpResult=new HttpResult(code,message,data);
//                    tvShowMsg.setText(httpResult.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }

                Gson gson=new Gson();
                HttpResult1 httpResult1=gson.fromJson(strData,HttpResult1.class);
//                tvShowMsg.setText(httpResult.toString());
                List<Menu> data=httpResult1.getData();

                MenuAdapter menuAdapter=new MenuAdapter(R.layout.item_view,data);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(menuAdapter);
            }
        };
    }

    @Override
    public void onClick(View v) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    URL url=new URL("http://10.0.2.2/getdata.php");
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    if(conn.getResponseCode()==200){
                        InputStream is=conn.getInputStream();
                        StringBuffer sBuffer=new StringBuffer();
                        int hasRead=-1;
                        byte[] buffer=new byte[512];
                        while ((hasRead=is.read(buffer))!=-1){
                            sBuffer.append(new String(buffer,0,hasRead));
                        }
                        Message msg=handler.obtainMessage();
                        Bundle bundle=new Bundle();
                        bundle.putString("data",sBuffer.toString());
                        msg.setData(bundle);
                        handler.sendMessage(msg);

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
