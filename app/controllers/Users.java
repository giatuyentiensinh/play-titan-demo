package controllers;

import java.util.List;

import models.User;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

@Transactional
public class Users extends Controller {

	public static Result lists() {
		List list = JPA.em().createNamedQuery("lists").getResultList();
		return ok(Json.toJson(list));
	}

	public static Result saveUser() {

		Form<User> form = new Form<User>(User.class).bindFromRequest();
		if (form.hasErrors())
			return badRequest(form.errorsAsJson());
		JPA.em().persist(form.get());
		return redirect(routes.Users.lists());
	}
}
