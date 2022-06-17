package com.binar.kelompok3.secondhand.service.wishlist;

import com.binar.kelompok3.secondhand.model.Users;
import com.binar.kelompok3.secondhand.model.Wishlist;
import com.binar.kelompok3.secondhand.repository.WishlistRepository;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
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
        return wishlistRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
    }

    @Override
    public void deleteById(Integer id) {
        wishlistRepository.deleteById(id);
    }
}
