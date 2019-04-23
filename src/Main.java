import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)throws IOException {
        File file = new File(System.getenv("TrollPath"));
        CollectionCase collectionCase = new CollectionCase();
        FileManager fileManager = new FileManager(collectionCase, file.getPath());

        fileManager.updateCollection();

        Scanner scanner = new Scanner(System.in);
        String command="";

        while (true){
            try{
                command = scanner.nextLine();
                Command cmd = Command.parseCmd(command);
                cmd.start(fileManager);
            } catch (IllegalArgumentException ex){
                if (ex.getMessage().contains("аргумент")){
                    System.err.println("Ошибка: " + ex.getMessage());
                } else {
                    System.err.println("Ошибка: введённая команда не найдена.");
                }
            } catch (NoSuchElementException ex){
                System.err.println("Ошибка. Завершение программы. " +
                        "Для продолжения работы запустите программу заново и" +
                        " введите \"help\" для получения справки.");
                System.exit(0);
            }
        }

    }

}
