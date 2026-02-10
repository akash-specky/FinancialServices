package services;

import entities.BillReminder;

public interface ReminderService {
    void addReminder(BillReminder reminder);
    void checkDueReminders();
}
