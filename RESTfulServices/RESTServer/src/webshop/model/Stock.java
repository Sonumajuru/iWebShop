package webshop.model;

import java.util.ArrayList;

public class Stock {

    private final ArrayList<Product> products = new ArrayList<>();

    public Stock() {

        products.add(new Product(1,"Ebay Cards"));
        products.add(new Product(2,"Game Cards"));
        products.add(new Product(3,"iTunes Cards"));
        products.add(new Product(4,"Mobile Call Cards"));
    }

    /** returns number of products */
    public int getNumberOfProducts(){
        return this.products.size();
    }

    /** returns specific product with id */
    public Product getProductWithId(int id) {
        for (Product s : products) {
            if (s.getId() == id) { return s;}
        }
        return null;
    }

    /** returns specific product with name */
    public Product getProductWithName(String name) {
        for (Product s : products) {
            if (s.getName() == name) { return s;}
        }
        return null;
    }

    /** returns products */
    public ArrayList<Product> getAllProducts() {
//        ArrayList<Product> prods = new ArrayList<>();
//        for (Product s : products) {
//            prods.add(s);
//        }
        return products;
    }

    /** deletes specific product with */
    public boolean deleteProduct(Product s) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equals(s)){
                products.remove(i);
                return true;
            }
        }
        return  false;
    }

    /** adding a product in the list of products */
    public boolean addProduct(Product p) {
//        products.add(p);
        boolean prodExists = false;
        for(Product prod: products){
            if(prod.getId() == p.getId()){
                prodExists = true;
                break;
            }
        }
        if(!prodExists){
            products.add(p);
            return true;
        }
        return false;
    }

    /** webShop name display */
    public String ToString(){
        return "WebShop name is REST-WebShop";
    }
}
