package com.gmail.infomaniac50.cameraoverlay;

public class ARItemModel {

	public ARItemModel(String name, String address, int rating) {
		this.name = name;
		this.address = address;
		this.rating = rating;
	}

	private String name;
	private String address;
	private int rating;
	
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getRatingText() {
		return "Rating: ".concat(String.valueOf(rating));
	}
}
