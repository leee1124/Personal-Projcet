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
	String header[] = {"��¥", "���Գ���", "���⳻��", "��������"};
	String contents[][] = new String[0][4];
	private String[] names = {"���ͱ�", "�����"};
	private String[] InOut = {"����", "����"};	
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
