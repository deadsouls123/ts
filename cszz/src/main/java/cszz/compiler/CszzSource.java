package cszz.compiler;

/**
 *
 *  
 */
public class CszzSource {

    //要求实现System.IO.print，目前System.out.print已经支持，因此，转换即可支持
    private static final String CSZZ_SYSTEM_PRINT = "System.IO.print";
    private static final String JAVA_SYSTEM_PRINT = "System.out.print";

    //要求实现System.Process.exit()，目前System.exit(0)已经支持，因此，转换即可支持
    private static final String CSZZ_SYSTEM_EXIT = "System.Process.exit()";
    private static final String JAVA_SYSTEM_EXIT = "System.exit(0)";

    //要求实现字符串操作函数：String.subString()，目前小写substring已经支持，因此，将字符串替换为小写后即可支持
    private static final String CSZZ_SUB_STRING = "subString";
    private static final String JAVA_SUB_STRING = "substring";

    private String className;
    
    private String text;
    
    private String fileName;

    public CszzSource(String className, String text,String fileName) {
        this.className = className;
        this.text = replaceKeySomeMethod(text);
        this.fileName = fileName;
    }

    private String replaceKeySomeMethod(String origin) {
        String operation = origin;
        if (operation != null && !operation.equals("")) {
            if (operation.contains(CSZZ_SYSTEM_PRINT)) {
                operation = operation.replace(CSZZ_SYSTEM_PRINT, JAVA_SYSTEM_PRINT);
            }
            if (operation.contains(CSZZ_SYSTEM_EXIT)) {
                operation = operation.replace(CSZZ_SYSTEM_EXIT, JAVA_SYSTEM_EXIT);
            }
            if (operation.contains(CSZZ_SUB_STRING)) {
                operation = operation.replace(CSZZ_SUB_STRING, JAVA_SUB_STRING);
            }
        }
        return operation;
    }

    public String getClassName() {
        return className;
    }

    public String getText() {
        return text;
    }

    public CszzSource(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

}
