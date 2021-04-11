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
* [Installation and Setup](#installation_and_setup) 
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
Because some essential part of the code is written using newer features of Java, installing JDK8 or higher is a requirement for running the project.<br><br>*Warning!* You need **JDK (Java Development Kit)** for building and running the project.<br>DO NOT INSTALL JRE (Java Runtime Environment).  
2. **Maven 3.6**  
Having Maven around is Recommended. With Maven installed, you don't need to worry about dependencies and package installations. Also, the installation guide in this readme is directly using Maven for building the project.

3. **Git and Git Bash**  
Download and install the latest version of Git Bash for convenient use of Git.

4. **NetBeans 8 (Or Higher)** _Recomended_  
The project itself is written using NetBeans so for convenient use, it is recommended to have NetBeans installed as well.  
However, you need to make a little modification to your NetBeans to build the project directly. More on that in the Installation section.  

5. **Clone JTS Repository** _Recomended_  
_Cloning_ and _Building_ the JTS repo is recommended though not necessary. After building JTS, You can use the **JTS TestBuilder** to create your own test cases for running the code. Find out more about it in the installation section.  

## Installation and Setup
_**Note:** This installation and setup guide is for Windows operating system. Future updates may include support for Linux._<br><br>
If you are new to writing and building programs, make sure to follow every step of the installation and setup. It will cover **ALL** the things you need to do to run the code.<br>
The +++++ part of the guide is on building JTS and using its TestBuilder tool for creating your own test cases. You can skip that if you are not interested in that. 
### Installation Guide<br>  

1. **Installing Git**  
    Simply download and install Git from [here](https://git-scm.com/downloads). When you are installing git, in the *Select Component* window, make sure to check `Git Bash Here` under the *Windows Explorer integration* tab. Other than that, use the installation's recommended settings and you're good to go.<br><br>


2. **Installing Maven**  
    - Download Maven's **Binary zip archive** from [here](https://maven.apache.org/download.cgi). Extract the zip file in a secure location (for example your Program Files folder in Windows partition).
    
    - Now you need to add the maven's bin folder to your windows path environment variables.
        * In the extracted maven directory, copy the bin folder's path (ie. `C:\Program Files\apache-maven-3.8.1\bin`).
        * Press `WIN + R` to open up *Run* (`WIN` is the windows key on the keyboard) and type in `SystemPropertiesAdvanced`.
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

    - Head to [Oracles Websit](https://www.oracle.com/java/technologies/javase-downloads.html) and download JDK.
    
    - Run the installation. It must be pretty straightforward. Use the recommended settings for installation. Just remember the directory that you are installing JDK in.
    - When the installation is over, follow [these](https://javatutorial.net/set-java-home-windows-10) instructions to add **JAVA_HOME** to your environment variables.
    - Open a command line prompt and type in `java -version`. It must show something like this:<pre>
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
Now that we installed all the requirements of the project, it's time to set them up.<br>
1. **Clone the Repository**  

    - Create an empty folder anywhere in your computer and navigate inside it.
    - Right Click and chose __Git Bash Here__.
    - In the opened up shell prompt, type in `git clone https://github.com/ark1375/model_SA.git` and hit enter.<br>
    Git will download the repository from the github server.<br>

2. **Build the Project using Maven**  

    - 

## A Tour of the Code

## How to Use?
Future updates may include support for common CAD file formats like .DXF or .DWG.
## Credits

## Sources