/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package es.uniovi.eii.ds.main.command;

/**
 *
 * @author UO297383
 */
public class ReplaceCommand implements Command{

    private final String[] args;
    private final StringBuilder text;

    public ReplaceCommand(String[] args, StringBuilder text){
        this.args = args;
        this.text = text;
    }

    @Override
    public String excute() {
        if (!checkArguments(args, 2, "replace <find> <replace>"))
			return "";
		String find = args[0];
		String replace = args[1];
		return text.toString().replace(find, replace);
    }
    private boolean checkArguments(String[] args, int expected, String syntax) {
        if (args.length != expected) {
            System.out.println("Invalid number of arguments => " + syntax);
            return false;
        }
        return true;
    }

}
