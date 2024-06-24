package com.example.SeguimientoHallazgo.Domain.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    private String toDestination;
    private String recipientName;
    private String emailTitle;
    private String emailBody;
}
