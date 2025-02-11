package de.fred4jupiter.fredbet.service.ranking;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import de.fred4jupiter.fredbet.repository.UsernamePoints;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
class SameRankingCollector {

    void markEntriesWithSameRanking(List<UsernamePoints> rankings) {
        final Multimap<Integer, UsernamePoints> map = ArrayListMultimap.create();

        rankings.forEach(usernamePoints -> {
            map.put(usernamePoints.getUniqueHash(), usernamePoints);
        });

        map.keySet().forEach(key -> {
            Collection<UsernamePoints> valuesOfKey = map.get(key);
            if (valuesOfKey.size() > 1) {
                valuesOfKey.forEach(usernamePoints -> {
                    usernamePoints.setSameRankingPositionAsOtherUser(true);
                });
            }
        });
    }
}
