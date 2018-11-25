
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Normalization {
   int szs = 10000;
    int szc = 10000;
    String text;
    public char charA[] = new char[szc];
    public char temp[] = new char[szc];
    public String token[] = new String[szs];
    public String Ftoken[] = new String[szs];
    public String lookup_table[] = new String[szs];
    public String aux_table[] = new String[szs];
    public String bangla_no[] = new String[szs];
    public int flag[] = new int[szs];
    public int Fflag[] = new int[szs];
    public int total_token, Ftotal, st;
    public static int n;

    public Normalization() {
        temp[0] = 0x9A1;
        temp[1] = 0x995;
        temp[2] = 0x9CD;
        temp[3] = 0x99F;
        temp[4] = 0x9B0;
        temp[5] = '\0';
        lookup_table[0] = String.copyValueOf(temp, 0, 5);
        temp[0] = 0x9A1;
        temp[1] = 0x9BE;
        temp[2] = 0x995;
        temp[3] = 0x9CD;
        temp[4] = 0x9A4;
        temp[5] = 0x9BE;
        temp[6] = 0x9B0;
        temp[7] = '\0';
        lookup_table[1] = String.copyValueOf(temp, 0, 7);
        temp[0] = 0x9A1;
        temp[1] = 0x983;
        temp[2] = '\0';
        aux_table[0] = String.copyValueOf(temp, 0, 2);
        temp[0] = 0x9A1;
        temp[1] = 0x9BE;
        temp[2] = 0x983;
        temp[3] = '\0';
        aux_table[1] = String.copyValueOf(temp, 0, 3);
        initial();
    }

    public void initial() {
        int i;
        StringBuffer buffer = new StringBuffer();

        try {
            int ch;
            ClassLoader CLDR = this.getClass().getClassLoader();
            InputStream inputStream = CLDR.getResourceAsStream("data_bn.txt");
            InputStreamReader isr = new InputStreamReader(inputStream, "UTF8");
            Reader in = new BufferedReader(isr);
            i = 0;
            while ((ch = in.read()) > -1) {

                if (ch != 65279) {
                    if (ch == 32) {
                        bangla_no[i] = buffer.toString();
                        i++;

                        buffer = new StringBuffer();
                    } else {
                        buffer.append((char) ch);
                    }
                }


            }
            in.close();
            System.out.println("File reading complete(data_bn.txt)");
            Preloader.frmabout.statusUpdate("data_bn.txt");

        } catch (IOException e) {
            e.printStackTrace();

        }

    }

    public String Normalize(String temptext) {
        int i;
        Tokenization(temptext);

        n = total_token;
        String s;
        s = " " + '\n';
        for (i = 0; i < total_token; i++) {
            s += token[i];

            s += '\n';
        }
        System.out.println("total token: " + total_token);
        s += " " + '\n';

        Ftotal = 0;

        for (i = 0; i < total_token; i++) {
            if (flag[i] == 2) {
                Ngram(i);
            } else if (flag[i] == 6) {
                Ngram(i);
                st = 0;
            } else if (flag[i] == 3 || flag[i] == 4 || flag[i] == 7 || flag[i] == 9) {
                Convert_Number(i);
                st = 0;
            } else {
                Ftoken[Ftotal] = token[i];
                Ftotal++;
            }
        }
        s += " " + '\n';
        for (i = 0; i < Ftotal; i++) {
            s += Ftoken[i];

            s += '\n';

        }
        return s;

    }

    public void Ngram(int pos) {
        //  System.out.println("Ngram#19");
        int i, j, len;
        char c[] = new char[szc];

        if (flag[pos] == 2) {
            for (i = 1; i <= 2 && pos + i < total_token; i++) {
                len = token[pos + i].length();
                st = 0;
                token[pos + i].getChars(0, len, c, 0);
                if (c[0] == 0x9B8 && c[1] == 0x9BE && c[2] == 0x9B2) {
                    st = 1;
                    System.out.println(st);
                    Convert_Number(pos);
                    return;
                }//if
            }
            Convert_Number(pos);///call for normal case
            return;
        }//if
        else if (flag[pos] == 6) {
            len = token[pos + 2].length();
            token[pos + 2].getChars(0, len, c, 0);
            if (c[0] == 0x9AE && c[1] == 0x9BF && c[2] == 0x9A8 && c[3] == 0x9BF && c[4] == 0x99F) {///minute
                Convert_Number(pos);
                temp[0] = 0x99F;
                temp[1] = 0x9BE;
                temp[2] = '\0';
                Ftoken[Ftotal] = String.copyValueOf(temp, 0, 2);
                Ftotal++;
                return;
            }//if
            else {
                Convert_Number(pos);
                temp[0] = 0x985;
                temp[1] = 0x9A8;
                temp[2] = 0x9C1;
                temp[3] = 0x9AA;
                temp[4] = 0x9BE;
                temp[5] = 0x9A4;
                temp[6] = '\0';
                Ftoken[Ftotal] = String.copyValueOf(temp, 0, 6);
                Ftotal++;
                return;
            }
        }
    }

    public void Tokenization(String temptext) {
        // System.out.println("Tokenization#19");
        charA = new char[szc];
        flag = new int[szs];
        Fflag = new int[szs];
        char temp1[] = new char[szc];
        char temp2[] = new char[szc];
        char t[] = new char[szc];
        char c1[] = new char[szc];
        char c2[] = new char[szc];

        String s1, s2, s3;
        int len1, len2, len3;
        text = temptext;
        text.trim();
        int len = text.length();
        text.getChars(0, len, charA, 0); //undef
        int i, j, k, tag;
        total_token = 0;
        //start tokenization

        for (i = 0; i < len; i++) {
            tag = 0;
            if (charA[i] >= 0x981 && charA[i] <= 0x9DF) {///search for charecter only
                j = 0;
                while (charA[i] >= 0x981 && charA[i] <= 0x9DF) {
                    temp[j] = charA[i];
                    i++;
                    j++;
                }
                temp[j] = '\0';
                for (j = 0; temp[j] != '\0'; j++);
                token[total_token] = String.copyValueOf(temp, 0, j);
                //token[total_token] = new String(temp);
                flag[total_token] = 1;

                if (temp[j - 1] == 0x983) {
                    help_lookuptable();////call for look up table
                }
                total_token++;
            }///end of search for charecter only
            else if (charA[i] >= 0x9E6 && charA[i] <= 0x9EF) {////search which start with digit
                j = 0;
                int a;

                while (charA[i] >= 0x9E6 && charA[i] <= 0x9EF) {
                    temp[j] = charA[i];

                    i++;
                    j++;
                }
                temp[j] = '\0';
                j = 0;

                if (charA[i] == '/' || charA[i] == '-') {
                    c1[0] = charA[i];
                    i++;
                    while (charA[i] >= 0x9E6 && charA[i] <= 0x9EF) {
                        temp1[j] = charA[i];
                        i++;
                        j++;
                    }
                    temp1[j] = '\0';
                    j = 0;

                    if (charA[i] == '/' || charA[i] == '-') {
                        c2[0] = charA[i];
                        i++;
                        while (charA[i] >= 0x9E6 && charA[i] <= 0x9EF) {
                            temp2[j] = charA[i];
                            i++;
                            j++;
                        }
                        temp2[j] = '\0';

                        for (j = 0; temp[j] != '\0'; j++);
                        s1 = String.copyValueOf(temp, 0, j);
                        for (j = 0; temp1[j] != '\0'; j++);
                        s2 = String.copyValueOf(temp1, 0, j);
                        for (j = 0; temp2[j] != '\0'; j++);
                        s3 = String.copyValueOf(temp2, 0, j);

                        len1 = s1.length();
                        len2 = s2.length();
                        len3 = s3.length();

                        if (len1 > 2 || len2 > 2 || len3 > 4) { //if true it will telephone number

                            for (j = 0; temp[j] != '\0'; j++) {
                                t[0] = temp[j];
                                t[1] = '\0';
                                // token[total_token]=new String(t);
                                token[total_token] = String.copyValueOf(t, 0, 1);
                                flag[total_token] = 3;
                                total_token++;
                            }

                            for (j = 0; temp1[j] != '\0'; j++) {
                                t[0] = temp1[j];
                                t[1] = '\0';
                                //token[total_token]=new String(t);
                                token[total_token] = String.copyValueOf(t, 0, 1);
                                flag[total_token] = 3;
                                total_token++;
                            }

                            for (j = 0; temp2[j] != '\0'; j++) {
                                t[0] = temp2[j];
                                t[1] = '\0';
                                // token[total_token]=new String(t);
                                token[total_token] = String.copyValueOf(t, 0, 1);
                                flag[total_token] = 3;
                                total_token++;
                            }
                        } else {///date
                            // token[total_token]=new String(temp);
                            for (j = 0; temp[j] != '\0'; j++);
                            token[total_token] = String.copyValueOf(temp, 0, j);
                            flag[total_token] = 4;
                            total_token++;

                            c1[1] = '\0';
                            token[total_token] = String.copyValueOf(c1, 0, 1);
                            flag[total_token] = 8;
                            total_token++;
                            // token[total_token]=new String(temp1);
                            for (j = 0; temp1[j] != '\0'; j++);
                            token[total_token] = String.copyValueOf(temp1, 0, j);
                            flag[total_token] = 4;
                            total_token++;

                            c2[1] = '\0';
                            token[total_token] = String.copyValueOf(c2, 0, 1);
                            flag[total_token] = 8;
                            total_token++;
                            //token[total_token]=new String(temp2);
                            for (j = 0; temp2[j] != '\0'; j++);
                            token[total_token] = String.copyValueOf(temp2, 0, j);
                            flag[total_token] = 4;
                            total_token++;
                        }///end of date
                        tag = 1;
                    }//inner if for / and -

                    if (tag == 0) {  ///number
                        // token[total_token]=new String(temp);
                        for (j = 0; temp[j] != '\0'; j++);
                        token[total_token] = String.copyValueOf(temp, 0, j);
                        flag[total_token] = 2;
                        total_token++;

                        temp[0] = 0x9AC;
                        temp[1] = 0x9BE;
                        temp[2] = 0x987;
                        temp[3] = '\0';
                        token[total_token] = String.copyValueOf(temp, 0, 3);
                        flag[total_token] = 1;
                        total_token++;
                        //token[total_token]=new String(temp1);
                        for (j = 0; temp1[j] != '\0'; j++);
                        token[total_token] = String.copyValueOf(temp1, 0, j);
                        flag[total_token] = 2;
                        total_token++;
                        tag = 1;
                    }
                }//outer if for / and -
                else if (charA[i] == '%') {
                    // token[total_token]=new String(temp);
                    for (j = 0; temp[j] != '\0'; j++);
                    token[total_token] = String.copyValueOf(temp, 0, j);
                    flag[total_token] = 2;
                    total_token++;

                    temp[0] = 0x9AA;
                    temp[1] = 0x9BE;
                    temp[2] = 0x9B0;
                    temp[3] = 0x9CD;
                    temp[4] = 0x9B8;
                    temp[5] = 0x9C7;
                    temp[6] = 0x9A8;
                    temp[7] = 0x9CD;
                    temp[8] = 0x99F;
                    temp[9] = '\0';

                    token[total_token] = String.copyValueOf(temp, 0, 9);
                    flag[total_token] = 5;
                    total_token++;
                    tag = 1;
                } else if (charA[i] == ':') {///colon need to compare for ratio or time

                    j = 0;
                    i++;
                    while (charA[i] >= 0x9E6 && charA[i] <= 0x9EF) {
                        temp1[j] = charA[i];
                        i++;
                        j++;
                    }
                    temp1[j] = '\0';

                    //token[total_token]=new String(temp);
                    for (j = 0; temp[j] != '\0'; j++);
                    token[total_token] = String.copyValueOf(temp, 0, j);
                    flag[total_token] = 6;
                    total_token++;

                    //token[total_token]=new String(temp1);
                    for (j = 0; temp1[j] != '\0'; j++);
                    token[total_token] = String.copyValueOf(temp1, 0, j);
                    flag[total_token] = 9;
                    total_token++;

                    tag = 1;
                }///ratio time
                else if (temp[0] == 0x9E6) {//start with zero
                    for (j = 0; temp[j] != '\0'; j++) {
                        t[0] = temp[j];
                        t[1] = '\0';
                        // token[total_token]=new String(t);
                        token[total_token] = String.copyValueOf(t, 0, 1);
                        flag[total_token] = 3;
                        total_token++;
                    }
                } else { ///number
                    //  token[total_token]=new String(temp);
                    int l;
                    if (total_token > 0 && flag[total_token - 1] == 2) {/// space is found betw 2 number
                        l = token[total_token - 1].length();
                        token[total_token - 1].getChars(0, l, temp1, 0);
                        total_token--;
                        for (j = 0; j < l; j++) {
                            t[0] = temp1[j];
                            t[1] = '\0';
                            // token[total_token]=new String(t);
                            token[total_token] = String.copyValueOf(t, 0, 1);
                            flag[total_token] = 3;
                            total_token++;
                        }//for j

                        for (j = 0; temp[j] != '\0'; j++);
                        l = j;
                        for (j = 0; j < l; j++) {
                            t[0] = temp[j];
                            t[1] = '\0';
                            // token[total_token]=new String(t);
                            token[total_token] = String.copyValueOf(t, 0, 1);
                            flag[total_token] = 3;
                            total_token++;
                        }//for j

                    }//if 2
                    else if (total_token > 0 && flag[total_token - 1] == 3) {
                        l = token[total_token - 1].length();
                        token[total_token - 1].getChars(0, l, temp1, 0);
                        total_token--;
                        for (j = 0; j < l; j++) {
                            t[0] = temp1[j];
                            t[1] = '\0';
                            // token[total_token]=new String(t);
                            token[total_token] = String.copyValueOf(t, 0, 1);
                            flag[total_token] = 3;
                            total_token++;
                        }//for j

                        for (j = 0; temp[j] != '\0'; j++);
                        l = j;
                        for (j = 0; j < l; j++) {
                            t[0] = temp[j];
                            t[1] = '\0';
                            // token[total_token]=new String(t);
                            token[total_token] = String.copyValueOf(t, 0, 1);
                            flag[total_token] = 3;
                            total_token++;
                        }//for j
                    } else {
                        for (j = 0; temp[j] != '\0'; j++);
                        token[total_token] = String.copyValueOf(temp, 0, j);

                        flag[total_token] = 2;
                        total_token++;
                    }
                    //  System.out.println(j);
                    // System.out.println(token[total_token-1].length());
                }
                Convert_Number(total_token - 1);
                //System.out.println(token[total_token-1].length());
            }//start with digit
            else if (charA[i] == '+') {///start with +
                i++;
                j = 0;
                while (charA[i] >= 0x9E6 && charA[i] <= 0x9EF) {
                    temp[j] = charA[i];
                    i++;
                    j++;
                }
                temp[j] = '\0';
                for (j = 0; temp[j] != '\0'; j++) {
                    t[0] = temp[j];
                    t[1] = '\0';
                    //token[total_token]=new String(t);
                    token[total_token] = String.copyValueOf(t, 0, 1);
                    flag[total_token] = 7;
                    total_token++;
                }
            }///end of +
            if (charA[i] == 0x964) {///for dari
                temp[0] = charA[i];
                temp[1] = '\0';
                token[total_token] = String.copyValueOf(temp, 0, 1);
                flag[total_token] = 8;
                total_token++;

            }
            if (charA[i] == ',') {///for ,
                temp[0] = charA[i];
                temp[1] = '\0';
                token[total_token] = String.copyValueOf(temp, 0, 1);
                flag[total_token] = 8;
                total_token++;

            }

            if (charA[i] == '.') {///for .
                temp[0] = charA[i];
                temp[1] = '\0';
                token[total_token] = String.copyValueOf(temp, 0, 1);
                flag[total_token] = 10;
                total_token++;

            }
        }
    }

    public void help_lookuptable() {
        //  System.out.println("help_lookuptable#");
        int i;
        for (i = 0; i < 2; i++) {
            if (token[total_token].equals(aux_table[i])) {
                token[total_token] = lookup_table[i];
                break;
            }
        }
    }

    public void Convert_Number(int pos) {
        // System.out.println("Convert_Number#");
        int len, i, j, div, check, n = 0;
        char c[] = new char[szc];

        len = token[pos].length();
        if (len == 0) {
            return;
        }
        token[pos].getChars(0, len, c, 0);
        for (i = 0; i < len; i++) {
            n += (c[i] - 2534) * Math.pow(10, (len - 1 - i));
        }
        check = 0;
        // System.out.println("asdada "+n);
        if (n > 99999999) {
            check = 1;

            div = n / 10000000;

            convert_to_BanglaNumber(div, check, pos);

            //printf(" kuti");
            Ftoken[Ftotal] = bangla_no[104];
            Fflag[Ftotal] = 11;
            Ftotal++;
            n = n - (div * 10000000);

            convert_to_BanglaNumber(n, check, pos);
        } else {
            convert_to_BanglaNumber(n, check, pos);
        }

        //System.out.println(n);
    }

    public void convert_to_BanglaNumber(int N, int i, int pos) {
        System.out.println("convert_to_BanglaNumber#");

        int mod, check = 0;

        if (N > 9999999) {
            mod = N / 10000000;

            if (mod > 0) {
                //printf(" %d kuti",mod);
                Ftoken[Ftotal] = bangla_no[mod];
                Fflag[Ftotal] = 11;
                Ftotal++;
                Ftoken[Ftotal] = bangla_no[103];
                Fflag[Ftotal] = 11;
                Ftotal++;
                check = 1;
            }

            N = N - (mod * 10000000);
        }

        if (N > 99999) {
            mod = N / 100000;

            if (mod > 0) {
                // printf(" %d lakh",mod);
                //  System.out.println(mod);
                Ftoken[Ftotal] = bangla_no[mod];
                Fflag[Ftotal] = 11;
                Ftotal++;
                Ftoken[Ftotal] = bangla_no[102];
                Fflag[Ftotal] = 11;
                Ftotal++;
                check = 1;
            }

            N = N - (mod * 100000);
        }

        if (N > 1099 && N < 2000 && st == 1) {
            mod = N / 100;

            if (mod > 0) {		//
                //printf(" %d hajar",mod);
                // System.out.println(mod);
                Ftoken[Ftotal] = bangla_no[mod];
                Fflag[Ftotal] = 11;
                Ftotal++;
                Ftoken[Ftotal] = bangla_no[104];
                Fflag[Ftotal] = 11;
                Ftotal++;
                check = 1;
            }


            N = N - (mod * 100);
        }



        if (N > 999) {
            mod = N / 1000;

            if (mod > 0) {		//
                //printf(" %d hajar",mod);
                // System.out.println(mod);
                Ftoken[Ftotal] = bangla_no[mod];
                Fflag[Ftotal] = 11;
                Ftotal++;
                Ftoken[Ftotal] = bangla_no[101];
                Fflag[Ftotal] = 11;
                Ftotal++;
                check = 1;
            }


            N = N - (mod * 1000);
        }
        if (N > 99) {
            mod = N / 100;

            if (mod > 0) {
                //printf(" %d shata",mod);
                Ftoken[Ftotal] = bangla_no[mod];
                Fflag[Ftotal] = 11;//11 for dic
                Ftotal++;
                Ftoken[Ftotal] = bangla_no[100];
                Fflag[Ftotal] = 11;
                Ftotal++;
                check = 1;
            }

            N = N - (mod * 100);
        }


        if (N > 0) {// printf(" %d",N);
            Ftoken[Ftotal] = bangla_no[N];
            if (flag[pos] == 3 || flag[pos] == 7) {
                Fflag[Ftotal] = 3;
            } else {
                Fflag[Ftotal] = 11;
            }
            Ftotal++;
        } else if (N == 0 && check == 0 && i == 0) {
            Ftoken[Ftotal] = bangla_no[0];
            if (flag[pos] == 3 || flag[pos] == 7) {
                Fflag[Ftotal] = 3;///3 for indivi number
            } else {
                Fflag[Ftotal] = 11;
            }
            Ftotal++;
        }
    }
}
