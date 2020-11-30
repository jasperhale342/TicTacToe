## About
This is the repo housing Tic Tac Toe created for the SENG 350 project at the University of Victoria in Fall 2020.

## Using Gradle
To clean this project, run the following command at the root of the repository:
```
./gradlew clean
```

To build this project (compilation, etc), run the following command at the root of the repository:
```
./gradlew build
```

## Running the project
Running this project first requires that the project be built and after that, the class files generated for all the subprojects (execpt Main) 
need to be transferred to `./Main/build/classes/java/main`. This can be done with `gradle` and `cp` manually but a script for this has been added, called `build_project`. It can be found at the root of the project.

So, to run the project, first run:
```
./build_project
```
Then, navigate to `./Main/build/classes/java/main` and run the following:
```
java Main
```