package sk.streetofcode.service;

import sk.streetofcode.db.Contact;
import sk.streetofcode.db.DBContactService;
import sk.streetofcode.utility.InputUtils;

import java.util.List;

public class CRUDManager {
    private final DBContactService contactService;

    public CRUDManager() {
        this.contactService = new DBContactService();
    }

    public void printOptions() {
        System.out.println("Hello, welcome to contacts manager\n");
        while (true) {
            System.out.println("0. Get all contacts");
            System.out.println("1. Edit contact");
            System.out.println("2. Add contact");
            System.out.println("3. Delete contact");
            System.out.println("4. Search contacts");
            System.out.println("5. Exit");

            final int choice = InputUtils.readInt();
            switch (choice) {
                case 0 -> printAllContacts();
                case 1 -> System.out.println("Not implemented");
                case 2 -> System.out.println("Not implemented");
                case 3 -> System.out.println("Not implemented");
                case 4 -> System.out.println("Not implemented");
                case 5 -> {
                    System.out.println("Good Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void printAllContacts() {
        final List<Contact> contacts = contactService.readAll();
        contacts.forEach(System.out::println);
    }
}
