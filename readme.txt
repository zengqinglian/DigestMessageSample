Assumption:
To simply some of design, I made below assumptions
1. The main purpose of this practice is design the message/event notification. The spring MVC controller and end points design is out of scope. So the controller in the code is very simple.
2. Assume the trade message is immutable once we received it. The fields of message can not be null. Assume all fields will be PK of each message.
   Message validation is based on assumption and might not match reality.
3. In case of no MQ set up, I created a simple producer and consumer to mock the message generation and consumption.
4. producer and consumer design is out of scope. 
5. dealing with failed message is out of scope.
6. Logger and log file generation is out of scope.
7. Spring boot or spring integration test are out of scope.
8. Spring MVC exception handler design is out of scope.
9. Avg price is weighted avg price.


Run project
This is a spring boot project.
The easiest way to run the project is import project as a maven project into STS IDE.

How to run spring boot project can be found from
https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html


Test end point
http://localhost:8080/getTradeStatistics?symbol=XYZ%20LN


Improvement
1. Make project support gradle and maven, add maven and gradle warpper.
2. add sonar to monitor code quality and code test coverage.
3. test coverage and test cases can be improved.
4. Use concurrentHashMap as cache. When trade data is big, system might run out of memory.
5. When there are too many notifier, synchronized notification will be slow. But if use asynchronized notification, we need deal with failed trade messages.
6. Controller should be rewriten. Exception handler need to be added.
7. As always, the design and infrastructure can be discussed. 
8. The format of numbers can be formalized. use jackson mapper or customize JsonSerializer.  Keep two decimal.