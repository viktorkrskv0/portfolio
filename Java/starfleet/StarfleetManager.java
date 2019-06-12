package il.ac.tau.cs.sw1.ex9.starfleet;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class StarfleetManager {

	/**
	 * Returns a list containing string representation of all fleet ships,
	 * sorted in descending order by fire power, and then in descending order by
	 * commission year
	 */
	@SuppressWarnings({})
	public static List<String> getShipDescriptionsSortedByFirePowerAndCommissionYear(Collection<Spaceship> fleet) {
		Collections.sort((List<Spaceship>) fleet, new shipCompare());
		List<String> l = new LinkedList<String>();
		Iterator<Spaceship> it = fleet.iterator();
		while (it.hasNext()) {
			l.add(it.next().toString());
		}
		return l;
	}

	/**
	 * Returns a map containing ship type names as keys (the class name) and the
	 * number of instances created for each type as values
	 */
	public static Map<String, Integer> getInstanceNumberPerClass(Collection<Spaceship> fleet) {
		Map<String, Integer> m = new HashMap<String,Integer>();
		Iterator<Spaceship> it = fleet.iterator();
		while (it.hasNext()) {
			Spaceship next = it.next();
			String name = next.getClass().getSimpleName();
			if (m.containsKey(name)) {
				m.put(name, m.get(name) + 1);
			} else {
				m.put(name, 1);
			}
		}
		return m;
	}

	/**
	 * Returns the total annual maintenance cost of the fleet (which is the sum
	 * of maintenance costs of all the fleet's ships)
	 */
	public static int getTotalMaintenanceCost(Collection<Spaceship> fleet) {
		int sum = 0;
		Iterator<Spaceship> it = fleet.iterator();
		while (it.hasNext()) {
			sum = sum + it.next().getAnnualMaintenanceCost();
		}
		return sum;
	}

	/**
	 * Returns a set containing the names of all the fleet's weapons installed
	 * on any ship
	 */
	public static Set<String> getFleetWeaponNames(Collection<Spaceship> fleet) {
		Set<String> s = new HashSet<String>();
		Iterator<Spaceship> itS = fleet.iterator();
		while (itS.hasNext()) {
			Spaceship next = itS.next();
			if (next instanceof Fighter) {
				Fighter f = (Fighter) next;
				List<Weapon> wList = f.getWeapon();
				Iterator<Weapon> itW = wList.iterator();
				while (itW.hasNext()) {
					String wName = itW.next().getName();
					if (!s.contains(wName)) {
						s.add(wName);
					}
				}
			} else if (next instanceof Bomber) {
				Bomber f = (Bomber) next;
				List<Weapon> wList = f.getWeapon();
				Iterator<Weapon> itW = wList.iterator();
				while (itW.hasNext()) {
					String wName = itW.next().getName();
					if (!s.contains(wName)) {
						s.add(wName);
					}
				}
			} else if (next instanceof StealthCruiser) {
				StealthCruiser f = (StealthCruiser) next;
				List<Weapon> wList = f.getWeapon();
				Iterator<Weapon> itW = wList.iterator();
				while (itW.hasNext()) {
					String wName = itW.next().getName();
					if (!s.contains(wName)) {
						s.add(wName);
					}
				}
			}
		}
		return s;
	}

	/*
	 * Returns the total number of crew-members serving on board of the given
	 * fleet's ships.
	 */
	public static int getTotalNumberOfFleetCrewMembers(Collection<Spaceship> fleet) {
		int sum = 0;
		Set<CrewMember> s;
		Iterator<Spaceship> it = fleet.iterator();
		while (it.hasNext()) {
			s = it.next().getCrewMembers();
			sum = sum + s.size();
		}
		return sum;
	}

	/*
	 * Returns the average age of all officers serving on board of the given
	 * fleet's ships.
	 */
	public static float getAverageAgeOfFleetOfficers(Collection<Spaceship> fleet) {
		float avg = 0f;
		int counter = 0;
		Iterator<Spaceship> itS = fleet.iterator();
		Set<CrewMember> s;
		while (itS.hasNext()) {
			Spaceship ship = itS.next();
			s = ship.getCrewMembers();
			Iterator<CrewMember> itM = s.iterator();
			while (itM.hasNext()) {
				CrewMember temp = itM.next();
				if (temp instanceof Officer) {
					avg = avg + (float) temp.getAge();
					counter++;
				}
			}
		}
		if (counter == 0) {
			return 0f;
		}
		avg = avg / ((float) counter);
		return avg;
	}

	/*
	 * Returns a map mapping the highest ranking officer on each ship (as keys),
	 * to his ship (as values).
	 */
	public static Map<Officer, Spaceship> getHighestRankingOfficerPerShip(Collection<Spaceship> fleet) {

		Map<Officer, Spaceship> map = new HashMap<Officer,Spaceship>();
		Iterator<Spaceship> itS = fleet.iterator();
		Spaceship ship;
		Set<CrewMember> s;
		Officer max = null;
		while (itS.hasNext()) {
			ship = itS.next();
			s = ship.getCrewMembers();
			Iterator<CrewMember> itM = s.iterator();
			while (itM.hasNext()) {
				CrewMember member = itM.next();
				if (member instanceof Officer) {
					if (max == null) {
						max = (Officer) member;
					} else {
						if (max.getRank().compareTo(((Officer) member).getRank()) < 0) {
							max = (Officer) member;
						}
					}
				}
			}
			map.put(max, ship);
			max = null;
		}

		return map;
	}

	/*
	 * Returns a List of entries representing ranks and their occurrences. Each
	 * entry represents a pair composed of an officer rank, and the number of
	 * its occurrences among starfleet personnel. The returned list is sorted
	 * descendingly based on the number of occurrences.
	 */
	public static List<Map.Entry<OfficerRank, Integer>> getOfficerRanksSortedByPopularity(Collection<Spaceship> fleet) {

		Map<OfficerRank, Integer> map = new HashMap<OfficerRank,Integer>();
		Iterator<Spaceship> itS = fleet.iterator();
		Spaceship ship;
		Set<CrewMember> s;
		while (itS.hasNext()) {
			ship = itS.next();
			s = ship.getCrewMembers();
			Iterator<CrewMember> itM = s.iterator();
			while (itM.hasNext()) {
				CrewMember member = itM.next();
				if (member instanceof Officer) {
					if (!map.containsKey(((Officer) member).getRank())) {
						map.put(((Officer) member).getRank(), 1);
					} else {
						map.put(((Officer) member).getRank(), map.get(((Officer) member).getRank()) + 1);
					}
				}
			}
		}

		Set<Map.Entry<OfficerRank, Integer>> m = map.entrySet();

		Iterator<Map.Entry<OfficerRank, Integer>> it = m.iterator();
		List<Map.Entry<OfficerRank, Integer>> l = new LinkedList<Entry<OfficerRank, Integer>>();
		while (it.hasNext()) {
			l.add(it.next());
		}
		Collections.sort(l, new Comparator<Map.Entry<OfficerRank, Integer>>() {
			public int compare(Map.Entry<OfficerRank, Integer> o1, Map.Entry<OfficerRank, Integer> o2) {
				return (o1.getValue().compareTo(o2.getValue()))*(-1);
			}
		});

		return l;
	}

	/*
	 * static List<Map.Entry<OfficerRank, Integer>> sortByValue(Map<OfficerRank,
	 * Integer> map) { List<Map.Entry<OfficerRank, Integer>> list = new
	 * LinkedList(map.entrySet()); Collections.sort(list, new Comparator() {
	 * public int compare(Object o1, Object o2) { return ((Comparable)
	 * ((Map.Entry<OfficerRank, Integer>) (o1)).getValue())
	 * .compareTo(((Map.Entry<OfficerRank, Integer>) (o2)).getValue()); } });
	 * return list; }
	 */
}
