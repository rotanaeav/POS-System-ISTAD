package co.istad.service;

import co.istad.dao.ProductDao;
import co.istad.dao.impl.ProductFileDao;
import co.istad.entity.Product;
import java.util.List;
import static co.istad.utils.InputUtils.*;
import static co.istad.utils.PrintUtils.*;
import static co.istad.view.TableUtils.*;

public class ProductService {

    private final ProductDao productDao = new ProductFileDao();

    public void AddProduct() {
        printHead("STOCK MANAGEMENT");
        List<Product> allProducts = productDao.selectAll();
        renderProducts(allProducts);

        String inputId = readText("Enter ID to Restock (or press ENTER to create NEW): ");
        Product foundProduct = null;
        boolean isIdProvided = !inputId.isEmpty();

        if (isIdProvided) {
            try {
                int id = Integer.parseInt(inputId);
                for (Product p : allProducts) {
                    if (p.getId().equals(id)) {
                        foundProduct = p;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                printErr("Error");
            }
        }

        if (foundProduct != null) {
            restockProduct(foundProduct);

        } else if (isIdProvided) {
            printWarm("Product ID [" + inputId + "] not found.");

            boolean create = readconfirm("Do you want to create it as a NEW product?");

            if (create) {
                addNewProduct(allProducts);
            } else {
                printInfo("Returning to menu...");
            }

        } else {
            addNewProduct(allProducts);
        }
    }

    private void addNewProduct(List<Product> allProducts) {
        int nextId = 1001;
        if (!allProducts.isEmpty()) {
            int maxId = allProducts.stream().mapToInt(Product::getId).max().orElse(1000);
            nextId = maxId + 1;
        }

        printHead("CREATING NEW PRODUCT (ID: " + nextId + ")");

        String name = readValidText("Name: ");
        double cost = readDouble("Import Price (Cost): ");
        double price = readDouble("Sale Price: ");
        int qty = readInt("Qty: ");
        String category = readValidText("Category: ");

        Product newP = new Product(nextId, name, price, qty, category, "Active", cost);
        productDao.insert(newP);
        printTrue("Product Created Successfully!");
    }

    public void searchProduct() {
        printHead("SEARCH PRODUCT");

        String keyword = readValidText("Enter ID or Name to search: ");

        List<Product> results = productDao.search(keyword);

        if (results.isEmpty()) {
            printErr("No products found matching: " + keyword);
        } else {
            printInfo("Found " + results.size() + " result(s):");
            renderProducts(results);
        }
    }

    private void restockProduct(Product p) {
        printHead("RESTOCKING: " + p.getName() + " (Current: " + p.getQty() + ")");
        int addQty = readInt("Qty to add: ");

        p.setQty(p.getQty() + addQty);
        p.setStatus("Active");

        productDao.insert(p);
        printTrue("Stock Updated!");
    }

    public void editProduct() {
        printHead("UPDATE PRODUCT DETAILS");

        renderProducts(productDao.selectAll());

        int id = readInt("Enter Product ID to Edit: ");
        Product p = productDao.findById(id);

        if (p == null) {
            printErr("Product not found!");
            return;
        }

        printInfo("Current: [Name: " + p.getName() + "] [Price: $" + p.getPrice() + "]");
        printInfo("Type new value or Press [ENTER] to skip.");

        String newName = readText("New Name: ");
        if (!newName.isEmpty()) {
            p.setName(newName);
        }

        String newPriceStr = readText("New Price: ");
        if (!newPriceStr.isEmpty()) {
            try {
                double newPrice = Double.parseDouble(newPriceStr);
                p.setPrice(newPrice);
            } catch (NumberFormatException e) {
                printWarm("Invalid price format. Keeping old price.");
            }
        }

        String newCostStr = readText("New Cost: ");
        if (!newCostStr.isEmpty()) {
            try {
                double newCost = Double.parseDouble(newCostStr);
                p.setCost(newCost);
            } catch (NumberFormatException e) {
                printWarm("Invalid cost format. Keeping old cost.");
            }
        }

        String newCat = readText("New Category: ");
        if (!newCat.isEmpty()) {
            p.setCategory(newCat);
        }

        productDao.insert(p);
        printTrue("Product Details Updated!");
    }

    public void deleteProduct() {
        printHead("DELETE PRODUCT");

        renderProducts(productDao.selectAll());

        int id = readInt("Enter ID to delete: ");
        Product p = productDao.findById(id);

        if (p == null) {
            printErr("Product not found!");
            return;
        }

        if (p.getStatus().equalsIgnoreCase("Deleted")) {
            printWarm("Product is already deleted.");
            return;
        }

        println("Found: " + p.getName() + " (Price: $" + p.getPrice() + ")");

        boolean isConfirmed = readconfirm("Are you sure you want to delete this?");

        if (isConfirmed) {
            productDao.deleteById(id);
            printTrue("Product '" + p.getName() + "' has been deleted.");
        } else {
            printInfo("Operation cancelled.");
        }
    }

    public void viewProducts() {
        List<Product> allProducts = productDao.selectAll();

        List<Product> activeProducts = new java.util.ArrayList<>();
        for(Product p : allProducts) {
            if(!p.getStatus().equalsIgnoreCase("Deleted")) {
                activeProducts.add(p);
            }
        }
        //paginate
        int pageSize = 5; // How many rows per page
        int totalRecords = activeProducts.size();
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
        int currentPage = 1;
        while (true) {
            printHead("PRODUCT LIST (Page " + currentPage + "/" + totalPages + ")");

            int start = (currentPage - 1) * pageSize;
            int end = Math.min(start + pageSize, totalRecords);

            if (activeProducts.isEmpty()) {
                printInfo("No products found.");
                return;
            }

            List<Product> pageData = activeProducts.subList(start, end);

            renderProducts(pageData);
            String[] navOptions = {
                    "N. Next Page",
                    "P. Previous Page",
                    "B. Back"
            };
            renderMenu("PAGE " + currentPage + "/" + totalPages, navOptions);
            String choice = readText(">> Navigation: ").toLowerCase();

            switch (choice) {
                case "n":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        printWarm("You are on the last page.");
                    }
                    break;
                case "p":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        printWarm("You are on the first page.");
                    }
                    break;
                case "b":
                    return;
                default:
                    printErr("Invalid navigation option.");
            }
        }
    }
}