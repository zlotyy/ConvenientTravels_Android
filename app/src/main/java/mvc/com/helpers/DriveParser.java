package mvc.com.helpers;

import java.util.Collections;
import java.util.List;

import mvc.com.dto.DriveDTO;
import mvc.com.dto.DriveDTO_String;
import mvc.com.enums.LuggageSize;
import mvc.com.model.DriveModel;
import mvc.com.model.StopOverPlaceModel;

/**
 * Created by zloty on 2018-01-12.
 */

public class DriveParser {


    /**
     * metoda zamienia przejazd z formularza androidowego (pola String) na DriveDTO
     * @param driveDTO_String
     * @return
     */
    public static DriveDTO parseDriveDTOString_TO_DriveDTO(DriveDTO_String driveDTO_String){

        DriveDTO driveDTO = new DriveDTO();

        driveDTO.setCityStart(driveDTO_String.getCityStart());
        driveDTO.setStreetStart(driveDTO_String.getStreetStart());
        driveDTO.setExactPlaceStart(driveDTO_String.getExactPlaceStart());
        driveDTO.setCityArrival(driveDTO_String.getCityArrival());
        driveDTO.setStreetArrival(driveDTO_String.getStreetArrival());
        driveDTO.setExactPlaceArrival(driveDTO_String.getExactPlaceArrival());
        driveDTO.setStartDate(driveDTO_String.getStartDate());
        driveDTO.setReturnDate(driveDTO_String.getReturnDate());
        driveDTO.setIsRoundTrip(driveDTO_String.getIsRoundTrip());
        driveDTO.setIsSmokePermitted(driveDTO_String.getIsSmokePermitted());
        driveDTO.setDriverComment(driveDTO_String.getDriverComment());

        if(!driveDTO_String.getCost().equals("")) {
            driveDTO.setCost(Integer.parseInt(driveDTO_String.getCost()));
        } else {
            driveDTO.setCost(0);
        }

        if(!driveDTO_String.getPassengersQuantity().equals("")){
            driveDTO.setPassengersQuantity(Integer.parseInt(driveDTO_String.getPassengersQuantity()));
        } else {
            driveDTO.setPassengersQuantity(0);
        }

        if(driveDTO_String.getLuggageSize() != null) {
            if (driveDTO_String.getLuggageSize().equals("Maly")) {
                driveDTO.setLuggageSize(LuggageSize.MALY);
            } else if (driveDTO_String.getLuggageSize().equals("Sredni")) {
                driveDTO.setLuggageSize(LuggageSize.SREDNI);
            } else if (driveDTO_String.getLuggageSize().equals("Duzy")) {
                driveDTO.setLuggageSize(LuggageSize.DUZY);
            } else {
                driveDTO.setLuggageSize(null);
            }
        } else {
            driveDTO.setLuggageSize(null);
        }

        driveDTO.setStopOverPlaces(Collections.<StopOverPlaceModel>emptyList());


        return driveDTO;
    }


    public static DriveDTO_String parseDriveModel_TO_DriveDTOString(DriveModel drive){
        DriveDTO_String driveDTO_string = new DriveDTO_String();

        // parsowanie daty na stringa
        DateFormatHelper dateFormatHelper = new DateFormatHelper(drive.getStartDate(), "yyyy-MM-dd HH:mm");
        String startDate = dateFormatHelper.calendarToString_DateTimeFormat();
        dateFormatHelper = new DateFormatHelper(drive.getReturnDate(), "yyyy-MM-dd HH:mm");
        String returnDate = dateFormatHelper.calendarToString_DateTimeFormat();

        String stopOverPlaces = "";
        List<StopOverPlaceModel> stopOverPlacesList = drive.getStopOverPlaces();
        if(stopOverPlacesList != null){
            for(StopOverPlaceModel place : stopOverPlacesList){
                stopOverPlaces.concat(place.getStopOverCity() + " " + place.getStopOverStreet());
                // dodaj nowa linie
                if(!place.equals( stopOverPlacesList.get(0) )){
                    stopOverPlaces.concat("\n");
                }
            }
        }

        driveDTO_string.setCityStart(drive.getCityStart());
        driveDTO_string.setStreetStart(drive.getStreetStart());
        driveDTO_string.setExactPlaceStart(drive.getExactPlaceStart());
        driveDTO_string.setCityArrival(drive.getCityArrival());
        driveDTO_string.setStreetArrival(drive.getStreetArrival());
        driveDTO_string.setExactPlaceArrival(drive.getExactPlaceArrival());
        driveDTO_string.setStartDate(startDate);
        driveDTO_string.setReturnDate(returnDate);
        driveDTO_string.setIsRoundTrip(drive.isRoundTrip() ? "Tak" : "Nie");
        driveDTO_string.setStopOverPlaces(stopOverPlaces);
        driveDTO_string.setCost(String.valueOf(drive.getCost()));

        return driveDTO_string;
    }



}
