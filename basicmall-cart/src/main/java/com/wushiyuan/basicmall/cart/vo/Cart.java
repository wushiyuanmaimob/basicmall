package com.wushiyuan.basicmall.cart.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 整个购物车
 */
public class Cart {
    List<CartItem> items;
    //商品数量
    private Integer countNum;
    //商品类型数量
    private Integer countType;
    //商品总价
    private BigDecimal totalAmount;
    //减免价格
    private BigDecimal reduce = new BigDecimal("0.00");

    public List<CartItem> getItems() {
        return items;
    }

    public Integer getCountNum() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItem cartItem:items
                 ) {
                count += cartItem.getCount();
            }
        }
        return count;
    }

    public Integer getCountType() {
        int count = 0;
        if (items != null && items.size() > 0) {
            for (CartItem cartItem:items
            ) {
                count += 1;
            }
        }
        return count;
    }

    public BigDecimal getTotalAmount() {
        BigDecimal amount = new BigDecimal("0.00");
        //计算购物项总价
        if (items != null && items.size() > 0) {
            for (CartItem cartItem:items
            ) {
                amount = amount.add(cartItem.getTotalPrice());
            }
        }
        //减去优惠价
        BigDecimal subtract = amount.subtract(getReduce());

        return subtract;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }
}
