package controllers;

import java.util.ArrayList;
import java.util.List;

import models.edge.Friend;
import models.vertex.Person;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.thinkaurelius.titan.core.TitanGraph;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.frames.FramedGraph;
import com.tinkerpop.frames.FramedGraphFactory;
import com.tinkerpop.frames.FramedTransactionalGraph;
import com.tinkerpop.frames.modules.gremlingroovy.GremlinGroovyModule;
import com.tinkerpop.frames.modules.javahandler.JavaHandlerModule;

public class Application extends Controller {

	public static TitanGraph graph;

	public static Result showPerson() {

		Iterable<Vertex> it = graph.getVertices();

		for (Vertex vertex : it) {
			Object id = vertex.getId();
			Logger.info("id: " + id);
			Logger.info("name: " + vertex.getProperty("name"));
			Logger.info("age: " + vertex.getProperty("age"));
			Logger.info("desc: " + vertex.getProperty("desc"));
			Logger.debug("-----------");
		}
		
		FramedGraphFactory factory = new FramedGraphFactory(
				new GremlinGroovyModule());
		FramedTransactionalGraph<TitanGraph> frame = factory.create(graph);

		List<Person> listPersons = new ArrayList<Person>();

		List<ObjectNode> vexters = new ArrayList<ObjectNode>();

		frame.getVertices().forEach(e -> {
			Person person = frame.getVertex(e.getId(), Person.class);
			listPersons.add(person);
			ObjectNode vexter = Json.newObject();
			vexter.put("id", e.getId().toString());
			vexter.put("label", person.getName());
			vexters.add(vexter);
		});

		ObjectNode node = Json.newObject();
		node.put("persons", Json.toJson(listPersons));
		node.put("vexters", Json.toJson(vexters));
		return ok(Json.toJson(node));
	}

	public static Result showFriend() {
		Logger.info("controller.Application.showFriend");
		Iterable<Edge> it = graph.getEdges();

		List<Friend> friends = new ArrayList<Friend>();
		List<ObjectNode> listId = new ArrayList<ObjectNode>();

		for (Edge edge : it) {
			Object id = edge.getId();
			Logger.debug("-------------------------");
			Logger.info("id: " + id);
			Logger.info("label: " + edge.getLabel());
			Logger.info("Properties: ");
			edge.getPropertyKeys().forEach(e -> {
				System.out.println(e + " : " + edge.getProperty(e));
			});
			FramedGraph<TitanGraph> frame = new FramedGraphFactory(
					new GremlinGroovyModule()).create(graph);
			Friend edge_friend = frame.getEdge(id, Friend.class);

			String in = edge_friend.getInPerson().asVertex().getId().toString();
			String out = edge_friend.getOutPerson().asVertex().getId()
					.toString();

			ObjectNode idNode = Json.newObject();
			idNode.put("from", in);
			idNode.put("to", out);
			listId.add(idNode);

			edge_friend.setRelation("Thật là tuyệt vời");

			friends.add(edge_friend);
		}

		ObjectNode node = Json.newObject();
		node.put("edges", Json.toJson(friends));
		node.put("ids", Json.toJson(listId));

		return ok(Json.toJson(node));
	}

	public static Result savePerson() {
		Form<People> form = new Form<People>(People.class).bindFromRequest();
		if (form.hasErrors())
			// return badRequest("Form không hợp lệ");
			return badRequest(form.errorsAsJson());

		People people = form.get();

		FramedGraphFactory factory = new FramedGraphFactory(
				new GremlinGroovyModule());
		FramedTransactionalGraph<TitanGraph> frame = factory.create(graph);
		Vertex vertex = frame.addVertex(1);
		Person person = frame.getVertex(vertex.getId(), Person.class);
		person.setName(people.name);
		person.setAge(people.age);
		person.setDesc(people.desc);

		graph.commit();

		return ok(Json.toJson(people));
	}

	public static Result updateEdge() {
		JsonNode params = request().body().asJson();
		String personname = params.get("person").asText();

		FramedGraphFactory factory = new FramedGraphFactory(
				new GremlinGroovyModule(), new JavaHandlerModule());
		FramedTransactionalGraph<TitanGraph> frame = factory.create(graph);

		Vertex v = graph.getVertices("name", personname).iterator().next();

		String namein = params.get("inperson").asText();
		Vertex vin = graph.getVertices("name", namein).iterator().next();
		Logger.info("in person: " + namein);

		String nameout = params.get("outperson").asText();
		Vertex vout = graph.getVertices("name", nameout).iterator().next();
		Logger.info("out person: " + nameout);

		Object idedge1 = graph.addEdge(1, v, vout, "friend").getId();
		Object idedge2 = graph.addEdge(1, vin, v, "know").getId();

		Friend edge1 = frame.getEdge(idedge1, Friend.class);
		Friend edge2 = frame.getEdge(idedge2, Friend.class);

		frame.commit();
		
		System.out.println("Vexter: " + Json.toJson(v.getProperty("name")));
		
		System.out.println("Inperson edge1:" + Json.toJson(edge1.getInPerson()));
		System.out.println("Outperson edge1: " +  Json.toJson(edge1.getOutPerson()));

		System.out.println("Inperson edge1:" + Json.toJson(edge2.getInPerson()));
		System.out.println("Outperson edge1: " +  Json.toJson(edge2.getOutPerson()));
		
		return ok(Json.toJson(edge2));
	}

	public static class People {
		@Required
		public String name;
		@Required
		public Integer age;
		@Required
		public String desc;

		public People() {
			super();
		}
	}
}
