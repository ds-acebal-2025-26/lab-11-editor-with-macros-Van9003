/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package es.uniovi.eii.ds.main.history;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.uniovi.eii.ds.main.command.Command;

/**
 *
 * @author UO297383
 */
public class History {
    Map<String, List<Command>> history = new HashMap();

    public void add(String key,Command c){
        if(history.containsKey(key)){
            history.get(key).add(c);
        }else{
            List<Command> list = new ArrayList<>();
            list.add(c);
            history.put(key, list);
        }
    }

    public List<Command> getRecord(String key){
        return history.get(key);
    }
}
