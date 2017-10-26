package com.bwie.goodscar.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.goodscar.R;
import com.bwie.goodscar.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 张乔君 on 2017/10/24.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private ArrayList<Bean.DataBean> list;
    private Context context;
    private ChildAdapter ada;

    public MyAdapter(ArrayList<Bean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.item,null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {

        holder.title.setText(list.get(position).getSellerName());
        final List<Bean.DataBean.ListBean> l = this.list.get(position).getList();
        ada = new ChildAdapter((ArrayList<Bean.DataBean.ListBean>) l,context);
        holder.lv.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.lv.setAdapter(ada);

        //根据数据，状态改变
        holder.cb.setChecked(list.get(position).isFlag());

        //商家改变子商品
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.get(position).setFlag(!list.get(position).isFlag());
                for (int i = 0; i < l.size(); i++) {
                    if (list.get(position).isFlag()){
                        l.get(i).setSelected(1);
                    }else {
                        l.get(i).setSelected(0);
                    }
                }
                holder.lv.setAdapter(ada);
                notifyDataSetChanged();
              moneyResult.money();
            }
        });

        //计算钱
        ada.setCallMoney(new ChildAdapter.CallMoney() {
            @Override
            public void changeMoney() {
                moneyResult.money();
            }
        });

        //子改变商家cb
        ada.setOnItemAllClick(new ChildAdapter.OnItemAllClick() {
            @Override
            public void all() {
                list.get(position).setFlag(true);
                holder.cb.setChecked(true);
                notifyDataSetChanged();
            }
            @Override
            public void noall() {
                list.get(position).setFlag(false);
                holder.cb.setChecked(false);
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyHolder extends RecyclerView.ViewHolder{
        private RecyclerView lv;
        private TextView title;
        private CheckBox cb;
        public MyHolder(View itemView) {
            super(itemView);
            lv=itemView.findViewById(R.id.re2);
            title=itemView.findViewById(R.id.boss);
            cb=itemView.findViewById(R.id.boss_all);
        }
    }
    private MoneyResult moneyResult;

    public void setMoneyResult(MoneyResult moneyResult) {
        this.moneyResult = moneyResult;
    }

    public interface MoneyResult{
        void money();
    }
}
