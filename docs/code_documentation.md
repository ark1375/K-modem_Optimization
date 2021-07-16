
# Code Documentation

In this document, you can find detailed explaination on the code itself.  

There are a total of eight classes in _ku.cs.model.sa_ package (except main) which are the main logic of the program and three classes in _ku.cs.model.sa.graphics_ which provide some basic graphic for the logic (_note that this part is still in development phase_).  
  
__Logic:__  

- Modem
- Polygon
- VPCalculator
- GA_Config
- OW_Config
- GeneticsAlgorithm
- OptimizedWalk
- Util  

__Graphic:__

- PlotFrame
- PlotPanel
- Plotter
  
We go through each class one by one, explaining it's functionality and providing information on importatnt attributes and methods of each class.

## Logic Workflow

The logic of the program can be takean apart into three different sections.

1. The __Data Structure__ section which basically provide the classes needed to create suitable input/output objects for the algorithms. These classes are *Modem* , *Polygon* , *GA_Config* and *OW_Config*.  
2. The __Algorithm and Optimization__ which carry the main functionality of the program and consist of classes *VPCalculator* , *GeneticsAlgorithm* and *OptimizedWalk*.
3. The **Util** class in which you can find static methods for different uses such as *Benchmarkingthe algorithms* and *Solving K-Modem Probelm*.
  
There are two ways in which the program can be used.

- The first way, is to find optimal positions of k-modems for given number of _k-modems_ and peneteration rate _k_ in a polygon _p_.  
- The second way is to use the static methods provided in _Util_ class to either verify the concluded results in the article or to solve the K-Modem problem for a given polygon.  

The workflow for both aproaches are shown in the diagram bellow.
![Image1](./screenshots/diagram3.jpg)

### First Aproach

### Second Aproach

## Logic Classes

- [__Modem__](#Modem)
- [__Polygon__](#Polygon)
- [__VPCalculator__](#VPCalculator)
- [__GA_Config__](#GA_Config)
- [__OW_config__](#OW_Config)
- [__Genetics Algorithm__](#GeneticsAlgorithm)
- [__OptimizedWalk__](#OptimizedWalk)
- [__Util__](#Util)

### Modem

### Polygon

### VPCalculator  

### GA_Config  

### OW_Config  

### GeneticsAlgorithm  

### OptimizedWalk  

### Util  

## Graphics  

### PlotFrame

### PlotPanel

### Plotter
