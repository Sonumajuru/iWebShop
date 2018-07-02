package webshop.model;

public class Brand {

    private String name; /** full name of product */

    public Brand(String name) {
        this.setName(name);
    } /** for XML Serialization */

    public String getName() {
        return name;
    } /** for XML Serialization */

    public void setName(String name) {
        this.name = name;
    } /** for XML Serialization */
}
