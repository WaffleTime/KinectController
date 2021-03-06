/*
This program is called "Kinect Controller". It is meant to detect gestures with the Kinect
and then simulate keyboard and/or mouse input. The configuration files used by this program are
not intended to be under the following license.

By using Esper without their commercial license we are also required to release our software under
a GPL license.

Copyright (C) 2015  Jacob Waffle, Ryan Spiekerman and Josh Rogers

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
*/

package com.wafflesoft.kinectcontroller;

import com.primesense.nite.JointType;

import java.awt.event.*;
import java.lang.reflect.*;

public class Conversions {
	
	public Conversions() {
    }

	/**
	 * This is to convert the keyIds that are defined in the .gdef config file into an integer that's compatible
	 * with the Robot module.
	 * @param key This string can be any field defined within the java.awt.event.KeyEvent class. Except, the 'VK_' prefix is
	 *            assumed to not be included -- it is prepended onto the key string before we fetch the value.
	 * @return An integer that conforms to the fields within java.awt.event.KeyEvent for the given key.
	 */
	public static int getKeyId(String key) {
		String fieldName = String.format("VK_%s", key.toUpperCase());
		
		int value = -1;
		try {
			value = KeyEvent.class.getDeclaredField(fieldName).getInt(KeyEvent.class);
		} catch (Exception e) {
			// if the specified object is not an instance of the class or
			// interface declaring the underlying field (or a subclass or
			// implementor thereof)
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * This allows the user's config file to use words instead of numbered joint ids. The config.jj generated code uses this
	 * to lookup the numbered id of a joint that was defined in the user's config file. The config.jj code then stores
	 * the numbered id instead of a string joint name.
	 *
	 * Note: Nite's JointType enumeration defines the compatible joint names for this method!
	 *
	 * @param jointName The name of the joint that we need the numbered id of.
	 * @return A numbered id of a joint as defined by Nite's JointType enumeration.
	 */
	public static int getJointId(String jointName) {
		return JointType.valueOf(jointName).toNative();
	}
}