package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;
import java.util.Iterator;
import java.util.List;

public class Bomber implements Spaceship {

	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private final int basicFirePower = 10;
	private Set<CrewMember> crewMembers;
	private List<Weapon> weapons;
	private int numberOfTechnicians;

	public Bomber(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers,
			List<Weapon> weapons, int numberOfTechnicians) {
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
		this.weapons = weapons;
		this.numberOfTechnicians = numberOfTechnicians; 
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int getCommissionYear() {
		return this.commissionYear;
	}

	@Override
	public float getMaximalSpeed() {
		return this.maximalSpeed;
	}

	@Override
	public int getFirePower() {
		int firePowerSum = 0;
		firePowerSum = basicFirePower;

		List<Weapon> l = this.getWeapon();
		Iterator<Weapon> it = l.iterator();

		while (it.hasNext()) {
			firePowerSum = firePowerSum + it.next().getFirePower();
		}

		return firePowerSum;
	}

	@Override
	public Set<CrewMember> getCrewMembers() {
		return this.crewMembers;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		int price = 5000;
		int weaponsPrice = 0;
		List<Weapon> l = this.getWeapon();
		Iterator<Weapon> it = l.iterator();

		while (it.hasNext()) {
			weaponsPrice = weaponsPrice + it.next().getAnnualMaintenanceCost();
		}

		float temp1 = (float) weaponsPrice, temp2 = (float) price;
		temp2 = temp2 + temp1;
		temp1 = temp1 * ((float) this.getNumberOfTechnicians() / 10);
		temp2 = temp2 - temp1;
		price = Math.round(temp2);

		return price;
	}

	public List<Weapon> getWeapon() {
		return this.weapons;
	}

	public int getNumberOfTechnicians() {
		return this.numberOfTechnicians;
	}

	public String toString(){
		String str = "Bomber\n";
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
		str = str + "\n";
		str = str + "	NumberOfTechnicians="+this.getNumberOfTechnicians();
		return str;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bomber other = (Bomber) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
