import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;

import com.thinkaurelius.titan.core.TitanFactory;

public class Global extends GlobalSettings {

	@Override
	public void onStart(Application app) {
		Logger.info("Start application ...");
		String path = Play.application().configuration().getString("titan");

		try {
			controllers.Application.graph = TitanFactory.open(path);

		} catch (Throwable e) {
			Logger.error(e.getMessage());

			Configuration conf = new BaseConfiguration();
			conf.addProperty("storage.backend", "cassandra");
			conf.addProperty("storage.hostname", "localhost");
			// conf.addProperty("index.search.backend", "elasticsearch");
			// conf.addProperty("index.search.hostname", "localhost");
			// conf.addProperty("index.search.directory", "./db");
			// conf.addProperty("index.search.elasticsearch.local-mode", true);
			// conf.addProperty("index.search.elasticsearch.client-only",
			// false);
			controllers.Application.graph = TitanFactory.open(conf);

		}

		System.out.println(controllers.Application.graph);
		Logger.info("Global.onStart");

		super.onStart(app);
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Stop applicaton !");
		controllers.Application.graph.shutdown();
		super.onStop(app);
	}

}
