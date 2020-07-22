package dev.yaowei.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import dev.yaowei.entities.Account;
import dev.yaowei.exceptions.NegativeBalanceExceptions;
import dev.yaowei.utils.ConnectionUtil;

public class AccountDAOImpl implements AccountDAO {
	private static AccountDAO adao = null;
	private AccountDAOImpl() {
		
	}
	
	public static AccountDAO getAccountDAOImpl() {
		if(adao == null) {
			adao = new AccountDAOImpl();
		}
		
		return adao;
	}
	
	@Override
	public Account createAccounts(Account account) throws NegativeBalanceExceptions {
		if (account.getBalance() < 0) {
			throw new NegativeBalanceExceptions();
		}
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO projectzero_db.account VALUES (?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, account.getaId()); //aid
			ps.setInt(2, account.getcId()); //cid
			ps.setString(3, account.getAccountName()); //account name
			ps.setInt(4, account.getBalance());
			ps.execute(); 
			
			ResultSet rs = ps.getGeneratedKeys(); // returns a virtual table
			rs.next();// moves you to the first record in that table
			//int key = rs.getInt("stu_id");
			int key = rs.getInt("a_id");
			//student.setStuId(key);
			account.setaId(key);
			
			return account;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Set<Account> getAllAccounts(){
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM projectzero_db.account";
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			Set<Account> accounts = new HashSet<Account>();
			
			while(rs.next()) {
				
				Account account = new Account();
				account.setaId(rs.getInt("a_id"));
				account.setAccountName(rs.getString("account_name"));
				account.setcId(rs.getInt("c_id"));	
				account.setBalance(rs.getInt("balance"));
				accounts.add(account); //adding each account info to the accounts set
				
			}
			
			return accounts;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Account getAccountById(int id) {
		
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM projectzero_db.account WHERE a_id = ?"; //change
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery(); // get us back a table with all the information
			rs.next();// move table to first record
			
			Account account = new Account();
			account.setaId(rs.getInt("a_id"));
			account.setAccountName(rs.getString("account_name"));
			account.setcId(rs.getInt("c_id"));
			account.setBalance(rs.getInt("balance"));
			
			return account;
		
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Set<Account> getAccountsByCId(int id){
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM projectzero_db.account WHERE c_id = ?"; //change
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			Set<Account> accounts = new HashSet<Account>();
			
			while(rs.next()) {
				
				Account account = new Account();
				account.setaId(rs.getInt("a_id"));
				account.setAccountName(rs.getString("account_name"));
				account.setcId(rs.getInt("c_id"));	
				account.setBalance(rs.getInt("balance"));
				accounts.add(account);
				
			}
			
			return accounts;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public Account updateAccount(Account account) throws NegativeBalanceExceptions {
		if (account.getBalance() < 0) {
			throw new NegativeBalanceExceptions();
		
		}
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE projectzero_db.account SET account_name = ?, c_id = ? WHERE a_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, account.getAccountName());
			ps.setInt(2, account.getcId());
			ps.setInt(3, account.getaId());
			ps.execute();
			
			return account;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean deleteAccountById(int id) {
		try(Connection conn = ConnectionUtil.getConnection()){
			String sql = "DELETE FROM projectzero_db.account WHERE a_id =?";
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
