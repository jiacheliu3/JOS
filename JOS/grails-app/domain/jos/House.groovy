package jos

class House {
	//information
	String name;
	String address;
	String tel;
	String homepage;
	
	//relationship management
	Map<String,Integer> nextTicketNumber=new HashMap<>();
	Map<String,Integer> currentTicketNumber=new HashMap<>();
	Set<Customer> customers=new HashSet<>();
	Map<String,TreeSet<Ticket>> waitingList=new HashMap<>();
    static constraints = {
    }
	
	
}
