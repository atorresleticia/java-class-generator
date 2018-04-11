package utils;

import generator.Generator;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanner {

    private Generator generator;

    public FileScanner(Generator generator) {
        this.generator = generator;
    }

    public FileScanner() {}

    public File copy(Path source, Path destination, String fileName) throws IOException {

        System.out.println(fileName);

        if(!Files.exists(destination)) {
            try {
                Files.createDirectories(destination);
            } catch (IOException e) {
            }
        }

        destination = Paths.get(destination.toString() + "/" + fileName + ".java");

        Files.copy(source, destination, LinkOption.NOFOLLOW_LINKS);

        return destination.toFile();
    }

    public String xmlToString(File xml) {
        try (Reader reader = new FileReader(xml)) {
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder sb = new StringBuilder();
            String line = bufferedReader.readLine();
            while (line != null) {
                sb.append(line).append("\n");
                line = bufferedReader.readLine();
            }

            return sb.toString();

        } catch (FileNotFoundException e1) {
        } catch (IOException e1) {
        }
        return null;
    }

    public String[] readAttributes() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xmlToString(new File("attributes.xml"))));
            Document document = builder.parse(src);
            String[] attributes = new String[6];

            attributes[0] = document.getElementsByTagName("api").item(0).getTextContent();
            attributes[1] = document.getElementsByTagName("entityPkg").item(0).getTextContent();
            attributes[2] = document.getElementsByTagName("name").item(0).getTextContent();
            attributes[3] = document.getElementsByTagName("mail").item(0).getTextContent();
            attributes[4] = document.getElementsByTagName("version").item(0).getTextContent();
            attributes[5] = document.getElementsByTagName("date").item(0).getTextContent();

            return attributes;

        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        } catch (SAXException e) {
        }

        return null;
    }

    public String readPath() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xmlToString(new File("path.xml"))));
            Document document = builder.parse(src);

            return document.getElementsByTagName("web").item(0).getTextContent();

        } catch (ParserConfigurationException e) {
        } catch (IOException e) {
        } catch (SAXException e) {
        }

        return null;
    }

    public void findAndReplace(String[] attributes, File file) throws IOException {
        Path path = Paths.get(file.toURI());
        try (Stream<String> lines = Files.lines(path)) {
            List<String> replaced = lines.map(line -> line
                    .replaceAll("<api>", attributes[0])
                    .replaceAll("<entityPkg>", attributes[1])
                    .replaceAll("<author>", attributes[2])
                    .replaceAll("<mail>", attributes[3])
                    .replaceAll("<version>", attributes[4])
                    .replaceAll("<date>", attributes[5]))
                    .collect(Collectors.toList());
            Files.write(path, replaced);
            lines.close();
        }
    }
}