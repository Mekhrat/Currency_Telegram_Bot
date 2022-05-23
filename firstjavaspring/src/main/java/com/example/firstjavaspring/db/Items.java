package com.example.firstjavaspring.db;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    private int id;
    private String name;
    private int price;
}
