package com.example.A2.repository;

import com.example.A2.domain.Customer;
import com.example.A2.domain.Review;
import com.example.A2.domain.dto.CustomerResponseLikes;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import java.util.List;

public class CustomerRepositoryImpl implements ICustomerRepository{
    @PersistenceContext
    EntityManager em;

    @Override
    public Page<CustomerResponseLikes> getCustomerStats(Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> review_likes_cq = cb.createQuery(Tuple.class);

        Root<Review> review = review_likes_cq.from(Review.class);
        review_likes_cq.multiselect(review.get("customer").get("id").alias("customer_id"), cb.avg(cb.coalesce(review.get("numberLikes"),0)).alias("numberLikes"))
                .groupBy(review.get("customer").get("id"))
                .orderBy(cb.desc(cb.avg(cb.coalesce(review.get("numberLikes"),0))));

        TypedQuery<Tuple> typedQuery = em.createQuery(review_likes_cq);

        List<CustomerResponseLikes> results = typedQuery.setFirstResult(pageable.getPageNumber()* pageable.getPageSize())
                .setMaxResults(pageable.getPageSize()).getResultList()
                .stream().map(row -> {
                    CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
                    Root<Customer> customer = cq.from(Customer.class);
                    cq.select(customer).where(cb.equal(customer.get("id"),row.get("customer_id")));

                    return new CustomerResponseLikes(em.createQuery(cq).getSingleResult().getId(),
                            em.createQuery(cq).getSingleResult().getFirstName(),
                            em.createQuery(cq).getSingleResult().getLastName(),
                            (Double) row.get("numberLikes"));
                }).toList();

        CriteriaQuery<Long> count_cq = cb.createQuery(Long.class);
        Root<Review> reviewCount = count_cq.from(Review.class);
        count_cq.select(cb.countDistinct(reviewCount.get("customer").get("id")));
        long total = em.createQuery(count_cq).getSingleResult();

        return new PageImpl<>(results,pageable,total);
    }
}
