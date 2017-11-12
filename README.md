__Warby Parker Take Home Test__

This is an implementation of Warby Parker's Take Home exam. For the detailed requirements, the readers are suggested to read the accompanying *SoftwareEngineer* mark down file.

The project requires Java 8 to compile and execute the program, and  Maven to run the unit tests and to create a java archive.

The steps to execute the program with maven are given below.

* Navigate to the root of the project directory
* Execute the following command `mvn clean package && java -jar target/assignment-1.0-SNAPSHOT.jar` and press the return key.
* Follow the instructions as given in **Input Format** section of `SoftwareEngineering` mark down file.

To execute the program without using Maven, the following steps are recommended.

* Navigate to the root of the project directory
* Execute the following command `cd src/main/java' to navigate to the root of the java package.
* Compile the Java source code with the following command `javac com/warbyparker/*.java`
* Execute the java binary class file with the following command `java com.warbyparker.WarbyParker`

The projects uses TestNG to execute the unit tests. The dependency scope is `test only` and leverages Maven to download and install the package. The dependency is categorized in the `pom.xml`.
