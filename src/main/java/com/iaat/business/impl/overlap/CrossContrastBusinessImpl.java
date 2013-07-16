package com.iaat.business.impl.overlap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iaat.business.overlap.CrossContrastBusiness;
import com.iaat.dao.factory.DaoFactory;
import com.iaat.dao.overlap.CrossContrastDao;
import com.iaat.json.LatitudeBean;
import com.iaat.json.overlap.ContrastResultBean;
import com.iaat.json.overlap.ContrastSearchBean;
import com.iaat.json.overlap.CrossCaseBean;
import com.iaat.json.overlap.CrossContrastBean;
import com.iaat.json.overlap.CrossDataBean;
import com.iaat.model.CrossContrastType;
import com.iaat.util.ValidateUtils;
import com.nokia.ads.common.util.ObjectUtils;

public class CrossContrastBusinessImpl implements CrossContrastBusiness {

	CrossContrastDao crossDao = DaoFactory.getInstance(CrossContrastDao.class);

	@Override
	public List<CrossDataBean> getCorssConstrasts(ContrastSearchBean[] cases,
			ContrastResultBean resultBean) {
		// 查询结果集
		CrossContrastType[] crosTypes = CrossContrastType.values();
		LatitudeBean latiBean = LatitudeBean.getInstance();
		Map<String, CrossDataBean> crossDataBeans = new HashMap<String, CrossDataBean>();
		for (CrossContrastType crossType : crosTypes) {
			List<CrossCaseBean> crossCaseBeans = new ArrayList<CrossCaseBean>();
			CrossDataBean crossDataBean = new CrossDataBean();
			crossDataBean.setTitle(crossType.name());
			crossDataBean.setData(crossCaseBeans);
			crossDataBeans.put(crossType.name(), crossDataBean);
		}
		for (ContrastSearchBean contrastBean : cases) {
			String province = contrastBean.getProvince();
			String city = contrastBean.getCity();
			if (ValidateUtils.isNotNull(province)) {
				contrastBean.setProvinceFk(latiBean.getRegionBeanByProvinKey(
						province).getRegionFk());
			}
			if (ValidateUtils.isNotNull(city)) {
				contrastBean.setCityFk((latiBean.getRegionBeanByCityKey(city)
						.getRegionFk()));
			}
			CrossContrastBean crossConstBean = crossDao
					.getCorssConstrasts(contrastBean);
			CrossContrastBean totalConstBean = crossDao
					.getCorssConstrastsByTotal(contrastBean);
			initTotalContrast(resultBean, crossDataBeans, contrastBean,
					totalConstBean);
			initCrossBean(resultBean, crossDataBeans, contrastBean,
					crossConstBean);
		}
		// 合并结果集
		List<CrossDataBean> datas = new ArrayList<CrossDataBean>();
		for (Map.Entry<String, CrossDataBean> crossDataBean : crossDataBeans
				.entrySet()) {
			CrossDataBean dataBean = crossDataBean.getValue();
			List<CrossCaseBean> crossCases = dataBean.getData();
			if(!ObjectUtils.isEmpty(crossCases)){
				datas.add(crossDataBean.getValue());	
			}
		}
		return datas;
	}

