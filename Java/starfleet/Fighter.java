package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Fighter implements Spaceship {

	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private final int basicFirePower = 10;
	protected List<Weapon> weaponsList;
	private Set<CrewMember> crewMembers;
	private int basicCost = 2500;

	public Fighter(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers,
			List<Weapon> weapons) {
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
		this.weaponsList = weapons;
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
		int firePowerSum = this.basicFirePower;
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
		int price = this.basicCost;
		List<Weapon> l = this.getWeapon();
		Iterator<Weapon> it = l.iterator();
		while (it.hasNext()) {
			price = price + it.next().getAnnualMaintenanceCost();
		}
		int enginePrice = 1000 * Math.round(this.maximalSpeed);
		price = price + enginePrice;
		return price;
	}

	public List<Weapon> getWeapon() {
		return this.weaponsList;
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
		Fighter other = (Fighter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public String toString(){
		String str = "Fighter\n";
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
