package com.nokia.ads.common.util;

import java.util.Random;

import com.nokia.ads.common.util.HashUtil.HashAlgorithm;

public class IdGenerator {

	private static final Random rand = new Random();
	
	/**
	 * <pre>
	 * appId: 15位 hex 字符，[aaaa][b][cccccccc][dd]
	 * - a 是 developerId 的 hashcode
	 * - b 是 应用平台
	 * - c 是唯一数字，可以通过 FNV 类似的算法按照 FNV(developerId+developerName+applicationName+applicationPlatform+accountEmail+当前时间戳) 来获得
	 * - d 是随机数字，random(0,ff) 获得
	 * </pre>
	 * 
	 * @return
	 */
	public static String newAppId(int platform, String developerId,
			String developerName, String appName, String accountEmail) {
		String a = String.format("%04x", HashAlgorithm.KETAMA_HASH.hash(developerId) & 0xffffL); 
		String b = Integer.toHexString(platform & 0xf);
		String c = String.format("%08x", HashAlgorithm.FNV1_32_HASH.hash(developerId
				+ "|" + developerName + "|" + appName + "|" + accountEmail
				+ "|" + System.currentTimeMillis()) & 0xffffffffL);
		String d = String.format("%02x", rand.nextInt(0xff) & 0xff);
		return (a+b+c+d).toLowerCase();
	}
	
	/**
	 * <pre>
	 * adId: 15位 hex 字符, [aaaa][bb][c][dddd][eeee]
	 * - a 是 advertiserId 的 hashcode
	 * - b 是 compaignId 的 hashcode
	 * - c 是 adtype
	 * - d 是唯一数字，通过 System.currentTimeInMills 计算
	 * - e 是随机数
	 * 
	 * @param advertiserId
	 * @param campaignId
	 * @param adSize
	 * @param adType
	 * @return
	 */
	public static String newAdId(String advertiserId, String campaignId, int adType) {
		String a = String.format("%04x", HashAlgorithm.KETAMA_HASH.hash(advertiserId) & 0xffffL);
		String b = String.format("%02x", HashAlgorithm.KETAMA_HASH.hash(campaignId) & 0xffL);
		String c = Integer.toHexString(adType & 0xf);
		String d = String.format("%04x", HashAlgorithm.FNV1_32_HASH.hash(advertiserId
				+ "|" + campaignId + "|" + adType
				+ "|" + System.currentTimeMillis())& 0xffffL);
		String e = String.format("%04x", rand.nextInt(0xffff) & 0xffff);
		return (a+b+c+d+e).toLowerCase();
	}

	/**
	 * appSecretKey: 32位 hex 字符，用 UUID 类来产生即可。
	 * 
	 * @return
	 */
	public static String newAppSecretKey() {
		String uuid = java.util.UUID.randomUUID().toString();
		String ids[] = uuid.split("-");
		StringBuffer strBuf = new StringBuffer();
		for (int i = 0; i < ids.length; i++) {
			strBuf.append(ids[i]);
		}
		return strBuf.toString().toLowerCase();
	}

	/**
	 * <pre>
	 * inventoryId: 共 8 位 int 数字，[a][b][cccc][d][e] 组成：
	 * - a 是平台类型，比如 wap, ios, android 等
	 * - b 是inventoryType，比如 textAd, bannerAd, videoAd 等
	 * - c 是 appId 的 四位 hashcode
	 * - d 是 inventory 在这个 app 里面的顺序
	 * - e 是 校检位，校检算法空余出来，我这边给出
	 * </pre>
	 * @return
	 */
	public static String newInventoryId(int platform, int type, String appId, int order) {
		String a = Integer.toHexString(platform & 0xf);
		String b = Integer.toHexString(type & 0xf);
		String c = String.format("%04x", HashAlgorithm.FNV1A_32_HASH.hash(appId) & 0xffffL);
		String d = Integer.toHexString(type & 0xf);
		String e = Long.toHexString(HashAlgorithm.CRC_HASH.hash(platform+"|"+type+"|"+appId+"|"+order) & 0xfL);
		return (a+b+c+d+e).toLowerCase();
	}
}
