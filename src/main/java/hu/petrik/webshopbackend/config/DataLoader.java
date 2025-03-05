package hu.petrik.webshopbackend.config;

import hu.petrik.webshopbackend.cart_items.CartItem;
import hu.petrik.webshopbackend.cart_items.CartItemRepository;
import hu.petrik.webshopbackend.items.Item;
import hu.petrik.webshopbackend.items.ItemRepository;
import hu.petrik.webshopbackend.user.User;
import hu.petrik.webshopbackend.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private static PasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;
    private final CartItemRepository cartItemRepository;

    public DataLoader(UserRepository userRepository, PasswordEncoder passwordEncoder, ItemRepository itemRepository, CartItemRepository cartItemRepository) {
        this.userRepository = userRepository;
        DataLoader.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        cartItemRepository.deleteAll();
        userRepository.deleteAll();
        itemRepository.deleteAll();

        userRepository.resetSequence();
        itemRepository.resetSequence();
        cartItemRepository.resetSequence();


        userRepository.saveAll(createUser());
        User user = new User("admin","admin",passwordEncoder.encode("admin"),true);
        userRepository.save(user);

        itemRepository.save(createItem());

        User u = userRepository.findByEmail("email").get();
        Item item = itemRepository.findById(1L).get();
        cartItemRepository.save(new CartItem(u,10,item));

    }

    private static ArrayList<User> createUser() {

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
             users.add(User.builder()
                    .username("username" + i)
                    .email("email" + i)
                    .password(passwordEncoder.encode("asd" + i))
                    .admin(false)
                    //.authorities("ROLE_ADMIN")
                    .build());
        }

        return users;
    }

    private static Item createItem() {

        return new Item("name","desc",100);
    }


}
