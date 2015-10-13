package org.devoxx4kids;

import java.util.HashMap;
import java.util.Map;

import com.tinkerforge.IPConnection;

/**
 * Created by alexanderbischof on 23.09.14.
 */
public class BrickletReader {

	public static final String HOST = "localhost";
	public static final int PORT = 4223;

	// Mapping von DeviceIdentifier auf Strings
	public final static Map<Integer, String> uidDeviceMap = new HashMap<>();

	static {
		uidDeviceMap.put(com.tinkerforge.BrickMaster.DEVICE_IDENTIFIER, "Masterbrick");
		uidDeviceMap.put(com.tinkerforge.BrickletVoltageCurrent.DEVICE_IDENTIFIER, "Strom / Spannung Bricket");
		uidDeviceMap.put(com.tinkerforge.BrickletIndustrialQuadRelay.DEVICE_IDENTIFIER, "Quad-Relay Bricklet");
	}

	private Map<Integer, Bricklet> brickletMap = new HashMap<>();

	/**
	 * Reads the bricklets from default connection parameter:
	 * <ul>
	 * <li>{@value #HOST}
	 * <li>{@value #PORT}
	 * </ul>
	 */
	public BrickletReader readBricklets() throws Exception {
		return readBricklets(HOST, PORT);
	}

	public BrickletReader readBricklets(String host, int port) throws Exception {
		IPConnection ipcon = new IPConnection();
		ipcon.connect(host, port);

		// Register enumerate listener and print incoming information
		ipcon.addEnumerateListener(new IPConnection.EnumerateListener() {
			@Override
			public void enumerate(String uid, String connectedUid, char position, short[] hardwareVersion,
					short[] firmwareVersion, int deviceIdentifier, short enumerationType) {
				if (enumerationType == IPConnection.ENUMERATION_TYPE_DISCONNECTED) {
					return;
				}

				brickletMap.put(deviceIdentifier, new Bricklet(uidDeviceMap.get(deviceIdentifier), uid));
			}
		});

		ipcon.enumerate();

		// Wait to get result
		Thread.sleep(2000l);
		ipcon.disconnect();

		return this;
	}

	public Map<Integer, Bricklet> getBrickletMap() {
		return brickletMap;
	}

	public Bricklet getBrickletByDeviceId(int deviceId) {
		return brickletMap.get(deviceId);
	}
}
