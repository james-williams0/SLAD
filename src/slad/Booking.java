package slad;

public class Booking {
    private String name;
    private String date;
    private String time;
    private String table;
    private String details;
    private byte numOfPeople;

    public Booking(String name, String date, String time, String table, String details, byte numOfPeople){
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

    public String getTime(){ return this.time; }

    public String getTable() { return this.table; }

    public String getDetails(){
        return this.details;
    }

    public byte getNumOfPeople(){ return this.numOfPeople;           }
}
