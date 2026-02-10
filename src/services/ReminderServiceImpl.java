package services;

import entities.BillReminder;

import java.util.ArrayList;
import java.util.List;

public class ReminderServiceImpl implements ReminderService {

    private final List<BillReminder> reminders = new ArrayList<>();

    @Override
    public void addReminder(BillReminder reminder) {
        reminders.add(reminder);
    }

    @Override
    public void checkDueReminders() {
        reminders.stream()
                .filter(BillReminder::isDueToday)
                .forEach(r ->
                        System.out.println(
                                "Bill due today: " +
                                        r.getBillName() +
                                        " | Amount: " +
                                        r.getAmount()
                        )
                );
    }
}
