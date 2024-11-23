import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Person {
    private final String fullName;
    private final LocalDate birthDate;

    public Person(String fullName, String birthDate) {
        this.fullName = validateAndFormatFullName(fullName);
        this.birthDate = parseBirthDate(birthDate);
    }

    private String validateAndFormatFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("ФИО не может быть пустым.");
        }
        fullName = fullName.trim().replaceAll("\\s+", " ");

        if (!fullName.matches("([\\p{IsCyrillic}]+(-[\\p{IsCyrillic}]+)?\\s){2}[\\p{IsCyrillic}]+")) {
            throw new IllegalArgumentException("ФИО должно быть в формате: Фамилия Имя Отчество.");
        }
        return fullName;
    }

    private LocalDate parseBirthDate(String birthDateInput) {
        if (birthDateInput == null || birthDateInput.isBlank()) {
            throw new IllegalArgumentException("Дата рождения не может быть пустой.");
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate birthDate = LocalDate.parse(birthDateInput.trim(), formatter);
            if (birthDate.isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Дата рождения не может быть в будущем.");
            }
            return birthDate;
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Дата рождения должна быть в формате: дд-ММ-гггг.");
        }
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLastName() {
        return fullName.split(" ")[0];
    }

    public String getFirstName() {
        return fullName.split(" ")[1];
    }

    public String getMiddleName() {
        return fullName.split(" ")[2];
    }

    public String getAge() {
        Period period = Period.between(birthDate, LocalDate.now());
        return String.format("%d %s, %d %s, %d %s",
                period.getYears(), getAgeSuffix(period.getYears(), "год", "года", "лет"),
                period.getMonths(), getAgeSuffix(period.getMonths(), "месяц", "месяца", "месяцев"),
                period.getDays(), getAgeSuffix(period.getDays(), "день", "дня", "дней"));
    }

    public String determineGender() {
        String middleName = getMiddleName();
        if (middleName.endsWith("ович") || middleName.endsWith("евич") || middleName.endsWith("ич")) {
            return "мужской";
        } else if (middleName.endsWith("овна") || middleName.endsWith("евна") || middleName.endsWith("ична")) {
            return "женский";
        }
        return "неизвестно";
    }

    public String getInitials() {
        return String.format("%s.%s.",
                getFirstName().charAt(0),
                getMiddleName().charAt(0)).toUpperCase();
    }

    private String getAgeSuffix(int number, String singular, String dual, String plural) {
        int lastTwoDigits = number % 100;
        int lastDigit = number % 10;

        if (lastTwoDigits >= 11 && lastTwoDigits <= 14) {
            return plural;
        }
        return switch (lastDigit) {
            case 1 -> singular;
            case 2, 3, 4 -> dual;
            default -> plural;
        };
    }
}
