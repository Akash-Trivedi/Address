package com.akatri.address.view;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import com.akatri.address.model.Person;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class PersonEditDialogController {
	@FXML
	private TextField firstName;
	@FXML
	private TextField lastName;
	@FXML
	private TextField city;
	@FXML
	private TextField zipcode;
	@FXML
	private TextField address;
	@FXML
	private TextField birthday;
	@FXML
	private TextField contact;
	@FXML
	private Button save;
	@FXML
	private Button cancel;
	@FXML
	private Button uploadPic;
	@FXML
	private ImageView imgview;

	private boolean isOKClicked;

	private Stage stage;

	private Person person;

	@FXML
	private void handleSaving() {
		if (isValidInput()) {
			person.setfirstName(firstName.getText());
			person.setlastName(lastName.getText());
			person.setaddress(address.getText());
			person.setcity(city.getText());
			person.setcontact(contact.getText());
			person.setzipcode(Integer.parseInt(zipcode.getText()));
			person.setbirthday(LocalDate.parse(birthday.getText()));
			person.setimage(imgview.getImage());

			isOKClicked = true;
			stage.close();
		}
	}

	@FXML
	private void handleUpload() {

		FileChooser filechooser = new FileChooser();
		FileChooser.ExtensionFilter jpgFilter = new ExtensionFilter("JPG(*.JPG)", "*.JPG");
		FileChooser.ExtensionFilter pngFilter = new ExtensionFilter("PNG(*.PNG)", "*.PNG");
		FileChooser.ExtensionFilter pngFilter2 = new ExtensionFilter("png(*.png)", "*.png");
		filechooser.getExtensionFilters().addAll(jpgFilter, pngFilter, pngFilter2);
		File file = filechooser.showOpenDialog(stage);

		InputStream is = null;
		try {
			is = new BufferedInputStream(new FileInputStream(file.getPath()));
		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Invalid Image");
			alert.setHeaderText("Please Choose Valid Image");
			alert.setContentText("Error in parsing image.");
			alert.showAndWait();

		}
		Image image = new Image(is);

		imgview.setImage(image);

	}

	private boolean isValidInput() {
		StringBuilder error = new StringBuilder();
		if (firstName.getText() == null || firstName.getText().length() == 0) {
			error.append("First Name shouldn't be empty.\n");
		}
		if (lastName.getText() == null || lastName.getText().length() == 0) {
			error.append("Last Name shouldn't be empty.\n");
		}
		if (address.getText() == null || address.getText().length() == 0) {
			error.append("Enter Valid Address! \n");
		}
		if (city.getText() == null || city.getText().length() == 0) {
			error.append("Enter Valid City! \n");
		}
		if (zipcode.getText() == null || zipcode.getText().length() == 0) {
			error.append("Enter Valid ZipCode! \n");
		} else {
			try {
				int z = Integer.parseInt(zipcode.getText());
			} catch (NumberFormatException e) {
				error.append("Enter Valid ZipCode! \n");
			}
		}
		if (contact.getText() == null || contact.getText().length() == 0) {
			error.append("Enter Valid Contact! \n");
		} else {
			try {
				Long z = Long.parseLong(contact.getText());
			} catch (NumberFormatException e) {
				error.append("Enter Valid Contact! \n");
			}
		}
		if (birthday.getText() == null || birthday.getText().length() == 0) {
			error.append("Enter Valid bithday! \n");
		} else {
			try {
				LocalDate date = LocalDate.parse(birthday.getText());
			} catch (DateTimeParseException e) {
				error.append("Enter Valid bithday! \n");
			}
		}
		if (error.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(stage);
			alert.setTitle("Invalid Details");
			alert.setHeaderText("Please Correct Invalid Details");
			alert.setContentText(error.toString());
			alert.showAndWait();

			return false;
		}
	}

	@FXML
	private void handleCancelling() {
		stage.close();
	}

	public void setDialogStage(Stage dialogStage) {
		this.stage = dialogStage;

	}

	public void setPerson(Person person) {
		this.person = person;
		firstName.setText(person.getfirstName());
		lastName.setText(person.getlastName());
		address.setText(person.getaddress());
		city.setText(person.getcity());
		zipcode.setText(String.valueOf(person.getzipcode()));
		contact.setText(person.getcontact().toString());
		birthday.setText(person.getbirthday().toString());
		imgview.setImage(person.getimage());

	}

	public boolean isOkClicked() {

		return isOKClicked;
	}

}
