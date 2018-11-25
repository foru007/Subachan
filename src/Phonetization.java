import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.io.*;
import java.util.*;

public class Phonetization {
    int szs=10000;
    //extra
    public String tempstr = "";
    public String tempstr2 = "";
    public String tempstr3 = "";
    public String tempstr4 = "";
    public String tempstr5 = "";
    public JLabel status = new JLabel(" ");
    public static SoundPlayer soundPlayer = new SoundPlayer();
    private String text[],  actualText[];
    StringTokenizer sT;
    File out;
    int i;
    boolean isForwardBackward, isPause, isStop;
    String fol = "Sound/";
    String O = "985";
    String delim = ",-\"\'\n; " + (char) 0x964; //comma, dash, double quote, single quote, new line, semicolon, blank space, dari
    // private SaveWindow sW;
    public static boolean saveMode = false;
    String fileNotFound;
    boolean startSentence;
    int totaltoken = 0;
    private SaveWindow sW;

    public void message(String str) {
     //   System.out.println("message#1");
        status.setText(str);
    }

    private String VerbWordConvert(String s) {
       // System.out.println("VerbWordConvert#2");
        return s;
    }
    private int LengthWithoutShortForm(String s, int l) {
        System.out.println("LengthWithoutShortForm#4");
        int j, k = 0;
        for (j = 0; j < l; j++)
        {
            if (s.charAt(j) != 0x9cd && isDepVowel(s.charAt(j)) == false)
            {
                k++;
            }
        }
        return k;
    }
   private String NumberConvert(String s) {
        System.out.println("NumberConvert#");
        return s;
    }
    private String SignConvert(String s) {
        System.out.println("SignConvert#");
        return s;
    }
    private String AbbrebiationConvert(String s) {
        System.out.println("AbbrebiationConvert#");
        return s;
    }
    private String longForm(char c) {
        System.out.println("longForm#7");
        if (isDepVowel(c))
        {
            if(c=='১')return Hex(c);
            return Hex((c - 56));
        }
        return Hex(c);
    }
    private char longForm2(char c) {
        System.out.println("longForm2#8");
        if (isDepVowel(c))
        {
            return (char) (c - 56);
        }
        return (char) c;
    }
    public boolean isPunctuation(char c) {
        System.out.println("isPunctuation#9");
        if (c == 0x964 || c == ',' || c == ';'|| c == '-' || c == '/'|| c == '&')
        {
            return true;
        }
        return false;
} 
private int firstTransition(char ch[], int j, int len, boolean isSave) {
        System.out.println("firstTransition#10");
        int tagrl=0;
        File s1 = null;
        String s = "";
        String temp ;
        int k = 1;
        try
        {
             if (j + 2 < len && ch[j + 1] == '্' && (ch[j + 2] == 'র' || ch[j + 2] == 'ল'))
            {       //r-fola, l-fola
                 if(ch[j+2]=='র'){
                 if(j+3<len && ch[j+3]=='ি'){
                     s1 = new File(fol + longForm(ch[j]) +"9c3"+".wav");
                     s+=""+ch[j]+'ৃ';
                     k += 3;
                 }
                 else{
                    s1 = new File(fol +longForm(ch[j]) +"19b0" + ".wav");
                    s+=""+ch[j]+'্'+'র';
                    k += 3;
                    if(j+3<len && isDepVowel(ch[j+3])){
                        tagrl=2;
                    }
                 }
                 }
                 else {
                    s1 = new File(fol + longForm('{') + longForm(ch[j]) + ".wav");
                    s+='{'+""+ch[j];
                    k += 2;
                    tagrl=1;
                 }
//                 s1 = new File(fol + longForm('{') + longForm(ch[j]) + ".wav");
//                    s+='{'+""+ch[j];
//                    k += 2;
//                    tagrl=1;
            } 
            else if (j == len || (j < len && (ch[j] == '্' || ch[j]=='-' )))
            {
                   s1 = new File(fol + longForm(ch[j - 1]) + longForm('}') + ".wav");
                   s+=longForm2(ch[j-1])+""+'}';
            }
            else if (j == 0 || (j > 0 && (ch[j - 1] == '্' || ch[j-1]=='-'|| ch[j-1]=='ঙ')))
            {
                    s1 = new File(fol + longForm('{') + longForm(ch[j]) + ".wav");
                    s+='{'+""+longForm2(ch[j]);
            } 
            else if (ch[j] == 'য়')
            {        //for untisto o specially handle
                    s = fol;
                System.out.println();


                    if (j == 0 || ch[j - 1] == 0x9cd)
                    {
                            ;
                    }
                    else if (isConsonent(ch[j - 1]))
                    {
                            s1 = new File(fol + longForm(ch[j - 1]) + O + ".wav");
                            s=longForm2(ch[j-1])+""+(char)0x985;
                            if (isSave == true)
                            {
                                    soundPlayer.saveSound(s1);
                            }
                            else
                            {
                                    if (soundPlayer.playSound(s1))
                                    {
                                            fileNotFound += s + " " + s1.getName() + "; ";
                                    }
                            }
                            s = fol;
                            s += O;
                    }
                    else
                    {
                        s += longForm(ch[j - 1]);
                    }
                    s += longForm(ch[j]);
                    if (j + 1 < len && isDepVowel(ch[j + 1]))
                    {
                            s += longForm(ch[j + 1]);
                            k++;
                    }                   
                    s1 = new File(s + ".wav");
            } 
            else if (isDepVowel(ch[j]))
            {
                    s1 = new File(fol + longForm(ch[j - 1]) + longForm(ch[j]) + ".wav");
                    s+=longForm2(ch[j-1])+""+longForm2(ch[j]);
            } 
            else if (isConsonent(ch[j - 1]))
            {
                    s1 = new File(fol + longForm(ch[j - 1]) + O + ".wav");
                    s+=longForm2(ch[j-1])+""+(char)0x985;                   
                    if (isSave == true)
                    {
                            soundPlayer.saveSound(s1);
                    }
                    else
                    {
                            if (soundPlayer.playSound(s1))
                            {
                                    fileNotFound += s + " " + s1.getName() + "; ";
                            }
                    }
                    s1 = new File(fol + O + longForm(ch[j]) + ".wav");
                    s+=(char)0x985+""+longForm2(ch[j]);
            }            
            else
            {
                    s1 = new File(fol + longForm(ch[j - 1]) + longForm(ch[j]) + ".wav");
                    s+=longForm2(ch[j-1])+""+longForm2(ch[j]);
            }
            if (isSave == true)
            {
                    soundPlayer.saveSound(s1);
            }
            else
            {
                if (soundPlayer.playSound(s1))
                {
                    fileNotFound += s + " " + s1.getName() + "; ";
                }
                if(tagrl==1)
                {
                        soundPlayer.playSound(new File(fol + longForm('{') + longForm(ch[j+2]) + ".wav"));
                        tagrl=0;        
                }
                else if(tagrl==2)
                {

                    temp = fol + longForm('{') + longForm(ch[j+3]) + ".wav" ;
                     soundPlayer.playSound(new File(fol + longForm('{') + longForm(ch[j+3]) + ".wav"));
                     tagrl=0;
                     System.out.println("This is the procesed string: "+temp);
                }
            }
        }
        catch (Exception exc) {
                System.out.println("First Transition: " + ch.toString() + "-----" + exc);
                tempstr = tempstr + "\n\nFirst Transition: " + ch.toString() + "-----" + exc + "\n\n";
        }
        System.out.println("this is the final string:        "+s);
        System.out.println("this is the final string:        "+s1);
        System.out.println(j + " "+ len);
        return k;
}
private String stringConvertion(String s) {
       MergeTest mt = new MergeTest();
       String str;
       String res="";
       str = "" + s;
       str = "" + str.trim();
       char cr[] = new char[50];
       char cha[] = new char[50];
       cr = str.toCharArray();
       for(int i=0;i<cr.length;i++){
           if((cr[i]=='ঁ'&&cr.length!=1) ||(i==0 && cr[i]=='্')|| (i+1==cr.length && cr[i]=='্'))continue;
           else res = res + cr[i];
       }
       cr = res.toCharArray();
       if (mt.getTest(str) == null) {
           str = ""+res;
           if(mt.errorTest(str)==false)return str;
           if((str.length()>=2 && cr[0]=='এ') ||(str.length()>2 && cr[1]=='ে'))str = mt.OAA(str);
           str = mt.AMD(str);
           str = mt.Fola(str);          
           str = mt.Process(str);
           str = mt.breakPoint(str);
       } else {
           str = mt.getTest(str);
           cha = str.toCharArray();
           if(cha[cha.length-1]=='$')return str.substring(0, cha.length-1);
           str = mt.breakPoint(str);
       }
       System.out.println("THIS IS THE PROCESSED STRING: "+str);
      return str;
}
public void graphemeToPhoneme(int k, boolean isSave) throws Exception {
        String s = text[k];
        char ch[] = s.toCharArray();
        int len = ch.length, tlen;
        File s1=null;
        int j = 0;
        System.out.println("graphemeToPhoneme#12"+s);
        try
        {
            if (len == 0)
            {
                if (!isLetter(text[k - 1].charAt(0)) && !isLetter(text[k + 1].charAt(0)))
                {
                    return;
                }
                try
                {
                    
                    s1 = new File(fol + longForm(text[k - 1].charAt((text[k - 1].length() - 1))) + longForm('}') + ".wav");
                    if (isSave == true)
                    {
                        soundPlayer.saveSound(s1);
                    } 
                    else
                    {
                        if (isPunctuation(text[k - 1].charAt(0))==false&&text[k - 1].length()!=1)
                        {
                            soundPlayer.playSound(s1);
                        }
                        soundPlayer.playSound(new File(fol + "silence" + ".wav"));
                    }
                } 
                catch (Exception exc)
                {
                    System.out.println(ch.toString() + "-----" + exc);
                    tempstr = tempstr + "\n\n" + ch.toString() + "-----" + exc + "\n\n";
                }
            } 
            else if (len == 1)
            {
                try
                {

                    s1 = new File(fol + Hex((int) ch[0]) + ".wav");
                    if (isSave == true)
                    {
                        soundPlayer.saveSound(s1);
                    }
                    else
                    {
                        soundPlayer.playSound(s1);
                    }
                    if (isPunctuation(ch[0]))
                    {
                        startSentence = true;
                    }

                } 
                catch (Exception exc)
                {
                    System.out.println(ch.toString() + "-----" + exc);
                    tempstr = tempstr + "\n\n" + ch.toString() + "-----" + exc + "\n\n";
                }
            }
            else
            {            
                j = 0;
                tlen = len;
                if (text[k + 1].length() == 1 && text[k + 1].charAt(0) == (char) 0x964)
                {
                    tlen++;
                }
                startSentence = false;
                while (j < tlen)
                {
                    j += firstTransition(ch, j, len, isSave);
                }
             }
        } catch (Exception exc) {
        }
}
public boolean isLetter(char c) {
        System.out.println("isLetter#13");
        if (isConsonent(c) || isVowel(c) || isDepVowel(c))
        {
            return true;
        }
        return false;
    }
    public boolean isConsonent(char c) {
        System.out.println("isConsonent#14");
        return (c >= 0x995 && c <= 0x9B9) || (c >= 0x9DC && c <= 0x9DF) || c == 0x9ce;
    }
    public boolean isDepVowel(char c) { 
        System.out.println("isDepVowel#");
        return ((c >= 0x9BE && c <= 0x9CC)|| c == '১');
    }
    public boolean isNumber(char c) {
        System.out.println("isNumber#");
        return (c >= 0x9E6 && c <= 0x9EF);
    }
    public boolean isVowel(char c) {
        System.out.println("isVowel#");
        return (c >= 0x985 && c <= 0x994);
    }

