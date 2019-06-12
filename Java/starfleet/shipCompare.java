package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Comparator;

public class shipCompare implements Comparator<Object>{

	@Override
	public int compare(Object arg0, Object arg1) {
		Spaceship ship0 = (Spaceship)arg0;
		Spaceship ship1 = (Spaceship)arg1;
		int firePower0 = ship0.getFirePower();
		int firePower1 = ship1.getFirePower();
		if(firePower0 > firePower1){
			return -1;
		}
		else if(firePower0 < firePower1){
			return 1;
		}
		else{
			int commissionYear0 = ship0.getCommissionYear();
			int commissionYear1 = ship1.getCommissionYear();
			if(commissionYear0 > commissionYear1){
				return -1;
			}
			else if(commissionYear0 < commissionYear1){
				return 1;
			}
			else{
				return 0;
			}
		}
	}

}
