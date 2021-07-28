package com.example.rest_api.controller;

import com.example.rest_api.service.TerminalService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.mockito.Mockito.*;

@WebMvcTest
class TerminalControllerTest {

    @Autowired
    private TerminalController controller;

    @MockBean
    private TerminalService service;

    @BeforeEach
    public void setup(){
        standaloneSetup(this.controller);
    }

    @Test
    void getTerminalFound() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.OK)).when(service).getTerminal(1);

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/v1/terminal/{logic}", 1)
        .then()
                .statusCode(HttpStatus.OK.value());

        verify(service, atMostOnce()).getTerminal(1);
    }

    @Test
    void getTerminalNotFound() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)).when(service).getTerminal(2);

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/v1/terminal/{logic}", 2)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        verify(service, atMostOnce()).getTerminal(2);
    }

    @Test
    void getTerminalBadRequest() throws Exception {
      doReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).when(service).getTerminal(-1);

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/v1/terminal/{logic}", -1)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(service, atMostOnce()).getTerminal(-1);
    }

}