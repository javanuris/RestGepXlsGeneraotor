package kazpost.nuris.gep.models;

public class Form103Path {
    private String fromPath;
    private String toPath;
    private String fileNameBefore;
    private String fileNameAfter;

   public Form103Path(){

   }

    public String getFromPath() {
        return fromPath;
    }

    public void setFromPath(String fromPath) {
        this.fromPath = fromPath;
    }

    public String getToPath() {
        return toPath;
    }

    public void setToPath(String toPath) {
        this.toPath = toPath;
    }

    public String getFileNameBefore() {
        return fileNameBefore;
    }

    public void setFileNameBefore(String fileNameBefore) {
        this.fileNameBefore = fileNameBefore;
    }

    public String getFileNameAfter() {
        return fileNameAfter;
    }

    public void setFileNameAfter(String fileNameAfter) {
        this.fileNameAfter = fileNameAfter;
    }
}
