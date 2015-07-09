package chatbot.services;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.impl.Propertyfiles;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class RaffleService {

    private final ViewerRepository viewerRepository;
    private final TicketService ticketService;

    @Autowired
    public RaffleService(ViewerRepository viewerRepository, TicketService ticketService) {
        this.viewerRepository = viewerRepository;
        this.ticketService = ticketService;
    }

    public Set<Viewer> findRaffleParticipants() {
        return viewerRepository.findCurrentViewers();
    }

    public Viewer findWinner(Set<Viewer> raffleParticipants) {
        return ticketService.findRandomKeyByTicketChance(raffleParticipants);
    }

}
