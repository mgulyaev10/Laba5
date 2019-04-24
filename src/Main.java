import java.io.File;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        File file = new File("");
        try {
            file = new File(System.getenv(args[0]));
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            System.err.println("Ошибка. Введите имя переменной окружения, в которой хранится путь к коллекции.");
            finishProgramDueToError();
        }

        CollectionCase collectionCase = new CollectionCase();
        FileManager fileManager = new FileManager(collectionCase, file.getPath());

        fileManager.updateCollection();

        System.out.println("Программа успешно запущена. Чтобы получить справку, введите \"help\"");
        Scanner scanner = new Scanner(System.in);
        String command = "";

        while (true) {
            try {
                command = scanner.nextLine();
                Command cmd = Command.parseCmd(command);
                cmd.start(fileManager);
            } catch (IllegalArgumentException ex) {
                if (ex.getMessage().contains("аргумент")) {
                    System.err.println("Ошибка: " + ex.getMessage());
                } else {
                    System.err.println("Ошибка: введённая команда не найдена.");
                }
            } catch (NoSuchElementException ex) {
                finishProgramDueToError();
            }
        }
    }

    public static void finishProgramDueToError() {
        System.err.println("Завершение программы. " +
                "Для продолжения работы запустите программу заново и" +
                " введите \"help\" для получения справки.");
        System.exit(0);
    }

}
