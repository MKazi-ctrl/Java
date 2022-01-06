import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

public class Flight
{
	public enum Status {DELAYED, ONTIME, ARRIVED, INFLIGHT};
	public static enum FlightType {SHORTHAUL, MEDIUMHAUL, LONGHAUL};
	public static enum SeatType {ECONOMY, FIRSTCLASS, BUSINESS};

	String flightNum;
	private String airline;
	private String origin, dest;
	private String departureTime;
	private Status status;
	private int flightDuration;
	protected Aircraft aircraft;
	protected int numPassengers;
	protected FlightType type;
	protected ArrayList<Passenger> manifest; 
	protected TreeMap<String, Passenger> seatMap;
	boolean seatTaken = false;

	protected Random random = new Random();
	

	public Flight()
	{
		this.flightNum = "";
		this.airline = "";
		this.dest = "";
		this.origin = "Toronto";
		this.departureTime = "";
		this.flightDuration = 0;
		this.aircraft = null;
		numPassengers = 0;
		status = Status.ONTIME;
		type = FlightType.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
	}
	
	public Flight(String flightNum)
	{
		this.flightNum = flightNum;
	}
	
	public Flight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		this.flightNum = flightNum;
		this.airline = airline;
		this.dest = dest;
		this.origin = "Toronto";
		this.departureTime = departure;
		this.flightDuration = flightDuration;
		this.aircraft = aircraft;
		numPassengers = 0;
		status = Status.ONTIME;
		type = FlightType.MEDIUMHAUL;
		manifest = new ArrayList<Passenger>();
		seatMap = new TreeMap<String, Passenger>();
	}
	
	public FlightType getFlightType()
	{
		return FlightType.MEDIUMHAUL;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	public void setFlightNum(String flightNum)
	{
		this.flightNum = flightNum;
	}
	public String getAirline()
	{
		return airline;
	}
	public void setAirline(String airline)
	{
		this.airline = airline;
	}
	public String getOrigin()
	{
		return origin;
	}
	public void setOrigin(String origin)
	{
		this.origin = origin;
	}
	public String getDest()
	{
		return dest;
	}
	public void setDest(String dest)
	{
		this.dest = dest;
	}
	public String getDepartureTime()
	{
		return departureTime;
	}
	public void setDepartureTime(String departureTime)
	{
		this.departureTime = departureTime;
	}
	
	public Status getStatus()
	{
		return status;
	}
	public void setStatus(Status status)
	{
		this.status = status;
	}
	public int getFlightDuration()
	{
		return flightDuration;
	}
	public void setFlightDuration(int dur)
	{
		this.flightDuration = dur;
	}
	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	public void setNumPassengers(int numPassengers)
	{
		this.numPassengers = numPassengers;
	}
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.numEconomySeats);
		p.setSeat("ECO"+ seat);
	}
	
	public String getLastAssignedSeat()
	{
		if (!manifest.isEmpty())
			return manifest.get(manifest.size()-1).getSeat();
		return "";
	}
	
	public boolean seatsAvailable(String seatType)
	{
		if (!seatType.equalsIgnoreCase("ECO")) return false;
		return numPassengers < aircraft.numEconomySeats;
	}
	
	public boolean seatsAvailable()
	{
		if(this.aircraft.numEconomySeats > this.numPassengers){
			return true;
		}
		return false;
	}
	
	public boolean equals(Object other)
	{
		Flight otherFlight = (Flight) other;
		return this.flightNum.equals(otherFlight.flightNum);
	}



	public void printSeats()
	{

		String[][] result = aircraft.seatLayout(); //assigns a new variable to the seatLayout created in the aircraft file
 
		for(int i = 0; i < result.length; i++)
		{
			for(int j = 0; j < result[i].length; j++)
			{
				if(!seatMap.isEmpty())//checkes if the seatMap is not null
				{
					seatTaken = false; 
					Set<String> keySet = seatMap.keySet(); //I created a set to loop through the keys in the seatmap which is the seat number

					for(String key : keySet) 
					{
						if( result[i][j].equals(key)){ //if someone reserved a seat under that particular seat number by checking the seatMap
							seatTaken = true; //then boolean is set to true
						}
					}
					if(seatTaken) //if the boolean is true, that mean the seat is taken
					{
						System.out.print("XX "); //therefore, an "XX" will be printed instead of the seat number to indicate that the seat is occupied
					}
					else
					{
					 System.out.print(result[i][j] + " "); //if the seat is not taken, then it will print the seat numbers according to the seat layout
					 seatTaken = false;
					}	
				    	
					
				}
				else{
					System.out.print(result[i][j] + " "); //if the seatmap is empty, there will be no need to check if the seat is taken and just prints the seatlayout
				}
			}
			if(i%2 == 0){

				System.out.print("\n"); //this allows the rows and columns to be represented as desired when printed
			}
			else{
				System.out.println("\n");
			}
			
			
		}
		System.out.println("XX = Occupied  + = First Class");

	}

	public void printPassengerManifest() //this method prints all the information related to the passenger that have reserved a seat in the flight
	{
		for(int i = 0; i < manifest.size(); i++)
		{
			System.out.println(manifest.get(i).toString()); //uses the toString() method located in passengers to print the passenger information
		}
	}

	public boolean reserveSeat()
	{
		if(seatsAvailable())
		{
			this.numPassengers += 1;
			return true;
		}
		return false;
	}

	public void reserveSeat(Passenger p, String seat) 
	{
		boolean found = false;
		boolean duplicate = false;

		for(int i = 0; i < manifest.size(); i++) //this for loop checks if the passenger already has reserved a seat
		{
			if(manifest.get(i).equals(p))
			{
				duplicate = true; //if passenger is already in the manifest arraylist then the duplicate variable will be set to true
			}
			
		}
		Set<String> keySet = seatMap.keySet(); // I created a set that loops through the key values in the seatMap which is the seat number values
		for(String key: keySet)
		{
			if(key.equals(seat)) //if the seat that is trying to be reserved is already in the seatmap 
			{
				found = true;// then the boolean variable found is set to true
			}
	
		}

		if(found){ //if the boolean variable found is true
			numPassengers -= 1; //it decrements the number of passengers to avoid an additional increment taking place when an exception is being thrown
			throw new SeatOccupiedException("Seat " + seat + " already occupied"); // it will throw the exception indicating that the seat is occupied
		}
		else if(duplicate) //else if the duplicate variable is true
		{
			numPassengers -= 1;
			throw new DuplicatePassengerException("Duplicate Passenger " + p.getName() + " " + p.getPassport()); //it will throw the exception that the passenger has already reserved this seat
		}
		else //else if none of the boolean variables are set to true
		{
			manifest.add(p); //it will add the passenger to the manifest arraylist
			seatMap.put(seat,p); // it will add the key value which is the seat number and the value of the key which is the passenger to the seatMap

		}
			
		
		
	
	
		
	}

	public void cancelSeat()
	{
		if(this.getNumPassengers() >= 0)
		{
			this.numPassengers -= 1;
		}
	}

	public void cancelSeat(Passenger p) // this method removes the passenger from the manifest array list and seatMap
	{
		
		manifest.remove(p); //removes the passenger from the arraylist 
		seatMap.remove(p.getSeat()); //removes the passenger from the seatmap by removing the key
		
		
	}

	
	public String toString()
	{
		 return airline + "\t Flight:  " + flightNum + "\t Dest: " + dest + "\t Departing: " + departureTime + "\t Duration: " + flightDuration + "\t Status: " + status;
	}
	
}

class DuplicatePassengerException extends RuntimeException{

	public DuplicatePassengerException(String message)
	{
		super(message);
	}
}

class PassengerNotInManifestException extends RuntimeException
{
	
	public PassengerNotInManifestException(String message)
	{
		super(message);
	}
}

class SeatOccupiedException extends RuntimeException
{
	
	public SeatOccupiedException(String message)
	{
		super(message);
	}
}

class FlightNotFound extends RuntimeException
{
	
	public FlightNotFound(String message)
	{
		super(message);
	}
}

class FlightIsFull extends RuntimeException{
	
	public FlightIsFull(String message){
		super(message);
	}
}




