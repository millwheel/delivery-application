package msa.restaurant.exception.store;

import msa.restaurant.exception.DeliveryRestaurantException;

public class InvalidManagerException extends DeliveryRestaurantException {

    private static final String INVALID_MANAGER_MESSAGE = "This store doesn't belong to this manager. (managerId = %s)";

    public InvalidManagerException(String managerId) {
        super(String.format(INVALID_MANAGER_MESSAGE, managerId));
    }

}
