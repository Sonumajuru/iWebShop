package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import webshop.model.Product;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.ArrayList;


public class WebShopController {

    //connect to the service
    private static WebTarget serviceTarget;

    public WebShopController() {
        org.glassfish.jersey.client.ClientConfig config = new org.glassfish.jersey.client.ClientConfig();
        javax.ws.rs.client.Client client = ClientBuilder.newClient(config);
        /** Using Tomcat "http://localhost:8080/RESTServer_war/rest" */
        URI baseURI = UriBuilder.fromUri("http://localhost:8080/RESTServer_war/rest").build();
        serviceTarget = client.target(baseURI);
    }

    @FXML
    public Label webStoreName;
    @FXML
    public ListView<String> ListViewName;
    @FXML
    public ListView<Integer> ListViewID;
    @FXML
    public ListView<Integer> ListViewStock;
    @FXML
    public TextField prodName;
    @FXML
    public TextField prodId;


    @FXML
    public  void handleWebNameButtonAction(ActionEvent event) {

//        //connect to your operation
//        WebTarget operationTarget = serviceTarget.path("product").path("hello");
//        //build the request: MediaType.TEXT_PLAIN
//        Invocation.Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
//        //execute the request: MediaType.TEXT_PLAIN
//        Response response = requestBuilder.get(); /** getting response from class */
//        //read the result/answer from the http response.
//        String result = response.readEntity(String.class);
//        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
//            System.out.println(result);
//            webStoreName.setText(result);
//        } else {
//            System.err.println(result);
//        }

        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product").path("name");
        //build the request: MediaType.TEXT_PLAIN
        Invocation.Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        Form form = new Form();
        form.param("name", String.valueOf(prodName.getText()));
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = requestBuilder.put(entity); /** updating / actualising entity */

        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Product Found!");
            alert.showAndWait();
            System.out.println("FOUND SUCCESSFUL!! to view follow the URL");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Found Unsuccessful!");
            alert.showAndWait();
            System.out.println("FOUND UNSUCCESSFUL!!! (" + response + ")");
        }

    }

    @FXML
    public  void handleProdListButtonAction(ActionEvent event){

        ListViewName.getItems().clear();
        ListViewID.getItems().clear();
        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product");
        //build the request: MediaType.TEXT_PLAIN
        Invocation.Builder requestBuilder = resourceTarget.request().accept(MediaType.APPLICATION_JSON); /** choose XML or JSON */
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get();  /** getting response from class */
        //read the result/answer from the http response.

        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            GenericType<ArrayList<Product>> genericType = new  GenericType<ArrayList<Product>>() {};
            ArrayList<Product> list = response.readEntity(genericType);

            for (Product product: list) {
                ListViewName.getItems().add(product.getName());
                ListViewID.getItems().add(product.getId());
            }
        } else {
            System.err.println(response.readEntity(String.class));
        }
    }

    @FXML
    public  void handleProdCountsButtonAction(ActionEvent event){
        //connect to your operation
        WebTarget resourceTarget = serviceTarget.path("product").path("count");
        //build the request: MediaType.TEXT_PLAIN
        Invocation.Builder requestBuilder = resourceTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN
        Response response = requestBuilder.get();  /** getting response from class */
        //read the result/answer from the http response.
        if (response.getStatus() == Response.Status.OK.getStatusCode()) {
            Integer answer = response.readEntity(Integer.class);
            System.out.println("The webShop has " + answer + " products!");
            ListViewStock.getItems().add(answer);
        } else {
            System.err.println("Error : " + response);
        }

    }

    @FXML
    public  void  handleDeleteProdButtonAction(ActionEvent event){

        String id = prodId.getText();
        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product").path(id);
        //build the request: MediaType.TEXT_PLAIN
        Invocation.Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        //execute the request: MediaType.TEXT_PLAIN

        Response response = requestBuilder.delete(); /** deleting entity from class */
        //read the result/answer from the http response.
        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            System.out.println("DELETE SUCCESSFUL!! to view follow the URL");
            webStoreName.setText("DELETE SUCCESSFUL!!");

        } else {
            System.out.println("DELETE UNSUCCESSFUL!!! (" + response + ")");
            webStoreName.setText("DELETE SUCCESSFUL!!");
        }
    }

    @FXML
    public void handleCreateProductButtonAction(ActionEvent event){

        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product");
        //build the request: MediaType.TEXT_PLAIN
        Invocation.Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);

        Form form = new Form();
        form.param("id", String.valueOf(prodId.getText()));
        form.param("name", String.valueOf(prodName.getText()));
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = requestBuilder.post(entity); /** creating / posting entity*/

        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("An Information Dialog");
            alert.setContentText("Product Created Successful!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("An Information Dialog");
            alert.setContentText("Product Created Unsuccessful!");
            alert.showAndWait();
        }
    }

    @FXML
    public void handleUpdateButtonAction(ActionEvent event) {
        //connect to your operation
        WebTarget operationTarget = serviceTarget.path("product").path("2");
        //build the request: MediaType.TEXT_PLAIN
        Invocation.Builder requestBuilder = operationTarget.request().accept(MediaType.TEXT_PLAIN);
        Form form = new Form();
        form.param("id", String.valueOf(prodId.getText()));
        form.param("name", String.valueOf(prodName.getText()));
        Entity<Form> entity = Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED);

        Response response = requestBuilder.put(entity); /** updating / actualising entity */

        if (response.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Product Updated Successful!");
            alert.showAndWait();
            System.out.println("UPDATE SUCCESSFUL!! to view follow the URL");
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Product Update Unsuccessful!");
            alert.showAndWait();
            System.out.println("UPDATE UNSUCCESSFUL!!! (" + response + ")");
        }
    }
}