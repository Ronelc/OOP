package oop.ex6.main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Method object that has different features.
 */
public class Method {
    private static final String RGX_METHOD_PARAM = "[a-zA-Z1-9_]+\\s=*\\s*[a-zA-Z1-9_]*;*";
    private static final String RGX_METHOD_TYPE = "void";
    Scope methodScope;
    String methodName;
    ArrayList<String> methodParams;

    /**
     * constructor of method Object
     * @param scope the scope of the method
     * @param methodDeclaration the line of the method declaration
     */
    Method(Scope scope, String methodDeclaration){
        methodParams = new ArrayList<>();
        methodScope=scope;
        defineMethodParams(methodDeclaration);
        defineName(methodDeclaration);
    }

    /**
     * define the params of the method
     * @param methodDeclaration method declaration.
     */
    private void defineMethodParams(String methodDeclaration) {
        String checkParam = "";
        int indexStartParams = methodDeclaration.indexOf('(');
        int indexEndParams = methodDeclaration.indexOf(')');
        String[] listParams = methodDeclaration.substring(indexStartParams+1,indexEndParams).split(MethodValidate
                .RGX_COMMA);
        for (String param : listParams){
            Matcher m = Pattern.compile(RGX_METHOD_PARAM).matcher(param);
            while (m.find()){
                checkParam = param.substring(m.start(),m.end());
            }
            String[] typeName = checkParam.split(TextProcessor.SPACE);
            if (!param.equals("")){
                methodParams.add(typeName[0]);
            }


        }
    }

    /**
     * get method params
     * @return method params
     */
    public ArrayList<String> getMethodParams(){
        return methodParams;
    }

    /**
     * defines name of the method
     * @param methodDeclaration method declaration
     */
    private void defineName(String methodDeclaration){

        String method= methodDeclaration.split("\\(")[0];
        methodName = method.split(RGX_METHOD_TYPE)[1].trim();
    }

    /**
     * gets method name
     * @return method name
     */
    public String getMethodName() {
        return methodName;
    }


}
