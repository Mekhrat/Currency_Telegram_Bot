package com.example.firstjavaspring.db;


import java.util.ArrayList;

public class DBManager {
    public static ArrayList<Items> items = new ArrayList<>();

    static {
        items.add(new Items(1,"Samsung A72",170000));
        items.add(new Items(2,"Xiaomi Redmi Note 9",120000));
        items.add(new Items(3,"Iphone 13 Pro",520000));
        items.add(new Items(4,"Samsung A52",150000));
    }
    public static int id = 5;
    public static Items getItem(int id){
        for (Items i : items){
            if (i.getId() == id){
                return i;
            }
        }
        return null;
    }

    public static ArrayList<Items> getItems(){
        return items;
    }

    public static void addItem(Items item){
        item.setId(id);
        items.add(item);
        id++;
    }


    }
