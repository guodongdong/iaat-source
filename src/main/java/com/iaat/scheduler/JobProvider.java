package com.iaat.scheduler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.nokia.ads.common.util.Log;
import com.nokia.ads.platform.backend.core.scheduler.IJobConfig;
import com.nokia.ads.platform.backend.core.scheduler.SchedulableJob;

public class JobProvider {

	private static final Log log = Log.getLogger(JobProvider.class);

	private static final String JOB_XML_FILE = "jobs.xml";

	private static JobProvider instance;

	public static JobProvider getInstance() {
		if (instance == null) {
			instance = new JobProvider();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public List<IJobConfig<? extends SchedulableJob>> getJobSettings() {
		List<IJobConfig<? extends SchedulableJob>> setting = new ArrayList<IJobConfig<? extends SchedulableJob>>();
		URL confURL = JobProvider.class.getClassLoader().getResource(
				JOB_XML_FILE);
		if (confURL == null)
			return null;
		log.debug("job xml path is {0}", confURL.toString());
		File f = new File(confURL.getPath());
		SAXReader reader = new SAXReader();
		Element root;
		Document doc;
		try {
			doc = reader.read(f);
			root = doc.getRootElement();

			Element foo;

			for (Iterator<Element> i = root.elementIterator("job"); i.hasNext();) {
				foo = i.next();
				Properties p = new Properties();
				p.put("jobid", foo.elementText("jobid"));
				p.put("name", foo.elementText("name"));
				p.put("timeout", foo.elementText("timeout"));
				p.put("enabled", foo.elementText("enabled"));
				p.put("concurrency", foo.elementText("concurrency"));
				p.put("cron", foo.elementText("cron"));
				p.put("class", foo.elementText("class"));
				p.put("params", foo.elementText("params"));
				setting.add((XmlJobConfig<SchedulableJob>) XmlJobConfig
						.getInstance(p));
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return setting;
	}
}
