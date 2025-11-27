package co.istad.dao.impl;

import co.istad.dao.ProductDao;
import co.istad.entity.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductFileDao implements ProductDao {

    private static final String FILE_PATH = "data/products.csv";

    @Override
    public List<Product> selectAll() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return products;
        // come : Java Reader
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; } // Skip Header

                String[] parts = line.split(",");

                if (parts.length >= 6) {
                    Product p = new Product();
                    p.setId(Integer.parseInt(parts[0].trim()));
                    p.setName(parts[1].trim());
                    p.setPrice(Double.parseDouble(parts[2].trim()));
                    p.setQty(Integer.parseInt(parts[3].trim()));
                    p.setCategory(parts[4].trim());
                    p.setStatus(parts[5].trim());
                    products.add(p);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading data file: " + e.getMessage());
        }
        return products;
    }

    @Override
    public void insert(Product product) {
        boolean isNewFile = !new File(FILE_PATH).exists();
        // come: Java Reader
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            if (isNewFile) {
                writer.write("ID,Name,Price,Qty,Category,Status");
                writer.newLine();
            }

            String line = String.format("%d,%s,%.2f,%d,%s,%s",
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getQty(),
                    product.getCategory(),
                    product.getStatus()
            );
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(System.out));
        }
    }

    public void deleteById(Integer id) {
        List<Product> allProducts = selectAll();
        boolean found = false;

        for (Product p : allProducts) {
            if (p.getId().equals(id)) {
                p.setStatus("Deleted");
                found = true;
                break;
            }
        }
        if (found) {
            overwrite(allProducts);
        }
    }

    private void overwrite(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("ID,Name,Price,Qty,Category,Status");
            writer.newLine();

            for (Product p : products) {
                String line = String.format("%d,%s,%.2f,%d,%s,%s",
                        p.getId(), p.getName(), p.getPrice(), p.getQty(), p.getCategory(), p.getStatus());
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(new PrintWriter(System.out));
        }
    }
}