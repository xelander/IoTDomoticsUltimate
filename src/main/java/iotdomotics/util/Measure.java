package iotdomotics.util;

public class Measure {
	//Le variabili di istanza saranno tutte stringhe perchè il metodo
	//addProperty di Individual accetta una stringa come valore della propietà
	public String hasCelsius = null;
	public String hasLumen = null;
	public String hasName = null;
	public String hasPercentageOpening = null;
	public String hasPercentageUmidity = null;
	public String hasTime = null;
	public String isConditionerOn = null;
	public String isLightOn = null;
	public String isOccupantPresence = null;
	public String isOccupantUp = null;
	public String isWindowOpen = null;
	public String powerInUsed = null;
	public String temperatureGoal = null;
	
	public Measure(){
		
	}

	public String getHasCelsius() {
		return hasCelsius;
	}

	public String getHasLumen() {
		return hasLumen;
	}

	public String getHasName() {
		return hasName;
	}

	public String getHasPercentageUmidity() {
		return hasPercentageUmidity;
	}

	public String getHasTime() {
		return hasTime;
	}

	public String getIsConditionerOn() {
		return isConditionerOn;
	}

	public String getIsLightOn() {
		return isLightOn;
	}

	public String getIsOccupantPresence() {
		return isOccupantPresence;
	}

	public String getIsOccupantUp() {
		return isOccupantUp;
	}

	public String getIsWindowOpen() {
		return isWindowOpen;
	}

	public String getPowerInUsed() {
		return powerInUsed;
	}

	public String getHasPercentageOpening() {
		return hasPercentageOpening;
	}

	public String getTemperatureGoal() {
		return temperatureGoal;
	}

	public void setTemperatureGoal(String temperatureGoal) {
		this.temperatureGoal = temperatureGoal;
	}

	public void setHasPercentageOpening(String hasPercentageOpening) {
		this.hasPercentageOpening = hasPercentageOpening;
	}

	public void setHasCelsius(String hasCelsius) {
		this.hasCelsius = hasCelsius;
	}

	public void setHasLumen(String hasLumen) {
		this.hasLumen = hasLumen;
	}

	public void setHasName(String hasName) {
		this.hasName = hasName;
	}

	public void setHasPercentageUmidity(String hasPercentageUmidity) {
		this.hasPercentageUmidity = hasPercentageUmidity;
	}

	public void setHasTime(String hasTime) {
		this.hasTime = hasTime;
	}

	public void setIsConditionerOn(String isConditionerOn) {
		this.isConditionerOn = isConditionerOn;
	}

	public void setIsLightOn(String isLightOn) {
		this.isLightOn = isLightOn;
	}

	public void setIsOccupantPresence(String isOccupantPresence) {
		this.isOccupantPresence = isOccupantPresence;
	}

	public void setIsOccupantUp(String isOccupantUp) {
		this.isOccupantUp = isOccupantUp;
	}

	public void setIsWindowOpen(String isWindowOpen) {
		this.isWindowOpen = isWindowOpen;
	}

	public void setPowerInUsed(String powerInUsed) {
		this.powerInUsed = powerInUsed;
	}

	@Override
	public String toString() {
		return "Measure [hasCelsius=" + hasCelsius + ", hasLumen=" + hasLumen + ", hasName=" + hasName 
				+ ", hasPercentageOpening=" + hasPercentageOpening + ", hasPercentageUmidity=" 
				+ hasPercentageUmidity + ", hasTime=" + hasTime + ", isConditionerOn="
				+ isConditionerOn + ", isLightOn=" + isLightOn + ", isOccupantPresence=" + isOccupantPresence
				+ ", isOccupantUp=" + isOccupantUp + ", isWindowOpen=" + isWindowOpen + ", powerInUsed=" + powerInUsed
				+ ", temperatureGoal=" + temperatureGoal + "]";
	}

}
