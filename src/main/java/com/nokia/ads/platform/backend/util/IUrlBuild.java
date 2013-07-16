package com.nokia.ads.platform.backend.util;

public interface IUrlBuild {
	
	public String buildAdKeyword(long adCampaignID, long adGroupID,
			long adKeywordID, String adCampaign, String adGroup,
			String adKeyword);
	
	public String buildAdCopy(long adCampaignID, long adGroupID, long adCopyID,
			String adCampaign, String adGroup, String adCopy);
	
}	
