public class Passenger
{
	private String flightNum;
	private String passengerName;
	private String passport;
	private String Seat;
	private int SeatNum;
	private int passportNum;
	private String seatType;
	

	public Passenger(String flightNum, String passengerName, String passport, String Seat, String seatType)
	{
		this.flightNum = flightNum;
		this.passengerName = passengerName;
		this.passport = passport;
		this.Seat = Seat;
		this.seatType = seatType;
	}
	



	public boolean equals(Object other)
	{
		Passenger res = (Passenger) other;
		return (this.getName().equals(res.getName()) && (this.getPassport().equals(res.passport)));
		
		
	}

	
	public String getName()
	{
		return passengerName;
	}
	

	public void setName(String name)
	{
		this.passengerName = name;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getSeat()
	{
		return Seat;
	}

	public void setSeat(String seatNum)
	{
		this.Seat = seatNum;
	}
	
	public String toString()
	{
		return passengerName + " " + passport + " " + Seat;
	}
}
