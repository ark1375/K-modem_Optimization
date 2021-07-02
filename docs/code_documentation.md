## Code Documentation
In this text, you can find detailed explaination on the code itself.
<br>
The structure of the program is as shown in the tree bellow:
```
Model_SA
│
│   dependency-reduced-pom.xml
│   pom.xml
│   README.md
│   convertor.py
│   updateReadme.sh
│   
├───dataset
│   │   PLY.rar
│   │   WKT.rar
│   │   
│   └───Picked
│           ...Random Dataset Picked Files...
│           
├───docs
│   │   code_documentation.md
│   │   
│   └───screenshots
│           
├───src
│   ├───main
│   │   ├───java
│   │   │   └───ku
│   │   │       └───cs
│   │   │           └───model
│   │   │               └───sa
│   │   │                   │   <span style = "color: blue;">GA_Config.java</span>
│   │   │                   │   <span style = "color: blue;">GeneticsAlgorithm.java</span>
│   │   │                   │   <span style = "color: blue;">Main.java</span>
│   │   │                   │   <span style = "color: blue;">Modem.java</span>
│   │   │                   │   <span style = "color: blue;">OptimizedWalk.java</span>
│   │   │                   │   <span style = "color: blue;">OW_Config.java</span>
│   │   │                   │   <span style = "color: blue;">Polygon.java</span>
│   │   │                   │   <span style = "color: blue;">Util.java</span>
│   │   │                   │   <span style = "color: blue;">VPCalculator.java</span>
│   │   │                   │   
│   │   │                   └───graphics
│   │   │                           <span style = "color: brown;">PlotFrame.java</span>
│   │   │                           <span style = "color: brown;">PlotPanel.java</span>
│   │   │                           <span style = "color: brown;">Plotter.java</span>
│   │   │                           
│   │   └───resources
│   └───test
│       └───java
│           
└───test_cases
        ...Test Cases...
```
There are a total of seven classes in _ku.cs.model.sa_ package (except main) which are the main logic of the program.  
