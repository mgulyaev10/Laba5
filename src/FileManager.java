import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;
import java.util.Scanner;

public class FileManager {

    private String filePath;
    private CollectionCase mCollectionCase;
    private String initDate;

    public FileManager(CollectionCase collectionCase, String path) {
        filePath = path;
        mCollectionCase = collectionCase;
        initDate = new Date().toString();
    }

    public Deque<Troll> getCollectionFromFile() {
        Deque<Troll> trolls = new ArrayDeque<>();
        try (Reader reader = new FileReader(filePath)) {
            Scanner scanner = new Scanner(reader);
            CharSequence content = new StringBuilder();
            while (scanner.hasNext()) {
                content = ((StringBuilder) content).append(scanner.nextLine());
            }

            if (content.length() == 0) {
                System.err.println("Файл с коллекцией пуст.");
            } else {
                JSONArray array = new JSONArray(content.toString());
                array.forEach(o -> trolls.add(new Troll((JSONObject) o)));
            }
        } catch (FileNotFoundException fnfEx) {
            System.err.println("Ошибка. Файл с коллекцией не найден. Введите имя переменной окружения, в которой хранится путь к коллекции.");
            Main.finishProgramDueToError();
        } catch (IOException ioe) {
            System.err.println("Ошибка. Что-то пошло не так при закрытии файла.");
        }
        return trolls;
    }

    public void updateCollection() {
        mCollectionCase.getCollection().clear();
        mCollectionCase.getCollection().addAll(getCollectionFromFile());
    }

    public boolean saveCollection(ArrayDeque<Troll> collection) {
        JSONArray array = new JSONArray();
        collection.forEach(o -> array.put(o.getJSON()));

        String prettyJsonString = prettyJSONFromJsonArray(array);

        try (Writer writer = new FileWriter(filePath)) {
            writer.write(prettyJsonString);
        } catch (IOException ioe) {
            System.out.println("Ошибка при сохранении коллекции");
            return false;
        }
        return true;
    }

    public String prettyJSONFromJsonArray(JSONArray array) {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(array.toString());
        String prettyJsonString = gson.toJson(je);

        return prettyJsonString;
    }

    public CollectionCase getCollectionCase() {
        return mCollectionCase;
    }

    public String getInitDate() {
        return initDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
