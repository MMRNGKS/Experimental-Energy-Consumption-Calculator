package energyConsumption;

import java.io.File;
import java.io.IOException;

public class MainEnergy {

	public static void main(String[] args) {
		UiEnergy u = new UiEnergy();
		u.uiMain();
		
		try {
		      File myObj = new File("D:\\Eclipse\\workspaze\\Assignment16\\src\\Appliances.csv");
		      if (myObj.createNewFile()) {
		        System.out.println("File created: " + myObj.getName());
		      } else {
		        System.out.println("File already exists.");
		      }
		    } catch (IOException exp) {
		    	exp.printStackTrace();
		    }
	}

}