	private void initCrossBean(ContrastResultBean resultBean,
			Map<String, CrossDataBean> results,
			ContrastSearchBean contrastBean, CrossContrastBean crossConstBean) {
		if (isAddSearch(resultBean.getUV())) {
			CrossCaseBean uvBean = new CrossCaseBean();
			uvBean.setName(contrastBean.getName());
			Integer uv = null;
			if (crossConstBean != null) {
				uv = crossConstBean.getUV();
			}
			uvBean.setY(Double.valueOf(uv == null ? 0 : uv));
			addValueByType(uvBean, CrossContrastType.UV, results);
		}
		if (isAddSearch(resultBean.getTotalUser())) {
			CrossCaseBean sumUser = new CrossCaseBean();
			sumUser.setName(contrastBean.getName());
			Integer totalUser = null;
			if (crossConstBean != null) {
				totalUser = crossConstBean.getTotalUser();
			}
			sumUser.setY(Double.valueOf(totalUser == null ? 0 : totalUser));
			addValueByType(sumUser, CrossContrastType.TOTALUSER, results);
		}
		if (isAddSearch(resultBean.getNewUser())) {
			CrossCaseBean newUser = new CrossCaseBean();
			newUser.setName(contrastBean.getName());
			Integer tolNewUser = null;
			if (crossConstBean != null) {
				tolNewUser = crossConstBean.getNewUser();
			}
			newUser.setY(Double.valueOf(tolNewUser == null ? 0 : tolNewUser));
			addValueByType(newUser, CrossContrastType.NEWUSER, results);
		}
		if (isAddSearch(resultBean.getOldUser())) {
			CrossCaseBean oldUser = new CrossCaseBean();
			oldUser.setName(contrastBean.getName());
			Integer tolOldUser = null;
			if (crossConstBean != null) {
				tolOldUser = crossConstBean.getOldUser();
			}
			oldUser.setY(Double.valueOf(tolOldUser == null ? 0 : tolOldUser));
			addValueByType(oldUser, CrossContrastType.OLDUSER, results);
		}
		if (isAddSearch(resultBean.getActiveUser())) {
			CrossCaseBean activeUser = new CrossCaseBean();
			activeUser.setName(contrastBean.getName());
			Integer activeUserNum = null;
			if (crossConstBean != null) {
				activeUserNum = crossConstBean.getActiveUser();
			}
			activeUser.setY(Double.valueOf(activeUserNum == null ? 0
					: activeUserNum));
			addValueByType(activeUser, CrossContrastType.ACTIVEUSER, results);
		}

		if (isAddSearch(resultBean.getOnceAccessUser())) {
			CrossCaseBean onlyAcc = new CrossCaseBean();
			onlyAcc.setName(contrastBean.getName());
			Integer onlyAccNum = null;
			if (crossConstBean != null) {
				onlyAccNum = crossConstBean.getOnceAccessUser();
			}
			onlyAcc.setY(Double.valueOf(onlyAccNum == null ? 0 : onlyAccNum));
			addValueByType(onlyAcc, CrossContrastType.ONCEACCESSUSER, results);
		}

		if (isAddSearch(resultBean.getOnlyLoginUser())) {
			CrossCaseBean onlyLoginUser = new CrossCaseBean();
			onlyLoginUser.setName(contrastBean.getName());
			Integer onlyLogUser = null;
			if (crossConstBean != null) {
				onlyLogUser = crossConstBean.getOnlyLoginUser();
			}
			onlyLoginUser.setY(Double.valueOf(onlyLogUser == null ? 0
					: onlyLogUser));
			addValueByType(onlyLoginUser, CrossContrastType.ONLYLOGINUSER,
					results);
		}

		if (isAddSearch(resultBean.getAvgLoginUser())) {
			CrossCaseBean avgLoginUser = new CrossCaseBean();
			avgLoginUser.setName(contrastBean.getName());
			Double avgUser = null;
			if (crossConstBean != null) {
				avgUser = crossConstBean.getAvgLoginUser();
			}
			avgLoginUser.setY(Double.valueOf(avgUser == null ? 0 : avgUser));
			addValueByType(avgLoginUser, CrossContrastType.AVGLOGINUSER,
					results);
		}

		if (isAddSearch(resultBean.getAvgAccessUser())) {
			CrossCaseBean avgAccessUser = new CrossCaseBean();
			avgAccessUser.setName(contrastBean.getName());
			Double avgAccuser = null;
			if (crossConstBean != null) {
				avgAccuser = crossConstBean.getAvgAccessUser();
			}
			avgAccessUser.setY(Double.valueOf(avgAccuser == null ? 0
					: avgAccuser));
			addValueByType(avgAccessUser, CrossContrastType.AVGACCESSUSER,
					results);
		}

		if (isAddSearch(resultBean.getAvgIntervalTime())) {
			CrossCaseBean avgIntervalTime = new CrossCaseBean();
			avgIntervalTime.setName(contrastBean.getName());
			Double avgInter = null;
			if (crossConstBean != null) {
				avgInter = crossConstBean.getAvgIntervalTime();
			}
			avgIntervalTime.setY(Double
					.valueOf(avgInter == null ? 0 : avgInter));
			addValueByType(avgIntervalTime, CrossContrastType.AVGINTERVALTIME,
					results);
		}
	}

	private void initTotalContrast(ContrastResultBean resultBean,
			Map<String, CrossDataBean> results,
			ContrastSearchBean contrastBean, CrossContrastBean totalConstBean) {
		if (isAddSearch(resultBean.getPV())) {
			CrossCaseBean pvBean = new CrossCaseBean();
			pvBean.setName(contrastBean.getName());
			pvBean.setY(0d);
			Integer pv = null;
			if (totalConstBean != null) {
				pv = totalConstBean.getPV();
			}
			pvBean.setY(Double.valueOf(pv == null ? 0 : pv));
			addValueByType(pvBean, CrossContrastType.PV, results);
		}
		if (isAddSearch(resultBean.getWeb_PV())) {
			CrossCaseBean webPvBean = new CrossCaseBean();
			webPvBean.setName(contrastBean.getName());
			Integer webPv = null;
			if (totalConstBean != null) {
				webPv = totalConstBean.getWeb_PV();
			}
			webPvBean.setY(Double.valueOf(webPv == null ? 0 : webPv));
			addValueByType(webPvBean, CrossContrastType.WEB_PV, results);
		}
		if (isAddSearch(resultBean.getWap_PV())) {
			CrossCaseBean wapPvBean = new CrossCaseBean();
			wapPvBean.setName(contrastBean.getName());
			Integer wap_pv = null;
			if (totalConstBean != null) {
				wap_pv = totalConstBean.getWap_PV();
			}
			wapPvBean.setY(Double.valueOf(wap_pv == null ? 0 : wap_pv));
			addValueByType(wapPvBean, CrossContrastType.WAP_PV, results);
		}
	}

	private boolean isAddSearch(Boolean searchVal) {
		if (searchVal != null && searchVal) {
			return true;
		}
		return false;
	}

	private void addValueByType(CrossCaseBean crossCaseBean,
			CrossContrastType crossType,
			Map<String, CrossDataBean> crossDataBeans) {
		CrossDataBean dataBean = crossDataBeans.get(crossType.name());
		List<CrossCaseBean> caseBeans = dataBean.getData();
		if (ObjectUtils.isEmpty(caseBeans)) {
			caseBeans = new ArrayList<CrossCaseBean>();
			dataBean.setData(caseBeans);
		}
		caseBeans.add(crossCaseBean);
	}
}
