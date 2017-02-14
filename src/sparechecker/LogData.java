package sparechecker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogData {
	private static FileWriter fw;
	private static BufferedWriter bw;
	private static String path = "log.csv";
	
	public static void init() {
		if(new File(path).isFile()) {
			try {
				fw = new FileWriter(path, true);
				bw = new BufferedWriter(fw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			try {
				fw = new FileWriter(path);
				bw = new BufferedWriter(fw);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void log(int period, Student s) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Period " + (char)(period + 65) + ",");
		sb.append(s.getFirstName() + ",");
		sb.append(s.getLastName() + ",");
		sb.append(s.getStudentNumber() + ",");
		sb.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		bw.append(sb.toString() + "\n");
		bw.flush();
	}
}
