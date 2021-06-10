
package ku.cs.model.sa;


public final class OW_Config {
    
    private int defaultK = 0;
    private int movingThreshold = 5;
    private double agentPace = 50;
    private double deteriorationRate = 0.5;
    private int monteCarloItterations = 1000;
    private int numberOfInitialPopulation = 5000;
    private int numberOfSelectedPopulation = 50;
    private double monteCarloErrorThreshold = 0.015;
    private boolean useMultiThreading = false;

    public OW_Config(
            int defaultK,
            int movingThreshold,
            double agentPace,
            double deteriorationRate,
            int monteCarloItterations,
            int numberOfInitialPopulation,
            int numberOfSelectedPopulation,
            double monteCarloErrorThreshold,
            boolean useMultiThreading) {
        
        setDefaultK(defaultK);
        setMovingThreshold(movingThreshold);
        setAgentPace(agentPace);
        setDeteriorationRate(deteriorationRate);
        setMonteCarloItterations(monteCarloItterations);
        setNumberOfInitialPopulation(numberOfInitialPopulation);
        setNumberOfSelectedPopulation(numberOfSelectedPopulation);
        setMonteCarloErrorThreshold(monteCarloErrorThreshold);
        setUseMultiThreading(useMultiThreading);
        
    }
    
    public OW_Config(){}

    public void setDefaultK(int defaultK) {
        
        if(defaultK >= 0){
            this.defaultK = defaultK;
        }
        
    }

    public void setMovingThreshold(int movingThreshold) {
        
        if(movingThreshold >= 0){
            this.movingThreshold = movingThreshold;
        }
    }

    public void setAgentPace(double agentPace) {
        this.agentPace = agentPace;
    }

    public void setDeteriorationRate(double deteriorationRate) {
        
        if (deteriorationRate >= 0 && deteriorationRate < 1)
            this.deteriorationRate = deteriorationRate;
        
    }

    public void setMonteCarloItterations(int monteCarloItterations) {
        if ( monteCarloItterations >= 1 )
            this.monteCarloItterations = monteCarloItterations;
    }

    public void setNumberOfInitialPopulation(int numberOfInitialPopulation) {
        if (numberOfInitialPopulation >= 1 )
            this.numberOfInitialPopulation = numberOfInitialPopulation;
    }

    public void setNumberOfSelectedPopulation(int numberOfSelectedPopulation) {
        if(numberOfSelectedPopulation >= 1 && numberOfSelectedPopulation <= numberOfInitialPopulation)
            this.numberOfSelectedPopulation = numberOfSelectedPopulation;
    }
    
    

    public void setMonteCarloErrorThreshold(double monteCarloErrorThreshold) {
        
        this.monteCarloErrorThreshold = monteCarloErrorThreshold;
    
    }
    
    public void setUseMultiThreading(boolean useMultiThreading){
        this.useMultiThreading = useMultiThreading;
    }

    public int getDefaultK() {
        return defaultK;
    }

    public int getMovingThreshold() {
        return movingThreshold;
    }

    public double getAgentPace() {
        return agentPace;
    }

    public double getDeteriorationRate() {
        return deteriorationRate;
    }

    public int getMonteCarloItterations() {
        return monteCarloItterations;
    }

    public int getNumberOfInitialPopulation() {
        return numberOfInitialPopulation;
    }

    public int getNumberOfSelectedPopulation() {
        return numberOfSelectedPopulation;
    }
    

    public double getMonteCarloErrorThreshold() {
        return monteCarloErrorThreshold;
    }

    public boolean isUseMultiThreading() {
        return useMultiThreading;
    }
    
    
    @Override
    public String toString(){
        return 
                "\nDefault Peneteration Value: " + this.defaultK +
                "\nMonte Carlo Itterations: " + this.monteCarloItterations +
                "\nMonte Carlo Error Threshold: " + this.monteCarloErrorThreshold +
                "\nInitial Population: " + this.numberOfInitialPopulation +
                "\nSelected Population: " + this.numberOfSelectedPopulation +
                "\nAgent Pace: " + this.agentPace +
                "\nSpeed Deterioration Rate: " + this.deteriorationRate +
                "\nAgent Wrong Moves Threshold: " + this.movingThreshold +
                "\nUse Multi-Threading: " + this.useMultiThreading;
    }
}
