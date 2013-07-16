
package com.iaat.model;

import java.io.Serializable;

/**    
 * @name AccountBean
 * 
 * @description CLASS_DESCRIPTION
 * 
 * MORE_INFORMATION
 * 
 * @author lijing
 * 
 * @since 2012-3-21
 *       
 * @version 1.0
 */
public class AccountBean implements Serializable
{	
	/**    
	 *   
	 * @since Ver 1.1   
	 */   
 	private static final long serialVersionUID = 201203312126l;

	private Long id;
	
	private String name;
	
	private String oui;
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getOui() {
		return oui;
	}

	public void setOui(String oui) {
		this.oui = oui;
	}
}
