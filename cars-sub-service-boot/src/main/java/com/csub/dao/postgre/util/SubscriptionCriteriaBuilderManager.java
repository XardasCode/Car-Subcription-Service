package com.csub.dao.postgre.util;

import com.csub.entity.*;
import com.csub.util.CarSearchInfo;
import com.csub.util.SubscriptionSearchInfo;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class SubscriptionCriteriaBuilderManager {

    private  SubscriptionCriteriaBuilderManager(){
    }
    private static final String ID = "id";
    private static final String IS_ACTIVE = "isActive";
    private static final String USER = "userSurname";
    private static final String CAR = "brandCar";
    private static final String MANAGER = "managerSurname";
    private static final String STATUS = "statusName";
    private static final String DESC = "desc";

    public static List<Predicate> buildCriteria(SubscriptionSearchInfo info, CriteriaBuilder builder, CriteriaQuery<Subscription> query, Root<Subscription> root) {
        List<Predicate> fieldPredicates = new ArrayList<>();
        setFilterInfo(info, builder, root, fieldPredicates);
        setSortingInfo(info, builder, query, root);
        return fieldPredicates;
    }
    private static void setFilterInfo(SubscriptionSearchInfo info, CriteriaBuilder builder, Root<Subscription> root, List<Predicate> fieldPredicates) {
        if (info.getFilter() != null && !info.getFilter().isEmpty()) {
            for (String field : info.getFilter()) {
                String[] filter = field.split(":");

                if (filter.length == 2) {
                    switch (filter[0]) {
                        case IS_ACTIVE -> fieldPredicates.add(builder.equal(root.get(IS_ACTIVE), Boolean.parseBoolean(filter[1]) ));
                        case USER -> {
                            Join<Subscription, User> userJoin = root.join("user", JoinType.INNER);
                            fieldPredicates.add(builder.like(userJoin.get("surname"), "%" + filter[1] + "%" ));
                        }
                        case CAR -> {
                            Join<Subscription, Car> carJoin = root.join("car", JoinType.INNER);
                            fieldPredicates.add(builder.like(carJoin.get("brand"), "%" + filter[1] + "%" ));
                        }
                        case MANAGER -> {
                            Join<Subscription, Manager> managerJoin = root.join("manager", JoinType.INNER);
                            fieldPredicates.add(builder.like(managerJoin.get("surname"), "%" + filter[1] + "%" ));
                        }
                        case STATUS -> {
                            Join<Subscription, SubscriptionStatus> statusJoin = root.join("status", JoinType.INNER);
                            fieldPredicates.add(builder.like(statusJoin.get("name"), "%" + filter[1] + "%" ));
                        }
                    }
                }

            }
            fieldPredicates.add(builder.and(fieldPredicates.toArray(new Predicate[0])));
        }
    }
    private static void setSortingInfo(SubscriptionSearchInfo info, CriteriaBuilder
            builder, CriteriaQuery<Subscription> query, Root<Subscription> root) {
        if (info.getSortField() != null && !info.getSortField().
                isEmpty()) {
            switch (info.getSortField()) {
                case IS_ACTIVE -> setSortDirection(IS_ACTIVE, info.getDirection(), builder, query, root);
                case USER -> setSortDirection("user", info.getDirection(), builder, query, root);
                case CAR -> setSortDirection("car", info.getDirection(), builder, query, root);
                case MANAGER -> setSortDirection("manager", info.getDirection(), builder, query, root);
                case STATUS -> setSortDirection("status", info.getDirection(), builder, query, root);
                default -> setSortDirection(ID, info.getDirection(), builder, query, root);
            }
        }
    }

    private static void setSortDirection(@NotNull String field, @NotNull String direction,
                                         CriteriaBuilder builder, CriteriaQuery<Subscription> query, Root<Subscription> root) {
        if (direction.equalsIgnoreCase(DESC)) {
            query.orderBy(builder.desc(root.get(field)));
        } else {
            query.orderBy(builder.asc(root.get(field)));
        }
    }

    public static List<Predicate> buildCountCriteria(int size, List<String> filter, CriteriaBuilder builder, Root<Subscription> root) {
        List<Predicate> fieldPredicates = new ArrayList<>();
        SubscriptionSearchInfo info = SubscriptionSearchInfo.builder()
                .filter(filter)
                .size(size)
                .build();
        setFilterInfo(info, builder, root, fieldPredicates);
        return fieldPredicates;
    }

}
