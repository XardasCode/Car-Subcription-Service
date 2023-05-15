package com.csub.dao.postgre.util;

import com.csub.entity.Car;
import com.csub.entity.CarStatus;
import com.csub.entity.Subscription;
import com.csub.entity.SubscriptionStatus;
import com.csub.util.CarSearchInfo;
import com.csub.util.CarStatusList;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public final class CarCriteriaBuilderManager {
    private CarCriteriaBuilderManager() {
    }

    private static final String BRAND = "brand";
    private static final String MODEL = "model";
    private static final String YEAR = "year";
    private static final String PRICE = "price";
    private static final String COLOR = "color";
    private static final String ID = "id";
    private static final String DESC = "desc";

    public static List<Predicate> buildCriteria(CarSearchInfo info, CriteriaBuilder builder, CriteriaQuery<Car> query, Root<Car> root) {
        List<Predicate> fieldPredicates = new ArrayList<>();
        setFilterInfo(info, builder, root, fieldPredicates);
        setSortingInfo(info, builder, query, root);
        return fieldPredicates;
    }

    public static List<Predicate> buildCountCriteria(int size, List<String> filter, CriteriaBuilder builder, Root<Car> root) {
        List<Predicate> fieldPredicates = new ArrayList<>();
        CarSearchInfo info = CarSearchInfo.builder()
                .filter(filter)
                .size(size)
                .build();
        setFilterInfo(info, builder, root, fieldPredicates);
        return fieldPredicates;
    }

    private static void setFilterInfo(CarSearchInfo info, CriteriaBuilder builder, Root<Car> root, List<Predicate> fieldPredicates) {
        long statusId = CarStatusList.AVAILABLE.getStatusId();
        log.debug("Getting car with statusId {}", statusId);
        fieldPredicates.add(builder.equal(root.get("carStatus"), statusId));

        if (info.getFilter() != null && !info.getFilter().isEmpty()) {
            for (String field : info.getFilter()) {
                String[] filter = field.split(":");
                if (filter.length == 2) {
                    switch (filter[0]) {
                        case BRAND -> fieldPredicates.add(builder.like(root.get(BRAND), "%" + filter[1] + "%"));
                        case MODEL -> fieldPredicates.add(builder.like(root.get(MODEL), "%" + filter[1] + "%"));
                        case COLOR -> fieldPredicates.add(builder.like(root.get(COLOR), "%" + filter[1] + "%"));
                        case YEAR -> fieldPredicates.add(builder.equal(root.get(YEAR), Integer.parseInt(filter[1])));
                        case PRICE -> fieldPredicates.add(builder.equal(root.get(PRICE), Integer.parseInt(filter[1])));
                    }
                }

            }
            fieldPredicates.add(builder.and(fieldPredicates.toArray(new Predicate[0])));
        }
    }

    private static void setSortingInfo(CarSearchInfo info, CriteriaBuilder
            builder, CriteriaQuery<Car> query, Root<Car> root) {
        if (info.getSortField() != null && !info.getSortField().
                isEmpty()) {
            switch (info.getSortField()) {
                case BRAND -> setSortDirection(BRAND, info.getDirection(), builder, query, root);
                case MODEL -> setSortDirection(MODEL, info.getDirection(), builder, query, root);
                case YEAR -> setSortDirection(YEAR, info.getDirection(), builder, query, root);
                case PRICE -> setSortDirection(PRICE, info.getDirection(), builder, query, root);
                case COLOR -> setSortDirection(COLOR, info.getDirection(), builder, query, root);
                default -> setSortDirection(ID, info.getDirection(), builder, query, root);
            }
        }
    }

    private static void setSortDirection(@NotNull String field, @NotNull String direction,
                                         CriteriaBuilder builder, CriteriaQuery<Car> query, Root<Car> root) {
        if (direction.equalsIgnoreCase(DESC)) {
            query.orderBy(builder.desc(root.get(field)));
        } else {
            query.orderBy(builder.asc(root.get(field)));
        }
    }
}
