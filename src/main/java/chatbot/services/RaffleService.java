package chatbot.services;

import chatbot.entities.Viewer;
import chatbot.repositories.api.ViewerRepository;
import chatbot.repositories.impl.Propertyfiles;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by matthias.popp on 05.02.2015.
 */
@Component
public class RaffleService {

    private final ViewerRepository viewerRepository;
    private final Random rand;

    @Autowired
    public RaffleService(ViewerRepository viewerRepository) {
        this.viewerRepository = viewerRepository;
        rand = new Random();
    }

    public Set<Viewer> findRaffleParticipants() {
        return viewerRepository.findCurrentViewers();
    }

    public Viewer findWinner(Set<Viewer> raffleParticipants) {
        Set<Viewer> participants = raffleParticipants.parallelStream().filter(p -> p.wallet.sessionPoints > 0).collect(Collectors.toSet());
        long totalTickets = participants.parallelStream().mapToLong(p -> p.wallet.sessionPoints).sum();
        long randomTickets = nextRandomLongInInterval(0, totalTickets);
        return electWinner(participants, randomTickets);
    }

    private Viewer electWinner(Set<Viewer> raffleParticipants, long randomTickets) {
        for(Viewer viewer: raffleParticipants){
            randomTickets -= viewer.wallet.sessionPoints;
            if(randomTickets <= 0){
                return viewer;
            }
        }
        return null;
    }

    private long calculateTotalTickets(Set<Viewer> raffleParticipants) {
        long totalTickets = 0L;
        for(Viewer viewer : raffleParticipants){
            totalTickets += viewer.wallet.sessionPoints;
        }
        return totalTickets;
    }

    private long nextRandomLongInInterval(long min, long max){
        return min + (long)(rand.nextDouble()*(max - min));
    }
}
