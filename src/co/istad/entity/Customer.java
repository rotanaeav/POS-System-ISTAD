package co.istad.entity;

public class Customer {
    private String id;
    private String name;
    private String phone = "None";
    private String type;
    public Customer(){};
    public Customer(String id, String name, String phone,String type) {
        this.id = id;
        this.name = name;
        setPhone(phone);
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        if(phone == null || phone.isEmpty()) {
            phone = "None";
        }
        else {
            this.phone = phone;
        }
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getType() {
        return type;
    }
}
