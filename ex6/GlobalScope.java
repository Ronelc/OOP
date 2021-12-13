package oop.ex6.main;

import java.util.HashMap;

/**
 * a sub-class of Scope class - has additional features as methods map.
 */
public class GlobalScope extends Scope{
    protected static HashMap<String, Method> mapMethods = new HashMap<>();
    // String is the name of the method, Method is the method itself

    /**
     *
     * @param method
     */
    public void addMethodToMap(Method method){
        mapMethods.put(method.getMethodName(),method);
    }
    public HashMap<String,Method> getMapMethods(){
        return mapMethods;
    }

}
