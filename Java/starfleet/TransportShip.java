package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Set;

public class TransportShip implements Spaceship {

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
		TransportShip other = (TransportShip) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	private String name;
	private int commissionYear;
	private float maximalSpeed;
	private Set<CrewMember> crewMembers;
	private int cargoCapacity;
	private int passengerCapacity;
	private final int basicFirePower = 10;
	
	public TransportShip(String name, int commissionYear, float maximalSpeed, Set<CrewMember> crewMembers,
			int cargoCapacity, int passengerCapacity) {
		this.name = name;
		this.commissionYear = commissionYear;
		this.maximalSpeed = maximalSpeed;
		this.crewMembers = crewMembers;
		this.cargoCapacity = cargoCapacity;
		this.passengerCapacity = passengerCapacity;
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
		return this.basicFirePower;
	}

	@Override
	public Set<CrewMember> getCrewMembers() {
		return this.crewMembers;
	}

	@Override
	public int getAnnualMaintenanceCost() {
		return (3000 + (5*this.cargoCapacity) + (3*this.passengerCapacity));
	}
	
	public int getCargoCapacity(){
		return this.cargoCapacity;
	}
	
	public int getPassengerCapacity(){
		return this.passengerCapacity;
	}

	public String toString(){
		String str = "TransportShip\n";
		str = str +  "	Name="+this.getName()+"\n";
		str = str +  "	CommissionYear="+this.getCommissionYear()+"\n";
		str = str +  "	MaximalSpeed="+this.getMaximalSpeed()+"\n";
		str = str +  "	FirePower="+this.getFirePower()+"\n";
		Set<CrewMember> s = this.getCrewMembers();
		str = str +  "	CrewMembers="+s.size()+"\n";
		str = str +  "	AnnualMaintenanceCost="+this.getAnnualMaintenanceCost()+"\n";
		str = str +  "	CargoCapacity="+this.getCargoCapacity()+"\n";
		str = str +  "	PassengerCapacity="+this.getPassengerCapacity()+"\n";
		return str;

	}

}
