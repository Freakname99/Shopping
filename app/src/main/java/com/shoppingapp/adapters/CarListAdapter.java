package com.shoppingapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shoppingapp.R;
import com.shoppingapp.beans.CarBean;
import com.shoppingapp.beans.GoodsBean;
import com.shoppingapp.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapter extends BaseAdapter {
    private Context context;
    private List<CarBean> carBeanList;
    private boolean isEdite = false;

    private DbHelper dbHelper;

    private int saveposition;
    private DeleteCallback deleteCallback;
    private CheckCallBack checkCallBack;
    private UnCheckCallBack unCheckCallBack;


    private int num = 0;

    public CarListAdapter(Context context, List<CarBean> carBeanList, Boolean isEdite) {
        this.context = context;
        this.carBeanList = carBeanList;
        this.isEdite = isEdite;

        dbHelper = new DbHelper(context, null, null, 1);
    }

    public int getPrice() {
         int price = 0;
        for (int i = 0; i < carBeanList.size(); i++) {
            if(carBeanList.get(i).getChecked().equals("true")){
                price += carBeanList.get(i).getPrice() * carBeanList.get(i).getQuantity();
            }
        }
        return price;
    }

    public void editeable(boolean editable) {
        isEdite = editable;
    }


    public boolean isEdite() {
        return isEdite;
    }



    public int getNum(int position){
        return num;
    }

    public void setData(List<CarBean> carBeanList) {
        this.carBeanList = carBeanList;
    }

    @Override
    public int getCount() {
        return carBeanList.size();
    }

    @Override
    public Object getItem(int position) {
        return carBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        Holder viewHolder = null;
        View view = null;
        if (convertView == null) {
            viewHolder = new Holder();
            view = LayoutInflater.from(context).inflate(R.layout.item_car, null);
            viewHolder.cbItem = view.findViewById(R.id.cb_item);
            viewHolder.ivGoods = view.findViewById(R.id.iv_goods);
            viewHolder.tvTitle = view.findViewById(R.id.tv_title);
            viewHolder.btnSub = view.findViewById(R.id.btn_sub);
            viewHolder.btnAdd = view.findViewById(R.id.btn_add);
            viewHolder.tvGoodsNum = view.findViewById(R.id.tv_goods_num);
            viewHolder.tvGoodsPrice = view.findViewById(R.id.tv_goods_price);
            viewHolder.tvGoodsNumRed = view.findViewById(R.id.tv_goods_num_red);
            viewHolder.btnDelete = view.findViewById(R.id.btn_delete);

            if (carBeanList.get(position).getChecked().equals("true")) {
                viewHolder.cbItem.setChecked(true);
            }

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (Holder) convertView.getTag();
        }


        Glide.with(context).load(carBeanList.get(position).getPhoto()).into(viewHolder.ivGoods);

        viewHolder.tvTitle.setText(carBeanList.get(position).getGoodsname());
        Log.e("viewHolder  ==", "position ==: " + position + "  carbean" + carBeanList.get(position).toString());
        viewHolder.tvGoodsNum.setText(carBeanList.get(position).getQuantity() + "");
        viewHolder.tvGoodsNumRed.setText("×" + carBeanList.get(position).getQuantity());
        viewHolder.tvGoodsPrice.setText("￥" + carBeanList.get(position).getPrice());


        if (!isEdite) {
            viewHolder.cbItem.setVisibility(View.VISIBLE);
            viewHolder.btnAdd.setVisibility(View.GONE);
            viewHolder.btnDelete.setVisibility(View.GONE);
            viewHolder.btnSub.setVisibility(View.GONE);
            viewHolder.tvGoodsNum.setVisibility(View.GONE);
        }


        final Holder finalViewHolder = viewHolder;
        viewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(finalViewHolder.tvGoodsNum.getText().toString()) + 1;
                finalViewHolder.tvGoodsNum.setText(num + "");
                finalViewHolder.tvGoodsNumRed.setText("×" + num);
                carBeanList.get(position).setQuantity(num);
            }
        });
        final int num = Integer.parseInt(finalViewHolder.tvGoodsNum.getText().toString()) - 1;
        viewHolder.btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (num >= 1) {
                    finalViewHolder.tvGoodsNum.setText(num + "");
                    finalViewHolder.tvGoodsNumRed.setText("×" + num);
                    carBeanList.get(position).setQuantity(num);
                } else {
                    Toast.makeText(context, "请删除商品", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.btnDelete.setTag(position);
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveposition = Integer.parseInt(v.getTag().toString());// TODO
                dbHelper.deleteCar(carBeanList.get(position).getCarid());
                deleteCallback.deletePosition(saveposition);
            }
        });


        viewHolder.cbItem.setTag(position);

        viewHolder.cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    carBeanList.get(position).setChecked("true");
                    dbHelper.updateCar(carBeanList.get(position));
                    checkCallBack.checkPosition(Integer.parseInt(buttonView.getTag().toString()));
                }else {
                    carBeanList.get(position).setChecked("false");
                    dbHelper.updateCar(carBeanList.get(position));
                    checkCallBack.checkPosition(Integer.parseInt(buttonView.getTag().toString()));
                }
            }
        });

        return view;
    }


    public interface DeleteCallback {
        void deletePosition(int saveposition);
    }

    public void setDeleteCallback(DeleteCallback Callback) {
        this.deleteCallback = Callback;
    }

    public interface CheckCallBack{
        void checkPosition(int saveposition);
    }

    public void setCheckCallBack(CheckCallBack checkCallBack){
        this.checkCallBack = checkCallBack;
    }

    public interface UnCheckCallBack{
        void unCheckPosition(int saveposition);
    }

    public void setUnCheckCallBack(UnCheckCallBack uncheckCallBack){
        this.unCheckCallBack = uncheckCallBack;
    }



    class Holder {
        CheckBox cbItem;
        ImageView ivGoods;
        TextView tvTitle;
        Button btnSub;
        Button btnAdd;
        TextView tvGoodsNum;
        TextView tvGoodsPrice;
        TextView tvGoodsNumRed;
        Button btnDelete;
    }
}
