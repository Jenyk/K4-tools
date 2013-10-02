package cz.mzk.k4.tools.scripts;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.log4j.Logger;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import cz.mzk.k4.tools.domain.Knihovna;
import cz.mzk.k4.tools.domain.KrameriusProcess;
import cz.mzk.k4.tools.utils.ProcessManager;

/**
 * 
 * @author holmanj
 * 
 */
public class DefServlet extends HttpServlet {

	private static final long serialVersionUID = -2635991662902637019L;
	private String host;
	private static ProcessManager pm;
	private static Integer defSize; // default number of processes fetched from Kramerius
	private InputStream inputStream;
	private static org.apache.log4j.Logger LOGGER = Logger.getLogger(DefServlet.class);
	static final String CONF_FILE_NAME = "k4_tools_config.properties";
	private Knihovna knihovna;

	/**
	 * Ukazka servletu s nactenim config. souboru
	 * 
	 */
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// get properties file (/home/{user}/properties)
		String home = System.getProperty("user.home");
		File f = new File(home + "/" + CONF_FILE_NAME);

		Properties properties = new Properties();
		try {
			inputStream = new FileInputStream(f);
			properties.load(inputStream);
		} catch (IOException e) {
			LOGGER.fatal("Cannot load properties file");
		}

		defSize = Integer.parseInt(properties.getProperty("resultSize"));
		knihovna = Knihovna.valueOf(properties.getProperty("knihovna"));
		host = properties.getProperty(knihovna + ".host");
		String username = properties.getProperty(knihovna + ".username");
		String password = properties.getProperty(knihovna + ".password");

        pm = new ProcessManager(host, username, password);

		try {
			// handle URL parameters
			@SuppressWarnings("unchecked")
			Enumeration<String> en = request.getParameterNames();
			Map<String, String> params = new HashMap<String, String>();
			while (en.hasMoreElements()) {
				String paramName = en.nextElement();
				String paramBody = request.getParameter(paramName);
				params.put(paramName, paramBody);
			}
			if (!params.containsKey("resultSize")) {
				params.put("resultSize", defSize.toString());
			}
			int resultSize = Integer.parseInt(params.get("resultSize")
					.toString());

			// fetch processes
			List<KrameriusProcess> processes = pm.searchByParams(params);

			// specified resultSize can be bigger than the actual number of
			// processes fetched from Kramerius
			if (resultSize > processes.size()) {
				resultSize = processes.size();
			}

			// handle processes

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}