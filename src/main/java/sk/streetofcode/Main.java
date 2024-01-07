package sk.streetofcode;

import sk.streetofcode.db.DBContactService;

public class Main {
    public static void main(String[] args) {
        DBContactService service = new DBContactService();
        service.readAll().forEach(System.out::println);
    }
}
