
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class dataCollector {
    String dataFileName = "backend/src/main/resources/csvFiles/sampledata.csv";
    String avgFilename = "backend/src/main/resources/csvFiles/sampleAverage.csv";
    String ShoppingListFilename = "backend/src/main/resources/csvFiles/ShoppingList.csv";
    String Date;

    public dataCollector(){
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date = dateFormat.format(currentDate);
    }

    public String[] getReccomendations(){
        ArrayList<String> recs = new ArrayList<String>();
        List<List<String>> data = readCSV(avgFilename);
        for(List<String> line: data){
            int timeDiff = timeDiff(line.get(2), Date);
            if((timeDiff*1.2) >= Integer.parseInt(line.get(4)) && notInList(line.get(0))){
                recs.add(line.get(0));
            }
        }
        int size = recs.size();
        String[] toReturn = new String[size];
        for(int i =0; i<size; i++){
            toReturn[i] = recs.get(i);
        }
        return toReturn;
        
    }
    boolean notInList(String item){
        String[] list = getShoppingList();
        for(String thing: list){
            if(thing.matches(item)){
                return false;
            }
        }
        return true;
    }
    public String[] getShoppingList(){
        List<List<String>> data = readCSV(ShoppingListFilename);
        String[] shoppingList = new String[data.size()];
        for(int i=0; i< data.size(); i++){
            shoppingList[i] = data.get(i).get(0);
        }
        return shoppingList;
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
