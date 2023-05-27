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
        String text = "Вітаємо! Ми помітили, що ви досі не оплатили поточний місяць, Ваша оплата прострочена на " + daysUntilNextPayment + " днів. " +
                "Будь ласка, оплатіть його якомога швидше, щоб не втратити доступ до сервісу";
        emailSender.sendEmail(user.getEmail(), subject, text);
    }

    private void sendPaymentReminderEmail(User user, long daysUntilNextPayment) {
        String subject = "Нагадування про оплату";
        String text = "Вітаємо! За умовами підписки, Вам необхідно оплатити наступний місяць протягом " + daysUntilNextPayment + " днів";
        emailSender.sendEmail(user.getEmail(), subject, text);
    }

}
