import java.util.*;
import java.util.function.*;

public class FileProcessingFramework
{

    public static void main(String[] args)
    {
        FileData file = new FileData("students.csv", "CSV", 2.5, "101,Rahul\n102,Priya\n103,Amit","ADMIN");

        Predicate<FileData> validator = f -> f.getFileSizeInMb() < 5 && f.getContent() != null && !f.getContent().isBlank() && f.getUploadedBy() != null && Arrays.asList("CSV", "JSON", "XML", "TXT").contains(f.getFileType());



        Consumer<FileData> fileInfoConsumer =
                f ->
                {
                    System.out.println("File Name : " + f.getFileName());
                    System.out.println("File Type : " + f.getFileType());
                    System.out.println("Uploaded By : " + f.getUploadedBy());
                };

        BiConsumer<String, String> auditLogger = (user, action) -> System.out.println("[AUDIT] User : " + user + " | Action : "+ action);



        FileProcessor csvProcessor = f ->
        {
            long records = Arrays.stream(f.getContent().split("\n")).count();
            System.out.println("CSV Records Count : " + records);
            System.out.println("CSV Records Processed Successfully");
        };

        FileProcessor jsonProcessor = f -> {
            System.out.println("JSON Parsed Successfully");
            System.out.println("JSON Data Processed Successfully");
        };



        FileProcessor xmlProcessor = f ->
        {
            System.out.println("XML Parsed Successfully");
            System.out.println("XML Data Processed Successfully");
        };



        FileProcessor txtProcessor = f ->
        {
            System.out.println("TXT File Read Successfully");
            System.out.println("TXT Content Processed Successfully");
        };



        Map<String, FileProcessor> processors = new HashMap<>();
        processors.put("CSV", csvProcessor);
        processors.put("JSON", jsonProcessor);
        processors.put("XML", xmlProcessor);
        processors.put("TXT", txtProcessor);
        fileInfoConsumer.accept(file);



        if (!validator.test(file))
        {

            System.out.println("Validation : FAILED");
            return;
        }
        System.out.println("Validation : SUCCESS");
        auditLogger.accept(file.getUploadedBy(), "FILE_UPLOAD");

        FileProcessor processor = processors.get(file.getFileType());
        System.out.println("Processor Selected : " + file.getFileType() + " Processor");
        processor.process(file);
    }
}