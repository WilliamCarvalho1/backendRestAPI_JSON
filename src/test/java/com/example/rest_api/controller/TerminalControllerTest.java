package com.example.rest_api.controller;

import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.model.Terminal;
import com.example.rest_api.service.TerminalService;
import io.restassured.http.ContentType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void ShouldCreateTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setVersion(terminalDTO.getVersion());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        Assertions.assertThat(service.createTerminal(terminalDTO))
                .satisfies(t -> assertEquals(terminalDTO.getId(), t.getBody().getId()));

        verify(service, atMostOnce()).createTerminal(terminalDTO);
    }

    @Test
    void ShouldUpdateTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setVersion(terminalDTO.getVersion());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        ResponseEntity<Terminal> createdTerminal = service.createTerminal(terminalDTO);

        TerminalDTO updateDTO = TerminalDTO.builder()
                .model("PWWIZ")
                .sam(1)
                .build();

        Terminal updateTerminal = new Terminal();
        updateTerminal.setSerial(createdTerminal.getBody().getSerial());
        updateTerminal.setModel(updateDTO.getModel());
        updateTerminal.setSam(updateDTO.getSam());
        updateTerminal.setVersion(createdTerminal.getBody().getVersion());

        when(service.updateTerminal(createdTerminal.getBody().getId(), updateDTO)).thenReturn(new ResponseEntity<>(updateTerminal, HttpStatus.OK));

        Assertions.assertThat(service.updateTerminal(createdTerminal.getBody().getId(), updateDTO))
                .satisfies(t -> {
                    assertEquals(createdTerminal.getBody().getId(), t.getBody().getId());
                    assertEquals(updateDTO.getModel(), t.getBody().getModel());
                    assertEquals(updateDTO.getSam(), t.getBody().getSam());
                });

        verify(service, atMostOnce()).updateTerminal(Objects.requireNonNull(createdTerminal.getBody()).getId(), terminalDTO);
    }

    @Test
    void updateTerminalWithNonexistentIdShouldFail() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setVersion(terminalDTO.getVersion());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        ResponseEntity<Terminal> createdTerminal = service.createTerminal(terminalDTO);

        TerminalDTO updateDTO = TerminalDTO.builder()
                .model("PWWIZ")
                .sam(1)
                .build();

        when(service.updateTerminal(2, updateDTO)).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));

        Assertions.assertThat(service.updateTerminal(2, updateDTO))
                .satisfies(t -> assertEquals(HttpStatus.NOT_FOUND, t.getStatusCode()));

        verify(service, atMostOnce()).updateTerminal(2, updateDTO);
    }

    @Test
    void shouldDeleteTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setVersion(terminalDTO.getVersion());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        ResponseEntity<Terminal> createdTerminal = service.createTerminal(terminalDTO);

        when(service.deleteTerminal(Objects.requireNonNull(createdTerminal.getBody()).getId())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        Assertions.assertThat(service.deleteTerminal(createdTerminal.getBody().getId()))
                .satisfies(t -> assertEquals(HttpStatus.OK, t.getStatusCode()));

        verify(service, atMostOnce()).createTerminal(terminalDTO);
    }

    @Test
    void ShouldGetTerminal() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.OK)).when(service).getTerminal(1);

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/v1/terminal/{id}", 1)
                .then()
                .statusCode(HttpStatus.OK.value());

        verify(service, atMostOnce()).getTerminal(1);
    }

    @Test
    void ShouldNotGetTerminalWithNonExistingId() throws Exception {
        doReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND)).when(service).getTerminal(2);

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/v1/terminal/{id}", 2)
        .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        verify(service, atMostOnce()).getTerminal(2);
    }

    @Test
    void ShouldNotGetTerminalWithBadRequest() throws Exception {
      doReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST)).when(service).getTerminal(-1);

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/v1/terminal/{id}", -1)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(service, atMostOnce()).getTerminal(-1);
    }

}