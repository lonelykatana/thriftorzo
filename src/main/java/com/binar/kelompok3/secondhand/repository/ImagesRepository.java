package com.binar.kelompok3.secondhand.repository;

import com.binar.kelompok3.secondhand.model.entity.Images;
import com.binar.kelompok3.secondhand.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Integer> {

    Images findImagesByImageUrl(String imageUrl);

    Images findImagesByIdAndUsers(Integer id, Users users);

    Images findImagesById(Integer id);

    Images findImagesByCreatedOn(Date createdOn);

    Images findImagesByUsers(Integer users);

    @Query(value ="update image set image_url=?1, title=?2 where id=?3" ,nativeQuery = true)
    void updateImage(String imageUrl, String title, Integer id);
}
