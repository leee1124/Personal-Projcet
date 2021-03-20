package lib;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JLabel;
public class Time extends Thread{
	public JLabel time = new JLabel();
	private String str;

	@Override
	public void run() {
		while(true) {
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat form = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분 ss초");
			str = form.format(cal.getTime());
			time.setText(str);
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				return;
			}
		}
	}
}
