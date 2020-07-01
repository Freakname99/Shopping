package com.shoppingapp.beans;

import java.io.Serializable;

public class OrderDetailBean implements Serializable {
    private int goodsId;
    private int oderFid;
    private int goodsPrice;
    private String goodsName;


    public OrderDetailBean(int goodsId, int oderFid, String goodsName, int goodsPrice) {
        this.goodsId = goodsId;
        this.oderFid = oderFid;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
    }

    public OrderDetailBean(int goodsId, int goodsPrice, String goodsName) {
        this.goodsId = goodsId;
        this.goodsPrice = goodsPrice;
        this.goodsName = goodsName;
    }

    public OrderDetailBean() {
    }


    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getOderFid() {
        return oderFid;
    }

    public void setOderFid(int oderFid) {
        this.oderFid = oderFid;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public int getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(int goodsPrice) {
        this.goodsPrice = goodsPrice;
    }


}
