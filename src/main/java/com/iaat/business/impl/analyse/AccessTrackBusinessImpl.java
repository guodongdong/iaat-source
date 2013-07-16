package com.iaat.business.impl.analyse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iaat.business.analyse.AccessTrackBusiness;
import com.iaat.dao.analyse.AccessTrackDao;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.json.FocusUrlsBean;
import com.iaat.json.SearchBar;
import com.iaat.json.analyse.AccessBean;
import com.iaat.json.analyse.AccessTrackBean;
import com.iaat.model.TrackInput;
import com.iaat.model.TrackOutput;
import com.nokia.ads.common.util.Log;
import com.nokia.ads.common.util.StringUtils;
import com.nokia.ads.platform.backend.util.Paging;

public class AccessTrackBusinessImpl implements AccessTrackBusiness {
	private final static Log log = Log.getLogger(AccessTrackBusinessImpl.class);
	@Override
	public List<AccessTrackBean> getUrlsList(SearchBar bar) {
		log.info("[AccessTrackBusinessImpl.getUrlsList] [begin]");
		AccessTrackDao accessDao = DaoFactory.getInstance(AccessTrackDao.class);
		List<TrackInput> accBeans = accessDao.getUrlsList(bar);
		Map<String,AccessTrackBean> accMaps = new HashMap<String,AccessTrackBean>();
		String urls = null;
		AccessTrackBean accessTrackBean = null;
		int i = 0;
		Long sumUv = 0l;
		String preUrl = null;
		for (TrackInput trackInput : accBeans) {
			urls = trackInput.getFocusAddress();
			accessTrackBean  = accMaps.get(urls);
			if(i!=0&&accessTrackBean==null){
				AccessTrackBean accTracBean = accMaps.get(preUrl);
				accTracBean.setSize(sumUv.intValue());
				sumUv = 0l;
			}
			if(accessTrackBean==null){
				accessTrackBean =  new AccessTrackBean();
				accessTrackBean.setName(trackInput.getFocusAddress());
				List<String> children = new ArrayList<String>();
				String childUrl = trackInput.getInputAddress();
				if(childUrl!=null&&StringUtils.hasLength(childUrl)){
					children.add(childUrl);	
				}
				accessTrackBean.setChildren(children);
				accMaps.put(urls, accessTrackBean);
			}else{
				List<String> children = accessTrackBean.getChildren();
				String childUrl = trackInput.getInputAddress();
				if(childUrl!=null&&StringUtils.hasLength(childUrl)){
					children.add(trackInput.getInputAddress());	
				}
			}
			preUrl = urls;
			sumUv += trackInput.getImputUv();
			i++;
		}
		List<AccessTrackBean> accTrackBeans = new ArrayList<AccessTrackBean>();
		//返回集合
		for (Map.Entry<String, AccessTrackBean> accBean: accMaps.entrySet()) {
			List<String> children = accBean.getValue().getChildren();
			if(children!=null&&children.size()==0){
				accBean.getValue().setSize(0);
			}
			accTrackBeans.add(accBean.getValue());
		}
		log.info("[AccessTrackBusinessImpl.getUrlsList] [end]");
		return accTrackBeans;
	}	
	
