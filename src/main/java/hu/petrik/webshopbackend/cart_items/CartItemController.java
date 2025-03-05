package hu.petrik.webshopbackend.cart_items;

import hu.petrik.webshopbackend.items.Item;
import hu.petrik.webshopbackend.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cartitems")
public class CartItemController {
    private final CartItemService cartItemService;
    public CartItemController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @GetMapping()
    public List<CartItem> getCartItems() {
        return cartItemService.findAll();
    }


    @PostMapping()
    public CartItem createItem(@RequestBody CartItem cartItem) {
        return cartItemService.createCartItem(cartItem);
    }

    @GetMapping("{id}")
    public CartItem findById(@PathVariable long id) {
        return cartItemService.findById(id);

    }


}
