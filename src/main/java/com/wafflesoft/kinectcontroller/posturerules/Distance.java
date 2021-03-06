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

package com.wafflesoft.kinectcontroller.posturerules;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Distance implements Rule {
	private String 		id = "";
	private int 		joint1;
	private int 		joint2;
    private double 		distance;
    
	
	public Distance(int joint1, int joint2, double distance) {
        this.joint1 = joint1;
        this.joint2 = joint2;
        this.distance = distance;
		this.id = getHash();
    }

	private String getHash() {
		String hash = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String text = String.format("%s:%d:%d", getType().alias, joint1, joint2);

			md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
			hash = new String(md.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return hash;
	}
	
	public String getId() {
		return this.id;
	}

	public RuleType getType() {
		return RuleType.DISTANCE;
	}
	
	public void setId(String id) {
		this.id = id;
	}
    
	public int getJoint1() {
		return this.joint1;
	}
	
	public void setJoint1(int j1) {
		this.joint1 = j1;
	}
	
	public int getJoint2() {
		return this.joint2;
	}
	
	public void setJoint2(int j2) {
		this.joint2 = j2;
	}
    
    public double getDistance() {
		return this.distance;
	}
	
	public void setDistance(double distance) {
		this.distance = distance;
	}
}