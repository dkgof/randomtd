/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.lystrup.randomtd.util;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Thor
 */
public class BuffUtil {
    
    private static int nextInt = 0;
    private static Map<Class, String> idMap = new HashMap<>();
    
    public static String getBuffId(Class c){
        if(idMap.containsKey(c)){
            return idMap.get(c);
        }else{
            String id = generateBuffId();
            idMap.put(c, id);
            return id;
        }
    }

    private static String generateBuffId() {
        return "BUFF_"+nextInt++;
    }
}
