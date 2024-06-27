/**
 * Language.java
 *
 * Copyright (C) 2007,  Richard Midwinter
 *
 * This file is part of google-api-translate-java.
 *
 * google-api-translate-java is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * google-api-translate-java is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with google-api-translate-java. If not, see <http://www.gnu.org/licenses/>.
 */
package com.google.api.translate;

/**
 * Defines language information for the Google Translate API.
 *
 * @author Richard Midwinter
 * @author alosii
 * @author bjkuczynski
 */
public enum Language {
	AUTO_DETECT(""),
	ENGLISH("en"),
	RUSSIAN("ru"),
	UKRANIAN("uk");
	
	/**
	 * Google's String representation of this language.
	 */
	private final String language;
	
	/**
	 * Enum constructor.
	 * @param pLanguage The language identifier.
	 */
	private Language(final String pLanguage) {
		language = pLanguage;
	}
	
	public static Language fromString(final String pLanguage) {
		for (Language l : values()) {
			if (l.toString().equals(pLanguage)) {
				return l;
			}
		}
		return null;
	}
	
	/**
	 * Returns the String representation of this language.
	 * @return The String representation of this language.
	 */
	@Override
	public String toString() {
		return language;
	}
}