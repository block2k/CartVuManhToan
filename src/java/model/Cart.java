/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author ADMIN
 */
public class Cart {

    private int id;
    private String name;
    private int quantity;
    private double price;
    private double totalPrice;
    private int productId;

    public Cart() {
    }

    public Cart(int id, String name, int quantity, double price, double totalPrice, int productId) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.productId = productId;
    }

    public int getId() {
        return id;
    }

    public String genId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public int getProductId() {
        return productId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Cart{" + "id=" + id + ", name=" + name + ", quantity=" + quantity + ", price=" + price + ", totalPrice=" + totalPrice + '}';
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getTotalPriceVND() {
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(totalPrice);
    }

    public String getPriceVND() {
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(price);
    }

}
