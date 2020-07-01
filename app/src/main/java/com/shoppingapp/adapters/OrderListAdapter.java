package com.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.shoppingapp.R;
import com.shoppingapp.beans.OrderBean;
import com.shoppingapp.beans.OrderDetailBean;
import com.shoppingapp.db.DbHelper;

import java.util.List;

public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderBean> orderBeanList;
    private List<OrderDetailBean> orderDetailBeanList;
    private DbHelper dbHelper;

    public OrderListAdapter(Context context,List<OrderBean> orderBeanList){
        this.orderBeanList = orderBeanList;
        this.context = context;
        dbHelper = new DbHelper(context, null, null, 1);
    }

    @Override
    public int getCount() {
        return orderBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        View view = null;

        if (convertView == null) {
            holder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_order, null);
            holder.tvDate = view.findViewById(R.id.tv_orderdate);
            holder.tvOrderNum = view.findViewById(R.id.tv_ordernum);
            holder.tvPrice = view.findViewById(R.id.tv_orderprice);
            holder.tvDetail = view.findViewById(R.id.tv_orderdetail);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (Holder) convertView.getTag();
        }

        orderDetailBeanList = dbHelper.getOrderDetail(orderBeanList.get(position).getOrderId());

        String detail ="";
        for (int i = 0; i < orderDetailBeanList.size(); i++) {
             detail += "商品名："+orderDetailBeanList.get(i).getGoodsName()+"  价格："+orderDetailBeanList.get(i).getGoodsPrice()+"\n";
        }


        holder.tvOrderNum.setText("订单"+(position+1));
        holder.tvDate.setText(orderBeanList.get(position).getOrderDate());
        holder.tvPrice.setText("￥"+orderBeanList.get(position).getTotalPrice());
        holder.tvDetail.setText(detail);
        return view;
    }

    class Holder {
        TextView tvOrderNum;
        TextView tvPrice;
        TextView tvDate;
        TextView tvDetail;
    }
}
