package col106.assignment5;

public class DateNode implements DateInterface {

	int day;
	int month;
	int year;

	public DateNode(int day, int month , int year){
		this.day = day;
		this.month= month;
		this.year = year;
	}

	public int getDay(){
		return this.day;
	}

	public int getMonth(){
		return this.month;
	}

	public int getYear(){
		return this.year;
	}

}
