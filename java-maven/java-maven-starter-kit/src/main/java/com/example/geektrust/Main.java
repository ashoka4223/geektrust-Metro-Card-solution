package com.example.geektrust;
import java.util.List;
import com.example.geektrust.Exception.ProcessingException;
import com.example.geektrust.Model.InputCommands;
import com.example.geektrust.Service.CardExecutionService;
import com.example.geektrust.Service.CardExecutionServiceImpl;
import com.example.geektrust.Utility.FileProcessingUtility;

/**
 * @author Ashok Kumar
 * @date 24/05/2024
 */

public class Main {
    public static void main(String[] args) {
        try {
            if (args.length != 1) {
                throw new ProcessingException("Input file not supplied. Please provide the input file");
            }
            String filePath = args[0];
            processMetroCard(filePath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public static String processMetroCard(String filePath) {
        FileProcessingUtility reader = new FileProcessingUtility(filePath);
        List<InputCommands> commands = reader.getCommandsFromFile();
        CardExecutionService cardService=new CardExecutionServiceImpl();
        String output=cardService.executeCommands(commands);
        return output;
    }
}