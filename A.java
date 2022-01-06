public class Aircraft implements Comparable<Aircraft>
{
  int numEconomySeats;
  int numFirstClassSeats;
  String model;
  
// I created a new constructor that does not need the model as it is not required

  public Aircraft(int seats, int firstClass) //This constructor takes into account of both economy and firstclass seats for LongHaulFlights
  {
	  this.numEconomySeats = seats;
	  this.numFirstClassSeats = firstClass;
  }

  public Aircraft(int seats) //This constructor takes into account of only economy seats for normal flights
  {
	  this.numEconomySeats = seats;
  }
  
	public int getNumSeats()
	{
		return numEconomySeats;
	}
	
	public int getTotalSeats()
	{
		return numEconomySeats + numFirstClassSeats;
	}
	
	public int getNumFirstClassSeats()
	{
		return numFirstClassSeats;
	}

	public String getModel()
	{
		return model;
	}

	public void setModel(String model)
	{
		this.model = model;
	}
	
	public void print()
	{
		System.out.println("Model: " + model + "\t Economy Seats: " + numEconomySeats + "\t First Class Seats: " + numFirstClassSeats);
	}

	public int getRows() //I added this method to get the number of rows depending on the seat capacity of the aircraft 
	{
		int rows = 0;
		if(getTotalSeats() <= 8) //if the capacity of the aircraft is less or equal to 8 then it will use only to rows to print the seat layout
		{
			rows = 2;
		}
		else{
			rows = 4; //else if the capacity is larger than 8 then it will use 4 rows 
		}
		return rows;
	}

	public int getColumns() // I added this method to get the number of columns of economy seats depending on the seat capacity of the aircraft
	{
		int columns = 0;

		columns = getTotalSeats()/getRows(); //it uses the getRows() method to determine how many columns will be needed depending on the number of rows

		return columns;
	}

	public int getColumnsFCL() // I added this method to get the number of columns of first class seats depenging on the
	{
		int columns = 0;
		if(getNumFirstClassSeats() != 0) //checks if the aircraft has first class seats
		{
			columns = getNumFirstClassSeats()/4; //if it is then it will determine the number of columns needed for the seat layout
		}
		else{
			columns = -1; //otherwise set the column variable to -1 so that the seatLayout() method can print the seats in correct form
		}
		return columns;
	}



	

	public int compareTo(Aircraft other)
	{
  	if (this.numEconomySeats == other.numEconomySeats)
  		return this.numFirstClassSeats - other.numFirstClassSeats;
  	
  	return this.numEconomySeats - other.numEconomySeats; 
    }

	public String[][] seatLayout(){ //I added this method so that the seat layout will be set correctly 

		
		String[][] result = new String[getRows()][getColumns()]; //This will initialize the 2D array list to the number of rows and columns needed using the previous methods
		
		for(int i =0; i < getRows(); i++){ 

			char row = (char) ((char) 65 + i); //this uses digits to convert them into alphabetical value from A to D depending on how many rows there are

			for(int j = 0; j < getColumns(); j++)
			{
				String call = "";
				if((j <= getColumnsFCL()) && (getNumFirstClassSeats() > 0)) //checks if there are first class seats
				{
					call = "+"; //if there are then it will add the "+" string to the end of the concatenated string
				}
				else{
					call = ""; //if it's not, then nothing will be added
				}

				result[i][j] = Integer.toString(j+1) + Character.toString(row) + call; //this line will convert the Integer and char values found so that they are able to be concatenated 
			}
		}

		return result; //the result will be the string name of the seat 

	}


}
