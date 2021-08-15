package com.example.rest_api;

import com.example.rest_api.controller.TerminalController;
import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.model.Terminal;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class BackendRestApiApplicationIT {

    @Autowired
    TerminalController controller;

    @Test
    void shouldCreateTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        assertThat(controller.createTerminal(terminalDTO))
                .satisfies(body -> {
                    assertThat(Objects.requireNonNull(body.getBody()).getSerial()).isEqualTo(terminalDTO.getSerial());
                    assertThat(body.getBody().getModel()).isEqualTo(terminalDTO.getModel());
                    assertThat(body.getBody().getSam()).isEqualTo(terminalDTO.getSam());
                });
    }

    @Test
    void shouldGetTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        ResponseEntity<Terminal> createdTerminal = controller.createTerminal(terminalDTO);

        assertThat(controller.getTerminal(createdTerminal.getBody().getId()))
                .satisfies(body -> {
                    assertThat(body.getBody().getSerial()).isEqualTo(terminalDTO.getSerial());
                    assertThat(body.getBody().getModel()).isEqualTo(terminalDTO.getModel());
                    assertThat(body.getBody().getSam()).isEqualTo(terminalDTO.getSam());
                });
    }

    @Test
    void shouldUpdateTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        ResponseEntity<Terminal> createdTerminal = controller.createTerminal(terminalDTO);

        TerminalDTO update = TerminalDTO.builder()
                .serial("124")
                .model(createdTerminal.getBody().getModel())
                .sam(1)
                .version(createdTerminal.getBody().getVersion())
                .build();

        assertThat(controller.updateTerminal(createdTerminal.getBody().getId(), update))
                .satisfies(body -> {
                    assertThat(body.getBody().getId()).isEqualTo(createdTerminal.getBody().getId());
                    assertThat(body.getBody().getSerial()).isEqualTo(update.getSerial());
                    assertThat(body.getBody().getModel()).isEqualTo(update.getModel());
                    assertThat(body.getBody().getSam()).isEqualTo(update.getSam());
                });
    }

    @Test
    void shouldDeleteTerminal() throws Exception {
        TerminalDTO terminalDTO = TerminalDTO.builder()
                .serial("123")
                .model("PWWIN")
                .sam(0)
                .version("8.00b3")
                .build();

        ResponseEntity<Terminal> terminal = controller.createTerminal(terminalDTO);

        assertThat(controller.deleteTerminal(terminal.getBody().getId()))
                .satisfies(body -> {
                    assertThat(body.getBody()).isEqualTo("Terminal deleted successfully!");
                    assertThat(body.getStatusCode()).isEqualTo(HttpStatus.OK);
                });
    }

}
