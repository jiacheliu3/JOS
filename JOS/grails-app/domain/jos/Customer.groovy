package jos

class Customer {
	String name;
	String email;
	String address;
	String mobile;
	
	
	Set<House> following=new HashSet<>();
	Set<Ticket> tickets=new HashSet<>();
	static constraints = {
		name(blank:false,nullable:false)
		address(blank:true,nullable:true)
		email(blank:true,nullable:true)
		mobile(blank:true,nullable:true)
	}
	static mapping={
		tickets sqlType: 'VARBINARY(10000)'
		following sqlType: 'VARBINARY(10000)'

	}
}
