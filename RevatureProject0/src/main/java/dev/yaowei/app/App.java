package dev.yaowei.app;

import dev.yaowei.controllers.AccountController;
import dev.yaowei.controllers.CustomerController;
import io.javalin.Javalin;

public class App {
	public static void main(String[] args) {
		Javalin app = Javalin.create().start(7010);
		
		
		// REST APIs should support at a minimum the basic CRUD operations
		// read operations
//		app.get("/customers", CustomerController.getAllCustomers);// this method returns all schools	
		app.get("/customers/:cid", CustomerController.getCustomerById);// this returns a specific customer based on the id
		app.get("/customers", CustomerController.getAllCustomers);
		app.get("/customers/:cid/accounts/:aid", CustomerController.getAccountWithcIdAndaId);//returns All the students in that school
		app.get("/customers/:cid/accounts", CustomerController.getAccountsByCustomerId);

		// create operation
		app.post("/customers", CustomerController.createCustomer	);
	
		//update operation
		app.put("/customers", CustomerController.updateCustomer);
		
		//delete operation
		app.delete("/customers/:cid", CustomerController.deleteCustomer);
		
		//	
		app.get("/accounts", AccountController.getAllAccount);
		app.get("/accounts/:aid", AccountController.getAccountById);
		app.delete("/accounts/:aid", AccountController.deleteAccount);
		
		app.put("/accounts", AccountController.updateAccount);
		app.post("/accounts", AccountController.createAccount);
		
		/*

		app.get("/customers/:id/accounts/:aid", StudentController.getStudentById);//returns a student with that id
		app.get("/customers/:aid", StudentController.getStudentById); // also correct
		
		app.post("/customers/:id/accounts", StudentController.createStudent);//Add a new student to that school
		
		app.put("/customers/:id/accounts", StudentController.updateStudent);// update a student at that school
		
		app.delete("/customers/:id/accounts/:aid", StudentController.deleteStudent);// delete this student at that school
		
		
	
		
		*/
	}
}
