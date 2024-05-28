package com.example.geektrust.Service;

import com.example.geektrust.Model.InputCommands;
import com.example.geektrust.Model.Passenger;

import java.util.List;

public interface CardExecutionService {

    //executes given set of commands from input file
    public String executeCommands(List<InputCommands> arguments);

    //BALANCE command execution
    public Passenger initializeBalance(List<String> tokens);

    //CHECK_IN command execution
    public void processCheckIn(List<String> tokens);

    //PRINT_SUMMARY command execution
    public String printSummary();
}
