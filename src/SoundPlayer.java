import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

public class SoundPlayer {

    String temp, temp2;
    AudioFormat auF = null;
    AudioInputStream auP = null;
    AudioInputStream auS = null;
    AudioInputStream audioInputStream;
    SourceDataLine playSourceDataLine, saveSourceDataLine;
    byte playTempBuffer[] = new byte[20000];
    byte saveTempBuffer[] = new byte[20000];
    ByteArrayOutputStream byteArrayOutputStream;
    ByteArrayOutputStream byteArrayPlayStream;
    boolean pause,stop;
	
	public void pausePlayer(){
		pause=true;
       }

    public void stopPlayer() {
        stop = true;
    }
    public void startPlayer() {
        stop = false;
        pause = false;
    }
    public SoundPlayer() {
        auF = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, (float) 44100.0, 16,1, 2, (float) 44100.0, false);
    }

    public void playSpeed(int SR) {
        auF = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, SR, 16,1, 2, (float) 44100.0, false);
    }
    public void playStreamReset() {
        byteArrayPlayStream = new ByteArrayOutputStream();
    }

    public boolean playSound(File sound) {
        try {
            temp = sound.toString().substring(sound.toString().lastIndexOf("\\") + 1, sound.toString().indexOf(".wav") + 4);
            temp2 = findunivalue(temp);
          //  System.out.println(temp+"        "+temp2);

            Welcome.ps.phonetization.tempstr2 = Welcome.ps.phonetization.tempstr2 + "+" + temp;
            Welcome.ps.phonetization.tempstr3 = Welcome.ps.phonetization.tempstr3 + "+" + temp2;

            if (Welcome.win.tagjmf == false) {

                int cnt;
                auP = AudioSystem.getAudioInputStream(sound);
                while ((cnt = auP.read(playTempBuffer, 0, playTempBuffer.length)) != -1) {
                    if (cnt > 0) {
                        byteArrayPlayStream.write(playTempBuffer, 0, cnt);
                    }//end if
                }//end while
            }
        } catch (Exception exc) {
            System.out.println("PlaySound arg: " + exc);
            String s2 = exc.toString().substring(exc.toString().indexOf("\\") + 1, exc.toString().indexOf(".wav") + 4);
            Welcome.ps.phonetization.tempstr4 = Welcome.ps.phonetization.tempstr4 + ":" + s2 + "->" + findunivalue(s2) + ":";
            return true;
        }
        return false;
    }
   public void playSound() {
        try {
            temp = "\"\"";
            //System.out.println("::"+temp);
            temp2 = "\"\"";
            Welcome.ps.phonetization.tempstr2 = Welcome.ps.phonetization.tempstr2 + "+" + temp;
            Welcome.ps.phonetization.tempstr3 = Welcome.ps.phonetization.tempstr3 + "+" + temp2;

            if (Welcome.win.tagjmf == false) {
                byte audioData[] = byteArrayPlayStream.toByteArray();
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, auF);
                playSourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                playSourceDataLine.open(auF);
                playSourceDataLine.start();
                int k = 0, len = audioData.length;
                while (k < len && !stop) {
                    while (pause) {
                        ;
                    }
                    playSourceDataLine.write(audioData, k, (k + 20000 < len) ? 20000 : len - k);
                    k += (k + 20000 < len) ? 20000 : len - k;
                }
                playSourceDataLine.drain();
                playSourceDataLine.close();
            }
        } catch (Exception exc) {

            System.out.println("playSound: " + exc);
            Welcome.ps.phonetization.tempstr4 = Welcome.ps.phonetization.tempstr4 + ":" + exc + ":";
        }
    }

    public void outputStreamReset() {
        byteArrayOutputStream = new ByteArrayOutputStream();
    }

    public void saveSound(File sound) {
        try {
            //System.out.print(sound);
            int cnt;
            auS = AudioSystem.getAudioInputStream(sound);
            while ((cnt = auS.read(saveTempBuffer, 0, saveTempBuffer.length)) != -1) {
                if (cnt > 0) {
                    byteArrayOutputStream.write(saveTempBuffer, 0, cnt);
                }//end if
            }//end while
        //System.out.println("Finish");
        } catch (Exception exc) {
            System.out.println("<" + exc);
            Welcome.ps.phonetization.tempstr4 = Welcome.ps.phonetization.tempstr4 + ":" + exc + ":";

        }
    }

    public void saveSoundInFile(File out) {
        try {
            //System.out.print(out);
            byte audioData[] = byteArrayOutputStream.toByteArray();
            InputStream byteArrayInputStream = new ByteArrayInputStream(audioData);
            audioInputStream = new AudioInputStream(byteArrayInputStream, auF, audioData.length / auF.getFrameSize());
            if (AudioSystem.isFileTypeSupported(AudioFileFormat.Type.WAVE, audioInputStream)) {
                AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, out);
            }

        //System.out.println("Finish");
        } catch (Exception exc) {
            System.out.println("<" + exc);
            Welcome.ps.phonetization.tempstr4 = Welcome.ps.phonetization.tempstr4 + ":" + exc + ":";

        }
    }

    public String findunivalue(String tm) {
        String uni2 = "";


        if (tm.indexOf("7b") != -1) {
            tm = tm.replaceFirst("7b", "07b");
        }

        if (tm.indexOf("7d") != -1) {
            tm = tm.replaceFirst("7d", "07d");
        }
        int digitno;
        digitno = tm.length() - 4;
        if (digitno == 3) {
            uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
        } else if (digitno == 6) {
            uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
            uni2 = uni2.concat(hextoletter(tm.substring(3, 6)));
        } else if (digitno == 9) {
            uni2 = uni2.concat(hextoletter(tm.substring(0, 3)));
            uni2 = uni2.concat(hextoletter(tm.substring(3, 6)));
            uni2 = uni2.concat(hextoletter(tm.substring(6, 9)));
        } else if (digitno == 2) {
            uni2 = uni2.concat(hextoletter(tm.substring(0, 2)));
        //uni2=uni2.concat(filename[ln].substring(0, 2));
        } else if (digitno == 1) {
            //uni2=uni2.concat(hextoletter(filename[ln].substring(0, 1))); 
            uni2 = uni2.concat(tm.substring(0, 1));
        } else {


            uni2 = uni2.concat(tm.substring(0, tm.length() - 4));
        }
        return uni2;
 }
 public String hextoletter(String dig) {
        String ans = "";
        int intans;
        //intans=(Float.valueOf(dig)).intValue();
        intans = callint(dig, 16);
        return ans + (char) intans;
    }

    public int callint(String arr, int base) {
        int i, x, y, sum = 0, val;
        x = arr.length() - 1;
        for (i = 0; i < arr.length(); i++) {
            val = (int) Math.pow(base, i);
            if (arr.charAt(x) > 64 && arr.charAt(x) < 71) {
                y = arr.charAt(x) - 65 + 10;
            } else if (arr.charAt(x) > 96 && arr.charAt(x) < 103) {
                y = arr.charAt(x) - 97 + 10;
            } else {
                y = arr.charAt(x) - 48;
            }
            sum = sum + val * y;
            x--;
        }
        return sum;
    }
}