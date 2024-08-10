package logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MdToTex extends ConverterInterface {

    public MdToTex(final String path) {
        this.path = path;
        this.fileName = "markdownToConvert.md";
        this.validateFileEnding(".md");
    }

    @Override
    public void convert() {
        final List<String> allLines = new ArrayList<>();

        final File file = new File(this.path + this.fileName);
        try {
            final Scanner scanner = new Scanner(file, StandardCharsets.UTF_8.name());
            while (scanner.hasNext()) {
                allLines.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            final FileWriter fileWriter = new FileWriter("src/resources/converted.tex");
            fileWriter.write(this.getHeaderForLaTeX());
            fileWriter.write(this.convertFromStringArray(allLines));
            fileWriter.write("\\end{document}\n");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getHeaderForLaTeX() {
        return ""
            + "\\documentclass{article}\n"
            + "\\usepackage{xcolor}\n"
            + "\\usepackage[a4paper, total={6in, 9in}]{geometry}\n"
            + "\\title{Improtraining}\n"
            + "\\author{IMPRO-TECH}\n"
            + "\\begin{document}\n"
            + "\\maketitle\n";
    }

    private String convertFromStringArray(final List<String> arrayList) {
        String result = "";
        boolean isItemizeBlockOpen = false;
        boolean isAnnotationSectionOpen = false;
        for (final String uneditedLine : arrayList) {
            if (uneditedLine == "") {
                continue;
            }
            final String line = this.editLine(uneditedLine);
            if (!(line.startsWith(">")) && isItemizeBlockOpen) {
                isItemizeBlockOpen = false;
                result += "\\end{itemize}\n";
            } else if (!(line.startsWith("-")) && isAnnotationSectionOpen) {
                isAnnotationSectionOpen = false;
                result += "\\end{itemize}\n";
            }

            if (line.startsWith("# ")) {
                result += "\\section{" + line.substring(2) + "}";
            } else if (line.startsWith("## ")) {
                result  += "\\subsection{" + line.substring(3) + "}";
            } else if (line.startsWith("### ")) {
                result  += "\\subsubsection{" + line.substring(4) + "}";
            } else if (line.startsWith(">")) {
                if (!isItemizeBlockOpen) {
                    isItemizeBlockOpen = true;
                    result += "\\begin{itemize}\n";
                }
                result += "\\item[" + this.colorItem(line.substring(2, 4)) + ":] " + line.substring(4);
            } else if (line.startsWith("- ")) {
                if (!isAnnotationSectionOpen) {
                    isAnnotationSectionOpen = true;
                    result += "\\begin{itemize}\n";
                }
                result += "\\item " + line.substring(2);
            } else {
                result += line;
            }
            result += "\n";
        }
        return result;
    }

    private String editLine(final String line) {
        String result = "";
        Character personSuspected = null;
        for (final char character : line.toCharArray()) {
            if (personSuspected != null & character == ' ') {
                result += this.colorItem("" + personSuspected) + " ";
                personSuspected = null;
                continue;
            } else if (personSuspected != null) {
                result += personSuspected;
                personSuspected = null;
            }
            switch (character) {
                case 'Ä':
                    result += "Ae"; // Ä
                    break;
                case 'Ö':
                    result += "Oe"; // Ö
                    break;
                case 'Ü':
                    result += "Ue"; // Ü
                    break;
                case 'ä':
                    result += "ae"; // ä
                    break;
                case 'ö':
                    result += "oe"; // ö
                    break;
                case 'ü':
                    result += "ue"; // ü
                    break;
                case 'A':
                case 'B':
                case 'C':
                case 'D':
                    personSuspected = character;
                    continue;
                default:
                    result += character;
            }
            personSuspected = null;
        }
        return result;
    }

    private String colorItem(final String string) {
        switch (string.charAt(0)) {
            case 'A':
                return "\\textcolor{red}{A}";
            case 'B':
                return "\\textcolor{blue}{B}";
            case 'C':
                return "\\textcolor{green}{C}";
            case 'D':
                return "\\textcolor{purple}{D}";
            default:
                return string;
        }
    }
}
