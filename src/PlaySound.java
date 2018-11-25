
//import java.applet.Applet;
//import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.StringTokenizer;

import javax.sound.sampled.*;

import java.io.InputStream;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class PlaySound {

    public static Phonetization phonetization = new Phonetization();
    public static Normalization norm = new Normalization();
    public static HashBTTS hashvar = new HashBTTS();
    //String text;
    long time1, time2, time3, time4, time5, time6;
    int flaginit;
    //String dir = "F:\\javaworks\\Sruti\\Sound";
    String dir = "Sound";
    public static ListSoundFile lsf = Preloader.frmabout.lsf;
    public static MyTask mytask1;
    Thread thread1;
    static int tagplaying = 0;
    Clip clip;
    InputStream is;
    AudioInputStream ain;
    private JFileChooser filechooser = new JFileChooser();
    File saveFile;
    javax.swing.filechooser.FileFilter textFilter = new FileNameExtensionFilter("Text File", "txt");
    javax.swing.filechooser.FileFilter audioFilter = new FileNameExtensionFilter("Audio File", "wav");

    public synchronized void playallthesound(String text) {
        dir = System.getProperty("user.dir").toString() + "\\Sound";
        System.out.println(dir);
        if (Welcome.win.tagdebug == true) {
            Welcome.win.debugWin.settexttojtextarea("\n>" + text + "\n");
        }
        //*************     CALL PLAY   *******************  
        time1 = System.currentTimeMillis();
        phonetization.tempstr = "";
        phonetization.tempstr2 = "";
        phonetization.tempstr3 = "";
        phonetization.tempstr4 = "";
        phonetization.tempstr5 = "";

        System.out.println("class playsoung:playallthesound:");
        phonetization.setText(text.trim());
        time2 = System.currentTimeMillis();
        System.out.println("Time settext : " + (time2 - time1) + " ms");
        System.out.println("class playsoung: phonetization.play() called:");
        phonetization.play();
        time3 = System.currentTimeMillis();
        System.out.println("Time play : " + (time3 - time2) + " ms");
       

        phonetization.tempstr2 = phonetization.tempstr2.substring(1);
        phonetization.tempstr3 = phonetization.tempstr3.substring(1);
        //System.out.println("\n\nToken\n"+phonetization.tempstr5+"\n\n\n"+phonetization.tempstr +"\nMissing File:\n"+ phonetization.tempstr4+"\n\n\n.........File.........\n\n" + phonetization.tempstr2 + "\n\n" + phonetization.tempstr3); 
        String dlist = phonetization.tempstr3.trim();

        if (Welcome.win.tagdebug == true) {
            Welcome.win.debugWin.settexttojtextarea("Token :\n" + phonetization.tempstr5 + "\n\n" + phonetization.tempstr + "\n\nMissing File:\n" + phonetization.tempstr4 + "\n\n\n.........File.........\n\n" + phonetization.tempstr2 + "\n\n" + phonetization.tempstr3);
        }
//*************     PLAY SOUND FILE   *******************      
        if (Welcome.win.tagjmf == true) {

            String st;
            String ptext;
            int totalselect = 0;

            ptext = dlist;
            ptext = ptext + "+";

            int loci = -1;
            flaginit = lsf.flaginit;
            int locindex;

            int len_ptext = ptext.length();
            for (int cn11 = 0; cn11 < len_ptext; cn11++) {
                if (ptext.charAt(cn11) == '+') {
                    totalselect++;
                }
            }
            time4 = System.currentTimeMillis();
            System.out.println("Time +count : " + (time4 - time3) + " ms");

////*************     Build from list   *******************             
            String[] filelist = new String[totalselect + 2];
            filelist[0] = "-o";
            filelist[1] = "out.wav";
            st = "";
            for (int cn6 = 0,  cn11 = 0; cn11 < len_ptext; cn11++) {
                if (ptext.charAt(cn11) == '+') {

                    if (lsf.hsh.containsKey(st)) {

                        filelist[cn6 + 2] = dir + "\\" + lsf.hsh.get(st).toString();
                        //System.out.println(">"+filelist[cn6 + 2]+" i="+(cn6+2));
                        cn6++;
                    } else {
                        filelist[cn6 + 2] = dir + "\\silence.wav";
                        //System.out.println(">"+filelist[cn6 + 2]+" i="+(cn6+2));
                        cn6++;
                    }

                    st = "";
                } else {
                    st = st + ptext.charAt(cn11);
                }
            }
            time5 = System.currentTimeMillis();
            System.out.println("Time buildlist : " + (time5 - time4) + " ms");

            new Concat(filelist);

            time6 = System.currentTimeMillis();
            System.out.println("Time concat : " + (time6 - time5) + " ms");


////*************     PLAY SOUND FILE   *******************    

            try {

                //System.out.println("Start app. \t" + System.currentTimeMillis());
                URL url = new URL("file:" + "out.wav");

                is = url.openStream();
                ain = AudioSystem.getAudioInputStream(is);
                DataLine.Info info = new DataLine.Info(Clip.class, ain.getFormat());
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(ain);
                //System.out.println("Start Clip \t" + System.currentTimeMillis());
                clip.start();
            //System.out.println("Clip started \t" + System.currentTimeMillis());
            } catch (Exception ex) {
                Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      //   JOptionPane.showMessageDialog(null,"[ALL sound has finised playing]");
    }
    
    public void playthesound(String text) {
        if (tagplaying == 1) {
            mytask1.stop();
            thread1.stop();
            if (Welcome.win.tagjmf == true) {
                clip.stop();
            }
            tagplaying = 2;
        }
        mytask1 = new MyTask(text);
        mytask1.start();
        //mytask1.run();
        System.out.println(">>play finished.");
        tagplaying = 1;
       // System.out.println("Running playthesound function");
    }
    
    public void stopthesound() {
        if (tagplaying == 1) {
            mytask1.stop();
            thread1.stop();
            if (Welcome.win.tagjmf == true) {
                clip.stop();
            }
            tagplaying = 2;
        System.out.println(">>stop finished.");
        }
    }
    
    public void pausethesound() {
        mytask1.suspend();
        thread1.suspend();
        if (Welcome.win.tagjmf == true) {
            clip.stop();

        }

    }

    public void resumethesound() {
        mytask1.resume();
        thread1.resume();
        if (Welcome.win.tagjmf == true) {
            clip.start();
        }

    }

    public void savethesound() {
        stopthesound();
        if (checkSaveState()) {
            return;
        }

        phonetization.saveAudio(SaveAudioFile());

    }

    private File SaveAudioFile() {
        try {
            filechooser.removeChoosableFileFilter(textFilter);
            filechooser.addChoosableFileFilter(audioFilter);
            filechooser.setSelectedFile(new File("btts.wav"));
            if (filechooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION) {
                return null;
            }
            saveFile = filechooser.getSelectedFile();
            if (saveFile.exists()) {
                String msg = "<html><table><tr><td>" + saveFile.getPath() + " already exists.</td></tr><tr><td>Do you want to replace it?</td></tr></table></html>";
                String title = "Save File.";
                if (JOptionPane.showConfirmDialog(null, msg, title, JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                    return null;
                }
            }
            String fileName = saveFile.getPath();
            if (saveFile.getName().contains(".".subSequence(0, 1)) == false) {
                fileName += ".wav";
            }
            return (new File(fileName));
        } catch (Exception exc) {
            System.out.println(exc);
        }
        return null;
    }

    public boolean checkSaveState() {
        if (Phonetization.saveMode) {
            JOptionPane.showMessageDialog(null, "System is busy to save audio.");
            return true;
        }
        return false;
    }

    public class MyTask extends Thread {

        String msg = "blank";

        public MyTask(String s) {
            msg = s;
        }

        public void run() {
            //playallthesound(msg);

            StringTokenizer stoke = new StringTokenizer(msg, ",?!।", true);
            int tagfirst = 0;
            Sleeper sleepy, tempsleepy = null;

            // = new Thread(new TestThread(1));
            int cn1 = 0;
            while (stoke.hasMoreElements()) {
                try {
//                try {
                    System.out.println("enter in mytask run");
                    String s = stoke.nextToken();
                    //System.out.println(s);
                    //playallthesound(s);
//                    if(tagfirst!=0){
                    //sleepy = new Sleeper(tempsleepy, tagfirst, s);
                    thread1 = new Thread(new Joiner(s, cn1));
                    cn1++;
//                    }
//                    else{
//                        sleepy = new Sleeper(tempsleepy, tagfirst, s);
//                    }
                    //sleepy.join();
                    thread1.start();
                    //sleepy.start();
                    //tempsleepy=sleepy;
                    thread1.join();

//                    Thread t = new Thread();
//                    t.start();
//                    while (clip.isRunning()) {
//
//                        try {
//
//                            System.out.println(s);
//                            t.sleep(10);
//
//                        } catch (InterruptedException ex) {
//                        }
//                    }






//                } catch (InterruptedException ex) {
//                    Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
//                }
                    tagfirst = 1;
                } catch (InterruptedException ex) {
                    Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        //System.out.println("Hi world");
        }

        class Sleeper extends Thread {

            String name;
            Sleeper prev;
            int tagfirst;

            public Sleeper(Sleeper prev, int tagfirst, String name) {
                //super(name);
                this.prev = prev;
                this.tagfirst = tagfirst;
                this.name = name;
            //System.out.println("tagfirst " + tagfirst + " " + name);
            //start();
            }

            public void run() {
                try {
                    if (tagfirst == 1) {
                        prev.join();
                    }
                    playallthesound(name);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }

        class Joiner extends Thread {

            String name;
            int id;

            public Joiner(String name, int id) {
                this.name = name;
                this.id = id;
            //start();
            }

            public void run() {


                playallthesound(name);
                if (Welcome.win.tagjmf == true) {
                    System.out.println("Thread " + id + " is waiting..");
                    for (int i = 1; clip.isOpen() == true && clip.isRunning() == false; i++) {
                        try {
                            this.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                    for (int i = 1; clip.isRunning(); i++) {
                        try {

                            this.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        clip.close();
                        ain.close();
                        is.close();
                        System.out.println("Thread " + id + " is finished...");
                    } catch (IOException ex) {
                        Logger.getLogger(PlaySound.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
        }
    }
}
