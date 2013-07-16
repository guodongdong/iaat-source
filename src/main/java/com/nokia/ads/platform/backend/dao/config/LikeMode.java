package com.nokia.ads.platform.backend.dao.config;

/**
 * @name LikeMode
 * 
 * @description CLASS_DESCRIPTION
 * 
 *              MORE_INFORMATION
 * 
 * @author Ai.jy
 * 
 * @since 2011-1-5
 * 
 * @version 1.0
 */
public enum LikeMode {

	BEFORE {
		@Override
		public LikeMode setValue(String val) {
			this.val = val + "%";
			return this;
		}
	},
	END {
		@Override
		public LikeMode setValue(String val) {
			this.val = "%" + val;
			return this;
		}
	},
	MIDDLE {
		@Override
		public LikeMode setValue(String val) {
			this.val = "%" + val + "%";
			return this;
		}
	},
	NOTLIKE {
		@Override
		public LikeMode setValue(String val) {
			this.val = "%" + val + "%";
			return this;
		}
	},
	PRECISE {
		@Override
		public LikeMode setValue(String val) {
			this.val = val;
			return this;
		}
	};
	public String getValue() {
		return this.val;
	}

	public abstract LikeMode setValue(String val);

	protected String val;

}
