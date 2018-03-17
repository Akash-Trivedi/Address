package com.akatri.address.view;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import com.akatri.address.model.Person;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;

public class BirthdayStatisticsController {
	@FXML
	private BarChart<String, Integer> barChart;
	@FXML
	private CategoryAxis xAxis;
	@FXML
	private ObservableList<String> months = FXCollections.observableArrayList();

	@FXML
	public void initialize() {
		String[] monthNames = DateFormatSymbols.getInstance(Locale.ENGLISH).getMonths();
		months.addAll(Arrays.asList(monthNames));
		xAxis.setCategories(months);
		xAxis.setTickLabelRotation(270);
	}

	public void setBirthdayFrequencyInEachMonth(List<Person> personList) {
		int[] frequency = new int[12];
		for (Person p : personList) {
			int count = p.getbirthday().getMonthValue()-1;
			frequency[count]++;

		}
		XYChart.Series<String, Integer> series=new XYChart.Series<>();
		
		for(int i=0;i<frequency.length;i++ ) {
			series.getData().add(new XYChart.Data<>(months.get(i), frequency[i]));
		}
		barChart.getData().addAll(series);

	}
	
	
	
}
