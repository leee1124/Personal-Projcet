package app;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Table{
	String header[] = {"날짜", "수입내역", "지출내역", "결제수단"};
	String contents[][] = new String[0][4];
	private String[] names = {"수익금", "지출금"};
	private String[] InOut = {"수익", "지출"};	
	JTable tab;
	JScrollPane scroll;
	
	Table() {		
		DefaultTableModel model = new DefaultTableModel(contents,header);
		tab = new JTable(model);
		tab.setEnabled(false);
		scroll = new JScrollPane(tab);
		scroll.setSize(1080,720);
		scroll.setLocation(600, 100);

	}
}
