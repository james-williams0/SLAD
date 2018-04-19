package slad;
//Node Class
public class Booking {
    //Attributes of a booking
    private String name;
    private String date;
    private String time;
    private short table;
    private String details;
    private short numOfPeople;

    //Constructor
    public Booking(String name, String date, String time, short table, String details, short numOfPeople){
        this.name = name;
        this.date = date;
        this.time = time;
        this.table = table;
        this.details = details;
        this.numOfPeople = numOfPeople;
    }

    //Getters
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

    //Sort the booking by date of booking, not order in which bookings were made
    public int dateSort(Booking booking1, Booking booking2){

        //Parse input data
        String[] dates1 = booking1.getDate().split("/");
        String[] dates2 = booking2.getDate().split("/");
        int[] datesInt1 = new int[dates1.length];
        int[] datesInt2 = new int[dates2.length];

        try {
            for (int i = 0; i < datesInt1.length; i++) {
                datesInt1[i] = Integer.parseInt(dates1[i]);
                datesInt2[i] = Integer.parseInt(dates2[i]);
            }

        }catch(NumberFormatException e){
            e.printStackTrace();
        }

        //Test date
        for(int i=datesInt1.length-1; i>=0; i--){
            if(datesInt1[i] < datesInt2[i]){
                return 2;
            }else if(datesInt1[i] > datesInt2[i]){
                return 1;
            }
        }
        //if same date, test time
        return timeSort(booking1, booking2);

    }
    //Sort data set based on time
    private int timeSort(Booking booking1, Booking booking2) {
        //parse user input
        String[] times1 = booking1.getTime().split(":");
        String[] times2 = booking2.getTime().split(":");
        int[] timesInt1 = new int[times1.length];
        int[] timesInt2 = new int[times2.length];

        try {
            for (int i = 0; i < timesInt1.length; i++) {
                timesInt1[i] = Integer.parseInt(times1[i]);
                timesInt2[i] = Integer.parseInt(times2[i]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //Sort by time
        for(int i = 0; i<timesInt1.length; i++){
            if(timesInt1[i] < timesInt2[i]){
                return 2;
            }else if(timesInt2[i] < timesInt1[i]){
                return 1;
            }
        }
        return 0;
    }
}
