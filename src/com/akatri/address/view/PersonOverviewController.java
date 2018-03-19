package com.akatri.address.view;

import com.akatri.address.MainApp;
import com.akatri.address.model.Person;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

public class PersonOverviewController {
	@FXML
	private TableView<Person> personTable;
	@FXML
	private TableColumn<Person, String> firstNameColumn;
	@FXML
	private TableColumn<Person, String> lastNameColumn;
	@FXML
	private Label firstName;
	@FXML
	private Label lastName;
	@FXML
	private Label city;
	@FXML
	private Label zipcode;
	@FXML
	private Label address;
	@FXML
	private Label birthday;
	@FXML
	private Label contact;

	@FXML
	private Label occupation;

	@FXML
	private Button delete;
	@FXML
	private Button add;
	@FXML
	private Button edit;
	@FXML
	private ImageView imgview;

	private MainApp mainApp;

	public PersonOverviewController() {

	}

	@FXML
	private void initialize() {
		firstNameColumn.setCellValueFactory(celldata -> celldata.getValue().getfirstNameProperty());
		lastNameColumn.setCellValueFactory(celldata -> celldata.getValue().getlastNameProperty());
		showPersonDetails(null);
		personTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));

	}

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		personTable.setItems(mainApp.getPersonData());

	}

	public void showPersonDetails(Person person) {
		if (person != null) {
			firstName.setText(person.getfirstName());
			lastName.setText(person.getlastName());
			city.setText(person.getcity());
			zipcode.setText(Integer.toString(person.getzipcode()));
			address.setText(person.getaddress());
			birthday.setText(person.getbirthday().toString());
			contact.setText(person.getcontact());
			occupation.setText(person.getoccupation());
			imgview.setImage(person.getimage());
		} else {
			firstName.setText("");
			lastName.setText("");
			city.setText("");
			zipcode.setText("");
			address.setText("");
			birthday.setText("");
			contact.setText("");
			occupation.setText("");
			imgview.setImage(null);
		}

	}

	@FXML
	private void handleDeletion() {
		int index = personTable.getSelectionModel().getSelectedIndex();
		if (index >= 0) {
			personTable.getItems().remove(index);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Person Selected");
			alert.setContentText("Please Select The Person In The Table");
			alert.showAndWait();
		}

	}

	@FXML
	private void handleEditPerson() {

		Person editable = personTable.getSelectionModel().getSelectedItem();
		if (editable != null) {
			boolean isValidPerson = mainApp.showPersonEditDialog(editable);
			if (isValidPerson) {
				mainApp.getPersonData().add(editable);
			}
		}
	}

	@FXML
	private void handleNewPerson() {

		Person temp = new Person();
		boolean isValidPerson = mainApp.showPersonEditDialog(temp);
		if (isValidPerson) {
			mainApp.getPersonData().add(temp);
		}

	}

}
