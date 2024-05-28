package com.example.geektrust.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.example.geektrust.Utility.FileProcessingUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.geektrust.Model.InputCommands;

public class FileProcessingUtilityTest {

    private FileProcessingUtility fileUtility;

    @BeforeEach
    void setUp() {
        fileUtility=new FileProcessingUtility();
    }

    @Test
    public void parseInputTest() {
        List<String> tokens=new ArrayList<>();
        tokens.add("MC1");
        Integer balance=100;
        tokens.add(balance.toString());
        InputCommands expectedCommand=new InputCommands("BALANCE", tokens);

        InputCommands actualCommand=fileUtility.parseInput("BALANCE MC1 100");

        assertEquals(actualCommand, expectedCommand);
    }
}