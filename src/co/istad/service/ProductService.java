package co.istad.service;

import co.istad.dao.ProductDao;
import co.istad.dao.impl.ProductFileDao;
import co.istad.entity.Product;
import static co.istad.utils.InputUtils.*;
import co.istad.view.TableUtils;
import java.util.List;
import static co.istad.utils.PrintUtils.*;

public class ProductService {

    private final ProductDao productDao = new ProductFileDao();

    public void AddProduct() {
        printHead("STOCK MANAGEMENT");
        List<Product> allProducts = productDao.selectAll();
        TableUtils.renderProducts(allProducts);

        String inputId = readText("Enter ID to add qty (or press ENTER to create NEW)");

        Product foundProduct = null;
        boolean isId = !inputId.isEmpty();

        if (isId) {
            try {
                int id = Integer.parseInt(inputId);
                for (Product p : allProducts) {
                    if (p.getId().equals(id)) {
                        foundProduct = p;
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                printErr("System Error : "+e.getMessage());
            }
        }

        if (foundProduct != null) {
            restockProduct(foundProduct);

        } else if (isId) {
            printWarm("Product ID [" + inputId + "] not found.");

            boolean create = readconfirm("Do you want to create a new product?");

            if (create) {
                addNewProduct(allProducts);
            } else {
               printInfo("<< returning to menu...");
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

        printHead("Creating New Product (ID: " + nextId+")");

        String name = readValidText("Name: ");
        double cost = readDouble("Import Price");
        double price = readDouble("Sale Price");

        int qty = readInt("Qty: ");
        String category = readValidText("Category: ");

        Product newP = new Product(nextId, name, price, qty, category, "Active", cost);
        productDao.insert(newP);
        printTrue("Product Created!");
    }

    private void restockProduct(Product p) {
        printHead("Restocking: " + p.getName() + " (Current: " + p.getQty() + ")");
        int addQty = readInt("Qty to add: ");

        p.setQty(Integer.valueOf(p.getQty() + addQty));
        productDao.insert(p);
        printTrue("Stock Updated!");
    }

    public void updateProduct() {
        printHead("UPDATE PRODUCT DETAILS");

        int id = readInt("Enter Product ID to Edit");
        Product p = productDao.findById(Integer.valueOf(id));

        if (p == null) {
           printErr("Product not found!");
            return;
        }

       printInfo("[Name: " + p.getName() + "] [Price: $" + p.getPrice() + "]");
        //skip if enter
       println("(Press ENTER to skip )");

        String newName = readText("New Name");
        if (!newName.isEmpty()) {
            p.setName(newName);
        }

        String newPriceStr = readText("New Price");
        if (!newPriceStr.isEmpty()) {
            try {
                double newPrice = Double.parseDouble(newPriceStr);
                p.setPrice(Double.valueOf(newPrice));
            } catch (NumberFormatException e) {
                printWarm("Invalid price format. Keeping old price.");
            }
        }

        String newCat = readText("New Category");
        if (!newCat.isEmpty()) {
            p.setCategory(newCat);
        }

        productDao.insert(p);
        printTrue("Product Details Updated!");
    }
    public void deleteProduct() {
        int id = readInt("Enter ID");
        Product p = productDao.findById(Integer.valueOf(id));
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
            productDao.deleteById(Integer.valueOf(id));
            printTrue("Product '" + p.getName() + "' has been deleted.");
        } else {
            printInfo("Operation cancelled.");
        }
    }
}