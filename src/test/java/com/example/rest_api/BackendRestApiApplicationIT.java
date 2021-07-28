package com.example.rest_api;

import com.example.rest_api.controller.TerminalController;
import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.model.Terminal;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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
				.ptid("F04A2E4088B")
				.plat(4)
				.version("8.00b3")
				.mxr(0)
				.mxf(16777216)
				.verfm("PWWIN")
				.build();

		assertThat(controller.createTerminal(terminalDTO))
				.satisfies(body -> {
					assertThat(body.getBody().getSerial()).isEqualTo(terminalDTO.getSerial());
					assertThat(body.getBody().getModel()).isEqualTo(terminalDTO.getModel());
					assertThat(body.getBody().getSam()).isEqualTo(terminalDTO.getSam());
					assertThat(body.getBody().getVerfm()).isEqualTo(terminalDTO.getVerfm());
				});
	}

	@Test
	void shouldGetTerminal() throws Exception {
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

		ResponseEntity<Terminal> createdTerminal = controller.createTerminal(terminalDTO);

		assertThat(controller.getTerminal(createdTerminal.getBody().getLogic()))
				.satisfies(body -> {
					assertThat(body.getBody().getSerial()).isEqualTo(terminalDTO.getSerial());
					assertThat(body.getBody().getModel()).isEqualTo(terminalDTO.getModel());
					assertThat(body.getBody().getSam()).isEqualTo(terminalDTO.getSam());
					assertThat(body.getBody().getVerfm()).isEqualTo(terminalDTO.getVerfm());
				});
	}

	@Test
	void shouldUpdateTerminal() throws Exception {
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

		ResponseEntity<Terminal> createdTerminal = controller.createTerminal(terminalDTO);

		TerminalDTO update = TerminalDTO.builder()
				.serial("124")
				.model(createdTerminal.getBody().getModel())
				.sam(1)
				.ptid(createdTerminal.getBody().getPtid())
				.plat(createdTerminal.getBody().getPlat())
				.version(createdTerminal.getBody().getVersion())
				.mxr(createdTerminal.getBody().getMxr())
				.mxf(createdTerminal.getBody().getMxf())
				.verfm("ZETG")
				.build();

		assertThat(controller.updateTerminal(createdTerminal.getBody().getLogic(), update))
				.satisfies(body -> {
					assertThat(body.getBody().getLogic()).isEqualTo(createdTerminal.getBody().getLogic());
					assertThat(body.getBody().getSerial()).isEqualTo(update.getSerial());
					assertThat(body.getBody().getModel()).isEqualTo(update.getModel());
					assertThat(body.getBody().getSam()).isEqualTo(update.getSam());
					assertThat(body.getBody().getVerfm()).isEqualTo(update.getVerfm());
				});
	}

}
