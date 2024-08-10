package gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

public class OpenPDF {
    
    public static void openSesame() {
        if (Desktop.isDesktopSupported()) {
            try {
                final File pdf = new File("src/resources/output.pdf");
                Desktop.getDesktop().open(pdf);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
