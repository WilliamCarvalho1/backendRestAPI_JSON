package com.example.rest_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Data
@Entity
public class Terminal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    @Basic(optional = false)
    @Column(name = "logic", unique = true, nullable = false)
    private int logic;

    @Column(name = "serial", nullable = false)
    private String serial;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "sam")
    private int sam;

    @Column(name = "ptid")
    private String ptid;

    @Column(name = "plat")
    private int plat;

    @Column(name = "version", nullable = false)
    private String version;

    @Column(name = "mxr")
    private int mxr;

    @Column(name = "mxf")
    private int mxf;

    @Column(name = "verfm")
    private String verfm;

}