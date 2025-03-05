package hu.petrik.webshopbackend.items;

import hu.petrik.webshopbackend.cart_items.CartItem;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Entity
@Table(name = "Items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1024)
    private String description;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true,length = 100)
    @Lob
    @Nullable
    private Byte[] image;

    //mappedBy = "item"
    /*
    @OneToOne()
    private CartItem cart;

     */


    public Item(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
