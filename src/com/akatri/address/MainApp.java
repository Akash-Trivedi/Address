package com.akatri.address;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

import com.akatri.address.model.Person;
import com.akatri.address.model.PersonListWrapper;
import com.akatri.address.view.BirthdayStatisticsController;
import com.akatri.address.view.PersonEditDialogController;
import com.akatri.address.view.PersonOverviewController;
import com.akatri.address.view.RootLayoutController;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane root;

	private ObservableList<Person> personList = FXCollections.observableArrayList();

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AddressApp");
		this.primaryStage.getIcons().add(new Image("file:Resources/Images/notebook.png"));

		initRootLayout();

		showPersonOverview();

	}

	public MainApp() {
		// Add some sample data
		personList.add(new Person("Hans", "Muster"));
		personList.add(new Person("Ruth", "Mueller"));
		personList.add(new Person("Heinz", "Kurz"));
		personList.add(new Person("Cornelia", "Meier"));
		personList.add(new Person("Werner", "Meyer"));
		personList.add(new Person("Lydia", "Kunz"));
		personList.add(new Person("Anna", "Best"));
		personList.add(new Person("Stefan", "Meier"));
		personList.add(new Person("Martin", "Mueller"));

	}

	/*
	 * Initializes the RootLayout
	 */
	public void initRootLayout() {

		try {
			// ..load layout from fxml file
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));// ..provide the location where the
																					// file is
			root = (BorderPane) loader.load();// ..load it

			// ..set scene
			Scene scene = new Scene(root); // ..give it root to set it upon
			primaryStage.setScene(scene); // ..give this scene to the stage
			RootLayoutController rootController = loader.getController();
			rootController.setMainApp(this);

			primaryStage.show(); // ..unveil the stage's curtain

		} catch (IOException e) {

			e.printStackTrace();
		}
		File file = getPersonFilePath();
		if (file != null) {
			loadPersonDataFromFile(file);
		}

	}// ..method closed

	public void showBirthdayStatistics() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Stage dialog = new Stage();
			dialog.setTitle("Birthday Statistics");
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialog.setScene(scene);

			BirthdayStatisticsController controller = loader.getController();
			controller.setBirthdayFrequencyInEachMonth(personList);
			dialog.show();

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	public ObservableList<Person> getPersonData() {
		return personList;
	}

	/*
	 * Initialize the PersonOverview
	 */

	public void showPersonOverview() {
		try {

			// ..Do again

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));// ..provide the location where
																						// the file is
			AnchorPane aPane = (AnchorPane) loader.load();// ..load it

			// ..stick it to the center of root
			root.setCenter(aPane);

			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
			// controller.setImageView(imgView);

		} catch (IOException e) {

			e.printStackTrace();

		}

	}// ..method closed

	public boolean showPersonEditDialog(Person person) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
			AnchorPane page = loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Person");
			dialogStage.initOwner(primaryStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
		
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);

			PersonEditDialogController controller = loader.getController();

			controller.setDialogStage(dialogStage);
			controller.setPerson(person);
	
			dialogStage.showAndWait();
			return controller.isOkClicked();

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	public File getPersonFilePath() {
		Preferences pref = Preferences.systemNodeForPackage(MainApp.class);
		String filepath = pref.get("filepath", null);
		if (filepath != null) {
			return new File(filepath);
		} else {
			return null;
		}

	}

	public void setPersonFilePath(File file) {
		Preferences pref = Preferences.systemNodeForPackage(MainApp.class);
		if (file != null) {
			pref.put("filepath", file.getPath());
			primaryStage.setTitle("AddressApp - " + file.getName());

		} else {
			pref.remove("filepath");
			primaryStage.setTitle("AddressApp");
		}

	}

	public void loadPersonDataFromFile(File file) {

		try {

			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			PersonListWrapper persons = (PersonListWrapper) unmarshaller.unmarshal(file);
			personList.clear();
			personList.addAll(persons.getPersonList());
			setPersonFilePath(file);

		} catch (JAXBException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not load data");
			alert.setContentText("Could not load data from file:\n" + file.getPath());

			alert.showAndWait();

		}

	}

	public void loadPersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			PersonListWrapper plistwrapper = new PersonListWrapper();
			plistwrapper.setPersonList(personList);
			marshaller.marshal(plistwrapper, file);
			setPersonFilePath(file);

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Could not save data");
			alert.setContentText("Could not save data to file:\n" + file.getPath());

			alert.showAndWait();
		}

	}

	public Window getPrimaryStage() {

		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
