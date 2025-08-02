package com.Nong.treading.modal;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Watchlist {
     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

     @OneToOne
        private User user; // User who owns the watchlist

     @ManyToMany
        private List<Coin> coins = new ArrayList<>(); // List of coins in the watchlist

}
