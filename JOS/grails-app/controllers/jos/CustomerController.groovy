package jos



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


class CustomerController {

    static scaffolding=true;
	
	def addCustomer(){
		
	}
	def addFollowing(){
		String houseName=params["houseName"];
		String customerName=params['customerId'];
		House h=House.findByName(houseName);
		Customer c=Customer.findByName(customerName);
		if(h==null){
			render(status: 601, text: 'Server does not recognize the restaurant by name. ');
		}
		else if(c==null){
			render(status: 611, text: 'Server does not recognize the customer by name. ');
		}else{
			h.customers.add(c);
			h.merge();
			c.following.add(h);
			c.merge();
			if(h.hasErrors()){
				println h.errors;
				render(status: 608, text: 'Restaurant processing failed due to unknown reason');
			}else if(c.hasErrors()){
				println c.errors;
				render(status: 613, text: 'Customer processing failed due to unknown reason');
			}
			else{
				render(status: 671, text: "Successfully followed the restaurant.");
			}

		}


	}
	def removeFollowing(){
		String houseName=params["houseName"];
		String customerName=params['customerId'];
		House h=House.findByName(houseName);
		Customer c=Customer.findByName(customerName);
		if(h==null){
			render(status: 601, text: 'Server does not recognize the restaurant by name. ');
		}
		else if(c==null){
			render(status: 611, text: 'Server does not recognize the customer by name. ');
		}else{
			h.customers.remove(c);
			h.merge();
			c.following.remove(h);
			c.merge();
			if(h.hasErrors()){
				println h.errors;
				render(status: 608, text: 'Restaurant processing failed due to unknown reason');
			}else if(c.hasErrors()){
				println c.errors;
				render(status: 613, text: 'Customer processing failed due to unknown reason');
			}
			else{
				render(status: 671, text: "Successfully followed the restaurant.");
			}

		}
	}
}
