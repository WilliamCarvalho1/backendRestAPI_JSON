package com.example.rest_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TerminalDTO {

    private int id;

    private String serial;

    private String model;

    private int sam;

    private String version;

}