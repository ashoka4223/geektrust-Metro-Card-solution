package com.example.geektrust.Service;

import com.example.geektrust.Exception.ProcessingException;
import com.example.geektrust.Model.*;
import com.example.geektrust.Utility.InputChecksImpl;

import java.util.*;
import java.util.stream.Collectors;

public class CardExecutionServiceImpl implements CardExecutionService {

    //MetroCard data
    private PassengerSummery passengerSummery = new PassengerSummery();
    @Override
    public String executeCommands(List<InputCommands> arguments) {

        String output = "";

        //validate the incoming commands
        for(InputCommands input:arguments){
            InputChecksImpl inputChecks = new InputChecksImpl();
            inputChecks.commandChecks(input);

            if(input.getCommand().equals("BALANCE")){
                initializeBalance(input.getToken());
            }
            else if (input.getCommand().equals("CHECK_IN")) {
                processCheckIn(input.getToken());
            }
            else if (input.getCommand().equals("PRINT_SUMMERY")) {
                output= printSummary();
                System.out.println(output);
            }
            else {
                throw new ProcessingException("Invalid Input Commands, please check the input command.");
            }

        }
        return output;
    }


    //Called when invoked BALANCE command
    //Initializes user, user's balance
    //adds user to metroCard
    @Override
    public Passenger initializeBalance(List<String> tokens) {
      String id= tokens.get(0);
      Integer balance = Integer.parseInt(tokens.get(1));
      Passenger passenger = new Passenger(id,balance);
      passengerSummery.getPassengerMap().put(id,passenger);
        return passenger;
    }

    //Called when invoked CHECK_IN command
    //adds checkIn journey to journey List
    @Override
    public void processCheckIn(List<String> tokens) {
        PassengerCheckIn checkedIn = intializeCheckList(tokens);
        EachJourneyCharge journeyCharge = updateBalance(checkedIn.getMetroCardNumber(), checkedIn.getCharge());
        checkedIn.setJourneyCharge(journeyCharge);
        if(checkedIn.getFromStation().equals("AIRPORT") && !passengerSummery.getOrderByTypeAirport().contains(checkedIn.getPassengerType())) {
            passengerSummery.getOrderByTypeAirport().add(checkedIn.getPassengerType());
        }
        if(checkedIn.getFromStation().equals("CENTRAL") && !passengerSummery.getOrderByTypeCentral().contains(checkedIn.getPassengerType())) {
            passengerSummery.getOrderByTypeCentral().add(checkedIn.getPassengerType());
        }
        passengerSummery.getCheckInList().add(checkedIn);

    }




    @Override
    public String printSummary() {
        String output = calculateStationStatistics(passengerSummery.getCheckInList());
        return output;
    }

    //Calculates Station Statistics and sets output in expected format
    public String calculateStationStatistics(List<PassengerCheckIn> checkedIn) {
        //filter journey from station "AIRPORT" group by Passenger Type and calculate statistics for "AIRPORT" station
        Map<String,List<PassengerCheckIn>> passengerAtAirport= checkedIn.stream().filter(current ->current.getFromStation().equals("AIRPORT")).collect(Collectors.groupingBy(current -> current.getPassengerType()));
        List<StationStatistics> airportStats = calcEachStationStatistics("AIRPORT", passengerAtAirport);
        String airportSummary = createSummary("AIRPORT",airportStats);


        //filter journey from station "CENTRAL" group by Passenger Type and calculate statistics for "CENTRAL" station
         Map<String,List<PassengerCheckIn>> passengerAtCentral = checkedIn.stream().filter(current ->current.getFromStation().equals("CENTRAL")).collect(Collectors.groupingBy(current -> current.getPassengerType()));
         List<StationStatistics> centralStats = calcEachStationStatistics("CENTRAL",passengerAtCentral);
         String centralSummary= createSummary("CENTRAL",centralStats);

         //Output construction
        String output = "";
        output= output+"TOTAL COLLECTION CENTRAL"+ passengerSummery.getTotalAmountCentral()+" "+passengerSummery.getTotalDiscountCentral()+"\n";
        output= output+"PASSENGER_TYPE_SUMMERY\n";
        output = output+centralSummary;
        output+="TOTAL_COLLECTION AIRPORT "+passengerSummery.getTotalAmountAirport()+" "+passengerSummery.getTotalDiscountAirport()+"\n";
        output+="PASSENGER_TYPE_SUMMARY\n";
        output+=airportSummary;

        return output;
    }

