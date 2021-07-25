package com.example.rest_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TerminalDTO {

    private int logic;

    private String serial;

    private String model;

    private int sam;

    private String ptid;

    private int plat;

    private String version;

    private int mxr;

    private int mxf;

    private String verfm;

}