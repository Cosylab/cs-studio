package org.csstudio.nams.common.material.regelwerk;



/**
 * Operators supported by this filter-condition.
 */
public enum Operator implements HoldsAnDatabaseId {
	/**
	 * The given Value is equal to the value on the channel.
	 */
	EQUALS((short) 1),

	/**
	 * The given Value is not equal to the value on the channel.
	 */
	UNEQUALS((short) 2),

	/**
	 * The value on the channel is smaller than the given value.
	 */
	SMALLER((short) 3),

	/**
	 * The value on the channel is greater than the given value.
	 */
	GREATER((short) 4);

	/**
	 * Returns the Operator for given database-id or {@code null} if id is
	 * unknown.
	 */
	public static Operator findOperatorOfDBId(short id) {
		for (Operator op : Operator.values()) {
			if (op._dbid == id) {
				return op;
			}
		}

		return null;
	}

	/**
	 * The database-id.
	 */
	private short _dbid;

	/**
	 * Creates a value-representation with given database-id.
	 * 
	 * @param id
	 *            The id in database:
	 */
	Operator(short id) {
		_dbid = id;
	}

	/**
	 * Returns the database-id of this Operator.
	 * 
	 * <strong>Pay attention:</strong> Please do never use this method
	 * outside the DAOs!
	 */
	public short asDatabaseId() {
		return _dbid;
	}
}