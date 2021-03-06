/*
MIT License

Copyright (c) 2016 Sebastian Janisch

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package org.sjanisch.skillview.core.contribution.api;

import java.util.Objects;
import java.util.Optional;

/**
 * Designates the originator or contributor of a contribution which is a unique
 * identifier for this contributor.
 * <p>
 * {@link #hashCode()} and {@link #equals(Object)} are implemented against
 * {@link #getName()} and {@link #getEmail()}.
 * <p>
 * Implementors must retain thread-safety and immutability.
 * 
 * @author sebastianjanisch
 *
 */
public interface Contributor {

	/**
	 * 
	 * @return never {@code null} or whitespace.
	 */
	String getName();

	/**
	 * 
	 * @return never {@code null}
	 */
	Optional<String> getEmail();

	/**
	 * 
	 * @param name
	 *            must not be {@code null} or whitespace.
	 * @return never {@code null}
	 */
	public static Contributor of(String name) {
		return of(name, null);
	}

	/**
	 * 
	 * @param name
	 *            must not be {@code null} or whitespace.
	 * @param email
	 *            can be {@code null}
	 * @return never {@code null}
	 */
	public static Contributor of(String name, String email) {
		Objects.requireNonNull(name, "name");
		return new Contributor() {

			@Override
			public String getName() {
				return name;
			}

			@Override
			public Optional<String> getEmail() {
				return Optional.ofNullable(email);
			}

			@Override
			public String toString() {
				return String.format("%s[%s, %s]", getClass().getSimpleName(), name, email);
			}

			@Override
			public int hashCode() {
				return (name + email).hashCode();
			}

			@Override
			public boolean equals(Object obj) {
				if (obj != null && obj instanceof Contributor) {
					Contributor c = (Contributor) obj;
					if (c.getName().equals(name)) {
						if (getEmail().isPresent() && c.getEmail().isPresent()) {
							if (getEmail().get().equals(c.getEmail().get())) {
								return true;
							}
						} else if (!getEmail().isPresent() && !c.getEmail().isPresent()) {
							return true;
						}
					}
					return false;
				}
				return false;
			}
		};
	}

}
