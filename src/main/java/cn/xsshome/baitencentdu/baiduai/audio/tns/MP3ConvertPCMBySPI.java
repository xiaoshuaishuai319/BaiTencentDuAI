package cn.xsshome.baitencentdu.baiduai.audio.tns;

import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
/**
 * MP3转PCM方法
 * @author 小帅丶
 *
 */
public class MP3ConvertPCMBySPI {
    public static void main(String[] args) {
        String path = "C:/Users/Administrator/text2audio/VOICE1513237078";
        String mp3filepath = path + ".mp3";
        String pcmfilepath = path + ".pcm";
        try {
        	MP3ConvertPCMBySPI.convertMP32PCM(mp3filepath, pcmfilepath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	 /**
     * MP3转换PCM文件方法
     * @param mp3filepath  原始文件路径
     * @param pcmfilepath  转换文件的保存路径
     * @throws Exception
     */
    public static void convertMP32PCM(String mp3filepath, String pcmfilepath) throws Exception {
    	//转换PCM audioInputStream 数据
        AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
        //写入PCM预给定的文件
        AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, new File(pcmfilepath));
    }
    /**
     * 获取PCM AudioInputStream 数据
     * @param mp3filepath
     * @return AudioInputStream
     */
    private static AudioInputStream getPcmAudioInputStream(String mp3filepath) {
        File mp3File = new File(mp3filepath);
        AudioInputStream audioInputStream = null;
        AudioFormat targetFormat = null;
        try {
            AudioInputStream in = null;
            MpegAudioFileReader mp = new MpegAudioFileReader();
            in = mp.getAudioInputStream(mp3File);
            AudioFormat baseFormat = in.getFormat();
            targetFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate(), 16,
                    baseFormat.getChannels(), baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);
            audioInputStream = AudioSystem.getAudioInputStream(targetFormat, in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return audioInputStream;
    }
    /**
     * 播放MP3方法
     * @param mp3filepath
     * @throws Exception
     */
    public static void playMP3(String mp3filepath) throws Exception {
        File mp3 = new File(mp3filepath);
        // 播放
        int k = 0, length = 8192;
        AudioInputStream audioInputStream = getPcmAudioInputStream(mp3filepath);
        if (audioInputStream == null)
            System.out.println("null audiostream");
        AudioFormat targetFormat;
        targetFormat = audioInputStream.getFormat();
        byte[] data = new byte[length];
        DataLine.Info dinfo = new DataLine.Info(SourceDataLine.class, targetFormat);
        SourceDataLine line = null;
        try {

            line = (SourceDataLine) AudioSystem.getLine(dinfo);
            line.open(targetFormat);
            line.start();

            int bytesRead = 0;
            byte[] buffer = new byte[length];
            while ((bytesRead = audioInputStream.read(buffer, 0, length)) != -1) {
                line.write(buffer, 0, bytesRead);
            }
            audioInputStream.close();

            line.stop();
            line.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("audio problem " + ex);

        }
    }
}
