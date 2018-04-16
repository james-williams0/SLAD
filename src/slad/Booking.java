package slad;

public class Booking {
    private String name;
    private String date;
    private String time;
    private short table;
    private String details;
    private short numOfPeople;

    public Booking(String name, String date, String time, short table, String details, byte numOfPeople){
        this.name = name;
        this.date = date;
        this.time = time;
        this.table = table;
        this.details = details;
        this.numOfPeople = numOfPeople;
    }

    public String getName(){
        return this.name;
    }

    public String getDate(){
        return this.date;
    }

    public String getTime() {
        return this.time;
    }

    public short getTable() {
        return this.table;
    }

    public String getDetails(){
        return this.details;
    }

    public short getNumOfPeople(){
        return this.numOfPeople;
    }
}
