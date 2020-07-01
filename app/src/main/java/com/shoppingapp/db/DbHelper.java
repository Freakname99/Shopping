package com.shoppingapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.util.Log;

import com.shoppingapp.R;
import com.shoppingapp.activities.MainActivity;
import com.shoppingapp.beans.CarBean;
import com.shoppingapp.beans.GoodsBean;
import com.shoppingapp.beans.OrderBean;
import com.shoppingapp.beans.OrderDetailBean;
import com.shoppingapp.beans.UserInfoBean;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = DbHelper.class.getSimpleName();
    private DbManager dbManager;
    private Context mContext;

    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dbManager = new DbManager(mContext);
//        dbManager.openDataBase("goods", R.raw.goods);
//        dbManager.openDataBase("goodsrecently", R.raw.goodsrecently);
//        dbManager.openDataBase("order", R.raw.order);
//        dbManager.openDataBase("shoppingcar", R.raw.shoppingcar);
//        dbManager.openDataBase("userinfo", R.raw.userinfo);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * 获取所有商品
     */
    public List<GoodsBean> getAllGoods() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("goods", R.raw.goods);
        SQLiteDatabase db = dbManager.getDb();

        Cursor cursor = db.query("goodstb", null, null, null, null, null, null);
        if (cursor.moveToNext()) {

            do {
                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                goodsBean.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsid")));
                goodsBean.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
                goodsBean.setIntroduce(cursor.getString(cursor.getColumnIndex("introduce")));
                goodsBean.setType(cursor.getString(cursor.getColumnIndex("type")));
                goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
                goodsBeanList.add(goodsBean);
//                Log.d("DbTest", "title:" + title);
            } while (cursor.moveToNext());
        }
        dbManager.closeDataBase();
        return goodsBeanList;
    }

    /**
     * 获取分类商品
     */
    public List<GoodsBean> getCategoryGoods(String category) {
        List<GoodsBean> goodsBeanList = new ArrayList<>();

        dbManager = new DbManager(mContext);
        dbManager.openDataBase("goods", R.raw.goods);
        SQLiteDatabase db = dbManager.getDb();

        String sql = "select * from " + "goodstb" + " where " + "type" + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{category});

        while (cursor.moveToNext()) {
            GoodsBean goodsBean = new GoodsBean();
            goodsBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            goodsBean.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsid")));
            goodsBean.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
            goodsBean.setIntroduce(cursor.getString(cursor.getColumnIndex("introduce")));
            goodsBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            goodsBeanList.add(goodsBean);
        }
        dbManager.closeDataBase();
        return goodsBeanList;
    }


    /**
     * 搜索商品
     */
    public List<GoodsBean> searchGoods(String string) {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        String str = string;
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("goods", R.raw.goods);
        SQLiteDatabase db = dbManager.getDb();
        String sql = "select * from " + "goodstb" + " where " + "title" + " like ?";
        Cursor cursor = db.rawQuery(sql, new String[]{"%"+str+"%"});


        Log.e(TAG, "searchGoods: sql ==="+sql  +"str =="+str );

        while (cursor.moveToNext()) {
            GoodsBean goodsBean = new GoodsBean();
            goodsBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            goodsBean.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsid")));
            goodsBean.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
            goodsBean.setIntroduce(cursor.getString(cursor.getColumnIndex("introduce")));
            goodsBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            goodsBeanList.add(goodsBean);
            Log.e(TAG, "searchGoods: str ==== "+str +" goodsbean====  "+goodsBean.toString());
        }
        dbManager.closeDataBase();

        return goodsBeanList;
    }

    /**
     * 获取指定id商品
     */
    public GoodsBean getGoods(int goodsid) {
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("goods", R.raw.goods);
        SQLiteDatabase db = dbManager.getDb();

        String sql = "select * from " + "goodstb" + " where " + "goodsid" + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{goodsid + ""});
        GoodsBean goodsBean = new GoodsBean();
        while (cursor.moveToNext()) {
            goodsBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            goodsBean.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsid")));
            goodsBean.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
            goodsBean.setIntroduce(cursor.getString(cursor.getColumnIndex("introduce")));
            goodsBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
        }
        dbManager.closeDataBase();
        return goodsBean;
    }

    /**
     * 判断用户是否存在
     */
    public UserInfoBean isUserExit(String userName, String password) {
        List<UserInfoBean> userInfoBeanList = new ArrayList<>();
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("userinfo", R.raw.userinfo);
        SQLiteDatabase db = dbManager.getDb();

        Cursor cursor = db.query("userinfotb", null, null, null, null, null, null);

        if (cursor.moveToNext()) {

            do {
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUserId(cursor.getInt(cursor.getColumnIndex("userid")));
                userInfoBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                userInfoBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userInfoBean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                userInfoBean.setAge(cursor.getString(cursor.getColumnIndex("age")));
                userInfoBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                userInfoBean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                userInfoBeanList.add(userInfoBean);
            } while (cursor.moveToNext());
        }
        dbManager.closeDataBase();

        if (userInfoBeanList.size() != 0) {
            for (int i = 0; i < userInfoBeanList.size(); i++) {
                if (userInfoBeanList.get(i).getPassword().equals(password) && userInfoBeanList.get(i).getUsername().equals(userName))
                    return userInfoBeanList.get(i);
            }
        }
        return null;
    }

    /**
     * 判断用户是否存在
     */
    public UserInfoBean isUserExit(String userName) {
        List<UserInfoBean> userInfoBeanList = new ArrayList<>();
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("userinfo", R.raw.userinfo);
        SQLiteDatabase db = dbManager.getDb();

        Cursor cursor = db.query("userinfotb", null, null, null, null, null, null);

        if (cursor.moveToNext()) {

            do {
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUserId(cursor.getInt(cursor.getColumnIndex("userid")));
                userInfoBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                userInfoBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userInfoBean.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                userInfoBean.setAge(cursor.getString(cursor.getColumnIndex("age")));
                userInfoBean.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                userInfoBean.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                userInfoBeanList.add(userInfoBean);
            } while (cursor.moveToNext());
        }
        dbManager.closeDataBase();

        if (userInfoBeanList.size() != 0) {
            for (int i = 0; i < userInfoBeanList.size(); i++) {
                if (userInfoBeanList.get(i).getUsername().equals(userName))
                    return userInfoBeanList.get(i);
            }
        }
        return null;
    }


    /**
     * 用户注册
     */
    public void registerUser(UserInfoBean userInfoBean) {
        String userName = userInfoBean.getUsername();
        String password = userInfoBean.getPassword();
        String sex = userInfoBean.getSex();
        String age = userInfoBean.getAge();
        String phone = userInfoBean.getPhone();
        String address = userInfoBean.getAddress();

        dbManager = new DbManager(mContext);
        dbManager.openDataBase("userinfo", R.raw.goods);
        SQLiteDatabase db = dbManager.getDb();

        ContentValues cValue = new ContentValues();
        cValue.put("username", userName);
        cValue.put("password", password);
        cValue.put("sex", sex);
        cValue.put("age", age);
        cValue.put("phone", phone);
        cValue.put("address", address);
        db.insert("userinfotb", null, cValue);
        dbManager.closeDataBase();
    }

    /**
     * 修改用户信息
     */
    public void updateUserInfo(UserInfoBean userInfoBean) {

        dbManager = new DbManager(mContext);
        dbManager.openDataBase("userinfo", R.raw.goods);
        SQLiteDatabase db = dbManager.getDb();

        String userName = userInfoBean.getUsername();
        String password = userInfoBean.getPassword();
        String sex = userInfoBean.getSex();
        String age = userInfoBean.getAge();
        String phone = userInfoBean.getPhone();
        String address = userInfoBean.getAddress();

        ContentValues cValue = new ContentValues();
        cValue.put("username", userName);
        cValue.put("password", password);
        cValue.put("sex", sex);
        cValue.put("age", age);
        cValue.put("phone", phone);
        cValue.put("address", address);
        db.update("userinfotb", cValue
                , "username" + "=?"
                , new String[]{userName});

        dbManager.closeDataBase();
    }

    /**
     * 获取购物车商品
     */
    public List<CarBean> getAllCarGoods(int userid) {
        List<CarBean> carBeanList = new ArrayList<>();

        dbManager = new DbManager(mContext);
        dbManager.openDataBase("shoppingcar", R.raw.shoppingcar);
        SQLiteDatabase db = dbManager.getDb();

        String sql = "select * from " + "shoppingcartb" + " where " + "userid" + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{userid + ""});

        while (cursor.moveToNext()) {
            CarBean carBean = new CarBean();
            carBean.setCarid(cursor.getInt(cursor.getColumnIndex("carid")));
            carBean.setUserid(cursor.getInt(cursor.getColumnIndex("userid")));
            carBean.setGoodsid(cursor.getInt(cursor.getColumnIndex("goodsid")));
            carBean.setGoodsname(cursor.getString(cursor.getColumnIndex("goodsname")));
            carBean.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
            carBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            carBean.setQuantity(cursor.getInt(cursor.getColumnIndex("quantity")));
            carBean.setChecked(cursor.getString(cursor.getColumnIndex("checked")));
            carBeanList.add(carBean);
        }
        dbManager.closeDataBase();
        return carBeanList;
    }

    /**
     * 删除购物车商品
     */
    public void deleteCar(int carid) {
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("shoppingcar", R.raw.shoppingcar);
        SQLiteDatabase db = dbManager.getDb();
        db.delete("shoppingcartb"
                , "carid" + "=?"
                , new String[]{String.valueOf(carid)});

        Log.e(TAG, "deleteCar: caid ==" + carid);
        dbManager.closeDataBase();
    }

    /**
     * 修改购物车
     */
    public void updateCar(CarBean carBean) {
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("shoppingcar", R.raw.shoppingcar);
        SQLiteDatabase db = dbManager.getDb();
        int carid = carBean.getCarid();
        int userid = carBean.getUserid();
        int goodsid = carBean.getGoodsid();
        String goodsname = carBean.getGoodsname();
        String photo = carBean.getPhoto();
        int price = carBean.getPrice();
        int quantity = carBean.getQuantity();
        String checked = carBean.getChecked();

        ContentValues cValue = new ContentValues();
        cValue.put("carid", carid);
        cValue.put("userid", userid);
        cValue.put("goodsid", goodsid);
        cValue.put("goodsname", goodsname);
        cValue.put("photo", photo);
        cValue.put("price", price);
        cValue.put("quantity", quantity);
        cValue.put("checked", checked);
        db.update("shoppingcartb", cValue
                , "carid" + "=?"
                , new String[]{String.valueOf(carid)});

        dbManager.closeDataBase();
    }

    /**
     * 添加购物车
     */
    public void addCar(CarBean carBean) {
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("shoppingcar", R.raw.shoppingcar);
        SQLiteDatabase db = dbManager.getDb();
        int carid = carBean.getCarid();
        int userid = carBean.getUserid();
        int goodsid = carBean.getGoodsid();
        String goodsname = carBean.getGoodsname();
        String photo = carBean.getPhoto();
        int price = carBean.getPrice();
        int quantity = carBean.getQuantity();
        String checked = carBean.getChecked();

        ContentValues cValue = new ContentValues();
        cValue.put("userid", userid);
        cValue.put("goodsid", goodsid);
        cValue.put("goodsname", goodsname);
        cValue.put("photo", photo);
        cValue.put("price", price);
        cValue.put("quantity", quantity);
        cValue.put("checked", checked);
        db.insert("shoppingcartb", null, cValue);

        dbManager.closeDataBase();
    }

    /**
     * 获取所有订单
     *
     * @param userid
     * @return
     */
    public List<OrderBean> getAllOrder(int userid) {
        List<OrderBean> orderBeanList = new ArrayList<>();

        dbManager = new DbManager(mContext);
        dbManager.openDataBase("order", R.raw.order);
        SQLiteDatabase db = dbManager.getDb();

        String sql = "select * from " + "ordertb" + " where " + "userid" + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{userid + ""});

        while (cursor.moveToNext()) {
            OrderBean orderBean = new OrderBean();
            orderBean.setOrderDate(cursor.getString(cursor.getColumnIndex("orderDate")));
            orderBean.setOrderId(cursor.getInt(cursor.getColumnIndex("orderId")));
            orderBean.setUserId(cursor.getInt(cursor.getColumnIndex("userId")));
            orderBean.setTotalPrice(cursor.getInt(cursor.getColumnIndex("totalPrice")));
            orderBeanList.add(orderBean);
        }
        dbManager.closeDataBase();
        return orderBeanList;
    }

    /**
     * 获取订单详情
     *
     * @param orderFid
     * @return
     */
    public List<OrderDetailBean> getOrderDetail(int orderFid) {
        List<OrderDetailBean> orderDetailBeans = new ArrayList<>();

        dbManager = new DbManager(mContext);
        dbManager.openDataBase("order", R.raw.order);
        SQLiteDatabase db = dbManager.getDb();

        String sql = "select * from " + "orderDetailtb" + " where " + "orderFid" + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{orderFid + ""});

        while (cursor.moveToNext()) {
            OrderDetailBean orderDetailBean = new OrderDetailBean();
            orderDetailBean.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsId")));
            orderDetailBean.setGoodsName(cursor.getString(cursor.getColumnIndex("goodsName")));
            orderDetailBean.setOderFid(cursor.getInt(cursor.getColumnIndex("orderFid")));
            orderDetailBean.setGoodsPrice(cursor.getInt(cursor.getColumnIndex("goodsPrice")));
            orderDetailBeans.add(orderDetailBean);
        }
        dbManager.closeDataBase();
        return orderDetailBeans;
    }

    /**
     * 添加订单
     */
    public void addOrder(OrderBean orderBean,List<OrderDetailBean> orderDetailBeanList) {
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("order", R.raw.order);
        SQLiteDatabase db = dbManager.getDb();
        int orderId = orderBean.getOrderId();
        int userId = orderBean.getUserId();
        String orderDate = orderBean.getOrderDate();
        int totalPrice = orderBean.getTotalPrice();

        for (int i = 0; i < orderDetailBeanList.size(); i++) {
            int goodsId = orderDetailBeanList.get(i).getGoodsId();
            int goodsPrice = orderDetailBeanList.get(i).getGoodsPrice();
            String goodsName = orderDetailBeanList.get(i).getGoodsName();

            ContentValues cValue = new ContentValues();
            cValue.put("goodsId", goodsId);
            cValue.put("orderFId", orderId);
            cValue.put("goodsName", goodsName);
            cValue.put("goodsPrice", goodsPrice);
            db.insert("orderDetailtb", null, cValue);
        }

        ContentValues cValue = new ContentValues();
        cValue.put("orderId", orderId);
        cValue.put("userId", userId);
        cValue.put("orderDate", orderDate);
        cValue.put("totalPrice", totalPrice);
        db.insert("ordertb", null, cValue);

        dbManager.closeDataBase();
    }


    /**
     * 删除订单
     * @param orderId
     */
    public void deleteOrder(int orderId){
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("order", R.raw.order);
        SQLiteDatabase db = dbManager.getDb();
        db.delete("ordertb"
                , "orderId" + "=?"
                , new String[]{String.valueOf(orderId)});
        db.delete("orderDetailtb"
                , "orderFid" + "=?"
                , new String[]{String.valueOf(orderId)});
        dbManager.closeDataBase();
    }

    /**
     * 获取足迹
     * @param userName
     * @return
     */
    public List<GoodsBean> getFoot(String userName){
        List<GoodsBean> goodsBeanList = new ArrayList<>();

        dbManager = new DbManager(mContext);
        dbManager.openDataBase("goodsrecently", R.raw.goodsrecently);
        SQLiteDatabase db = dbManager.getDb();

        String sql = "select * from " + "goodsrecentlytb" + " where " + "userName" + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{userName});

        while (cursor.moveToNext()) {
            GoodsBean goodsBean = new GoodsBean();
            goodsBean.setTitle(cursor.getString(cursor.getColumnIndex("photo")));
            goodsBean.setGoodsId(cursor.getInt(cursor.getColumnIndex("goodsID")));
            goodsBean.setPhoto(cursor.getString(cursor.getColumnIndex("title")));
            goodsBean.setIntroduce(cursor.getString(cursor.getColumnIndex("introduce")));
            goodsBean.setType(cursor.getString(cursor.getColumnIndex("type")));
            goodsBean.setPrice(cursor.getInt(cursor.getColumnIndex("price")));
            goodsBeanList.add(goodsBean);
        }
        dbManager.closeDataBase();
        return goodsBeanList;
    }

    /**
     * 添加足迹
     * @param userName
     * @param goodsBean
     */
    public void addFoot(String userName,GoodsBean goodsBean){
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("goodsrecently", R.raw.goodsrecently);
        SQLiteDatabase db = dbManager.getDb();
        String username = userName;
         int goodsId = goodsBean.getGoodsId();
         String photo = goodsBean.getPhoto();
         String title= goodsBean.getTitle();
         String type= goodsBean.getType();
         String introduce= goodsBean.getIntroduce();
         int price= goodsBean.getPrice();

        ContentValues cValue = new ContentValues();
        cValue.put("username", username);
        cValue.put("goodsID", goodsId);
        cValue.put("photo", title);
        cValue.put("title", photo);
        cValue.put("type", type);
        cValue.put("introduce", introduce);
        cValue.put("price", price);
        db.insert("goodsrecentlytb", null, cValue);

        dbManager.closeDataBase();
    }

    /**
     * 删除足迹
     * @param username
     * @param goodsId
     */
    public void deleteFoot(String username,int goodsId){
        dbManager = new DbManager(mContext);
        dbManager.openDataBase("goodsrecently", R.raw.goodsrecently);
        SQLiteDatabase db = dbManager.getDb();
        db.delete("goodsrecentlytb"
                , "username =? and goodsID =?"
                , new String[]{username,String.valueOf(goodsId)});

        dbManager.closeDataBase();
    }

}





