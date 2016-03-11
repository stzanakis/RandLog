# RandLog
RandLog is a java executable to generate random logs, or trigger url links using multiple threads and log4j2.
Timing is set accordingly from the input arguments.


###Building ###
`mvn clean package`  
After building under target directory there would be the executable.jar and a directory conf for dynamic configuration.
Copy this two to the location that the application will be executed.

###Running ###
Edit the log4j configuration file under conf directory accordingly.  

`java -Dlog4j.configurationFile=conf/log4j2.xml -jar RandLog-0.0.1-SNAPSHOT-executable.jar`
The above defaults on the input arguments: -m log -e true -t 5 -l 1000 -h 2000 -f url-list.txt

Otherwise the application can run with providing the arguments, if not arguments it will failover to the default values.
`java -Dlog4j.configurationFile=conf/log4j2.xml -jar RandLog-0.0.1-SNAPSHOT-executable.jar -m url -e false -t 5 -l 1000 -h 5000 -f /tmp/url-list.txt`