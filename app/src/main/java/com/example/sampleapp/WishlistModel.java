package com.example.sampleapp;

public class WishlistModel {
    private String productImage;
    private String productTitle;
    private long freeCoupons;
    private String rating;
    private long totalRatings;
    private String productPrice;
    private String cutTedPrice;
    private Boolean COD;

    public WishlistModel(String productImage, String productTitle, long freeCoupons, String rating, long totalRatings, String productPrice, String cutTedPrice, Boolean COD) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupons = freeCoupons;
        this.rating = rating;
        this.totalRatings = totalRatings;
        this.productPrice = productPrice;
        this.cutTedPrice = cutTedPrice;
        this.COD = COD;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupons() {
        return freeCoupons;
    }

    public void setFreeCoupons(long freeCoupons) {
        this.freeCoupons = freeCoupons;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCutTedPrice() {
        return cutTedPrice;
    }

    public void setCutTedPrice(String cutTedPrice) {
        this.cutTedPrice = cutTedPrice;
    }

    public Boolean getCOD() {
        return COD;
    }

    public void setCOD(Boolean COD) {
        this.COD = COD;
    }
}
