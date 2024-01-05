package com.skm.cards.service;

import com.skm.cards.dto.CardsDto;
import org.springframework.stereotype.Service;


public interface ICardsService {


    /**
     * @param mobileNumber
     */
    void createCard(String mobileNumber);

    /**
     * @param mobileNumber
     * @return
     */
    CardsDto fetchCard(String mobileNumber);

    /**
     * @param cardsDto
     * @return
     */
    boolean updateCard(CardsDto cardsDto);

    /**
     * @param mobileNumber
     * @return
     */
    boolean deleteCard(String mobileNumber);

}
