package co.istad.dao;
import co.istad.entity.Product;
import java.util.List;

public interface ProductDao {
    static List<Product> selectAll() {
        return null;
    }

    void insert(Product product);
    void deleteById(Integer id);
    List<Product> selectByName(String name);
    Product findById(Integer id);
    List<Product> search(String keyword);
}