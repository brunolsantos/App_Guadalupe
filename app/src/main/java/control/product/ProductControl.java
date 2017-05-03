package control.product;

import java.util.ArrayList;

/**
 * Created by phiin on 04/04/2017.
 */

public class ProductControl {

    //SINGLETON
    private static ProductControl mInstance = null;
    public static ProductControl getInstance(){
        if(mInstance == null)
        {
            mInstance = new ProductControl();
        }
        return mInstance;
    }

    private ArrayList<Product> product_list = new ArrayList<Product>();

    public void addProduct(Product product){
        product_list.add(product);
    }

    public ArrayList<Product> getProductList(){
        return this.product_list;
    }

    public void cleanProductList(){
        this.product_list.clear();
    }

}
