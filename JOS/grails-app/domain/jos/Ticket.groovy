package jos

class Ticket implements Comparable<Ticket> {
	

	Customer customer;
	House house;
	Date time;
	int number;
	boolean isValid;
    static constraints = {
    }
	
	public Ticket(Customer customer,House house,int number){
		customer=customer;
		house=house;
		time=new Date();
		number=number;
		isValid=true;
	}
	
	@Override
	public int compareTo(Ticket other){
		// compareTo should return < 0 if this is supposed to be
		// less than other, > 0 if this is supposed to be greater than
		// other and 0 if they are supposed to be equal
		int last = this.number.compareTo(other.number);
		return last;
	}
}
