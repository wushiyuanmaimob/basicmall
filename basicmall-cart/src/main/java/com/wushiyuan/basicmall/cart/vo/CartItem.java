package com.wushiyuan.basicmall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物项内容
 */
public class CartItem {
    private Long skuId;
    private Boolean check = true;
    private String title;
    private String image;
    private List<String> skuAttr;
    private BigDecimal price;
    private Integer count;
    private BigDecimal totalPrice;

    public Long getSkuId() {
        return skuId;
    }

    public Boolean getCheck() {
        return check;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public List<String> getSkuAttr() {
        return skuAttr;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }

    /**
     * 计算购物项总价
     * @return
     */
    public BigDecimal getTotalPrice() {
        return this.price.multiply(new BigDecimal(this.count));
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public void setCheck(Boolean check) {
        this.check = check;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setSkuAttr(List<String> skuAttr) {
        this.skuAttr = skuAttr;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
