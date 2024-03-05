package edu.jsu.mcis.cs310.tas_sp24;

public class Department {

    private int ID, terminalID;
    private final String description;

    public Department(int id, String desc, int termID) {
        this.ID = id;
        this.description = desc;
        this.terminalID = termID;
    }

    public int getID() {
        return ID;
    }

    public String getDescription() {
        return description;
    }

    public int getTerminalID() {
        return terminalID;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(ID).append(' ');
        s.append('(').append(description).append("), ");
        s.append("Terminal ID: ").append(terminalID);

        return s.toString();
    }
}
