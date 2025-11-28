package co.istad.dao.impl;

import co.istad.dao.ProductDao;
import co.istad.entity.Product;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static co.istad.utils.PrintUtils.*;

public class ProductFileDao implements ProductDao {

    private static final String FILE_PATH = "data/products.csv";
    //read file
    @Override
    public List<Product> selectAll() {
        List<Product> products = new ArrayList<>();
        File file = new File(FILE_PATH);

        if (!file.exists()) return products;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; } // Skip Header

                String[] parts = line.split(",");

                if (parts.length >= 7) {
                    Product p = new Product();
                    p.setId(Integer.parseInt(parts[0].trim()));
                    p.setName(parts[1].trim());
                    p.setPrice(Double.parseDouble(parts[2].trim()));
                    p.setQty(Integer.parseInt(parts[3].trim()));
                    p.setCategory(parts[4].trim());
                    p.setStatus(parts[5].trim());
                    p.setCost(Double.parseDouble(parts[6].trim()));
                    products.add(p);
                }
            }
        } catch (IOException e) {
            printErr("Error reading data file: " + e.getMessage());
        }
        return products;
    }

    @Override
    public Product findById(Integer id) {
        return selectAll().stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> selectByName(String name) {
        return selectAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .filter(p -> !p.getStatus().equals("Deleted"))
                .collect(Collectors.toList());
    }

    //write

    @Override
    public void insert(Product product) {
        List<Product> allProducts = selectAll();
        boolean isFound = false;
        //update
        for (int i = 0; i < allProducts.size(); i++) {
            if (allProducts.get(i).getId().equals(product.getId())) {
                allProducts.set(i, product);
                isFound = true;
                break;
            }
        }

        //add new
        if (!isFound) {
            allProducts.add(product);
        }

        renew(allProducts);
    }

    @Override
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
            renew(allProducts);
        }
    }

    private void renew(List<Product> products) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("ID,Name,Price,Qty,Category,Status,Cost");
            writer.newLine();
            for (Product p : products) {
                String line = String.format("%d,%s,%.2f,%d,%s,%s,%.2f",
                        p.getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getQty(),
                        p.getCategory(),
                        p.getStatus(),
                        p.getCost()
                );
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            printErr("Error renewing data file: " + e.getMessage());
        }
    }
}