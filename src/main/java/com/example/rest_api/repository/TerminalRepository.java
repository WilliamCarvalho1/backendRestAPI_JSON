package com.example.rest_api.repository;

import com.example.rest_api.model.Terminal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TerminalRepository extends JpaRepository<Terminal, String> {

    @Query("SELECT terminal FROM Terminal terminal " +
            "WHERE terminal.logic = ?1 ")
    Terminal findTerminal(int logic);


    @Modifying
    @Query("DELETE FROM Terminal terminal " +
            "WHERE terminal.logic = ?1 ")
    void deleteTerminalById(int logic);

}