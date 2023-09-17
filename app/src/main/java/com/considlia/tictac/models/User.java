package com.considlia.tictac.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
	private long id;
	private String username;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;

	public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {

		@Override
		public User createFromParcel(Parcel source) {
			return new User(source);
		}

		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};

	public User() {

	}

	public User(Parcel source) {
		this.id = source.readLong();
		this.username = source.readString();
		this.firstName = source.readString();
		this.lastName = source.readString();
		this.email = source.readString();
		this.phone = source.readString();
	}
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeLong(this.id);
		dest.writeString(this.username);
		dest.writeString(this.firstName);
		dest.writeString(this.lastName);
		dest.writeString(this.email);
		dest.writeString(this.phone);
	}

	public String getFullName() {
		return this.firstName + " " + this.lastName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", userName='" + username + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", email='" + email + '\'' +
				", phone='" + phone + '\'' +
				'}';
	}
}