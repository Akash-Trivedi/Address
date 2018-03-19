package com.akatri.address.model;

import java.time.LocalDate;
import java.time.Month;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.akatri.address.util.ImageAdapter;
import com.akatri.address.util.LocalDateAdapter;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Person {
	private final StringProperty firstName;
	private final StringProperty lastName;
	private final StringProperty address;
	private final IntegerProperty zipCode;
	private final SimpleStringProperty contact;
	private final StringProperty city;
	private final StringProperty occupation;
	private final ObjectProperty<LocalDate> birthday;
	private final ObjectProperty<Image> image;

	public Person() {
		this(null, null);
	}

	public Person(String firstName, String lastName) {

		this.firstName = new SimpleStringProperty(firstName);
		this.lastName = new SimpleStringProperty(lastName);
		this.address = new SimpleStringProperty("some address");
		this.city = new SimpleStringProperty("some city");
		this.contact = new SimpleStringProperty("1234567890");
		this.zipCode = new SimpleIntegerProperty(123456);
		this.occupation=new SimpleStringProperty("Occupation..");
		this.birthday = new SimpleObjectProperty<LocalDate>(LocalDate.of(1993, Month.JANUARY, 6));
		this.image = new SimpleObjectProperty<Image>(null);
	}

	public String getfirstName() {
		return firstName.get();
	}

	public String getlastName() {
		return lastName.get();
	}

	public String getaddress() {
		return address.get();
	}

	public int getzipcode() {
		return zipCode.get();
	}

	public String getcontact() {
		return contact.get();
	}

	public String getcity() {
		return city.get();
	}

	public void setfirstName(String firstName) {
		this.firstName.set(firstName);
		;
	}

	public void setlastName(String lastName) {
		this.lastName.set(lastName);
	}

	public void setaddress(String address) {
		this.address.set(address);
	}

	public void setzipcode(int zipCode) {
		this.zipCode.set(zipCode);
	}

	public void setcontact(String contact) {
		this.contact.set(contact);
	}

	public void setcity(String city) {
		this.city.set(city);
	}

	public void setoccupation(String occupation) {
		this.occupation.set(occupation);
	}

	public String getoccupation() {
		return occupation.get();
	}

	@XmlJavaTypeAdapter(LocalDateAdapter.class)
	public LocalDate getbirthday() {
		return birthday.get();
	}

	public StringProperty getfirstNameProperty() {

		return firstName;
	}

	public StringProperty getlastNameProperty() {

		return lastName;
	}

	public void setbirthday(LocalDate birthday) {

		this.birthday.set(birthday);
	}

	public void setimage(Image image) {

		this.image.set(image);
	}

	@XmlJavaTypeAdapter(ImageAdapter.class)
	public Image getimage() {
		return image.get();

	}

}
