# ModelSA

Welcome to Model-SA.   

This project is a simple solution for the **K-Modem Illumination Problem**.  
We used the Monte Carlo technique alongside with Genetics Algorithm for solving the problem.  
The code itself is written in __Java__ (Requirements are listed below) and it is heavily dependent on the JTS geometry library.   

This repository contains   
- The Projects Source   
- Documentation and Screenshots   
- Test Cases   
- A Randomly generated dataset of simple geometries   

**Warning!**   
The following readme file **Dose Not** contains detailed information about the problem.  
For further information on technical details of the problem, experiments, and their results, check out [Technical Details](TechincalDetails.md).

## Table of Contents
* [Problem Description](#problem-description)
* [Requirements](#requirements)
* [Installation](#installation) 
* [A Tour of the Code](#a_tour_of_the_code) 
* [How to use?](#how_to_use?)
* [Credits](#credits)  

## Problem Description
### Original K-Modem Description
For the original description of the K-Modem problem, check out the [Technical Details](TechincalDetails.md#problem_description).

### This Variation of K-Modem Problem
_**Note**: This is a subset of the original K-Modem illumination problem and it is the actual problem that the code is written for. With little adjustments (check [Technical Details](TechincalDetails.md#problem_description)) the code can solve the original problem as well. The solution code for the original K-Modem Illumination problem will be included in future updates._  
  

Let **_P_** be a simple polygon consist of an outer shell (the outer walls) and zero or more holes (check the screenshot below). Given the number of k-modems available **_q_**, and the penetration rate **_k_**, what are the best coordinates to put the k-modems on, such that the maximum area of the given polygon is covered.   
Another custom variation of the problem comes from the assumption that the k-modems can have signal confliction. If any point is covered by more than one k-modem, signal confliction happens and the point is not covered anymore.   
![sc1](/docs/screenshots/sc1.jpg)

### Practical Usage
The main practical usage of the problem comes from finding the optimal positions of a set of wireless modems with given signal strength in a given map for the maximum coverage of the signal (eg. Maximum WiFi coverage inside a given building).
Using the available code alongside JTS Testbuilder, you can create your own maps and find the optimal position for your modems inside the map (check out [How to Use?](#how_to_use?)).


## Requirements
_Having knowledge of Java for running and simple use of the code is not essential though recomended_  
  
1. **JDK 8 (Or Higher)**   
Because some essential part of the code is written using newer features of Java, installing JDK8 or higher is a requirement for running the project. 

    *Warning!*  
    You need **JDK (Java Development Kit)** for building and running the project.  
    DO NOT INSTALL JRE (Java Runtime Environment).   
2. **Maven 3.6**  
Having Maven around is Recommended. With Maven installed, you don't need to worry about dependencies and package installations. Also, the installation guide in this readme is directly using Maven for building the project.

3. **Git and Git Bash**  
Download and install the latest version of Git Bash for convinient use of Git.

4. **NetBeans 8 (Or Higher)** _Recomended_  
The project itself is written using NetBeans so for convenient use, it is recommended to have NetBeans installed as well.  
However, you need to make a little modification to your NetBeans to build the project directly. More on that in the Installation section.  

5. **Clone JTS Repository** _Recomended_  
_Cloning_ and _Building_ the JTS repo is recomended though not neccesary. After building JTS, You can use the **JTS TestBuilder** to create your own test cases for runinng the code. Find out more about it in the installation section.  

## Installation and Setup
**_Note:_** This installation and setup guide is for Windows operating system. Future updates may include support for linux.  
If you are new too writing and building programs, make sure to follow every step of the installation and setup. It will cover **ALL** the things you need to do to run the code.  
The +++++ part of the guide is on building JTS and using it's TestBuilder tool for creating your own test cases. You can skip that if your not interestead in that. 
### Installation  

1. **Installing Git**  
Simply download and install Git from [here](https://git-scm.com/downloads). When you are installing git, in the *Select Componenet* window, make sure to check `Git Bash Here` under *Windows Explorer integration* tab. Other than that, use the installations recomended settings and you're good to go.  

2. **Installing Maven**  
    -   Download Maven's **Binary zip archive** from [here](https://maven.apache.org/download.cgi). Extract the zip file in a secure loaction (for example your Prograrm Files folder in Windows partition).  
    -   Now you need to add the mavens bin folder to your windows path envoirment variables.
        * In the extracted maven directory, copy the bin folder's path (for example `C:\Program Files\apache-maven-3.8.1\bin`).  
        * Press `WIN + R` to open up *Run* (WIN is windows key on the keyboard), type in `SystemPropertiesAdvanced` and hit Enter.  
        * In *System Properties* window, click on *Envoirment Variable*.
        * From the _System Variable_ section, click on _Path_ and then click on _Edit_. 
        * In the pop-up window, click on _New_ and paste the path that you just copied and click _OK_ to make the changes.  
    -   Open up a command prompt and use `mvn --version`.
It must show something like this:<pre>

<code>Apache Maven 3.6.3 (--------------some code--------------)
Maven home: C:\Program Files (x86)\apache-maven-3.6.3\bin\..
Java version: 15.0.1, vendor: Oracle Corporation, runtime: C:\Program Files\Java\jdk-15.0.1
Default locale: en_US, platform encoding: ---- some code ----
OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"
</code></pre>
    -   Congratulations, you just installed Maven.

3. **Installing JDK**  
```
s60e c6de
```

4. **Installing NetBeans** _optional_  

### Setup the Project

## A Tour of the Code

## How to Use?
Future updates may include support for common CAD file formats like .DXF or .DWG.
## Credits

## Sources