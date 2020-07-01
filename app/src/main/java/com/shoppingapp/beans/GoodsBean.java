package com.shoppingapp.beans;

import java.io.Serializable;

public class GoodsBean implements Serializable {
    private int goodsId;
    private String photo;
    private String title;
    private String type;
    private String introduce;
    private int price;

    public int getGoodsId() {
        return goodsId;
    }

    public GoodsBean() {

    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "goodsId=" + goodsId +
                ", photo='" + photo + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", introduce='" + introduce + '\'' +
                ", price=" + price +
                '}';
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public GoodsBean(int goodsId, String photo, String title, String type, String introduce, int price) {
        this.goodsId = goodsId;
        this.photo = photo;
        this.title = title;
        this.type = type;
        this.introduce = introduce;
        this.price = price;
    }


}
