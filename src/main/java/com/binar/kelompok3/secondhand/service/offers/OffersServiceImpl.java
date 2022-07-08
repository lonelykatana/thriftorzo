package com.binar.kelompok3.secondhand.service.offers;

import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.OffersRepository;
import com.binar.kelompok3.secondhand.service.notification.INotificationService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OffersServiceImpl implements IOffersService {

    private OffersRepository offersRepository;
    private IUsersService iUsersService;
    private IProductsService iProductsService;

    public OffersServiceImpl(OffersRepository offersRepository, IUsersService iUsersService, IProductsService iProductsService, INotificationService iNotificationService) {
        this.offersRepository = offersRepository;
        this.iUsersService = iUsersService;
        this.iProductsService = iProductsService;
        this.iNotificationService = iNotificationService;
    }

    private INotificationService iNotificationService;


    private String isiTitle = "Penawaran produk";

    @Override
    public List<Offers> getAllOffers() {
        return offersRepository.getAllOffers();
    }

    //userId = userId pembeli
    @Override
    public void saveOffers(Integer userId, String productId, Double offerPrice, Integer status) {
        Offers offers = new Offers();
        Users users = iUsersService.findUsersById(userId);
        Products products = iProductsService.findProductsById(productId);
        offers.setUserId(users);
        offers.setProductId(products);
        offers.setOfferPrice(offerPrice);
        offers.setStatus(status);
        offersRepository.save(offers);

        Integer sellerId = products.getUserId().getId();
        Products products1 = iProductsService.findProductsById(productId);
        Offers offerId = findOffersById(offers.getId());
        iNotificationService.saveNotification(isiTitle, "Penawaran telah dilanjutkan ke" +
                " penjual", offerId, products1, userId);
        iNotificationService.saveNotification(isiTitle, "Anda mendapat tawaran", offerId, products1,
                sellerId);
    }

    //service untuk riwayat tawaran buyer
    @Override
    public List<Offers> getAllByUserId(Integer userId) {
        return offersRepository.findAllByUserId(userId);
    }

    //service untuk riwayat tawaran penjual

    @Override
    public List<Offers> getHistorySeller(Integer userId) {
        return offersRepository.getHistorySeller(userId);
    }

    @Override
    public Offers findOffersById(Integer id) {
        return offersRepository.findOffersById(id);
    }

    //update status pada tawaran
    @Override
    public void updateOffers(Integer id, Integer status) {

        String productId = findOffersById(id).getProductId().getId();
        Products products = iProductsService.findProductsById(productId);
        Integer buyerId = findOffersById(id).getUserId().getId();
        Offers offerId = findOffersById(id);
        if (status.equals(2)) {
            offersRepository.updateOffers(id, status);
            iNotificationService.saveNotification(isiTitle, "Penawaran " +
                    "anda ditolak penjual", offerId, products, buyerId);
        } else if (status.equals(3)) {
            offersRepository.updateOffers(id, status);
            iNotificationService.saveNotification(isiTitle, "Penawaran " +
                            "anda diterima, penjual akan menhubungi anda via WhatsApp", offerId, products,
                    buyerId);
        } else if (status.equals(4)) {
            iProductsService.updateStatus(productId, 2);
            offersRepository.setAllStatusToDeclined(productId, id);
            offersRepository.updateOffers(id, status);
            iNotificationService.saveNotification(isiTitle, "Penawaran " +
                    "berhasil!", offerId, products, buyerId);
        } else offersRepository.updateOffers(id, status);

    }

    @Override
    public void deleteOffersById(Integer id) {
        offersRepository.deleteOffersById(id);
    }


}
