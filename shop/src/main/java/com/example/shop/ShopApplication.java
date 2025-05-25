package com.example.shop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);

		Test test = new Test();
		test.setName("ABC");
		test.setAge(-10);
		test.addAge();
		System.out.println(test.getName());
		System.out.println(test.getAge());
	}

}

@Getter
@Setter
class Test {
	private String name = "";
	private Integer age = 0;

	public String getName() {
		return name;
	}

	public Integer getAge() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(Integer age) {
		if(age >= 0) this.age = age;
//		else this.age = this.age;
//		this.age = age;
	}

	public void addAge() {
		this.age += 1;
	}
}
