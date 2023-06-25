# naggaro-test-sharjeel

Introduction: 
The server will handle requests to view statements by performing simple search on date and amount ranges. 
o	-  The request should specify the account id. 
o	-  The request can specify from date and to date (the date range). 
o	-  The request can specify from amount and to amount (the amount range). 
o	-  If the request does not specify any parameter, then the search will return three months 
back statement. 
o	-  If the parameters are invalid a proper error message should be sent to user. 
o	-  The account number should be hashed before sent to the user. 
o	-  All the exceptions should be handled on the server properly. 
Authentication and Security: 
The authenticated users are:
User1: Username: admin & Password: admin 
User2: Username: user & Password: user 

Access Control Specifications: 
o	-  The ‘admin’ can perform all the requests (specify date and amount range). 
o	-  The ‘user’ can only do a request without parameters which will return the three months 
back statement. 
o	-  When the test user tries to specify any parameter, then HTTP unauthorized (401) access 
error will be sent. 
o	-  The user cannot login twice (the user should logout before login). 
o	-  The session time out is 5 minutes. 
Database information: 
We are using JPA so you can use any database just by changing it’s driver and other configuration in configuration file.
Anyhow below entities are available in our code

Account: 
	-  ID: Autonumber 
	-  account_type: Text 
	-  account_number: Text 
Statement: 
	-  ID: Autonumber 
	-  account_id: Number 
	-  datefield: Text 
	-  amount: Text 


A project contain 2 controllers.
1.	Login Controller
2.	Statement Controller

1.	Login Controller
Login user with below credentials
Admin
Username: admin
Password: admin

User
Username: user
Password: user
2.	Statement Controller
Download statement of any user either for admin or user.
For Admin:
	Admin can download report with all the filters mentioned above
For User:
	User can download report of last 3 month only
	
![image](https://github.com/sharjeelzaazmi1/naggaro-test-sharjeel/assets/130983554/7b39b345-aeb7-49ae-8f44-92ad3131daea)
