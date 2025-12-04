package co.istad.service;

import co.istad.dao.CustomerDao;
import co.istad.dao.impl.CustomerFileDao;
import co.istad.entity.Customer;

import java.util.List;
import static co.istad.utils.IDGenerator.generateNextId;
import static co.istad.utils.PrintUtils.*;
import static co.istad.utils.InputUtils.*; // Don't forget this for readText!
import static co.istad.view.TableUtils.*;

public class CustomerService {
    private final CustomerDao customerDB = new CustomerFileDao();
    public void viewCustomers() {
        List<Customer> all = customerDB.getAll();
        printHead("Customer List");
        //TableUtils.renderCustomers(all);
        printInfo("Total " + all.size() + " customers");
    }
    public void addCustomer() {
        printHead("Create New Customer");

        List<Customer> all = customerDB.getAll();
        String lastId = null;

        if (!all.isEmpty()) {
            lastId = all.getLast().getId();
        }
        String newID = generateNextId(lastId, "IP");
        printTrue("Generated ID: " + newID);
        String name = readValidText(">> Name: ");
        String phone = readText("Phone (Enter to skip)");
        String type = readCustType("Choice")?"Normal":"VIP";
        Customer customer = new Customer(newID, name, phone, type);
        customerDB.insert(customer);
        printTrue("Customer " + name + " added successfully!");
    }
    // --- 3. EDIT CUSTOMER ---
    public void editCustomer() {
        printHead("EDIT CUSTOMER Info");

        viewCustomers();
        String id = readText("Enter Customer ID to Edit: ");
        Customer c = customerDB.searchById(id);
        if (c == null) {
            printErr("Customer ID [" + id + "] not found.");
            return;
        }
        renderCustinfo(c);
        printInfo("Type new value or Press [ENTER] to keep current.");

        String newName = readText("New Name");
        if (!newName.isEmpty()) {
            c.setName(newName);
        }
        String newPhone = readText("New Phone");
        if (!newPhone.isEmpty()) {
            c.setPhone(newPhone);
        }
       readCustType("You need to choose");

        customerDB.insert(c);
        printTrue("Customer details updated successfully!");
    }
    public void searchCustomer() {
        printHead("Search Customer");
        viewCustomers();
        String name = readValidText("Enter Customer Name");
        Customer result = customerDB.searchByName(name);
        if (result == null) {
            printErr("Customer Name [" + name + "] not found.");
            return;
        }
        printTrue("Customer " + name + " found successfully!");
        renderCustinfo(result);
    }
}
