# The Solution
The solution provided here for the problem statement is designed based off of the API specifications provided in the 
api.yml file. The development is done in Java (JDK 17) and was functionally tested using Postman. There are unit tests 
written for the primary controller class, which serve as a proof of standard practices being followed. Unit tests were 
not written for the services as I wanted to submit this for review as soon as possible.

# Instructions for Execution
JDK 17 (minimum) is required in the environment to run this service. If Java is not installed in your environment, instructions 
to do so can be found in the links provided below.
1. [Linux](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-linux-platforms.html#GUID-79FBE4A9-4254-461E-8EA7-A02D7979A161)
2. [MacOS](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-macos.html#GUID-E8A251B6-D9A9-4276-ABC8-CC0DAD62EA33)
3. [Windows](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html#GUID-A7E27B90-A28D-4237-9383-A58B416071CA)

The installation can be verified by running `java -version` in the terminal, which should show you the current Java version
Once verified, open navigate to the directory with the `build.gradle` file and run the following commands:
* To build `./gradlew build`
* To run `./gradlew run`

Once the services are running, it can be tested in localhost.

# About
Completed and submitted by Mayan Iyer (apremeyan.iyer@gmail.com) 