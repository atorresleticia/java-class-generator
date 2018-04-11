package main;

import utils.FileScanner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String args[]) {

        FileScanner fs = new FileScanner();

        String[] attributes = fs.readAttributes();

        String entityName = attributes[1];
        String webPath = fs.readPath();

//        webPath += "/" + attributes[0] + "/src/main/java/com/ws/" + attributes[0] + "/" + attributes[1];
        webPath += "/calendarbkp/src/main/java/com/ws/" + attributes[0] + "/" + attributes[1];
        entityName = entityName.substring(0, 1).toUpperCase() + entityName.substring(1);

        File entity = new File("Entity.java");
        File dao = new File("EntityDAO.java");
        File search = new File("EntitySearch.java");
        File service = new File("EntityService.java");
        File resource = new File("EntityResource.java");
        File validation = new File("EntityValidation.java");

        try {
            fs.findAndReplace(attributes, fs.copy(Paths.get(entity.toString()), Paths.get(webPath), entityName));
            fs.findAndReplace(attributes, fs.copy(Paths.get(dao.toString()), Paths.get(webPath), (entityName+"DAO")));
            fs.findAndReplace(attributes, fs.copy(Paths.get(search.toString()), Paths.get(webPath), (entityName+"Search")));
            fs.findAndReplace(attributes, fs.copy(Paths.get(service.toString()), Paths.get(webPath), (entityName+"Service")));
            fs.findAndReplace(attributes, fs.copy(Paths.get(resource.toString()), Paths.get(webPath), (entityName+"Resource")));
            fs.findAndReplace(attributes, fs.copy(Paths.get(validation.toString()), Paths.get(webPath), (entityName+"Validation")));
        } catch (IOException e) {
        }

    }
}