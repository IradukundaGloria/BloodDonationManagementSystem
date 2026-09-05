package com.blooddonation.dao;

import com.blooddonation.entity.Recipient;
import com.blooddonation.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class RecipientDAO extends GenericDAO<Recipient, Long> {

    public RecipientDAO() {
        super(Recipient.class);
    }

    public Recipient findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Recipient> query = em.createQuery(
                "SELECT r FROM Recipient r WHERE LOWER(r.email) = :email", Recipient.class);
            query.setParameter("email", email.trim().toLowerCase());
            List<Recipient> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public boolean isEmailExists(String email, Long excludeRecipientId) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(r) FROM Recipient r WHERE LOWER(r.email) = :email";
            if (excludeRecipientId != null) {
                jpql += " AND r.recipientId <> :excludeId";
            }
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("email", email.trim().toLowerCase());
            if (excludeRecipientId != null) {
                query.setParameter("excludeId", excludeRecipientId);
            }
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
