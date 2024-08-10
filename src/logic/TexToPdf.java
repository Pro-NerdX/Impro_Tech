package logic;

import com.aspose.pdf.*;

public class TexToPdf extends ConverterInterface {

    public TexToPdf(final String path, final String fileName) {
        this.path = path;
        this.fileName = fileName;
        this.validateFileEnding(".tex");
    }

    @Override
    public void convert() {
        TeXLoadOptions options = new TeXLoadOptions();
        Document document = new Document(
            this.path + this.fileName,
            options
        );
        document.save("src/resources/output.pdf");
        document.close();
    }
}
