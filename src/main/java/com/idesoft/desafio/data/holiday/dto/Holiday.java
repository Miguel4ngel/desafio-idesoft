package com.idesoft.desafio.data.holiday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Holiday {

    private String date;
    private String title;
    private String type;
    private boolean inalienable;
    private String extra;

}
