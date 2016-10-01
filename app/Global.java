import org.apache.commons.configuration.BaseConfiguration;
import org.apache.commons.configuration.Configuration;

import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.Play;

import com.thinkaurelius.titan.core.PropertyKey;
import com.thinkaurelius.titan.core.TitanFactory;
import com.thinkaurelius.titan.core.schema.Mapping;
import com.thinkaurelius.titan.core.schema.TitanManagement;
import com.tinkerpop.blueprints.Vertex;

public class Global extends GlobalSettings {

	public static final String LABEL = "PERSON";
	public static final String NAME = "name";
	public static final String SEARCH_NAME = "Search_name";
	public static final String SEARCH_ENGINE = "search";

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
		TitanManagement mgmt = controllers.Application.graph
				.getManagementSystem();
		if (!mgmt.containsVertexLabel(LABEL)) {
			PropertyKey name = mgmt.makePropertyKey(NAME)
					.dataType(String.class).make();
			mgmt.makeVertexLabel(LABEL).make();
			mgmt.buildIndex(SEARCH_NAME, Vertex.class)
					.addKey(name, Mapping.TEXT.getParameter())
					.buildMixedIndex(SEARCH_ENGINE);
			mgmt.commit();
		}
		super.onStart(app);
	}

	@Override
	public void onStop(Application app) {
		Logger.info("Stop applicaton !");
		controllers.Application.graph.shutdown();
		super.onStop(app);
	}

}
