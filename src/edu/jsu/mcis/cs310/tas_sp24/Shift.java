package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.util.HashMap;

public class Shift {
    private final int id;
    private final String description;
    private final LocalTime shiftstart, shiftstop, lunchStart, lunchStop; //ask about the threshehold 
    private final int roundInterval, gracePeriod, dockPenalty, lunchDuration, shiftDuration;

    public Shift(HashMap<String, String> parameters) {
        this.id = Integer.parseInt(parameters.get("id"));
        this.description = parameters.get("description");
        this.shiftstart = LocalTime.parse(parameters.get("shiftstart"));
        this.shiftstop = LocalTime.parse(parameters.get("shiftstop"));
        this.lunchStart = LocalTime.parse(parameters.get("lunchStart"));
        this.lunchStop = LocalTime.parse(parameters.get("lunchStop"));
        //-------------------------------
        this.roundInterval = Integer.parseInt(parameters.get("roundInterval"));
        this.gracePeriod = Integer.parseInt(parameters.get("gracePerioid"));
        this.dockPenalty = Integer.parseInt(parameters.get("dockPenalty"));
        //-------------------------------------
        this.lunchDuration = Integer.parseInt(parameters.get("lunchDuration"));
        this.shiftDuration = Integer.parseInt(parameters.get("shiftDuration"));
    }
    
    public int getID(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public LocalTime getStart(){
        return shiftstart;
    }
    public int getRoundInterval(){
        return roundInterval;
    }
    public int getGracePerioid(){
        return gracePeriod;
    }
    public int getDockPenalty(){
        return dockPenalty;
    }
    
    public LocalTime getStop(){
        return shiftstop;
    }
    public LocalTime getLunchStart(){
        return lunchStart;
    }
    public LocalTime getLunchStop(){
        return lunchStop;
    }
    public int getLunchDuration(){
        return lunchDuration;
    }
    public int getShiftDuration(){
        return shiftDuration;
    }
    //STILL NEED TO ADD SHIFT & LUNCH DURATION CALCULATIONS
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description, shiftstart, shiftstop, shiftDuration, lunchStart, lunchStop, lunchDuration);
    }
}
