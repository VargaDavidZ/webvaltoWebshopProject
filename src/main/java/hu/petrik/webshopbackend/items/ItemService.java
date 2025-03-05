package hu.petrik.webshopbackend.items;

import hu.petrik.webshopbackend.Exceptions.ItemNotFoundException;
import hu.petrik.webshopbackend.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }

    public Item findById(long id) {
        return itemRepository.findById(id).orElseThrow(() ->new ItemNotFoundException("Item not found"));
    }
}
