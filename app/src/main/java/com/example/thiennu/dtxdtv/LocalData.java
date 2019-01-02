package com.example.thiennu.dtxdtv;

import com.example.thiennu.dtxdtv.TripInfo;

import java.util.ArrayList;

class DataException extends Exception {
    static final int SET_PHONE_TWICE = 0;
    static final int PHONE_NOT_SET = 1;

    private final int kind;

    DataException(int kind) {
        this.kind = kind;
    }

    int getKind() {
        return kind;
    }

    public String toString() {
        return "Data error";
    }
}

class LocalData {
    static private String phoneNumber;

    static void setPhoneNumber(String p) throws DataException {
        if (phoneNumber != null) {
            throw new DataException(DataException.SET_PHONE_TWICE);
        }
        phoneNumber = p;
    }

    static String getPhoneNumber() throws DataException {
        if (phoneNumber == null) {
            throw new DataException(DataException.PHONE_NOT_SET);
        }
        return phoneNumber;
    }
}
