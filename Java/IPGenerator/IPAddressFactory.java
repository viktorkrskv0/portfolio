package il.ac.tau.cs.sw1.ip;

public class IPAddressFactory {
		
	public static IPAddress createAddress(String ip) {
		IPAddressString IPAddress = new IPAddressString(ip);
		return IPAddress;
	} 
	
	public static IPAddress createAddress(short[] ip) {
		IPAddressShort IPAddress = new IPAddressShort(ip);
		return IPAddress;
	} 
	
	public static IPAddress createAddress(int ip) {
		IPAddressInt IPAddress = new IPAddressInt(ip);
		return IPAddress;
	}
	
}
