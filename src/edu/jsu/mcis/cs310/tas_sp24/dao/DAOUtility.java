package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.util.ArrayList;
import java.math.BigDecimal;


/**
 *
 * Utility class for DAOs. This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 *
 */
public class DAOUtility {

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int totalMinutes = 0;
        boolean lunchDeducted = false;

        for (int i = 0; i < dailypunchlist.size() - 1; i++) {
            Punch inPunch = dailypunchlist.get(i);
            Punch outPunch = dailypunchlist.get(i + 1);

            // Check if the pair is a valid clock in and clock out punch
            if (inPunch.isClockIn() && outPunch.isClockOut()) {
                int minutes = (int) ((outPunch.getPunchTime().getTime() - inPunch.getPunchTime().getTime()) / (60 * 1000));
                totalMinutes += minutes;
                i++; // Skip the next punch as it has already been processed
            }
        }

        // Deduct lunch break if applicable
        if (totalMinutes > shift.getLunchThreshold() && !lunchDeducted) {
            totalMinutes -= shift.getLunchBreakDuration();
            lunchDeducted = true;
        }

        return totalMinutes;
    }

    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s) {
        int minutesWorked = calculateTotalMinutes(punchlist, s);

        long expectedMinutes = (s.getShiftDuration() * 5) - (s.getLunchDuration() * 5);

        double percentage = ((double) minutesWorked / expectedMinutes);

        return BigDecimal.valueOf((1 - percentage) * 100);
    }
}
