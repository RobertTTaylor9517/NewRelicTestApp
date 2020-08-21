package com.newrelic.shopjava.entities;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "products")
public class Product {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name = "name", columnDefinition = "LONGTEXT")
	private String name;
	private double price;
	
	@Enumerated
	private Category category;
	
	@Column(name = "description", columnDefinition = "LONGTEXT")
	private String description;
	
	@Column(name = "img", columnDefinition = "LONGTEXT")
	private String img;
	
	private Integer quantity;
	private Integer rating;
	
	@JsonIgnore
	@OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Review> reviews;

	public Product() {
	}

	public Product(long id, String name, double price, Category category, String description, String img, Integer quantity, Integer rating) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.category = category;
		this.description = description;
		this.img = img;
		this.quantity = quantity;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
	
	

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, description, id, img, name, price);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return category == other.category && Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(img, other.img) && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(price) == Double.doubleToLongBits(other.price);
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + ", description="
				+ description + ", img=" + img + "]";
	}
	
	
	
	
}
