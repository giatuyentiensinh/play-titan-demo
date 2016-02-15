package models.edge;

import models.vertex.Person;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.frames.Adjacency;
import com.tinkerpop.frames.EdgeFrame;
import com.tinkerpop.frames.InVertex;
import com.tinkerpop.frames.OutVertex;
import com.tinkerpop.frames.Property;

public interface Friend extends EdgeFrame {

	@Property("relation")
	public String getRelation();

	@Property("relation")
	public void setRelation(String relation);

	@Adjacency(label = "friend", direction = Direction.OUT)
	public void setOutPerson(Person person);

	@Adjacency(label = "know", direction = Direction.IN)
	public void setInPerson(Person person);

	@OutVertex
	public Person getOutPerson();

	@InVertex
	public Person getInPerson();

}
