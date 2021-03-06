/* ***********************************************************
 * This file was automatically generated on 2013-01-28.      *
 *                                                           *
 * Bindings Version 2.0.2                                    *
 *                                                           *
 * If you have a bugfix for this file and want to commit it, *
 * please fix the bug in the generator. You can find a link  *
 * to the generator git on tinkerforge.com                   *
 *************************************************************/

package com.tinkerforge;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Device for controlling a LCD with 2 lines a 16 characters
 */
public class BrickletLCD16x2 extends Device {
	public final static int DEVICE_IDENTIFIER = 211;

	public final static byte FUNCTION_WRITE_LINE = (byte)1;
	public final static byte FUNCTION_CLEAR_DISPLAY = (byte)2;
	public final static byte FUNCTION_BACKLIGHT_ON = (byte)3;
	public final static byte FUNCTION_BACKLIGHT_OFF = (byte)4;
	public final static byte FUNCTION_IS_BACKLIGHT_ON = (byte)5;
	public final static byte FUNCTION_SET_CONFIG = (byte)6;
	public final static byte FUNCTION_GET_CONFIG = (byte)7;
	public final static byte FUNCTION_IS_BUTTON_PRESSED = (byte)8;
	public final static byte CALLBACK_BUTTON_PRESSED = (byte)9;
	public final static byte CALLBACK_BUTTON_RELEASED = (byte)10;
	public final static byte FUNCTION_SET_CUSTOM_CHARACTER = (byte)11;
	public final static byte FUNCTION_GET_CUSTOM_CHARACTER = (byte)12;
	public final static byte FUNCTION_GET_IDENTITY = (byte)255;


	private List<ButtonPressedListener> listenerButtonPressed = new ArrayList<ButtonPressedListener>();
	private List<ButtonReleasedListener> listenerButtonReleased = new ArrayList<ButtonReleasedListener>();

	public class Config {
		public boolean cursor;
		public boolean blinking;

		public String toString() {
			return "[" + "cursor = " + cursor + ", " + "blinking = " + blinking + "]";
		}
	}

	/**
	 * This listener is triggered when a button is pressed. The parameter is
	 * the number of the button (0 to 2).
	 */
	public interface ButtonPressedListener {
		public void buttonPressed(short button);
	}

	/**
	 * This listener is triggered when a button is released. The parameter is
	 * the number of the button (0 to 2).
	 */
	public interface ButtonReleasedListener {
		public void buttonReleased(short button);
	}

