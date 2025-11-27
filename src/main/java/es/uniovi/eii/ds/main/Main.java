package es.uniovi.eii.ds.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import es.uniovi.eii.ds.main.command.Command;
import es.uniovi.eii.ds.main.command.OpenCommand;
import es.uniovi.eii.ds.main.command.ReplaceCommand;
import es.uniovi.eii.ds.main.editor.Editor;
import es.uniovi.eii.ds.main.editor.OpenEditor;
import es.uniovi.eii.ds.main.editor.RecordEditor;
import es.uniovi.eii.ds.main.history.History;

public class Main {

    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	
	// Represents the document of the editor.
	StringBuilder text = new StringBuilder();
	History history = new History();

    public static void main(String[] args) {
        new Main().run();
    }
	
	// Main program loop.
    public void run() {
		drawLogo();
		showHelp();

		while (true) {
			UserCommand command = promptUser();
			String[] args = command.args;
			Command c = null;
			Editor editor = new OpenEditor();

			switch (command.name) {
				case "open" -> {
					text = new StringBuilder();
					c = new OpenCommand(args);}
				case "insert" -> { 
					for (String word : args) {
						text.append(" ").append(word);
					}
				}
				case "delete" -> {
					int indexOfLastWord = text.toString().trim().lastIndexOf(" ");
					if (indexOfLastWord == -1)
						text = new StringBuilder("");
					else
						text.setLength(indexOfLastWord);
				}
				case "replace" -> c = new ReplaceCommand(args, text);
				case "help" -> showHelp();
				case "record" -> {
					String macroName = args[0];
					editor = new RecordEditor(macroName, history);
				}
				case "stop" -> { 
					editor = new OpenEditor();
				}
				case "execute" -> {
					String macroName = args[0];
					for(Command com : history.getRecord(macroName)){
						text.append(com.excute());
					}
				}
				default -> {
					System.out.println("Unknown command");
					continue;
				}
			}
			if(c != null){
				editor.execute(c);
			}
			System.out.println(text);
		}
	}

	//$-- Some individual user commands that do a bit more work ---------------

	//$-- Auxiliary methods ---------------------------------------------------

	// YOU DON'T NEED TO UNDERSTAND OR MODIFY THE CODE BELOW THIS LINE

	private record UserCommand(String name, String[] args) {}

    // Prompts the user and reads a line of input and returns it as a record with
	// the command and its arguments. If EOF is reached (i.e., there are nothing to
	// read), an error occurs or the user types "exit", the program exits. If there
	// are no arguments, the args array is empty.
	//
	// Example:
	//
	//   > insert "no quiero acordarme" --> returns UserInput("insert", ["no", "quiero", "acordarme"])
	//	 > delete                       --> returns UserInput("delete", [])
	//
	private UserCommand promptUser() {
		while (true) {
            System.out.print("> ");
            try {
                String line = in.readLine();
				if (line == null) System.exit(0);
				if (line.equals("exit")) exit();
				if (line.isBlank()) continue;
				String[] parts = line.split("\\s+");
				return new UserCommand(parts[0], Arrays.copyOfRange(parts, 1, parts.length));
            } catch (IOException e) {
                System.out.println("Error reading input");
				System.exit(2);
			}
		}
    }

    

	private void exit() {
		System.out.println("Goodbye!");
		System.exit(0);
	}	

	private void drawLogo() {
		System.out.println(LOGO);
	}

	private void showHelp() {
		System.out.println(HELP);
	}

	private static final String LOGO = """

			███╗   ███╗ █████╗  ██████╗████████╗███████╗██╗  ██╗
			████╗ ████║██╔══██╗██╔════╝╚══██╔══╝██╔════╝╚██╗██╔╝
			██╔████╔██║███████║██║        ██║   █████╗   ╚███╔╝ 
			██║╚██╔╝██║██╔══██║██║        ██║   ██╔══╝   ██╔██╗ 
			██║ ╚═╝ ██║██║  ██║╚██████╗   ██║   ███████╗██╔╝ ██╗
			╚═╝     ╚═╝╚═╝  ╚═╝ ╚═════╝   ╚═╝   ╚══════╝╚═╝  ╚═╝
			""";

	private static final String HELP = """
			┌──────────────────────┬─────────────────────────────────────────────┐
			│ open <file>          │                                             │
			│ insert <text>        │ append text to the end                      │
			│ delete               │ delete the last word                        │
			│ replace <a> <b>      │ replace <a> with <b> in the whole document  │
			├──────────────────────┼─────────────────────────────────────────────┤
			│ record <macro>       │ start recording a macro                     │
			│ stop                 │ stop recording                              │
			│ execute <macro>      │ execute the specified macro                 │
			├──────────────────────┼─────────────────────────────────────────────┤
			│ help                 │                                             │
			│ exit                 │                                             │
			└──────────────────────┴─────────────────────────────────────────────┘
			""";
}
