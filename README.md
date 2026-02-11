# Warehouse Management System (Simple CRUD)

Prosta aplikacja konsolowa typu CRUD napisana w **Java** z wykorzystaniem bazy danych **PostgreSQL**. Projekt stworzony w celu demonstracji obsługi JDBC, walidacji danych wejściowych oraz poprawnej obsługi wyjątków.

## Funkcje
* Wyświetlanie listy produktów.
* Dodawanie nowych pozycji do magazynu.
* Edycja istniejących danych (po ID).
* Usuwanie produktów z bazy.
* **Odporność na błędy:** Program obsługuje błędne dane wprowadzane przez użytkownika (np. tekst zamiast liczb).

## Technologie
* Java 17+
* PostgreSQL
* JDBC (Java Database Connectivity)

## Jak uruchomić
1. **Baza danych:** Uruchom skrypt znajdujący się w `sql/schema.sql`, aby utworzyć tabelę.
2. **Konfiguracja:** Zmień dane logowania (`URL`, `USER`, `PASSWORD`) w klasie `WarehouseApp.java`.
3. **Biblioteki:** Upewnij się, że masz dodany sterownik JDBC PostgreSQL (`postgresql-42.x.x.jar`) do swojego projektu.
4. **Kompilacja i start:**
   ```bash
   javac -d bin src/warehouse/WarehouseApp.java
   java -cp "bin;path/to/postgresql.jar" warehouse.WarehouseApp
