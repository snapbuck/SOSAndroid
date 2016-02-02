package vn.snapbuck.sos.model;

/**
 * Created by sb4 on 10/28/15.
 */
public class MapModel {
    public CharSequence placeId;
    public CharSequence description;
    public boolean check;
    public CharSequence placeName, location;

    public CharSequence getPlaceName() {
        return placeName;
    }

    public void setPlaceName(CharSequence placeName) {
        this.placeName = placeName;
    }

    public CharSequence getLocation() {
        return location;
    }

    public void setLocation(CharSequence location) {
        this.location = location;
    }

    public CharSequence getPlaceId() {
        return placeId;
    }

    public void setPlaceId(CharSequence placeId) {
        this.placeId = placeId;
    }

    public CharSequence getDescription() {
        return description;
    }

    public void setDescription(CharSequence description) {
        this.description = description;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public MapModel(CharSequence placeId,CharSequence placeName, CharSequence location, CharSequence description, boolean check) {

        this.placeId = placeId;
        this.description = description;
        this.check = check;
        this.placeName = placeName;
        this.location = location;
    }
}
