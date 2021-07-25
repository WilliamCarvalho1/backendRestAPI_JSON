package com.example.rest_api;

import com.example.rest_api.controller.TerminalController;
import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.model.Terminal;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
class BackendRestApiApplicationIT {

	@Autowired
	TerminalController controller;

	@Test
	void shouldCreateTerminal() throws Exception {
		Terminal terminal = new Terminal();
		terminal.setLogic(1);
		terminal.setSerial("123");
		terminal.setModel("PWWIN");
		terminal.setSam(0);
		terminal.setPtid("F04A2E4088B");
		terminal.setPlat(4);
		terminal.setVersion("8.00b3");
		terminal.setMxr(0);
		terminal.setMxf(16777216);
		terminal.setVerfm("PWWIN");

		assertThat(controller.createTerminal(terminal))
				.satisfies(body -> {
					assertThat(body.getSerial()).isEqualTo(terminal.getSerial());
					assertThat(body.getModel()).isEqualTo(terminal.getModel());
					assertThat(body.getSam()).isEqualTo(terminal.getSam());
					assertThat(body.getVerfm()).isEqualTo(terminal.getVerfm());
				});
	}

	@Test
	void shouldGetTerminal() throws JsonProcessingException {
		Terminal terminal = new Terminal();
		terminal.setLogic(1);
		terminal.setSerial("123");
		terminal.setModel("PWWIN");
		terminal.setSam(0);
		terminal.setPtid("F04A2E4088B");
		terminal.setPlat(4);
		terminal.setVersion("8.00b3");
		terminal.setMxr(0);
		terminal.setMxf(16777216);
		terminal.setVerfm("PWWIN");

		Terminal createdTerminal = controller.createTerminal(terminal);

		assertThat(controller.getTerminal(createdTerminal.getLogic()))
				.satisfies(body -> {
					assertThat(body.getBody().getSerial()).isEqualTo(terminal.getSerial());
					assertThat(body.getBody().getModel()).isEqualTo(terminal.getModel());
					assertThat(body.getBody().getSam()).isEqualTo(terminal.getSam());
					assertThat(body.getBody().getVerfm()).isEqualTo(terminal.getVerfm());
				});
	}

	@Test
	void shouldUpdateTerminal() throws JsonProcessingException {
		Terminal terminal = new Terminal();
		terminal.setLogic(1);
		terminal.setSerial("123");
		terminal.setModel("PWWIN");
		terminal.setSam(0);
		terminal.setPtid("F04A2E4088B");
		terminal.setPlat(4);
		terminal.setVersion("8.00b3");
		terminal.setMxr(0);
		terminal.setMxf(16777216);
		terminal.setVerfm("PWWIN");

		Terminal createdTerminal = controller.createTerminal(terminal);

		TerminalDTO update = TerminalDTO.builder()
				.serial("124")
				.model(terminal.getModel())
				.sam(1)
				.ptid(terminal.getPtid())
				.plat(terminal.getPlat())
				.version(terminal.getVersion())
				.mxr(terminal.getMxr())
				.mxf(terminal.getMxf())
				.verfm("ZETG")
				.build();

		assertThat(controller.updateTerminal(createdTerminal.getLogic(), update))
				.satisfies(body -> {
					assertThat(body.getBody().getLogic()).isEqualTo(createdTerminal.getLogic());
					assertThat(body.getBody().getSerial()).isEqualTo(update.getSerial());
					assertThat(body.getBody().getModel()).isEqualTo(update.getModel());
					assertThat(body.getBody().getSam()).isEqualTo(update.getSam());
					assertThat(body.getBody().getVerfm()).isEqualTo(update.getVerfm());
				});
	}

}
