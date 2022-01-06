import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class FlightReservationSystem
{
	public static void main(String[] args)
	
	{
		FlightManager manager = new FlightManager();

		ArrayList<Reservation> myReservations = new ArrayList<Reservation>();	// my flight reservations


		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		while (scanner.hasNextLine())
		{
			String inputLine = scanner.nextLine();
			if (inputLine == null || inputLine.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			Scanner commandLine = new Scanner(inputLine);
			String action = commandLine.next();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}

			else if (action.equalsIgnoreCase("Q") || action.equals("QUIT"))
				return;

			else if (action.equalsIgnoreCase("LIST"))
			{
				manager.printAllFlights(); 
			}
			else if (action.equalsIgnoreCase("RES")) 
			{
				String flightNum = null;
				String passengerName = null;
				String passport = null;
				String seatnumber = null;
				
				if(commandLine.hasNext()) //it will read the values from the scanner and puts them into variables
				{
					flightNum = commandLine.next();

					if(commandLine.hasNext()){
						passengerName = commandLine.next();
					}
					if(commandLine.hasNext()){
						passport = commandLine.next();
					}
					if(commandLine.hasNext()){
						seatnumber = commandLine.next();
					}

					try{
						boolean found = false; //boolean variable used to indicate if the flightnumber is valid
						Set<String> keySet = manager.flights.keySet(); //using the created set, it will check all the flightnumbers stored in the map

						for(String key : keySet)
						{
							if(key.equals(flightNum))
							{
								found = true;
							}
						}

						if(found){ //if the flight number is valid

							Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seatnumber, manager.flights.get(flightNum).toString()); //tries to reserve a flight
							if(res == null) //if the reservation object is null, it will throw an exception 
							{
								throw new NullPointerException("");
							}
							else{ //otherwise, it will add the reservation in the arraylist and prints it
	
								myReservations.add(res); //adds the reservation to the arraylist
								res.print(); //prints the flight info along with the passenger information of the reservation 
							}
						}
						else{
							throw new FlightNotFound("Flight " + flightNum + " Not Found"); //if the flight number is not found, it will print an exception 
						}

					}
					catch(FlightNotFound | FlightIsFull | DuplicatePassengerException | SeatOccupiedException | PassengerNotInManifestException | NullPointerException e)
					{
						System.out.println(e.getMessage()); //catches all exceptions so that it prints the message stored in them if caught
					}
			    }		


				
			}

			else if (action.equalsIgnoreCase("CANCEL"))
			{
				String flightNum = null;
				String passengerName = null;
				String passport = null;
				String seatnumber = null;
				String seatType = null; //the null variable is used to help to compare the values stored in the reservation to the scanner variables
				if(commandLine.hasNext()){
					flightNum = commandLine.next();

					if(commandLine.hasNext()){
						passengerName = commandLine.next();
					}
					if(commandLine.hasNext()){
						passport = commandLine.next();
					}
					


					try
					{
						boolean found = false; //indicates if the flight number is valid
						boolean next = false; //indicates if the program can proceed in cancelling the reservation after checking the scanner variables
						boolean error = true; //indicates if the passenger inputted information is valid

						Set<String> keySet = manager.flights.keySet(); //using the created set, it will check if the flightnumber inputted is valid

						for(String key : keySet)
						{
							if(key.equals(flightNum))
							{
								found = true;
							}
						}

		


						if(found){ //if the flightnumber is valid
							
							
							Flight get = manager.flights.get(flightNum); //variable to get the flight from the flights map
							   
							
							Set<String> keysSet = new HashSet<String>(get.seatMap.keySet()); //creates a copy of a set to avoid errors when looping

							for(String key : keysSet) //loops through the keys of the seatmap to check if the inputted values are in the map
							{
		
								if(get.seatMap.get(key).getName().equals(passengerName)) //checks if the passenger name is in the seatmap
								{
									
									
									if(get.seatMap.get(key).getPassport().equals(passport)) //checks if the passport number is in the seatmap
									{
										next = true; //if both values are in the seatmap, the coding can proceed to cancel the reservation
										error = false; // prevents the exception from being thrown
										seatnumber = key; // stores the seat number in the seatmap to a variable
										get.cancelSeat(get.seatMap.get(key)); //removes the passenger from the seat map 
										
									}
									
									
									
									
								}
								
								
								
								
							}
							if(error){ //if error variable is true, then throw the exception
								throw new PassengerNotInManifestException("Passenger not found");
							}
							
							Reservation res = manager.reserveSeatOnFlight(flightNum, passengerName, passport, seatnumber, seatType); //reservation object used for comparison

							if(next)
							{
								for(int i = 0; i < myReservations.size(); i++) //loops through the reservation array list
								{
									if(myReservations.get(i).equals(res)){ //overides the method in the reservation file to check if values are equal

										manager.cancelReservation(myReservations.get(i)); // if so, the reservation is cancelled
							
										myReservations.remove(myReservations.get(i)); //then the reservation will be removed from the array list
										break;

									}
									
										
									
										
								}
										
							}
										
						}
									
						else
						{ // if the flight is not found, it will return an exception

							throw new FlightNotFound("Flight not found");
						}
									
									
									
									
								


						

						

					}
					catch(FlightNotFound | FlightIsFull | SeatOccupiedException | PassengerNotInManifestException |NullPointerException e)
					{
						System.out.println(e.getMessage());
					}
				}
			}
			else if (action.equalsIgnoreCase("SEATS"))
			{
				String flightNum = null;
				boolean found = false;
				if (commandLine.hasNext())
				{
					flightNum = commandLine.next();
				}
				try
				{
					Set<String> keySet = manager.flights.keySet(); //using the created set, it will check if the flightnumber inputted is valid

						for(String key : keySet)
						{
							if(key.equals(flightNum))
							{
								found = true;
							}
						}
					if(found){ //if the flight number is valid

						
						manager.seatsAvailable(flightNum); //checks if seats are available by checking the number of passengers
						
						manager.flights.get(flightNum).printSeats(); //prints the seatlayout
					}
					else{
						throw new FlightNotFound("Flight " + flightNum + " Not found");
					}
				}
				
				
				catch(FlightIsFull | NullPointerException |FlightNotFound e) //otherwise, it will print an exception indicating the flight is full
				{
					System.out.println(e.getMessage());
				}

				//if seat is occupied print XX instead
			}
			else if(action.equalsIgnoreCase("PASMAN"))
			{
				if(commandLine.hasNext())
				{
					String flightNum = commandLine.next();
					try{

						manager.flights.get(flightNum).printPassengerManifest(); //gets the value from the flights map to print the passengers that have reserved a seat
					}
					catch(NullPointerException e){
						System.out.println("Value not found");
					}
				}
			}
			else if (action.equalsIgnoreCase("MYRES"))
			{
				for (Reservation myres : myReservations)
					myres.print();
			}
			else if (action.equalsIgnoreCase("SORTBYDEP"))
			{
				manager.sortByDeparture();
			}
			else if (action.equalsIgnoreCase("SORTBYDUR"))
			{
				manager.sortByDuration();
			}
			else if (action.equalsIgnoreCase("CRAFT"))
			{
				manager.printAllAircraft();
			}
			else if (action.equalsIgnoreCase("SORTCRAFT"))
			{
				manager.sortAircraft();
			}
			System.out.print("\n>");
		}
	}


}

