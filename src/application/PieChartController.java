package application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;

public class PieChartController {
	
	 @FXML
	 private PieChart pieChart1;
	
	public void pieChartview() {
		
	
		String path = "D:\\Milad\\Northeastern Uni\\Courses\\Intro data mining and machine learning\\HWs\\Practicum 2\\heart.csv";
		String line = "";
		//ArrayList to store the values
		ArrayList<String> chestPainType = new ArrayList<String>();
		String line2 = "";
		ArrayList<String> chestPainTypeNotRepeart = new ArrayList<String>();
		ArrayList<Integer> countList = new ArrayList<Integer>();
		
		ArrayList<String> headerName = new ArrayList<String>();
		try {
			//opening the file
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			line = br.readLine();
			String[] headers = line.split(",");
			
			for (int i = 0; i < headers.length; i++) {
				System.out.println(i + " " + headers[i]);
			}
			System.out.println("Number of columns is " + headers.length);
						
			//reading all the values
			while((line = br.readLine()) != null) {
				String[] values = line.split(",");
				chestPainType.add(values[2]);
			}
			chestPainTypeNotRepeart.add(chestPainType.get(0));
			//System.out.println(chestPainType.size());
			
			for (int m = 1; m < chestPainType.size(); m++) {
				
				int times = 0;
				for (int b = 0; b < chestPainTypeNotRepeart.size(); b++) {
										
					if (!chestPainType.get(m).equals(chestPainTypeNotRepeart.get(b))) {
						times++;
							
					}
					
				}
				
				if (times == chestPainTypeNotRepeart.size()) {
					chestPainTypeNotRepeart.add(chestPainType.get(m));
					
				}
			}
			
			for (int t = 0; t < chestPainTypeNotRepeart.size(); t++) {
				System.out.println(chestPainTypeNotRepeart.get(t));
			}
			
			
			
			for (int s = 0; s < chestPainTypeNotRepeart.size(); s++) {
				int count = 0;
				for (int n = 0; n < chestPainType.size(); n++) {
					if (chestPainType.get(n).equals(chestPainTypeNotRepeart.get(s))) {
						count++;
						}
				}
				countList.add(count);
			}
			
			for (int r = 0; r < countList.size(); r++) {
				System.out.println(countList.get(r));
			}
			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	
        
 
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < chestPainTypeNotRepeart.size(); i++) {
        	pieChartData.add(new PieChart.Data(chestPainTypeNotRepeart.get(i), countList.get(i)));
        }
        
  
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Chest pain type");
	}
}