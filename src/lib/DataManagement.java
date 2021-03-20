package lib;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JFrame;
public class DataManagement {
	private int income;
	private int outcome;
	private int balance;
	private String paymentMethod;
	private String currentTime;
		
	public DataManagement() {
	}
	
	public DataManagement(String currentTime,int income, int outcome, String paymentMethod) {
		this.currentTime = currentTime;
		this.income=income;
		this.outcome=outcome;
		this.paymentMethod=paymentMethod;
		setBalance();
	}
	
	public void setIncome(int income) {
		this.income += income;
		setBalance();
	}
	public void setOutcome(int outcome) {
		this.outcome += outcome;
		setBalance();
	}
	public void setBalance() {
		balance = income - outcome;
	}
	public int getTotalIncome() {
		return income;
	}
	public int getTotalOutcome() {
		return outcome;
	}
	
	public int getBalance() {
		return balance;
	}
	public String getTime() {
		return currentTime;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
}
