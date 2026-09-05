package com.blooddonation.dao;

import com.blooddonation.entity.Donor;
import com.blooddonation.util.JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class DonorDAO extends GenericDAO<Donor, Long> {

    public DonorDAO() {
        super(Donor.class);
    }

    public Donor findByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return null;
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Donor> query = em.createQuery(
                "SELECT d FROM Donor d WHERE LOWER(d.email) = :email", Donor.class);
            query.setParameter("email", email.trim().toLowerCase());
            List<Donor> results = query.getResultList();
            return results.isEmpty() ? null : results.get(0);
        } finally {
            em.close();
        }
    }

    public boolean isEmailExists(String email, Long excludeDonorId) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT COUNT(d) FROM Donor d WHERE LOWER(d.email) = :email";
            if (excludeDonorId != null) {
                jpql += " AND d.donorId <> :excludeId";
            }
            TypedQuery<Long> query = em.createQuery(jpql, Long.class);
            query.setParameter("email", email.trim().toLowerCase());
            if (excludeDonorId != null) {
                query.setParameter("excludeId", excludeDonorId);
            }
            return query.getSingleResult() > 0;
        } finally {
            em.close();
        }
    }
}
