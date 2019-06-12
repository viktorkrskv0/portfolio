package il.ac.tau.cs.sw1.ip;

import java.util.regex.Pattern;

public class IPAddressString implements IPAddress {

	private String address;

	IPAddressString(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return this.address;
	}

	@Override
	public boolean equals(IPAddress other) {
		return this.address.equals(other.toString());
	}

	@Override
	public int getOctet(int index) {
		String[] array = this.address.split(Pattern.quote("."));
		return Integer.parseInt(array[index]);
	}

	@Override
	public boolean isPrivateNetwork() {
		boolean flag = false;
		if ((this.getOctet(3) >= 0 && this.getOctet(3) <= 255) && (this.getOctet(2) >= 0 && this.getOctet(2) <= 255)) {
			if ((this.getOctet(1) >= 0 && this.getOctet(1) <= 255) && this.getOctet(0) == 10) {
				flag = true;
			}
			if ((this.getOctet(1) >= 16 && this.getOctet(1) <= 31) && this.getOctet(0) == 172) {
				flag = true;
			}
			if (this.getOctet(0) == 192 && this.getOctet(1) == 168) {
				flag = true;
			}
			if (this.getOctet(0) == 169 && this.getOctet(1) == 259) {
				flag = true;
			}
		}

		return flag;
	}

}
