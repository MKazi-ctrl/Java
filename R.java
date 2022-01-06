public class Reservation
{
	String flightNum;
	String flightInfo;
	String passengerName;
	String passport;
	String seat;
	String seatType;
	boolean firstClass;
	int seatNum;
	
	
	
	public Reservation(String flightNum, String passengerName, String passport, String seat, String info)
	{
		this.flightNum = flightNum;
		this.passengerName = passengerName;
		this.passport = passport;
		this.seat = seat;
		this.flightInfo = info;

	}

	public boolean isFirstClass()
	{
		return firstClass;
	}
	
	public String getFlightNum()
	{
		return flightNum;
	}
	
	public String getFlightInfo()
	{
		return flightInfo;
	}

	public void setFlightInfo(String flightInfo)
	{
		this.flightInfo = flightInfo;
	}

	public void setFirstClass()
	{
		this.firstClass = true;
	}
	
	public boolean equals(Object other)
	{
		Reservation otherRes = (Reservation) other;
		return flightNum.equals(otherRes.flightNum)&&  passengerName.equals(otherRes.passengerName) && passport.equals(otherRes.passport); 
	}


	public void print()
	{
		System.out.println(flightInfo + " " + passengerName + " " + passport + " " + seat);
	}
}
