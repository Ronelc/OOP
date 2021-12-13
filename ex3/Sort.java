import oop.ex3.searchengine.*;

import java.util.*;
import java.lang.*;


/**
 * sorting the hotels by stars
 */
class StarsSort implements Comparator<Hotel> {


	@Override
	public int compare(Hotel hotel1, Hotel hotel2) {
		if ((hotel1.getStarRating() == hotel2.getStarRating())) {
			return hotel1.getPropertyName().compareTo(hotel2.getPropertyName());
		}
		return hotel2.getStarRating() - hotel1.getStarRating();
	}
}

/**
 * sorting the hotels by distance
 */
class DistanceSort implements Comparator<Hotel> {
	double x;
	double y;
	int strong = 2;
	double root = 0.5;

	public DistanceSort(double x, double y) {
		this.x = x;
		this.y = y;
	}


	@Override
	public int compare(Hotel hotel1, Hotel hotel2) {
		double A_distance_X = Math.pow(hotel1.getLatitude() - x, strong);
		double A_distance_y = Math.pow(hotel1.getLongitude() - y, strong);
		double B_distance_X = Math.pow(hotel2.getLatitude() - x, strong);
		double B_distance_y = Math.pow(hotel2.getLongitude() - y, strong);
		double A = Math.pow(A_distance_X + A_distance_y, root);
		double B = Math.pow(B_distance_X + B_distance_y, root);
		if (A == B) {
			return hotel2.getNumPOI() - hotel1.getNumPOI();
		}
		double distance = A - B;
		if (distance > 0) {
			return 1;
		} else {
			return -1;
		}
	}
}