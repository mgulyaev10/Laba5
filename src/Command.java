import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayDeque;
import java.util.regex.Pattern;

/**
 * Класс, предназначенный для обработки команд, вводимых пользователем.
 */
public enum Command {
    /**
     * Удаление последнего элемента коллекции.
     */
    remove_last((manager, data) -> {
        ArrayDeque<Troll> trolls = (ArrayDeque<Troll>) manager.getCollectionFromFile();
        trolls.removeLast();
        manager.saveCollection(trolls);
        manager.updateCollection();
        System.out.println("Команда успешно выполнена");
    }),
    /**
     * Удаление элемента, соответствуещему введённому JSON.
     */
    remove((manager, data) -> {
        try {
            JSONObject jsonObject = new JSONObject(data);
            ArrayDeque<Troll> trolls = (ArrayDeque<Troll>) manager.getCollectionFromFile();
            if (trolls.remove(new Troll(jsonObject))) {
                manager.saveCollection(trolls);
                manager.updateCollection();
                System.out.println("Команда успешно выполнена");
            } else {
                System.err.println("Тролля, соответствующему введённому JSON не найдено.");
            }
        } catch (JSONException ex) {
            System.err.println("Ошибка: введите корректный json-объект");
        }
    }),
    /**
     * Удаление всех элементов коллекции.
     */
    clear((manager, data) -> {
        ArrayDeque<Troll> trolls = (ArrayDeque<Troll>) manager.getCollectionFromFile();
        trolls.clear();
        manager.saveCollection(trolls);
        manager.updateCollection();
        System.out.println("Команда успешно выполнена");
    }),
    /**
     * Вывод информации о типе коллекции, дате инициализации, а также количестве элементов
     */
    info((manager, data) -> {
        System.out.println(String.format(
                "Тип коллекции: %s \nТип элементов коллекции: %s\nДата инициализации: %s\nКоличество элементов: %s\n",
                manager.getCollectionCase().getCollection().getClass().getName(),
                "Troll", manager.getInitDate(), manager.getCollectionCase().getCollection().size()));

        System.out.println("Команда успешно выполнена");
    }),
    /**
     * Удаление всех элементов коллекции, меньше элемента, соответствующего введённому JSON.
     */
    remove_lower((manager, data) -> {
        try {
            ArrayDeque<Troll> trolls = (ArrayDeque<Troll>) manager.getCollectionFromFile();
            JSONObject jsonObject = new JSONObject(data);
            Troll t = new Troll(jsonObject);
            trolls.forEach(o -> {
                if (o.compareTo(t) < 0) {
                    trolls.remove(o);
                }
            });
            manager.saveCollection(trolls);
            manager.updateCollection();
            System.out.println("Команда успешно выполнена");
        } catch (JSONException ex) {
            System.err.println("Ошибка: введите корректный json-объект");
        }
    }),
    /**
     * Добавление элемента, соответствующему введённому JSON, в коллекцию.
     */
    add((manager, data) -> {
        ArrayDeque<Troll> trolls = (ArrayDeque<Troll>) manager.getCollectionFromFile();
        try {
            JSONObject jsonObject = new JSONObject(data);
            Troll t = new Troll(jsonObject);
            trolls.add(t);
            manager.saveCollection(trolls);
            manager.updateCollection();
            System.out.println("Команда успешно выполнена");
        } catch (JSONException ex) {
            System.err.println("Ошибка: введите корректный json-объект");
        }
    }),
    /**
     * Вывод всех элементов коллекции
     */
    show((manager, data) -> {
        ArrayDeque<Troll> trolls = (ArrayDeque<Troll>) manager.getCollectionFromFile();
        if (trolls.size() > 0) {
            trolls.forEach(System.out::println);
            System.out.println("Команда успешно выполнена");
        } else {
            System.out.println("Файл с коллекцией пуст");
        }
    }),
    exit((manager, data) -> {
        System.out.println("Завершение программы...");
        System.exit(0);
    }),
    help((manager, data) -> {
        System.out.println(
                "\"remove_last\": удалить последний элемент из коллекции\n" +
                        "\"remove {element}\": удалить элемент из коллекции по его значению\n" +
                        "\"clear\": очистить коллекцию\n" +
                        "\"info\": вывести в стандартный поток вывода информацию о коллекции " +
                        "(тип, дата инициализации, количество элементов и т.д.)\n" +
                        "\"remove_lower {element}\": удалить из коллекции все элементы, меньшие, чем заданный\n" +
                        "\"add {element}\": добавить новый элемент в коллекцию\n" +
                        "\"show\": вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                        "\"help\": получить информацию о доступных командах\n" +
                        "\"exit\": выйти из программы\n\n" +
                        "Пример корректного JSON-объекта:\n" +
                        "{\"isSad\":true,\"name\":\"Петя\",\"HP\":100,\"things\":[{\"condition\":\"Solid\"," +
                        "\"name\":\"Соль\",\"weight\":20}],\"isSit\":true,\"age\":10}\n\n" +
                        "При запуске программы указывайте переменную окружения, в которой хранится путь к коллекции.");
    });

    Command(Startable s) {
        cmd = s;
    }

    /**
     * Метод, предназначенный для обработки строки, вводимой пользователем
     *
     * @param jsonInput - строка, введённая пользователем
     * @return - возвращает команду, содержащую внутри себя JSON-объект.
     * @throws IllegalArgumentException - выбрасывает IllegalArgumentException, если введённой команды не существует.
     */
    public static Command parseCmd(String jsonInput) throws IllegalArgumentException {

        if (!jsonInput.contains("{")) {
            Command command = Command.valueOf(jsonInput.replace(" ", "").toLowerCase());
            if (command == Command.add || command == Command.remove || command == Command.remove_lower) {
                throw new IllegalArgumentException("эта команда требует аргумент в формате JSON");
            } else
                return Command.valueOf(jsonInput.replace(" ", "").toLowerCase());
        } else {
            Command command = Command
                    .valueOf(jsonInput.split(Pattern.quote("{"))[0].replace(" ", "").toLowerCase());

            command.data = jsonInput.substring(command.toString().length());
            return command;
        }
    }

    private Startable cmd;

    private String data;

    /**
     * Метод, предназначенный для запуска команды, введённой пользователем.
     *
     * @param manager файловый менеджер, с помощью которого можно получить коллекцию.
     */
    public void start(FileManager manager) {
        if (cmd != null) {
            if (manager != null) {
                cmd.start(manager, data);
            } else {
                System.err.println("Ошибка: Файловый менеджер пуст.");
            }
        } else {
            System.err.println("Ошибка: Комманда не определена.");
        }

    }
}


interface Startable {
    void start(FileManager manager, String data);
}
