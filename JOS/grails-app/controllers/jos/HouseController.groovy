package jos



import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional


class HouseController {

	static scaffolding=true

	def addRestaurant(){
		//parameters
		String name=params["houseName"];
		String address=params["address"];
		String tel=params['tel'];
		String homepage=params["homepage"];
		if(name==null||name==""){
			println ""
			render(status: 602, text: 'Cannot create the requested restaurant due to invalid fields.');
		}

		House h=new House(name:name,address:address,tel:tel,homepage:homepage);
		h.save();
		if(h.hasErrors()){
			println h.errors;
			render(status: 608, text: 'Restaurant processing failed.');
		}else
			render(status: 651, text: 'Restaurant created as requested');
	}
	def updateRestaurant(){
		//parameters
		String name=params["houseName"];
		String address=params["address"];
		String tel=params['tel'];
		String homepage=params["homepage"];
		House h=House.findByName(name);
		if(h==null){
			render(status: 601, text: 'Restaurant not found.');
		}
		else{
			h.address=address;
			h.tel=tel;
			h.homepage=homepage;
			h.merge(flush:true);
			if(h.hasErrors()){
				println h.errors;
				render(status: 608, text: 'Restaurant processing failed.');
			}else
				render(status: 652, text: 'Restaurant updated as requested');
		}
		
	}
	def addQueue(){
		String houseName=params["houseName"];
		String queueName=params["queueName"];
		String currentNumber=params['number'];
		House h=House.find("from House as h where h.name=:name",[name:houseName]);
		if(h==null){
			println "Restaurant not found! Please check the name "+houseName;
			render(status: 601, text: 'Server does not recognize the restaurant by name. ');
		}else if(!currentNumber.isInteger()){
			render(status: 603, text: 'Queue does not have a valid current number.');
		}
		else{
			def m=h.nextTicketNumber;
			def n=h.currentTicketNumber;
			//check if house already has the queue
			if(m.containsKey(queueName)){
				println "The queue name is already used in nextTicketNumber. The new value will override the older one";
				m[queueName]=Integer.parseInt(currentNumber);
				
			}
			else{
				m.put(queueName,Integer.parseInt(currentNumber));

			}
			if(n.containsKey(queueName)){
				println "The queue name is already used in currentTicketNumber. The new value will override the older one";
				n[queueName]=Integer.parseInt(currentNumber);
				
			}
			else{
				n.put(queueName,Integer.parseInt(currentNumber));

			}
			h.save();
			if(h.hasErrors()){
				println h.errors;
				render(status: 609, text: 'Queue processing failed.');
			}else
				render(status: 654, text: 'Queue created as request.');
		}
	}
	def removeQueue(){
		String houseName=params["houseName"];
		String queueName=params["queueName"];
		House h=House.find("from House as h where h.name=:name",[name:houseName]);
		if(h==null){
			println "Restaurant not found! Please check the name "+houseName;
			render(status: 601, text: 'Server does not recognize the restaurant by name. ');
		}
		else{
			def m=h.nextTicketNumber;
			def n=h.currentTicketNumber;
			//check if house already has the queue
			if(m.containsKey(queueName)&&n.containsKey(queueName)){
				println "Queue ${queueName} of ${houseName} will be removed. All the queueing information will be wiped!";
				m.remove(queueName);
				n.remove(queueName);
			}
			else{
				println "Queue ${queueName} of ${houseName} may not exist in nextTicket queue or currentTicket queue. It will be removed anyway. ";
				m.remove(queueName);
				n.remove(queueName);
			}
			h.save();
			if(h.hasErrors()){
				println h.errors;
				render(status: 609, text: 'Queue processing failed.');
			}else
				render(status: 656, text: 'Queue removed as request.');
		}
	}
	def updateQueue(){
		String houseName=params["houseName"];
		String queueName=params["queueName"];
		String currentNumber=params['number'];
		House h=House.find("from House as h where h.name=:name",[name:houseName]);
		if(h==null){
			println "Restaurant not found! Please check the name "+houseName;
		}else if(!currentNumber.isInteger()){
			render(status: 603, text: 'Queue does not have a valid current number.');
		}
		else{
			def m=h.nextTicketNumber;
			//check if house already has the queue
			if(m.containsKey(queueName)){
				m[queueName]=Integer.parseInt(currentNumber);
				println "Queue ${queueName} is updated";
			}
			else{
				m.put(queueName,Integer.parseInt(currentNumber));
				println "Queue ${queueName} is created";
			}
			h.save();
			if(h.hasErrors()){
				println h.errors;
				render(status: 609, text: 'Queue processing failed.');
			}else
				render(status: 654, text: 'Queue created as request.');
		}
	}
	def requestQueue(){
		String houseName=params["houseName"];
		String customerName=params['customerId'];
		String queueId=params["queueName"];

		House h=House.findByName(houseName);
		Customer c=Customer.findByName(customerName);
		if(h==null){
			render(status: 601, text: 'Server does not recognize the restaurant by name. ');
		}
		else if(c==null){
			render(status: 611, text: 'Server does not recognize the customer by name. ');
		}else{
			//find the current status of the house
			def map=h.currentTicketNumber;
			if(map.containsKey(queueId)){
				int index=map[queueId];
				render(status: 655, text:(int)(index-1));
			}
			else{
				println "Queue not found for ${queueId} in ${houseName}";
				render(status: 604, text: 'No queue found.');
			}
		}
	}

}
