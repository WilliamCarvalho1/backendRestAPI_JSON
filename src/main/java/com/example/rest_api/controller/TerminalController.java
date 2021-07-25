package com.example.rest_api.controller;

import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.model.Terminal;
import com.example.rest_api.service.TerminalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/terminal")
public class TerminalController {

   @Autowired
   private TerminalService terminalService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Terminal createTerminal(@RequestBody String terminal) throws JsonProcessingException {
            return terminalService.createTerminal(terminal);
        }

    @GetMapping("/{logic}")
    public ResponseEntity<Terminal> getTerminal (@PathVariable("logic") int logic){
        return terminalService.getTerminal(logic);
    }

    @PutMapping("/{logic}")
    public ResponseEntity<Terminal> updateTerminal (@PathVariable("logic") int logic, @RequestBody TerminalDTO updateDTO){
        return terminalService.updateTerminal(logic, updateDTO);
    }

}
