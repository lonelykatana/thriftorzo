package com.binar.kelompok3.secondhand.service.offers;

import com.binar.kelompok3.secondhand.enumeration.ERole;
import com.binar.kelompok3.secondhand.model.entity.Offers;
import com.binar.kelompok3.secondhand.model.entity.Products;
import com.binar.kelompok3.secondhand.model.entity.Users;
import com.binar.kelompok3.secondhand.repository.OffersRepository;
import com.binar.kelompok3.secondhand.service.notification.INotificationService;
import com.binar.kelompok3.secondhand.service.products.IProductsService;
import com.binar.kelompok3.secondhand.service.users.IUsersService;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.binar.kelompok3.secondhand.utils.Constant.*;

@Service
public class OffersServiceImpl implements IOffersService {

    private OffersRepository offersRepository;
    private IUsersService iUsersService;
    private IProductsService iProductsService;
    private INotificationService iNotificationService;

    public OffersServiceImpl(OffersRepository offersRepository, IUsersService iUsersService, IProductsService iProductsService, INotificationService iNotificationService) {
        this.offersRepository = offersRepository;
        this.iUsersService = iUsersService;
        this.iProductsService = iProductsService;
        this.iNotificationService = iNotificationService;
    }

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
        iNotificationService.saveNotification(TAWARAN_PRODUCT, SALURKAN_KE_PENJUAL, ERole.BUYER.getNumber(), offerId, products1, userId);
        iNotificationService.saveNotification(MENDAPAT_TAWARAN, PELANGGAN_TERTARIK, ERole.SELLER.getNumber(), offerId, products1, sellerId);
    }

    @Override
    @Cacheable
    public List<Offers> getAllByUserId(Integer userId) {
        return offersRepository.findAllByUserId(userId);
    }

    @Override
    @Cacheable
    public List<Offers> getHistorySeller(Integer userId) {
        return offersRepository.getHistorySeller(userId);
    }

    @Override
    public Offers findOffersById(Integer id) {
        return offersRepository.findOffersById(id);
    }

    @Override
    @Cacheable
    public Boolean getTransaction(Integer userId, String productId) {
        Offers transaction = offersRepository.getTransaction(userId, productId);
        if (transaction == null)
            return false;
        return !transaction.getStatus().equals(2) && !transaction.getStatus().equals(4);
    }

    @Override
    @CachePut
    public void updateOffers(Integer id, Integer status) {

        String productId = findOffersById(id).getProductId().getId();
        Products products = iProductsService.findProductsById(productId);
        Integer buyerId = findOffersById(id).getUserId().getId();
        Offers offerId = findOffersById(id);
        if (status.equals(2)) {
            offersRepository.updateOffers(id, status);
            iNotificationService.saveNotification(TAWARAN_PRODUCT, TAWARAN_DITOLAK, ERole.BUYER.getNumber(), offerId, products, buyerId);
        } else if (status.equals(3)) {
            offersRepository.updateOffers(id, status);
            iNotificationService.saveNotification(TAWARAN_PRODUCT, TAWARAN_DITERIMA, ERole.BUYER.getNumber(), offerId, products, buyerId);
        } else if (status.equals(4)) {
            iProductsService.updateStatus(productId, 2);
            offersRepository.setAllStatusToDeclined(productId, id);
            offersRepository.updateOffers(id, status);
            iNotificationService.saveNotification(TAWARAN_PRODUCT, TAWARAN_BERHASIL, ERole.BUYER.getNumber(), offerId, products, buyerId);
            List<Offers> userIdandOffers = offersRepository.getOffersUserId(productId);

            List<Offers> userIdFilter = userIdandOffers.stream().filter(value ->
                    !Objects.equals(value.getUserId().getId(), buyerId)).collect(Collectors.toList());
            for (Offers offer : userIdFilter) {
                iNotificationService.saveNotification(TAWARAN_PRODUCT, TAWARAN_TERJUAL,
                        ERole.BUYER.getNumber(), offer, products,
                        offer.getUserId().getId());

            }

        } else offersRepository.updateOffers(id, status);

    }

    @Override
    public void deleteOffersById(Integer id) {
        offersRepository.deleteOffersById(id);
    }

}
