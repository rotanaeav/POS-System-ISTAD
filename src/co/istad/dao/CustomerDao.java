package co.istad.dao;

import co.istad.entity.Customer;

import java.util.List;

public interface CustomerDao {

    public List<Customer> getAll();
    public void insert(Customer customer);
    public Customer searchById(String id);
    public Customer searchByName(String name);
}
