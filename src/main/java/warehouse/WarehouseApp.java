package warehouse;

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WarehouseApp {

    private static final String URL = "jdbc:postgresql://localhost:5432/warehouse_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "your-password-db";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //try-with-resources dla połączenia
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            int choice = -1;

            while (choice != 0) {
                printMenu();
                choice = readInt(scanner);

                try {
                    switch (choice) {
                        case 1 -> showProducts(connection);
                        case 2 -> addProduct(connection, scanner);
                        case 3 -> deleteProduct(connection, scanner);
                        case 4 -> updateProduct(connection, scanner);
                        case 0 -> System.out.println("Zamykanie systemu...");
                        default -> System.out.println("Nieprawidłowy wybór! Wybierz 0-4.");
                    }
                } catch (SQLException e) {
                    System.err.println("Błąd podczas operacji: " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            System.err.println("Nie można połączyć się z bazą danych: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void printMenu() {
        System.out.println("\n SYSTEM MAGAZYNOWY ");
        System.out.println("1. Wyświetl produkty");
        System.out.println("2. Dodaj produkt");
        System.out.println("3. Usuń produkt (po ID)");
        System.out.println("4. Edytuj produkt (po ID)");
        System.out.println("0. Wyjdź");
        System.out.print("Twój wybór: ");
    }


    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                int val = scanner.nextInt();
                scanner.nextLine();
                return val;
            } catch (InputMismatchException e) {
                System.out.print("Błąd! Podaj liczbę całkowitą: ");
                scanner.nextLine();
            }
        }
    }

    private static double readDouble(Scanner scanner) {
        while (true) {
            try {
                double val = scanner.nextDouble();
                scanner.nextLine();
                return val;
            } catch (InputMismatchException e) {
                System.out.print("Błąd! Podaj poprawną cenę (np. 19,99): ");
                scanner.nextLine();
            }
        }
    }

    // METODY

    private static void showProducts(Connection connection) throws SQLException {
        String sql = "SELECT * FROM products ORDER BY id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\nID | NAZWA | ILOŚĆ | CENA");
            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                System.out.printf("%d | %s | %d | %.2f zł%n",
                        rs.getInt("id"), rs.getString("name"),
                        rs.getInt("quantity"), rs.getDouble("price"));
            }
            if (!hasData) System.out.println("Magazyn jest pusty.");
        }
    }

    private static void addProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Nazwa produktu: ");
        String name = scanner.nextLine();
        System.out.print("Ilość: ");
        int qty = readInt(scanner);
        System.out.print("Cena: ");
        double price = readDouble(scanner);

        String sql = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, qty);
            pstmt.setDouble(3, price);
            pstmt.executeUpdate();
            System.out.println("Sukces: Dodano produkt!");
        }
    }

    private static void deleteProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Podaj ID do usunięcia: ");
        int id = readInt(scanner);

        String sql = "DELETE FROM products WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Sukces: Usunięto produkt.");
            else System.out.println("Informacja: Nie znaleziono produktu o ID " + id);
        }
    }

    private static void updateProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Podaj ID do edycji: ");
        int id = readInt(scanner);


        System.out.print("Nowa nazwa: ");
        String name = scanner.nextLine();
        System.out.print("Nowa ilość: ");
        int qty = readInt(scanner);
        System.out.print("Nowa cena: ");
        double price = readDouble(scanner);

        String sql = "UPDATE products SET name = ?, quantity = ?, price = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setInt(2, qty);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, id);

            int rows = pstmt.executeUpdate();
            if (rows > 0) System.out.println("Sukces: Zaktualizowano dane!");
            else System.out.println("Błąd: Produkt o ID " + id + " nie istnieje.");
        }
    }
}