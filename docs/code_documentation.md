
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
_
The workflow for both aproaches are shown in the diagram bellow.
![Image1](./screenshots/diagram3.jpg)

### First Aproach, Manual Optimization

For manulay using the optimization algorithms, the user is asked to provide a configuration file based on the algorithm and an input polygon to perform optimization on.  
The user can use the `polygon` class directly to import the polygon into the program. Currently there is no way to create the polygon on sight. Supported file formats for the polygon calss are __wkt__ and JTS __XML__ polygon file which can be used to import simple or complex polygons.  
The configuration onject has to be created based on the optimization algorithm. In this configuration object, the parameters required for runnig the optimization are saved. These parameters can be divided into tow category. __Problem parameters__ provide constraints of the problem which are _number of modems_ , _peneteration rate_ and _number of allowed collision_. __Optimization Parameters__ are used to provide constratints for the algorithms them self. These parameters are explained thorughly for each algorithm later on in the docs.  
After running the optimization, the user can use the provided methods in each optimization class to retrive the result of the run.

### Second Aproach, Benchmarks and K-Modem Solver

Each benchmarking mehtod is explained throughly in it's own section later on.  
The K-Modem solver requires a polygon, a genetic optimization config, a optimized walk optimization config some and additional parameters which is explained later on.  
The method then uses these parameters to loop through optimizing the polygon for different number of modems. For `numberOfModems = 1` the Optimized Walk is used as it is suited to optimize the position of one modem. For `numberOfModems > 1` genetics optimization is used instead.  
After reaching 100% coverage (or the threshold provided by user) the algorithm is halted and an ArrayList of agents (genes) is returend for each number of input modems. The user can later use this ArrayList to retrive the modem's positions.

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

#### Explanation

The Modem class is the implementation of K-Modems. It hold's a coordinate and a peneteration rate _k_.
> For the most part, creation of the modems are handled by the logic itself.  

#### Constructor

- ##### `Modem()`

  Empty constructot.  
  Default coordinate and default peneteration rate is loaded inside the object.  
  __Default Coordinate:__ `X = 0` and `Y = 0`  
  __Default Peneteration Rate:__ `K = 0`  

- ##### `Modem(double x , double y)`

  `x` coordinate, `y` coordinate and __default k__ are loaded inside the object.

- ##### `Modem(double x , double y, int k)`

  `x` coordinate, `y` coordinate and `k` are loaded inside the object. `k` should be a none zero positive integer. If not, the __default k__ is loaded automaticaly.

#### Methods

- ##### `setK(int k)`

  `k` should be positive and none zero. Otherwise, ignored.

- ##### `setX(double x)`

  Sets the value of x coordinate manualy.

- ##### `setY(double y)`

  Sets the value of y coordinate manualy.

- ##### `getCoordinateXY()`

  Return a JTS `coordinate` object. More on that [here - not set]().

- ##### `toString()`

  Will return a string in format `(xCord , yCord)` for example `(12.4 , 32,2)`.

### Polygon

#### Explanation

This class is a container for the JTS Polygon class. Although the program is mostly using the JTS polygon methods, all input arguments of the methods inside the program are as this type.  

#### Constructor

- ##### `Polygon()`
  
  Empty constructor. Nothing is loaded inside the object.

- ##### `Polygon(String path)`
  
  Polygon file inside the path is loaded inside the object.
  `path` should refer to a JTS XML polygon file.

#### Methods

- ##### `readPolygonWKT(String path)`
  
   Using the provided path, loades a wkt polygon file inside the object.

- ##### `readPolygonXML(String path)`

   Using the provided path, loades a JTS XML polygon file inside the object.  
   Relies on `readXMLFile(String path)` private method.

- ##### `convertXMLFile(String path)`

   Using the provided path, coverts a JTS XML polygon file into a WKT file. The converted file is saved in the same directoryand the file name is preserved. The file name extension is changed to wkt instead.  
   Relies on `readXMLFile(String path)` private method.

- ##### `readXMLFile(String path)` :lock:

   __DEVELOPER GUIDE__
   This method will read an XML file, itterates inside the file and ignores the header. Extracts the coordinates and convert it to WKT standard coordinate.  
   In the end it'll return the WKT standard string.

