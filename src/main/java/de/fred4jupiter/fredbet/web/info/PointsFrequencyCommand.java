package de.fred4jupiter.fredbet.web.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.MultiValuedMap;

import de.fred4jupiter.fredbet.repository.PointCountResult;

public class PointsFrequencyCommand {

	private final List<PointsFrequency> resultList = new ArrayList<>();

	public PointsFrequencyCommand(MultiValuedMap<Double, PointCountResult> map) {
		List<Double> pointsList = new ArrayList<>(map.keySet());
		Collections.reverse(pointsList);

		for (Double points : pointsList) {
			if (points > 0) {
				Collection<PointCountResult> collection = map.get(points);
				PointsFrequency pointsFrequency = new PointsFrequency(points, new ArrayList<>(collection));
				resultList.add(pointsFrequency);	
			}			
		}
	}

	public List<PointsFrequency> getResultList() {
		return resultList;
	}

}
