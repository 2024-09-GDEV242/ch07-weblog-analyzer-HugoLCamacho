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
    // Use a LogfileReader to access the data.
    private LogfileReader reader;

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer()
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader("weblog.txt");
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
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
}
