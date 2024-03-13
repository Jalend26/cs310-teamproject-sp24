package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.util.HashMap;

//COMPLETED
public class Shift {
    
    private final int id;
    private final String description;
    private final LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    private final int roundInterval, gracePeriod, dockPenalty, lunchThreshold;
    private final long lunchDuration, shiftDuration; // Calculated in minutes

    public Shift(HashMap<String, String> parameters) {
        
        this.id = Integer.parseInt(parameters.get("id"));
        this.description = parameters.get("description");
        this.shiftStart = LocalTime.parse(parameters.get("shiftstart"));
        this.shiftStop = LocalTime.parse(parameters.get("shiftstop"));
        this.lunchStart = LocalTime.parse(parameters.get("lunchstart"));
        this.lunchStop = LocalTime.parse(parameters.get("lunchstop"));
        this.roundInterval = Integer.parseInt(parameters.get("roundinterval"));
        this.gracePeriod = Integer.parseInt(parameters.get("graceperiod"));
        this.dockPenalty = Integer.parseInt(parameters.get("dockpenalty"));
        this.lunchThreshold = Integer.parseInt(parameters.get("lunchthreshold"));
        // Calculate durations based on provided start and stop times
        this.lunchDuration = (int) java.time.Duration.between(lunchStart, lunchStop).toMinutes();
        this.shiftDuration = (int) java.time.Duration.between(shiftStart, shiftStop).toMinutes();
    }

    // Getters
    public int getId() {
        
        return id; 
    }
    
    public String getDescription() { 
        
        return description; 
    }
    
    public LocalTime getShiftStart() { 
        
        return shiftStart; 
        
    }
    
    public LocalTime getShiftStop() { 
        
        return shiftStop; 
    }
    public LocalTime getLunchStart() { 
        return lunchStart;
    }
    public LocalTime getLunchStop() { 
        return lunchStop; 
    }
    public int getRoundInterval() { 
        return roundInterval; 
    }
    public int getGracePeriod() { 
        return gracePeriod;
    }
    public int getDockPenalty() { 
        return dockPenalty;
    }
    public int getLunchThreshold() {
        return lunchThreshold; 
    }
    public long getLunchDuration() { 
        return lunchDuration;
    }
    public long getShiftDuration() { 
        return shiftDuration; 
    }

    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description, shiftStart, shiftStop, shiftDuration, lunchStart, lunchStop, lunchDuration);
    }
    
    //using stringbuilder would give more control
    //change formatter to string builder
}
