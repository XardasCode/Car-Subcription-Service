package com.csub.dao.postgre.impl;

import com.csub.dao.postgre.util.SubscriptionCriteriaBuilderManager;
import com.csub.entity.Subscription;
import com.csub.dao.SubscriptionDAO;
import com.csub.entity.SubscriptionStatus;
import com.csub.util.SubscriptionSearchInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class PostgreSubscriptionDAO implements SubscriptionDAO {

    private final EntityManager sessionFactory;

    @Override
    public long addSubscription(Subscription subscription) {
        log.debug("Adding subscription: {}", subscription);
        sessionFactory.persist(subscription);
        log.debug("Subscription created with id {}", subscription.getId());
        return subscription.getId();
    }

    @Override
    public void deleteSubscription(long id) {
        log.debug("Delete subscription with id: {}", id);
        Subscription subscription = sessionFactory.find(Subscription.class, id);
        if (subscription != null) sessionFactory.remove(subscription);
        log.debug("Subscription deleted with id {}", id);
    }

    @Override
    public void updateSubscription(Subscription subscription) {
        log.debug("Updating subscription: {}", subscription);
        sessionFactory.merge(subscription);
        log.debug("Subscription updated: {}", subscription);
    }

    @Override
    public Optional<Subscription> getSubscription(long id) {
        log.debug("Getting subscription with id {}", id);
        return Optional.ofNullable(sessionFactory.find(Subscription.class, id));
    }

    @Override
    public List<Subscription> getAllSubscription() {
        log.debug("Getting all users");
        return sessionFactory.createQuery("from Subscription", Subscription.class).getResultList();
    }

    @Override
    public Optional<Subscription> getSubscriptionsByUserId(long id) {
        log.debug("Getting subscription with user id {}", id);
        return sessionFactory.createQuery("from Subscription where Subscription.user.id = :id", Subscription.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
    }

    @Override
    public Optional<SubscriptionStatus> getSubscriptionStatusById(String statusId) {
        log.debug("Getting car subscription with id {}", statusId);
        return Optional.ofNullable(sessionFactory.find(SubscriptionStatus.class, statusId));
    }

    @Override
    public List<Subscription> searchSubscription(SubscriptionSearchInfo info) {
        log.debug("Getting subscription with search info {}", info);
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Subscription> query = builder.createQuery(Subscription.class);
        Root<Subscription> root = query.from(Subscription.class);

        List<Predicate> predicates = SubscriptionCriteriaBuilderManager.buildCriteria(info, builder, query, root);

        int offset = (info.getPage() - 1) * info.getSize();
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Subscription> typedQuery = sessionFactory.createQuery(query)
                .setFirstResult(offset)
                .setMaxResults(info.getSize());
        return typedQuery.getResultList();

    }
}
