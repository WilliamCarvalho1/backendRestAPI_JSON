package com.example.rest_api.controller;

import com.example.rest_api.dto.TerminalDTO;
import com.example.rest_api.model.Terminal;
import com.example.rest_api.service.TerminalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/terminal")
public class TerminalController {

   @Autowired
   private TerminalService terminalService;

    @PostMapping
    public ResponseEntity<Terminal> createTerminal(@RequestBody TerminalDTO terminalDTO) throws Exception {
            return terminalService.createTerminal(terminalDTO);
        }

    @GetMapping("/{logic}")
    public ResponseEntity<Terminal> getTerminal (@PathVariable("logic") int logic) throws Exception {
        return terminalService.getTerminal(logic);
    }

    @PutMapping("/{logic}")
    public ResponseEntity<Terminal> updateTerminal (@PathVariable("logic") int logic, @RequestBody TerminalDTO updateDTO) throws Exception {
        return terminalService.updateTerminal(logic, updateDTO);
    }

}
