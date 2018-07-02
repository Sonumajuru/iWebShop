package webshop.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement /** for XML Serialization */
public class Product extends Brand{

    private int id; /** Product ID */
    private String name; /** full name of product */
    private Double value;
    private int duration;
    private String platform;
    private String country;


    public Product(int nr, String name) {
        super(name);
        this.setId(nr);
        this.setName(name);
    } /** for XML Serialization */

    public Product() {
        super("");
        this.setId(0);
        this.setName("");
    } /** for XML Serialization */

    public String getName() {
        return name;
    } /** for XML Serialization */

    public void setName(String name) {
        this.name = name;
    } /** for XML Serialization */

    public int getId() {
        return id;
    } /** for XML Serialization */

    public void setId(int id) {
        this.id = id;
    } /** for XML Serialization */

    public Double getValue() { return value;} /** for XML Serialization */

    public void setValue(Double value) { this.value = value; } /** for XML Serialization */

    public int getDuration() { return duration; } /** for XML Serialization */

    public void setDuration(int duration) { this.duration = duration; } /** for XML Serialization */

    public String getPlatform() { return platform; } /** for XML Serialization */

    public void setPlatform(String platform) { this.platform = platform; } /** for XML Serialization */

    public String getCountry() { return country; } /** for XML Serialization */

    public void setCountry(String country) { this.country = country; } /** for XML Serialization */
}
