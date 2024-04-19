package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import edu.jsu.mcis.cs310.tas_sp24.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


//COMPLETED by Qays

public class Punch {
    
    private int id;
    private final int terminalId;
    private final Badge badge;
    private final EventType punchType;
    private LocalDateTime originalTime; // Initialize in constructor for new punches
    private LocalDateTime adjustedTime; // Initially null, set later
    private PunchAdjustmentType adjustmentType = PunchAdjustmentType.NONE;

    // Constructor for new punches
    public Punch(int terminalId, Badge badge, EventType punchType) {
        
        this.terminalId = terminalId;
        this.badge = badge;
        this.punchType = punchType;
        this.originalTime = LocalDateTime.now(); // Current system time
        
    }

    // Constructor for existing punches
    public Punch(int id, int terminalId, Badge badge, LocalDateTime originalTime, EventType punchType) {
        
        this.id = id;
        this.terminalId = terminalId;
        this.badge = badge;
        this.originalTime = originalTime;
        this.punchType = punchType;
        
    }

    // Accessors
    public int getId() { 
        
        return id; 
    
    }
    public int getTerminalId() {
        
        return terminalId; 
        
    }
    public Badge getBadge() {
        
        return badge; 
        
    }
    public EventType getPunchType() {
        
        return punchType; 
        
    }
    public LocalDateTime getOriginalTime() {
        
        return originalTime; 
        
    }
    public LocalDateTime getAdjustedTime() {
        
        return adjustedTime; 
        
    }
    public PunchAdjustmentType getAdjustmentType() {
        
        return adjustmentType; 
        
    }

    // Setters for adjusted time and type
    public void setAdjustedTime(LocalDateTime adjustedTime) {
        
        this.adjustedTime = adjustedTime; 
        
    }
    public void setAdjustmentType(PunchAdjustmentType adjustmentType) {
        
        this.adjustmentType = adjustmentType; 
        
    }
    
    public void adjust(Shift s){
        
        //initialize adjustment type to NONE
        this.adjustmentType = PunchAdjustmentType.NONE;
        
        //get length of time between clock in/out and shift start/end in MINUTES
        long minsFromStart_S = ChronoUnit.MINUTES.between(s.getShiftStart(),originalTime);
        long minsFromEnd_S = ChronoUnit.MINUTES.between(s.getShiftStop(),originalTime);
        long minsFromStart_L = ChronoUnit.MINUTES.between(s.getLunchStart(),originalTime);
        long minsFromEnd_L = ChronoUnit.MINUTES.between(s.getLunchStop(),originalTime);
        
        //Set the adjusted time & type for CLOCK IN punches if they fall before the shift starts under the round interval 
        if(minsFromStart_S < 0 && Math.abs(minsFromStart_S) <= s.getRoundInterval()){
            LocalTime time = s.getShiftStart();
            LocalDate date = LocalDate.now();
            setAdjustedTime(LocalDateTime.of(date,time));
            this.adjustmentType = PunchAdjustmentType.SHIFT_START;
        }
        
        //Set the adjusted time & type for CLOCK OUT punches if they fall after the shift ends under the round interval
        else if(minsFromEnd_S > 0 && Math.abs(minsFromEnd_S) <= s.getRoundInterval()){
            LocalTime time = s.getShiftStop();
            LocalDate date = LocalDate.now();
            setAdjustedTime(LocalDateTime.of(date, time));
            this.adjustmentType = PunchAdjustmentType.SHIFT_STOP;
        }
        
        //Set the adjusted time & type for CLOCK IN punches if they fall after the shift starts under the grace period
        else if(minsFromStart_S > 0 && Math.abs(minsFromStart_S) <= s.getGracePeriod()){
            LocalTime time = s.getShiftStart();
            LocalDate date = LocalDate.now();
            setAdjustedTime(LocalDateTime.of(date, time));
            this.adjustmentType = PunchAdjustmentType.SHIFT_START;
        }
        
        
        
        
        
        /*
        If( math.Abs[shiftStartRule - shift.ClockInPunch] =< 5){
        round(shift.ClockInPunch) to shiftRule
        startTime = shift.ClockInPunch
        }
        
        if( math.Abs[shiftStopRule - shift.ClockOutPunch] =< 5){
        round(shift.ClockOutPunch) to shiftRule
        endTime = shift.ClockOutPunch
        }
        
        shiftDur = endTime - startTime
        
        if(math.Abs[lunchStartRule - shift.LunchClockOut] =< 5){
        round(shift.LunchOutPunch) to LunchStartRule
        lunchStartTime = shift.LunchStartRule
        }
        
        if(math.Abs[lunchEndRule - shift.LunchClockIn] =< 5){
        round(shift.LunchInPunch) to LunchEndRule
        lunchEndTime = shift.LunchStartRule
        }
        
        lunchDur = LunchEndRule - LunchStartRule
        
        shiftNetDur = shiftDur - lunchDur
        
        add logic for penalties that will deduct 15 minutes from a person's shift for each:
        math.Abs[lunchStartRule - shift.LunchClockOut] > 5
        
        math.Abs[lunchEndRule - shift.LunchClockIn] > 5
        
        math.Abs[shiftStopRule - shift.ClockOutPunch] > 5
        
        math.Abs[shiftStartRule - shift.ClockInPunch] > 5
        */
       
    } 

    // Pretty print for original timestamp
    public String printOriginal() {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String formattedDate = originalTime.format(formatter).toUpperCase();
        return String.format("#%s %s: %s", badge.getId(), punchType, formattedDate);
        
    }
    
    public String printAdjusted(){
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        String adjustedDate = originalTime.format(formatter).toUpperCase();
        return String.format("#%s %s: %s %s", badge.getId(), punchType, adjustedDate, adjustmentType);
    }

    @Override
    public String toString() {
        
        return printOriginal(); // For now, this will print the original timestamp
        
    }
}
