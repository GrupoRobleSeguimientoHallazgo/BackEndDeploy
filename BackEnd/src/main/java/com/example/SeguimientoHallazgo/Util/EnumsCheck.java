package com.example.SeguimientoHallazgo.Util;

import com.example.SeguimientoHallazgo.Common.Priority;
import com.example.SeguimientoHallazgo.Common.Status;
import org.springframework.stereotype.Component;

@Component
public class EnumsCheck {

    public boolean isValidStatus(String pStatus) {
        try {
            Status.valueOf(pStatus.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public boolean isValidPriority(String pPriority) {
        try {
            Priority.valueOf(pPriority.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
