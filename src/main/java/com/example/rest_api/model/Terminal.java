package com.example.rest_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Data
@Entity
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logic", unique = true, nullable = false)
    private int logic;

    @Column(name = "serial", nullable = false)
    private String serial;

    @Column(name = "model", nullable = false)
    private String model;

    private int sam;

    private String ptid;

    private int plat;

    @Column(name = "version", nullable = false)
    private String version;

    private int mxr;

    private int mxf;

    private String verfm;

}