    //Input - Map Key(PassengerType) Value(Every Journey detail for that passenger type)
    //Output- Returns Station Statistics List - Contains For particular passenger type, total passengers, total charge, total discount
    public List<StationStatistics> calcEachStationStatistics(String fromStation, Map<String, List<PassengerCheckIn>> passengerAtStation) {
      List<StationStatistics> stationstatsList = new ArrayList<>();

      // Traversing passenger type
        for(Map.Entry<String,List<PassengerCheckIn>> current: passengerAtStation.entrySet()){
            StationStatistics statistics=new StationStatistics(current.getKey());
             if (fromStation=="AIRPORT"){
                 statistics.setOrderByType(passengerSummery.getOrderByTypeAirport().indexOf(current.getKey()));
             }
             if(fromStation=="CENTRAL"){
                 statistics.setOrderByType(passengerSummery.getOrderByTypeCentral().indexOf(current.getKey()));
             }
             for(PassengerCheckIn passenger: current.getValue()){
                 statistics.setCount(statistics.getCount()+1);
                 statistics.setTotalCharge(statistics.getTotalCharge()+passenger.getJourneyCharge().getCostOfJourney());
                 statistics.setTotalDiscount(statistics.getTotalDiscount()+passenger.getJourneyCharge().getDiscount());
             }
             stationstatsList.add(statistics);
        }
        stationstatsList=sortBasedOnHighestAmount(stationstatsList);
        return stationstatsList;
    }


    //Returns Output of Statistics as a list
    public String createSummary(String station, List<StationStatistics> stationStats) {
        String output = "";
        int totalCharge=0,totalDiscount=0;
            for(StationStatistics stats:stationStats){
                totalCharge=totalCharge+stats.getTotalCharge();
                totalDiscount=totalDiscount+stats.getTotalDiscount();
                output=output+stats.getPassengerType()+" "+stats.getCount()+"\n";
            }

            if (station.equals("AIRPORT")){
                passengerSummery.setTotalAmountAirport(totalCharge);
                passengerSummery.setTotalDiscountAirport(totalDiscount);
            }
            else if (station.equals("CENTRAL")) {
                passengerSummery.setTotalAmountCentral(totalCharge);
                passengerSummery.setTotalDiscountCentral(totalDiscount);
            }
        return output;
    }

    //sort given PassengerType List according to value field(Total Amount) descending
    public List<StationStatistics> sortBasedOnHighestAmount(List<StationStatistics> stationstatsList) {
        String output = "";
        Comparator<StationStatistics> compareByAmount = (StationStatistics s1,StationStatistics s2)->s2.getTotalCharge().compareTo(s1.getTotalCharge());
        Comparator<StationStatistics> compareByType = (StationStatistics s1, StationStatistics s2)-> s1.getOrderByType().compareTo(s2.getOrderByType());
        Collections.sort(stationstatsList,compareByAmount.thenComparing(compareByType));
        return stationstatsList;
    }


    //Checks In user with given details
    public PassengerCheckIn intializeCheckList(List<String> tokens) {
        String id = tokens.get(0);
        String type = tokens.get(1);
        String station = tokens.get(2);
        PassengerCheckIn checkedIn = new PassengerCheckIn(id, type, station);
        return checkedIn;
    }


    //calculates total amount to be paid, discount
    //updates user balance accordingly
    public EachJourneyCharge updateBalance(String metroCardNumber, int charge) {
      int totalAmountCollected = 0, discount=0;
      Passenger currentPassenger= passengerSummery.getPassengerMap().get(metroCardNumber);
      if (currentPassenger==null){
          throw new ProcessingException("MetroCard user not registered");
      }
      currentPassenger.setJourneyCount(currentPassenger.getJourneyCount()+1);

      //Return journey, 50% discount
        if (currentPassenger.getJourneyCount()%2==0){
            charge=charge/2;
            discount=charge;
        }

        //Insufficient Balance, Calculate service fee and balance required
        //SufficientBalance, Deduct Money from metroCard
        if(currentPassenger.getBalanceInMetroCard()<charge){
            int balanceRequired = charge-currentPassenger.getBalanceInMetroCard();
            totalAmountCollected= (int) (totalAmountCollected+0.02*balanceRequired);
            currentPassenger.setBalanceInMetroCard(0);
        }
        else {currentPassenger.setBalanceInMetroCard(currentPassenger.getBalanceInMetroCard()-charge);
        }
        totalAmountCollected=totalAmountCollected+charge;

        //stores discount, amount paid during this journey
       EachJourneyCharge journeyCharge = new EachJourneyCharge(discount,totalAmountCollected);
        return journeyCharge;
    }

}
