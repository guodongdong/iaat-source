package com.nokia.ads.platform.backend.model;

import java.io.Serializable;
import java.lang.reflect.Array;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.nokia.ads.platform.backend.annotation.ChangeComparison;

/**
 * 
 * @name ModelObject
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author WangXL
 * 
 * @since 2011-1-27
 * 
 * @version 1.0
 */
@MappedSuperclass
abstract public class ModelObject<ID extends Serializable> {

	public static final String ENUM_TYPE_SPLITTER = ",";

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		ModelObject<ID> peer = (ModelObject<ID>) obj;
		if (this.getId() == null) {
			return false;
		} else if (!this.getId().equals(peer.getId()))
			return false;
		return true;
	}

	protected ModelObject() {
	}

	public ModelObject(ID id) {
		super();
		this.id = id;
	}

	/**
	 * id
	 */
	@ChangeComparison
	
	protected ID id;

	/**********************************************************************
	 * below are get, set methods
	 **********************************************************************/
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	public ID getId() {
		return this.id;
	}

	public void setId(final ID id) {
		this.id = id;
	}

	/*******************
	 * static functions
	 *******************/

	protected static int makeHashCode(Object... properties) {
		int hashcode = 173; // SEED
		for (Object prop : properties) {
			if (prop != null) {
				if (prop.getClass().isArray()) {
					for (Object o : (Object[]) prop) {
						hashcode = calculateHashCode(hashcode, o);
					}
				} else {
					hashcode = calculateHashCode(hashcode, prop);
				}
			}
		}
		return hashcode;
	}

	protected static int makeHashCode(int hashcode, Object... properties) {
		return 37 * hashcode + makeHashCode(properties);
	}

	private static int calculateHashCode(int hashcode, Object o) {
		int objCode = 0;
		if (o instanceof NetworkObject) {
			NetworkObject netObj = (NetworkObject) o;
			if (netObj.getId() != null)
				objCode = netObj.getId().hashCode();
			if (netObj.getRemoteID() != null)
				objCode = 37 * objCode + netObj.getRemoteID().hashCode();
		} else if (o instanceof ModelObject) {
			ModelObject<?> modObj = (ModelObject<?>) o;
			if (modObj.getId() != null)
				objCode = modObj.getId().hashCode();
		} else if (o != null) {
			objCode = o.hashCode();
		}
		if (objCode == 0)
			return hashcode;
		return 37 * hashcode + objCode;
	}

	protected static String makeIdentifiableString(ModelObject<?> target,
			Object... objs) {
		if (target == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		if (objs != null) {
			for (Object obj : objs) {
				if (obj != null) {
					String str = obj.toString();
					sb.append(str);
					sb.append(" - ");
				}
			}
		}
		return String.format("%s[%s]: %s", target.getClass().getSimpleName(),
				target.getId(), sb);
	}

	//

	@SuppressWarnings("rawtypes")
	public static String EnumTypesToString(Enum[] types) {
		if (types != null && types.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (Enum type : types) {
				sb.append(type.name());
				sb.append(ENUM_TYPE_SPLITTER);
			}
			String str = sb.substring(0, sb.length() - 1);
			return str;
		} else {
			return null;
		}
	}

	@SuppressWarnings( { "unchecked", "rawtypes" })
	public static <T extends Enum> T[] ParseEnumTypes(String typeStr,
			Class<T> enumType) {
		if (typeStr != null) {
			String[] strs = typeStr.split(ENUM_TYPE_SPLITTER);
			T[] types = (T[]) Array.newInstance(enumType, strs.length);
			for (int i = 0; i < strs.length; i++) {
				T t = (T) Enum.valueOf(enumType, strs[i]);
				types[i] = t;
			}
			return types;
		}
		return null;
	}
}
