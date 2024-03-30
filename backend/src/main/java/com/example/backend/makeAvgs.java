
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class makeAvgs{
    String dataFileName = "src/sampledata.csv";
    String avgFilename = "src/sampleAverage.csv";
    String Date;

    public makeAvgs(String date){
        Date = date;
        List<List<String>> data = readCSV(dataFileName);
        for(int i =0; i < data.size(); i++){
            String curDate = data.get(i).get(1);
            if(curDate.equals(Date)){
                String item = data.get(i).get(0);
                if(!first(item)){
                    int numPurch = setNumPurch(item);

                    String firstDate = getFirstDate(item);
                    System.out.println("this is: " + item + firstDate);
                    int timeDiff = timeDiff(firstDate, Date);
                    int avg = avg(timeDiff,numPurch);
                    setNewVals(item,Date, avg );
                }
            }
        }
    }

    void setNewVals(String item, String last, int avg){
        List<List<String>> avgs = readCSV(avgFilename);
        for (int i = 0; i < avgs.size(); i++) {
            List<String> row = avgs.get(i);
            if (row.contains(item)) {
                avgs.get(i).set(2, last);
                avgs.get(i).set(3, Integer.toString(avg));
            }
        }
        writeCSV(avgs, avgFilename);
    }


    boolean first(String item){
        List<List<String>> avgs = readCSV(avgFilename);
        for (int i = 0; i < avgs.size(); i++) {
            List<String> row = avgs.get(i);
            if (row.contains(item)) {
                return (row.get(2).equals("\0"));
            }
        }
        return true;
    }

    String getFirstDate(String item){
        List<List<String>> avgs = readCSV(avgFilename);
        int itemIndex;
        for (int i = 0; i < avgs.size(); i++) {
            List<String> row = avgs.get(i);
            if (row.contains(item)) {
                return row.get(1);
            }
        }
        return "";
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

    int avg(int timeDiff, int numPurch){
        return timeDiff/numPurch;
    }

    int setNumPurch(String item){
        List<List<String>> avgs = readCSV(avgFilename);
        int itemIndex = 0;
        for (int i = 0; i < avgs.size(); i++) {
            List<String> row = avgs.get(i);
            if (row.contains(item)) {
                itemIndex = i;
                break;
            }
        }
        List<String> toEdit = avgs.get(itemIndex);
        int num = Integer.parseInt(toEdit.get(4)) + 1;
        toEdit.set(4, Integer.toString(num));
        writeCSV(avgs, avgFilename);
        return num;
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

    private static void writeCSV(List<List<String>> data, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (List<String> row : data) {
                writer.write(String.join(",", row));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
