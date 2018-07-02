package webshop.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;

import org.glassfish.jersey.client.ClientConfig; // Include when doing rest for the ClientConfig
import webshop.model.Product;

public class webStoreClient {

    //connect to the service
    private static WebTarget serviceTarget;

    /** WebShopRESTClient */
    public webStoreClient() {

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        /** Using Tomcat "http://localhost:8080/RESTServer_war/rest" */
        URI baseURI = UriBuilder.fromUri("http://localhost:8080/rest").build();
        serviceTarget = client.target(baseURI);
    }

    /** Main Method */
    public static void main(String[] args) {
        webStoreClient webClient = new webStoreClient();
        Product productOne = new Product(6,"Tomato Sauce");

        hello(); // displays welcome service running
        nrOfProducts(); // displays number of products
        getProducts(4); // gets a specific product with specific id
        prodNameByQuery();  // gets a product ny name using query
        productNameByPath(); // gets a product ny name using path
        updateProductStockInfo(); // updates a product account
        createStock(); // creates a product
        createProductObject(productOne);  // creates a product object
        returnAllProducts(); // returns all list of products
//        downLoadFIle(); // downloading files
//        uploadFile(); // uploading files
        //deleteProductNameByQuery(); // deletes a product by name using query
        deleteProductNameByPath(); // deletes a product by name using path

    }

    /** Methods */
    /** (1) GET operation WITHOUT parameters */

    public static void hello() {
        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product").path("hello");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get(); /** getting response from class */
        //read the result/answer from the http response.
        String result = response.readEntity(String.class);
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            System.out.println(result);
        } else {
            System.err.println(result);
        }
    }

    public static void nrOfProducts() {
        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product").path("count");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = resourceTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get();  /** getting response from class */
        //read the result/answer from the http response.
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Integer answer = response.readEntity(Integer.class);
            System.out.println("The webShop has " + answer + " products!");
        } else {
            System.err.println("Error : " + response);
        }
    }

    /** (2) GET operation with QUERY parameters. */
    public static void prodNameByQuery() {
        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product").queryParam("id", 9);
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get(); /** getting response from entity */
        //read the result/answer from the http response.
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String result = response.readEntity(String.class);
            System.out.println(result);
        } else {
            System.err.println(response.readEntity(String.class));
        }
    }

    /** (3) GET operation with PATH parameters. */
    public static void productNameByPath() {
        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product").path("2");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = resourceTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get(); /** getting response from entity */
        //read the result/answer from the http response.
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            String result = response.readEntity(String.class);
            System.out.println(result);
        } else {
            System.err.println(response.readEntity(String.class));
        }
    }

    /** 4.B operation with QUERY parameters. */
    public static void deleteProductNameByQuery() {
        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product").queryParam("id", 2);
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.delete(); /** deleting entity from class */
        //read the result/answer from the http response.
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("DELETE SUCCESSFUL!!");
        } else {
            System.out.println("DELETE UNSUCCESSFUL!!! (" + response + ")");
        }
    }

    /** 4.B operation with PATH parameters. */
    public static void deleteProductNameByPath() {
        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product").path("4");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN

        Response response = requestBuilder.delete(); /** deleting entity from class */
        //read the result/answer from the http response.
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("DELETE SUCCESSFUL!! to view follow the URL");
        } else {
            System.out.println("DELETE UNSUCCESSFUL!!! (" + response + ")");
        }
    }

    /** 4.C Update operation with PATH parameters. */
    public static void updateProductStockInfo(){
        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        Form form = new Form();
        form.param("id","2");
        form.param("name", "Curry Powder Sauce");
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = requestBuilder.put(entity); /** updating / actualising entity */

        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("UPDATE SUCCESSFUL!! to view follow the URL");
        } else {
            System.out.println("UPDATE UNSUCCESSFUL!!! (" + response + ")");
        }
    } // this is called on the GUI since if called on the console would require the path but the gui form only requires the formparam

    /** 4.D Create operation with PATH parameters. */
    public static void createStock(){

        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        Form form = new Form();
        form.param("id","5");
        form.param("name", "Pepperini Sauce");
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = requestBuilder.post(entity); /** creating / posting entity*/

        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("CREATE SUCCESSFUL!!");
        } else {
            System.out.println("CREATE UNSUCCESSFUL!!! (" + response + ")");
        }
    }

    //==================== XML or JSON BELOW =========================//
    /** 5.D Getting product objects. */
    public static void getProducts(int id) {
        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product").path("stock").path(Integer.toString(id));
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = resourceTarget.request().accept(MediaType.APPLICATION_XML);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get();  /** getting response from class */
        //read the result/answer from the http response.

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Product product = response.readEntity(Product.class);
            System.out.println("The name of product with id: " + id + " is " + product.getName() + " " + product.getId());
        } else {
            System.err.println("Error : " + response);
        }

    }

    /** 5.D  1 Getting product objects. */
    public static void getProductsName(String name) {
        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product").path("stock").path(name);
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = resourceTarget.request().accept(MediaType.APPLICATION_XML);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get();  /** getting response from class */
        //read the result/answer from the http response.

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Product product = response.readEntity(Product.class);
            System.out.println("The name of product: " + name + " name is " + product.getName() + " " + product.getId());
        } else {
            System.err.println("Error : " + response);
        }

    }

    /** 5.D Create product objects. */
    public static void createProductObject(Product product){

        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = resourceTarget.request().accept(MediaType.TEXT_PLAIN); /** choose XML or JSON */
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.post(Entity.entity(product, MediaType.APPLICATION_JSON));  /** getting response from class */
        //read the result/answer from the http response.

        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("Product created name : " + product.getName() +  " has been added");
        } else {
            System.err.println(response.readEntity(String.class));
        }
    }

    /** 5.E Return student objects. */
    public static void returnAllProducts(){

        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product");
        //build the request: MediaType.TEXT_PLAIN
        Builder requestBuilder = resourceTarget.request().accept(MediaType.APPLICATION_JSON); /** choose XML or JSON */
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get();  /** getting response from class */
        //read the result/answer from the http response.

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            GenericType<ArrayList<Product>> genericType = new  GenericType<ArrayList<Product>>() {};
            ArrayList<Product> list = response.readEntity(genericType);

            for (Product product: list) {
                System.out.println("Product name : " + product.getName() +  " and id: " + product.getId());
            }
        } else {
            System.err.println(response.readEntity(String.class));
        }
    }

}
