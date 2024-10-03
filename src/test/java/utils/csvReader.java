package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.testng.annotations.DataProvider;

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

}
