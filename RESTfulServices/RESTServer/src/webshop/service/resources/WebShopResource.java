package webshop.service.resources;

import webshop.model.Product;
import webshop.model.Stock;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.ArrayList;

@Path("/product")
@Singleton
public class WebShopResource {

    private Stock stocks;

    public WebShopResource() {
        stocks = new Stock();
    }

    /** Displaying hellos. */
    @GET
    @Path("/hello") // To Read or Get With PATH Parameter
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello(){
        return "Welcome! Your REST Service works! you have " + stocks.getNumberOfProducts() + " products and " + stocks.ToString();
    }

    /** Counting the products. */
    @GET // To Read or Get With PATH Parameter
    @Path("/count")
    @Produces(MediaType.TEXT_PLAIN)
    public int getNumberOfProducts(){
        return stocks.getNumberOfProducts();
    }

    //http://localhost:8080/SchoolRESTService/rest/product?id=4

//    /** (2) GET operation with QUERY parameters. */
//    @GET // To Read or Get With QUERY Parameter
//    @Produces(MediaType.TEXT_PLAIN)
//    public String getProductNameQuery(@QueryParam("id") int id){
//        Product p = stocks.getProductWithId(id);
//        if (p != null){
//            return "Query display:: Product name is: " + p.getName();
//        } else {
//            throw new RuntimeException("Product with id " + id + " does not exist!");
//        }
//    }

    /** (3) GET operation with PATH parameters. */
    @GET // To Read or Get With PATH Parameter
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getProductNamePath(@PathParam("id") int id){
        Product prod = stocks.getProductWithId(id);
        if (prod != null){
            return  "PATH display:: Product name is: " + prod.getName();
        } else {
            throw new RuntimeException("Product with id " + id + " does not exist!");
        }
    }

    /** (3) GET operation with PATH parameters. */
    @GET // To Read or Get With PATH Parameter
    @Path("{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getProductNamePath(@PathParam("name") String name){
        Product prod = stocks.getProductWithName(name);
        if (prod != null){
            prod.getName();
            return  "PATH display:: Product name is: " + prod.getName();
        } else {
            throw new RuntimeException("Product name is " + name + " does not exist!");
        }
    }

    /** 4.B Delete operation with QUERY parameters. */
    @DELETE // For Deleting With QUERY Parameter
    @Produces(MediaType.TEXT_PLAIN)
    public void deleteProductQuery(@QueryParam("id") int id){
        Product p = stocks.getProductWithId(id);
        if(stocks.deleteProduct(p)){
            System.out.println("Product with id: " + id + " was deleted");
        }
        else{
            throw new RuntimeException("Product with id " + id + " does not exist!");
        }
    }

    /** 4.B Delete operation with PATH parameters. */
    @DELETE // For Deleting With PATH Parameter
    @Path("{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public void deleteProductPath(@PathParam("id") int id){
        Product p =  stocks.getProductWithId(id);
        if(stocks.deleteProduct(p)){
            System.out.println("Product with id: " + id + " was deleted");
        }
        else{
            throw new RuntimeException("Product with id " + id + " does not exist!");
        }
    }

    /** 4.C Update operation with PATH parameters. */
    @PUT // To Update or Refresh With PATH Parameter
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateProductNameForm(@FormParam("id") int id, @FormParam("name") String name){
        Product prod = stocks.getProductWithId(id);
        if (prod != null){
            prod.setName(name);
            prod.setId(id);
        } else {
            throw new RuntimeException("Product with id " + id + " does not exist!");
        }
    }

    /** 4.D Create operation with PATH parameters. */
    @POST // To Create or Put With PATH Parameter
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void createProductNameForm(@FormParam("id") int id, @FormParam("name") String name){
        System.out.println("before");
        Product prod = stocks.getProductWithId(id);
        //System.out.println(prod);
        if (prod == null){
            prod = new Product(id,name);
            stocks.addProduct(prod);
         //   System.out.println(prod.getId());
        } else {
            throw new RuntimeException("Product with id " + id + " does not exist!");
        }
    }


//    //=================== USING XML & JSON ===========================//
//
//    /** Displaying a specific product by id. */
//    @GET // To Read or Get With PATH Parameter
//    @Path("/stock/{id}")
//    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
//    public Product getProductObject(@PathParam("id") int id){
//        Product prod = stocks.getProductWithId(id);
//        if (prod != null){
//            return  prod;
//        } else {
//            throw new RuntimeException("Product with id " + id + " does not exist!");
//        }
//    }
//
//    /** Returning and displaying the list of all student. */
//    @GET
//    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public ArrayList<Product> getStudentsJSON(){
//        return stocks.getAllProducts();
//    }
//
////    /** Creating product objects. */
////    @POST // To Create or Put With PATH Parameter
////    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
////    public void createStudentJSONObject(Product product){
////        if (stocks.getProductWithId(product.getId()) == null){
////            stocks.addProduct(product);
////        } else {
////            throw new RuntimeException("Product with id " + product.getId() + " already exist!");
////        }
////    }
//
//
//    //========================= BUILDING OWN HTTP RESPONSE IN THE SERVICE ==========================//
//
//    /** (2) GET operation with QUERY parameters. */
//    @GET // To Read or Get With QUERY Parameter
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response getProductNameQuery(@QueryParam("id") int id){
//        Product prod = stocks.getProductWithId(id);
//        if (prod != null){
//            return  Response.ok(prod).build();
//        } else {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Sorry but the product with id "
//                    + id + " does not exist in our REST-WebShop store!").build();
//        }
//    }
//
//    /** Creating product objects and displaying own custom message when error happens or success. */
//    @POST // To Create or Put With PATH Parameter
//    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
//    public Response createStudentJSONOCustomMessage(Product product){
//        if (stocks.getProductWithId(product.getId()) == null){
//            stocks.addProduct(product);
//            return Response.noContent().build();
//        } else {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Sorry but the requested product with id "
//                    + product.getId() + " already exist in our REST-WebShop store!").build();
//        }
//    }
//
//    //========================= END OWN HTTP RESPONSE IN THE SERVICE ==============================//
}
