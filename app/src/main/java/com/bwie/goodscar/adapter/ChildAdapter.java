package com.bwie.goodscar.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwie.goodscar.R;
import com.bwie.goodscar.bean.Bean;

import java.util.ArrayList;

/**
 * Created by 张乔君 on 2017/10/24.
 */

public class ChildAdapter extends RecyclerView.Adapter<ChildAdapter.MHolder> {
    private ArrayList<Bean.DataBean.ListBean> list;
    private Context context;

    public ChildAdapter(ArrayList<Bean.DataBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.childitem,null);
        return new MHolder(view);
    }

    @Override
    public void onBindViewHolder(final MHolder holder, final int position) {
        holder.title.setText(list.get(position).getSubhead());
        holder.title.setMaxLines(1);//限制行
        holder.money.setText(list.get(position).getBargainPrice()+"");
        holder.c.setText(list.get(position).getTitle());
        holder.c.setMaxLines(1);
        holder.num.setText(list.get(position).getNum()+"");
        String[] split = list.get(position).getImages().split("\\|");
        Glide.with(context).load(split[0]).into(holder.iv);

        //根据数据,改变状态
        if(list.get(position).getSelected()==1){
            holder.cb.setChecked(true);
        }else{
            holder.cb.setChecked(false);
        }

        //子商品的点击事件
        holder.cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cb.isChecked()){
                    list.get(position).setSelected(1);
                }else {
                    list.get(position).setSelected(0);
                }
                all();
                notifyDataSetChanged();
                callMoney.changeMoney();
            }
        });

    }

    //判断是否是全选的回调
    private int sum=0;
    private void all() {
        for (int i = 0; i <list.size() ; i++) {
            if(list.get(i).getSelected()==1){
                sum++;
            }else{
                sum--;
            }
        }
        if(sum==list.size()){
            onItemAllClick.all();
        }else{
            onItemAllClick.noall();
        }
        sum=0;
    }
    private OnItemAllClick onItemAllClick;

    public void setOnItemAllClick(OnItemAllClick onItemAllClick) {
        this.onItemAllClick = onItemAllClick;
    }

    public interface OnItemAllClick{
        void all();
        void noall();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        TextView money;
        TextView title;
        TextView c;
        CheckBox cb;
        TextView num;
        public MHolder(View itemView) {
            super(itemView);
            iv=itemView.findViewById(R.id.good_iv);
            money=itemView.findViewById(R.id.good_money);
            title=itemView.findViewById(R.id.good_shop);
            c=itemView.findViewById(R.id.buy_content);
            cb=itemView.findViewById(R.id.good_cb);
            num=itemView.findViewById(R.id.good_num);
        }
    }

    private CallMoney callMoney;

    public void setCallMoney(CallMoney callMoney) {
        this.callMoney = callMoney;
    }

    public interface CallMoney{
        void changeMoney();
    }
}
