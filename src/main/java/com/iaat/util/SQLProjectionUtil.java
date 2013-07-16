package com.iaat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.SQLProjection;
import org.hibernate.type.Type;
@SuppressWarnings("serial")
public class SQLProjectionUtil extends SQLProjection {
	protected SQLProjectionUtil(String sql, String[] columnAliases, Type[] types) {  
        super(sql, columnAliases, types);     
	}  
	@Override
	public String toSqlString(Criteria criteria, int loc,
			CriteriaQuery criteriaQuery) throws HibernateException {
		 String sql = super.toSqlString(criteria, loc, criteriaQuery);  
	      Pattern p = Pattern.compile("\\{(\\w++)\\}");  
	      Matcher m = p.matcher(sql);  
	      StringBuffer sb = new StringBuffer();  
	      while (m.find()) {  
	             String s = m.group();  
	             s = s.replace("{", "").replace("}", "")+".";  
	            m.appendReplacement(sb, criteriaQuery.getSQLAlias(criteria, s));  
	       }  
	       m.appendTail(sb);    
	       return sb.toString();  
	}
	public static SQLProjection sqlProjection(String sql, String[] columnAliases, Type... types)  
	{  
	    return new SQLProjectionUtil(sql, columnAliases, types);  
	}   
}
