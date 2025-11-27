package co.istad.entity;

public class Product {
    private Integer id;
    private String name;
    private Double price;
    private Integer qty;
    private String category;
    private String status; //

    public Product() {
    }

    public Product(Integer id, String name, Double price, Integer qty, String category, String status) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.qty = qty;
        this.category = category;
        this.status = "Active";
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQty() {
        return qty;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }
}
