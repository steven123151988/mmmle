/*
 *  Copyright 2010 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.daking.sports.view;

/**
 * Array Wheel adapter.
 */
public class ArrayWheelAdapter implements WheelAdapter {

	// /** The default min value */
	// public static final int DEFAULT_MAX_VALUE = 9;
	//
	// /** The default max value */
	// private static final int DEFAULT_MIN_VALUE = 0;

	private String[] array;

	// format
	private String format;

	/**
	 * Default constructor
	 */
	public ArrayWheelAdapter() {
		this(new String[] { "00", "10", "20", "30" });
	}

	/**
	 * Constructor
	 * 
	 * @param minValue
	 *            the wheel min value
	 * @param maxValue
	 *            the wheel max value
	 */
	public ArrayWheelAdapter(String[] array) {
		this(array, null);
	}

	/**
	 * Constructor
	 * 
	 * @param minValue
	 *            the wheel min value
	 * @param maxValue
	 *            the wheel max value
	 * @param format
	 *            the format string
	 */
	public ArrayWheelAdapter(String[] array, String format) {
		this.array = array;
		this.format = format;
	}

	@Override
	public String getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
			return format != null ? String.format(format, array[index])
					: array[index];
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return array.length;
	}

	@Override
	public int getMaximumLength() {
		int maxLen = 2;
		for (String a : array) {
			if (maxLen < a.length()) {
				maxLen = a.length();
			}
		}
		return maxLen;
	}
}
