import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Введите ФИО (Фамилия Имя Отчество):");
            String fullName = scanner.nextLine();

            System.out.println("Введите дату рождения (дд-ММ-гггг):");
            String birthDate = scanner.nextLine();

            Person human = new Person(fullName, birthDate);

            System.out.println("ФИО: " + human.getFullName());
            System.out.println("Инициалы: " + human.getInitials());
            System.out.println("Возраст: " + human.getAge());
            System.out.println("Пол: " + human.determineGender());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
