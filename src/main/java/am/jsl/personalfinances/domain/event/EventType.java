package am.jsl.personalfinances.domain.event;

import java.util.Arrays;

/**
 *  An enum containing possible event types.
 *
 * @author hamlet
 */
public enum EventType {
	LOGIN((byte)1, "Login"),
	CREATE_ROLE((byte)2, "Create role"),
	UPDATE_ROLE((byte)3, "Update role"),
	DELETE_ROLE((byte)4, "Delete role"),
	CREATE_USER((byte)5, "Create user"),
	UPDATE_USER((byte)6, "Update user"),
	DELETE_USER((byte)7, "Delete user"),
	CHANGE_PASSWORD((byte)8, "Change password"),
	REGISTER_USER((byte)9, "Register user");

	private byte value;
	private String operation;
	
	EventType(byte value, String operation) {
		this.value = value;
		this.operation = operation;
	}
	
	public static EventType getByValue(byte value) {
		EventType[] values = values();

		return Arrays.stream(values).filter(eventType -> eventType.getValue() == value).findFirst().orElse(null);
	}
	
	public byte getValue() {
		return value;
	}
	
	public void setValue(byte value) {
		this.value = value;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

}
