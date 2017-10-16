package cszz.compiler;
/**
 *
 * 
 */
public class Diagnosis {

    private final CompileContext context;
    
    public enum Kind{
        FATAL(true),ERROR(true),WARNING(false),NOTE(false)
        ;
        boolean isError;
        Kind(boolean isError){
            this.isError = isError;
        }
        public boolean isError(){
            return isError;
        }
    }
    
    private Kind kind;
    
    private OffsetRange offset;
    
    private String description;
    
    private CszzSource source;

    public Diagnosis(CompileContext context,Kind kind, OffsetRange offset, String description, CszzSource cszzSource) {
        this.context = context;
        this.kind = kind;
        this.offset = offset;
        this.description = description;
        this.source = cszzSource;
    }

    public Kind getKind() {
        return kind;
    }

    public OffsetRange getOffset() {
        return offset;
    }

    public String getDescription() {
        return description;
    }

    public CszzSource getSource() {
        return source;
    }

    public CompileContext getContext() {
        return context;
    }

    @Override
    public String toString() {
        String fileName =  source == null ? "UNKNOWN SOURCE" : source.getFileName();

        if(description != null && !description.equalsIgnoreCase("")){
            Exception e = new Exception(description);
            e.printStackTrace();	
        }

        return String.format("%s:%s:%s:%s", kind,fileName,offset.startLine,description);
    }
    
    
    
    

}
