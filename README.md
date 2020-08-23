Download and unzip my application
Download any Java Ide like IntelliJ. You can download IntelliJ here: https://www.jetbrains.com/idea/download
Install Intellij and open it 

Click on import project and select my application i.e. Transaction Processor.
Next select import project from external model and in that select Maven and click Finish. 

On top left hand side click on project and go to src/main/java/com/krishna/TransactionProcessor/TransactionProcessorApplication.java
On top there will be an option to setup SDK, click on that and select any Java version present.
Double click anywhere and click on Run TransactionProcessor.. or ctrl+shift+R. This will start the service easy peasy!
Open the terminal and call the service.

for example, You can run this commands: 
1)curl -X POST -H "Content-Type: application/json" -d @transactions.json http://localhost:5000/transactions
2)curl "http://localhost:5000/accounts?accountId=ACT100&accountId=ACT300" (Please use double inverted commas while calling this endpoint if you want to pass multiple ids)
