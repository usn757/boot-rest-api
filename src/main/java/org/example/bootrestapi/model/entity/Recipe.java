package org.example.bootrestapi.model.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Recipe {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "user_seq")
//    @TableGenerator(
//            name = "user_seq",
//            table = "sequence_table",
//            pkColumnName = "sequence_name",
//            valueColumnName = "next_value",
//            initialValue = 1,
//            allocationSize = 1
//    )
    private Long id;
    String name;
    String description;
}
