package com.PGmitra.app.Repository;

import com.PGmitra.app.Entity.Owner;
import com.PGmitra.app.Entity.Payment;
import com.PGmitra.app.Entity.Tenant;
import com.PGmitra.app.Enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
    List<Payment> findByTenant(Tenant tenant);
    List<Payment> findByOwner(Owner owner);
    List<Payment> findByStatusAndDueDateBefore(Status status, LocalDate date);
    List<Payment> findByStatus(Status status);

    // @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
    //        "FROM Payment p WHERE p.tenant.id = :tenantId AND " +
    //        "FUNCTION('YEAR', p.dueDate) = :year AND FUNCTION('MONTH', p.dueDate) = :month")
    @Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
                    "FROM payments p WHERE p.tenant_id = :tenantId " +
                    "AND EXTRACT(YEAR FROM p.due_date) = :year " +
                    "AND EXTRACT(MONTH FROM p.due_date) = :month",
        nativeQuery = true)
    boolean existsByTenantIdAndMonth(@Param("tenantId") Long tenantId,
                                 @Param("year") int year,
                                 @Param("month") int month);

    // Overload for YearMonth
    default boolean existsByTenantIdAndMonth(Long tenantId, YearMonth yearMonth) {
        return existsByTenantIdAndMonth(tenantId, yearMonth.getYear(), yearMonth.getMonthValue());
    }
}
