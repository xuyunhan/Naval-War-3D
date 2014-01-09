package HCQ;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JSlider;

public class GunSound extends Thread {
	private File file = null;
	private int Currindex = 0;
	private FloatControl volume = null;
	private Vector<String> playlist = null;
	private SourceDataLine line = null;
	private DataLine.Info info = null;
	private AudioFormat Format = null;
	private AudioInputStream audioInputStream = null;
	private long time = 0;
    public boolean read;
	
	public GunSound(Vector<String> playlist) {
		read = true;
		this.playlist = playlist;
	}

	public void init() {
		try {
			if (audioInputStream != null)
				audioInputStream.close();
			if (line != null)
				line.close();
			file = new File(playlist.get(Currindex));
			audioInputStream = AudioSystem.getAudioInputStream(file);
			Format = audioInputStream.getFormat();
			if (Format.getEncoding() == AudioFormat.Encoding.PCM_UNSIGNED
					|| Format.getEncoding() == AudioFormat.Encoding.ULAW
					|| Format.getEncoding() == AudioFormat.Encoding.ALAW
					|| Format.getEncoding() == AudioFormat.Encoding.PCM_SIGNED) {
				time = (file.length() * 8000000)
						/ ((int) (Format.getSampleRate() * Format
								.getSampleSizeInBits()));
			} else {
				int bitrate = 0;
				if (Format.properties().get("bitrate") != null) {
					// 取得播放速度(单位位每秒)
					bitrate = (int) ((Integer) (Format.properties()
							.get("bitrate")));
					if (bitrate != 0)
						time = (file.length() * 8000000) / bitrate;
				}
			}
			Format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
					Format.getSampleRate(), 16, Format.getChannels(),
					Format.getChannels() * 2, Format.getSampleRate(), false);
			audioInputStream = AudioSystem.getAudioInputStream(Format,
					audioInputStream);
			info = new DataLine.Info(SourceDataLine.class, Format);
			try {
				line = (SourceDataLine) AudioSystem.getLine(info);
				line.open(Format);
				line.start();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			init();
		}
	}

	public FloatControl getVolume() {
		return volume;
	}

	public int getCurrindex() {
		return Currindex;
	}

	public void Next() {
		line = null;
	}

	public void Previous() {
		Currindex -= 2;
		line = null;
	}

	public void star() {
		if (playlist.size() > 0) {
			init();
			this.start();
		}
	}

	public void run() {
		int nBytesRead = 0;
		byte[] abData = new byte[102400];
		while (read) {
			nBytesRead = 0;
			while (nBytesRead != -1 && line != null) {
				try {
					nBytesRead = audioInputStream
							.read(abData, 0, abData.length);
				} catch (IOException e) {
					e.printStackTrace();
				}
				if (nBytesRead >= 0 && line != null) {
					line.write(abData, 0, nBytesRead);
				}
			}
			read = false;
			// if(!state.getStop()){
			//Currindex++;
			 //if(Currindex>=playlist.size())Currindex=0;
			// init();
		}

	}
}

