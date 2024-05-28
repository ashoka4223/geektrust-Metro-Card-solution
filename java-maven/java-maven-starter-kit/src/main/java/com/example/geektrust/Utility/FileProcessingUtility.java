package com.example.geektrust.Utility;

import com.example.geektrust.Exception.ProcessingException;
import com.example.geektrust.Model.InputCommands;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@NoArgsConstructor
public class FileProcessingUtility {

    private String filePath;
    public List<InputCommands> getCommandsFromFile() throws ProcessingException {
        try (Stream<String> lines = Files.lines(Paths.get(this.filePath))) {
            return lines.filter(line -> !isEmptyOrComment(line)).map(line -> parseInput(line))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new ProcessingException("Some error occurred while processing input file");
        }
    }

    // Check whether command is empty ot comment
    private boolean isEmptyOrComment(String line){
        return line.trim().isEmpty() || line.trim().startsWith("#");
    }

    public InputCommands parseInput(String str){
       try{
           String[] commandsWithArguments= str.split(" ");
           List<String> tokens = Arrays.stream(commandsWithArguments).skip(1).collect(Collectors.toList());
           InputCommands input = new InputCommands(commandsWithArguments[0],tokens);
           return input;
       }  catch (Exception e){
           throw new UnsupportedOperationException("Invalid Command Supplied: " + str);
       }
    }
}
