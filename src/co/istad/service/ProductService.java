package co.istad.service;

import co.istad.dao.ProductDao;
import co.istad.dao.impl.ProductFileDao;
import co.istad.entity.Product;
import co.istad.utils.InputUtils;
import co.istad.view.TableUtils;

import java.util.List;

public class ProductService {

    private final ProductDao productDao = new ProductFileDao();

    public void smartAddProduct() {
        System.out.println("--- STOCK MANAGEMENT ---");
        List<Product> allProducts = productDao.selectAll();
        TableUtils.renderProducts(allProducts);

        String inputId = InputUtils.readText(">> Enter Product ID (or press ENTER to create NEW): ");

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
                System.out.println("System Error : "+e.getMessage());
            }
        }

        if (foundProduct != null) {
            restockProduct(foundProduct);

        } else if (isId) {
            System.out.println("[!] Product ID [" + inputId + "] not found.");

            boolean create = InputUtils.confirm("Do you want to create a new product?");

            if (create) {
                createNewProduct(allProducts);
            } else {
                System.out.println("🔙 returning to menu...");
            }

        } else {
            createNewProduct(allProducts);
        }
    }

    private void createNewProduct(List<Product> allProducts) {
        int nextId = 1001;
        if (!allProducts.isEmpty()) {
            int maxId = allProducts.stream().mapToInt(Product::getId).max().orElse(1000);
            nextId = maxId + 1;
        }

        System.out.println("[NEW] Creating New Product (Auto-ID: " + nextId + ")");

        String name = InputUtils.readValidText("Name: ");
        double price = InputUtils.readDouble("Price: ");
        int qty = InputUtils.readInt("Qty: ");
        String category = InputUtils.readValidText("Category: ");

        Product newP = new Product(nextId,name, price, qty, category, "Active");
        productDao.insert(newP);
        System.out.println("*** Product Created Successfully!");
    }

    private void restockProduct(Product p) {
        System.out.println("[*] Restocking: " + p.getName() + " (Current: " + p.getQty() + ")");
        int addQty = InputUtils.readInt("➕ Qty to add: ");

        p.setQty(p.getQty() + addQty);
        productDao.insert(p);
        System.out.println("[OK] Stock Updated!");
    }
}