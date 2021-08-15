package com.example.rest_api.service;

import com.example.rest_api.controller.TerminalController;
import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.exception.BadRequestException;
import com.example.rest_api.exception.CustomNoContentException;
import com.example.rest_api.exception.CustomNotFoundException;
import com.example.rest_api.exception.JsonValidationException;
import com.example.rest_api.model.Terminal;
import com.example.rest_api.repository.TerminalRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class TerminalService {

    @Autowired
    private TerminalRepository repository;

    public List<Terminal> getAll() throws CustomNoContentException {
        List<Terminal> terminalList = repository.findAll();

        if (terminalList.isEmpty()) {
            throw new CustomNoContentException();
        }

        return terminalList;
    }

    public Terminal getTerminal(int id) throws BadRequestException, CustomNotFoundException {
        return findTerminal(id);
    }

    public Terminal createTerminal(TerminalDTO terminalDTO) throws JsonValidationException, JsonProcessingException {
        validateJson(terminalDTO);

        Terminal terminal = new Terminal();
        terminal.setSerial(terminalDTO.getSerial());
        terminal.setModel(terminalDTO.getModel());
        terminal.setSam(terminalDTO.getSam());
        terminal.setVersion(terminalDTO.getVersion());

        return repository.save(terminal);
    }

    public Terminal updateTerminal(int id, TerminalDTO updateDTO) throws BadRequestException, CustomNotFoundException {

        Terminal terminal = getTerminal(id);

        if (updateDTO.getSerial() != null && !updateDTO.getSerial().equals(terminal.getSerial()))
            terminal.setSerial(updateDTO.getSerial());
        if (updateDTO.getModel() != null && !updateDTO.getModel().equals(terminal.getModel()))
            terminal.setModel(updateDTO.getModel());
        if (updateDTO.getSam() >= 0 && updateDTO.getSam() != (terminal.getSam()))
            terminal.setSam(updateDTO.getSam());
        if (updateDTO.getVersion() != null && !updateDTO.getVersion().equals(terminal.getVersion()))
            terminal.setVersion(updateDTO.getVersion());

        return repository.save(Objects.requireNonNull(terminal));
    }

    @Transactional
    public String deleteTerminal(int id) throws CustomNotFoundException, BadRequestException {
        Terminal terminal = findTerminal(id);

        repository.deleteTerminalById(terminal.getId());

        return "Terminal deleted successfully!";
    }

    public Terminal findTerminal(int id) throws BadRequestException, CustomNotFoundException {
        if (id < 1) {
            throw new BadRequestException();
        }

        Terminal terminal = repository.findTerminal(id);

        if (terminal == null) {
            throw new CustomNotFoundException();
        }

        return terminal;
    }

    public void validateJson(TerminalDTO terminalDTO) throws JsonValidationException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String terminalJson = "";
        try {
            terminalJson = mapper.writeValueAsString(terminalDTO);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("Json String request: " + terminalJson);
        InputStream schemaAsStream = TerminalController.class.getClassLoader().getResourceAsStream("model/terminal-schema.json");
        JsonSchema schema = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7).getSchema(schemaAsStream);

        ObjectMapper om = new ObjectMapper();
        om.setPropertyNamingStrategy(PropertyNamingStrategies.KEBAB_CASE);
        JsonNode jsonNode = om.readTree(terminalJson);

        Set<ValidationMessage> errors = schema.validate(jsonNode);

        StringBuilder bld = new StringBuilder();
        errors.forEach(error -> {
            log.error("Validation Error: {}", error);
            bld.append(error.toString()).append("\n");
        });
        String errorsCombined = bld.toString();

        if (!errors.isEmpty())
            throw new JsonValidationException(errorsCombined);
    }

}
