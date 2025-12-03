import java.io.*;
import java.util.ArrayList;
import java.util.List;

class DataService {
    private final String FILENAME = "library_data.csv";

    public void saveBooks(List<Book> books) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Book book : books) {
                writer.println(book.toCSV());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> loadBooks() {
        List<Book> books = new ArrayList<>();
        File file = new File(FILENAME);
        if (!file.exists()) return books;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    books.add(new Book(parts[0], parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }
}