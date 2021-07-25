package com.example.rest_api.service;

import com.example.rest_api.controller.TerminalController;
import com.example.rest_api.dto.TerminalDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Set;

@Slf4j
@Service
public class TerminalService {

    @Autowired
    private TerminalRepository repository;

    public ResponseEntity<Terminal> getTerminal(int logic){
        if(logic < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Terminal terminal = findTerminal(logic);

        if(terminal == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(terminal, HttpStatus.OK);
    }

    public ResponseEntity<Terminal> updateTerminal(int logic, TerminalDTO updateDTO){

        if(logic < 1){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ResponseEntity<Terminal> terminal = getTerminal(logic);

        if(terminal.getBody() == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(updateDTO.getSerial() != null && !updateDTO.getSerial().equals(terminal.getBody().getSerial()))
            terminal.getBody().setSerial(updateDTO.getSerial());
        if(updateDTO.getModel() != null && !updateDTO.getModel().equals(terminal.getBody().getModel()))
            terminal.getBody().setModel(updateDTO.getModel());
        if(updateDTO.getSam() != 0 && updateDTO.getSam() != (terminal.getBody().getSam()))
            terminal.getBody().setSam(updateDTO.getSam());
        if(updateDTO.getPtid() != null && !updateDTO.getPtid().equals(terminal.getBody().getPtid()))
            terminal.getBody().setPtid(updateDTO.getPtid());
        if(updateDTO.getPlat() != 0 && updateDTO.getPlat() != terminal.getBody().getPlat())
            terminal.getBody().setPlat(updateDTO.getPlat());
        if(updateDTO.getVersion() != null && !updateDTO.getVersion().equals(terminal.getBody().getVersion()))
            terminal.getBody().setVersion(updateDTO.getVersion());
        if(updateDTO.getMxr() != 0 && updateDTO.getMxr() != terminal.getBody().getMxr())
            terminal.getBody().setMxr(updateDTO.getMxr());
        if(updateDTO.getMxf() != 0 && updateDTO.getMxf() != terminal.getBody().getMxf())
            terminal.getBody().setMxf(updateDTO.getMxf());
        if(updateDTO.getVerfm() != null && !updateDTO.getVerfm().equals(terminal.getBody().getVerfm()))
            terminal.getBody().setVerfm(updateDTO.getVerfm());

        return new ResponseEntity<>(repository.save(terminal.getBody()), HttpStatus.OK);
    }

    public Terminal createTerminal(String terminal) throws JsonProcessingException {
        Terminal newTerminal = mapStringToTerminal(terminal);

        validateJson(newTerminal);

        return repository.save(newTerminal);
    }

    public Terminal findTerminal(int logic) {
        return repository.findTerminal(logic);
    }

    public static Terminal mapStringToTerminal(String terminal){
        String[] myArr = terminal.split("body ", 10);

        String body = myArr[1];
        String[] bodyArr = body.split(";", 10);

        Terminal newTerminal = new Terminal();
        newTerminal.setLogic(Integer.parseInt(bodyArr[0]));
        newTerminal.setSerial(bodyArr[1]);
        newTerminal.setModel(bodyArr[2]);
        newTerminal.setSam(Integer.parseInt(bodyArr[3]));
        newTerminal.setPtid(bodyArr[4]);
        newTerminal.setPlat(Integer.parseInt(bodyArr[5]));
        newTerminal.setVersion(bodyArr[6]);
        newTerminal.setMxr(Integer.parseInt(bodyArr[7]));
        newTerminal.setMxf(Integer.parseInt(bodyArr[8]));
        newTerminal.setVerfm(bodyArr[9]);

        return newTerminal;
    }

    public void validateJson(Terminal terminal) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String terminalJson = "";
        try {
            terminalJson = mapper.writeValueAsString(terminal);
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
        for (ValidationMessage error : errors) {
            log.error("Validation Error: {}", error);
            bld.append(error.toString()).append("\n");
        }
        String errorsCombined = bld.toString();

        if (!errors.isEmpty())
            throw new RuntimeException("Please fix your json! " + errorsCombined);
    }

}
