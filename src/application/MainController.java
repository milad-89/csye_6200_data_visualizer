package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController {

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private ListView<String> listview;
    
    @FXML
	private PieChart pieChart1;
    
    @FXML
    private BarChart<String, Number> barChart1;
    
    @FXML
    private ChoiceBox<String> chartBox;

    @FXML
    private ChoiceBox<String> featureBox;
    
    @FXML
    private ChoiceBox<String> featureBox2;
    
    @FXML
    private Button btnPieChart;
    
    @FXML
    private TextField txt1Field;
    
    @FXML
    private ScatterChart<?, ?> scatterChart;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    ObservableList<String> featureList = FXCollections.observableArrayList();
    
    private String[] headers;
    
    XYChart.Series barChartData = new XYChart.Series();
    
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    
   
    public static String[] readHeader(String p) throws IOException {
    	
    	BufferedReader br = new BufferedReader(new FileReader(p));
    	
    	String line = "";
    	line = br.readLine();
    	
		String[] headers = line.split(",");
    	
    	return headers;
    }
    
    
    public void button1Action() throws IOException {
    	
    	featureList.removeAll(featureList);
    	listview.getItems().clear();
    	
    	FileChooser fc = new FileChooser();
    	
    	fc.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));
    	
    	File selectedFile = fc.showOpenDialog(null);
    	
    	String path = selectedFile.getAbsolutePath();
    	String[] headers = readHeader(path);
    	
		for (int i = 0; i < headers.length; i++) {
			listview.getItems().add(headers[i]);
			featureList.add(headers[i]);
		}    		
      	
    	featureBox.setItems(featureList);
    	featureBox2.setItems(featureList);
    	
    	txt1Field.setText(path);
    	
    }
    
    
    
 	public void showPieChart() {
		
 		ArrayList<String> rowList = new ArrayList<>();
 		ArrayList<String> categoryList = new ArrayList<>();
 		ArrayList<Integer> countList = new ArrayList<>();
 		
 		rowList.clear();
 		categoryList.clear();
 		countList.clear();
 		
 		pieChart1.getData().removeAll(pieChartData);
 		pieChartData.removeAll(pieChartData);
 		
		try {
			//opening the file
			BufferedReader br = new BufferedReader(new FileReader(txt1Field.getText()));
					
			String line = br.readLine();
			String[] headers = line.split(",");
			
									
			//reading all the values
			
			for (int i = 0; i < headers.length; i++) {
				if (featureBox.getValue().equals(headers[i])) {
					while((line = br.readLine()) != null) {
						String[] values = line.split(",");
						rowList.add(values[i]);
					}
				}
			}
			
			
			categoryList.add(rowList.get(0));
			
			for (int m = 1; m < rowList.size(); m++) {
				
				int times = 0;
				for (int b = 0; b < categoryList.size(); b++) {
										
					if (!rowList.get(m).equals(categoryList.get(b))) {
						times++;
							
					}
					
				}
				
				if (times == categoryList.size()) {
					categoryList.add(rowList.get(m));
					
				}
			}
			
			for (int s = 0; s < categoryList.size(); s++) {
				int count = 0;
				for (int n = 0; n < rowList.size(); n++) {
					if (rowList.get(n).equals(categoryList.get(s))) {
						count++;
						}
				}
				countList.add(count);
			}
			
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

       
      
       for (int i = 0; i < categoryList.size(); i++) {
    	   pieChartData.add(new PieChart.Data(categoryList.get(i), countList.get(i)));
       }
       
              
       
       if (pieChart1 != null) { 
    	   pieChart1.getData().addAll(pieChartData);
    	   pieChart1.setTitle(featureBox.getValue());
       }else {
    	   System.out.println("It is null!!!!!");
       }

       
	}
    
    
 	public void showBarChart() {
		
 		ArrayList<String> rowList = new ArrayList<>();
 		ArrayList<String> categoryList = new ArrayList<>();
 		ArrayList<Integer> countList = new ArrayList<>();
 		
 		rowList.clear();
 		categoryList.clear();
 		countList.clear();
 		
 		barChartData.getData().clear();
 		barChart1.getData().removeAll(barChartData);
 		
		try {
			//opening the file
			BufferedReader br = new BufferedReader(new FileReader(txt1Field.getText()));
					
			String line = br.readLine();
			String[] headers = line.split(",");
			
									
			//reading all the values
			
			for (int i = 0; i < headers.length; i++) {
				if (featureBox.getValue().equals(headers[i])) {
					while((line = br.readLine()) != null) {
						String[] values = line.split(",");
						rowList.add(values[i]);
					}
				}
			}
			
			
			categoryList.add(rowList.get(0));
			
			for (int m = 1; m < rowList.size(); m++) {
				
				int times = 0;
				for (int b = 0; b < categoryList.size(); b++) {
										
					if (!rowList.get(m).equals(categoryList.get(b))) {
						times++;
							
					}
					
				}
				
				if (times == categoryList.size()) {
					categoryList.add(rowList.get(m));
					
				}
			}
			
			for (int s = 0; s < categoryList.size(); s++) {
				int count = 0;
				for (int n = 0; n < rowList.size(); n++) {
					if (rowList.get(n).equals(categoryList.get(s))) {
						count++;
						}
				}
				countList.add(count);
			}
			
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
       for (int i = 0; i < categoryList.size(); i++) {
    	   barChartData.getData().add(new XYChart.Data<String, Integer>(categoryList.get(i), countList.get(i)));
       }
       
              
       
       if (barChart1 != null) {
    	   
    	   barChart1.getData().addAll(barChartData);
    	   barChart1.setTitle(featureBox.getValue());
       }else {
    	   System.out.println("It is null!!!!!");
       }

       
	}
 	
 	public void showScatterPlot() throws IOException {
 		
        XYChart.Series series1 = new XYChart.Series();

        scatterChart.setAnimated(false);
        scatterChart.getData().clear();

		String dropDownXValString = featureBox.getValue();
		String dropDownYValString = featureBox2.getValue();
				
	    xAxis.setLabel(dropDownXValString);  
	    yAxis.setLabel(dropDownYValString);
	    
	    BufferedReader br = new BufferedReader(new FileReader(txt1Field.getText()));
		
		String line = br.readLine();
		String[] headers = line.split(",");
		
		// Read x col data
		ArrayList<String> colXData = new ArrayList<>();
		for (int i = 0; i < headers.length; i++) {
			if (dropDownXValString.equals(headers[i])) {
				while((line = br.readLine()) != null) {
					String[] values = line.split(",");
					colXData.add(values[i]);
				}
			}
		}

		// Read Y col data
		BufferedReader br2 = new BufferedReader(new FileReader(txt1Field.getText()));
		ArrayList<String> colYData = new ArrayList<>();
		for (int i = 0; i < headers.length; i++) {
			if (dropDownYValString.equals(headers[i])) {
				while((line = br2.readLine()) != null) {
					String[] values = line.split(",");
					colYData.add(values[i]);
				}
			}
		}

		for(int i=0; i < colXData.size(); i++) {
			try {
				Double colYval = Double.parseDouble(colYData.get(i));
				series1.getData().add(new XYChart.Data(colXData.get(i), colYval));
			}
			catch (Exception e) {
				System.out.println("Error converting the string to double");
			}			
		}
		

        scatterChart.getData().add(series1);
        scatterChart.setTitle("Test");
		
	}
 	
 	public void buttonClear() {
 		barChartData.getData().clear();
 		barChart1.getData().removeAll(barChartData);
 		barChart1.setTitle("");
 		
 		
 		pieChart1.getData().removeAll(pieChartData);
 		pieChartData.removeAll(pieChartData);
 		pieChart1.setTitle("");
 	}
    

}