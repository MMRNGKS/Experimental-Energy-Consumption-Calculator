package saveEnergy;
//import energyConsumption.UiEnergy;

public class StoreEnergy {
		private double origElec, origUsed;
		
	
	public void origBill(double elecBill, double elecUsed) {
		origElec = elecBill;
		origUsed = elecUsed;
	}
	
	public double getOrigBill() {
		return origElec;
	}
	
	public double getOrigUsed() {
		return origUsed;
	}
		
}
