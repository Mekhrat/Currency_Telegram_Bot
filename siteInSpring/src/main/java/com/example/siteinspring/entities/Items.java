package com.example.siteinspring.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "t_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description",length = 2000)
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "small_pic_url")
    private String smallPicURL;

    @Column(name = "large_pic_url")
    private String largePicURL;

    @Column(name = "added_date")
    private Date addedDate;

    @Column(name = "in_top_page")
    private Boolean inTopPage;

    @ManyToOne(fetch = FetchType.EAGER)
    private Brands brand;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Categories> categories;
}
