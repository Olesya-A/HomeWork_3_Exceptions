import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Task_1 {

    public static void main(String[] args) throws IOException {
        try {
            makeRecord();
        } catch (FileSystemException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void makeRecord() throws Exception {
        try (Scanner scan = new Scanner(System.in)) {
            System.out.println(
                    "Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона (число без разделителей) и пол(символ латиницей f или m), разделенные пробелом");
            String inputData = scan.nextLine();
            String[] inputDataArray = inputData.split(" ");
            int checkLength = checkLengthInputData(inputDataArray);
            if (checkLength == -1) {
                System.out.println("Вы ввели меньше данных, чем требуется");
            } else if (checkLength == -2) {
                System.out.println("Вы ввели больше данных, чем требуется");
            } else {
                savePersonalData(inputDataArray);
            }
        }
    }

    public static int checkLengthInputData(String[] array) {
        if (array.length < 6) {
            return -1;
        }
        if (array.length > 6) {
            return -2;
        }
        return array.length;
    }

    public static void savePersonalData(String[] array) throws Exception {

        String surname = array[0];
        String name = array[1];
        String patronymic = array[2];

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd.MM.yyyy");
        Date birthdate;
        try {
            birthdate = format.parse(array[3]);
        } catch (ParseException e) {
            throw new ParseException("Неверный формат даты рождения", e.getErrorOffset());
        }

        int phone;
        try {
            phone = Integer.parseInt(array[4]);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Неверный формат телефона");
        }

        String gender = array[5];
        if (!gender.toLowerCase().equals("m") && !gender.toLowerCase().equals("f")) {
            throw new RuntimeException("Неверно введен пол");
        }

        String fileName = surname.toLowerCase() + ".txt";
        File file = new File(fileName);
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            if (file.length() > 0) {
                fileWriter.write('\n');
            }
            fileWriter.write(
                    String.format("%s %s %s %s %s %s", surname, name, patronymic, birthdate, phone, gender));
            System.out.println("successfully");
        } catch (IOException e) {
            throw new FileSystemException("Возникла ошибка при работе с файлом");
        }
    }

}
