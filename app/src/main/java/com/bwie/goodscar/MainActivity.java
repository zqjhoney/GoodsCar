package com.bwie.goodscar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwie.goodscar.adapter.MyAdapter;
import com.bwie.goodscar.bean.Bean;
import com.bwie.goodscar.utils.NetUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycle;
    private CheckBox cball;
    private TextView money;
    private List<Bean.DataBean> data;
    private MyAdapter ada;
    private int kb;
    private double money1;
    private DecimalFormat df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        df = new DecimalFormat("######0.00");
        initview();
        initdata();
        setlistener();
    }

    private void setlistener() {
        cball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cball.isChecked()){
                    for (Bean.DataBean bean : data) {
                        bean.setFlag(true);
                        List<Bean.DataBean.ListBean> list = bean.getList();
                        for (Bean.DataBean.ListBean listBean : list) {
                            listBean.setSelected(1);
                        }
                    }
                }else{
                    for (Bean.DataBean bean : data) {
                        bean.setFlag(false);
                        List<Bean.DataBean.ListBean> list = bean.getList();
                        for (Bean.DataBean.ListBean listBean : list) {
                            listBean.setSelected(0);
                        }
                    }
                }
                ada.notifyDataSetChanged();

            }
        });
    }

    private void initdata() {
        NetUtils.getResult(Api.LOOK_URL, new NetUtils.CallResult() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) {

                try {
                    String str=response.body().string();
                    Gson gson=new Gson();
                    Bean bean = gson.fromJson(str, Bean.class);

                    data = bean.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ada= new MyAdapter((ArrayList<Bean.DataBean>) data,MainActivity.this);
                            recycle.setLayoutManager(new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false));
                            recycle.setAdapter(ada);
                            //回调的钱的接口
                            ada.setMoneyResult(new MyAdapter.MoneyResult() {
                                @Override
                                public void money() {
                                     kb= 0;
                                     money1= 0;
                                    for (Bean.DataBean dataBean : data) {
                                        List<Bean.DataBean.ListBean> been = dataBean.getList();
                                        for (Bean.DataBean.ListBean listBean : been) {

                                            if(listBean.getSelected()==1){
                                                kb++;
                                                money1+=listBean.getBargainPrice()*listBean.getNum();
                                            }
                                        }
                                    }
                                    //设置总价
                                    money.setText(df.format(money1)+"");
                                }
                            });

                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void initview() {
        recycle = (RecyclerView) findViewById(R.id.r1_review);
        cball = (CheckBox) findViewById(R.id.selectall);
        money = (TextView) findViewById(R.id.sumnoney);

    }
}
