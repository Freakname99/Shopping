package com.shoppingapp.beans;

import java.io.Serializable;

public class CarBean implements Serializable {
    private int carid;
    private int userid;
    private int goodsid;
    private String goodsname;
    private String photo;
    private int price;
    private int quantity;
    private String checked;


    public int getCarid() {
        return carid;
    }

    @Override
    public String toString() {
        return "CarBean{" +
                "carid=" + carid +
                ", userid=" + userid +
                ", goodsid=" + goodsid +
                ", goodsname='" + goodsname + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", checked=" + checked +
                '}';
    }

    public void setCarid(int carid) {
        this.carid = carid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public CarBean( int userid, int goodsid, String goodsname, String photo, int price, int quantity, String checked) {

        this.userid = userid;
        this.goodsid = goodsid;
        this.goodsname = goodsname;
        this.photo = photo;
        this.price = price;
        this.quantity = quantity;
        this.checked = checked;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }



    public CarBean(){}






}
