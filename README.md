# JOS
*5 domain classes:*

House: This can be a restaurant or a shop etc.

Queue: A house can have many queues, such as for big tables and small tables.

Customer: A customer should be a termial user that is not a part of the restaurant.

Ticket: This simply means reservations.

User: This handles your login and password

#Request Format

http get and post are all accepted. Post is preferred. The house controller functions can be reached by url like *http://144.214.121.58:8080/JOS/house/addHouse* or *https://144.214.121.58:9090/JOS/house/addHouse* 

One note is that you can access *https://144.214.121.58:9090/JOS/house/create* to create House objects. **https://144.214.121.58:9090/JOS/house/* simply redirects you to that creation page. This holds true for Ticket and Customer classes. For User class, *https://144.214.121.58:9090/JOS/user/* redirects you to register page that simply serves the same as creation. 

In the sections below the fields in bold are the request urls and beneath them are the required fields that are read by the controllers. All fields that are not mentioned will simply be ignored by the server.

#House Controller Functions:

**addHouse**

houseName, address, tel, homepage, latitude, longitude, description, logo  (only houseName is compulsory field)

The field "logo" is an image with max size of 100kb.

*https://144.214.121.58:9090/JOS/house/create* serves as a template form page for easy House object creations.

**updateHouse**

Same parameters as addHouse

**addQueue**

houseNumber, queueNumber, expectedNumber, ticketNumber

expectedNumber is the next ticket number to be called.

ticketNumber is the number to be given on the next ticket. expectedNumber should be less than ticketNumber but currently this is not checked.

**removeQueue**

houseNumber, queueNumber

The queue will be removed regardless of the current status and tickets. This should only be done by restaurant itself.

**updateQueue**

houseNumber, queueName, expectedNumber, ticketNumber

The format is just the same as addQueue.

**requestQueue**

houseNumber, queueName

A successful response is like 'Expected next number is: 10, next ticket number is : 12'

**requestFollowers**

houseName

In text of a successful response all followers of the house are printed. This can be used to count the number of followers. The houseName is case-sensitive.

**queryQueues**

houseName

All Queues of the house will be returned in json format as a list, each of which possesses 3 fields: name, expectedNumber, ticketNumber

**findHouseByName**

houseName

The House object is returned in json format. There are two fields that should be stressed. The queues of this house are not rendered. If you want queue information you should just call queryQueues. The logo image is not passed either. It should be requested by calling houseLogoImage.

**houseLogoImage**

houseName

This service is separated because one http response can only carry data or file, not both. Since you have houseName, it doesn't hurt to call two urls because they simply either succeed or fail together.

#Customer Controller Functions:

**addCustomer**

customerId, email, mobile, address (only customerId is compulsory)

On a successful request, the customer is created. Note the customerId is a String that is case-sensitive.

**updateCustomer**

customerId, email, mobile, address (only customerId is compulsory)

**addFollowing**

houseName, customerId

When a customer follows a restaurant he/she should be able to see the current status of queues(by design). Customer will be checked if duplicate before following a restaurant. That is to say, a customer cannot follow a house twice. Otherwise a response will be sent with http status 631 or 632.

**removeFollowing**

houseName, customerId

**requestFollowing**

customerId

The rendered text of response is the set of all houses that are followed by the customer.

#Ticket Controller Functions:

**createTicket**

houseName, customerId, queueName

When a ticket is successfully created, the response text will be the ticket id to be used to track the ticket.

**inactivateTicket**

ticketId

The validity of the ticket will be invalid meaning it cannot be used.

**requestTicket**

ticketId

In a successful response the full status will be printed.

**useTicketWithId**

ticketId

The ticket will be marked invalid and the next expected number in queue will go forward to the ticket number + 1.

# User Controller Functions:

**createUser**

Note this is not a service that you send requests to, but a page where you fills a page and get registered. The email and password will be validated. However, by sending requests to **register** there will not be such validations.

**register**

email, password

A user registers by email and password. 

# Ad

**createAd**

houseName, adImage(an image file that is the content of this ad, with max size of 100kb), description

The ad belongs to the house found by houseName, aka the deletion of houses will be cascaded on all ads. A Long will be returned as id of this advertisement to keep track of it.

**findAdById**

adId

The id, houseName and description will be returned in json format.

**adImage**

adId

This returns the image of advertisement

**adShopLogo**

adId

This returns the image for shop logo that is got from the house that this ad belongs to.


# Error codes

I used error code in range 601~650 as markers of error status. Http status in this range is not officially defined so I take them as my own status markers. Every request, if successful, should get a 200 http status while those failed ones get 6XX status. The status matchings can be found in HttpStatusCodes. The JOS/codes.txt is deprecated.

Generally a 6X9 status stands for an exception that is unexpected and the error case is not taken care of. So if you see one 6X9 status in response, let me know and I'll check the server log and try to solve it.

# Important changes to carry out in Stage 2

In stage 2 which is the next phase, both House and Customer classes will be binded with User. That is to say, you login as User and then the system will tell whether you are a Customer or a House. 

Another change is that you'll need to send "email" and "password" in every request for authentication and identification purposes. Requests that don't pass the authentication will get a error code (not decided yet, won't be 200 anyway).