- ##### `plotPolygon()` :warning:

  Plots the polygon.

  > It is recomended to not use this method as it is still in development.

- ##### `randomPoint()`

  Returns a random JTS Coordinate object such that the x and y coordinate are inside the polygon.

- ##### `setPoly(jts.geom.Polygon poly)`

  Manually sets the polygon. Polygon input argument is as JTS Polygon type.

- ##### `getPloy()`

  Returns the polygon as JTS Polygon type.

- ##### `getArea()`

  Returns the area of the polygon as a double.

- ##### `isHoled()`

  Returns false if the polygon is simple. True otherwise.

### VPCalculator  

#### Explanation

This is a static class for calculating the visiblity polygon, aka the coverage area of a k-modem.  
The main alogrithm used for calculating the coverage area derivs from Monte-Carlo area estimation.  
Basically some number of points (which is present as _itterations_ prarameter) is chosen inside the polygon using a uniform distribution. Then the algorithm calculates to see which of these points are covered by the modems. After that, it returns the ratio of _**number of visible points to total number of points**_ which is roughly speaking the ratio of _**visiblity polygon area to polygon area**_. We later use this as our fittness parameter.

#### Methods

- ##### `unholedVisibilityPolygon` :no_entry:

  __Do Not Use.__ This function is obsolete and should not be used.

- ##### `riticalVerticesCalc` :no_entry:

  __Do Not Use.__ This function is obsolete and should not be used.

- ##### `monteCarloVP(int itterations , Polygon poly , Modem[] modem , int numberOfAllowedCollisions)`

  This is the main class that is used for calculating the ratio of area of visiblity polygon to area of the polygon.  
  As explained, this function drives it's algorithm from [__Monte Carlo Area Estimation__](https://en.wikipedia.org/wiki/Monte_Carlo_method).  

  __Input Arguments__:  
  - `itterations` Number of random points used for area estimation. The larger this parameter is, the better the estimation but at the cost of processing power and time.
  - `poly` The polygon which the algorithm should perform the estimation on.
  - `modem` An array of `Modem`s which determines the location and the peneteration rate of all the modems.  
  - `numberOfAllowedCollisions` Determines how many collision between the signals are alowed. This number is between 0 (no collision is allowed) to `modem.length` (not to consider any collision).  
  
  __The Algorithm__:  
  
  Loop Through the following steps __itterations__ time:
  1. Generate a random point inside the polygon using a uniform distribution.
  2. Draw the line from each _modem_ to the generated points.
  3. For each line:
      1. Calculate number of intersections (`numberOfIntersections`) with the polygon's edges.
      2. If number of intersections is greater than **_K_ of the current modem**, add `numberOfVisibleModems` by one.
  4. if `numberOfVisibleModems` is greater than 0 and less than `numberOfAllowedCollisions` increase number of points in visiblity polygon (`nPointsInVisibilityPolygon`) by one. 

  Return number of points in visiblity polygon divided by to total number of points (`nPointsInVisibilityPolygon / itterations`)

- ##### `monteCarloVP_MT(int itterations , Polygon poly , Modem[] modem , int numberOfAllowedCollisions)`

  This function uses the same algorithm as `monteCarloVP`, with the differnece that the itterations run in 4 paralell thread.

- ##### `monteCarloVP_SavePoints( int itterations , Polygon poly , Modem[] modem , int numberOfAllowedCollisions , String path , boolean showTimeDifference)`

  This function uses the same algorithm as `monteCarloVP`. The only differnece is that it saves the created random points inside a _WKT_ file using the provided `path`. It also show the duration of algorithm process if a `true` value is passed as `showTimeDifference`.  
  Note that the `path` should be a __directory__ not a __filename__. The algorithm creates a file name _montecarlo_out.wkt_ in the passed directory and saves the points inside that.
  
- ##### `monteCarloBenchmark(Polygon poly , int itterations)` :warning:
  
  Do not use. Still in development.

### GA_Config  

### OW_Config  

### GeneticsAlgorithm  

### OptimizedWalk  

### Util  

## Graphics  

### PlotFrame

### PlotPanel

### Plotter
