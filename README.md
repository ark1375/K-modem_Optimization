# ModelSA

Welcome to Model-SA.   

The code before you is a simple solution for **K-Modem Problem Illumination** (Problem description available below) and it is the result of two students' bachelor's project (Check the contributors).  
The code itself is written in __Java__ (Requirements are listed below) and it is heavily dependent on the JTS geometry library.   

This repository contains   
- The Projects Source   
- Documentation and Screenshots   
- Test Cases   
- A Randomly generated dataset of simple geometries   

**Warning!**   
The following readme file **Dose Not** contain detailed information about the problem.   
For further information on technical details of the problem, experiments and theire results, check out [Technical Details](TechincalDetails.md)

## Table of Contents
* [Problem Description](#problem-description)
* [Requirements](#requirements)
* [Installation](#installation) 
* [A tour of the code](#a_tour_of_the_code) 
* [How to use?](#how_to_use?)
* [Credits](#credits)  

## Problem Description
#### Original Description
The K-Modem problem is actually not a stand-alone problem itself. It is a variation of an old, computational geometry problem called **Art Gallery Illumination Probelm** that consists on _finding the minimum number of light sources needed to illuminate a simple polygon (eg. map of a gallery)_<sup>[1](#credits)</sup>.    
One of the famous variations of the problem is **K-Modem Illumination Problem**.  
For a non-negative number _k_, a _k-modem_ is a wireless deice that can penetrate _k_ "walls". Let _L_ be a set of _n_ line segments (or lines) in the plane. A k-modem __illuminates all__ points _p_ of the plane such that the interior of the line segment joining p and the k-modem intersects at most _k_ elements of _L_. In general, k-modem illumination problems consist on finding the minimum number of k-modems necessary to illuminate a certain subset of the plane, for a given L.  
#### A subset of the K-Modem Problem
_**Note**: This is a subset of the original K-Modem illumination problem and it is the actual problem that the code is written for. With little adjustments (check [Technical Details](TechincalDetails.md/#problem_description)) the code can solve the original problem as well._
Let _P_ be a simple polygon consist of an outter shell (the outter walls) and zero or more holes (check the screenshots). Given the number of k-modems available _q_, and the penetration rate _k_, what are the best cordinates to put the k-modems on, such that the maximum area of the polygon is covered.   
If we asume that there is signal-confliction between k-modems such that for a random point _p(x,y)_
## Requirements

## Installation

## A Tour of the Code

## How to use?

## Credits

## Sources