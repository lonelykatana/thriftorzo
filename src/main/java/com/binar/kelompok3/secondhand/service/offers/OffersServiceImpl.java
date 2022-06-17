package com.binar.kelompok3.secondhand.service.offers;

import com.binar.kelompok3.secondhand.model.Offers;
import com.binar.kelompok3.secondhand.model.Products;
import com.binar.kelompok3.secondhand.model.Users;
import com.binar.kelompok3.secondhand.repository.OffersRepository;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OffersServiceImpl implements IOffersService{

    private OffersRepository offersRepository;
    private IUsersService iUsersService;
    private IProductsService iProductsService;

    @Override
    public List<Offers> getAllOffers() {
        return offersRepository.getAllOffers();
    }

    @Override
    public String saveOffers(Integer userId, Integer productId, Double offerPrice, Integer status) {
        Offers offers = new Offers();
        Users users = iUsersService.findUsersById(userId);
        Products products = iProductsService.findProductsById(productId);
        offers.setUserId(users);
        offers.setProductId(products);
        offers.setOfferPrice(offerPrice);
        offers.setStatus(status);
        return "sukses menyimpan offer";
    }

    //service untuk riwayat tawaran buyer
    @Override
    public List<Offers> getAllByUserId(Integer userId) {
        return offersRepository.getAllByUserId(userId);
    }

    //service untuk riwayat tawaran penjual
    @Override
    public List<Offers> getAllByProductId(Integer productId) {
        return offersRepository.getAllByProductId(productId);
    }

    //update status pada tawaran
    @Override
    public Integer updateOffers(Integer id, Integer status) {
        offersRepository.updateOffers(id, status);
        return 1; //1 untuk sukses
    }

    @Override
    public Integer deleteOffersById(Integer id) {
        offersRepository.deleteOffersById(id);
        return 1; //1 untuk sukses
    }
}
