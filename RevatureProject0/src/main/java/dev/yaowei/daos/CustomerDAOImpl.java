package dev.yaowei.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dev.yaowei.entities.Account;
import dev.yaowei.entities.Customer;
import dev.yaowei.utils.ConnectionUtil;

public class CustomerDAOImpl implements CustomerDAO {
	
	private AccountDAO accountDao = AccountDAOImpl.getAccountDAOImpl();
	private static CustomerDAO sdao = null;
	private CustomerDAOImpl() {
	}
	
	public static CustomerDAO getCustomerDAOImpl() {
		if(sdao == null) {
			sdao = new CustomerDAOImpl();
		}
		
		return sdao;
	}
	

	// try with resources. Will auto close the connection for us when we finish our interaction
	public Customer createCustomer(Customer customer) {
		try(Connection conn = ConnectionUtil.getConnection()){
		String sql = "INSERT INTO projectzero_db.customer VALUES (?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, 0);
		ps.setString(2, customer.getUsername());
		ps.setString(3, customer.getPassword());
		
		ps.execute();
		
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		int key = rs.getInt("s_id");
		customer.setcId(key);
		
		return customer;
		
	} catch (SQLException e) {
		e.printStackTrace();
		return null;
	}
	}

	@Override
	public Customer getCustomerBycId(int id) {
		// try with resources. Will auto close the connection for us when we finish our interaction
				try(Connection conn = ConnectionUtil.getConnection()){
					String sql = "SELECT * FROM projectzero_db.customer WHERE c_id = ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, id);
					
					ResultSet rs = ps.executeQuery();
					
					// ResultSet returns a table. the intial record it points is before the first record
					List<Customer> customers = new ArrayList();
					while(rs.next()) {
						Customer customer = new Customer();
						customer.setcId(rs.getInt("c_id"));
						customer.setUsername(rs.getString("username"));
						customer.setPassword(rs.getString("password"));

						customers.add(customer);
					}

					return customers.size() > 0 ? customers.get(0) : null;
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
	}

	public Set<Account> getAccountByCustomer(int customerId) {
		return accountDao.getAccountsByCId(customerId);
	}
	
	@Override
	public Set<Customer> getAllCustomer() {
	
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM projectzero_db.customer";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			// ResultSet returns a table. the intial record it points is before the first record
			
			Set<Customer> customers = new HashSet<Customer>();
			
			while(rs.next()) {
				Customer customer = new Customer();
				customer.setcId(rs.getInt("c_id"));
				customer.setUsername(rs.getString("username"));
				customer.setPassword(rs.getString("password"));
				
				Set<Account> accounts = this.getAccountByCustomer(customer.getcId());
				customer.setCustomerAccounts(accounts);
				customers.add(customer);
			}
			
			return customers;
							
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Customer updateCustomer(Customer school) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE projectzero_db.customer SET username = ?, password = ? WHERE c_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, school.getUsername());
			ps.setString(2, school.getPassword());
			ps.setInt(3, school.getcId());
			ps.execute();
				
			return school;				
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteCustomer(int id) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM projectzero_db.customer WHERE c_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			int rows = ps.executeUpdate();
			if(rows>0) {
				return true;
			}else {
				return false;
			}				
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
