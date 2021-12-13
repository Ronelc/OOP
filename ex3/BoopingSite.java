import oop.ex3.searchengine.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


/**
 * this class get file with hotels with many parameters, and sort the hotels on demand of the user
 */
public class BoopingSite {

	/** file that contain the hotels */
	final String fileName;

	/** array of hotels*/
	Hotel[] hotelsList;

	/** maximal and minimal latitude add longitude */
	final double MAXLATITUDE = 90.0;
	final double MINLATITUDE = -90.0;
	final double MAXLONGITUDE = 180.0;
	final double MINLONGITUDE = -180.0;


	public BoopingSite(String name) {
		fileName = name;
		hotelsList = HotelDataset.getHotels(name);
	}

	/**
	 * return array of hotels in the given city sorted by rating and by name
	 *
	 * @param city name of city
	 * @return array of hotels in the given city sorted by rating and by name
	 */
	public Hotel[] getHotelsInCityByRating(String city) {
		ArrayList<Hotel> citiesLst = new ArrayList<Hotel>();
		if (city != null) {
			for (Hotel hotel : hotelsList) {
				if (hotel.getCity().equals(city)) {
					citiesLst.add(hotel);
				}
			}
			if (citiesLst.size() > 0) {
				Collections.sort(citiesLst, new StarsSort());
				return citiesLst.toArray(new Hotel[citiesLst.size()]);
			}
			return new Hotel[0];
		}
		return new Hotel[0];
	}

	/**
	 * return array of hotels sorted by distance from given coordinators and by poi
	 *
	 * @param latitude  num of latitude
	 * @param longitude num of longitude
	 * @return array of hotels sorted by distance from given coordinators and by poi
	 */
	public Hotel[] getHotelsByProximity(double latitude, double longitude) {
		if (checkInput(latitude, longitude)) {
			ArrayList<Hotel> listToSort = new ArrayList<Hotel>(Arrays.asList(hotelsList));
			Collections.sort(listToSort, new DistanceSort(latitude, longitude));
			return listToSort.toArray(new Hotel[listToSort.size()]);
		}
		return new Hotel[0];
	}


	/**
	 * return array of hotels in given city sorted by distance from given coordinators and by poi
	 *
	 * @param city      name of city
	 * @param latitude  num of latitude
	 * @param longitude num of longitude
	 * @return array of hotels in given city sorted by distance from given coordinators and by poi
	 */
	public Hotel[] getHotelsInCityByProximity(String city, double latitude, double longitude) {
		if (checkInput(latitude, longitude) && city != null) {
			Hotel[] citiesLst = getHotelsInCityByRating(city);
			if(citiesLst.length >0){
				ArrayList<Hotel> listToSort = new ArrayList<Hotel>(Arrays.asList(citiesLst));
				Collections.sort(listToSort, new DistanceSort(latitude, longitude));
				return listToSort.toArray(new Hotel[listToSort.size()]);
			}
			return citiesLst;

		}
		return new Hotel[0];


	}

	/**
	 * @param latitude  num of latitude
	 * @param longitude num of longitude
	 * @return true if input is valid, false otherwise.
	 */
	private boolean checkInput(double latitude, double longitude) {
		return !(latitude > MAXLATITUDE) && !(latitude < MINLATITUDE) &&
			   !(longitude > MAXLONGITUDE) && !(longitude < MINLONGITUDE);

	}


}
