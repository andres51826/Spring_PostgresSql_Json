package com.springjpa.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
 
import com.springjpa.model.Customer;
import com.springjpa.repo.CustomerRepository;


 
@RestController
public class WebController {
	@Autowired
	CustomerRepository repository;
	
	@RequestMapping("/save")
	public String process(){
		repository.save(new Customer("Jack", "Smith"));
		repository.save(new Customer("Adam", "Johnson")); 
		repository.save(new Customer("Kim", "Smith"));
		repository.save(new Customer("David", "Williams"));
		repository.save(new Customer("Peter", "Davis"));
		return "Done";
	}
	@RequestMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String firtsName
			, @RequestParam String lastName) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Customer n = new Customer();
		n.setFirstName(firtsName);
		n.setLastName(lastName);
		repository.save(n);
		return "Saved";
	}
	
	@RequestMapping(path="/delete") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam long id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		repository.deleteById(id);
		return "Deleted";
	}
	
	@RequestMapping(path="/update") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam long id,@RequestParam String firtsName
			, @RequestParam String lastName) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		//Optional<Customer> n = repository.findById(id);
		Customer n = repository.findById(id).get();
		n.setFirstName(firtsName);
		n.setLastName(lastName);
		repository.save(n);
		
		//Customer n = repository.findById(id);
		return "Update";
	}
	/*

	@RequestMapping("/findall")
	public String findAll(){
		String result = "";
		
		for(Customer cust : repository.findAll()){
			result += cust.toString() + "<br>";
		}
		
		return result;
	}
	*/
	@RequestMapping(path="/all")
	public @ResponseBody Iterable<Customer> getAllUsers() {
		// This returns a JSON or XML with the users
		
		return repository.findAll();
	}
	

	
	@RequestMapping("/findall")
	public List<Customer> Listar() {
		List<Customer> customers=new ArrayList();
		for(Customer cust : repository.findAll()){
		Customer c5 = new Customer(cust.getId(),cust.getFirstName(),cust.getLastName());
		System.out.println(cust.getId()+","+cust.getFirstName()+","+cust.getLastName());
			customers.add(c5); 
		}
					

		return customers; 
	}
	
	
	@RequestMapping("/findbyid")
	public String findById(@RequestParam("id") long id){
		String result = "";
		result = repository.findById(id).toString();
		return result;
	}
	
	@RequestMapping("/findbylastname")
	public String fetchDataByLastName(@RequestParam("lastname") String lastName){
		String result = "";
		
		for(Customer cust: repository.findByLastName(lastName)){
			result += cust.toString() + "<br>"; 
		}
		
		return result;
	}
}
