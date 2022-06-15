package mrSpace;

import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		/* 寫檔 */
		FileReader record_fr = new FileReader("src/record.txt");
		BufferedReader rea = new BufferedReader(record_fr);
		int index = 0;
		String str = rea.readLine();
		while (str != null) {
			arr.score[index] = Integer.parseInt(str);
			str = rea.readLine();
			index++;
		}
		record_fr.close();

		/* debug */
		for (int i = 0; i < 4; i++) {
			System.out.println(arr.score[i]);
		}
		Menu menu = new Menu();

	}
}

/* 記錄分數的靜態陣列 可以在任何地方透過class的名稱呼叫它 像是arr.score[0] */
class arr {
	static int score[] = { 0, 0, 0, 0 };
}