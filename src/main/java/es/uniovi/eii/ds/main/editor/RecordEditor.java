/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package es.uniovi.eii.ds.main.editor;

import es.uniovi.eii.ds.main.command.Command;
import es.uniovi.eii.ds.main.history.History;

/**
 *
 * @author UO297383
 */
public class RecordEditor implements Editor{

    private final String macroName;
    private final History history;

    public RecordEditor(String macroName, History history) {
        this.macroName = macroName;
        this.history = history;
    }

    @Override
    public void execute(Command c) {
        history.add(macroName, c);
    }

}
