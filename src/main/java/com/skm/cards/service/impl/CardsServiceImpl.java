package com.skm.cards.service.impl;

import com.skm.cards.constants.CardsConstants;
import com.skm.cards.dto.CardsDto;
import com.skm.cards.entity.Cards;
import com.skm.cards.exception.CardAlreadyExistsException;
import com.skm.cards.exception.ResourceNotFoundException;
import com.skm.cards.mapper.CardsMapper;
import com.skm.cards.repository.CardsRepository;
import com.skm.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.apache.catalina.mapper.Mapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {

    private CardsRepository cardsRepository;

    @Override
    public void createCard(String mobileNumber) {
        if (cardsRepository.findByMobileNumber(mobileNumber).isPresent()){
            throw new CardAlreadyExistsException("Card already exist for mobile number"+ mobileNumber);
        }
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber){
        Cards card=new Cards();
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        card.setCardNumber(Long.toString(randomCardNumber));
        card.setMobileNumber(mobileNumber);
        card.setCardType(CardsConstants.CREDIT_CARD);
        card.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        card.setAmountUsed(0);
        card.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return card;
    }

    @Override
    public CardsDto fetchCard(String mobileNumber) {
       Cards card=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
               ()->new ResourceNotFoundException("Credit Card","Mobile Number",mobileNumber));

        return CardsMapper.mapToCardsDto(card,new CardsDto());
    }

    @Override
    public boolean updateCard(CardsDto cardsDto) {
        Cards card=cardsRepository.findByMobileNumber(cardsDto.getMobileNumber()).orElseThrow(
                ()->  new ResourceNotFoundException("Card", "CardNumber", cardsDto.getCardNumber()));
        CardsMapper.mapToCards(cardsDto,card);
        cardsRepository.save(card);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards card=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()->new ResourceNotFoundException("Credit Card","Mobile Number",mobileNumber));
        cardsRepository.deleteById(card.getCardId());
        return true;
    }
}
