package bank.dao;


import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import bank.model.Register;

public class RegisterDaoImpl implements RegisterDao {

	@Override
	public int createRecord(List<Register> lst) {
		int i=0;
		Connection con=DBConnection.myConnection();
		Register reg=lst.get(0);
		try {
			PreparedStatement pstate=con.prepareStatement("insert into ibank values(?,?,?,?,?)");
			pstate.setInt(1,reg.getRegNO());
			pstate.setString(2, reg.getName());
			pstate.setString(3, reg.getUsername());
			pstate.setString(4, reg.getPassword());
			pstate.setFloat(5, reg.getBalance());
			i=pstate.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int deleterecord(int accNumber) {
		Connection con=DBConnection.myConnection();
		int i=0;
		try {
			PreparedStatement pstate=con.prepareStatement("delete from ibank where regno=?");
			pstate.setInt(1,accNumber);
			i=pstate.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i;
	}

	@Override
	public int updateRecord(float newBalance,int regNO) {
		Connection con=DBConnection.myConnection();
		int i=0;
		try {
			PreparedStatement pstate = con.prepareStatement("UPDATE ibank SET balance = ? WHERE regNO = ?");
			pstate.setFloat(1, newBalance);
			pstate.setInt(2, regNO);
			i = pstate.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return i;
	}

	@Override
	public List<Register> retriveRecord(int accNumber) {
		ResultSet Result;
		Register r=null;
		List<Register> lst=new ArrayList<Register>();
		Connection con=DBConnection.myConnection();
		try {
			PreparedStatement pstate=con.prepareStatement("select * from ibank where regno=?");
			pstate.setInt(1, accNumber);
			Result=pstate.executeQuery();
			if(Result.next()) {
				r=new Register(Result.getInt(1),Result.getString(2),Result.getString(3),Result.getString(4),Result.getFloat(5));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lst.add(r);
		
		return lst;
	}

	@Override
	public List<Register> displayAll() {
		ResultSet Result;
		List<Register> lst=new ArrayList<Register>();
		Connection con=DBConnection.myConnection();
		
		try {
			PreparedStatement pstate=con.prepareStatement("select * from ibank");
			Result=pstate.executeQuery();
			while(Result.next()) {
				lst.add(new Register(Result.getInt(1),Result.getString(2),Result.getString(3),Result.getString(4),Result.getFloat(5)));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lst;
	}
	
	

	@Override
	public int sendtoaccno(int regno, float amt, int regno2) {
		/*
		Connection con=DBConnection.myConnection();
		ResultSet Result,Result1;
		
		int i=0;
		try {
			PreparedStatement pstate=con.prepareStatement("select * from ibank where regno=?");
			pstate.setInt(1, regno);
			Result=pstate.executeQuery();
			
			PreparedStatement pstate2 = con.prepareStatement("UPDATE ibank SET balance = ? WHERE regNO = ?");
			pstate2.setFloat(1,(Result.getFloat(5)-amt));
			pstate2.setInt(2, regno);
			i = pstate2.executeUpdate();
			
			PreparedStatement pstate3=con.prepareStatement("select * from ibank where regno=?");
			pstate3.setInt(1, regno2);
			Result1=pstate3.executeQuery();
			
			PreparedStatement pstate4 = con.prepareStatement("UPDATE ibank SET balance = ? WHERE regNO = ?");
			pstate4.setFloat(1, (Result1.getFloat(5)+amt));
			pstate4.setInt(2, regno2);
			i = pstate4.executeUpdate();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		return i;
		*/
		    Connection con = null;
		    PreparedStatement pstate = null;
		    PreparedStatement pstate2 = null;
		    PreparedStatement pstate3 = null;
		    PreparedStatement pstate4 = null;
		    ResultSet result = null;
		    ResultSet result1 = null;
		    int i = 0;

		    try {
		        con = DBConnection.myConnection();
		        con.setAutoCommit(false); // Disable auto-commit to handle transaction manually

		        // Fetch sender's balance
		        pstate = con.prepareStatement("SELECT balance FROM ibank WHERE regno = ?");
		        pstate.setInt(1, regno);
		        result = pstate.executeQuery();

		        if (result.next()) {
		            float senderBalance = result.getFloat("balance");

		            if (senderBalance >= amt) {
		                // Update sender's balance
		                pstate2 = con.prepareStatement("UPDATE ibank SET balance = ? WHERE regno = ?");
		                pstate2.setFloat(1, senderBalance - amt);
		                pstate2.setInt(2, regno);
		                i = pstate2.executeUpdate();

		                if (i > 0) {
		                    // Fetch receiver's balance
		                    pstate3 = con.prepareStatement("SELECT balance FROM ibank WHERE regno = ?");
		                    pstate3.setInt(1, regno2);
		                    result1 = pstate3.executeQuery();

		                    if (result1.next()) {
		                        float receiverBalance = result1.getFloat("balance");

		                        // Update receiver's balance
		                        pstate4 = con.prepareStatement("UPDATE ibank SET balance = ? WHERE regno = ?");
		                        pstate4.setFloat(1, receiverBalance + amt);
		                        pstate4.setInt(2, regno2);
		                        i = pstate4.executeUpdate();

		                        if (i > 0) {
		                            con.commit(); // Commit transaction
		                        } else {
		                            con.rollback(); // Rollback if receiver's update fails
		                            i = 0; // Indicate failure
		                        }
		                    } else {
		                        con.rollback(); // Rollback if receiver is not found
		                        i = 0; // Indicate failure
		                    }
		                } else {
		                    con.rollback(); // Rollback if sender's update fails
		                    i = 0; // Indicate failure
		                }
		            } else {
		                System.out.println("Insufficient balance for transfer");
		                i = 0; // Indicate failure
		            }
		        } else {
		            System.out.println("Sender account not found");
		            i = 0; // Indicate failure
		        }
		    } catch (SQLException e) {
		        try {
		            if (con != null) {
		                con.rollback(); // Rollback transaction in case of error
		            }
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		        }
		        e.printStackTrace();
		        i = 0; // Indicate failure
		    } finally {
		        // Close all resources
		        try {
		            if (result != null) result.close();
		            if (result1 != null) result.close();
		            if (pstate != null) pstate.close();
		            if (pstate2 != null) pstate2.close();
		            if (pstate3 != null) pstate3.close();
		            if (pstate4 != null) pstate4.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return i;
		


	}

	@Override
	public int recharge(int regNO, float bal) {
		
		/*Connection con=DBConnection.myConnection();
		Connection con2=DBConnection.myConnection();
		ResultSet Result;
		int i=0;
		try {
			PreparedStatement pstate=con.prepareStatement("select * from ibank where regno=?");
			pstate.setInt(1, regNO);
			Result=pstate.executeQuery();
			 if (con != null) {
				 con.close();
			 }
			 

			PreparedStatement pstate2 = con2.prepareStatement("UPDATE ibank SET balance = ? WHERE regNO = ?");
			pstate2.setFloat(1,(Result.getFloat(5)-bal));
			pstate2.setInt(2, regNO);
			i = pstate2.executeUpdate();
			 if (con2!= null) {
				 con2.close();
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return i;
		*/
		   Connection con = null;
		    PreparedStatement pstate = null;
		    PreparedStatement pstate2 = null;
		    ResultSet result = null;
		    int i = 0;

		    try {
		        con = DBConnection.myConnection();

		        pstate = con.prepareStatement("SELECT * FROM ibank WHERE regno = ?");
		        pstate.setInt(1, regNO);
		        result = pstate.executeQuery();
		       
		        if (result.next()) {
		        	
		        	System.out.println("get bal");
		            float currentBalance = result.getFloat("balance"); 
		            System.out.println("check bal"+currentBalance+" my bal "+bal);
		            float newBalance = currentBalance - bal; 
		            System.out.println("New Bal: "+ newBalance);
		            pstate2 = con.prepareStatement("UPDATE ibank SET balance = ? WHERE regno = ?");
		            pstate2.setFloat(1, newBalance);
		            pstate2.setInt(2, regNO);
		            i = pstate2.executeUpdate();
		            System.out.println("New bal added");
		        } else {
		            System.out.println("No record found for regNO: " + regNO);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    } finally {
		        // Close all resources
		        try {
		            if (result != null) result.close();
		            if (pstate != null) pstate.close();
		            if (pstate2 != null) pstate2.close();
		            if (con != null) con.close();
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		    }

		    return i;
		}

	
	}
		