    public boolean isPlosive(char c) {
        System.out.println("isPlosive#");
        return (c >= 0x995 && c <= 0x9ae);
    }
   String Hex(int i) {
        return Integer.toHexString(i);
    }
    String Number(char c) {
        return "" + (c - 0x9E6);
    }
   public Phonetization() {
        System.out.println("Phonetization#17");
        status.setBorder(new EtchedBorder());
        status.setFont(new Font("Kalpurush", 0, 13));
    }
   public void setText(String str) {
        System.out.println("setText#18");
        int length; //extra
        Welcome.ps.phonetization.tempstr5 = Welcome.ps.norm.Normalize(str);
        text = new String[szs*2];
        actualText = new String[szs*2];
        length = Welcome.ps.norm.Ftotal * 2 - 1; //extra
        i = 0;
        int j = 0;
        boolean space = false;
        while (j < Welcome.ps.norm.Ftotal)
        {
            actualText[i] = Welcome.ps.norm.Ftoken[j];
            text[i] = actualText[i];
           if (text[i].length() > 0)
            {
                    String tempvar = Welcome.ps.hashvar.Search(actualText[i]);
                    if (tempvar!=null)
                    {
                        text[i] = tempvar;
                         i++;
                        space = false;
                    }
                    else
                    {
                        if(Welcome.ps.norm.Fflag[j]==11||Welcome.ps.norm.Fflag[j]==3)//
                        {
                            text[i] = actualText[i];
                        }
                        else
                        {
                            text[i] = stringConvertion(actualText[i]);
                            System.out.println("setText#"+text[i]);
                        }
                        i++;
                        space = false;
                    }
             }
          if(Welcome.ps.norm.Fflag[j]==3)
            {
                actualText[i] = text[i] = " ".trim();
                i++;
                actualText[i] = text[i] = "&";
                i++;
                actualText[i] = text[i] = " ".trim();
                
            }
            else
            {
                actualText[i] = text[i] = " ".trim();
            }
            i++;
            j++;
        }
        totaltoken = i + 1;
        text[i] = "" + (char) 0x964;
        actualText[i] = "" + (char) 0x964;
        i = 0;
        for (int cn1 = 0; cn1 < length; cn1++)
        {
            tempstr = tempstr + "actualtext[" + cn1 + "]=" + actualText[cn1] + "\r\n";
            tempstr = tempstr + "text[" + cn1 + "]=" + text[cn1] + "\r\n";
        }
    }
    public void setCurrentText() {
    }
    public void playSpeed(int v) {
        System.out.println("playSpeed#19");
        if (v < 0)
        {
            soundPlayer.playSpeed(44100 + v * 3400);
        }
        else
        {
            soundPlayer.playSpeed(44100 + v * 6400);
        }
    }
   public void play() {
        System.out.println("play#19");
       String s = "";
        i = 0;
        isPause = false;
        isStop = false;
        isForwardBackward = false;
        startSentence = true;
        soundPlayer.playStreamReset();
        soundPlayer.startPlayer();
        while (i < totaltoken && isStop == false)
        {
             s += (actualText[i].length() == 0) ? " " : actualText[i];
             fileNotFound = "";
            soundPlayer.startPlayer();
            try
            {
                graphemeToPhoneme(i, false);
            } 
            catch (Exception exc)
            {
            }
            if (text[i].length() == 1 && isPunctuation(text[i].charAt(0)))
            {
                soundPlayer.playSound();
                soundPlayer.playStreamReset();
                s = "";
            }
            i++;
            isForwardBackward = false;
        }
       status.setText("Stop");
    }
public void resume() {
        isPause = false;
        soundPlayer.startPlayer();
        status.setText(text[i]);
    }
  public void pause() {
        isPause = true;
        soundPlayer.pausePlayer();
        status.setText("Pause");
    }
   public void stop() {
        isStop = true;
        soundPlayer.stopPlayer();
        status.setText("Stop");
    }
  public void forward() {
        isForwardBackward = true;
        if (i + 5 >= text.length) {
            i = text.length;
        } else {
            i += 5;
        }
        soundPlayer.stopPlayer();
        status.setText(text[i]);
    }
   public void backward() {
        isForwardBackward = true;
        if (i - 5 < 0) {
            i = 0;
        } else {
            i -= 5;
        }
        soundPlayer.stopPlayer();
        status.setText(text[i]);
    } 
  	public void saveAudio(File out){
		if(out == null)return;
		this.out = out;
		saveMode=true;
		soundPlayer.outputStreamReset();	
		(new Help()).start();
	}
 public void saveAudio(){
		int j=0;
		int len=text.length; 		
	  	while(j<len&&saveMode){
	  		sW.setMessage(text[j],((double)j)/len);	  	
	  		status.setText(" ");		  			  		
	  		try{graphemeToPhoneme(j,true);}
	  		catch(Exception exc){}
	  		j++;	  		
	  	}
	  	sW.setMessage(" ",((double)j)/len);	  	
	  	soundPlayer.saveSoundInFile(out);
	  	sW.dispose();
	  	saveMode=false;
	  	if(j==len)JOptionPane.showMessageDialog(null,"Audio save Successfully.");
	  	else JOptionPane.showMessageDialog(null,"Audio cannot save Successfully.");
	}
 private class Help extends Thread{
		public void run(){
			sW = new SaveWindow();
			sW.setVisible(true);
			sW.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			saveAudio();
		}
	}    
   }