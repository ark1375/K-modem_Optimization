# ModelSA

Welcome to Model-SA.   

This project is a simple solution for the **K-Modem Illumination Problem**.<br>
This soloution uses Monte Carlo technique alongside with Genetics Algorithm for solving the problem.<br>
The code itself is written in __Java__ (Requirements are listed below) and it is heavily dependent on the JTS geometry library.   

This repository contains   
- The Projects Source   
- Documentation and Screenshots   
- Test Cases   
- A Randomly generated dataset of simple geometries   


> :warning: **Warning~**<br> The following readme file **Dose Not** contains detailed information about the problem.<br>
For further information on technical details of the problem, experiments, and their results, check out [Technical Details](TechincalDetails.md).

## Table of Contents
* [Problem Description](#problem-description)
* [Requirements](#requirements)
* [Installation and Setup](#installation_and_setup) 
* [How to use?](#how_to_use)
* [Credits](#credits)  

## Problem Description
### Original K-Modem Description
For the original description of the K-Modem problem, check out the [Technical Details](TechincalDetails.md#problem_description).

### This Variation of K-Modem Problem
_**Note**: This is a subset of the original K-Modem illumination problem and it is the actual problem that the code is written for. With little adjustments (check [Technical Details](TechincalDetails.md#problem_description)) the code can solve the original problem as well. The solution code for the original K-Modem Illumination problem will be included in future updates._  
  

Let **_P_** be a simple polygon consist of an outer shell (the outer walls) and zero or more holes (check the screenshot below). Given the number of k-modems available **_q_**, and the penetration rate **_k_**, what are the best coordinates to put the k-modems on, such that the maximum area of the given polygon is covered<br>
Another custom variation of the problem comes from the assumption that the k-modems can have signal confliction. If any point is covered by more than one k-modem, signal confliction happens and the point is not covered anymore.   
![sc1](/docs/screenshots/sc1.jpg)

### Practical Usage
The main practical usage of the problem comes from finding the optimal positions of a set of wireless modems with given signal strength in a given map for the maximum coverage of the signal (eg. Maximum WiFi coverage inside a given building).
Using the available code alongside JTS Testbuilder, you can create your own maps and find the optimal position for your modems inside the map (check out [How to Use?](#how_to_use?)).


## Requirements
_Having knowledge of Java for running and simple use of the code is not essential though recomended_  

1. **JDK 8 (Or Higher)**<br>
*Warning!* You need **JDK (Java Development Kit)** for building and running the project.<br>DO NOT INSTALL JRE (Java Runtime Environment).  
2. **Maven 3.6 (Or Higher)**  

3. **Git and Git Bash**  

4. **NetBeans 8 (Or Higher)** _Recomended_   

5. **Clone JTS Repository** _Recomended_  

## Installation and Setup
_**Note:** This installation and setup guide is for Windows operating system. Future updates may include support for Linux._<br><br>
If you are new to writing and building programs, make sure to follow every step of the installation and setup. It will cover **ALL** the things you need to do to run the code.<br>
**Setup the Project, Section 4** is about building JTS and using its TestBuilder tool for creating your own test cases. You can skip it if you are not interested. 
### Installation Guide<br>  

1. **Installing Git**  
    Simply download and install Git from [here](https://git-scm.com/downloads). When you are installing git, in the *Select Component* window, make sure to check `Git Bash Here` under the *Windows Explorer integration* tab. Other than that, use the installation's recommended settings and you're good to go.<br><br>


2. **Installing Maven**  
    - Download Maven's **Binary zip archive** from [here](https://maven.apache.org/download.cgi). Extract the zip file in an arbiturary directory (for example your Program Files folder in Windows partition).
    
    - Now you need to add the Maven's bin folder to your windows path environment variables.
        * In the extracted Maven directory, copy the bin folder's path (ie. `C:\Program Files\apache-maven-3.8.1\bin`).
        * Press `WIN + R` to open up *Run* and type in `SystemPropertiesAdvanced`.
        * In *System Properties* window, click on *Environment Variable*.
        * From the _System Variable_ section, click on _Path_ and then click on _Edit_.
        * In the pop-up window, click on _New_ and paste the path that you just copied and click _OK_ to make the changes. 

    - Open up a command prompt and use `mvn --version`.
It must show something like this:<pre>
<code>Apache Maven 3.6.3 (--------------some code--------------)
Maven home: C:\Program Files (x86)\apache-maven-3.6.3\bin\..
Java version: 15.0.1, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-15.0.1
Default locale: en_US, platform encoding: ---- some code ----
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
</code></pre>Congratulations, you just installed Maven.<br><br> 


3. **Installing JDK**  

    - Head to [Oracles Website](https://www.oracle.com/java/technologies/javase-downloads.html) and download JDK.
    
    - Run the installation. Use the recommended settings for installation. Just remember the directory that you are installing JDK in.
    - When the installation is over, follow [these](https://javatutorial.net/set-java-home-windows-10) instructions to add **JAVA_HOME** to your environment variables.
    - Open a command-line prompt and type in `java -version`. It must show something like this:<pre>
<code>java version "1.8.0_261"
Java(TM) SE Runtime Environment (build 1.8.0_261-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.261-b12, mixed mode)
</code></pre>
    - If you encounter any errors, follow [these](https://www.javatpoint.com/how-to-set-path-in-java) instructions.<br><br>
    
    
4. **Installing NetBeans** _optional_  
Installing the NetBeans is optional but recommended. Download and install NetBeans using [this link](https://netbeans.apache.org/download/index.html).<br>

    **_Note:_** While installing the NetBeans, you will encounter a field in which you need to pass in the directory you installed JDK in. If you installed JDK correctly, this field should be filled automatically. If not, close the setup, follow the instructions provided in the last paragraph of _Installing JDK_ and run the setup again.
    <br><br>

### Setup the Project<br>  
After installing all the requirements, you can begin setting up the project
Now that we installed all the requirements of the project, it's time to set them up.<br>
1. **Clone the Repository**  

    - Create an empty folder on your computer and navigate inside it.
    - Right Click and chose __Git Bash Here__.
    - Execute `git clone https://github.com/ark1375/model_SA.git` in the Bash Prompt.<br>
    Git make's a clone of the repository in your local machine.<br>

2. **Using Maven to Install Dependencies**  _Skip this part if you are using NetBeans_    

    - Inside the local repository, you can find a **pom.xml** file. This is the Maven's pom file which specifies details of the project including build options and most importantly, dependencies.
    - Open a command prompt (cmd) and change directory to the local repository. 
    - Type in `mvn clean install`. Using this command, Maven handels the dependencies you need to run the project.
    
3. **Configuring NetBeans**  _Skip this part if you are using Maven_ <br>
NetBeans should automatically recognize the repository as a _Project Directory_. Using `Ctrl + Shift + O` you can open the repository directly inside NetBeans.<br><br>
**Warning!**<br> _If you are using an older version of NetBeans (version 9 or bellow) you may want to update NetBeans Maven._<br><br>
For updating NetBeans Maven:
    - Download the Maven's Bin files as explained in **Installing Maven** section one. 
    - Extract it and copy all the files inside the `apache-maven-version` folder.
    - Navigate inside NetBeans installation directory and open the `Java` folder.
    - Inside the `maven` folder, paste and replace everything that you copied.
    
4. **Clone and Build JTS** _optional_<br>
In order to use JTS TestBuilder to create your own polygons and geometries, you need to clone and build JTS libreary.<br>
    - Create another empty folder and open GitBash prompt as explained previously.
    - Type in `git clone https://github.com/locationtech/jts.git` and hit enter.
    - Navigate inside the cloned repository using `cd` command (`cd jts`).
    - Type `mvn clean install` to install dependencies and build JTS.
    - Use `java -jar modules/app/target/JTSTestBuilder.jar` to run JTS TestBuilder.<br><br>
    Head to [JTS Repository](https://github.com/locationtech/jts) for more information about JTS geometry library.<br>

## How to Use?<br>
Now that you setedup everything you need, it's time to learn how to run and how to properly use the code. A little tour of the code will be a great place to start.
### A Tour of the Code<br>
_**Note**: Reading this section is optional, skip to **How To Use** if not interestead._<br>
I won't go deep into details of the code (for that, refer to [Technical Details](TechincalDetails.md#problem_description)) but I will give you enough information to be able to understand the basic idea of it.<br>
I will begin with explaining the important classes.<br>
* __The Polygon Class__  
    As the name implies, Using this class you can import your polygons into the program. Note that the programs design is based  on **Importing the Polygons** and not to create them on sight using code or GUI.<br><br>
You can create polygons in two ways.  

    - Using empty constructor to build an object and reading the poygon later.
        ```java
        Polygon pl = new Polygon();
        pl.readPolygonXML(path);
        ```
    - Passing path directly to the constructor.
        ```java
        Polygon pl = new Polygon(path);
        ```
    Polygon class can read XML files created by JTS TestBuilder. It is also compatible with WKT files. But in order to take advantage of WKT you need to create an empty object and use `readPolygonWKT` method to read WKT directly (check out the box bellow).<br>
    ```java
    Polygon pl = new Polygon();
    pl.readPolygonWKT(path);
    ```
    
    Future updates may include support for common CAD file formats like .DXF or .DWG.


* **The Moedem Class**  
You won't need to use this class directly. Just know that this is K-Modem class. Every modem will have a Peneteration Rate (_k_) and a 2D cordinate (_x_ and _y_).  

* **VPCalculator Class**
This static class contains algorithms for calculating the visiblity areas of modems (where there is signal coverage).<br>
You won't be using this class directly as well.

* **GeneticsAlgorithm Class**  
This is the heart of the project. GeneticAlgorithm is the class that handles everything. After importing a polygon into the program, you have to use this class to pass in your desiered parameters and find the optimal solutions. It will do so by creating a population of arbitary size and run **Genetics Algorithm** on them.<br>
As you are creating an object, the parameters that you'll need to pass to the constructor are listed in order:
    - The Polygon
    
    - Number of Modems
    
    - Default K Value<br>   Peneteration Rate of Signals, (_how many walls signals can pass thorugh_)
    
    - Monte Carlo Itterations<br>   Because the program is using a method called Monte Carlo Method to estimate the signal coverage of the modems, you need to determain the number of itterations for the method. Obviouslt, higher number of itterations will lead to higher precisions but at the cost of system resources.<br> **Set this number somewhere between 1000 and 10000 based on your CPU power.**
    
    - Size of Population<br>
    This is the population that you'll be running genetics algorithm on. Higher values will lead to faster convergance and higher precissions but proceed with caution. Setting this number too high will consume an enormous amount of system power.<br> **Set this number somewhere between 100 and 1000 based on your CPU power.**
    
    - Mutation Rate<br>
    This will determain how many indiviuals in the population will Mutate in every generation. You have to pass in a number between 0 and 1.
    For example if you have a mutation rate of 0.1, in every generation, 10% of the population will be mutated randomly.
    
    - Generations<br>
    This parameter will detrmin the number of itterations for genetics algorihthim. Obviously, higher number of itterations will lead to more acurate result but at the price of time.<br> **Set this number between 25 and 100 based on your system power and your own patiance.**
    
    Now that you are familiar with the parameters, you can create objects like this:
    ```java
    GeneticsAlgorithm gna;
    gna = new GeneticsAlgorithm( poly, numOfModems, k , MCItter, popSize, mutRate, numOfGens );
    ```
    After creating your object, use `runGenetics` method to run the algorithim.
    ```java
    GeneticsAlgorithm gna;
    gna = new GeneticsAlgorithm( poly, numOfModems, k , MCItter, popSize, mutRate, numOfGens );
    gna.runGenetics();
    ```
    For getting the output of the algorithim, there are several `get` methodes provided in GeneticAlgorithm class for convinient use.<br>
    `gna.getPopulation()` will return an ArrayList of the population. Each element of ArrayList is a **Chromosome** which contains an array of K-Modems. You can use a code like `gna.getPopulation().get(someIndex).modemList` to have access to the ArrayList of K-Modems.<br>
    Also you can use `gna.getBestGene()` to directly access the best gene availabe. This will return an array of K-Modems.
    One additional option is `gna.getTopTenResults()` which returns a 10 size ArrayList of best K-Modem Arrays.<br><br>
    _**Note:** Future updates will include a faster algorithims for calculating modem coverage. This will increase total speed of program considerably._
    
### How to Use<br>


For each itteration of the algorithim, you will have something printed out in your console.
```
Itteration: 1
Crossover Started
Crossover Done 
Mutation Started
Mutation Done 
Selection Started
Selection Done 
BCF: 0.996000
```
BCF shows the Best Chromosome Fitness which is the score of the best indiviual of the population in the current run. The score is between 0 and 1 which shows the signal coverage inside the polygon. For example, in the previous print box, BCF is 0.996. That means the best indivual of the population (which is just a set of cordinates for the modems) has 99.6% signal coverage. In another words, if you put your modems acording to the best indivual, you will have 99.6% coverage.<br>
Now the question rises, how can you see these best cordinates?<br>
There are series of _get_ methods included in the GeneticsAlgorithm class that you can use to get these cordinates.<br>
One example is the `getPopulation()` method which returns an ArrayList of the chromosoms (whole population). This ArrayList will always be in order of best to worst, meaning the 0 index is the best of all. The code below is one example of using this method to your advantage.
```java
System.out.println(gna.getPopulation().get(0));
```
This will print out the best indivuals genes which is the cordinates of the modem.







## Credits

## Sources