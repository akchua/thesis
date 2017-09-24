package com.thesis.tremor.enums;

public enum FingerType {

	THUMB("Thumb"),
	
	INDEX("Index"),
	
	MIDDLE("Middle"),
	
	RING("Ring"),
	
	PINKY("Pinky"),
	
	ROOT("Root");
	
	private final String displayName;
	
	FingerType(final String displayName) {
		this.displayName = displayName;
	}
	
	public String getName() {
		return toString();
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
