package ch.ethz.coss.nervousnet.vm.storage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import ch.ethz.coss.nervousnet.vm.storage.MagneticData;
import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * DAO for table "MAGNETIC_DATA".
 */
public class MagneticDataDao extends AbstractDao<MagneticData, Long> {

	public static final String TABLENAME = "MAGNETIC_DATA";

	/**
	 * Properties of entity MagneticData.<br/>
	 * Can be used for QueryBuilder and for referencing column names.
	 */
	public static class Properties {
		public final static Property Id = new Property(0, Long.class, "id", true, "_id");
		public final static Property TimeStamp = new Property(1, Long.class, "TimeStamp", false, "TIME_STAMP");
		public final static Property MagX = new Property(2, Float.class, "MagX", false, "MAG_X");
		public final static Property MagY = new Property(3, Float.class, "MagY", false, "MAG_Y");
		public final static Property MagZ = new Property(4, Float.class, "MagZ", false, "MAG_Z");
		public final static Property Volatility = new Property(5, long.class, "Volatility", false, "VOLATILITY");
		public final static Property ShareFlag = new Property(6, Boolean.class, "ShareFlag", false, "SHARE_FLAG");
	};

	public MagneticDataDao(DaoConfig config) {
		super(config);
	}

	public MagneticDataDao(DaoConfig config, DaoSession daoSession) {
		super(config, daoSession);
	}

	/** Creates the underlying database table. */
	public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
		String constraint = ifNotExists ? "IF NOT EXISTS " : "";
		db.execSQL("CREATE TABLE " + constraint + "\"MAGNETIC_DATA\" (" + //
				"\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
				"\"TIME_STAMP\" INTEGER," + // 1: TimeStamp
				"\"MAG_X\" REAL," + // 2: MagX
				"\"MAG_Y\" REAL," + // 3: MagY
				"\"MAG_Z\" REAL," + // 4: MagZ
				"\"VOLATILITY\" INTEGER NOT NULL ," + // 5: Volatility
				"\"SHARE_FLAG\" INTEGER);"); // 6: ShareFlag
	}

	/** Drops the underlying database table. */
	public static void dropTable(SQLiteDatabase db, boolean ifExists) {
		String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MAGNETIC_DATA\"";
		db.execSQL(sql);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	protected void bindValues(SQLiteStatement stmt, MagneticData entity) {
		stmt.clearBindings();

		Long id = entity.getId();
		if (id != null) {
			stmt.bindLong(1, id);
		}

		Long TimeStamp = entity.getTimeStamp();
		if (TimeStamp != null) {
			stmt.bindLong(2, TimeStamp);
		}

		Float MagX = entity.getMagX();
		if (MagX != null) {
			stmt.bindDouble(3, MagX);
		}

		Float MagY = entity.getMagY();
		if (MagY != null) {
			stmt.bindDouble(4, MagY);
		}

		Float MagZ = entity.getMagZ();
		if (MagZ != null) {
			stmt.bindDouble(5, MagZ);
		}
		stmt.bindLong(6, entity.getVolatility());

		Boolean ShareFlag = entity.getShareFlag();
		if (ShareFlag != null) {
			stmt.bindLong(7, ShareFlag ? 1L : 0L);
		}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public Long readKey(Cursor cursor, int offset) {
		return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public MagneticData readEntity(Cursor cursor, int offset) {
		MagneticData entity = new MagneticData( //
				cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
				cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1), // TimeStamp
				cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2), // MagX
				cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3), // MagY
				cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4), // MagZ
				cursor.getLong(offset + 5), // Volatility
				cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0 // ShareFlag
		);
		return entity;
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public void readEntity(Cursor cursor, MagneticData entity, int offset) {
		entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
		entity.setTimeStamp(cursor.isNull(offset + 1) ? null : cursor.getLong(offset + 1));
		entity.setMagX(cursor.isNull(offset + 2) ? null : cursor.getFloat(offset + 2));
		entity.setMagY(cursor.isNull(offset + 3) ? null : cursor.getFloat(offset + 3));
		entity.setMagZ(cursor.isNull(offset + 4) ? null : cursor.getFloat(offset + 4));
		entity.setVolatility(cursor.getLong(offset + 5));
		entity.setShareFlag(cursor.isNull(offset + 6) ? null : cursor.getShort(offset + 6) != 0);
	}

	/**
	 * @inheritdoc
	 */
	@Override
	protected Long updateKeyAfterInsert(MagneticData entity, long rowId) {
		entity.setId(rowId);
		return rowId;
	}

	/**
	 * @inheritdoc
	 */
	@Override
	public Long getKey(MagneticData entity) {
		if (entity != null) {
			return entity.getId();
		} else {
			return null;
		}
	}

	/**
	 * @inheritdoc
	 */
	@Override
	protected boolean isEntityUpdateable() {
		return true;
	}

}