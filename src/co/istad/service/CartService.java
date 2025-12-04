package co.istad.service;

import co.istad.entity.Product;

import java.util.List;

public interface CartService {
    public void addItemToCart(Integer productId, Integer quantity);
    public double checkout();
    public void clearCart();
    public List<Product> getCartItems();
}
