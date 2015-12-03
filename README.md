Remote Arduino in Java.  
  
The idea is to make an interface (RemoteClient) able to controll arduino over serial communication (ArduinoClient) through a server program (Server). The code can be easily adjusted to any new functionalities.   
  
Requirements:  
1. JDK 1.8 or above.  
2. [RXTX for Java](http://fizzed.com/oss/rxtx-for-java) installed in your $JAVA_HOME  
3. [Maven](https://maven.apache.org) to build from the source  
  
From the root directory:  
1. mvn package  
2. java -jar Server/target/Server-1.0-SNAPSHOT-jar-with-dependencies.jar [port]  
3. java -jar ArduinoClient/target/ArduinoClient-1.0-SNAPSHOT-jar-with-dependencies.jar [host] [port]  
then choose a serial port from the list  
4. java -jar RemoteClient/target/RemoteClient-1.0-SNAPSHOT-jar-with-dependencies.jar  
(change target host and port in Controller class)  
  
Current sample:  
  
Arduino:  
LED diode - digital port 8  
Buzzer - digital port 9  
Servo - digital port 10  
  
Controlls in RemoteClient:  
LED diode - radio button  
Buzzer - enter key  
Servo - left/right keys  
