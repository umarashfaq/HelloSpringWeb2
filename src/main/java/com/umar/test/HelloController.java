package com.umar.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
public class HelloController {
	private static final String VIEW_DISH_FORM = "/WEB-INF/add_dish_form.jsp";
	private static final String VIEW_DISH_FORM_SUCCESS = "/WEB-INF/add_dish_form_success.jsp";
	private static final String VIEW_DISH_LIST = "/WEB-INF/dishes.jsp";
	
	@PersistenceContext
	private EntityManager em;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
	@RequestMapping(value={"/hello", "/"})
	public String sayHello(@RequestParam(required=false, defaultValue="X", value="firstName") String name, @RequestParam String city, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("city", city);
		return "/WEB-INF/hello.jsp";		
	}
	
	@RequestMapping("/persons/{name}")
	public String test2(@PathVariable String name, Model model, @RequestParam Date date) throws ParseException {
		// dd-mm-yyyy
		
		System.out.println(date);
		
		model.addAttribute("name", name);
		return "/WEB-INF/hello.jsp";
	}
	
	@RequestMapping("/ingredients")
	public String showIngredients(Model model) {
		model.addAttribute("ingredients", fetchAllIngredients());
		return "/WEB-INF/ingredients.jsp";
	}
	
	@RequestMapping("/dishes")
	public String showDishes(Model model) {
		model.addAttribute("dishes", fetchSpecificDishes());
		return VIEW_DISH_LIST;
	}
	
	@RequestMapping(value="/dishes/add", method=RequestMethod.GET)
	public String getDishForm(Model model) {
		model.addAttribute("command", new Dish());
		return VIEW_DISH_FORM;
	}
	
	@RequestMapping(value="/dishes/add", method=RequestMethod.POST)
	public String postDishForm(@ModelAttribute("command") @Valid Dish dish, BindingResult result,  Model model) {
		if ( result.hasErrors() ) {
			result.reject(null, "There was an error in the form");
			return VIEW_DISH_FORM;
		}
		
		addDish(dish);
		
		model.addAttribute("dish", dish);
		return "redirect:/dishes";
	}
	
	private void addDish(Dish dish) {
		em.persist(dish);
	}
	
	private List<Dish> fetchAllDishes() {		
		return em.createQuery("select d from Dish d")
					.getResultList();
	}
	
	private List<Ingredient> fetchAllIngredients() {		
		return em.createQuery("select d from Ingredient d")
					.getResultList();
	}
	
	private List<Dish> fetchSpecificDishes() {
		return em.createQuery("select d from Dish d join d.ingredients i where i.name='Water'")
				.getResultList();
	}
}
