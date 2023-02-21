package view.cli;

import view.cli.Command;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TextMenu {
    private Map<String, Command> commands;

    public TextMenu() {
        commands = new HashMap<>();
    }

    public void addCommand(Command command)
    {
        commands.put(command.getKey(), command);
    }

    private void printMenu()
    {
        for (Command c : commands.values())
        {
            String line = String.format("%4s : %s", c.getKey(), c.getDescription());
            System.out.println(line);
        }
    }

    public void show()
    {
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            printMenu();
            System.out.print("Input your option: ");
            String key = scanner.next();
            Command command = commands.get(key);
            if (command == null)
            {
                System.out.println("Invalid option.");
                continue;
            }
            command.execute();
        }
    }
}