	@Override
	public List<AccessBean> getInputUrlsByFocus(SearchBar searchBar,
			String focusUrl,Paging paging) {
		log.info("[AccessTrackBusinessImpl.getInputUrlsByFocus] [begin]");
		focusUrl = validateFocusUrl(focusUrl);
		AccessTrackDao accessDao = DaoFactory.getInstance(AccessTrackDao.class);
		List<TrackInput> tackInputs = accessDao.getInputUrlsByFocus(searchBar, focusUrl,paging );
		List<AccessBean> accTrackBeans = new ArrayList<AccessBean>();
		TrackInput sumPvBean = accessDao.getInputUVCount(searchBar, focusUrl);
		Long sumPv = 0l;
		if(sumPvBean!=null){
			sumPv = sumPvBean.getImputUv();	
		}
		for (TrackInput trackInput : tackInputs) {
			AccessBean accessBean = new AccessBean();
			String inputAddRess = trackInput.getInputAddress();
			if(inputAddRess!=null&&StringUtils.hasLength(inputAddRess)){
				accessBean.setName(inputAddRess);	
			}else{
				accessBean.setName(focusUrl);
			}
			accessBean.setSize(trackInput.getImputUv().intValue());
			Double rate = 0d;
			if(sumPv.intValue()!=0){
				rate = Double.valueOf(trackInput.getImputUv())/Double.valueOf(sumPv);	
			}
			accessBean.setRate(rate.floatValue());
			accTrackBeans.add(accessBean);
		}
		List<AccessBean> sortBeans = sortRate(accTrackBeans); 
		log.info("[AccessTrackBusinessImpl.getInputUrlsByFocus] [end]");
		return sortBeans;
	}
	@SuppressWarnings("unchecked")
	private List<AccessBean> sortRate(List<AccessBean> accTrackBeans) {
		AccessBean[] accBeans = accTrackBeans.toArray(new AccessBean[]{});
		Arrays.sort(accBeans, new Comparator() {
			@Override
			public int compare(Object fromObj, Object toObj) {
				AccessBean logFrom = (AccessBean)fromObj;
				AccessBean toFrom = (AccessBean)toObj;
				return toFrom.getRate().compareTo(logFrom.getRate());
			}
		});
		return Arrays.asList(accBeans);
	}
	@Override
	public List<AccessBean> getOuputUrlsByFocus(SearchBar searchBar,
			String focusUrl,Paging paging) {
		log.info("[AccessTrackBusinessImpl.getOuputUrlsByFocus] [begin]");
		focusUrl = validateFocusUrl(focusUrl);
		AccessTrackDao accessDao = DaoFactory.getInstance(AccessTrackDao.class);
		List<TrackOutput> tackInputs = accessDao.getOuputUrlsByFocus(searchBar, focusUrl,paging);
		List<AccessBean> accTrackBeans = new ArrayList<AccessBean>();
		TrackOutput sumPvBean = accessDao.getOutputUVCount(searchBar, focusUrl);
		Long sumPv = 0l;
		if(sumPvBean!=null){
			sumPv = sumPvBean.getOutputUv();
		}
		for (TrackOutput trackOutput : tackInputs) {
			AccessBean accessBean = new AccessBean();
			accessBean.setName(trackOutput.getOutputAddress());
			accessBean.setSize(trackOutput.getOutputUv().intValue());
			Double rate = Double.valueOf(trackOutput.getOutputUv())/Double.valueOf(sumPv);
			accessBean.setRate(rate.floatValue());
			accTrackBeans.add(accessBean);
		}
		List<AccessBean> sortBeans = sortRate(accTrackBeans);
		log.info("[AccessTrackBusinessImpl.getOuputUrlsByFocus] [end]");
		return sortBeans;
	}

	private String validateFocusUrl(String focusUrl) {
		if(focusUrl==null||!StringUtils.hasLength(focusUrl)){
			focusUrl = " ";
		}
		return focusUrl;
	}

	@Override
	public AccessTrackBean getInputUVCount(SearchBar searchBar, String focusUrl) {
		log.info("[AccessTrackBusinessImpl.getInputUVCount] [begin]");
		focusUrl = validateFocusUrl(focusUrl);
		AccessTrackBean accessBean = new AccessTrackBean();
		accessBean.setSize(0);
		AccessTrackDao accessDao = DaoFactory.getInstance(AccessTrackDao.class);
		TrackInput trackInput = accessDao.getInputUVCount(searchBar, focusUrl);
		accessBean.setName(focusUrl);
		if(trackInput!=null&&trackInput.getImputUv()!=null){
			accessBean.setSize(trackInput.getImputUv().intValue());	
		}
		log.info("[AccessTrackBusinessImpl.getInputUVCount] [end]");
		return accessBean;
	}
	
	@Override
	public AccessTrackBean getOutputUVCount(SearchBar searchBar, String focusUrl) {
		log.info("[AccessTrackBusinessImpl.getOutputUVCount] [begin]");
		focusUrl = validateFocusUrl(focusUrl);
		AccessTrackBean accessBean = new AccessTrackBean();
		accessBean.setSize(0);
		AccessTrackDao accessDao = DaoFactory.getInstance(AccessTrackDao.class);
		TrackOutput trackOutput = accessDao.getOutputUVCount(searchBar, focusUrl);
		accessBean.setName(focusUrl);
		if(trackOutput!=null&&trackOutput.getOutputUv()!=null){
			accessBean.setSize(trackOutput.getOutputUv().intValue());	
		}
		log.info("[AccessTrackBusinessImpl.getOutputUVCount] [end]");
		return accessBean;
	}

	@Override
	public List<FocusUrlsBean> getTopFocusUrls(SearchBar searchBar) {
		log.info("[AccessTrackBusinessImpl.getTopFocusUrls] [begin]");
		AccessTrackDao accessDao = DaoFactory.getInstance(AccessTrackDao.class);
		log.info("[AccessTrackBusinessImpl.getTopFocusUrls] [end]");
		return  accessDao.getTopFocusUrls(searchBar);
	}
	
}
