package jos

class TicketController {

	static scaffolding=true;

	def createTicket(){
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
			h.customers.add(c);
			//go on add the customer to the queue
			def map=h.nextTicketNumber;
			if(map.containsKey(queueId)){
				println "Update queue ${queueId} of house ${houseName}";
				int clientId=map[queueId];

				h.customers.add(c);
				c.following.add(h);

				//create new ticket
				Ticket t=new Ticket(customer:c,house:h,number:clientId);
				t.save();
				if(t.hasErrors()){
					println t.errors;
					render(status: 621, text: 'Ticket creation failed');
				}else{
					map[queueId]+=1;
					h.waitingList[queueId].add(t);
					c.tickets.add(t);
					h.merge(flush:true);
					c.merge(flush:true);
					if(h.hasErrors()){
						println h.errors;
						render(status: 609, text: 'Queue processing failed.');
					}else if(c.hasErrors()){
						println c.errors;
						render(status: 609, text: 'Queue processing failed.');
					}
					else{
						render(status: 654, text: clientId);
					}

				}

			}else{
				render(status: 604, text: 'Queue name is not found.');
			}
		}
	}
	def removeTicket(){
		String houseName=params["houseName"];
		String customerName=params['customerId'];
		String queueId=params["queueName"];

		House h=House.findByName(houseName);
		Customer c=Customer.findByName(customerName);
		Ticket t=Ticket.findByCustomerAndHouse(c,h);
		if(h==null){
			render(status: 601, text: 'Server does not recognize the restaurant by name. ');
		}
		else if(c==null){
			render(status: 611, text: 'Server does not recognize the customer by name. ');
		}else if(t==null){
			render(status: 622, text: 'Ticket not found. ');
		}
		else{
			//remove ticket from house queue
			def map=h.waitingList;
			if(!map.containsKey(queueId)){
				render(status: 604, text: 'Queue name is not found.');
			}else{
				map[queueId].remove(t);
				h.merge();
				if(h.hasErrors()){
					println h.errors;
					render(status: 609, text: 'Queue processing failed.');
				}
			}
			//delete the ticket
			t.isValid=true;
			t.merge();
			if(t.hasErrors()){
				println t.errors;
				render(status: 623, text: 'Ticket processing failed.');
			}
		}
	}
	
}
