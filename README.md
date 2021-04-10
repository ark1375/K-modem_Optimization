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

1. **JDK 8 (Or Higher)**   
Because some essential part of the code is written using newer features of Java, installing JDK8 or higher is a requirement for running the project. 

**Warning!**  
You need **JDK (Java Development Kit)** for building and running the project.  **DO NOT INSTALL JRE (Java Runtime Environment)**.   
2. **Maven 3.6**  
Having Maven around is Recommended. With Maven installed, you don't need to worry about dependencies and package installations. Also, the installation guide in this readme is directly using Maven for building the project.

3. **NetBeans 8 (Or Higher) _Recomended_   
The project itself is written using NetBeans so for convenient use, it is recommended to have NetBeans installed as well.  
However, you need to make a little modification to your NetBeans to build the project directly. More on that in the Installation section.

## Installation

## A Tour of the Code

## How to Use?
Future updates may include support for common CAD file formats like .DXF or .DWG.
## Credits

## Sources