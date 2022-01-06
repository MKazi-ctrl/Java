/*
 * A Long Haul Flight is a flight that travels a long distance and has two types of seats (First Class and Economy)
 */

public class LongHaulFlight extends Flight
{
	int firstClassPassengers;
		
	String seatType;
	
	// Possible seat types
	public static final String firstClass = "First Class Seat";
	public static final String economy 		= "Economy Seat";  
	

	public LongHaulFlight(String flightNum, String airline, String dest, String departure, int flightDuration, Aircraft aircraft)
	{
		// use the super() call to initialize all inherited variables
		// also initialize the new instance variables 
		super(flightNum, airline, dest, departure, flightDuration, aircraft);
		firstClassPassengers = 0;
		seatType = "";
	}

	public LongHaulFlight()
	{
     // default constructor
	 super();
	 firstClassPassengers = 0;
	 seatType = "";
	}
	
	public void assignSeat(Passenger p)
	{
		int seat = random.nextInt(aircraft.getNumFirstClassSeats());
		p.setSeat("FCL"+ seat);
	}
	
	public boolean reserveSeat(String seatType)
	{
		
		if(seatType.equals(economy)) //checks if an economy seat is being reserved
		{
			if(super.reserveSeat()) //then reserve an economy seat
			{
				return true;
			}
		}
		else if(seatType.equals(firstClass)) //checks if a first class seat is being reserved
		{
			
			if(this.firstClassPassengers < aircraft.getNumFirstClassSeats()) //checks if seats are available
			{
				firstClassPassengers++; 
				return true;
			}
		}
		
		return false; 
		
	}
	
	public void cancelSeat(String seatType)
	{
		
		if(seatType.equals(firstClass) && this.firstClassPassengers > 0) //if seat type is first class then it will decrement the number of first class passengers
		{
			this.firstClassPassengers--; 
		}
		else{
			super.cancelSeat(); //otherwise, decrement the number of economy passengers
		}
		
	}
	
	public int getPassengerCount()
	{
		return getNumPassengers() +  firstClassPassengers;
	}
	
	
	public boolean seatsAvailable(String seatType)
	{
		
		if (seatType.equals("FCL"))
			return firstClassPassengers < aircraft.getNumFirstClassSeats();
		else
			return super.seatsAvailable(seatType);
	}

	
	public String toString()
	{
		 return super.toString() + "\t LongHaul";
	}

	public FlightType getFlightType()
	{
		return FlightType.LONGHAUL;
	}
}
