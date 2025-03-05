package hu.petrik.webshopbackend.cart_items;

import hu.petrik.webshopbackend.Exceptions.CartItemNotFoundException;
import hu.petrik.webshopbackend.items.Item;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {
    private final CartItemRepository cartItemRepository;
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }

    public CartItem createCartItem(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    public CartItem findById(long id) {
        return cartItemRepository.findById(id).orElseThrow(()-> new CartItemNotFoundException("Cart Item Not Found"));
    }
}
