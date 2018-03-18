package com.akatri.address.view;

import java.io.File;

import com.akatri.address.MainApp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;

public class RootLayoutController {
	private MainApp mainApp;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;

	}

	@FXML
	private void handleNew() {
		mainApp.getPersonData().clear();
		mainApp.setPersonFilePath(null);
	}

	@FXML
	private void handleSave() {
		File file = mainApp.getPersonFilePath();
		if (file != null) {
			mainApp.loadPersonDataToFile(file);
		} else {
			handleSaveAs();
		}
	}
	@FXML
	private void handleShowStatistics() {
		mainApp.showBirthdayStatistics();
	}

	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files(*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());
		if (file != null) {
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.loadPersonDataToFile(file);
		}
	}

	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML files(*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());
		if (file != null) {
			mainApp.loadPersonDataFromFile(file);
		}
	}

	@FXML
	private void handleAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("AddressApp");
		alert.setHeaderText("About");
		alert.setContentText("Author: Akash Trivedi\nWebsite: http://comingsoon..");

		alert.showAndWait();
	}

	@FXML
	private void handleExit() {
		mainApp.getPrimaryStage().hide();
	}

}
