package bank.dao;

import java.util.List;

import bank.model.Register;

public interface RegisterDao {

	
	int createRecord(List<Register> lst);
	int deleterecord(int accNumber);
	int updateRecord(float new_bal, int regNO);
	List<Register> retriveRecord(int accNumber);
	List<Register> displayAll();
	int sendtoaccno(int regno,float amt,int regno2);
	int recharge(int regNO,float bal);
	
	
}
