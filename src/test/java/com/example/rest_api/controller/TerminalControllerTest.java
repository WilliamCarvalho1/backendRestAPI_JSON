package com.example.rest_api.controller;

import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.exception.BadRequestException;
import com.example.rest_api.exception.CustomNotFoundException;
import com.example.rest_api.model.Terminal;
import com.example.rest_api.service.TerminalService;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Objects;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.standaloneSetup;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@WebMvcTest
class TerminalControllerTest {

    @Autowired
    private TerminalController controller;

    @MockBean
    private TerminalService service;

    @BeforeEach
    public void setup() {
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

        when(service.createTerminal(terminalDTO)).thenReturn(terminal);

        assertThat(service.createTerminal(terminalDTO))
                .satisfies(t -> assertEquals(terminalDTO.getId(), t.getId()));

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

        when(service.createTerminal(terminalDTO)).thenReturn(terminal);

        Terminal createdTerminal = service.createTerminal(terminalDTO);

        TerminalDTO updateDTO = TerminalDTO.builder()
                .model("PWWIZ")
                .sam(1)
                .build();

        Terminal updatedTerminal = new Terminal();
        updatedTerminal.setSerial(createdTerminal.getSerial());
        updatedTerminal.setModel(updateDTO.getModel());
        updatedTerminal.setSam(updateDTO.getSam());
        updatedTerminal.setVersion(createdTerminal.getVersion());

        when(service.updateTerminal(createdTerminal.getId(), updateDTO)).thenReturn(updatedTerminal);

        assertThat(service.updateTerminal(createdTerminal.getId(), updateDTO))
                .satisfies(t -> {
                    assertEquals(createdTerminal.getId(), t.getId());
                    assertEquals(updateDTO.getModel(), t.getModel());
                    assertEquals(updateDTO.getSam(), t.getSam());
                });

        verify(service, atMostOnce()).updateTerminal(createdTerminal.getId(), terminalDTO);
    }

    @Test
    void shouldNotUpdateTerminalWithNonexistentId() throws Exception {
        TerminalDTO updateDTO = TerminalDTO.builder()
                .model("PWWIZ")
                .sam(1)
                .build();

        when(service.updateTerminal(2, updateDTO)).thenThrow(new CustomNotFoundException());

        assertThat(service.updateTerminal(2, updateDTO))
                .satisfies(t -> assertNull(t.getModel()));

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

        when(service.createTerminal(terminalDTO)).thenReturn(terminal);

        Terminal createdTerminal = service.createTerminal(terminalDTO);

        when(service.deleteTerminal(Objects.requireNonNull(createdTerminal).getId())).thenReturn("Terminal deleted successfully!");

        assertThat(service.deleteTerminal(createdTerminal.getId()))
                .satisfies(t -> assertEquals("Terminal deleted successfully!", t));

        verify(service, atMostOnce()).deleteTerminal(createdTerminal.getId());
    }

    @Test
    void ShouldGetTerminal() throws Exception {
        Terminal terminal = new Terminal();
        terminal.setId(1);
        terminal.setSerial("123");
        terminal.setModel("PWWIN");
        terminal.setSam(1);
        terminal.setVersion("8.00b3");

        doReturn(terminal).when(service).getTerminal(terminal.getId());

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/v1/terminal/{id}", terminal.getId())
        .then()
                .statusCode(HttpStatus.OK.value());

        verify(service, atMostOnce()).getTerminal(terminal.getId());
    }

    @Test
    void ShouldNotGetTerminalWithNonExistingId() throws Exception {
        doThrow(new CustomNotFoundException()).when(service).getTerminal(2);

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
        doThrow(new BadRequestException()).when(service).getTerminal(-1);

        given()
                .accept(ContentType.JSON)
        .when()
                .get("/v1/terminal/{id}", -1)
        .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

        verify(service, atMostOnce()).getTerminal(-1);
    }

}