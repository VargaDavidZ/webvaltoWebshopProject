package hu.petrik.webshopbackend.cart_items;

import hu.petrik.webshopbackend.items.Item;
import hu.petrik.webshopbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Cart_Items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "user_id",nullable = false,referencedColumnName = "id")
    private User user;

    @Column(nullable = false)
    private int quantity;


    @JoinColumn(name = "item_id",nullable = false,referencedColumnName = "id")
    @OneToOne()
    private Item item;

    public CartItem(User user, int quantity, Item item) {
        this.user = user;
        this.quantity = quantity;
        this.item = item;
    }
}
