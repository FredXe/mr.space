package mrSpace;

import javax.swing.JFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Main {
	public static void main(String[] args) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
		/* �g�� */
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

/* �O�����ƪ��R�A�}�C �i�H�b����a��z�Lclass���W�٩I�s�� ���Oarr.score[0] */
class arr {
	static int score[] = { 0, 0, 0, 0 };
}