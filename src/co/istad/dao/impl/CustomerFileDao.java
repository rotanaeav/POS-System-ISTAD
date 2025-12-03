package co.istad.dao.impl;

import co.istad.dao.CustomerDao;
import co.istad.entity.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import static co.istad.utils.PrintUtils.*;

public class CustomerFileDao implements CustomerDao {
private static final String PATH = "data/customers.csv";
    @Override
    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();
        File getFile = new File(PATH);
        if(!getFile.exists()) return customers;
        try(
            BufferedReader reader = new BufferedReader(new FileReader(getFile))){
            String line;
            boolean isFirst = true;
            while((line=reader.readLine())!=null){
            if(isFirst){
                isFirst = false;
                continue;
            }
            String[] cells = line.split(",");
            if(cells.length == 4){
                Customer cust = new Customer();
                cust.setId(cells[0].trim());
                cust.setName(cells[1].trim());
                String phone = cells[2].trim();
                if(phone.isEmpty()) {phone = "None";}
                cust.setPhone(phone);
                cust.setType(cells[3].trim());
                customers.add(cust);
            }
            }

        }catch (IOException e) {
            printErr("Error reading customers: " + e.getMessage());
        }
        return customers;
    }
    @Override
    public void insert(Customer customer) {
        List<Customer> allCust = getAll();
        allCust.removeIf(c -> c.getId().equalsIgnoreCase(customer.getId()));
        allCust.add(customer);
        saveAll(allCust);
    }
    @Override
    public Customer searchById(String id) {
        return getAll().stream()
                .filter( cust-> cust.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
    @Override
    public Customer searchByName(String name) {
        return getAll().stream()
                .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
private void saveAll(List<Customer> customers) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH))) {
        writer.write("ID,Name,Phone,Type");
        writer.newLine();
        for (Customer c : customers) {
             String line = String.format("%s,%s,%s,%s",
                    c.getId(),
                    c.getName(),
                    c.getPhone(),
                    c.getType()
            );
            writer.write(line);
            writer.newLine();
        }
    } catch (IOException e) {
        printErr("Error saving customers: " + e.getMessage());
    }
}
}
