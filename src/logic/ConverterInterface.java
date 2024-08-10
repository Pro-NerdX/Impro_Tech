package logic;

public abstract class ConverterInterface {
    
    protected String path = null;
    protected String fileName = null;

    protected void validateFileEnding(final String suffix) {
        if (!(this.fileName.endsWith(suffix))) {
            throw new IllegalArgumentException(
                "ConverterInterface: Validation of fileName failed! (was: " + this.fileName + ")"
            );
        }
    }

    public abstract void convert();
}
