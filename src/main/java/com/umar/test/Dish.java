package com.umar.test;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="dishes")
public class Dish {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@NotBlank	
	private String title;
	
	@Min(1000)
	@Max(3000)
	private int cost;
	private int price;
	
	@Size(min=6, max=20)
	private String description;
	
	@OneToMany(mappedBy="dish")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Ingredient> ingredients;
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Dish [title=" + title + ", cost=" + cost + ", price=" + price
				+ ", description=" + description + "]";
	}
	
	public Dish() {
		// TODO Auto-generated constructor stub
	}
	
	public Dish(int id, String title, int cost, int price, String description) {
		super();
		this.id = id;
		this.title = title;
		this.cost = cost;
		this.price = price;
		this.description = description;
	}
	
	
}
