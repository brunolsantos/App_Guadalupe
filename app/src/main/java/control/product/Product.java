package control.product;

import com.example.phiin.app_guadalupe.R;

import java.io.Serializable;

/**
 * Created by phiin on 29/03/2017.
 */

public class Product implements Serializable{
    private int cod;
    private String productName;
    private int qtdy_registered;
    private double price_unit;

    private int quantity;
    private double total;
    private int status;
    private int status_color;
    private int row;
    public static final int available = 1;
    public static final int unavailable = 2;
    public static final int finishing = 3;
    private boolean currently_selected;

    public Product(){}
    public Product(String product, double price_unit, int quantity, int total, int status){
        setProductName(product);
        setPrice_unit(price_unit);
        setQuantity(quantity);
        setTotal(total);
        setStatus(status);
        setCurrently_selected(false);
    }


    public void setProductName(String productName){
        this.productName = productName;
    }
    public String getProductName(){
        return this.productName;
    }

    public void setPrice_unit(double price_unit){
        this.price_unit = price_unit;
    }
    public double getPrice_unit(){
        return this.price_unit;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }
    public int getQuantity(){
        return this.quantity;
    }

    public void setTotal(double total){
        this.total = total;
    }
    public double getTotal(){return this.total; }

    public void setCurrently_selected(boolean selected){this.currently_selected = selected;}
    public boolean getCurrently_selected(){return this.currently_selected;}

    public void setStatus(int status){
        this.status = status;
        setStatus_color(this.status);
    }
    public int getStatus(){return this.status;}

    public int getStatus_color() {
        return status_color;
    }

    public void setStatus_color(int status_color) {
        switch(status_color){
            case available:
                this.status_color = R.color.background_color_general;
                break;
            case unavailable:
                this.status_color = R.color.unavailable_product_bg;
                break;
            case finishing:
                this.status_color = R.color.finishing_product_bg;
                break;
            default:
                this.status_color = R.color.background_color_general;
                break;
        }
    }

    public int getRow() {return row;}
    public void setRow(int row) {this.row = row;}

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public int getQtdy_registered() {
        return qtdy_registered;
    }

    public void setQtdy_registered(int qtdy_registered) {
        this.qtdy_registered = qtdy_registered;
    }
}
