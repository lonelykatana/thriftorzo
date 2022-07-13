package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Optional<Notification> findById(Integer id);

    @Query(value = "select * from notification where user_id=?1 order by created_on DESC",
            nativeQuery = true)
    List<Notification> findNotifications(Integer userId);

    @Query(value = "select count(1) from notification where user_id=?1 and is_read=false ", nativeQuery = true)
    Integer unreadNotifications(Integer userId);
}
