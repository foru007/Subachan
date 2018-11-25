package bddict;

//import .*;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.Integer;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Hash {

    public String word,  word1,  word2;
    public int length,  total_list,  sec_total_list;
    public Hashtable ht;
    public Hashtable sec_ht;
    public Levenshtein dist = new Levenshtein();
    public Store store[] = new Store[100000];/// for original
    public Store sec_store[] = new Store[10000];///for deleting one latter
    public int sz = 5000;
    public String rank_str[] = new String[sz];
    // testing git
    public String token[] = new String[sz];
    public int rank_val[] = new int[sz];
    public int org_val[] = new int[sz];
    public int sx_val[] = new int[sz];
    public int pre_val[] = new int[sz];
    public int totaltoken;
    public int s_counter;
    public int countt;//total number of words
//    public String string[]=new String[113003];
    public Hash() {
        init_hash();
    }

    public void init_hash() {


        ht = new Hashtable();
        sec_ht = new Hashtable();

        StringBuffer buffer = new StringBuffer();
        char c[] = new char[100];
        char temp[] = new char[100];

        int len, j, i, k, m, val;
        String s, key_w, s_res;

        word2 = word1 = "=";
        countt = 0;
        total_list = 0;
        sec_total_list = 0;

        for (i = 0; i < 100000; i++) {
            store[i] = new Store();
            store[i].no = 0;
        }

        for (i = 0; i < 10000; i++) {
            sec_store[i] = new Store();
            sec_store[i].no = 0;
        }

        FileInputStream fis;
        InputStream is;
        InputStreamReader isr;
        Reader in;
        File file;
        for (k = 1; k <= 42; k++) {///number of file
            try {
                s = "dictinit/"+ k + ".txt";
               System.out.println(s);
              // file = new File(this.getClass().getResource(s).toURI());
             //   file = new File(s);
               ClassLoader CLDR = this.getClass().getClassLoader();
               InputStream inputStream = CLDR.getResourceAsStream(s);

              //  is = this.getClass().getResourceAsStream("1.txt");
               //  isr = new InputStreamReader(is);
           //     BufferedReader br = new BufferedReader(isr);
         //       fis = new FileInputStream(file);
                isr = new InputStreamReader(inputStream,"UTF8");
                in = new BufferedReader(isr);
                int ch;
                //System.out.printf(locale, "ans=%s", "  12কথা12");
                //System.out.println(">"+buffer.length());

                    while ((ch = in.read()) > -1) {
                        if (ch != 65279) {
                            buffer.append((char) ch);
                        //System.out.println(">>"+ch);
                        }
                        if (ch == 10) {
                            //buffer.trimToSize();
                            word = buffer.toString();///need to remove new line
                            word.trim();
                            len = word.length();
                            word.getChars(0, len, c, 0);
                            countt++;

                            for (i = 0, j = 0; i < len; i++) {
                                if (c[i] >= 0x981 && c[i] <= 0x9DF) {
                                    temp[j++] = c[i];
                                } else if (c[i] == ':') {
                                    temp[j] = '\0';
                                    word = String.copyValueOf(temp, 0, j);
                                    break;
                                }
                            }
                            key_w = double_metaphone(word);
                            //  string[meta_countt++]=key_w;
                            val = Search(key_w);
    //                    word1+=word+" "+key_w+'\n';
                            if (val == -1) {
                                val = total_list;
                                total_list++;
                                ht.put(key_w, new Integer(val));

                                store[val].s = word;
                                store[val].no++;
                            } else {
                                store[val].s += " " + word;
                                store[val].no++;
                            }

                            // **********    deleting one letter *****************   //

                            length = key_w.length();
                            key_w.getChars(0, length, c, 0);

                            for (i = 0; i < length; i++) {
                                m = 0;
                                for (j = 0; j < length; j++) {
                                    if (j != i) {
                                        temp[m++] = c[j];
                                    }
                                }
                                temp[m] = '\0';

                                key_w = String.copyValueOf(temp, 0, m);
    //                         word1+=" "+key_w;
                                val = Search(key_w);

                                if (val == -1) {
                                    val = total_list;
                                    total_list++;
                                    ht.put(key_w, new Integer(val));

                                    store[val].s = word;
                                    store[val].no++;
                                } else {
                                    store[val].s += " " + word;
                                    store[val].no++;
                                }

                            }
    //                   word1+='\n';
                            // word2+=key_w+'\n';
                            //word1+=word+'\n';
                            buffer = new StringBuffer();

                        }

                    }
                in.close();

            } catch (Exception ex) {
               
                JOptionPane.showMessageDialog(null, ex.toString());
            }

        }//k

        System.out.println("Count=" + countt);
//         for(i=0;i<total_list;i++){
////             word1=" ";
////             for(j=0;j<store[i].no;j++)
////                 word1+=store[i].s[j];
////              Main.jTextArea_display.setText(word1);
//             System.out.println(store[i].no);
//         }

//           word1=" ";
//           for(i=0;i<total_list;i++){
//               System.out.println(store[i].no);
//               if(store[i].no==18)
//                    word1=store[i].s[0];
//           }

        //Main.jTextArea_display.setText(word1);


        System.out.println("File reading complete"+" (bdinit/*.txt)");
        //Preloader.frmabout.statusUpdate("bdinit/*.txt");


    }

    public String Soundex(String w) {

        int len, i, j;
        char c[] = new char[30];
        char ct[] = new char[30];
        len = w.length();
        w.getChars(0, len, c, 0);

        for (i = 0, j = 0; i < len; i++) {
            if (c[i] == 0x985 || c[i] == 0x993 || c[i] == 0x9CB)//c[i]==0x9DF ||
            {
                ct[j++] = 0x985;
            } else if (c[i] == 0x986 || c[i] == 0x9BE)//|| c[i]==0x9BE  aa
            {
                ct[j++] = 0x986;
            } else if (c[i] == 0x99E)///nio 
            {
                ct[j++] = 0x99E;
            } else if (c[i] == 0x987 || c[i] == 0x988 || c[i] == 0x9BF || c[i] == 0x9C0)//|| c[i]==0x9BF || c[i]==0x9C0
            {
                ct[j++] = 0x987;
            } else if (c[i] == 0x989 || c[i] == 0x98A || c[i] == 0x9C1 || c[i] == 0x9C2)//|| c[i]==0x9C1 || c[i]==0x9C2
            {
                ct[j++] = 0x989;
            } else if (c[i] == 0x98F || c[i] == 0x9C7)//|| c[i]==0x9C7 a
            {
                ct[j++] = 0x98F;
            } else if (c[i] == 0x990 || c[i] == 0x9C8)//|| c[i]==0x9C8 oi
            {
                ct[j++] = 0x990;
            } else if (c[i] == 0x994 || c[i] == 0x9CC)//|| c[i]==0x9CC ou
            {
                ct[j++] = 0x994;
            } else if (c[i] == 0x98B || c[i] == 0x9C3)// ri & ri kar
            {
                ct[j++] = 0x98B;
            } else if (c[i] == 0x995 || c[i] == 0x996)///k & kh
            {
                ct[j++] = 0x995;
            } else if (c[i] == 0x997 || c[i] == 0x998)//ga
            {
                ct[j++] = 0x997;
            } else if (c[i] == 0x999 || c[i] == 0x982)///umo
            {
                ct[j++] = 0x999;
            } else if (c[i] == 0x99A || c[i] == 0x99B)//cha
            {
                ct[j++] = 0x99A;
            } else if (c[i] == 0x9AF || c[i] == 0x99C || c[i] == 0x99D)// 3 ja
            {
                ct[j++] = 0x99C;
            } else if (c[i] == 0x99F || c[i] == 0x9A0)// t
            {
                ct[j++] = 0x99F;
            } else if (c[i] == 0x9A1 || c[i] == 0x9A2)//da
            {
                ct[j++] = 0x9A1;
            } else if (c[i] == 0x9B0 || c[i] == 0x9DC || c[i] == 0x9DD)//3 ra
            {
                ct[j++] = 0x9B0;
            } else if (c[i] == 0x9A8 || c[i] == 0x9A3)// n
            {
                ct[j++] = 0x9A8;
            } else if (c[i] == 0x9A4 || c[i] == 0x9A5 || c[i] == 0x9CE) {
                ct[j++] = 0x9A4;
            } else if (c[i] == 0x9A6 || c[i] == 0x9A7)//d
            {
                ct[j++] = 0x9A6;
            } else if (c[i] == 0x9AA || c[i] == 0x9AB)//p
            {
                ct[j++] = 0x9AA;
            } else if (c[i] == 0x9AC || c[i] == 0x9AD)//b
            {
                ct[j++] = 0x9AC;
            } else if (c[i] == 0x9AE)//m
            {
                ct[j++] = 0x9AE;
            } else if (c[i] == 0x9B2)//l
            {
                ct[j++] = 0x9B2;
            } else if (c[i] == 0x9B6 || c[i] == 0x9B7 || c[i] == 0x9B8)//3 s
            {
                ct[j++] = 0x9B8;
            } else if (c[i] == 0x9B9)//h
            {
                ct[j++] = 0x9B9;
            } else if (c[i] == 0x9DF) {
                ct[j++] = 0x9DF;
            }
//             else if(c[i]==0x9CD)
//                ct[j++]=0x9CD;

        }
        w = String.copyValueOf(ct, 0, j);
        return w;
    }

    public String double_metaphone(String w) {

        int len, i, j;
        char c[] = new char[30];
        char ct[] = new char[30];
        len = w.length();
        w.getChars(0, len, c, 0);

        for (i = 0, j = 0; i < len; i++) {
            if (c[i] == 0x985 || c[i] == 0x993)//c[i]==0x9DF ||             // অ, ও >> অ
            {
                ct[j++] = 0x985;
            } else if (c[i] == 0x99E || c[i] == 0x986)//|| c[i]==0x9BE      // ঞ, আ >> আ
            {
                ct[j++] = 0x986;
            } else if (c[i] == 0x987 || c[i] == 0x988)//|| c[i]==0x9BF || c[i]==0x9C0   // ই, ঈ >> ই
            {
                ct[j++] = 0x987;
            } else if (c[i] == 0x989 || c[i] == 0x98A)//|| c[i]==0x9C1 || c[i]==0x9C2  // উ, ঊ >> উ
            {
                ct[j++] = 0x989;
            } else if (c[i] == 0x98F)//|| c[i]==0x9C7   // এ
            {
                ct[j++] = 0x98F;
            } else if (c[i] == 0x990)//|| c[i]==0x9C8  // ঐ
            {
                ct[j++] = 0x990;
            } else if (c[i] == 0x994)//|| c[i]==0x9CC  // ঔ
            {
                ct[j++] = 0x994;
            } else if (c[i] == 0x995 || c[i] == 0x996) {    // ক, খ >>  ক
                ct[j++] = 0x995;
            } else if (c[i] == 0x997 || c[i] == 0x998) {    // গ, ঘ >> গ
                ct[j++] = 0x997;
            } else if (c[i] == 0x999 || c[i] == 0x982) {    // ঙ, ং  >> ঙ
                ct[j++] = 0x999;
            } else if (c[i] == 0x99A || c[i] == 0x99B) {    // চ, ছ >> চ
                ct[j++] = 0x99A;
            } else if (c[i] == 0x9AF || c[i] == 0x99C || c[i] == 0x99D) {   // য, জ, ঝ >> জ
                ct[j++] = 0x99C;
            } else if (c[i] == 0x99F || c[i] == 0x9A0 || c[i] == 0x9A4 || c[i] == 0x9A5) {  // ট, ঠ, ত, থ >> ট
                ct[j++] = 0x99F;
            } else if (c[i] == 0x9A1 || c[i] == 0x9A2 || c[i] == 0x9A6 || c[i] == 0x9A7) {  // ড, ঢ, দ, ধ >> ড
                ct[j++] = 0x9A1;
            } else if (c[i] == 0x9B0 || c[i] == 0x9DC || c[i] == 0x9DD) {  // র, ড়, ঢ় >> র
                ct[j++] = 0x9B0;
            } else if (c[i] == 0x9A8 || c[i] == 0x9A3) {    // ন, ণ >> ন
                ct[j++] = 0x9A8;
            } //  else if(c[i]==0x9A4 || c[i]==0x9A5)
            //    ct[j++]=0x9A4;
            // else if(c[i]==0x9A6 || c[i]==0x9A7)
            //   ct[j++]=0x9A6;
            else if (c[i] == 0x9AA || c[i] == 0x9AB) {      // প ,ফ >> প
                ct[j++] = 0x9AA;
            } else if (c[i] == 0x9AC || c[i] == 0x9AD) {    // ব, ভ >> ব
                ct[j++] = 0x9AC;
            } else if (c[i] == 0x9AE) {                     // ম
                ct[j++] = 0x9AE;
            } else if (c[i] == 0x9B2) {                     // ল
                ct[j++] = 0x9B2;
            } else if (c[i] == 0x9B6 || c[i] == 0x9B7 || c[i] == 0x9B8) {   // শ, ষ, স >> স
                ct[j++] = 0x9B8;
            } else if (c[i] == 0x9B9) {                     // হ
                ct[j++] = 0x9B9;
            }


        }
        w = String.copyValueOf(ct, 0, j);
        return w;
    }

    public int Search(String key) {
        int val = -1;
        boolean blnExists = ht.containsKey(key);
        if (blnExists) {
            val = (Integer) ht.get(key);
        }
        return val;
    }

    public int Sec_Search(String key) {
        int val = -1;
        boolean blnExists = sec_ht.containsKey(key);
        if (blnExists) {
            return 1;
        }
        return val;
    }

    public void sort(int n) {

        int i, j, k, temp;
        String stemp;

        for (i = 0; i < n; i++) {

            for (j = 0; j < n - 1; j++) {
                if (rank_val[j] > rank_val[j + 1]) {
                    temp = rank_val[j];
                    rank_val[j] = rank_val[j + 1];
                    rank_val[j + 1] = temp;

                    temp = sx_val[j];
                    sx_val[j] = sx_val[j + 1];
                    sx_val[j + 1] = temp;

                    temp = org_val[j];
                    org_val[j] = org_val[j + 1];
                    org_val[j + 1] = temp;

                    temp = pre_val[j];
                    pre_val[j] = pre_val[j + 1];
                    pre_val[j + 1] = temp;

                    stemp = rank_str[j];
                    rank_str[j] = rank_str[j + 1];
                    rank_str[j + 1] = stemp;
                }
            }//j
        }

    }

    public int PrefixSuffix(String s1, String mw) {
        int l1, l2, d, ind;
        ind = s1.indexOf(mw);

        l1 = s1.length();
        l2 = mw.length();

        if (ind == 0) {

            return l1 - l2;
        } else if (ind > 0) {///prefix


            if (ind + l2 == l1) {
                return l1 - l2;
            } else {
                return 6;
            }
        }

        return 6;
    }

    public void distance(String mw) {
        int value, i, j, k, d, len, m;
        String key, s1, ts, soundex_mw, soundex_sw;
        char c[] = new char[100];
        char temp[] = new char[100];
        sec_ht.clear();


        key = double_metaphone(mw);
        value = Search(key);
        j = 0;
//         word1=" "; 
//        word1+=key+":";
        if(value==-1){
           totaltoken=0; 
           return; 
        }
        StringTokenizer st = new StringTokenizer(store[value].s);
        while (st.hasMoreTokens()) {
            // System.out.println(st.nextToken());
            //   System.out.println(">>>>>>>>>>>>111");
            token[j++] = st.nextToken();
            sec_ht.put(token[j - 1], "");
//           word1+=" "+token[j-1];   
        }
        word2 = "Given word: " + mw + " Meta form: " + key + '\n';

//        word1+='\n';
        len = key.length();
        key.getChars(0, len, c, 0);

        for (i = 0; i < len; i++) {   ///deleting one charecter from key
            k = 0;
            for (m = 0; m < len; m++) {
                if (i != m) {
                    temp[k++] = c[m];
                }
            }
            temp[k] = '\0';

            key = String.copyValueOf(temp, 0, k);
            value = Search(key);
//            word1+=key+":"; 
            st = new StringTokenizer(store[value].s);
            while (st.hasMoreTokens()) {
                ts = st.nextToken();
                value = Sec_Search(ts);
                if (value == -1) {
                    sec_ht.put(ts, "");
                    token[j++] = ts;
                    word1 += " " + token[j - 1];
                }



            }
//            word1+='\n'; 
        }
        soundex_mw = Soundex(mw);

        for (i = 0; i < j; i++) {
            s1 = token[i];
            soundex_sw = Soundex(s1);

            org_val[i] = dist.LD(mw, s1);
            sx_val[i] = dist.LD(soundex_mw, soundex_sw);
            pre_val[i] = PrefixSuffix(s1, mw);

//            if(sx_val[i]<=pre_val[i] && sx_val[i]<=org_val[i] && org_val[i]<3)
//                d=sx_val[i];
//            else if(pre_val[i]<=org_val[i] && pre_val[i]!=6 && org_val[i]<3)
//                d=pre_val[i];
//            else
//                d=org_val[i];


            if (org_val[i] <= pre_val[i] && org_val[i] <= sx_val[i]) {
                d = org_val[i];
            } else if (pre_val[i] <= sx_val[i] && pre_val[i] != 6 && org_val[i] < 3) {
                d = pre_val[i];
            } else if (org_val[i] < 3) {
                d = sx_val[i];
            } else {
                d = org_val[i];
            }



//            d=sx_val[i]+pre_val[i]+org_val[i];
            rank_str[i] = s1;
            rank_val[i] = d;
            System.out.println("> " + rank_str[i] + " Sum=" + rank_val[i] + " sx=" + sx_val[i] + " pre=" + pre_val[i] + " org=" + org_val[i] + "\n");
        }

        // java.util.Arrays.sort(rank);
        sort(j);


//        if(store[value].no<6)
//        for(i=0;i<store[value].no;i++){
//            word1+=rank_str[i]+" "+rank_val[i]+'\n';
//            System.out.println(">> "+rank_val[i]);
//        }
//        
//        else{
        for (i = 0; i < j; i++) {
            if (rank_val[i] > 2) {
                break;
            }
            word2 += rank_str[i] + " Sum=" + rank_val[i] + " sx=" + sx_val[i] + " pre=" + pre_val[i] + " org=" + org_val[i] + '\n';
            System.out.println(">> " + rank_str[i] + " Sum=" + rank_val[i] + " sx=" + sx_val[i] + " pre=" + pre_val[i] + " org=" + org_val[i] + "\n");
        }
        totaltoken = j;
//        }
    //Main.jTextArea_display.setText(word2);
    }
    
    
    
    
    
    public int isInDict(String mw) {
        int value, i, j, k, d, len, m;
        String key, s1, ts, soundex_mw, soundex_sw;
        char c[] = new char[100];
        char temp[] = new char[100];
        sec_ht.clear();


        key = double_metaphone(mw);
        value = Search(key);
        j = 0;
//         word1=" "; 
//        word1+=key+":";
        if(value==-1){
           totaltoken=0; 
           return -1; 
        }
        StringTokenizer st = new StringTokenizer(store[value].s);
        while (st.hasMoreTokens()) {
            // System.out.println(st.nextToken());
            //   System.out.println(">>>>>>>>>>>>111");
            token[j++] = st.nextToken();
            sec_ht.put(token[j - 1], "");
            if(token[j-1].equals(mw)){
               return 1; 
            }
                

//           word1+=" "+token[j-1];   
        }
        word2 = "Given word: " + mw + " Meta form: " + key + '\n';
        return -1;
    }      
    
}

