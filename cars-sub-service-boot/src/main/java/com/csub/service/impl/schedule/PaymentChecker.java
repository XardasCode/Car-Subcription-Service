package com.csub.service.impl.schedule;

import com.csub.entity.User;
import com.csub.service.UserService;
import com.csub.util.EmailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentChecker {

    private final UserService userService;

    private final EmailSender emailSender;

    @Scheduled(cron = "0 0 0 * * *") // every day at 00:00
    public void checkPayment() {
        log.info("Checking payment");
        List<User> userList = userService.getUsersWithSubscriptions();
        for (User user : userList) {
            LocalDate lastPayDate = getLocalDateFromOffsetDateTime(user.getSubscription().getLastPayDate());

            sendRemindEmail(getDaysUntilNextPayment(lastPayDate), user);
        }
    }

    private void sendRemindEmail(long daysUntilNextPayment, User user) {
        if (daysUntilNextPayment < 3) {
            sendPaymentReminderEmail(user, daysUntilNextPayment);
        }

        if (daysUntilNextPayment < 0) {
            sendPaymentOverdueEmail(user, daysUntilNextPayment);
        }
    }

    private LocalDate getLocalDateFromOffsetDateTime(String offsetDateTime) {
        OffsetDateTime offsetDateTime1 = OffsetDateTime.parse(offsetDateTime, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return offsetDateTime1.toLocalDate();
    }

    private long getDaysUntilNextPayment(LocalDate lastPayDate) {
        LocalDate currentDate = LocalDate.now();

        LocalDate nextPaymentDate = lastPayDate.plusMonths(1);

        return ChronoUnit.DAYS.between(currentDate, nextPaymentDate);
    }

    private void sendPaymentOverdueEmail(User user, long daysUntilNextPayment) {
        String subject = "Оплата прострочена";
        String text = "Hello, " + user.getName() + "! Your payment will be overdue for " + daysUntilNextPayment + " days." +
                "Please, pay for the next month as soon as possible. ";
        emailSender.sendEmail(user.getEmail(), subject, text);
    }

    private void sendPaymentReminderEmail(User user, long daysUntilNextPayment) {
        String subject = "Нагадування про оплату";
        String text = "Hello, " + user.getName() + "! Your payment is overdue for " + daysUntilNextPayment + " days already. " +
                "Please, pay for the next month as soon as possible or your subscription will be canceled. ";
        emailSender.sendEmail(user.getEmail(), subject, text);
    }

}
