# RandLog
RandLog is a java executable to generate random logs using multiple threads and log4j2.
Timing is set accordingly from the input arguments.


###Building ###
`mvn clean package`  
After building under target directory there would be the executable.jar and a directory conf for dynamic configuration.
Copy this two to the location that the application will be executed.

###Running ###
Edit the log4j configuration file under conf directory accordingly.  

`java -Dlog4j.configurationFile=conf/log4j2.xml -jar RandLog-0.0.1-SNAPSHOT-executable.jar`  
The above defaults on the input arguments: true 10 1000 2000

Input arguments are "boolean(enabled exceptions?)" "numberOfThreads" "lowCap in millisecs" "highCap in millisecs"

Otherwise the application can run with providing the arguments, if not 4 arguments it will failover to the default values.  
`java -Dlog4j.configurationFile=conf/log4j2.xml -jar RandLog-0.0.1-SNAPSHOT-executable.jar true 5 1000 2000`