package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
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
    private LineChart<String, Number> lineChart;
    
    XYChart.Series lineChartData = new XYChart.Series();
    
    @FXML
    private TextField txt1Field;
    
    @FXML
    private ScatterChart<?, ?> scatterChart;
    
    @FXML
    private CategoryAxis xAxis;
    
    @FXML
    private NumberAxis yAxis;
    
    @FXML
    private CategoryAxis xAxisLine;

    @FXML
    private NumberAxis yAxisLine;
        
    ObservableList<String> featureList = FXCollections.observableArrayList();
    
    private String[] headers;
    
    XYChart.Series barChartData = new XYChart.Series();
    XYChart.Series scatterChartData = new XYChart.Series();

    
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
    	
    	String path = null;
		String[] headers = null;
		
		fc.getExtensionFilters().addAll(new ExtensionFilter("CSV Files", "*.csv"));	
		File selectedFile = fc.showOpenDialog(null);
		
		
		try {
			path = selectedFile.getAbsolutePath();
			headers = readHeader(path);
		} catch (IOException e) {
			System.out.println("File not read properly, please select file from the dialog box");
		}
    	
    	// Use sets here to eliminate the headers data
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
 		
 		pieChart1.setAnimated(false);	 		
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
 		ArrayList<Double> numericList = new ArrayList<>();
 		ArrayList<Integer> countList = new ArrayList<>();
 		
 		rowList.clear();
 		categoryList.clear();
 		numericList.clear();
 		countList.clear();
 		
 		barChart1.setAnimated(false);	 		
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
			
			
			if (rowList.get(0).charAt(0)== '-' || rowList.get(0).charAt(0)== '0' || rowList.get(0).charAt(0)== '1' || rowList.get(0).charAt(0)== '2'
					 || rowList.get(0).charAt(0)== '3' || rowList.get(0).charAt(0)== '4' || rowList.get(0).charAt(0)== '5' || rowList.get(0).charAt(0)== '6'
					 || rowList.get(0).charAt(0)== '7' || rowList.get(0).charAt(0)== '8' || rowList.get(0).charAt(0)== '9') {
				
				numericList.add(Double.valueOf(rowList.get(0)));
 				
 				for (int m = 1; m < rowList.size(); m++) {
 					
 					int times = 0;
 					for (int b = 0; b < numericList.size(); b++) {
 						
 						double s = Double.valueOf(rowList.get(m));
 						double v = numericList.get(b); 
 						
 						
 						if (s != v) {
 							
 							times++;
 								
 						}
 						
 					}
 					 	 					
 					if (times == numericList.size()) {
 						numericList.add(Double.valueOf(rowList.get(m)));
 						
 					}
 				}
 				
 				Collections.sort(numericList);
 				
 				
 				for (int s = 0; s < numericList.size(); s++) {
 					
 					int count = 0;
 					for (int n = 0; n < rowList.size(); n++) {
 						
 						double z = Double.valueOf(rowList.get(n));
 						
 						double w = numericList.get(s);
 						;
 						 	 						
 						if (z == w) {
 							
 							count++;
 							
 							}
 						
 					}
 					
 					
 					countList.add(count);
 				
 					
 					
 				}
			}else {
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
 				
 				
			}
			
			
	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (numericList.size() > categoryList.size()) {
				for (int i = 0; i < numericList.size(); i++) {
					barChartData.getData().addAll(new XYChart.Data(String.valueOf(numericList.get(i)), countList.get(i)));
 	 	       }
 	 	       
 	 	              
 	 	       
 	 	       if (barChart1 != null) { 
 	 	    	   barChart1.getData().add(barChartData);
 	 	    	   barChart1.setTitle(featureBox.getValue());
 	 	       }else {
 	 	    	   System.out.println("It is null!!!!!");
 	 	       }
			}else {
				for (int i = 0; i < categoryList.size(); i++) {
			    	   barChartData.getData().add(new XYChart.Data(categoryList.get(i), countList.get(i)));
			       }
			       
			              
			       
			       if (barChart1 != null) {
			    	   
			    	   barChart1.getData().addAll(barChartData);
			    	   barChart1.setTitle(featureBox.getValue());
			       }else {
			    	   System.out.println("It is null!!!!!");
			       }
			}
	}
 	
 	public void showScatterPlot() throws IOException {
 		
        scatterChart.setAnimated(false);
 		scatterChartData.getData().clear();
 		scatterChart.getData().removeAll(scatterChartData);
 		scatterChart.setTitle("");
        
        ArrayList<String> colXData = new ArrayList<>();
        ArrayList<String> colYData = new ArrayList<>();
		ArrayList<String> newColXData = new ArrayList<String>();
		ArrayList<Double> tempList = new ArrayList<Double>();

        colXData.clear();
        colYData.clear();
        newColXData.clear();
        tempList.clear();
        
		String dropDownXValString = featureBox.getValue();
		String dropDownYValString = featureBox2.getValue();
				
	    xAxis.setLabel(dropDownXValString);  
	    yAxis.setLabel(dropDownYValString);
	    
	    // file objs to read it multiple timesfor colx and coly
	    HashMap<String, BufferedReader> fileObjs = new HashMap<String, BufferedReader>();
	    fileObjs.clear();
	    fileObjs.put("xAxisReader", new BufferedReader(new FileReader(txt1Field.getText())));
	    fileObjs.put("yAxisReader", new BufferedReader(new FileReader(txt1Field.getText())));
		
		String line = fileObjs.get("xAxisReader").readLine();
		String[] headers = line.split(",");
		
		// Read x col data
		for (int i = 0; i < headers.length; i++) {
			if (dropDownXValString.equals(headers[i])) {
				while((line = fileObjs.get("xAxisReader").readLine()) != null) {
					String[] values = line.split(",");
					colXData.add(values[i]);
				}
			}
		}

		// Read Y col data
		String dummyRead = fileObjs.get("yAxisReader").readLine();
		for (int i = 0; i < headers.length; i++) {
			if (dropDownYValString.equals(headers[i])) {
				while((line = fileObjs.get("yAxisReader").readLine()) != null) {
					String[] values = line.split(",");
					colYData.add(values[i]);
				}
			}
		}
		
		// Sort the array based on the data type
		if (colXData.get(0).charAt(0)== '-' || colXData.get(0).charAt(0)== '0' || colXData.get(0).charAt(0)== '1' || colXData.get(0).charAt(0)== '2'
				 || colXData.get(0).charAt(0)== '3' || colXData.get(0).charAt(0)== '4' || colXData.get(0).charAt(0)== '5' || colXData.get(0).charAt(0)== '6'
				 || colXData.get(0).charAt(0)== '7' || colXData.get(0).charAt(0)== '8' || colXData.get(0).charAt(0)== '9') {
			
			for (int i=0; i < colXData.size(); i++) { 
				tempList.add(Double.valueOf(colXData.get(i))); 
	        }

			Collections.sort(tempList);
			
			for (int i=0; i < tempList.size(); i++) { 
				newColXData.add(String.valueOf(tempList.get(i))); 
	        }			
		}
				
		// Scatterplot is for only numerical x and y axes
		for(int i=0; i < newColXData.size(); i++) {
			Double colYval = Double.parseDouble(colYData.get(i));
			scatterChartData.getData().add(new XYChart.Data(newColXData.get(i), colYval));
		}
		
        scatterChart.getData().add(scatterChartData);
	}
 	
 	@SuppressWarnings({ "unchecked", "rawtypes" })
 	public void showLineChart() {
 			
 			ArrayList<String> rowList = new ArrayList<>();
	 		ArrayList<String> categoryList = new ArrayList<>();
	 		ArrayList<Double> numericList = new ArrayList<>();
	 		ArrayList<Integer> countList = new ArrayList<>();
	 		
	 		rowList.clear();
	 		categoryList.clear();
	 		numericList.clear();
	 		countList.clear();
	 		
	 		lineChart.setAnimated(false);
	 		lineChartData.getData().clear();
	 		
	 		final NumberAxis xAxisLine = new NumberAxis();
	        final NumberAxis yAxisLine = new NumberAxis();
	 		
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
				
				
				if (rowList.get(0).charAt(0)== '-' || rowList.get(0).charAt(0)== '0' || rowList.get(0).charAt(0)== '1' || rowList.get(0).charAt(0)== '2'
						 || rowList.get(0).charAt(0)== '3' || rowList.get(0).charAt(0)== '4' || rowList.get(0).charAt(0)== '5' || rowList.get(0).charAt(0)== '6'
						 || rowList.get(0).charAt(0)== '7' || rowList.get(0).charAt(0)== '8' || rowList.get(0).charAt(0)== '9') {
					
					numericList.add(Double.valueOf(rowList.get(0)));
	 				
	 				for (int m = 1; m < rowList.size(); m++) {
	 					
	 					int times = 0;
	 					for (int b = 0; b < numericList.size(); b++) {
	 						double s = Double.valueOf(rowList.get(m));
	 						double v = numericList.get(b); 
	 						
	 						if (s != v) {
	 							times++;		
	 						}	
	 					}
	 					if (times == numericList.size()) {
	 						numericList.add(Double.valueOf(rowList.get(m)));
	 					}
	 				}
	 				
	 				Collections.sort(numericList);
	 				for (int s = 0; s < numericList.size(); s++) {
	 					int count = 0;
	 					for (int n = 0; n < rowList.size(); n++) {
	 						double z = Double.valueOf(rowList.get(n));
	 						double w = numericList.get(s);				
	 						if (z == w) {
	 							count++;
	 							}	
	 					}
	 					countList.add(count);	
	 				}
				}else {
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
	 				
	 				
				}
				
				
		
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	       
			if (numericList.size() > categoryList.size()) {
				for (int i = 0; i < numericList.size(); i++) {
 	 	    	   lineChartData.getData().addAll(new XYChart.Data(String.valueOf(numericList.get(i)), countList.get(i)));
 	 	       }
 	 	       
 	 	              
 	 	       
 	 	       if (lineChart != null) { 
 	 	    	   lineChart.getData().add(lineChartData);
 	 	    	   lineChart.setTitle(featureBox.getValue());
 	 	       }else {
 	 	    	   System.out.println("It is null!!!!!");
 	 	       }
			}else {
				for (int i = 0; i < categoryList.size(); i++) {
 	 	    	   lineChartData.getData().addAll(new XYChart.Data(categoryList.get(i), countList.get(i)));
 	 	       }
 	 	       
 	 	              
 	 	       
 	 	       if (lineChart != null) { 
 	 	    	   lineChart.getData().add(lineChartData);
 	 	    	   lineChart.setTitle(featureBox.getValue());
 	 	       }else {
 	 	    	   System.out.println("It is null!!!!!");
 	 	       }
			}   
		}
 	
 	public void buttonClear() {
 		barChartData.getData().clear();
 		barChart1.getData().removeAll(barChartData);
 		barChart1.setTitle("");
 		
 		
 		pieChart1.getData().removeAll(pieChartData);
 		pieChartData.removeAll(pieChartData);
 		pieChart1.setTitle("");
 		
 		scatterChartData.getData().clear();
 		scatterChart.getData().removeAll(scatterChartData);
 		scatterChart.setTitle("");
 		
 		lineChart.getData().clear();
 		lineChartData.getData().removeAll(scatterChartData);
 		lineChart.setTitle("");
 		
 	}
    

}