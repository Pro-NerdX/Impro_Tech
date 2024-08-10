package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

import logic.Language;
import logic.MdToTex;
import logic.SampleGenerator;
import logic.TexToPdf;

public class MainFrame extends JFrame implements ActionListener {
    
    public final JButton generateOutputButton;

    public MainFrame() {
        this.generateOutputButton = new JButton();
        this.generateOutputButton.setBounds(150, 150, 200, 100);
        this.generateOutputButton.setText("Impro-Plan");
        this.generateOutputButton.addActionListener(this);
        this.add(this.generateOutputButton);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(500, 500);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.generateOutputButton) {
            // generate output
            String path = "src/resources/";
            try {
                File file = new File(path + "converted.tex");
                if (!file.exists()) {
                    file.createNewFile();
                }
                file = new File(path + "markdownToConvert.md");
                if (!file.exists()) {
                    file.createNewFile();
                }
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            final SampleGenerator sampleGenerator = new SampleGenerator(path, Language.GERMAN);
            sampleGenerator.convert();
            final MdToTex markdownConverter = new MdToTex(path);
            markdownConverter.convert();
            final TexToPdf LaTeXconverter = new TexToPdf(path, "converted.tex");
            LaTeXconverter.convert();

            // open output.pdf
            OpenPDF.openSesame();
        }
    }
}
