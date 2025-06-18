package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.DataProvider;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class csvReader {
	
	@DataProvider(name="csvReader")
	public Object[][] data() throws IOException{
		
		String filepath = "src\\test\\resources\\";
		String filename = "testdata.csv";
		
		FileReader fr = new FileReader(filepath+filename);
		BufferedReader bfr = new BufferedReader(fr);
		
		String line;
		ArrayList<String[]> data = new ArrayList<>();
		boolean isHeader = false;
		while((line=bfr.readLine()) != null) {
			if(isHeader) {
				isHeader = false;
			
			}else {
				if(line.endsWith(",")) {
					line = line.substring(0, line.length()-1) +  ",NA2";
				}
				line = line.replace(",,", ",\"\",");
				
				String[] row = line.split(",");
				data.add(row);
				
			}
		}
		
		Object[][] dataRows = new Object[data.size()][data.get(0).length];
		int rowCount = data.size();
		int cellCount = data.get(0).length;
		for(int i=0; i<rowCount; i++) {
			for(int j=0; j<cellCount; j++) {
				dataRows[i][j] = data.get(i)[j].trim();
			}
		}
		
		return dataRows;
		
	}
	
	@DataProvider(name="csvReader2")
    public Object[][] data2() {
        String csvFile = System.getProperty("user.dir")+"//src//test//resources//"+"testdata.csv"; // Replace with your CSV file path
        Object[][] data;
        int rowCount;
        int cellCount;
        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            List<String[]> allData = reader.readAll(); // Reads all lines into a list of String arrays
            rowCount = allData.size();
            cellCount = allData.get(0).length;
            System.out.println(rowCount);
            System.out.println(cellCount);
            
            data = new Object[rowCount][cellCount];
            int i=0;
            for (String[] row : allData) {
                int j=0;
            	for (String cell : row) {
                    System.out.print(cell + " ");
            		data[i][j] = cell;
            		j+=1;
                }
            	i+=1;
               
            }
            //System.out.println(data[0][0]);
            return data;
        } catch (IOException | CsvException e) {
            e.printStackTrace();
            data = new Object[0][0];
            return data;
        }
        
        
    }

}
