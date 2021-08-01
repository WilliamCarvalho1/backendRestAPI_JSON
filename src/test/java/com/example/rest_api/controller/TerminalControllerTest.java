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
                .ptid("F04A2E4088B")
                .plat(4)
                .version("8.00b3")
                .mxr(0)
                .mxf(16777216)
                .verfm("PWWIN")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setPtid(terminalDTO.getPtid());
        terminal.setPlat(terminalDTO.getPlat());
        terminal.setVersion(terminalDTO.getVersion());
        terminal.setMxr(terminalDTO.getMxr());
        terminal.setMxf(terminalDTO.getMxf());
        terminal.setVerfm(terminalDTO.getVerfm());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        Assertions.assertThat(service.createTerminal(terminalDTO))
                .satisfies(t -> assertEquals(terminalDTO.getLogic(), t.getBody().getLogic()));

        verify(service, atMostOnce()).createTerminal(terminalDTO);
    }

    @Test
    void ShouldUpdateTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .ptid("F04A2E4088B")
                .plat(4)
                .version("8.00b3")
                .mxr(0)
                .mxf(16777216)
                .verfm("PWWIN")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setPtid(terminalDTO.getPtid());
        terminal.setPlat(terminalDTO.getPlat());
        terminal.setVersion(terminalDTO.getVersion());
        terminal.setMxr(terminalDTO.getMxr());
        terminal.setMxf(terminalDTO.getMxf());
        terminal.setVerfm(terminalDTO.getVerfm());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        ResponseEntity<Terminal> createdTerminal = service.createTerminal(terminalDTO);

        TerminalDTO updateDTO = TerminalDTO.builder()
                .model("PWWIZ")
                .sam(1)
                .verfm("PWWIZ")
                .build();

        Terminal updateTerminal = new Terminal();
        updateTerminal.setSerial(createdTerminal.getBody().getSerial());
        updateTerminal.setModel(updateDTO.getModel());
        updateTerminal.setSam(updateDTO.getSam());
        updateTerminal.setPtid(createdTerminal.getBody().getPtid());
        updateTerminal.setPlat(createdTerminal.getBody().getPlat());
        updateTerminal.setVersion(createdTerminal.getBody().getVersion());
        updateTerminal.setMxr(createdTerminal.getBody().getMxr());
        updateTerminal.setMxf(createdTerminal.getBody().getMxf());
        updateTerminal.setVerfm(updateDTO.getVerfm());

        when(service.updateTerminal(createdTerminal.getBody().getLogic(), updateDTO)).thenReturn(new ResponseEntity<>(updateTerminal, HttpStatus.OK));

        Assertions.assertThat(service.updateTerminal(createdTerminal.getBody().getLogic(), updateDTO))
                .satisfies(t -> {
                    assertEquals(createdTerminal.getBody().getLogic(), t.getBody().getLogic());
                    assertEquals(updateDTO.getModel(), t.getBody().getModel());
                    assertEquals(updateDTO.getSam(), t.getBody().getSam());
                    assertEquals(updateDTO.getVerfm(), t.getBody().getVerfm());
                });

        verify(service, atMostOnce()).updateTerminal(Objects.requireNonNull(createdTerminal.getBody()).getLogic(), terminalDTO);
    }

    @Test
    void updateTerminalWithNonexistentLogicShouldFail() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .ptid("F04A2E4088B")
                .plat(4)
                .version("8.00b3")
                .mxr(0)
                .mxf(16777216)
                .verfm("PWWIN")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setPtid(terminalDTO.getPtid());
        terminal.setPlat(terminalDTO.getPlat());
        terminal.setVersion(terminalDTO.getVersion());
        terminal.setMxr(terminalDTO.getMxr());
        terminal.setMxf(terminalDTO.getMxf());
        terminal.setVerfm(terminalDTO.getVerfm());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        ResponseEntity<Terminal> createdTerminal = service.createTerminal(terminalDTO);

        TerminalDTO updateDTO = TerminalDTO.builder()
                .model("PWWIZ")
                .sam(1)
                .verfm("PWWIZ")
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
                .ptid("F04A2E4088B")
                .plat(4)
                .version("8.00b3")
                .mxr(0)
                .mxf(16777216)
                .verfm("PWWIN")
                .build();

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setPtid(terminalDTO.getPtid());
        terminal.setPlat(terminalDTO.getPlat());
        terminal.setVersion(terminalDTO.getVersion());
        terminal.setMxr(terminalDTO.getMxr());
        terminal.setMxf(terminalDTO.getMxf());
        terminal.setVerfm(terminalDTO.getVerfm());

        when(service.createTerminal(terminalDTO)).thenReturn(new ResponseEntity<>(terminal, HttpStatus.CREATED));

        ResponseEntity<Terminal> createdTerminal = service.createTerminal(terminalDTO);

        when(service.deleteTerminal(Objects.requireNonNull(createdTerminal.getBody()).getLogic())).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        Assertions.assertThat(service.deleteTerminal(createdTerminal.getBody().getLogic()))
                .satisfies(t -> assertEquals(HttpStatus.OK, t.getStatusCode()));

        verify(service, atMostOnce()).createTerminal(terminalDTO);
    }

    @Test
    void ShouldGetTerminal() throws Exception {
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
    void ShouldNotGetTerminalWithNonExistingLogic() throws Exception {
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
    void ShouldNotGetTerminalWithBadRequest() throws Exception {
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