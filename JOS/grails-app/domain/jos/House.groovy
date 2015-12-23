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
		name(blank:false,nullable:false)
		address(blank:true,nullable:true)
		homepage(blank:true,nullable:true)
		tel(blank:true,nullable:true)
		
		
	}
	static mapping={
		nextTicketNumber sqlType: 'VARBINARY(10000)'
		currentTicketNumber sqlType: 'VARBINARY(10000)'
		customers sqlType: 'VARBINARY(10000)'
		waitingList sqlType: 'VARBINARY(10000)'
	}
	
}
