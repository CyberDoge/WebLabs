package com.web.app.dto;

import java.util.List;
import java.util.UUID;

public class AutoRentalDto {
    public UUID id;
    public List<AutoDto> autos;

    public static AutoRentalDto from(UUID id, List<AutoDto> autos) {
        var res = new AutoRentalDto();
        res.id = id;
        res.autos = autos;
        return res;
    }

}
