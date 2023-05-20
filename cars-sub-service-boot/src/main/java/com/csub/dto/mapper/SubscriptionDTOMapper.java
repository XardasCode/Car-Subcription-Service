package com.csub.dto.mapper;

import com.csub.dto.SubscriptionDTO;
import com.csub.entity.Subscription;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubscriptionDTOMapper implements Function<Subscription, SubscriptionDTO> {
    @Override
    public SubscriptionDTO apply(Subscription subscription) {
        long managerId = subscription.getManager() == null ? 0 : subscription.getManager().getId();
        String lastPayDate = subscription.getLastPayDate() == null ? "null" : subscription.getLastPayDate();
        return new SubscriptionDTO(
                subscription.getId(),
                subscription.isActive(),
                subscription.getStartDate(),
                subscription.getMonthPrice(),
                subscription.getTotalPrice(),
                subscription.getTotalMonths(),
                subscription.getPassportNumber(),
                subscription.getIpnNumber(),
                subscription.getSocMediaLink(),
                lastPayDate,
                subscription.getUser().getId(),
                subscription.getCar().getId(),
                managerId,
                subscription.getStatus().getName());
    }
}
