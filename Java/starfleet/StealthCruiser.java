package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class StealthCruiser extends Fighter {

	private static int numberOfEngines = 0;
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers,
			List<Weapon> weapons) {
		super(name, commissionYear, maximalSpeed, crewMembers, weapons);
		StealthCruiser.numberOfEngines++;
	}
	
	public StealthCruiser(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers){
		super(name, commissionYear, maximalSpeed, crewMembers, null);
		List<Weapon> weapons = new LinkedList<Weapon>();
		weapons.add(new Weapon("Laser Cannons",10,100));
		this.weaponsList = weapons;
		
		StealthCruiser.numberOfEngines++;
	}
	
	@Override 
	public int getAnnualMaintenanceCost(){
		int price = 0;
		price = super.getAnnualMaintenanceCost();
		price = price + StealthCruiser.numberOfEngines * 100;
		return price;
	}
	
	public String toString(){
		String str = "StealthCruiser\n";
		str = str +  "	Name="+this.getName()+"\n";
		str = str +  "	CommissionYear="+this.getCommissionYear()+"\n";
		str = str +  "	MaximalSpeed="+this.getMaximalSpeed()+"\n";
		str = str +  "	FirePower="+this.getFirePower()+"\n";
		Set<CrewMember> s = this.getCrewMembers();
		str = str +  "	CrewMembers="+s.size()+"\n";
		str = str +  "	AnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+"\n";
		str = str +  "	WeaponArray=[";
		List<Weapon> l = this.getWeapon();
		Iterator<Weapon> it = l.iterator();
		while(it.hasNext()){
			str = str + it.next().toString()+", ";
		}
		return str;
	}
	
	

}
