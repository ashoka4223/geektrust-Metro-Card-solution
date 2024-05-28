package com.example.geektrust.Model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class InputCommands {

    private String command;
    private List<String> token;

    @Override
    public boolean equals(Object obj) {
        if(this==obj) {
            return true;
        }
        if(obj==null || this.getClass()!=obj.getClass()) {
            return false;
        }
        InputCommands inputCommand=(InputCommands)obj;;
        return this.command.equals(inputCommand.command)
                && this.token.equals(inputCommand.token);
    }
}
