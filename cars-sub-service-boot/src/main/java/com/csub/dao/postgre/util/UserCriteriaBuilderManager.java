package com.csub.dao.postgre.util;

import com.csub.entity.Car;
import com.csub.entity.Subscription;
import com.csub.entity.SubscriptionStatus;
import com.csub.entity.User;

import com.csub.util.CarStatusList;
import com.csub.util.UserRolesList;
import com.csub.util.UserSearchInfo;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserCriteriaBuilderManager {

    private static final String IS_BLOCKED = "isBlocked";
    private static final String SUBSCRIPTION_STATUS = "subscriptionStatus";
    private static final String NO_SUBSCRIPTION = "noSubscription";
    private static final String DESC = "desc";
    private static final String NAME = "name";
    private static final String SURNAME = "surname";
    private static final String ID = "id";

    public static List<Predicate> buildCriteria(UserSearchInfo info, CriteriaBuilder builder, CriteriaQuery<User> query, Root<User> root) {
        List<Predicate> fieldPredicates = new ArrayList<>();
        setFilterInfo(info, builder, root, fieldPredicates);
        setSortingInfo(info, builder, query, root);
        return fieldPredicates;
    }

    public static List<Predicate> buildCountCriteria(int size, List<String> filter, CriteriaBuilder builder, Root<User> root) {
        List<Predicate> fieldPredicates = new ArrayList<>();
        UserSearchInfo info = UserSearchInfo.builder()
                .filter(filter)
                .size(size)
                .build();
        setFilterInfo(info, builder, root, fieldPredicates);
        return fieldPredicates;
    }
    private static void setFilterInfo(UserSearchInfo info, CriteriaBuilder builder, Root<User> root, List<Predicate> fieldPredicates) {
        long roleId = UserRolesList.USER.getRoleId();
        fieldPredicates.add(builder.equal(root.get("role"), roleId));

        if (info.getFilter() != null && !info.getFilter().isEmpty()) {
            for (String field : info.getFilter()) {
                String[] filter = field.split(":");

                if (filter.length == 2) {
                    switch (filter[0]) {
                        case IS_BLOCKED -> fieldPredicates.add(builder.equal(root.get(IS_BLOCKED), Boolean.parseBoolean(filter[1]) ));
                        case NO_SUBSCRIPTION -> {
                            Join<User, Subscription> subscriptionJoin = root.join("subscription", JoinType.LEFT);
                            fieldPredicates.add(builder.isNull(subscriptionJoin));
                        }
                        case SUBSCRIPTION_STATUS -> {
                            Join<User,Subscription> subscriptionJoin = root.join("subscription", JoinType.INNER);
                            Join<Subscription, SubscriptionStatus> statusJoin = subscriptionJoin.join("status", JoinType.INNER);
                            fieldPredicates.add(builder.like(statusJoin.get("name"), "%" + filter[1] + "%" ));
                        }
                    }
                }

            }
            fieldPredicates.add(builder.and(fieldPredicates.toArray(new Predicate[0])));
        }
    }
    private static void setSortingInfo(UserSearchInfo info, CriteriaBuilder
            builder, CriteriaQuery<User> query, Root<User> root) {
        if (info.getSortField() != null && !info.getSortField().
                isEmpty()) {
            switch (info.getSortField()) {
                case SURNAME -> setSortDirection(SURNAME, info.getDirection(), builder, query, root);
                case NAME -> setSortDirection(NAME, info.getDirection(), builder, query, root);
                default -> setSortDirection(ID, info.getDirection(), builder, query, root);
            }
        }
    }

    private static void setSortDirection(@NotNull String field, @NotNull String direction,
                                         CriteriaBuilder builder, CriteriaQuery<User> query, Root<User> root) {
        if (direction.equalsIgnoreCase(DESC)) {
            query.orderBy(builder.desc(root.get(field)));
        } else {
            query.orderBy(builder.asc(root.get(field)));
        }
    }

}
