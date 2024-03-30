import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class dataCollector {
    String dataFileName = "src/sampledata.csv";
    String avgFilename = "src/sampleAverage.csv";
    String Date;

    public dataCollector(){
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date = dateFormat.format(currentDate);
    }

    String[] getReccomendations(){
        List<List<String>> data = readCSV(dataFileName);
        return null;
    }

    int timeDiff(String first, String last){
        int daysDiff = 0;
        try {
            // Convert `String` to `Date`
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
            Date dateBefore = sdf.parse(first);
            Date dateAfter = sdf.parse(last);

            // Calculate the number of days between dates
            long timeDiff = Math.abs(dateAfter.getTime() - dateBefore.getTime());
            daysDiff = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        }catch(Exception e){
            e.printStackTrace();
        }
        return daysDiff;
    }
    private static List<List<String>> readCSV(String filePath) {
        List<List<String>> data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                List<String> row = Arrays.asList(line.split(","));
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }
}
