import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class FlightManager
{
 // ArrayList<Flight> flights = new ArrayList<Flight>();

  TreeMap<String, Flight> flights = new TreeMap<>(); // I replaced the flights arraylist to a TreeMap
  
  TreeMap<String, Integer> flightTimes = new TreeMap<String,Integer>(); // I also replaced the flightTimes arraylist to a TreeMap for convenience 

  String[] cities 	= 	{"Dallas", "New York", "London", "Paris", "Tokyo"};

  final int DALLAS = 0;  final int NEWYORK = 1;  final int LONDON = 2;  final int PARIS = 3; final int TOKYO = 16;

  

  ArrayList<Aircraft> airplanes = new ArrayList<Aircraft>();  

 // ArrayList<String> flightNumbers = new ArrayList<String>();
  
  //String errMsg = null;

  Random random = new Random();


  
  
  public FlightManager()
  {
  	
	//the key valeue in the map flightTimes is the string destination of the flight, the value of they key is the duration of the flight

	flightTimes.put("Dallas", DALLAS);
	flightTimes.put("New_York", NEWYORK);
	flightTimes.put("London", LONDON);
	flightTimes.put("Paris", PARIS);
	flightTimes.put("Tokyo", TOKYO);

	try{
		Scanner scanner = new Scanner(new File("flights.txt"));//scanner that reads the content of the text files
		while(scanner.hasNextLine())
		{
			//values of the text files are then inputted into variables to be used
			String airline = scanner.next();
			String destination = scanner.next();
			String departure = scanner.next();
			int capacity = scanner.nextInt();
			String flightNum = generateFlightNumber(airline); //uses the generateFlightNumber() method to assign a flightnumber to a flight

			

			if(destination.equals("Tokyo")) //I have set Tokyo as a longhaulflight with a duration of 16 as shown in the video
			{
				airplanes.add(0,new Aircraft(capacity, 8)); //uses a different constructor that adds the number of first class and economy seats
				LongHaulFlight flight = new LongHaulFlight(flightNum, airline, destination, departure, flightTimes.get(destination),airplanes.get(0)); //creates the LongHaulFlight
				flights.put(flightNum,flight); //puts the flightnumber as a key and the flight as value into the flights seatMap
			}
			else{//else if the flight is not heading to Tokyo, then it will create normal flights
				airplanes.add(0,new Aircraft(capacity)); 
				Flight flight = new Flight(flightNum, airline, destination, departure, flightTimes.get(destination), airplanes.get(0));
				flights.put(flightNum,flight);
			}

			if(scanner.hasNextLine()){ //checks if there is still a line left unchecked from the text file to either proceed or end the process
				scanner.nextLine();
			}
		}

	}
	catch(FileNotFoundException | NullPointerException e) //if the text file is not found, it will print an error message
	{
		e.printStackTrace();
	}

	
  }
  
  private String generateFlightNumber(String airline) //This method generates a flight number for each flight
  {
	char first = airline.charAt(0); //first variable gets the first character of the string airline
    char second = ' ';
    for(int i = 0; i < airline.length(); i++) //loop used to find the "_" in the string airline which indicates the start of a new word
    {
      if(airline.charAt(i) == '_') //once the "_" string is found
      {
        second = airline.charAt(i+1);  //store the first character of the second word in the string variable
        break;
      }
    }
    int rand = random.nextInt(300 - 101) + 101; //the variable random chooses a random 3 digit number between 101 and 300
    String result = "" + first + second + rand; //then concatenate all the values found to acquire the needed result

  	return result; 
  }

  
  
  public void printAllFlights()
  {
	Set<String> keySet = flights.keySet(); // I used a set to loop through the values of the keys to print each one of them
  	for(String key : keySet)
	  {
		  System.out.println(flights.get(key).toString());
	  }
  }
  
  public boolean seatsAvailable(String flightNum) // method to check if seats are available 
  {
	  if(!flights.get(flightNum).seatsAvailable()) //gets the value of key through the flightNum variable to check if seat is available to be reserved 
	  {
		  return false; // if it's not available, it will throw an exception 
	  }
	  
	  return true; //otherwise, seats are available
  }
  

  public Reservation reserveSeatOnFlight(String flightNum, String name, String passport, String seatnumber, String seatType) 
  {
	  
	  String typeseat = "";

	  if(seatType == null){ //this if statement is used to help the CANCEL action in the FlightReservationSystem file to compare values against the scanner
		  return new Reservation(flightNum,name,passport,seatnumber,seatType);
	  }

	  

	  else if(flights.get(flightNum) instanceof LongHaulFlight){ //checks if the flight is a LongHaulFlight

		  LongHaulFlight lhlflight = (LongHaulFlight) flights.get(flightNum); //created object to check if the LongHaulFlight meets the requirements to reserve a seat
		  
		  if(seatnumber.contains("+")){ //checks if the seat is a first class seat if it contains "+" string
			  typeseat = "First Class Seat"; //if it does, then the seat type is set to first class seat
		  }
		  else
		  {
			  typeseat = "Economy Seat"; //otherwise, the seat type is an economy seat
		  }
		  if(lhlflight.reserveSeat(typeseat)){ // it will then check if the seat can be reserved in a LongHaulFlight

			  Passenger p = new Passenger(flightNum,name,passport,seatnumber,typeseat); 
			  flights.get(flightNum).reserveSeat(p, seatnumber); //if it can, it will reserve a seat using the passenger object created and seatNumber
			  Reservation res = new Reservation(flightNum,name, passport, seatnumber,seatType); 
			  if(typeseat == "First Class Seat"){ //if it's a first class seat, it will set the first class boolean to true
				  res.firstClass = true;
				  return res;
			  }
			  else{
				  return res;
			  }
		  }
		  
		}
		else if(seatsAvailable(flightNum)) //if the flight is not a LongHaulFlights, it will check if seats are available through economy seats
		{
			try{

				if(flights.get(flightNum).reserveSeat()){ //checks if the seat can be reserved
	
					typeseat = "Economy Seat"; 
					Passenger p = new Passenger(flightNum,name,passport,seatnumber,typeseat);
					flights.get(flightNum).reserveSeat(p, seatnumber); 
					return new Reservation(flightNum, name, passport, seatnumber, seatType); //reserves an economy seat
				}
			}
			catch(SeatOccupiedException e) //otherwise, if seat is taken, it will return an exception indicating that the seat is occupied
			{
				System.out.println(e.getMessage());
			}
		}

		else{
			throw new FlightIsFull("Flight " + flightNum + " is Full"); //if seats are not available, the throw an exception indicating it's full
		}

		return null;


  }


  public void cancelReservation(Reservation res)
  {
	  if(res == null)
	  {

		  throw new FlightNotFound("Flight Not Found");
	  }
	  if(flights.get(res.flightNum) instanceof LongHaulFlight) //checks if its a longhaulflight so that it can know which seat type it needs to cancel
	  {
		  LongHaulFlight lhlflight = (LongHaulFlight) flights.get(res.flightNum); //creating an object

		  if(res.isFirstClass() == true){ //if the reserved seat is firstclass
			  lhlflight.cancelSeat("First Class Seat"); //then it will use this method to decrement the number of firstclasspassengers to remove the reservation
		  }
		  else{
			lhlflight.cancelSeat("Economy Seat"); //otherwise, decrement the number of economy passengers
		  }
	  }
	  else{

		  flights.get(res.flightNum).cancelSeat(); //if it's a normal flight, then just cancel an economy seat
	  }
	  
  }
  
  public void sortByDeparture()
  {
	  ArrayList<Flight> sortDep = new ArrayList<Flight>();
	  Set<String> keySet = flights.keySet();

	  for(String key: keySet)
	  {
		  sortDep.add(flights.get(key));
	  }
	  Collections.sort(sortDep, new DepartureTimeComparator());
  }
  
  private class DepartureTimeComparator implements Comparator<Flight>
  {
  	public int compare(Flight a, Flight b)
  	{
  	  return a.getDepartureTime().compareTo(b.getDepartureTime());	  
  	}
  }
  
  public void sortByDuration()
  {
	  ArrayList<Flight> sortDur = new ArrayList<Flight>();
	  
	  Set<String> keySet = flights.keySet();

	  for(String key: keySet)
	  {
		  sortDur.add(flights.get(key));
	  }
	  Collections.sort(sortDur, new DurationComparator());

  }
  
  private class DurationComparator implements Comparator<Flight>
  {
  	public int compare(Flight a, Flight b)
  	{
  	  return a.getFlightDuration() - b.getFlightDuration();
   	}
  }
  
  public void printAllAircraft()
  {
	for( int i = 0; i < airplanes.size(); i++)
	{
		airplanes.get(i).print();
	  }
   }
  
  public void sortAircraft()
  {
  	Collections.sort(airplanes);
  }
}

