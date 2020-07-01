package com.shoppingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.shoppingapp.R;
import com.shoppingapp.activities.GoodsDetileActivity;
import com.shoppingapp.beans.GoodsBean;

import java.util.List;

public class GoodsListAdapter extends BaseAdapter {
    private Context context;
    private List<GoodsBean> goodsBeanList;
    private int i = 0;

    public GoodsListAdapter(Context context, List<GoodsBean> goodsBeanList,int i) {
        this.context = context;
        this.goodsBeanList = goodsBeanList;
        this.i = i;
    }

    @Override
    public int getCount() {
        return goodsBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        View view = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_goods,null);
            viewHolder.goodsPic = view.findViewById(R.id.iv_item_goodspic);
            viewHolder.goodsTitle = view.findViewById(R.id.tv_item_goods_title);
            viewHolder.goodsPrice = view.findViewById(R.id.tv_item_goods_price);
            viewHolder.ivDelete = view.findViewById(R.id.iv_delete);



            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        Glide.with(context).load(goodsBeanList.get(position).getPhoto())
                .error(R.drawable.goodsdef)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.goodsPic);

        Glide.with(context).load(goodsBeanList.get(position).getPhoto()).into(viewHolder.goodsPic);
        viewHolder.goodsPrice.setText("￥"+goodsBeanList.get(position).getPrice());
        viewHolder.goodsTitle.setText(goodsBeanList.get(position).getTitle());

        if(i == 1){
            viewHolder.ivDelete.setVisibility(View.VISIBLE);
        }
        return view;
    }

    class ViewHolder{
        ImageView goodsPic;
        TextView goodsTitle;
        TextView goodsPrice;
        ImageView ivDelete;
    }
}
