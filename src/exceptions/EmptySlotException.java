package exceptions;

import gamehandlers.ItemType;

public class EmptySlotException extends Exception {

    public EmptySlotException(ItemType item) {
        super(item + " is not available");
    }
}
