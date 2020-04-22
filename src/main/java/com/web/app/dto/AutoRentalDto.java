package com.web.app.dto;

import java.util.List;
import java.util.UUID;

/**
 * Data transfer object. Объект для передачи только нужных полей и значений http ответа в виде JSON.
 */
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
