package hu.petrik.webshopbackend.items;

import hu.petrik.webshopbackend.user.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @GetMapping()
    public List<Item> getItems() {
        return itemService.findAll();
    }

    @PostMapping()
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @GetMapping("{id}")
    public Item findById(@PathVariable long id) {
        return itemService.findById(id);

    }

}
