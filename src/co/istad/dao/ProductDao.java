package co.istad.dao;
import co.istad.entity.Product;
import java.util.List;

public interface ProductDao {
    List<Product> selectAll();
    void insert(Product product);
    void deleteById(Integer id);
}