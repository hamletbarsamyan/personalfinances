package am.jsl.personalfinances.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Domain object that extends NamedEntity and contains description field.
 * Used as a base class for objects needing these properties.
 *
 * @author hamlet
 */
public class Descriptive extends NamedEntity implements Serializable {

	/**
	 * The description
	 */
	protected String description;

	/**
	 * Gets description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description.
	 *
	 * @param description the description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return 31 * super.hashCode() + Objects.hash(description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		if (!super.equals(obj)) {
			return false;
		}
		final Descriptive other = (Descriptive) obj;
		boolean equals = Objects.equals(this.description, other.description);
		return equals;
	}
}