	/**
	 * Creates an object with the unique device ID \c uid. and adds it to
	 * the IP Connection \c ipcon.
	 */
	public BrickletLCD16x2(String uid, IPConnection ipcon) {
		super(uid, ipcon);

		apiVersion[0] = 2;
		apiVersion[1] = 0;
		apiVersion[2] = 0;
		responseExpected[IPConnection.unsignedByte(FUNCTION_WRITE_LINE)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_CLEAR_DISPLAY)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_BACKLIGHT_ON)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_BACKLIGHT_OFF)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_BACKLIGHT_ON)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CONFIG)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CONFIG)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_IS_BUTTON_PRESSED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_SET_CUSTOM_CHARACTER)] = RESPONSE_EXPECTED_FLAG_FALSE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_CUSTOM_CHARACTER)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(FUNCTION_GET_IDENTITY)] = RESPONSE_EXPECTED_FLAG_ALWAYS_TRUE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_BUTTON_PRESSED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;
		responseExpected[IPConnection.unsignedByte(CALLBACK_BUTTON_RELEASED)] = RESPONSE_EXPECTED_FLAG_ALWAYS_FALSE;

		callbacks[CALLBACK_BUTTON_PRESSED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short button = IPConnection.unsignedByte(bb.get());

				for(ButtonPressedListener listener: listenerButtonPressed) {
					listener.buttonPressed(button);
				}
			}
		};

		callbacks[CALLBACK_BUTTON_RELEASED] = new CallbackListener() {
			public void callback(byte[] data) {
				ByteBuffer bb = ByteBuffer.wrap(data, 8, data.length - 8);
				bb.order(ByteOrder.LITTLE_ENDIAN);

				short button = IPConnection.unsignedByte(bb.get());

				for(ButtonReleasedListener listener: listenerButtonReleased) {
					listener.buttonReleased(button);
				}
			}
		};
	}

	/**
	 * Writes text to a specific line (0 to 1) with a specific position 
	 * (0 to 15). The text can have a maximum of 16 characters.
	 * 
	 * For example: (0, 5, "Hello") will write *Hello* in the middle of the
	 * first line of the display.
	 * 
	 * The display uses a special charset that includes all ASCII characters except
	 * backslash and tilde. The LCD charset also includes several other non-ASCII characters, see
	 * the `charset specification <https://github.com/Tinkerforge/lcd-16x2-bricklet/raw/master/datasheets/standard_charset.pdf>`__
	 * for details. The Unicode example above shows how to specify non-ASCII characters
	 * and how to translate from Unicode to the LCD charset.
	 */
	public void writeLine(short line, short position, String text) throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_WRITE_LINE);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)26, FUNCTION_WRITE_LINE, options, (byte)(0));
		bb.put((byte)line);
		bb.put((byte)position);
		for(int i = 0; i < 16; i++) {
			try {
				bb.put((byte)text.charAt(i));
			} catch(Exception e) {
				bb.put((byte)0);
			}
		}


		if(isResponseExpected) {
			byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_WRITE_LINE);

			bb = ByteBuffer.wrap(response, 8, response.length - 8);
			bb.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			sendRequestNoResponse(bb.array());
		}
	}

	/**
	 * Deletes all characters from the display.
	 */
	public void clearDisplay() throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_CLEAR_DISPLAY);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)8, FUNCTION_CLEAR_DISPLAY, options, (byte)(0));

		if(isResponseExpected) {
			byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_CLEAR_DISPLAY);

			bb = ByteBuffer.wrap(response, 8, response.length - 8);
			bb.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			sendRequestNoResponse(bb.array());
		}
	}

	/**
	 * Turns the backlight on.
	 */
	public void backlightOn() throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_BACKLIGHT_ON);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)8, FUNCTION_BACKLIGHT_ON, options, (byte)(0));

		if(isResponseExpected) {
			byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_BACKLIGHT_ON);

			bb = ByteBuffer.wrap(response, 8, response.length - 8);
			bb.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			sendRequestNoResponse(bb.array());
		}
	}

	/**
	 * Turns the backlight off.
	 */
	public void backlightOff() throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_BACKLIGHT_OFF);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)8, FUNCTION_BACKLIGHT_OFF, options, (byte)(0));

		if(isResponseExpected) {
			byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_BACKLIGHT_OFF);

			bb = ByteBuffer.wrap(response, 8, response.length - 8);
			bb.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			sendRequestNoResponse(bb.array());
		}
	}

	/**
	 * Returns *true* if the backlight is on and *false* otherwise.
	 */
	public boolean isBacklightOn() throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_IS_BACKLIGHT_ON);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)8, FUNCTION_IS_BACKLIGHT_ON, options, (byte)(0));

		byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_IS_BACKLIGHT_ON);

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean backlight = (bb.get()) != 0;

		return backlight;
	}

	/**
	 * Configures if the cursor (shown as "_") should be visible and if it
	 * should be blinking (shown as a blinking block). The cursor position
	 * is one character behind the the last text written with 
	 * {@link com.tinkerforge.BrickletLCD16x2.writeLine}.
	 * 
	 * The default is (false, false).
	 */
	public void setConfig(boolean cursor, boolean blinking) throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_SET_CONFIG);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)10, FUNCTION_SET_CONFIG, options, (byte)(0));
		bb.put((byte)(cursor ? 1 : 0));
		bb.put((byte)(blinking ? 1 : 0));

		if(isResponseExpected) {
			byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_SET_CONFIG);

			bb = ByteBuffer.wrap(response, 8, response.length - 8);
			bb.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			sendRequestNoResponse(bb.array());
		}
	}

	/**
	 * Returns the configuration as set by {@link com.tinkerforge.BrickletLCD16x2.setConfig}.
	 */
	public Config getConfig() throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_GET_CONFIG);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)8, FUNCTION_GET_CONFIG, options, (byte)(0));

		byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_GET_CONFIG);

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Config obj = new Config();
		obj.cursor = (bb.get()) != 0;
		obj.blinking = (bb.get()) != 0;

		return obj;
	}

	/**
	 * Returns *true* if the button (0 to 2) is pressed. If you want to react
	 * on button presses and releases it is recommended to use the
	 * {@link com.tinkerforge.BrickletLCD16x2.ButtonPressedListener} and {@link com.tinkerforge.BrickletLCD16x2.ButtonReleasedListener} listeners.
	 */
	public boolean isButtonPressed(short button) throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_IS_BUTTON_PRESSED);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)9, FUNCTION_IS_BUTTON_PRESSED, options, (byte)(0));
		bb.put((byte)button);

		byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_IS_BUTTON_PRESSED);

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		boolean pressed = (bb.get()) != 0;

		return pressed;
	}

	/**
	 * The LCD 16x2 Bricklet can store up to 8 custom characters. The characters
	 * consist of 5x8 pixels and can be addressed with the index 0-7. To describe
	 * the pixels, the first 5 bits of 8 bytes are used. For example, to make
	 * a custom character "H", you should transfer the following:
	 * 
	 * * character[0] = 0b00010001 (decimal value 17)
	 * * character[1] = 0b00010001 (decimal value 17)
	 * * character[2] = 0b00010001 (decimal value 17)
	 * * character[3] = 0b00011111 (decimal value 31)
	 * * character[4] = 0b00010001 (decimal value 17)
	 * * character[5] = 0b00010001 (decimal value 17)
	 * * character[6] = 0b00010001 (decimal value 17)
	 * * character[7] = 0b00000000 (decimal value 0)
	 * 
	 * The characters can later be written with {@link com.tinkerforge.BrickletLCD16x2.writeLine} by using the
	 * characters with the byte representation 8 to 15.
	 * 
	 * You can play around with the custom characters in Brick Viewer version
	 * >= 2.0.1.
	 * 
	 * Custom characters are stored by the LCD in RAM, so they have to be set
	 * after each startup.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public void setCustomCharacter(short index, short[] character) throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_SET_CUSTOM_CHARACTER);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)17, FUNCTION_SET_CUSTOM_CHARACTER, options, (byte)(0));
		bb.put((byte)index);
		for(int i = 0; i < 8; i++) {
			bb.put((byte)character[i]);
		}


		if(isResponseExpected) {
			byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_SET_CUSTOM_CHARACTER);

			bb = ByteBuffer.wrap(response, 8, response.length - 8);
			bb.order(ByteOrder.LITTLE_ENDIAN);
		} else {
			sendRequestNoResponse(bb.array());
		}
	}

	/**
	 * Returns the custon character for a given index, as set with 
	 * {@link com.tinkerforge.BrickletLCD16x2.setCustomCharacter}.
	 * 
	 * .. versionadded:: 2.0.1~(Plugin)
	 */
	public short[] getCustomCharacter(short index) throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_GET_CUSTOM_CHARACTER);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)9, FUNCTION_GET_CUSTOM_CHARACTER, options, (byte)(0));
		bb.put((byte)index);

		byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_GET_CUSTOM_CHARACTER);

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		short[] character = new short[8];
		for(int i = 0; i < 8; i++) {
			character[i] = IPConnection.unsignedByte(bb.get());
		}


		return character;
	}

	/**
	 * Returns the UID, the UID where the Bricklet is connected to, 
	 * the position, the hardware and firmware version as well as the
	 * device identifier.
	 * 
	 * The position can be 'a', 'b', 'c' or 'd'.
	 * 
	 * The device identifiers can be found :ref:`here <device_identifier>`.
	 * 
	 * .. versionadded:: 2.0.0~(Plugin)
	 */
	public Identity getIdentity() throws TimeoutException, NotConnectedException {
		byte options = 0;
		boolean isResponseExpected = getResponseExpected(FUNCTION_GET_IDENTITY);
		if(isResponseExpected) {
			options = 8;
		}
		ByteBuffer bb = ipcon.createRequestBuffer(uid, (byte)8, FUNCTION_GET_IDENTITY, options, (byte)(0));

		byte[] response = sendRequestExpectResponse(bb.array(), FUNCTION_GET_IDENTITY);

		bb = ByteBuffer.wrap(response, 8, response.length - 8);
		bb.order(ByteOrder.LITTLE_ENDIAN);

		Identity obj = new Identity();
		obj.uid = IPConnection.string(bb, 8);
		obj.connectedUid = IPConnection.string(bb, 8);
		obj.position = (char)(bb.get());
		for(int i = 0; i < 3; i++) {
			obj.hardwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}

		for(int i = 0; i < 3; i++) {
			obj.firmwareVersion[i] = IPConnection.unsignedByte(bb.get());
		}

		obj.deviceIdentifier = IPConnection.unsignedShort(bb.getShort());

		return obj;
	}

	/**
	 * Adds a ButtonPressed listener.
	 */
	public void addButtonPressedListener(ButtonPressedListener listener) {
		listenerButtonPressed.add(listener);
	}

	/**
	 * Removes a ButtonPressed listener.
	 */
	public void removeButtonPressedListener(ButtonPressedListener listener) {
		listenerButtonPressed.remove(listener);
	}

	/**
	 * Adds a ButtonReleased listener.
	 */
	public void addButtonReleasedListener(ButtonReleasedListener listener) {
		listenerButtonReleased.add(listener);
	}

	/**
	 * Removes a ButtonReleased listener.
	 */
	public void removeButtonReleasedListener(ButtonReleasedListener listener) {
		listenerButtonReleased.remove(listener);
	}
}