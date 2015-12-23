package jos

class Customer {
	String name;
	String email;
	String address;
	String mobile;
	
	
	Set<House> following=new HashSet<>();
	Set<Ticket> tickets=new HashSet<>();
	static constraints = {
	}
}
