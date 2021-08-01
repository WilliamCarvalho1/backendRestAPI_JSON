package com.example.rest_api.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
public class Terminal {

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int logic;

    @Column(nullable = false)
    private String serial;

    @Column(nullable = false)
    private String model;

    private int sam;

    private String ptid;

    private int plat;

    @Column(nullable = false)
    private String version;

    private int mxr;

    private int mxf;

    private String verfm;

}