package com.binar.kelompok3.secondhand.service.wishlist;

import com.binar.kelompok3.secondhand.model.entity.Wishlist;
import com.binar.kelompok3.secondhand.repository.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements IWishlistService {

    private WishlistRepository wishlistRepository;


    @Override
    public void createWishList(Wishlist wishlist) {
        wishlistRepository.save(wishlist);
    }

    @Override
    public List<Wishlist> readWishList(Integer userId) {
        return wishlistRepository.findAllByUserIdOrderByCreatedOnDesc(userId);
    }

    @Override
    public void deleteById(Integer id) {
        wishlistRepository.deleteById(id);
    }
}