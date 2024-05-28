package com.example.geektrust.Utility;

import com.example.geektrust.Exception.ProcessingException;
import com.example.geektrust.Model.InputCommands;

import java.util.List;

public class InputChecksImpl implements InputChecks {

    @Override
    public void commandChecks(InputCommands inputCommands) {
        String command  = inputCommands.getCommand();
        if(command.equals("BALANCE")){
            commandBalanceCheck(inputCommands.getToken());
        }
        else if (command.equals("CHECK_IN")) {
            commandCkeckInCheck(inputCommands.getToken());
        }
        else if (command.equals("PRINT_SUMMERY")) {
            commandPrintSummeryCheck(inputCommands.getToken());
        }
        else {
            throw new ProcessingException("Invalid commands, please check the input command");
        }
    }

    private void commandBalanceCheck(List<String> tokens) {
        if(tokens.size()!=2){
            throw new ProcessingException("Invalid number of argument, please valid input.");
        }
        Integer balance = Integer.parseInt(tokens.get(1));
        if(balance<0){
            throw new ProcessingException("MetroCard balance should be non negative, please validate input.");

        }
    }

    private void commandCkeckInCheck(List<String> tokens) {
        if (tokens.size() != 3) {
            throw new ProcessingException("Invalid number of argument, please valid input.");
        }
        String passengerType = tokens.get(1);
        if (!passengerType.equals("ADULT") && !passengerType.equals("SENIOR_CITIZEN") && !passengerType.equals("KID")) {
            throw new ProcessingException("Invalid Passenger Type, Please validate input.");
        }
        String fromStation=tokens.get(2);
        if(!fromStation.equals("AIRPORT") && !fromStation.equals("CENTRAL")) {
            throw new ProcessingException("Invalid Station, MetroCard Available Stations are : 1)AIRPORT  2)CENTRAL.");
        }
    }

    private void commandPrintSummeryCheck(List<String> tokens) {
        if(!tokens.isEmpty()){
            throw new ProcessingException("Invalid number of argument, please valid input.");
        }
    }
}
