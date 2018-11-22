
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SubFix {

    HashMap<Character, Character> swaps = new HashMap<>();

    public static void main(String[] args) {
        String fileName = "";
        String fix = "";

        //Menu
        if (args.length == 0) {
            System.out.println("Ewww! Add a filename as an argument... or try --help");
        } else if (args.length == 1 && !args[0].equals("--help")) {
            fileName = args[0];
            fix = fileName.replace(".srt", "FIX.srt");
        } else if (args[0].equals("--help")) {
            System.out.println("SubFix is a script made in purpose of fixing encoding problems in subtitles.\n"
                    + "Syntax:\n"
                    + "java SubFix [fileName/path] [fixedFileName/path]  --------> fixses file with \'fileName\' name and saves it to \'fixedFileName\' file\n"
                    + "java SubFix [fileName/path]                       --------> fixses file with \'fileName\' name and saves it to \'fileName\'+FIX file\n"
                    + "Example usages:\n"
                    + "     java SubFix titanik.720.srt\n"
                    + "     java SubFix titanik.720.srt titanikbestmovieever.sub.srt\n"
                    + "Version : 1.0\n"
                    + "Build date :22.11.2018 - 04:19\n"
                    + "Made by: nikola3in1");
        } else if (args.length == 2) {
            fileName = args[0];
            fix = args[1];
        } else {
            System.out.println("To many arguments. Try --help.");
        }

        if (!fileName.isEmpty()) {
            System.out.println(graphic());
            new SubFix(fileName, fix);
        }
    }

    SubFix(String fileName, String newFileName) {
        init();
        File originalSub = new File(fileName);
        File fixedSub = new File(newFileName);

        BufferedReader in = null;
        Writer out = null;
        try {
            in = new BufferedReader(new FileReader(originalSub, StandardCharsets.ISO_8859_1));
            out = new OutputStreamWriter(new FileOutputStream(fixedSub), StandardCharsets.UTF_16);
            String line = "";
            while ((line = in.readLine()) != null) {
                out.append(swap(line) + "\n");
            }

            in.close();
            out.close();
            System.out.println("Fixed to ---> /" + newFileName);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("File " + fileName + " was not found");
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String graphic() {
        return "Welcome to \n"
                + "\n"
                + ".d88888b           dP                 88888888b oo          \n"
                + "88.    \"'          88                 88                    \n"
                + "`Y88888b. dP    dP 88d888b.          a88aaaa    dP dP.  .dP \n"
                + "      `8b 88    88 88'  `88 88888888  88        88  `8bd8'  \n"
                + "d8'   .8P 88.  .88 88.  .88           88        88  .d88b.  \n"
                + " Y88888P  `88888P' 88Y8888'           dP        dP dP'  `dP \n"
                + "                                                       \n"
                + "                                                       v1.0 \n"
                + "                                              by: nikola3in1  \n"
                + "                                                            ";
    }

    private String swap(String line) {
        String fixedLine = new String(line);
//        test
        if (!line.isEmpty()) {
            for (Character c : this.swaps.keySet()) {
                if (fixedLine.contains(c + "")) {
                    fixedLine = fixedLine.replace(c, swaps.get(c));
                }
            }
        }
        return fixedLine;
    }

    private void init() {
        this.swaps.put('', 'š');
        this.swaps.put('è', 'č');
        this.swaps.put('æ', 'ć');
        this.swaps.put('ð', 'đ');
        this.swaps.put('', 'ž');

        this.swaps.put('', 'Š');
        this.swaps.put('È', 'Č');
        this.swaps.put('Æ', 'Ć');
        this.swaps.put('', 'Ž');
    }
}
