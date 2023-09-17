package com.considlia.tictac.models;

import com.google.gson.annotations.SerializedName;

public class Address {

	@SerializedName("addressId")
	private Long addressId;

	@SerializedName("city")
	private String city;

	@SerializedName("street")
	private String street;

	@SerializedName("postalCode")
	private String postalCode;

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreet() {
		return street;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPostalCode() {
		return postalCode;
	}

	@Override
	public String toString() {
		return "Address{" +
				"addressId=" + addressId +
				", city='" + city + '\'' +
				", street='" + street + '\'' +
				", postalCode=" + postalCode +
				'}';
	}
}