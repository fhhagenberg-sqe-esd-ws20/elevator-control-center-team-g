# Elevator Control Center

### State

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-g&metric=alert_status)](https://sonarcloud.io/dashboard?id=fhhagenberg-sqe-esd-ws20_elevator-control-center-team-g)

### **Members:**

- Egger Tobias
- Schuch Jakob
- Würflinger Patrick

### **Download **

````shell
git clone https://github.com/fhhagenberg-sqe-esd-ws20/elevator-control-center-team-g.git
cd elevator-control-center-team-g
````

### Instructions

This maven project is already set up for JavaFx based GUI applications. It also contains a small example application - `App.java`.

1. Import this git repository into your favourite IDE.

1. Make sure, you can run the sample application without errors.
	- Either run it in your IDE
	- Via command line, run it with `mvn clean javafx:run`.

You can build your project with maven with

```
mvn clean package
```

The resulting archive (`.jar` file) is in the `target` directory.

### Test concept

For testing our application we used unit tests in general. We splitted it up into different modules. These include:

- Automated gui tests with the TestFx framework
- End-To-End tests from the Model to the user interface and vice versa
- Controller tests to verfiy the correct behaviour of the logic functionality
- View-Model tests to verify that our model passes the right values to the gui
- Reconnection tests to verify that our application has a stable connection to the RMI

For testing our application without the real interface to access the RMI, we implemented a mock interface.  To ensure the quality of our application during development we also integrated and used the static code analysis tool sonar cloud.