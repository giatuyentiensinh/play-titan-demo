package models.vertex;

import com.tinkerpop.frames.Property;
import com.tinkerpop.frames.VertexFrame;
import com.tinkerpop.frames.annotations.gremlin.GremlinGroovy;
import com.tinkerpop.frames.annotations.gremlin.GremlinParam;

public interface Person extends VertexFrame {

	@Property("name")
	public String setName(String name);

	@Property("age")
	public Integer setAge(Integer age);

	@Property("desc")
	public String setDesc(String desc);

	@Property("name")
	public String getName();

	@Property("age")
	public Integer getAge();

	@Property("desc")
	public String getDesc();

	@GremlinGroovy(value = "it.has('name', name)")
	public Iterable<Person> getPerson(@GremlinParam("name") String name);

}
