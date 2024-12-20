/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    private int[] dayCounts;
    private int[] monthCounts;
    private int[] yearCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     * must use full file name with extenstion and surround with quotes when entering string 
     */
    public LogAnalyzer(String logFile)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dayCounts = new int[28];
        monthCounts = new int [12];
        yearCounts = new int [5];
        // Create the reader to obtain the data.
        reader = new LogfileReader(logFile);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }

        reader.reset();
    }

    /**
     * Analyze the Monthly access data from the log file.
     */
    public void analyzeMonthlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int month = entry.getMonth() - 1;
            monthCounts[month]++;
        }

        reader.reset();
    }

    /**
     * Analyze the Yearly access data from the log file.
     */
    public void analyzeYearlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int year = entry.getYear() - 2015;
            yearCounts[year]++;
        }

        reader.reset();
    }

    /**
     * will analyze year, month, day, and hour
     */
    public void analyzeData(){

        analyzeYearlyData();
        analyzeMonthlyData();
        analyzeHourlyData();
        analyzeDailyData();

    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeDailyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int day = entry.getDay() - 1;
            dayCounts[day]++;
        }

        reader.reset();
    }

    /**
     * @returns the day that most people access the site
     */
    public String busiestDay(){

        int highestDay = 0;
        int dayCompare = 0;

        for (int i = 0; i < dayCounts.length; i++ ){
            if(dayCounts[i] > dayCompare ){
                dayCompare = dayCounts[i]; 
                highestDay = i;
            }
        }

        return String.format("Most people visted the site on day %d",(highestDay + 1) );

    }

    /**
     * @returns the day that the least people access the site
     */
    public String quietestDay(){

        int lowestDay = 0;
        int lowDayCompare = dayCounts[0];

        for (int i = 0; i < dayCounts.length ; i++ ){
            if(dayCounts[i] < lowDayCompare ){
                lowDayCompare = dayCounts[i];
                lowestDay = i;
            }
        }

        return String.format("Day %d has the least amount of trafic compared to other days",(lowestDay + 1));

    }

    

    /**
     * @returns the amount of times that the site has been accessed 
     */
    public int numberOfAccesses(){
        int numberOfLines = 0;

        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            numberOfLines++;
        }

        reader.reset();
        return numberOfLines;
    }

    /**
     * @returns the hour that most people access the site
     */
    public String busiestHour(){

        int highestHour = 0;
        int hourCompare = 0;

        for (int i = 0; i < hourCounts.length; i++ ){
            if(hourCounts[i] > hourCompare ){
                hourCompare = hourCounts[i]; 
                highestHour = i;
            }
        }

        return String.format("Most people accessed the site on hour %d",highestHour );

    }

    /**
     * @returns the hour that the least people access the site
     */
    public String quietestHour(){

        int lowestHour = 0;
        int lowHourCompare = hourCounts[0];

        for (int i = 0; i < hourCounts.length; i++ ){
            if(hourCounts[i] < lowHourCompare ){
                lowHourCompare = hourCounts[i];
                lowestHour = i;
            }
        }

        return String.format("Hour %d has the least amount of trafic compared to other hours",lowestHour );

    }

    /**
     * @returns the month that the least people access the site
     */
    public String quietestMonth(){

        int lowestMonth = 0;
        int lowMonthCompare = monthCounts[0];

        for (int i = 0; i < monthCounts.length; i++ ){
            if(monthCounts[i] < lowMonthCompare ){
                lowMonthCompare = monthCounts[i];
                lowestMonth = i + 1;
            }
        }

        return String.format("Month %d has the least amount of trafic compared to other months",lowestMonth );

    }

    /**
     * @returns the month that the most people access the site
     */
    public String busiestMonth(){

        int highestMonth = 0;
        int highMonthCompare = monthCounts[0];

        for (int i = 0; i < monthCounts.length; i++ ){
            if(monthCounts[i] > highMonthCompare ){
                highMonthCompare = monthCounts[i];
                highestMonth = i + 1;
            }
        }

        return String.format("Month %d has the most amount of trafic compared to other months",highestMonth );

    }
    
    /**
     * This method takes the total amount of times the site is visted on each month then divides it by the
     * amount of years that are tracked to get the avrage amount of times the website is visted each month a year
     */
    public void averageAccessesPerMonth(){

        System.out.println("Month: Avg Count");
        for(int month = 0; month < monthCounts.length; month++){

            System.out.println((month +1) + ": " + (monthCounts[month]/yearCounts.length));

    
        }

    }

    
    /**
     * Method that finds out which two hour block had the most trafic
     * @returns A string that details the two hour block that had most trafic
     */
    public String busiestTwoHour(){

        int highHourCompare = (hourCounts[0] + hourCounts[23]);
        int highHourA = 0;
        int highHourB = 23;

        for(int i =0; i < hourCounts.length - 1; i++){
            if (hourCounts[i] + hourCounts[i +1] > highHourCompare ){
                highHourCompare = hourCounts[i] + hourCounts[i +1];
                highHourA = i;
                highHourB = i + 1;
            }

        }
        return String.format("The busiest two hour block is between the hours of %d and %d",highHourA, highHourB);

    
    }

    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }

    /**
     * Will print how many times each month had access
     */
    public void totalAccessesPerMonth(){
        System.out.println("Month: Count");
        for(int month = 0; month < monthCounts.length; month++){

            System.out.println((month +1) + ": " + monthCounts[month]);

        }

    }
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
